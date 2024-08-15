package com.overmighties.pubber.feature.auth;


import static com.overmighties.pubber.app.exception.ErrorSnackbarUI.showSnackbar;
import static com.overmighties.pubber.app.navigation.PubberNavRoutes.NEW_USER_DETAILS_FRAGMENT;
import static com.overmighties.pubber.app.navigation.PubberNavRoutes.PLACE_CHOICE_FRAGMENT;
import static com.overmighties.pubber.app.navigation.PubberNavRoutes.getNavDirections;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;

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
import com.overmighties.pubber.app.exception.ErrorSnackbarUI;
import com.overmighties.pubber.feature.account.AccountViewModel;
import com.overmighties.pubber.app.designsystem.UIText;

import java.util.regex.Pattern;

public class NewUserDetailsFragment extends Fragment {
    public static final String TAG="NewUserDetailsFragment";
    private AccountViewModel accountViewModel;
    private TextInputEditText editText;
    private NavController navController;
    private static final Pattern DISPLAY_NAME_PATTERN = Pattern.compile("^\\p{L}[\\p{L}0-9_$#!]{3,19}$", Pattern.CASE_INSENSITIVE);
    public NewUserDetailsFragment(){
        super(R.layout.fragment_new_user_details);
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        accountViewModel = new ViewModelProvider(requireActivity())
                .get(AccountViewModel.class);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        navController= Navigation.findNavController(requireActivity(),R.id.nav_host_fragment);
        editText=requireView().findViewById(R.id.edit_field_display_name);
        setUpSelectSex();
        setUpDisplayName();
        requireView().findViewById(R.id.button_confirm_display_name).setOnClickListener(v -> {
            String displayName=editText.getText().toString();
            if(checkDisplayNameValidity(displayName)){
                accountViewModel.updateDisplayName(displayName,
                    (errorType, uiText,logMes) -> showSnackbar(view, ErrorSnackbarUI.ErrorTypes.USER_ACCOUNT,(UIText.ResourceString)uiText,logMes),
                    ()->{
                        Log.i(TAG,"Navigating to PlaceChoiceFragment after successful change of display name");
                        navController.navigate(getNavDirections(NEW_USER_DETAILS_FRAGMENT,PLACE_CHOICE_FRAGMENT));
                    }
                );
            }
        });
    }
    void setUpDisplayName(){
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                TextInputLayout inputLayout = requireView().findViewById(R.id.textInputLayoutDisplayName);
                if(checkDisplayNameValidity(s.toString())){
                    inputLayout.setBoxStrokeColor(ContextCompat.getColor(requireActivity(), R.color.green_dark));
                    inputLayout.setError(null);
                }else{
                    inputLayout.setBoxStrokeColor(ContextCompat.getColor(requireActivity(), R.color.red_dark));
                    inputLayout.setError("Invalid display name");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
    private void setUpSelectSex(){
        RadioGroup radioGroup = requireActivity().findViewById(R.id.sex_radio_group);
        int selectedId = radioGroup.getCheckedRadioButtonId();

        if(selectedId == R.id.male_radio_button) {
            // Male is selected
        } else if(selectedId == R.id.female_radio_button) {
            // Female is selected
        } else if(selectedId == R.id.other_radio_button) {
            // Other is selected
        }
    }
    /**
    User can have display name which :
     -  have from 4 to 20 characters
     -  contains letters from every language 0...9 or _,#,$,! as characters
     -  starts from alphabetical letter4
     */
    private boolean checkDisplayNameValidity(String displayName){
        if(displayName==null)
            return false;
        return DISPLAY_NAME_PATTERN.matcher(displayName).find();
    }
}
