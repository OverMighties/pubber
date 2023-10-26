package com.overmighties.pubber.feature.search;

import static com.google.android.material.internal.ContextUtils.getActivity;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.overmighties.pubber.R;
import com.overmighties.pubber.feature.pubdetails.DetailsViewModel;
import com.overmighties.pubber.feature.pubdetails.PubDetailsUiState;
import com.overmighties.pubber.feature.search.stateholders.PubsCardViewUiState;
import com.overmighties.pubber.feature.search.stateholders.SelectListener;

import java.util.Random;

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
        private final TextView costRating;
        private final TextView averageRatingFromServices;
        private final ImageButton imageSaveButt;
        //private final ImageView imageIcon;

        public PubViewHolder(@NonNull View itemView,SelectListener selectListener) {
            super(itemView);
            name=(TextView) itemView.findViewById(R.id.nazwaPubu);
            qualityRating =(TextView) itemView.findViewById(R.id.ocenaJakosc);
            costRating =(TextView) itemView.findViewById(R.id.ocenaKoszty);
            timeOpenToday =(TextView) itemView.findViewById(R.id.timeOpenToday_card_view_row);
            averageRatingFromServices =(TextView) itemView.findViewById(R.id.ocenaSredniaSerwisy);
            //imageIcon =(ImageView) itemView.findViewById(R.id.PubImage);
            walkDistance =(TextView) itemView.findViewById(R.id.drogaPieszo);
            imageSaveButt =(ImageButton)itemView.findViewById(R.id.heart);
            imageSaveButt.setTag(R.drawable.heartempty);
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
    public ListPubAdapter(PubsCardViewUiState pubData,SelectListener selectListener) {this.pubData=pubData; this.selectListener=selectListener;}
    @Override
    public PubViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new PubViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.pub_recycler_view_row,viewGroup,false),selectListener);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull PubViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if(pubData.getPubItems().get(position).getName()!=null)
            if(pubData.getPubItems().get(position).getName().length()>16){
                if(pubData.getPubItems().get(position).getName().substring(15,16).equals(" ")){
                    holder.name.setText(pubData.getPubItems().get(position).getName().substring(0,15)+"...");
                }else{
                    holder.name.setText(pubData.getPubItems().get(position).getName().substring(0,16)+"...");
                }
            }else{
                holder.name.setText(pubData.getPubItems().get(position).getName());
            }
        if(pubData.getPubItems().get(position).getQualityRating()!=null)
            holder.qualityRating.setText(pubData.getPubItems().get(position).getQualityRating().toString());
        if(pubData.getPubItems().get(position).getCostRating()!=null)
            holder.costRating.setText(pubData.getPubItems().get(position).getCostRating());
        if(pubData.getPubItems().get(position).getTimeOpenToday()!=null){
            holder.timeOpenToday.setText(pubData.getPubItems().get(position).getTimeOpenToday());
            if(pubData.getPubItems().get(position).getIsOpenNow()==true){holder.timeOpenToday.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.highlightOpen));
                holder.timeOpenToday.setShadowLayer(3,1.8f,1.3f,ContextCompat.getColor(holder.itemView.getContext(), R.color.highlightOpen));}
            else {holder.timeOpenToday.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.highlightClose));
                holder.timeOpenToday.setShadowLayer(3,1.8f,1.3f,ContextCompat.getColor(holder.itemView.getContext(), R.color.highlightClose));}
        } else{ holder.timeOpenToday.setText("");}
        if(pubData.getPubItems().get(position).getAverageRatingFromServices()!=null)
            holder.averageRatingFromServices.setText(pubData.getPubItems().get(position).getAverageRatingFromServices().toString());
        if(pubData.getPubItems().get(position).getCarDistance()!=null)
            holder.walkDistance.setText(pubData.getPubItems().get(position).getCarDistance().toString()+"km");
        //if(pubData.getPubItems().get(position).getImageIconViewId()!=null)
          //  holder.imageIcon.setImageResource(pubData.getPubItems().get(position).getImageIconViewId());

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
