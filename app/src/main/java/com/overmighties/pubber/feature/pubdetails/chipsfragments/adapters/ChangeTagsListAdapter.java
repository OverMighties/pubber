package com.overmighties.pubber.feature.pubdetails.chipsfragments.adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.overmighties.pubber.R;
import com.overmighties.pubber.feature.pubdetails.chipsfragments.DetailsEditTagsFragment;
import com.overmighties.pubber.feature.pubdetails.chipsfragments.DetailsEditViewModel;
import com.overmighties.pubber.feature.pubdetails.chipsfragments.stateholders.DetailsEditTagsCardViewUiState;
import com.overmighties.pubber.util.DimensionsConverter;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

public class ChangeTagsListAdapter extends RecyclerView.Adapter<ChangeTagsListAdapter.ViewHolder> {
    public static final String TAG = "ChangeTagsListAdapter";
    private final List<DetailsEditTagsCardViewUiState> dataList;
    
    private final DetailsEditTagsFragment.dialogBoxDataType dataType;

    private final DetailsEditViewModel viewModel;
    
    @Getter
    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final Chip tag;
        private final ImageView removeButton;
    
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tag = itemView.findViewById(R.id.detailsTagsCardView);
            removeButton = itemView.findViewById(R.id.imageViewTagDeleteChange);
        }
    }

    public ChangeTagsListAdapter(List<DetailsEditTagsCardViewUiState> dataList, DetailsEditTagsFragment.dialogBoxDataType dataType, DetailsEditViewModel viewModel){
        this.dataList = new ArrayList<>(dataList); this.dataType = dataType; this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public ChangeTagsListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new ChangeTagsListAdapter.ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.details_edit_tags_cardview,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ChangeTagsListAdapter.ViewHolder holder, int position) {
        //TODO change add and remove colors for both themes to fit better
        if(dataList.get(position).getTagName() != null)
            holder.tag.setText(dataList.get(position).getTagName());
        if(dataType == DetailsEditTagsFragment.dialogBoxDataType.Add){
            holder.tag.setChipBackgroundColor(ColorStateList.valueOf(ContextCompat.getColor(holder.itemView.getContext(), R.color.chip_add_background_color)));
            holder.tag.setTextColor(ContextCompat.getColorStateList(holder.itemView.getContext(), R.color.chip_add_text_color));
            holder.tag.setChipIconTint(ColorStateList.valueOf(ContextCompat.getColor(holder.itemView.getContext(),R.color.green_bright)));
            holder.tag.setChipIcon(holder.itemView.getContext().getDrawable(R.drawable.ic_chip_checked_on_surface));
        }else{
            holder.tag.setChipBackgroundColor(ColorStateList.valueOf(ContextCompat.getColor(holder.itemView.getContext(), R.color.chip_remove_background_color)));
            holder.tag.setTextColor(ContextCompat.getColorStateList(holder.itemView.getContext(), R.color.chip_remove_text_color));
            holder.tag.setChipIconTint(ColorStateList.valueOf(ContextCompat.getColor(holder.itemView.getContext(),R.color.error)));
            holder.tag.setChipIcon(holder.itemView.getContext().getDrawable(R.drawable.ic_close_on_surface));
        }
        holder.removeButton.setOnClickListener(v->{
            if(dataType == DetailsEditTagsFragment.dialogBoxDataType.Add) {
                viewModel.getTagsUiState().getValue().getAddTagsListName().remove(position);

            }else{
                viewModel.getTagsUiState().getValue().getRemoveTagsListName().remove(position);
            }
            dataList.remove(position);
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        if(dataList.isEmpty()){return 0;}
        else{
            return  dataList.size();
        }
    }

}
