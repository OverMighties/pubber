package com.overmighties.pubber.feature.dictionary;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.overmighties.pubber.R;
import com.overmighties.pubber.feature.dictionary.stateholders.AlcoholCardViewUiState;
import com.overmighties.pubber.feature.dictionary.util.AlcoholSelectListener;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

public class AlcoholListAdapter extends RecyclerView.Adapter<AlcoholListAdapter.ViewHolder>{
    public static final String TAG = "AlcoholListAdapter";
    private final List<AlcoholCardViewUiState> dataList;
    private final AlcoholSelectListener alcoholSelectListener;

    @Getter
    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final ConstraintLayout layout;
        private final TextView name;
        private final TextView short_des;
        private final ImageView image;
        public ViewHolder(@NonNull View itemView, AlcoholSelectListener alcoholSelectListener) {
            super(itemView);
            layout = itemView.findViewById(R.id.dictionaryAlcohol_cardView);
            name = itemView.findViewById(R.id.dictionaryAlcoholCV_text_alcoholName);
            short_des = itemView.findViewById(R.id.dictionaryAlcoholCV_text_alcoholDes);
            image = itemView.findViewById(R.id.dictionaryAlcoholCV_image_alcohol);
            itemView.setOnClickListener(v->{
                if(alcoholSelectListener !=null)
                {
                    int pos=getBindingAdapterPosition();

                    if(pos!=RecyclerView.NO_POSITION)
                    {
                        alcoholSelectListener.onItemClicked(pos);
                    }
                }
            });
        }
    }

    public AlcoholListAdapter(List<AlcoholCardViewUiState> dataList, AlcoholSelectListener alcoholSelectListener){
        this.dataList = new ArrayList<>(dataList);
        this.alcoholSelectListener = alcoholSelectListener;
    }

    @NonNull
    @Override
    public AlcoholListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new AlcoholListAdapter.ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_dictionary_alcohol_cardview,viewGroup,false), alcoholSelectListener);
    }

    @Override
    public void onBindViewHolder(@NonNull AlcoholListAdapter.ViewHolder holder, int position) {
        if(dataList.get(position).getName() != null){
            holder.name.setText(dataList.get(position).getName());
            if(dataList.get(position).getShort_des() != null)
                holder.short_des.setText(dataList.get(position).getShort_des());
            Glide.with(holder.itemView.getContext())
                    .load(dataList.get(position).getPhoto_path())
                    .centerCrop()
                    .into(holder.image);

        }
    }

    @Override
    public int getItemCount() {
        if(dataList.isEmpty()){return 0;}
        else{
            return  dataList.size();
        }
    }
}
