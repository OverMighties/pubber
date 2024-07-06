package com.overmighties.pubber.feature.auth;


import static com.overmighties.pubber.app.Constants.SIGN_IN_TEXTFIELDS_IDS;
import static com.overmighties.pubber.app.Constants.SIGN_UP_TEXTFIELDS_IDS;
import static com.overmighties.pubber.app.navigation.PubberNavRoutes.SIGN_IN_FRAGMENT;
import static com.overmighties.pubber.app.navigation.PubberNavRoutes.SIGN_UP_FRAGMENT;
import static com.overmighties.pubber.app.navigation.PubberNavRoutes.getNavDirections;
import static com.overmighties.pubber.util.SnackbarUI.showSnackbar;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.textfield.TextInputEditText;
import com.overmighties.pubber.R;
import com.overmighties.pubber.util.UIText;


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
        navController=Navigation.findNavController(requireActivity(),R.id.nav_host_fragment);
        requireView().findViewById(R.id.emailSignInButton).setOnClickListener(v->{
            EditText email=requireView().findViewById(R.id.edit_field_email_sing_in);
            viewModel.updateEmail(email.getText().toString());
            EditText password=requireView().findViewById(R.id.edit_field_password_sing_in);
            viewModel.updatePassword(password.getText().toString());
            Log.d(TAG, viewModel.getEmail().getValue());
            Log.d(TAG, viewModel.getPassword().getValue());

            viewModel.onSignInClick(
                    (from,to)->  navController.navigate(getNavDirections(from,to)),
                    (snackbarType, uiText, logMes) -> showSnackbar(view,snackbarType,(UIText.ResourceString)uiText,logMes));
        });
        requireView().findViewById(R.id.sign_up_5).setOnClickListener(v->{
            navController.navigate(getNavDirections(SIGN_IN_FRAGMENT,SIGN_UP_FRAGMENT));
        });

        requireView().findViewById(R.id.SignInFragment).setOnClickListener(v->{
            for(var id:SIGN_IN_TEXTFIELDS_IDS)
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

    }
}
