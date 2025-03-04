package com.overmighties.pubber.feature.auth;


import static com.overmighties.pubber.app.Constants.SIGN_IN_INPUT_LAYOUTS_IDS;
import static com.overmighties.pubber.app.Constants.SIGN_IN_TEXT_FIELDS_IDS;
import static com.overmighties.pubber.app.exception.ErrorSnackbarUI.showSnackbar;
import static com.overmighties.pubber.app.navigation.PubberNavRoutes.SIGN_IN_FRAGMENT;
import static com.overmighties.pubber.app.navigation.PubberNavRoutes.SIGN_UP_FRAGMENT;
import static com.overmighties.pubber.app.navigation.PubberNavRoutes.getNavDirections;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.overmighties.pubber.R;
import com.overmighties.pubber.app.exception.ErrorSigningUIHandler;
import com.overmighties.pubber.app.exception.ErrorSigningUITypes;
import com.overmighties.pubber.app.exception.ErrorSnackbarUI;
import com.overmighties.pubber.app.designsystem.UIText;
import com.overmighties.pubber.app.settings.SettingsHandler;


public class SignInFragment extends Fragment {
    public static final String TAG="SignInFragment";
    private SignInViewModel viewModel;
    private NavController navController;
    public SignInFragment(){
        super(R.layout.fragment_sign_in);
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this,
                ViewModelProvider.Factory.from(SignInViewModel.initializer))
                .get(SignInViewModel.class);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController=Navigation.findNavController(requireActivity(),R.id.main_navHostFragment_container);
        requireView().findViewById(R.id.signIn_button_sign).setOnClickListener(v->{
            EditText email=requireView().findViewById(R.id.signIn_editText_email);
            viewModel.updateEmail(email.getText().toString());
            EditText password=requireView().findViewById(R.id.signIn_editText_password);
            viewModel.updatePassword(password.getText().toString());

            viewModel.onSignInClick(
                    (from,to)->  navController.navigate(getNavDirections(from,to)),
                    (errorType, uiText, logMes) -> {
                        String errMess= getErrorMess(errorType, (UIText.ResourceString)uiText,logMes);
                        if(errorType== ErrorSigningUITypes.FIREBASE_AUTH_EMAIL_ERROR)
                            underlineEmail(errMess);
                        else if(errorType== ErrorSigningUITypes.FIREBASE_AUTH_PASSWORD_ERROR )
                            underlinePassword(errMess);
                        else
                            showSnackbar(view, ErrorSnackbarUI.ErrorTypes.FIREBASE_AUTH,(UIText.ResourceString)uiText,logMes);
                    });
        });

        requireView().findViewById(R.id.signIn_button_back).setOnClickListener(v-> navController.popBackStack());

        requireView().findViewById(R.id.signIn_botton_signUp).setOnClickListener(v-> navController.navigate(getNavDirections(SIGN_IN_FRAGMENT,SIGN_UP_FRAGMENT)));

        requireView().findViewById(R.id.signIn_fragment).setOnClickListener(v->{
            for(var id: SIGN_IN_TEXT_FIELDS_IDS)
            {
                TextInputEditText EditText= requireView().findViewById(id);
                if (EditText.isFocused()) {
                    EditText.clearFocus();
                    InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(requireView().getWindowToken(), 0);
                    }
                }
            }

        });
        setUpEditTexts();
        //set pubber image
        int pubber_image_id = (SettingsHandler.ThemeHelper.getSavedTheme(requireContext()).equals(SettingsHandler.ThemeHelper.THEME_LIGHT))? R.drawable.ic_drink_pubber : R.drawable.ic_beer_pubber;
        ((ImageView)requireView().findViewById(R.id.signUp_image_pubberIcon)).setImageResource(pubber_image_id);

    }
    private void setUpEditTexts(){
        for (var edit_field: SIGN_IN_TEXT_FIELDS_IDS){
            //reset error message if there is any
            TextInputEditText editText= requireView().findViewById(edit_field);
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    for(var id: SIGN_IN_INPUT_LAYOUTS_IDS){
                        //reset error text
                        TextInputLayout InputLayout = requireView().findViewById(id);
                        InputLayout.setError(null);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {}
            });
        }
    }
    private void underlineEmail(String errMess) {
        ((TextInputLayout)requireView().findViewById(R.id.signIn_inputLayout_email)).setErrorEnabled(true);
        ((TextInputLayout)requireView().findViewById(R.id.signIn_inputLayout_email)).setError(errMess);
    }
    private void underlinePassword(String errMess){
        ((TextInputLayout)requireView().findViewById(R.id.signIn_inputLayout_password)).setErrorEnabled(true);
        ((TextInputLayout)requireView().findViewById(R.id.signIn_inputLayout_password)).setError(errMess);

    }
    private String getErrorMess(ErrorSigningUITypes type, @Nullable UIText.ResourceString authRes, String logMes) {
        return ErrorSigningUIHandler.getErrorMessage(requireContext(), type, authRes, logMes);
    }
}
