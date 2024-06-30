package com.overmighties.pubber.feature.auth;


import static com.overmighties.pubber.app.Constants.DIGIT_PATTERN;
import static com.overmighties.pubber.app.Constants.LOWERCASE_PATTERN;
import static com.overmighties.pubber.app.Constants.SIGN_UP_TEXTFIELDS_IDS;import static com.overmighties.pubber.app.Constants.SPECIAL_CHAR_PATTERN;
import static com.overmighties.pubber.app.Constants.UPPERCASE_PATTERN;
import static com.overmighties.pubber.app.navigation.PubberNavRoutes.getNavDirections;
import static com.overmighties.pubber.util.SnackbarUI.showSnackbar;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.overmighties.pubber.R;
import com.overmighties.pubber.util.UIText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;


public class SignUpFragment  extends Fragment {
    public static final String TAG="SignUpFragment";
    private SignUpViewModel viewModel;
    private NavController navController;
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
                    (snackbarType, uiText,logMes) -> showSnackbar(view,snackbarType,(UIText.ResourceString)uiText,logMes));
        });

        requireView().findViewById(R.id.SignUpFragment).setOnClickListener(v->{
            for(var edit_field:SIGN_UP_TEXTFIELDS_IDS)
            {
                TextInputEditText textInputEditText= (TextInputEditText) requireView().findViewById(edit_field);
                if (textInputEditText.isFocused()) {
                    textInputEditText.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                    }
                }
            }
        });

        ProgressBar progressBar = (ProgressBar) requireView().findViewById(R.id.progressBar2);
        ((EditText)requireView().findViewById(R.id.edit_filed_password_sing_up)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                progressBar.setVisibility(View.VISIBLE);
                requireView().findViewById(R.id.progressTV).setVisibility(View.VISIBLE);

                String password = String.valueOf(s);

                boolean len_6 = (password.length()>=6) ? true : false;
                boolean len_12 = (password.length()>=12) ? true : false;
                boolean hasLowercase = LOWERCASE_PATTERN.matcher(password).find();
                boolean hasUppercase = UPPERCASE_PATTERN.matcher(password).find();
                boolean hasDigit = DIGIT_PATTERN.matcher(password).find();
                boolean hasSpecialChar = SPECIAL_CHAR_PATTERN.matcher(password).find();

                int strength = 0;
                if (len_6) {
                    strength ++ ;
                    if (len_12)
                        strength++;
                    if (hasLowercase && hasUppercase)
                        strength++;
                    if (hasDigit)
                        strength++;
                    if (hasSpecialChar)
                        strength++;
                }

                if (strength <= 2)
                {
                    //weak
                    progressBar.setProgress(25);
                    ColorStateList progressTintList = ColorStateList.valueOf(getContext().getResources().getColor(R.color.red_bright));
                    progressBar.setProgressTintList(progressTintList);
                    ColorStateList progressBackgroundTintList = ColorStateList.valueOf(getContext().getResources().getColor(R.color.red_dark));
                    progressBar.setProgressBackgroundTintList(progressBackgroundTintList);
                    ((TextView)requireView().findViewById(R.id.progressTV)).setTextColor(getContext().getResources().getColor(R.color.red_bright));
                    ((TextView)requireView().findViewById(R.id.progressTV)).setText("Słabe");
                }
                else if (strength <=3)
                {
                    //fair
                    progressBar.setProgress(50);
                    ColorStateList progressTintList = ColorStateList.valueOf(getContext().getResources().getColor(R.color.yellow_bright));
                    progressBar.setProgressTintList(progressTintList);
                    ColorStateList progressBackgroundTintList = ColorStateList.valueOf(getContext().getResources().getColor(R.color.yellow_dark));
                    progressBar.setProgressBackgroundTintList(progressBackgroundTintList);
                    ((TextView)requireView().findViewById(R.id.progressTV)).setTextColor(getContext().getResources().getColor(R.color.yellow_bright));
                    ((TextView)requireView().findViewById(R.id.progressTV)).setText("Średnie");
                }
                else
                {
                    //strong
                    progressBar.setProgress(100);
                    ColorStateList progressTintList = ColorStateList.valueOf(getContext().getResources().getColor(R.color.green_bright));
                    progressBar.setProgressTintList(progressTintList);
                    ColorStateList progressBackgroundTintList = ColorStateList.valueOf(getContext().getResources().getColor(R.color.green_dark));
                    progressBar.setProgressBackgroundTintList(progressBackgroundTintList);
                    ((TextView)requireView().findViewById(R.id.progressTV)).setTextColor(getContext().getResources().getColor(R.color.green_bright));
                    ((TextView)requireView().findViewById(R.id.progressTV)).setText("Mocne");
                }

            }

            @Override
            public void afterTextChanged(Editable s) {}
        });


    }
}
