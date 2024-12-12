package com.overmighties.pubber.feature.bookmarks;

import static com.overmighties.pubber.feature.search.PubListViewModel.CONTENT_PROVIDED;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.Constraints;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;


import com.overmighties.pubber.R;
import com.overmighties.pubber.app.designsystem.NavigationBar;
import com.overmighties.pubber.databinding.FragmentSearcherBinding;
import com.overmighties.pubber.feature.search.ListPubAdapter;
import com.overmighties.pubber.feature.search.PubListViewModel;
import com.overmighties.pubber.feature.search.SearcherFragmentDirections;
import com.overmighties.pubber.feature.search.stateholders.PubsCardViewUiState;
import com.overmighties.pubber.feature.search.util.PubFiltrationState;
import com.overmighties.pubber.feature.search.util.PubListSelectListener;
import com.overmighties.pubber.util.DimensionsConverter;
import com.overmighties.pubber.util.ResourceUtil;

import java.util.stream.Collectors;

public class SavedFragment extends Fragment implements PubListSelectListener {
    private ListPubAdapter adapter;
    private  RecyclerView savedPubsRecyclerView ;

    private SavedViewModel viewModel;
    private PubListViewModel pubListViewModel;
    private NavController navController;
    public SavedFragment(){super(R.layout.fragment_saved);}

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState)
    {
        navController= Navigation.findNavController(requireActivity(),R.id.main_navHostFragment_container);
        savedPubsRecyclerView = requireView().findViewById(R.id.savelist);
        viewModel= new ViewModelProvider(this).get(SavedViewModel.class);
        pubListViewModel = new ViewModelProvider(requireActivity())
                .get(PubListViewModel.class);
        setup();
    }

    private void setup()
    {
        viewModel.getUiState().getValue().setPubItemCardViewUiState(new PubsCardViewUiState(false, CONTENT_PROVIDED,
                pubListViewModel.getSavedData()
                        .stream()
                        .map(pub ->new Pair<>(pub, new PubFiltrationState(-1, null, null)))
                        .map(pubListViewModel::mapPubToUiState).collect(Collectors.toList())));
        savedPubsRecyclerView.setAdapter(new ListPubAdapter(
                viewModel.getUiState().getValue().getPubItemCardViewUiState(),
                this,
                pubListViewModel.getSearcherUiState().getValue().getChipTag(),
                pubListViewModel.getFavouritePubState()));
    }

    @Override
    public void onItemClicked(int position) {
        /*
        FragmentSearcherBinding binding = pubListViewModel.getSearcherUiState().getValue().getBinding();
        Context context = binding.searcherFragment.getContext();
        View imageView = binding.searcherRecyclerViewPubList.getChildAt(position).findViewById(R.id.pubRVR_image);
        int[] location = new int[2];
        imageView.getLocationOnScreen(location);
        ConstraintLayout constraintLayout = binding.getRoot();
        ConstraintLayout view = new ConstraintLayout(context);
        view.setId(ResourceUtil.getResourceIdByName(context, "TrasitionImageView"));
        view.setBackgroundColor(ContextCompat.getColor(context, R.color.surface));
        view.setLayoutParams( new Constraints.LayoutParams(
                (int) DimensionsConverter.pxFromDp(context, 84),
                (int)DimensionsConverter.pxFromDp(context, 82)
        ));
        constraintLayout.addView(view);
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(constraintLayout);
        constraintSet.connect(view.getId(), ConstraintSet.START, constraintLayout.getId(), ConstraintSet.START,
                (int)DimensionsConverter.pxFromDp(context, 25));
        constraintSet.connect(view.getId(), ConstraintSet.TOP, constraintLayout.getId(), ConstraintSet.TOP,
                location[1]-(int)DimensionsConverter.pxFromDp(context, 42f));
        constraintSet.applyTo(constraintLayout);
        view.setZ(2);
        binding.searcherCardViewFiltration.setZ(1);
        binding.searcherCardViewSearch.setZ(1);
        binding.searcherFABMap.setVisibility(View.INVISIBLE);



        view.post(new Runnable() {
            @Override
            public void run() {
                int screenWidth = context.getResources().getDisplayMetrics().widthPixels;
                int screenHeight = context.getResources().getDisplayMetrics().heightPixels;
                float scaleX = (float) screenWidth / view.getWidth();
                float scaleY = (float) screenHeight / view.getHeight();
                ScaleAnimation scaleAnimation = new ScaleAnimation(
                        1f, scaleX+1, 1f, scaleY+1,
                        ScaleAnimation.RELATIVE_TO_SELF, DimensionsConverter.pxFromDp(context, 25)/screenWidth,
                        ScaleAnimation.RELATIVE_TO_SELF, (float)location[1]/(float)screenHeight);
                scaleAnimation.setDuration((int)(300-300*((float)location[1]/(float)screenHeight)));
                scaleAnimation.setFillAfter(true);
                scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {}
                    @Override
                    public void onAnimationEnd(Animation animation) {
                        Activity activity = (context instanceof Activity) ? (Activity) context : null;
                        NavigationBar.smoothHide(activity.findViewById(R.id.main_bottomNavView), (int)(300*((float)location[1]/(float)screenHeight)));
                        view.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Navigation.findNavController(binding.getRoot()).navigate(SearcherFragmentDirections.actionSearcherToDetails());
                            }
                        }, (int)(300*((float)location[1]/(float)screenHeight)));
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {}
                });
                view.startAnimation(scaleAnimation);
            }


        });

         */
        //detailsViewModel.setOpenedPubPosition(position);
        //pubListViewModel.setPubDetails(position,detailsViewModel);
        //navController.navigate(SearcherFragmentDirections.actionSearcherToDetails());

    }


    @Override
    public void onResume()
    {
        super.onResume();
        setup();
    }

}