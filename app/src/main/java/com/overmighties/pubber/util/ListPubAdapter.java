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

import java.util.List;

import lombok.Getter;

public class ListPubAdapter extends RecyclerView.Adapter<ListPubAdapter.PubViewHolder> {

    private List<PubUiState> pubData;
    public SelectListener selectlistener;
    @Getter
    public static  class  PubViewHolder extends RecyclerView.ViewHolder{

        private TextView nazwa;
        private TextView czasOtwarcia;
        private TextView czasAuto;
        private  TextView czasPieszo;
        private TextView ocenaJakosc;
        private TextView ocenaKoszty;
        private TextView ocenaGoogle;
        private ImageButton imageSaveButt;
        private ImageView imageIcon;
        private static final String FILE_NAME="saved.txt";

        public PubViewHolder(@NonNull View itemView,SelectListener selectlistener) {
            super(itemView);
            nazwa=(TextView) itemView.findViewById(R.id.nazwaPubu);
            ocenaJakosc =(TextView) itemView.findViewById(R.id.ocenaJakosc);
            ocenaKoszty =(TextView) itemView.findViewById(R.id.ocenaKoszty);
            czasOtwarcia=(TextView) itemView.findViewById(R.id.timeOpenToday_card_view_row);
            ocenaGoogle=(TextView) itemView.findViewById(R.id.ocenaSredniaSerwisy);
            imageIcon =(ImageView) itemView.findViewById(R.id.PubImage);
            czasAuto =(TextView) itemView.findViewById(R.id.drogaAuto);
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
    public ListPubAdapter(List<PubUiState> pubData, SelectListener selectlistener) {this.pubData=pubData;this.selectlistener=selectlistener;}
    @NonNull
    @Override
    public PubViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new PubViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.pub_recycler_view_row,viewGroup,false),selectlistener);
    }

    @Override
    public void onBindViewHolder(@NonNull PubViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.nazwa.setText(pubData.get(position).getName());
        holder.ocenaJakosc.setText(pubData.get(position).getRatingGoogle()+"");
        holder.ocenaKoszty.setText(pubData.get(position).getName());
        holder.czasOtwarcia.setText(pubData.get(position).getRatingGoogle()+"");
        holder.ocenaGoogle.setText(pubData.get(position).getName());
        holder.czasAuto.setText(pubData.get(position).getPrice());
        holder.czasPieszo.setText(pubData.get(position).getPrice());
        holder.imageIcon.setImageResource(pubData.get(position).getImage());

        holder.imageSaveButt.setOnClickListener(v -> {
            String  load;
            if((Integer)holder.imageSaveButt.getTag()==R.drawable.heartempty)
            {
                holder.imageSaveButt.setImageResource(R.drawable.heartfull);
                holder.imageSaveButt.setTag(R.drawable.heartfull);
                load=AppContainer.getInstance().getPubSearchingContainer().getSavedList().getValue();
                if(load!=null)
                {
                    load=load+pubData.get(position).getId();
                }
            }else
            {

                holder.imageSaveButt.setImageResource(R.drawable.heartempty);
                holder.imageSaveButt.setTag(R.drawable.heartempty);
                load=AppContainer.getInstance().getPubSearchingContainer().getSavedList().getValue();
                load=load.replace(pubData.get(position).getId()+"-","");
            }
            AppContainer.getInstance().getPubSearchingContainer().getSavedList().setValue(load);
        });

    }

    @Override
    public int getItemCount() {
        return pubData.size();
    }




}
