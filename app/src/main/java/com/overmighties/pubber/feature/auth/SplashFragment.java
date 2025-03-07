package com.overmighties.pubber.feature.auth;


import static com.overmighties.pubber.app.exception.ErrorSnackbarUI.showSnackbar;
import static com.overmighties.pubber.app.navigation.PubberNavRoutes.getNavDirections;

import android.app.PendingIntent;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.IntentSenderRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.credentials.CredentialManager;
import androidx.credentials.CredentialManagerCallback;
import androidx.credentials.GetCredentialRequest;
import androidx.credentials.GetCredentialResponse;
import androidx.credentials.exceptions.GetCredentialException;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption;
import com.overmighties.pubber.R;
import com.google.android.gms.auth.api.identity.GetSignInIntentRequest;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.overmighties.pubber.app.designsystem.NavigationBar;
import com.overmighties.pubber.app.settings.SettingsHandler;
import com.overmighties.pubber.app.designsystem.UIText;

import java.util.concurrent.Executors;

public class SplashFragment extends Fragment {
    private static final Integer SPLASH_DELAY=1;
    public static final String TAG="SplashFragment";
    private SplashViewModel viewModel;
    private SignInClient signInClient;
    private CredentialManager credentialManager;
    public SplashFragment(){
        super(R.layout.fragment_splash);
    }
    private NavController navController;
    // Initialize the ActivityResultLauncher for getting credentials
    private ActivityResultLauncher<IntentSenderRequest> signInLauncher;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this,
                ViewModelProvider.Factory.from(SplashViewModel.initializer))
                .get(SplashViewModel.class);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view,savedInstanceState);
        NavigationBar.smoothHide(requireActivity().findViewById(R.id.main_bottomNavView), 200);
        requireActivity().findViewById(R.id.main_topAppBarLayout_back).setVisibility(View.GONE);
        signInClient = Identity.getSignInClient(requireContext());
        credentialManager = CredentialManager.create(requireContext());
        navController=Navigation.findNavController(requireActivity(),R.id.main_navHostFragment_container);
        //TODO fix when applying new language the app crashes because of navigtion problems
        //Sign in launcher calls firebase api from viewmodel
        signInLauncher= registerForActivityResult(
                new ActivityResultContracts.StartIntentSenderForResult(),
                result -> viewModel.handleSignInResult(
                        result.getData(), signInClient,
                        (from, to)-> {
                            if(from != null && to != null)
                                Navigation.findNavController(requireActivity(),R.id.main_navHostFragment_container).navigate(getNavDirections(from,to));
                            else
                                loadView(view);
                        },
                        (errorType, uiText,logMes) -> showSnackbar(view,errorType,(UIText.ResourceString)uiText,logMes))
        );
        new Handler(
                Looper.getMainLooper())
                .postDelayed(() -> viewModel.currentUserCheckOnStart((from, to)-> {
                            if(from != null && to != null)
                                Navigation
                                    .findNavController(view)
                                    .navigate(getNavDirections(from, to));
                            else
                                loadView(view);
                        }
                    ), SPLASH_DELAY
                );
    }

    private void loadView(View view)
    {
        view.findViewById(R.id.splash_progress_loading).setVisibility(View.GONE);
        ViewStub stub = view.findViewById(R.id.splash_viewStub_accountLayout);
        View inflated = stub.inflate();
        inflated.findViewById(R.id.splash_button_signIn).setOnClickListener(v-> navController.navigate(R.id.action_splashFragment_to_signInFragment,null));
        inflated.findViewById(R.id.splash_button_signUp).setOnClickListener(v-> navController.navigate(R.id.action_splashFragment_to_signUpFragment,null));
        inflated.findViewById(R.id.splash_image_googleButton).setOnClickListener(v-> signInWithGoogle());
        int google_button_id = (SettingsHandler.LanguageHelper.getLanguage(view.getContext()).equals(SettingsHandler.LanguageHelper.LANGUAGE_POLISH))? R.drawable.ic_google_button_polish : R.drawable.ic_google_button_english;
        ((ImageView)inflated.findViewById(R.id.splash_image_googleButton)).setImageResource(google_button_id);
        int pubber_image_id = (SettingsHandler.ThemeHelper.getSavedTheme(view.getContext()).equals(SettingsHandler.ThemeHelper.THEME_LIGHT))? R.drawable.ic_pubber_light_theme : R.drawable.ic_pubber_dark_theme;
        ((ImageView)inflated.findViewById(R.id.splash_image_pubber)).setImageResource(pubber_image_id);

    }

    private void signInWithGoogle() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            Log.i(TAG,"New sign in request");
            CancellationSignal cancellationSignal = null;
            var googleIdOption= new GetSignInWithGoogleOption
                    .Builder(getString(R.string.FIREBASE_WEB_API_KEY))
                    .build();
            GetCredentialRequest signInRequest = new GetCredentialRequest.Builder()
                    .addCredentialOption(googleIdOption)
                    .build();
            credentialManager.getCredentialAsync(requireContext(), signInRequest, cancellationSignal, Executors.newSingleThreadExecutor(), new CredentialManagerCallback<>() {
                        @Override
                        public void onResult(GetCredentialResponse credentialResponse) {
                            viewModel.handleSignInResult(
                                    credentialResponse,
                                    (from, to) -> Navigation.findNavController(requireActivity(), R.id.main_navHostFragment_container).navigate(getNavDirections(from, to)),
                                    (snackbarType, uiText, logMes) -> showSnackbar(requireView(), snackbarType, (UIText.ResourceString) uiText, logMes));
                        }
                        @Override
                        public void onError(@NonNull GetCredentialException e) {
                            Log.e(TAG, "Sign in with google failed: " + e.getLocalizedMessage());
                        }
                    }
            );

        }else{
            Log.i(TAG,"Deprecated sign in request");
            GetSignInIntentRequest signInRequest = GetSignInIntentRequest.builder()
                    .setServerClientId(getString(R.string.FIREBASE_WEB_API_KEY))
                    .build();
            signInClient.getSignInIntent(signInRequest)
                    .addOnSuccessListener(this::launchSignIn)
                    .addOnFailureListener(e -> Log.e(TAG, "Google Sign-in failed", e));
        }
    }
    private void launchSignIn(PendingIntent pendingIntent) {
        try {
            IntentSenderRequest intentSenderRequest = new IntentSenderRequest.Builder(pendingIntent)
                    .build();
            signInLauncher.launch(intentSenderRequest);
        } catch (Exception e) {
            Log.e(TAG, "Couldn't start Sign In: " + e.getLocalizedMessage());
        }
    }

}
