package com.overmighties.pubber.feature.auth;


import static com.overmighties.pubber.app.Constants.SPLASH_FRAGMENT_BUTTONS_IDS;
import static com.overmighties.pubber.app.exception.ErrorSnackbarUI.showSnackbar;
import static com.overmighties.pubber.app.navigation.PubberNavRoutes.getNavDirections;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.IntentSenderRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
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
import com.overmighties.pubber.app.MainActivity;
import com.overmighties.pubber.app.ui.NavigationBar;
import com.overmighties.pubber.util.SettingsHandler;
import com.overmighties.pubber.util.UIText;

import java.util.Locale;
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
        NavigationBar.smoothHide(requireActivity().findViewById(R.id.bottom_nav_view));
        signInClient = Identity.getSignInClient(requireContext());
        credentialManager = CredentialManager.create(requireContext());
        navController=Navigation.findNavController(requireActivity(),R.id.nav_host_fragment);
        requireView().findViewById(R.id.button_sign_in_splash).setOnClickListener(v-> navController.navigate(R.id.action_splashFragment_to_signInFragment,null));
        requireView().findViewById(R.id.button_sing_up_splash).setOnClickListener(v-> navController.navigate(R.id.action_splashFragment_to_signUpFragment,null));

        //Sign in launcher calls firebase api from viewmodel
        signInLauncher= registerForActivityResult(
                new ActivityResultContracts.StartIntentSenderForResult(),
                result -> viewModel.handleSignInResult(
                        result.getData(), signInClient,
                        (from, to)-> Navigation.findNavController(requireActivity(),R.id.nav_host_fragment).navigate(getNavDirections(from,to)),
                        (errorType, uiText,logMes) -> showSnackbar(view,errorType,(UIText.ResourceString)uiText,logMes))
        );

        requireView().findViewById(R.id.IV_google_button).setOnClickListener(v-> signInWithGoogle());

        Integer google_button_id = (SettingsHandler.LanguageHelper.getLanguage(requireContext()) == SettingsHandler.LanguageHelper.LANGUAGE_POLISH)? R.drawable.ic_google_button_polish : R.drawable.ic_google_button_english;
        ((ImageView)requireView().findViewById(R.id.IV_google_button)).setImageResource(google_button_id);

        if (SettingsHandler.FirstTimeOpenHelper.getTimeOpened(requireContext()) ==  SettingsHandler.FirstTimeOpenHelper.FIRST_TIME){
            //open pupupwindow
            showPopUp();
        }

        new Handler(Looper.getMainLooper()).postDelayed(() -> viewModel.currentUserCheckOnStart((from, to)-> Navigation.findNavController(view)
                .navigate(getNavDirections(from,to))), SPLASH_DELAY);

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

    private void showPopUp(){
        // to do: change on settings clicked popupview theme and language
        View popUpView = LayoutInflater.from(requireActivity()).inflate(R.layout.first_time_settings_pop_up, null);
        if(requireActivity().findViewById(R.id.SplashFragment)==null)
            Log.e(TAG,"ERRRRR");
        final PopupWindow settingsPopUpWindow = new PopupWindow(popUpView,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT, true);
        if(settingsPopUpWindow==null)
            Log.e(TAG,"WWWWWWWWWWW");
        (requireActivity().findViewById(R.id.SplashFragment))
                .post(() -> settingsPopUpWindow.showAtLocation(requireActivity().findViewById(R.id.SplashFragment), Gravity.BOTTOM, 0, 0));
        preparePopUpWindow(popUpView, settingsPopUpWindow);
        setUpPopUpWindowListeners(popUpView, settingsPopUpWindow);
    }

    private void preparePopUpWindow(View popUpView, PopupWindow settingsPopUpWindow) {
        if (SettingsHandler.ThemeHelper.getSavedTheme(requireContext()) == SettingsHandler.ThemeHelper.THEME_DARK){
            ((RadioButton)popUpView.findViewById(R.id.radioButtonDark)).setChecked(true);
            ((ImageView)popUpView.findViewById(R.id.imageViewDark)).setColorFilter(ContextCompat.getColor(requireContext(), R.color.primary));
            ((TextView)popUpView.findViewById(R.id.textViewDark)).setTextColor(ContextCompat.getColor(requireContext(), R.color.primary));
        }
        else {
            ((RadioButton)popUpView.findViewById(R.id.radioButtonLight)).setChecked(true);
            ((ImageView)popUpView.findViewById(R.id.imageViewLight)).setColorFilter(ContextCompat.getColor(requireContext(), R.color.primary));
            ((TextView)popUpView.findViewById(R.id.textViewLight)).setTextColor(ContextCompat.getColor(requireContext(), R.color.primary));
        }
    }

    private void setUpPopUpWindowListeners(View popUpView, PopupWindow settingsPopUpWindow) {
        popUpView.findViewById(R.id.radioButtonLight).setOnTouchListener((v, event)->{
            if(!((RadioButton)popUpView.findViewById(R.id.radioButtonLight)).isChecked() && event.getAction() == MotionEvent.ACTION_UP ){
                ((RadioButton)popUpView.findViewById(R.id.radioButtonDark)).setChecked(false);
                SettingsHandler.ThemeHelper.saveTheme(requireContext(), SettingsHandler.ThemeHelper.THEME_LIGHT);
                changeFragmentsColor();
            }
            return false;
        });
        popUpView.findViewById(R.id.radioButtonDark).setOnTouchListener((v, event)->{
            if(!((RadioButton)popUpView.findViewById(R.id.radioButtonDark)).isChecked() && event.getAction() == MotionEvent.ACTION_UP ){
                ((RadioButton)popUpView.findViewById(R.id.radioButtonLight)).setChecked(false);
                SettingsHandler.ThemeHelper.saveTheme(requireContext(), SettingsHandler.ThemeHelper.THEME_DARK);
                changeFragmentsColor();
            }
            return false;
        });

        popUpView.findViewById(R.id.IV_first_pl).setOnTouchListener((v, event)->{
            SettingsHandler.LanguageHelper.saveLanguage(requireContext(), SettingsHandler.LanguageHelper.LANGUAGE_POLISH);
            changeFragmentsColor();
            return false;
        });

        popUpView.findViewById(R.id.IV_first_eng).setOnTouchListener((v, event)->{
            SettingsHandler.LanguageHelper.saveLanguage(requireContext(), SettingsHandler.LanguageHelper.LANGUAGE_ENGLISH);
            changeFragmentsColor();
            return false;
        });

        popUpView.findViewById(R.id.button_first_dismiss).setOnClickListener(v->{
            settingsPopUpWindow.dismiss();
            SettingsHandler.FirstTimeOpenHelper.setSecond(requireContext());
            Intent intent = new Intent(requireActivity(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            requireActivity().finish();
        });
    }

    private void changeFragmentsColor() {
        boolean dark_colors = SettingsHandler.ThemeHelper.getSavedTheme(requireContext()) == SettingsHandler.ThemeHelper.THEME_DARK;

        if (dark_colors) {
            requireView().findViewById(R.id.SplashFragment).setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.surface));
            requireActivity().getWindow().setStatusBarColor(ContextCompat.getColor(requireContext(), R.color.status_bar));
            requireActivity().getWindow().setNavigationBarColor(ContextCompat.getColor(requireContext(), R.color.surface_variant_secondary));
        }
        else {
            requireView().findViewById(R.id.SplashFragment).setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.opposing_surface));
            requireActivity().getWindow().setStatusBarColor(ContextCompat.getColor(requireContext(), R.color.opposing_status_bar));
            requireActivity().getWindow().setNavigationBarColor(ContextCompat.getColor(requireContext(), R.color.opposing_surface_variant_secondary));
        }





        for(var id: SPLASH_FRAGMENT_BUTTONS_IDS){
            Button button = requireView().findViewById(id);
            if (dark_colors){
                button.setBackground(getResources().getDrawable(R.drawable.button_shape_container));
                button.setTextColor(ContextCompat.getColor(requireContext(), R.color.on_secondary_container));
            }
            else{
                button.setBackground(getResources().getDrawable(R.drawable.button_shape_container_opposing));
                button.setTextColor(ContextCompat.getColor(requireContext(), R.color.opposing_on_secondary_container));
            }
        }

        if(SettingsHandler.LanguageHelper.getLanguage(requireContext()) == SettingsHandler.LanguageHelper.LANGUAGE_POLISH){
            ((Button)requireView().findViewById(R.id.button_sign_in_splash)).setText(getStringFromLocale(requireContext(), "label_email_sign_in", "pl-PL"));
            ((Button)requireView().findViewById(R.id.button_sign_in_splash)).setText(getStringFromLocale(requireContext(), "label_email_sign_up", "pl-PL"));
            if (dark_colors)
                ((ImageView)requireView().findViewById(R.id.IV_google_button)).setImageDrawable(getResources().getDrawable(R.drawable.ic_google_button_polish));
            else
                ((ImageView)requireView().findViewById(R.id.IV_google_button)).setImageDrawable(getResources().getDrawable(R.drawable.ic_google_button_polish_opposing));
        }
        else{
            ((Button)requireView().findViewById(R.id.button_sign_in_splash)).setText(getStringFromLocale(requireContext(), "label_email_sign_in", "en-US"));
            ((Button)requireView().findViewById(R.id.button_sign_in_splash)).setText(getStringFromLocale(requireContext(), "label_email_sign_up", "en-US"));
            if (dark_colors)
                ((ImageView)requireView().findViewById(R.id.IV_google_button)).setImageDrawable(getResources().getDrawable(R.drawable.ic_google_button_english));
            else
                ((ImageView)requireView().findViewById(R.id.IV_google_button)).setImageDrawable(getResources().getDrawable(R.drawable.ic_google_button_english_opposing));
        }
    }

    public String getStringFromLocale(Context context, String resourceName, String localeCode) {
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        Locale originalLocale = configuration.locale;

        Locale locale = new Locale(localeCode);
        Locale.setDefault(locale);
        configuration.setLocale(locale);
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());

        // Get the string resource
        int resourceId = resources.getIdentifier(resourceName, "string", context.getPackageName());
        String localizedString = resources.getString(resourceId);

        // Restore the original locale
        configuration.setLocale(originalLocale);
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());

        return localizedString;
    }

}
