package com.overmighties.pubber.feature.pubdetails.chipsfragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.overmighties.pubber.R;
import com.overmighties.pubber.feature.pubdetails.chipsfragments.stateholders.DetailsDrinkCardViewUiState;
import com.overmighties.pubber.feature.pubdetails.chipsfragments.stateholders.DetailsSelectListener;


import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

public class DetailsDrinkListAdapter extends RecyclerView.Adapter<DetailsDrinkListAdapter.ViewHolder> {
    public static final String TAG = "DetailsDrinkListAdapter";
    private final List<DetailsDrinkCardViewUiState> dataList;
    private final List<DetailsDrinkCardViewUiState> fullDataList;
    private final DetailsSelectListener detailsSelectListener;

    @Getter
    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView name;

        public ViewHolder(@NonNull View itemView, DetailsSelectListener detailsSelectListener) {
            super(itemView);
            name = itemView.findViewById(R.id.TV_name);
            itemView.setOnClickListener(v->{
                if(detailsSelectListener != null){
                    int pos=getBindingAdapterPosition();

                    if(pos!=RecyclerView.NO_POSITION)
                    {
                        detailsSelectListener.onItemClicked(pos);
                    }
                }
            });
        }
    }

    public DetailsDrinkListAdapter(List<DetailsDrinkCardViewUiState> fullDataList, DetailsSelectListener detailsSelectListener){
        this.fullDataList = new ArrayList<>(fullDataList); dataList = new ArrayList<>(fullDataList); this.detailsSelectListener = detailsSelectListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.filter_select_recycler_view_row,viewGroup,false), detailsSelectListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(dataList.get(position).getName() != null)
            holder.name.setText(dataList.get(position).getName());

    }

    public void filter(String text){
        dataList.clear();
        if (text.isEmpty()) {
            dataList.addAll(fullDataList);
        } else {
            for (var item : fullDataList) {
                if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                    dataList.add(item);
                }
            }
        }
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
