package com.overmighties.pubber.app.designsystem;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.overmighties.pubber.R;
import com.overmighties.pubber.util.DimensionsConverter;
import com.overmighties.pubber.util.RatingToIVConverter;
import com.overmighties.pubber.util.TriConsumer;

import java.util.ArrayList;
import java.util.List;

public class AlcoholAlertDialog {

    private static final String exampleURL = "https://trzechkumpli.pl/wp-content/uploads/LagerNiemiecki_medalion.png";
    //parametrs for beer [0]-hoppiness, [1]-maltiness, [2]-alcohol percantage for cocktails [0]-booziness, [1]-sweetness, [3]-alcohol percentage
    public static void show(Context context, String name, String short_des, String long_des, String photoPath, List<Float> paramets){
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.CustomDialog);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.particular_alcohol_dialog, null);
        ((TextView)dialogView.findViewById(R.id.textView)).setText(name);
        ((TextView)dialogView.findViewById(R.id.textView2)).setText(long_des);
        Glide.with(context)
                .load(exampleURL)
                .into((ImageView) dialogView.findViewById(R.id.imageView));
        //preparing imageview for RatingToIVConverter
        List<ImageView> list = new ArrayList<>();
        for(int i=0;i<5;i++){
            list.add(new ImageView(context));
        }
        new RatingToIVConverter().convert(list, 20, (ConstraintLayout)dialogView.findViewById(R.id.constraintLayout), 3.72F, 11, 20, false);

        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
