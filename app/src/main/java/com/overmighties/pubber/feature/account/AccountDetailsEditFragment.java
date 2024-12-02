package com.overmighties.pubber.feature.account;

import static com.overmighties.pubber.app.Constants.ACCOUNT_DETAILS_EDIT_EDITTEXT_IDS;
import static com.overmighties.pubber.app.Constants.SIGN_UP_TEXT_FIELDS_IDS;
import static com.overmighties.pubber.app.exception.ErrorSnackbarUI.showSnackbar;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.textfield.TextInputEditText;
import com.overmighties.pubber.R;
import com.overmighties.pubber.app.designsystem.UIText;

import java.util.Arrays;

public class AccountDetailsEditFragment extends Fragment {
    public static final String TAG="AccountDetailsFragment";

    private AccountViewModel accountViewModel;
    private NavController navController;

    public AccountDetailsEditFragment(){
        super(R.layout.fragment_account_details_edit);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        accountViewModel = new ViewModelProvider(requireActivity())
                .get(AccountViewModel.class);
        navController= Navigation.findNavController(requireActivity(), R.id.main_navHostFragment_container);
        setUpData();
        setUpListeners();

    }

    private void setUpData(){
        ((EditText)requireView().findViewById(R.id.accountDetailsEdit_editText_nickname))
                .setText(accountViewModel.getUserData().getValue().getUsername());
        ((EditText)requireView().findViewById(R.id.accountDetailsEdit_editText_email))
                .setText(accountViewModel.getUserData().getValue().getEmail());
        ((EditText)requireView().findViewById(R.id.accountDetailsEdit_editText_sex))
                .setText(accountViewModel.getUserData().getValue().getSex());
    }

    private void setUpListeners(){

        requireView().findViewById(R.id.accountDetailsEdit_editText_sex).setOnClickListener(v->{
            int[] selectedItem = {0};
            String[] sex_choices = {
                    requireContext().getString(R.string.female),
                    requireContext().getString(R.string.male),
                    requireContext().getString(R.string.other)
            };
            int checked_pos = Arrays.asList(sex_choices)
                    .indexOf(((EditText)requireView().findViewById(R.id.accountDetailsEdit_editText_sex)).getText());
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext(), R.style.CustomDialog);
            builder.setTitle(null)
                    .setSingleChoiceItems(sex_choices, checked_pos, (dialog, which) -> {
                        selectedItem[0] = which;
                    })
                    .setNeutralButton(R.string.cancel, (dialog, which)->dialog.dismiss())
                    .setNegativeButton(R.string.accept, (dialog, which)->{
                        ((EditText)requireView().findViewById(R.id.accountDetailsEdit_editText_sex)).setText(sex_choices[selectedItem[0]]);
                    });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        });

        requireView().findViewById(R.id.accountDetailsEdit_image_back).setOnClickListener(v->{
            navController.popBackStack();
        });
        requireView().findViewById(R.id.accountDetailsEdit_button_cancel).setOnClickListener(v->{
            navController.popBackStack();
        });

        requireView().findViewById(R.id.accountDetailsEdit_button_accept).setOnClickListener(v->{
            accountViewModel.updateDisplayName(
                    ((EditText)requireView().findViewById(R.id.accountDetailsEdit_editText_nickname)).getText().toString(),
                    (errorType, uiText,logMes) -> showSnackbar(requireView(),errorType,(UIText.ResourceString)uiText,logMes),
                    ()->{});
            /*
            accountViewModel.updateEmail(
                    ((EditText)requireView().findViewById(R.id.accountDetailsEdit_editText_email)).getText().toString(),
                    (errorType, uiText, logMes) -> showSnackbar(holder.itemView, errorType, (UIText.ResourceString) uiText, logMes));
             */
            //TODO make sex update

        });

        /*
        requireView().findViewById(R.id.accountDetailsEdit_fragment).setOnClickListener(v->{
            for(var id: ACCOUNT_DETAILS_EDIT_EDITTEXT_IDS)
            {
                EditText EditText= requireView().findViewById(id);
                if (EditText.isFocused()) {
                    EditText.clearFocus();
                    InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(requireView().getWindowToken(), 0);
                    }
                }
            }

        });

        for(var id: ACCOUNT_DETAILS_EDIT_EDITTEXT_IDS) {

            EditText editText = requireView().findViewById(id);
            editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(final View v, final boolean hasFocus) {
                    if (hasFocus && editText.isEnabled() && editText.isFocusable()) {
                        editText.post(new Runnable() {
                            @Override
                            public void run() {
                                final InputMethodManager imm = (InputMethodManager)requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);

                                imm.showSoftInput(editText,InputMethodManager.SHOW_IMPLICIT);
                            }
                        });
                    }
                }
            });
            editText.setFocusable(true);
            editText.setFocusableInTouchMode(true);
        }

         */
    }
}
