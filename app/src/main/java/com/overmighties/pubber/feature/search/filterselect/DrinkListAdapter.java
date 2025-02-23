package com.overmighties.pubber.feature.search.filterselect;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.overmighties.pubber.R;
import com.overmighties.pubber.feature.search.filterselect.stateholders.FilterSelectDrinkCardViewUiState;
import com.overmighties.pubber.feature.search.filterselect.stateholders.FilterSelectListener;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

public class DrinkListAdapter extends RecyclerView.Adapter<DrinkListAdapter.ViewHolder> {
    public static final String TAG = "DrinkListAdapter";
    private final List<FilterSelectDrinkCardViewUiState> dataList;
    private final List<FilterSelectDrinkCardViewUiState> fullDataList;
    private final FilterSelectListener filterSelectListener;

    @Getter
    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView name;

        public ViewHolder(@NonNull View itemView, FilterSelectListener filterSelectListener) {
            super(itemView);
            name = itemView.findViewById(R.id.filterSelectRVR_text_name);
            itemView.setOnClickListener(v->{
                if(filterSelectListener != null){
                    int pos=getBindingAdapterPosition();

                    if(pos!=RecyclerView.NO_POSITION)
                    {
                        filterSelectListener.onItemClicked(pos);
                    }
                }
            });
        }
    }

    public DrinkListAdapter(List<FilterSelectDrinkCardViewUiState> fullDataList, FilterSelectListener filterSelectListener){
        this.fullDataList = new ArrayList<>(fullDataList); dataList = new ArrayList<>(fullDataList); this.filterSelectListener = filterSelectListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.filter_select_recycler_view_row,viewGroup,false), filterSelectListener);
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
