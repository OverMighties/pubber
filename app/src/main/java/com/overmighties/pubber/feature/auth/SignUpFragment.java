package com.overmighties.pubber.feature.auth;


import static com.overmighties.pubber.app.Constants.DIGIT_PATTERN;
import static com.overmighties.pubber.app.Constants.LOWERCASE_PATTERN;
import static com.overmighties.pubber.app.Constants.SIGN_UP_INPUT_LAYOUTS_IDS;
import static com.overmighties.pubber.app.Constants.SIGN_UP_TEXT_FIELDS_IDS;import static com.overmighties.pubber.app.Constants.SPECIAL_CHAR_PATTERN;
import static com.overmighties.pubber.app.Constants.UPPERCASE_PATTERN;
import static com.overmighties.pubber.app.navigation.PubberNavRoutes.getNavDirections;

import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.overmighties.pubber.R;
import com.overmighties.pubber.app.exception.ErrorSigningUIHandler;
import com.overmighties.pubber.app.exception.ErrorSigningUITypes;
import com.overmighties.pubber.util.UIText;


public class SignUpFragment  extends Fragment {
    public static final String TAG="SignUpFragment";
    private SignUpViewModel viewModel;
    private NavController navController;
    private ProgressBar progressBarPassword;
    public SignUpFragment(){
        super(R.layout.fragment_sign_up);
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this,
                ViewModelProvider.Factory.from(SignUpViewModel.initializer))
                .get(SignUpViewModel.class);

    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        ((TextInputLayout)requireView().findViewById(R.id.textInputLayoutConfirmPassword)).setError("Błąd");
        navController=Navigation.findNavController(requireActivity(),R.id.nav_host_fragment);
        requireView().findViewById(R.id.button_create_account_sign_up).setOnClickListener(v->{
            EditText email=requireView().findViewById(R.id.edit_field_email_sing_up);
            viewModel.updateEmail(email.getText().toString());
            EditText password=requireView().findViewById(R.id.edit_filed_password_sing_up);
            viewModel.updatePassword(password.getText().toString());
            EditText confirmPassword=requireView().findViewById(R.id.edit_field_confirm_password_sign_up);
            viewModel.updateConfirmPassword(confirmPassword.getText().toString());
            viewModel.onSignUpClick(
                    (from,to)-> navController.navigate(getNavDirections(from,to)),
                    (errorType, uiText,logMes) -> {
                        String errMess= ErrorSigningUIHandler.getErrorMessage(requireContext(),errorType,
                                (UIText.ResourceString)uiText,logMes);
                        if(errorType== ErrorSigningUITypes.FIREBASE_AUTH_EMAIL_ERROR){
                            underlineEmail(errMess);
                        }
                        else if(errorType== ErrorSigningUITypes.FIREBASE_AUTH_PASSWORD_ERROR ||
                                errorType== ErrorSigningUITypes.AUTH_NOT_SAME_PASSWORDS){
                            underlinePassword();
                            underlineConfirmPassword(errMess);
                        }
                    }
            );
        });
        progressBarPassword = requireView().findViewById(R.id.progressBarPassword);
        setUpTextFields();
        ((EditText)requireView().findViewById(R.id.edit_filed_password_sing_up)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                progressBarPassword.setVisibility(View.VISIBLE);
                requireView().findViewById(R.id.progressTV).setVisibility(View.VISIBLE);
                String password = String.valueOf(s);
//                ((TextInputLayout)requireView().findViewById(R.id.textInputLayoutPassword)).setErrorEnabled(false);
                int strength=getPasswordStrength(password);
                if (s.length()==0)
                    setGonePasswordUnderlineView();
                else if (strength <= 2)
                    setWeakPasswordUnderlineView();
                else if (strength <=3)
                    setAveragePasswordUnderlineView();
                else
                    setStrongPasswordUnderlineView();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



    }
    private void setUpTextFields(){
        requireView().findViewById(R.id.SignUpFragment).setOnClickListener(v->{
            for(var id: SIGN_UP_TEXT_FIELDS_IDS)
            {
                TextInputEditText EditText= (TextInputEditText) requireView().findViewById(id);
                if (EditText.isFocused()) {
                    EditText.clearFocus();
                    InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(requireView().getWindowToken(), 0);
                    }
                }
            }

        });
        for (var edit_field: SIGN_UP_TEXT_FIELDS_IDS){
            //reset error message if there is any
            TextInputEditText editText= (TextInputEditText) requireView().findViewById(edit_field);
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    for(var id: SIGN_UP_INPUT_LAYOUTS_IDS){
                        //reset error text
                        TextInputLayout InputLayout = (TextInputLayout) requireView().findViewById(id);
                        InputLayout.setError(null);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {}
            });
        }
    }
    private void setGonePasswordUnderlineView(){
        progressBarPassword.setVisibility(View.GONE);
        requireView().findViewById(R.id.progressTV).setVisibility(View.GONE);
    }
    private void setWeakPasswordUnderlineView(){
        //weak
        progressBarPassword.setProgress(25);
        ColorStateList progressTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(),R.color.red_bright));
        progressBarPassword.setProgressTintList(progressTintList);
        ColorStateList progressBackgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(),R.color.red_dark));
        progressBarPassword.setProgressBackgroundTintList(progressBackgroundTintList);
        ((TextView)requireView().findViewById(R.id.progressTV)).setTextColor(ContextCompat.getColor(requireContext(),R.color.red_bright));
        ((TextView)requireView().findViewById(R.id.progressTV)).setText(getText(R.string.weak_password));
    }
    private void setAveragePasswordUnderlineView(){
        //fair
        progressBarPassword.setProgress(50);
        ColorStateList progressTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.yellow_bright));
        progressBarPassword.setProgressTintList(progressTintList);
        ColorStateList progressBackgroundTintList =  ColorStateList.valueOf(ContextCompat.getColor(requireContext(),R.color.yellow_dark));
        progressBarPassword.setProgressBackgroundTintList(progressBackgroundTintList);
        ((TextView)requireView().findViewById(R.id.progressTV)).setTextColor(ContextCompat.getColor(requireContext(),R.color.yellow_bright));
        ((TextView)requireView().findViewById(R.id.progressTV)).setText(getText(R.string.average_password));
    }
    private void setStrongPasswordUnderlineView(){
        //strong
        progressBarPassword.setProgress(100);
        ColorStateList progressTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(),R.color.green_bright));
        progressBarPassword.setProgressTintList(progressTintList);
        ColorStateList progressBackgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(),R.color.green_dark));
        progressBarPassword.setProgressBackgroundTintList(progressBackgroundTintList);
        ((TextView)requireView().findViewById(R.id.progressTV)).setTextColor(ContextCompat.getColor(requireContext(),R.color.green_bright));
        ((TextView)requireView().findViewById(R.id.progressTV)).setText(getText(R.string.strong_password));
    }
    private int getPasswordStrength(String password){
        boolean hasLowercase = LOWERCASE_PATTERN.matcher(password).find();
        boolean hasUppercase = UPPERCASE_PATTERN.matcher(password).find();
        boolean hasDigit = DIGIT_PATTERN.matcher(password).find();
        boolean hasSpecialChar = SPECIAL_CHAR_PATTERN.matcher(password).find();

        int strength = 0;
        if (password.length() >= 6) {
            strength++;
            if (password.length() >= 12)
                strength++;
            if (hasLowercase && hasUppercase)
                strength++;
            if (hasDigit)
                strength++;
            if (hasSpecialChar)
                strength++;
        }
        return strength;
    }
    private void underlineEmail(String errMess) {
        ((TextInputLayout)requireView().findViewById(R.id.textInputLayoutEmailSignUp)).setErrorEnabled(true);
        ((TextInputLayout)requireView().findViewById(R.id.textInputLayoutEmailSignUp)).setError(errMess);
    }
    private void underlinePassword(){
        ((TextInputLayout)requireView().findViewById(R.id.textInputLayoutPasswordSignUp)).setErrorEnabled(true);

    }
    private void underlineConfirmPassword(String errMess) {
        ((TextInputLayout) requireView().findViewById(R.id.textInputLayoutConfirmPasswordSignUp)).setErrorEnabled(true);
        ((TextInputLayout)requireView().findViewById(R.id.textInputLayoutConfirmPasswordSignUp)).setError(errMess);
    }

}
