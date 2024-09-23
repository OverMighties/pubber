package com.overmighties.pubber.feature.pubdetails.chipsfragments.adapters;

import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.overmighties.pubber.R;
import com.overmighties.pubber.core.model.Drink;
import com.overmighties.pubber.feature.pubdetails.chipsfragments.DetailsEditViewModel;
import com.overmighties.pubber.feature.pubdetails.chipsfragments.stateholders.ChangeAlcoholCardViewUiState;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

public class ChangeAlcoholListAdapter extends RecyclerView.Adapter<ChangeAlcoholListAdapter.ViewHolder>{
    public static final String TAG = "ChangeAlcoholListAdapter";
    private final List<ChangeAlcoholCardViewUiState> dataList;
    private final DetailsEditViewModel viewModel;
    private final boolean isRemove;
    private final boolean isDrink;

    @Getter
    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final Chip alcohol;
        private final TextView style;
        private final ImageView delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            alcohol = itemView.findViewById(R.id.detailsEditACV_chip_alcohol);
            style = itemView.findViewById(R.id.detailsEditACV_text_styles);
            delete = itemView.findViewById(R.id.detailsEditACV_image_delete);
        }
    }

    public ChangeAlcoholListAdapter(List<ChangeAlcoholCardViewUiState> dataList, boolean isRemove, boolean isDrink, DetailsEditViewModel viewModel){
        this.dataList = new ArrayList<>(dataList); this.isRemove = isRemove; this.isDrink = isDrink; this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public ChangeAlcoholListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new ChangeAlcoholListAdapter.ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.details_edit_alcohols_cardview,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ChangeAlcoholListAdapter.ViewHolder holder, int position) {
        ChangeAlcoholCardViewUiState uiState = dataList.get(position);
        if(uiState.getAlcohol() != null)
            holder.alcohol.setText(uiState.getAlcohol());
        if(uiState.getStyle() != null) {
            holder.style.setText(uiState.getStyle());
        }
        else{
            holder.style.setVisibility(View.GONE);
        }

        if(isRemove){
            holder.alcohol.setChipBackgroundColor(ColorStateList.valueOf((ContextCompat.getColor(holder.itemView.getContext(), R.color.error_container))));
            holder.alcohol.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.error));
            holder.alcohol.setChipIconTint(ColorStateList.valueOf((ContextCompat.getColor(holder.itemView.getContext(), R.color.error))));
            holder.alcohol.setChipIcon(holder.itemView.getContext().getDrawable(R.drawable.ic_close_on_surface));
            holder.alcohol.setCheckable(false);
        }


        holder.delete.setOnClickListener(v->{
            dataList.remove(position);
            if(isRemove)
                removeAlcohol(position);
            notifyDataSetChanged();
        });

    }

    public void addNextAlcohol(ChangeAlcoholCardViewUiState newAlcoholUi){
        dataList.add(newAlcoholUi);
        notifyDataSetChanged();
    }

    public void removeAlcohol(int pos){
        List<Drink> data = new ArrayList<>();
        if(!isDrink){
            data = viewModel.getAlcoholUiState().getValue().getRemoveBeerListName();
        }
        else{
            data = viewModel.getAlcoholUiState().getValue().getRemoveDrinkListName();
        }
        data.remove(pos);
    }



    @Override
    public int getItemCount() {
        if(dataList.isEmpty()){return 0;}
        else{
            return  dataList.size();
        }
    }
}
