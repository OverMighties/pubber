package com.overmighties.pubber.app.designsystem.AlcoholAlertDialog;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.overmighties.pubber.R;
import com.overmighties.pubber.util.RatingToIVConverter;

import java.util.ArrayList;
import java.util.List;

public class AlcoholAlertDialog {

    private static final String exampleURL = "https://trzechkumpli.pl/wp-content/uploads/LagerNiemiecki_medalion.png";

    public static void show(Context context, AlcoholAlertDialogUiState uiState){
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.CustomDialog);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.particular_alcohol_dialog, null);
        ((TextView)dialogView.findViewById(R.id.textView)).setText(uiState.getName());
        ((TextView)dialogView.findViewById(R.id.textView2)).setText(uiState.getShort_des());
        Glide.with(context)
                .load(exampleURL)
                .into((ImageView) dialogView.findViewById(R.id.imageView));
        //maltiness
        ((TextView)dialogView.findViewById(R.id.particularAlcoholD_text_hopinessRating))
                .setText(String.valueOf(uiState.getParameters().get(0))+"/5");
        List<ImageView> list = new ArrayList<>();
        prepareList(list, context);
        new RatingToIVConverter().convert(list, 15,
                (ConstraintLayout)dialogView.findViewById(R.id.particularAlcoholD_cl_hopiness),
                uiState.getParameters().get(0), 14, 16, false);
        ((TextView)dialogView.findViewById(R.id.particularAlcoholD_text_maltinessRating))
                .setText(String.valueOf(uiState.getParameters().get(1))+"/5");
        prepareList(list, context);
        new RatingToIVConverter().convert(list, 17,
                (ConstraintLayout)dialogView.findViewById(R.id.particularAlcoholD_cl_maltiness),
                uiState.getParameters().get(1), 10, 20, false);

        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private static void prepareList(List<ImageView> list, Context context){
        list.clear();
        for(int i=0;i<5;i++){
            list.add(new ImageView(context));
        }
    }
}
