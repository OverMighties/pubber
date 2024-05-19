package com.overmighties.pubber.feature.search;

import static androidx.core.content.ContextCompat.startActivity;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
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
import com.google.android.material.imageview.ShapeableImageView;

import com.overmighties.pubber.R;
import com.overmighties.pubber.feature.search.stateholders.PubItemCardViewUiState;
import com.overmighties.pubber.feature.search.stateholders.PubsCardViewUiState;
import com.overmighties.pubber.feature.search.stateholders.SelectListener;
import com.overmighties.pubber.util.RatingToIVConverter;

import java.util.ArrayList;

import lombok.Getter;

public class ListPubAdapter extends RecyclerView.Adapter<ListPubAdapter.PubViewHolder> {

    private final PubsCardViewUiState pubData;
    public SelectListener selectListener;
    @Getter
    public static  class  PubViewHolder extends RecyclerView.ViewHolder{

        private final TextView name;
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


       public PubViewHolder(@NonNull View itemView, SelectListener selectListener) {
            super(itemView);
            this.itemView=itemView;
            name=(TextView) itemView.findViewById(R.id.PName);
            qualityRating =(TextView) itemView.findViewById(R.id.PRating);
            //costRating =(TextView) itemView.findViewById(R.id.ocenaKoszty);
            timeOpenToday =(TextView) itemView.findViewById(R.id.timeOpenToday_card_view_row);
            pubIcon =(ShapeableImageView) itemView.findViewById(R.id.PubImage);
            walkDistance =(TextView) itemView.findViewById(R.id.PWalkDis);
            ratingCount = (TextView) itemView.findViewById(R.id.PRatingCount);
            ratingImage = (ConstraintLayout) itemView.findViewById(R.id.PClRatingImage);
            mapChip = (Chip) itemView.findViewById(R.id.MapChip);
            alcoholChip  = (Chip) itemView.findViewById(R.id.AlcoholChip);
            /*
            imageSaveButt =(ImageButton)itemView.findViewById(R.id.heart);
            imageSaveButt.setTag(R.drawable.heartempty);

             */
           itemView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   if(selectListener!=null)
                   {
                       int pos=getAdapterPosition();

                       if(pos!=RecyclerView.NO_POSITION)
                       {
                           selectListener.onItemClicked(pos);
                       }
                   }
               }
           });
       }
    }
    public ListPubAdapter(PubsCardViewUiState pubData, SelectListener selectListener) {this.pubData=pubData;this.selectListener=selectListener;}
    @Override
    public PubViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new PubViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.pub_recycler_view_row,viewGroup,false),selectListener);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull PubViewHolder holder, @SuppressLint("RecyclerView") int position) {
        PubItemCardViewUiState pubCardView=pubData.getPubItems().get(position);
        if(pubCardView.getName()!=null)
            if(pubCardView.getName().length()>20){
                if(pubCardView.getName().substring(19,20).equals(" ")){
                    holder.name.setText(pubCardView.getName().substring(0,19)+"...");
                }else{
                    holder.name.setText(pubCardView.getName().substring(0,20)+"...");
                }
            }else{
                holder.name.setText(pubCardView.getName());
            }
        if(pubCardView.getQualityRating()!=null) {
            holder.qualityRating.setText(pubCardView.getQualityRating().toString());
            ArrayList<ImageView> imageViews = new ArrayList<>();
            imageViews.add(new ImageView(holder.itemView.getContext()));
            imageViews.add(new ImageView(holder.itemView.getContext()));
            imageViews.add(new ImageView(holder.itemView.getContext()));
            imageViews.add(new ImageView(holder.itemView.getContext()));
            imageViews.add(new ImageView(holder.itemView.getContext()));

            new RatingToIVConverter().Convert(imageViews, 35, holder.ratingImage, pubCardView.getQualityRating(), 0,17);
        }
     //   if(pubCardView.getCostRating()!=null)
    //        holder.costRating.setText(pubData.getPubItems().get(position).getCostRating());
        if(pubCardView.getTimeOpenToday()!=null){
            holder.timeOpenToday.setText(pubCardView.getTimeOpenToday());
            if(pubCardView.getIsOpenNow()==true) {
                holder.timeOpenToday.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.highlight_open));
                holder.timeOpenToday.setShadowLayer(3, 1.8f, 1.3f, ContextCompat.getColor(holder.itemView.getContext(), R.color.highlight_open));}
            else{
                holder.timeOpenToday.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.highlight_close));
                holder.timeOpenToday.setShadowLayer(3, 1.8f, 1.3f, ContextCompat.getColor(holder.itemView.getContext(), R.color.highlight_close));
                }}
     //   if(pubCardView.getAverageRatingFromServices()!=null)
     //       holder.averageRatingFromServices.setText(pubData.getPubItems().get(position).getAverageRatingFromServices().toString());
        if(pubCardView.getWalkDistance()!=null) {
            holder.walkDistance.setText(pubCardView.getWalkDistance().toString());
        }
        //if(pubCardView.getRatingCount()!=null)
       // {
        holder.ratingCount.setText("("+String.valueOf(pubCardView.getRatingCount())+")");
       // }

        Glide.with(holder.getItemView())
                .load(pubCardView.getIconUrl())
                .placeholder(R.drawable.bar_beer_icon)
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
        holder.mapChip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri adress = Uri.parse("geo:0,0?q="+pubCardView.getAddress());
                Intent intent = new Intent(Intent.ACTION_VIEW, adress);
                intent.setPackage("com.google.android.apps.maps");
                try {
                    startActivity(holder.itemView.getContext(), intent, null);
                }catch (ActivityNotFoundException e){}

            }
        });

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
