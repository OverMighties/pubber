package com.overmighties.pubber.feature.search;

import static androidx.core.content.ContextCompat.startActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.imageview.ShapeableImageView;

import com.overmighties.pubber.R;
import com.overmighties.pubber.feature.search.stateholders.PubItemCardViewUiState;
import com.overmighties.pubber.feature.search.stateholders.PubsCardViewUiState;
import com.overmighties.pubber.feature.search.util.PubFiltrationState;
import com.overmighties.pubber.feature.search.util.PubListSelectListener;
import com.overmighties.pubber.util.DimensionsConverter;
import com.overmighties.pubber.util.RatingToIVConverter;

import java.util.ArrayList;

import lombok.Getter;

public class ListPubAdapter extends RecyclerView.Adapter<ListPubAdapter.PubViewHolder> {

    public static final String TAG="ListPubAdapter";
    private PubsCardViewUiState pubData;
    public final PubListSelectListener pubListSelectListener;
    private final String chiptag;
    private boolean isFirstImperfectShown = false;
    @Getter
    public static  class  PubViewHolder extends RecyclerView.ViewHolder{

        private final View divider;
        private final TextView dividerText;
        private final TextView name;
        private final TextView compatibility;
        private final TextView timeOpenToday;
        private final TextView walkDistance;
        private final TextView qualityRating;
        private final TextView ratingCount;
        private final ConstraintLayout ratingImage;
        //private final TextView costRating;
        //private final TextView averageRatingFromServices;
        //private final ImageButton imageSaveButt;
        private final ShapeableImageView pubIcon;
        private final View itemView;
        private final Chip mapChip;
        private final Chip alcoholChip;



       public PubViewHolder(@NonNull View itemView, PubListSelectListener pubListSelectListener) {
            super(itemView);
            this.itemView=itemView;
            divider = itemView.findViewById(R.id.pubRVR_view_imprefectDivider);
            dividerText = itemView.findViewById(R.id.pubRVR_text_imperfect);
            name= itemView.findViewById(R.id.pubRVR_text_name);
            compatibility = itemView.findViewById(R.id.pubRVR_text_compatibility);
            qualityRating = itemView.findViewById(R.id.pubRVR_text_rating);
            //costRating =(TextView) itemView.findViewById(R.id.ocenaKoszty);
            timeOpenToday = itemView.findViewById(R.id.pubRVR_text_timeOpen);
            pubIcon = itemView.findViewById(R.id.pubRVR_image);
            walkDistance = itemView.findViewById(R.id.pubRVR_text_distance);
            ratingCount = itemView.findViewById(R.id.pubRVR_text_ratingCount);
            ratingImage = itemView.findViewById(R.id.pubRVR_cl_ratingContainer);
            mapChip = itemView.findViewById(R.id.pubRVR_chip_guide);
            alcoholChip  = itemView.findViewById(R.id.pubRVR_chip_alcohol);
            /*
            imageSaveButt =(ImageButton)itemView.findViewById(R.id.heart);
            imageSaveButt.setTag(R.drawable.heartempty);

             */
           itemView.setOnClickListener(view -> {
               if(pubListSelectListener !=null)
               {
                   int pos=getBindingAdapterPosition();

                   if(pos!=RecyclerView.NO_POSITION)
                   {
                       pubListSelectListener.onItemClicked(pos);
                   }
               }
           });
       }
    }
    public ListPubAdapter(PubsCardViewUiState pubData, PubListSelectListener pubListSelectListener, String ChipTag) {

        this.pubData=pubData;this.pubListSelectListener = pubListSelectListener; this.chiptag=ChipTag;}
    @NonNull
    @Override
    public PubViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new PubViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.pub_recycler_view_row,viewGroup,false), pubListSelectListener);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull PubViewHolder holder, @SuppressLint("RecyclerView") int position) {
        PubItemCardViewUiState pubCardView=pubData.getPubItems().get(position);
        if(pubCardView.getIsBreakThrough() == null)
            setUpCompatibilityTextView(holder.compatibility, pubCardView.getCompatibility().first, pubCardView.getCompatibility().second,holder.itemView.getContext());
        if(pubCardView.getIsBreakThrough() != null && pubCardView.getIsBreakThrough() == true){
            holder.divider.setVisibility(View.VISIBLE);
            holder.dividerText.setVisibility(View.VISIBLE);
        }

        if(pubCardView.getName()!=null)
            holder.name.setText(pubCardView.getName());

        if(pubCardView.getQualityRating()!=null) {
            holder.qualityRating.setText(pubCardView.getQualityRating().toString());
            ArrayList<ImageView> imageViews = new ArrayList<>();
            imageViews.add(new ImageView(holder.itemView.getContext()));
            imageViews.add(new ImageView(holder.itemView.getContext()));
            imageViews.add(new ImageView(holder.itemView.getContext()));
            imageViews.add(new ImageView(holder.itemView.getContext()));
            imageViews.add(new ImageView(holder.itemView.getContext()));

            new RatingToIVConverter().convert(imageViews, 35, holder.ratingImage, pubCardView.getQualityRating(), 0,17, true);
        }

        if(pubCardView.getTimeOpenToday()!=null){
            holder.timeOpenToday.setText(pubCardView.getTimeOpenToday());
            if(pubCardView.getIsOpenNow()) {
                holder.timeOpenToday.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.highlight_open));
                holder.timeOpenToday.setShadowLayer(3, 1.8f, 1.3f, ContextCompat.getColor(holder.itemView.getContext(), R.color.highlight_open));
            }
            else{
                holder.timeOpenToday.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.highlight_close));
                holder.timeOpenToday.setShadowLayer(3, 1.8f, 1.3f, ContextCompat.getColor(holder.itemView.getContext(), R.color.highlight_close));
            }
        }
        else{
            holder.timeOpenToday.setText(holder.itemView.getContext().getString(R.string.nodata));
        }
        if(pubCardView.getWalkDistance()!=null) {
            holder.walkDistance.setText(pubCardView.getWalkDistance().toString());
        }
        holder.ratingCount.setText("("+ pubCardView.getRatingCount() +")");


        Glide.with(holder.getItemView())
                //.load(pubCardView.getIconUrl())
                .load("https://cdn-icons-png.freepik.com/512/1748/1748110.png")
                .placeholder(R.drawable.pub_icon_256)
                .fallback(R.drawable.bar_beer_icon)
                //.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT))
                .centerCrop()
                .into(holder.pubIcon);
        //holder.imageIcon.setImageResource(pubData.getPubItems().get(position).getIconUrl());
        /*
        holder.imageSaveButt.setOnClickListener(v -> {
            String  load;
            if((Integer)holder.imageSaveButt.getTag()==R.drawable.heartempty) {
                holder.imageSaveButt.setImageResource(R.drawable.heartfull);
                holder.imageSaveButt.setTag(R.drawable.heartfull);
            }else {
                holder.imageSaveButt.setImageResource(R.drawable.heartempty);
                holder.imageSaveButt.setTag(R.drawable.heartempty);
            }
        });

         */
        holder.mapChip.setOnClickListener(v -> {
            Uri adress = Uri.parse("geo:0,0?q="+pubCardView.getAddress());
            Intent intent = new Intent(Intent.ACTION_VIEW, adress);
            intent.setPackage("com.google.android.apps.maps");
            try {
                startActivity(holder.itemView.getContext(), intent, null);
            }catch (ActivityNotFoundException e) {
               Log.e(TAG,"Activity not found ");
            }

        });

        holder.alcoholChip.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(holder.itemView.getContext(), R.style.CustomDialog);
            LayoutInflater inflater = LayoutInflater.from(holder.itemView.getContext());
            View dialogView = inflater.inflate(R.layout.pub_recycler_view_row_dialog_alcohols, null);
            ChipGroup beerChipGroup = dialogView.findViewById(R.id.pubDA_chipGroup_beers);
            ChipGroup drinkChipGroup = dialogView.findViewById(R.id.pubDA_chipGroup_drinks);
            if (pubCardView.getAlcohol() != null) {
                for (var alcohol : pubCardView.getAlcohol()) {
                    Chip chip = new Chip(holder.itemView.getContext());
                    chip.setText(alcohol.getName());
                    chip.setPadding(16, 0, 16, 0);
                    chip.setChipCornerRadius(DimensionsConverter.pxFromDp(holder.itemView.getContext(), 8));
                    chip.setChipBackgroundColorResource(R.color.surface_container_highest);
                    chip.setTextColor(holder.itemView.getContext().getResources().getColor(R.color.on_surface_variant));
                    chip.setTextSize(14);
                    chip.setChipStrokeColorResource(R.color.outline);
                    chip.setChipStrokeWidth(DimensionsConverter.pxFromDp(holder.itemView.getContext(), 0.7F));
                    ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
                    chip.setLayoutParams(params);
                    if(alcohol.getType().equals("Beer")){
                        beerChipGroup.addView(chip);
                    }
                    else{
                        drinkChipGroup.addView(chip);
                    };
                }
            }


            builder.setView(dialogView);
            AlertDialog alertDialog = builder.create();
            dialogView.findViewById(R.id.pubDA_text_back).setOnClickListener(view->{
                alertDialog.dismiss();
            });
            alertDialog.show();
        });

        if (chiptag.equals("Small")){
            holder.mapChip.setChipIconSize(DimensionsConverter.pxFromDp(holder.itemView.getContext(), 20));
            holder.mapChip.setTextSize(12);
            holder.mapChip.setHeight(40);
            holder.alcoholChip.setChipIconSize(DimensionsConverter.pxFromDp(holder.itemView.getContext(), 16));
            holder.alcoholChip.setTextSize(12);
            holder.alcoholChip.setHeight(40);
        }

    }

    private void setUpCompatibilityTextView(TextView textView, Integer compatibility,  Integer allCompatibility, Context context) {
        textView.setText(
                String.valueOf(compatibility) + "/" + String.valueOf(allCompatibility));
        Float fraction = (float)compatibility/allCompatibility;
        fraction = fraction + 0.75f*fraction;
        Float textViewWidth = textView.getPaint().measureText(textView.getText().toString());
        int[] colors = {ContextCompat.getColor(context, R.color.primary),
                ContextCompat.getColor(context, R.color.on_surface)};
        LinearGradient linearGradient = new LinearGradient(
                0, 0, textViewWidth*fraction, 0,
                colors, null,
                Shader.TileMode.CLAMP);
        textView.getPaint().setShader(linearGradient);
        textView.setTextColor(ContextCompat.getColor(context, R.color.primary));
    }

    public void setPubData(PubsCardViewUiState pubData) {
        this.pubData = pubData;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        if(pubData.getPubItems()==null)
        {
            return 0;
        }
        return pubData.getPubItems().size();
    }




}
