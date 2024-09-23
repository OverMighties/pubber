package com.overmighties.pubber.feature.search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.overmighties.pubber.R;
import com.overmighties.pubber.feature.search.stateholders.ParticularBeersCardViewUiState;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

public class ListParticularBeersAdapter extends RecyclerView.Adapter<ListParticularBeersAdapter.ViewHolder>{
    public static final String TAG = "ListParticularBeersAdapter";
    private final List<ParticularBeersCardViewUiState> dataList;

    @Getter
    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView brewery;
        private final TextView style;
        private final CardView delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            brewery = itemView.findViewById(R.id.filtrationPB_text_brewery);
            style = itemView.findViewById(R.id.filtrationPB_text_style);
            delete = itemView.findViewById(R.id.filtrationPB_cardView_delete);
        }
    }

    public ListParticularBeersAdapter(List<ParticularBeersCardViewUiState> dataList){
        this.dataList = new ArrayList<>(dataList);
    }

    @NonNull
    @Override
    public ListParticularBeersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new ListParticularBeersAdapter.ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.filter_particular_beers_recycler_view_row,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(dataList.get(position).getBrewery() != null)
            holder.brewery.setText(dataList.get(position).getBrewery());
        if(dataList.get(position).getStyle() != null)
            holder.style.setText(dataList.get(position).getStyle());
        holder.delete.setOnClickListener(v->{
            dataList.remove(position);
            notifyDataSetChanged();
        });
    }

    public void addNextBeer(ParticularBeersCardViewUiState newBeerUi){
        dataList.add(newBeerUi);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(dataList.isEmpty()){return 0;}
        else{
            return  dataList.size();
        }
    }
}
