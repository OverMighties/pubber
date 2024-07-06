package com.overmighties.pubber.feature.account;

import static com.overmighties.pubber.app.navigation.PubberNavRoutes.getNavDirections;
import static com.overmighties.pubber.util.SnackbarUI.showSnackbar;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;
import com.overmighties.pubber.R;
import com.overmighties.pubber.app.ui.NavigationBar;
import com.overmighties.pubber.util.UIText;

public class AccountDetailsFragment extends Fragment {
    public static final String TAG="AccountDetailsFragment";
    private AccountViewModel viewModel;
    private NavController navController;
    private RecyclerView accountDetailsRecyclerView;
    private  ShapeableImageView profileImage;
    private AccountDetailsAdapter accountDetailsAdapter;
    public AccountDetailsFragment(){
        super(R.layout.fragment_account_details);
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG,"on create");
        viewModel = new ViewModelProvider(requireActivity(),
                ViewModelProvider.Factory.from(AccountViewModel.initializer))
                .get(AccountViewModel.class);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        accountDetailsRecyclerView = (RecyclerView) requireView().findViewById(R.id.account_details_recycler_view);
        navController= Navigation.findNavController(requireActivity(),R.id.nav_host_fragment);
        requireView().findViewById(R.id.sign_out_button).setOnClickListener(v-> {
            viewModel.onSignOutClick(
                    (from,to)-> navController.navigate(getNavDirections(from,to)),
                    (snackbarType, uiText,logMes) -> showSnackbar(view,snackbarType,(UIText.ResourceString)uiText,logMes));
        });
        profileImage=requireView().findViewById(R.id.profile_image);
        accountDetailsAdapter = new AccountDetailsAdapter(viewModel.getUserData().getValue());
        accountDetailsRecyclerView.setAdapter(accountDetailsAdapter);
        viewModel.getUserData().observe(getViewLifecycleOwner(), userData -> {
            Glide.with(this)
                    .load(userData.getPhotoUrl())
                    .placeholder(R.drawable.account_circle_24px)
                    .fallback(R.drawable.account_circle_24px)
                    .centerCrop()
                    .into(profileImage);
            accountDetailsAdapter = new AccountDetailsAdapter(userData);
            accountDetailsRecyclerView.setAdapter(accountDetailsAdapter);
        });
        viewModel.getCurrentUser();

    }
}
