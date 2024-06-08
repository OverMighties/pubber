package com.overmighties.pubber.feature.auth;


import static com.overmighties.pubber.app.navigation.PubberNavRoutes.SIGN_IN_FRAGMENT;
import static com.overmighties.pubber.app.navigation.PubberNavRoutes.SIGN_UP_FRAGMENT;
import static com.overmighties.pubber.app.navigation.PubberNavRoutes.getNavDirections;
import static com.overmighties.pubber.util.SnackbarUI.showSnackbar;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

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
            EditText email=requireView().findViewById(R.id.fieldEmail);
            viewModel.updateEmail(email.getText().toString());
            EditText password=requireView().findViewById(R.id.fieldPassword);
            viewModel.updatePassword(password.getText().toString());
            viewModel.onSignInClick(
                    (from,to)->  navController.navigate(getNavDirections(from,to)),
                    (snackbarType, uiText, logMes) -> showSnackbar(view,snackbarType,(UIText.ResourceString)uiText,logMes));
        });
        requireView().findViewById(R.id.sign_up_5).setOnClickListener(v->{
            navController.navigate(getNavDirections(SIGN_IN_FRAGMENT,SIGN_UP_FRAGMENT));
        });
    }
}
