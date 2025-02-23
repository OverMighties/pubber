package com.overmighties.pubber.feature.pubdetails.chipsfragments;

import android.app.AlertDialog;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.overmighties.pubber.R;
import com.overmighties.pubber.core.model.Tag;
import com.overmighties.pubber.feature.pubdetails.DetailsViewModel;
import com.overmighties.pubber.feature.pubdetails.chipsfragments.adapters.ChangeTagsListAdapter;
import com.overmighties.pubber.feature.pubdetails.chipsfragments.stateholders.DetailsEditTagsCardViewUiState;
import com.overmighties.pubber.util.DimensionsConverter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DetailsEditTagsFragment extends Fragment {

    public static final String TAG = "DetailsEditFragment";

    public DetailsEditTagsFragment() {super(R.layout.details_edit_tags);}

    private DetailsViewModel detailsViewModel;
    private DetailsEditViewModel viewModel;

    private ChangeTagsListAdapter adapter;
    public enum dialogBoxDataType{
        Add,
        Remove
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity(),
                ViewModelProvider.Factory.from(DetailsEditViewModel.initializer)).get(DetailsEditViewModel.class);
        detailsViewModel=new ViewModelProvider(requireActivity()).get(DetailsViewModel.class);

    }


    @Override
    public void onViewCreated(@NonNull View v, Bundle savedInstanceState) {
        setUpListeners();
    }

    private void setUpListeners(){
        requireView().findViewById(R.id.detailsEditT_image_back).setOnClickListener(v->{
            NavHostFragment.findNavController(getParentFragment()).popBackStack();
        });

        requireView().findViewById(R.id.detailsEditT_chip_addTag).setOnClickListener(v->{
            showDialogBox(dialogBoxDataType.Add);
        });
        requireView().findViewById(R.id.detailsEditT_chip_removeTag).setOnClickListener(v->{
            showDialogBox(dialogBoxDataType.Remove);
        });

        requireView().findViewById(R.id.detailsEditT_button_submit).setOnClickListener(v->{
            //TODO submiting data to online database for change
        });
    }

    private void showDialogBox(dialogBoxDataType dialogBoxDataType) {
        //set up data based on type
        RecyclerView recyclerView;
        List<DetailsEditTagsCardViewUiState> tags = null;
        List<DetailsEditTagsCardViewUiState> checked;
        String title;
        if(dialogBoxDataType == DetailsEditTagsFragment.dialogBoxDataType.Add){
            recyclerView = requireView().findViewById(R.id.detailsEditT_recyclerView_addTags);
            tags = Arrays.stream(requireContext().getResources().getStringArray(R.array.pub_tags))
                    .map(DetailsEditTagsCardViewUiState::new).collect(Collectors.toList());
            checked = viewModel.getTagsUiState().getValue().getAddTagsListName();
            title = getString(R.string.select_tags_add);
        }
        else{
            recyclerView = requireView().findViewById(R.id.detailsEditT_recyclerView_removeTags);
            if(detailsViewModel.getUiState().getValue().getTags() != null && !detailsViewModel.getUiState().getValue().getTags().isEmpty())
                tags = detailsViewModel.getUiState().getValue().getTags().stream()
                        .map(Tag::getName).map(DetailsEditTagsCardViewUiState::new)
                        .collect(Collectors.toList());
            checked = viewModel.getTagsUiState().getValue().getRemoveTagsListName();
            title = getString(R.string.select_tags_remove);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext(), R.style.CustomDialog);
        LayoutInflater inflater = LayoutInflater.from(requireContext());
        ChipGroup dialogView = (ChipGroup) inflater.inflate(R.layout.chips_container, null);
        if(tags != null){
            for(var tag: tags){
                Chip chip = new Chip(requireContext());
                chip.setText(tag.getTagName());
                styleChip(chip);
                if(dialogBoxDataType == DetailsEditTagsFragment.dialogBoxDataType.Add){
                    styleAddChip(chip);
                }else{
                    styleRemoveChip(chip);
                }
                if(!checked.isEmpty() && checked.contains(tag)){
                    chip.setChecked(true);
                }
                dialogView.addView(chip);
            }
        }
        builder.setTitle(title);
        builder.setNeutralButton(getString(R.string.cancel), ((dialog, which) -> {}));
        builder.setNegativeButton(getString(R.string.choose), ((dialog, which) -> {
            if(dialogView.getCheckedChipIds().size() != 0){
                List<DetailsEditTagsCardViewUiState> dataList = new ArrayList<>();
                for(int chipId: dialogView.getCheckedChipIds()){
                    DetailsEditTagsCardViewUiState checkTag = new DetailsEditTagsCardViewUiState(((Chip)dialogView.findViewById(chipId)).getText().toString());
                    dataList.add(checkTag);
                }
                if(!dataList.isEmpty()){
                    if(dialogBoxDataType == DetailsEditTagsFragment.dialogBoxDataType.Add){
                        viewModel.getTagsUiState().getValue().setAddTagsListName(dataList);
                    }else{
                        viewModel.getTagsUiState().getValue().setRemoveTagsListName(dataList);
                    }
                    adapter = new ChangeTagsListAdapter(dataList, dialogBoxDataType, viewModel);
                    recyclerView.setAdapter(adapter);
                }
                else {
                    recyclerView.setAdapter(null);
                }
            }
        }));
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void styleChip(Chip chip){
        chip.setPadding(16, 0, 16, 0);
        chip.setChipCornerRadius(DimensionsConverter.pxFromDp(requireContext(), 8));
        chip.setTextSize(14);
        chip.setChipStrokeColorResource(R.color.outline);
        chip.setChipStrokeWidth(DimensionsConverter.pxFromDp(requireContext(), 0.7F));
        chip.setCheckedIconVisible(true);
        chip.setCheckable(true);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        chip.setLayoutParams(params);
    }

    private void styleRemoveChip(Chip chip){
        chip.setChipBackgroundColorResource(R.color.chip_remove_background_color);
        chip.setTextColor(ContextCompat.getColorStateList(requireContext(), R.color.chip_remove_text_color));
        chip.setCheckedIconTint(ColorStateList.valueOf(ContextCompat.getColor(requireContext(),R.color.error)));
        chip.setCheckedIcon(requireContext().getDrawable(R.drawable.ic_close_on_surface));
    }

    private void styleAddChip(Chip chip){
        chip.setChipBackgroundColorResource(R.color.chip_add_background_color);
        chip.setTextColor(ContextCompat.getColorStateList(requireContext(), R.color.chip_add_text_color));
        chip.setCheckedIconTint(ColorStateList.valueOf(ContextCompat.getColor(requireContext(),R.color.green_bright)));
        chip.setCheckedIcon(requireContext().getDrawable(R.drawable.ic_chip_checked_on_surface));
    }
}
