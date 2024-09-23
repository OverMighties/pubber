package com.overmighties.pubber.feature.account;

import static com.overmighties.pubber.app.exception.ErrorSnackbarUI.showSnackbar;
import static com.overmighties.pubber.app.navigation.PubberNavRoutes.getNavDirections;

import android.app.AlertDialog;
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
        requireActivity().findViewById(R.id.main_topAppBarLayout_back).setVisibility(View.VISIBLE);
        accountDetailsRecyclerView = requireView().findViewById(R.id.accountDetails_recyclerView_info);
        navController= Navigation.findNavController(requireActivity(),R.id.main_navHostFragment_container);
        requireView().findViewById(R.id.accountDetails_button_signOut).setOnClickListener(v-> accountViewModel.onSignOutClick(
                (from,to)-> navController.navigate(getNavDirections(from,to)),
                (errorType, uiText,logMes) -> showSnackbar(view,errorType,(UIText.ResourceString)uiText,logMes)));
        requireView().findViewById(R.id.accountDetails_button_delete).setOnClickListener(v->{
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext(), R.style.CustomDialog);
            builder.setNeutralButton(getString(R.string.cancel), null);
            builder.setPositiveButton(getString(R.string.yes), (((dialog, which) -> {
                //TODO delete account function
                Log.i(TAG, "Account Deleted");
            })));
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        });
        profileImage=requireView().findViewById(R.id.accountDetails_image_profile);
        accountDetailsAdapter = new AccountDetailsAdapter(accountViewModel.getUserData().getValue(), accountViewModel);
        accountDetailsRecyclerView.setAdapter(accountDetailsAdapter);
        accountViewModel.getUserData().observe(getViewLifecycleOwner(), userData -> {
            Glide.with(this)
                    .load(userData.getPhotoUrl())
                    .placeholder(R.drawable.ic_account_primary_dark)
                    .fallback(R.drawable.ic_account_primary_dark)
                    .centerCrop()
                    .into(profileImage);
            accountDetailsAdapter = new AccountDetailsAdapter(userData, accountViewModel);
            accountDetailsRecyclerView.setAdapter(accountDetailsAdapter);
        });
        accountViewModel.getCurrentUser();


    }
}
