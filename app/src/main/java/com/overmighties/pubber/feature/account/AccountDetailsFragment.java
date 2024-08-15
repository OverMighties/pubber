package com.overmighties.pubber.feature.account;

import static com.overmighties.pubber.app.exception.ErrorSnackbarUI.showSnackbar;
import static com.overmighties.pubber.app.navigation.PubberNavRoutes.getNavDirections;

import android.os.Bundle;
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
import com.overmighties.pubber.app.designsystem.UIText;

public class AccountDetailsFragment extends Fragment {
    public static final String TAG="AccountDetailsFragment";
    private AccountViewModel accountViewModel;
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
        accountViewModel = new ViewModelProvider(requireActivity())
                .get(AccountViewModel.class);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        accountDetailsRecyclerView = requireView().findViewById(R.id.account_details_recycler_view);
        navController= Navigation.findNavController(requireActivity(),R.id.nav_host_fragment);
        requireView().findViewById(R.id.sign_out_button).setOnClickListener(v-> accountViewModel.onSignOutClick(
                (from,to)-> navController.navigate(getNavDirections(from,to)),
                (errorType, uiText,logMes) -> showSnackbar(view,errorType,(UIText.ResourceString)uiText,logMes)));
        profileImage=requireView().findViewById(R.id.profile_image);
        accountDetailsAdapter = new AccountDetailsAdapter(accountViewModel.getUserData().getValue());
        accountDetailsRecyclerView.setAdapter(accountDetailsAdapter);
        accountViewModel.getUserData().observe(getViewLifecycleOwner(), userData -> {
            Glide.with(this)
                    .load(userData.getPhotoUrl())
                    .placeholder(R.drawable.account_circle_24px)
                    .fallback(R.drawable.account_circle_24px)
                    .centerCrop()
                    .into(profileImage);
            accountDetailsAdapter = new AccountDetailsAdapter(userData);
            accountDetailsRecyclerView.setAdapter(accountDetailsAdapter);
        });
        accountViewModel.getCurrentUser();

    }
}
