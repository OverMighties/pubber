package com.overmighties.pubber.util;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.overmighties.pubber.R;
import com.overmighties.pubber.app.AppContainer;
import com.overmighties.pubber.data.model.PubUiState;
import com.overmighties.pubber.feature.search.stateholders.PubItemCardViewUiState;
import com.overmighties.pubber.feature.search.stateholders.PubsCardViewUiState;

import java.util.List;

import lombok.Getter;

public class ListPubAdapter extends RecyclerView.Adapter<ListPubAdapter.PubViewHolder> {

    private final PubsCardViewUiState pubData;
    public SelectListener selectlistener;
    @Getter
    public static  class  PubViewHolder extends RecyclerView.ViewHolder{

        private TextView name;
        private TextView timeOpenToday;
        private TextView carDistance;

        private TextView qualityRating;
        private TextView costRating;
        private TextView averageRatingFromServices;
        private ImageButton imageSaveButt;
        private ImageView imageIcon;

        public PubViewHolder(@NonNull View itemView,SelectListener selectlistener) {
            super(itemView);
            name=(TextView) itemView.findViewById(R.id.nazwaPubu);
            qualityRating =(TextView) itemView.findViewById(R.id.ocenaJakosc);
            costRating =(TextView) itemView.findViewById(R.id.ocenaKoszty);
            timeOpenToday =(TextView) itemView.findViewById(R.id.timeOpenToday_card_view_row);
            averageRatingFromServices =(TextView) itemView.findViewById(R.id.ocenaSredniaSerwisy);
            imageIcon =(ImageView) itemView.findViewById(R.id.PubImage);
            carDistance =(TextView) itemView.findViewById(R.id.drogaAuto);
            imageSaveButt =(ImageButton)itemView.findViewById(R.id.heart);
            imageSaveButt.setTag(R.drawable.heartempty);



            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(selectlistener!=null)
                    {
                        int pos=getAdapterPosition();

                        if(pos!=RecyclerView.NO_POSITION)
                        {
                            selectlistener.onItemClicked(pos);
                        }
                    }
                }
            });
        }
    }
    public ListPubAdapter(PubsCardViewUiState pubData, SelectListener selectlistener) {this.pubData=pubData;this.selectlistener=selectlistener;}
    @NonNull
    @Override
    public PubViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new PubViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.pub_recycler_view_row,viewGroup,false),selectlistener);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull PubViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.name.setText(pubData.getPubItems().get(position).getName());
        holder.qualityRating.setText(pubData.getPubItems().get(position).getQualityRating().toString());
        holder.costRating.setText(pubData.getPubItems().get(position).getName());
        holder.timeOpenToday.setText(pubData.getPubItems().get(position).getTimeOpenToday());
        holder.averageRatingFromServices.setText(pubData.getPubItems().get(position).getAverageRatingFromServices().toString());
        holder.carDistance.setText(pubData.getPubItems().get(position).getCarDistance().toString());
        holder.imageIcon.setImageResource(pubData.getPubItems().get(position).getImageIconViewId());

        holder.imageSaveButt.setOnClickListener(v -> {
            String  load;
            if((Integer)holder.imageSaveButt.getTag()==R.drawable.heartempty) {
                holder.imageSaveButt.setImageResource(R.drawable.heartfull);
                holder.imageSaveButt.setTag(R.drawable.heartfull);
                //load=AppContainer.getInstance().getPubSearchingContainer().getSavedList().getValue();
               // if(load!=null)
               // {
               //     load=load+pubData.get(position).getId();
               // }
            }else {
                holder.imageSaveButt.setImageResource(R.drawable.heartempty);
                holder.imageSaveButt.setTag(R.drawable.heartempty);
               // load=AppContainer.getInstance().getPubSearchingContainer().getSavedList().getValue();
                //load=load.replace(pubData.get(position).getId()+"-","");
            }
           // AppContainer.getInstance().getPubSearchingContainer().getSavedList().setValue(load);
        });

    }

    @Override
    public int getItemCount() {
        return pubData.getPubItems().size();
    }




}
