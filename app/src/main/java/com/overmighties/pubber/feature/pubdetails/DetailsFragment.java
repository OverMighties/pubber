package com.overmighties.pubber.feature.pubdetails;

import static com.overmighties.pubber.feature.pubdetails.DetailsViewModel.dpToPx;

import android.annotation.SuppressLint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;


import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.shape.CornerFamily;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.tabs.TabLayout;
import com.overmighties.pubber.R;

import com.overmighties.pubber.app.ui.NavigationBar;
import com.overmighties.pubber.app.ui.ViewPagerSlideTransformer;
import com.overmighties.pubber.app.ui.ViewPagerSliderAdapter;
import com.overmighties.pubber.app.ui.ViewPagerTabAdapter;
import com.overmighties.pubber.util.RatingToIVConverter;


import java.util.ArrayList;


public class DetailsFragment extends Fragment
{

    private ImageView BlurImageView;
    private ArrayList<Integer> fotki=new ArrayList<Integer>();
    private DetailsViewModel viewModel;

    public DetailsFragment() {super(R.layout.details);}
    private final ArrayList<Integer> idList=new ArrayList<Integer>();

    public ViewPager viewPager;
    private ConstraintLayout layout;
    private ShapeableImageView shapeableImageView;
    private TabLayout tabLayout;
    private ViewPager2 TabviewPager;
    private ViewPagerTabAdapter viewPagerTabAdapter;

    public void onViewCreated(@NonNull View v, Bundle savedInstanceState)
    {
        NavigationBar.smoothHide(getActivity().findViewById(R.id.bottom_nav_view));
        viewModel=new ViewModelProvider(getActivity(),
                ViewModelProvider.Factory.from(DetailsViewModel.initializer)).get(DetailsViewModel.class);
        PubDetailsUiState pubDetailsUiState= DetailsViewModel.getPubDetails().getValue();
        viewModel.setUiState(pubDetailsUiState);
        fotki.add(R.drawable.test_photo_1);
        fotki.add(R.drawable.test_photo_2);
        fotki.add(R.drawable.test_photo_3);
        fotki.add(R.drawable.test_photo_4);
        fotki.add(R.drawable.test_photo_5);
        fotki.add(R.drawable.test_photo_6);
        BlurImageView=requireView().findViewById(R.id.blur);
        layout=(ConstraintLayout)requireView().findViewById(R.id.detail);
        //listener for closing button
        requireView().findViewById(R.id.CloseButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(requireParentFragment()).navigate(DetailsFragmentDirections.actionDetailsToSearcher());
            }
        });

        SetUpTabViewPager();
        SetUpPubData(pubDetailsUiState);
        SetUpImageSlider(fotki);
    }


    private void SetUpTabViewPager(){
        tabLayout = requireView().findViewById(R.id.tabLayout);
        TabviewPager = requireView().findViewById(R.id.TabViewPager);
        viewPagerTabAdapter = new ViewPagerTabAdapter(requireActivity());
        TabviewPager.setAdapter(viewPagerTabAdapter);
        TabviewPager.setUserInputEnabled(false);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                TabviewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}
            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
        TabviewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabLayout.getTabAt(position).select();
                switch (position){
                    default: break;
                    case 1: break;

                }
            }
        });

    }

    private void SetUpPubData(PubDetailsUiState pubDetailsUiState) {
        ((TextView)requireView().findViewById(R.id.name)).setText(pubDetailsUiState.getName());
        //setting open today info parameters
        if(pubDetailsUiState.getTimeOpenToday()!=null){
            if((pubDetailsUiState.getTimeOpenToday().substring(0,1)).equals("O")){ ((TextView)requireView().findViewById(R.id.TimeOTd))
                    .setTextColor( ContextCompat.getColor(getContext(), R.color.highlight_open));
                ((TextView)requireView().findViewById(R.id.TimeOTd)).setShadowLayer(3,1.8f,1.3f,
                        ContextCompat.getColor(getContext(), R.color.highlight_open));
            }else{
                ((TextView)requireView().findViewById(R.id.TimeOTd))
                        .setTextColor( ContextCompat.getColor(getContext(), R.color.highlight_close));
                ((TextView)requireView().findViewById(R.id.TimeOTd)).setShadowLayer(3,1.8f,1.3f,
                        ContextCompat.getColor(getContext(), R.color.highlight_close));
            }
            ((TextView)requireView().findViewById(R.id.TimeOTd)).setText(pubDetailsUiState.getTimeOpenToday());
        }
        //set up rating and rating's iv
        ((TextView)requireView().findViewById(R.id.PubRating)).setText(pubDetailsUiState.getRatings().getAverageRating().toString());
        ((TextView)requireView().findViewById(R.id.PubRatingCount)).setText("("+pubDetailsUiState.getRatings().getRatingsCount()+")");
        ArrayList<ImageView> imageViews = new ArrayList<>();
        imageViews.add(new ImageView(getContext()));
        imageViews.add(new ImageView(getContext()));
        imageViews.add(new ImageView(getContext()));
        imageViews.add(new ImageView(getContext()));
        imageViews.add(new ImageView(getContext()));
        new RatingToIVConverter().Convert(imageViews, 37, requireView().findViewById(R.id.PubRatingIV), pubDetailsUiState.getRatings().getAverageRating(), 6,20);
        //setUpRating();
    }

    private void setUpRating(){
        ArrayList<ImageView> imageViews = new ArrayList<>();
        imageViews.add(new ImageView(getContext()));
        imageViews.add(new ImageView(getContext()));
        imageViews.add(new ImageView(getContext()));
        imageViews.add(new ImageView(getContext()));
        imageViews.add(new ImageView(getContext()));

      //  new RatingToIVConverter().Convert(imageViews, 35, requireView().findViewById(R.id.PubRatingIV), , 0,20);
    }


    private void SetUpImageSlider(ArrayList<Integer> fotki)
    {
        View popUpView = LayoutInflater.from(getActivity()).inflate(R.layout.detail_image_pop_up, null);
        viewPager=popUpView.findViewById(R.id.viewPager);
        viewPager.setAdapter(new ViewPagerSliderAdapter(fotki));
        viewPager.setPageTransformer(true,new ViewPagerSlideTransformer());

        final PopupWindow DetailImageViewPopUpWindow = new PopupWindow(popUpView,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT, true);
        DetailImageViewPopUpWindow.setOnDismissListener(() -> BlurImageView.setVisibility(View.GONE));
        popUpView.findViewById(R.id.dismissButton).setOnClickListener(v -> DetailImageViewPopUpWindow.dismiss());

        ConstraintLayout constraintLayout=(ConstraintLayout) requireView().findViewById(R.id.ImageSlider);
        ShapeAppearanceModel shapeAppearanceModel=new ShapeAppearanceModel()
                .toBuilder()
                .setAllCorners(CornerFamily.ROUNDED,30)
                .build();
        //set params
        for(int i=0;i< fotki.size();i+=3) {
            shapeableImageView = new ShapeableImageView(getContext());
            shapeableImageView = viewModel.CustomingBigShapeableImageView(shapeableImageView,constraintLayout, fotki.get(i),shapeAppearanceModel,i, getContext());
            //listner for popup
            shapeableImageView.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("MissingInflatedId")
                @Override
                public void onClick(View v) {
                    viewModel.takeScreenShot(layout,getContext());
                    BlurImageView.setBackground(new BitmapDrawable(getResources(), DetailsViewModel.getPubDetails().getValue().getCurrentScreen()));
                    BlurImageView.setVisibility(View.VISIBLE);

                    (getActivity().findViewById(R.id.detail))
                            .post(() -> DetailImageViewPopUpWindow.showAtLocation(getActivity().findViewById(R.id.detail), Gravity.BOTTOM, 0, 0));
                    viewPager=popUpView.findViewById(R.id.viewPager);
                    int n=0;
                    for(int fotka:fotki)
                    {
                        if(getResources().getDrawable(fotka).getConstantState()==v.getBackground().getConstantState()){
                            break;
                        }
                        n++;
                    }

                    viewPager.setCurrentItem(n,false);



                }
            });
            //next ImageView
            shapeableImageView = new ShapeableImageView(getContext());
            shapeableImageView=viewModel.CustomingSmallShapeableImageView(shapeableImageView,constraintLayout, fotki.get(i+1),
                    shapeAppearanceModel,i+1, getContext());
            //listener for popup
            shapeableImageView.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("MissingInflatedId")
                @Override
                public void onClick(View v) {
                    viewModel.takeScreenShot(layout,getContext());
                    BlurImageView.setBackground(new BitmapDrawable(getResources(), DetailsViewModel.getPubDetails().getValue().getCurrentScreen()));
                    BlurImageView.setVisibility(View.VISIBLE);

                    (getActivity().findViewById(R.id.detail))
                            .post(() -> DetailImageViewPopUpWindow.showAtLocation(getActivity().findViewById(R.id.detail), Gravity.BOTTOM, 0, 0));
                    viewPager=popUpView.findViewById(R.id.viewPager);

                    int n=0;
                    for(int fotka:fotki)
                    {
                        if(getResources().getDrawable(fotka).getConstantState()==v.getBackground().getConstantState()){
                            break;
                        }
                        n++;
                    }

                    viewPager.setCurrentItem(n,false);

                }
            });
            //nextImageView
            shapeableImageView = new ShapeableImageView(getContext());
            shapeableImageView = new ShapeableImageView(getContext());
            shapeableImageView=viewModel.CustomingSmallShapeableImageView(shapeableImageView,constraintLayout, fotki.get(i+2),
                    shapeAppearanceModel,i+2, getContext());
            //listener for popup
            shapeableImageView.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("MissingInflatedId")
                @Override
                public void onClick(View v) {
                    viewModel.takeScreenShot(layout,getContext());
                    BlurImageView.setBackground(new BitmapDrawable(getResources(), DetailsViewModel.getPubDetails().getValue().getCurrentScreen()));
                    BlurImageView.setVisibility(View.VISIBLE);

                    (getActivity().findViewById(R.id.detail))
                            .post(() -> DetailImageViewPopUpWindow.showAtLocation(getActivity().findViewById(R.id.detail), Gravity.BOTTOM, 0, 0));
                    viewPager=popUpView.findViewById(R.id.viewPager);

                    int n=0;
                    for(int fotka:fotki)
                    {
                        if(getResources().getDrawable(fotka).getConstantState()==v.getBackground().getConstantState()){
                            break;
                        }
                        n++;
                    }

                    viewPager.setCurrentItem(n,false);

                }
            });
        }
        shapeableImageView = new ShapeableImageView(getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(dpToPx(170), dpToPx(270));
        shapeableImageView.setLayoutParams(params);
        shapeableImageView.setId(View.generateViewId());
        constraintLayout.addView(shapeableImageView);
        ConstraintSet constraintSet=new ConstraintSet();
        constraintSet.clone(constraintLayout);
        constraintSet.connect(shapeableImageView.getId(), ConstraintSet.START, constraintLayout.getId(), ConstraintSet.START, 480+(fotki.size()-3)*(280));
        constraintSet.connect(shapeableImageView.getId(), ConstraintSet.TOP, constraintLayout.getId(), ConstraintSet.TOP, dpToPx(140));
        constraintSet.applyTo(constraintLayout);
    }





}