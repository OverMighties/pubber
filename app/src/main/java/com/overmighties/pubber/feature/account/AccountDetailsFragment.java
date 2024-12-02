package com.overmighties.pubber.feature.account;

import static com.overmighties.pubber.app.exception.ErrorSnackbarUI.showSnackbar;
import static com.overmighties.pubber.app.navigation.PubberNavRoutes.getNavDirections;
import static com.overmighties.pubber.feature.account.AccountDetailsUIState.UNKNOWN;
import static com.overmighties.pubber.util.SizeCalculator.calculateTextViewWidth;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.imageview.ShapeableImageView;
import com.overmighties.pubber.R;
import com.overmighties.pubber.app.designsystem.UIText;
import com.yalantis.ucrop.UCrop;

import java.io.File;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class AccountDetailsFragment extends Fragment {
    public static final String TAG="AccountDetailsFragment";

    private AccountViewModel accountViewModel;
    private NavController navController;

    private ActivityResultLauncher<Intent> pickImageLauncher;
    private ActivityResultLauncher<Intent> cropImageLauncher;



    private  ShapeableImageView profileImage;

    public AccountDetailsFragment(){
        super(R.layout.fragment_account_details);
    }

    //TODO add to account time joined
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        accountViewModel = new ViewModelProvider(requireActivity())
                .get(AccountViewModel.class);
        //register ImageLauncher
        cropImageLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        Uri croppedImageUri = UCrop.getOutput(result.getData());
                        if (croppedImageUri != null) {
                            //TODO send it to sever
                            Glide.with(this)
                                    .load(croppedImageUri)
                                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                                    .skipMemoryCache(true)
                                    .transform(new RoundedCornersTransformation(250, 0))
                                    .into(profileImage);
                        }
                    } else if (result.getResultCode() == UCrop.RESULT_ERROR) {
                        Throwable cropError = UCrop.getError(result.getData());
                        Log.e(TAG, "Error cropping image", cropError);
                    }
                }
        );
        pickImageLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        Uri selectedImageUri = result.getData().getData();
                        if (selectedImageUri != null) {
                            launchImageCropper(selectedImageUri);
                        }
                    }
                }
        );
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController= Navigation.findNavController(requireActivity(),R.id.main_navHostFragment_container);
        accountViewModel.getCurrentUser();
        profileImage = requireView().findViewById(R.id.accountDetails_image_profile);
        setUpData();
        setUpListeners();

    }

    private void setUpData(){
        accountViewModel.getUserData().observe(getViewLifecycleOwner(), userData -> {

            Glide.with(this)
                    .load(userData.getPhotoUrl())
                    .placeholder(R.drawable.ic_account_teritary)
                    .fallback(R.drawable.ic_account_teritary)
                    .centerCrop()
                    .into(profileImage);
            ((TextView)requireView().findViewById(R.id.accountDetails_text_nickname)).setText(userData.getUsername());
            TextView emailTextView = requireView().findViewById(R.id.accountDetails_text_email);
            String email = userData.getEmail();
            emailTextView.setWidth(calculateTextViewWidth(email, emailTextView));
            if(email != UNKNOWN) {
                emailTextView.setText(email.substring(0, 4) + "*".repeat(email.length() - 4));
            } else {
                emailTextView.setText(email);
                requireView().findViewById(R.id.accountDetails_view_showEmailContainer).setVisibility(View.GONE);
                requireView().findViewById(R.id.accountDetails_button_showEmail).setVisibility(View.GONE);
            }
        });
    }

    private void setUpListeners(){

        requireView().findViewById(R.id.accountDetails_image_edit).setOnClickListener(v->{
            navController.navigate(AccountDetailsFragmentDirections.actionAccountDetailsFragmentToAccountDetailsEditFragment());
        });

        requireView().findViewById(R.id.accountDetails_view_showEmailContainer).setOnClickListener(v->{
            TextView emailTextView = requireView().findViewById(R.id.accountDetails_text_email);
            String email = accountViewModel.getUserData().getValue().getEmail();
            ImageView showImage = requireView().findViewById(R.id.accountDetails_button_showEmail);
            if(accountViewModel.getUserData().getValue().isEmailVisible()){
                emailTextView.setText(email.substring(0, 4) + "*".repeat(email.length() - 4));
                accountViewModel.getUserData().getValue().setEmailVisible(false);
                showImage.setImageResource(R.drawable.ic_visibility_off);
            } else {
                emailTextView.setText(email);
                accountViewModel.getUserData().getValue().setEmailVisible(true);
                showImage.setImageResource(R.drawable.ic_visible);
            }
        });

        requireView().findViewById(R.id.accountDetails_image_profile).setOnClickListener(v->{
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            pickImageLauncher.launch(intent);
        });

        requireView().findViewById(R.id.accountDetails_button_signOut).setOnClickListener(v-> accountViewModel.onSignOutClick(
                (from,to)-> navController.navigate(getNavDirections(from,to)),
                (errorType, uiText,logMes) -> showSnackbar(requireView(), errorType, (UIText.ResourceString)uiText,logMes)));

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
    }


    private void launchImageCropper(Uri sourceUri) {
        Uri destinationUri = Uri.fromFile(new File(requireContext().getCacheDir(), "cropped_profile_pictur.jpg"));
        UCrop.Options options = new UCrop.Options();
        options.setCircleDimmedLayer(true);
        options.setShowCropFrame(false);
        options.setShowCropGrid(false);
        options.setToolbarColor(ContextCompat.getColor(requireContext(), R.color.secondary));
        options.setStatusBarColor(ContextCompat.getColor(requireContext(), R.color.secondary_container));
        options.setActiveControlsWidgetColor(ContextCompat.getColor(requireContext(), R.color.primary));
        Intent cropIntent = UCrop.of(sourceUri, destinationUri)
                .withOptions(options)
                .withAspectRatio(1, 1)
                .withMaxResultSize(800, 600)
                .getIntent(requireContext());
        cropImageLauncher.launch(cropIntent);
    }


}
