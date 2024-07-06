package com.overmighties.pubber.feature.auth;


import static com.overmighties.pubber.app.Constants.DIGIT_PATTERN;
import static com.overmighties.pubber.app.Constants.LOWERCASE_PATTERN;
import static com.overmighties.pubber.app.Constants.SIGN_UP_INPUTLAYOUTS_IDS;
import static com.overmighties.pubber.app.Constants.SIGN_UP_TEXTFIELDS_IDS;import static com.overmighties.pubber.app.Constants.SPECIAL_CHAR_PATTERN;
import static com.overmighties.pubber.app.Constants.UPPERCASE_PATTERN;
import static com.overmighties.pubber.app.navigation.PubberNavRoutes.getNavDirections;

import android.content.Context;
import android.content.res.ColorStateList;
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
import com.overmighties.pubber.app.exception.ErrorHandler;
import com.overmighties.pubber.util.UIText;


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
        ((TextInputLayout)requireView().findViewById(R.id.textInputLayoutConfirmPassword)).setError("Błąd");
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
                        handleError(errorType, (UIText.ResourceString)uiText,logMes);
                    }
            );
        });

        requireView().findViewById(R.id.SignUpFragment).setOnClickListener(v->{
            for(var id:SIGN_UP_TEXTFIELDS_IDS)
            {
                TextInputEditText EditText= (TextInputEditText) requireView().findViewById(id);
                if (EditText.isFocused()) {
                    EditText.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                    }
                }
            }

        });

        for (var edit_field:SIGN_UP_TEXTFIELDS_IDS){
            //reset error message if there is any
            TextInputEditText EditText= (TextInputEditText) requireView().findViewById(edit_field);
            EditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    for(var id:SIGN_UP_INPUTLAYOUTS_IDS){
                        //reset error text
                        TextInputLayout InputLayout = (TextInputLayout) requireView().findViewById(id);
                        InputLayout.setError(null);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {}
            });
        }

        ProgressBar progressBar = (ProgressBar) requireView().findViewById(R.id.progressBarPassword);
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

    private void handleError(ErrorHandler.ErrorTypes type, @Nullable UIText.ResourceString authRes, String logMes) {
        String error_message = ErrorHandler.getErrorMessage(getContext(), type, authRes, logMes);
        TextInputLayout textInputLayout = ((TextInputLayout)requireView().findViewById(R.id.textInputLayoutConfirmPassword));
        textInputLayout.setError(error_message);
        Log.d(TAG, type.toString());
        if (authRes != null)
        {
            Log.d(TAG, authRes.toString());
        Log.d(TAG, String.valueOf(authRes.getResId()));}
       // int messageResId = authRes==null?viewModel.getMessageResId(type):authRes.getResId();
      //  if(type== SnackbarUI.SnackbarTypes.BASIC_AUTH_ERROR || type== SnackbarUI.SnackbarTypes.FIREBASE_AUTH_ERROR)
      //      logMes+=getContext().getText(R.string.try_again);
      //  if(messageResId==viewModel.NONE_RES)
      //      textInputLayout.setError(logMes);
      //  else
        //    textInputLayout.setError(getContext().getText(messageResId)+logMes);
    }
}
