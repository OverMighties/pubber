package com.overmighties.pubber.app.designsystem.alcohol_alert_dialog;

import static com.overmighties.pubber.app.Constants.PARTICULAR_ALCOHOL_DIALOG_EDITEXTS_RATING_IDS;
import static com.overmighties.pubber.app.Constants.PARTICULAR_ALCOHOL_DIALOG_VIEWS_TOINVISIBLE_IDS;

import android.app.AlertDialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.navigation.NavController;

import com.bumptech.glide.Glide;
import com.overmighties.pubber.R;
import com.overmighties.pubber.util.RatingToIVConverter;

import java.util.ArrayList;
import java.util.List;

//TODO make constraintlayout with rating alignment and edit mode for alcohol

public class AlcoholAlertDialog {

    private static final String TAG = "AlcoholAlertDialog";

    private static AlcoholAlertDialogUiState uiState;

    private static boolean isEditMode = false;


    public static void show(Context context, AlcoholAlertDialogUiState ui, NavController navController, Integer actionId){
        uiState = ui;
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.CustomDialog);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_particular_alcohol, null);
        setUpData(dialogView, context);
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        setUpListeners(dialogView, navController, alertDialog, actionId);
        alertDialog.show();
    }

    private static void setUpData(View dialogView, Context context) {
        //Name + alcohol content
        ((TextView)dialogView.findViewById(R.id.particularAlcoholD_text_alcoholName)).setText(uiState.getName() + " ~ "+uiState.getParameters().get(2)+"%");
        //Short desciption
        ((TextView)dialogView.findViewById(R.id.particularAlcoholD_text_shortDes)).setText(uiState.getShort_des());
        Glide.with(context)
                .load(uiState.getPhotoPath())
                .centerCrop()
                .into((ImageView) dialogView.findViewById(R.id.particularAlcoholD_image_alcohol));
        //hopiness
        ((TextView)dialogView.findViewById(R.id.particularAlcoholD_text_hopinessRating))
                .setText(uiState.getParameters().get(0)+"/5");
        List<ImageView> list = new ArrayList<>();
        prepareList(list, context);
        new RatingToIVConverter().convert(list, 17,
                dialogView.findViewById(R.id.particularAlcoholD_cl_hopiness),
                uiState.getParameters().get(0), 10, 16, false, context);
        //maltiness
        ((TextView)dialogView.findViewById(R.id.particularAlcoholD_text_maltinessRating))
                .setText(uiState.getParameters().get(1)+"/5");
        prepareList(list, context);
        new RatingToIVConverter().convert(list, 17,
                dialogView.findViewById(R.id.particularAlcoholD_cl_maltiness),
                uiState.getParameters().get(1), 10, 16, false, context);
    }

    private static void setUpListeners(View dialogView, NavController navController, AlertDialog alertDialog, Integer actionId) {
        dialogView.findViewById(R.id.particularAlcoholD_button_edit).setOnClickListener(v->{
            if(isEditMode){
                setUpNormalMode(dialogView);
                isEditMode = false;
            } else {
                setUpEditMode(dialogView);
            }
        });
        dialogView.findViewById(R.id.particularAlcoholD_button_more).setOnClickListener(v->{
            if(isEditMode){
                setUpNormalMode(dialogView);
                isEditMode = false;
                //TODO send to server proposed changes
            } else {
                navController.navigate(actionId);
                alertDialog.dismiss();
            }
        });
    }

    private static void setUpEditMode(View dialogView) {
        isEditMode = true;
        //setUp editTexts
        for(var id:PARTICULAR_ALCOHOL_DIALOG_EDITEXTS_RATING_IDS){
            setUpRatingEditText(dialogView, id);
        }
        //hide views
        for(var id:PARTICULAR_ALCOHOL_DIALOG_VIEWS_TOINVISIBLE_IDS){
            dialogView.findViewById(id).setVisibility(View.INVISIBLE);
        }
        //change buttons
        setUpEditModeButtons(dialogView);
    }

    private static void setUpRatingEditText(View dialogView, int id) {
        setUpEditTextAppearance(dialogView, id);
        EditText editText = dialogView.findViewById(id);
        editText.addTextChangedListener(new TextWatcher() {
            String oldText;
            Integer pointer = 0;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                oldText = s.toString();
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                editText.removeTextChangedListener(this);
                String newNumber = findDiffrenceString(s.toString(), oldText);
                try {
                    if(newNumber != null){
                        if(pointer == 0){
                            if (Float.valueOf(newNumber) < 5) {
                                editText.setText(newNumber + "./5");
                                pointer++;
                            } else if (Float.valueOf(newNumber) > 5){
                                editText.setText(oldText);
                            } else if (Float.valueOf(newNumber) == 5){
                                editText.setText(newNumber + "/5");
                            } else {
                                editText.setText(newNumber+"/5");
                            }
                        } else {
                            if (Float.valueOf(newNumber) <= 9) {
                                editText.setText(oldText.substring(0, 2) + newNumber + "/5");
                            } else {
                                editText.setText(oldText);
                            }
                            pointer = 0;
                        }
                    } else {
                        pointer = 0;
                        editText.setText(oldText);
                    }
                } catch (Exception e) {
                    editText.setText(oldText);
                    Log.e(TAG, "RatingChange: Parsed character isn't numer");
                }
                editText.addTextChangedListener(this);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }
    private static void setUpAlcoholEditText(View dialogView, int id) {
        setUpEditTextAppearance(dialogView, id);
        EditText editText = dialogView.findViewById(id);
        editText.addTextChangedListener(new TextWatcher() {
            String oldText;
            boolean isDot;
            Integer pointer = 0;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                oldText = s.toString();
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                editText.removeTextChangedListener(this);
                String newNumber = findDiffrenceString(s.toString(), oldText);
                if(newNumber != null){
                    if(newNumber.equals(".") || newNumber.equals(",") || newNumber.equals(" ")){
                        editText.setText(oldText.substring(0, pointer) + "." + "%");
                        isDot = true;
                        pointer++;
                    } else {
                        try {
                            Float.valueOf(newNumber);
                            if(pointer == 0){
                                isDot = false;
                                editText.setText(newNumber+"%");
                                pointer++;
                            } else if (!isDot){
                                editText.setText(oldText.charAt(0) + newNumber + "%");
                                pointer++;
                            } else if(isDot){
                                editText.setText(oldText.substring(0, pointer) + newNumber + "%");
                                pointer = 0;
                            }
                        } catch (Exception e) {
                            editText.setText(oldText);
                            Log.e(TAG, "RatingChange: Parsed character isn't numer");
                        }
                    }
                }
                editText.addTextChangedListener(this);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private static void setUpEditTextAppearance(View dialogView, int id) {
        dialogView.findViewById(id).setEnabled(true);
        dialogView.findViewById(id).setBackground(ResourcesCompat.getDrawable(
                dialogView.getResources(),R.drawable.edit_text_shape, null));
        dialogView.findViewById(id).setOnFocusChangeListener((v, hasFocus)->{
            if(hasFocus){
                v.setBackground(ResourcesCompat.getDrawable(
                        dialogView.getResources(), R.drawable.edit_text_shape_highlight, null));
            } else {
                v.setBackground(ResourcesCompat.getDrawable(
                        dialogView.getResources(), R.drawable.edit_text_shape, null));
            }
        });
    }

    private static void setUpEditModeButtons(View dialogView) {
        ((Button)dialogView.findViewById(R.id.particularAlcoholD_button_edit)).setText(dialogView.getContext().getString(R.string.cancel));
        ((Button)dialogView.findViewById(R.id.particularAlcoholD_button_edit)).setTextColor(ContextCompat.getColor(dialogView.getContext(), R.color.error));
        dialogView.findViewById(R.id.particularAlcoholD_button_edit).setBackgroundResource(R.drawable.button_shape_negative);
        ((Button)dialogView.findViewById(R.id.particularAlcoholD_button_more)).setText(dialogView.getContext().getString(R.string.accept));
        ((Button)dialogView.findViewById(R.id.particularAlcoholD_button_more)).setTextColor(ContextCompat.getColor(dialogView.getContext(), R.color.green_bright));
        dialogView.findViewById(R.id.particularAlcoholD_button_more).setBackgroundResource(R.drawable.button_shape_positive);

    }

    private static void setUpNormalMode(View dialogView) {
        ((Button)dialogView.findViewById(R.id.particularAlcoholD_button_edit)).setText(dialogView.getContext().getString(R.string.edit));
        ((Button)dialogView.findViewById(R.id.particularAlcoholD_button_edit)).setTextColor(ContextCompat.getColor(dialogView.getContext(), R.color.on_secondary_container));
        dialogView.findViewById(R.id.particularAlcoholD_button_edit).setBackgroundResource(R.drawable.button_shape_container);
        ((Button)dialogView.findViewById(R.id.particularAlcoholD_button_more)).setText(dialogView.getContext().getString(R.string.more));
        ((Button)dialogView.findViewById(R.id.particularAlcoholD_button_more)).setTextColor(ContextCompat.getColor(dialogView.getContext(), R.color.on_primary));
        dialogView.findViewById(R.id.particularAlcoholD_button_more).setBackgroundResource(R.drawable.button_shape_primary);
        for(var id:PARTICULAR_ALCOHOL_DIALOG_VIEWS_TOINVISIBLE_IDS){
            dialogView.findViewById(id).setVisibility(View.VISIBLE);
        }
        ((TextView)dialogView.findViewById(R.id.particularAlcoholD_text_hopinessRating))
                .setText(uiState.getParameters().get(0)+"/5");
        ((TextView)dialogView.findViewById(R.id.particularAlcoholD_text_maltinessRating))
                .setText(uiState.getParameters().get(1)+"/5");
        for(var id:PARTICULAR_ALCOHOL_DIALOG_EDITEXTS_RATING_IDS){
            dialogView.findViewById(id).setEnabled(false);
            dialogView.findViewById(id).setBackground(null);
            dialogView.findViewById(id).setOnFocusChangeListener((v, hasFocus)->{});
        }
    }

    private static String findDiffrenceString(String cs1, String cs2) {
        for (int i = 0; i < cs1.length() && i < cs2.length(); ++i) {
            if (cs1.charAt(i) != cs2.charAt(i)) {
                return String.valueOf(cs1.charAt(i));
            }
        }
        if(cs1.length()  < cs2.length())
            return String.valueOf(cs2.charAt(cs1.length()));
        if(cs1.length()  > cs2.length())
            return String.valueOf(cs1.charAt(cs2.length()));
        return null;
    }

    private static void prepareList(List<ImageView> list, Context context){
        list.clear();
        for(int i=0;i<5;i++){
            list.add(new ImageView(context));
        }
    }
}
