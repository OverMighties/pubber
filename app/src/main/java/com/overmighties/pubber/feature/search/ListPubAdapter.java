package com.overmighties.pubber.feature.search;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;
import com.overmighties.pubber.R;
import com.overmighties.pubber.feature.search.stateholders.PubItemCardViewUiState;
import com.overmighties.pubber.feature.search.stateholders.PubsCardViewUiState;

import lombok.Getter;

public class ListPubAdapter extends RecyclerView.Adapter<ListPubAdapter.PubViewHolder> {

    private final PubsCardViewUiState pubData;
    @Getter
    public static  class  PubViewHolder extends RecyclerView.ViewHolder{

        private final TextView name;
        private final TextView timeOpenToday;
        private final TextView carDistance;
        private final TextView qualityRating;
        private final TextView costRating;
        private final TextView averageRatingFromServices;
        private final ImageButton imageSaveButt;
        private final ShapeableImageView pubIcon;
        private final View itemView;

        public PubViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView=itemView;
            name=(TextView) itemView.findViewById(R.id.nazwaPubu);
            qualityRating =(TextView) itemView.findViewById(R.id.ocenaJakosc);
            costRating =(TextView) itemView.findViewById(R.id.ocenaKoszty);
            timeOpenToday =(TextView) itemView.findViewById(R.id.timeOpenToday_card_view_row);
            averageRatingFromServices =(TextView) itemView.findViewById(R.id.ocenaSredniaSerwisy);
            pubIcon =(ShapeableImageView) itemView.findViewById(R.id.PubImage);
            carDistance =(TextView) itemView.findViewById(R.id.drogaAuto);
            imageSaveButt =(ImageButton)itemView.findViewById(R.id.heart);
            imageSaveButt.setTag(R.drawable.heartempty);
            itemView.setOnClickListener(view -> Navigation.findNavController(view).navigate(SearcherFragmentDirections.actionSearcherToDetails()));
        }
    }
    public ListPubAdapter(PubsCardViewUiState pubData) {this.pubData=pubData;}
    @Override
    public PubViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new PubViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.pub_recycler_view_row,viewGroup,false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull PubViewHolder holder, @SuppressLint("RecyclerView") int position) {
        PubItemCardViewUiState pubCardView=pubData.getPubItems().get(position);
        if(pubCardView.getName()!=null)
            holder.name.setText(pubData.getPubItems().get(position).getName());
        if(pubCardView.getQualityRating()!=null)
            holder.qualityRating.setText(pubData.getPubItems().get(position).getQualityRating().toString());
        if(pubCardView.getCostRating()!=null)
            holder.costRating.setText(pubData.getPubItems().get(position).getCostRating());
        if(pubCardView.getTimeOpenToday()!=null)
            holder.timeOpenToday.setText(pubData.getPubItems().get(position).getTimeOpenToday());
        if(pubCardView.getAverageRatingFromServices()!=null)
            holder.averageRatingFromServices.setText(pubData.getPubItems().get(position).getAverageRatingFromServices().toString());
        if(pubCardView.getCarDistance()!=null)
            holder.carDistance.setText(pubData.getPubItems().get(position).getCarDistance().toString());

        Glide.with(holder.getItemView())
                .load(pubCardView.getIconUrl())
                .placeholder(R.drawable.bar_beer_icon)
                .fallback(R.drawable.bar_beer_icon)
                //.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT))
                .centerCrop()
                .into(holder.pubIcon);
            //holder.imageIcon.setImageResource(pubData.getPubItems().get(position).getIconUrl());

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
