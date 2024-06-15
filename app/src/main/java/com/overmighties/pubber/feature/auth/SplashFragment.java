package com.overmighties.pubber.feature.auth;


import static com.overmighties.pubber.app.navigation.PubberNavRoutes.getNavDirections;
import static com.overmighties.pubber.util.SnackbarUI.showSnackbar;

import android.app.PendingIntent;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;

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
import com.overmighties.pubber.app.ui.NavigationBar;
import com.overmighties.pubber.util.UIText;

import java.util.concurrent.Executors;

public class SplashFragment extends Fragment {
    private static final Integer SPLASH_DELAY=1000;
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
        NavigationBar.smoothHide(getActivity().findViewById(R.id.bottom_nav_view));
        signInClient = Identity.getSignInClient(requireContext());
        credentialManager = CredentialManager.create(requireContext());
        navController=Navigation.findNavController(requireActivity(),R.id.nav_host_fragment);
        requireView().findViewById(R.id.button_sign_in_splash).setOnClickListener(v->{
            navController.navigate(R.id.action_splashFragment_to_signInFragment,null);
        });
        requireView().findViewById(R.id.button_sing_up_splash).setOnClickListener(v->{
            navController.navigate(R.id.action_splashFragment_to_signUpFragment,null);
        });

        //Sign in launcher calls firebase api from viewmodel
        signInLauncher= registerForActivityResult(
                new ActivityResultContracts.StartIntentSenderForResult(),
                result -> viewModel.handleSignInResult(
                        result.getData(), signInClient,
                        (from, to)-> Navigation.findNavController(requireActivity(),R.id.nav_host_fragment).navigate(getNavDirections(from,to)),
                        (snackbarType, uiText,logMes) -> showSnackbar(view,snackbarType,(UIText.ResourceString)uiText,logMes))
        );
        requireView().findViewById(R.id.button_sign_in_google_splash).setOnClickListener(v->{
            signInWithGoogle();
        });
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            viewModel.onAppStart((from, to)-> Navigation.findNavController(view)
                    .navigate(getNavDirections(from,to)));
        }, SPLASH_DELAY);

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
                                    (from, to) -> Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigate(getNavDirections(from, to)),
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
