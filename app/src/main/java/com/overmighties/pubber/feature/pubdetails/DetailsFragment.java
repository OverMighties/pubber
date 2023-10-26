package com.overmighties.pubber.feature.pubdetails;

import static com.overmighties.pubber.feature.pubdetails.DetailsViewModel.dpToPx;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.RenderEffect;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
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


import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.shape.CornerFamily;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.overmighties.pubber.R;

import com.overmighties.pubber.app.ui.NavigationBar;
import com.overmighties.pubber.app.ui.ViewPagerSlideTransformer;
import com.overmighties.pubber.app.ui.ViewPagerSliderAdapter;
import com.overmighties.pubber.feature.search.SearcherFragmentDirections;
import com.overmighties.pubber.util.DateType;


import java.util.ArrayList;

import eightbitlab.com.blurview.BlurView;
import eightbitlab.com.blurview.RenderEffectBlur;
import eightbitlab.com.blurview.RenderScriptBlur;


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

    public void onViewCreated(@NonNull View v, Bundle savedInstanceState)
    {
        NavigationBar.smoothHide(getActivity().findViewById(R.id.nav_view));
        viewModel=new ViewModelProvider(getActivity(),
                ViewModelProvider.Factory.from(DetailsViewModel.initializer)).get(DetailsViewModel.class);
        PubDetailsUiState pubDetailsUiState=viewModel.getPubDetails().getValue();

        fotki.add(R.drawable.zdjecie1);
        fotki.add(R.drawable.zdjecie2);
        fotki.add(R.drawable.zdjecie3);
        fotki.add(R.drawable.zdjecie4);
        fotki.add(R.drawable.zdjecie5);
        fotki.add(R.drawable.zdjecie6);
        BlurImageView=requireView().findViewById(R.id.blur);
        layout=(ConstraintLayout)requireView().findViewById(R.id.detail);
        //listener for closing button
        requireView().findViewById(R.id.CloseButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(getParentFragment()).navigate(DetailsFragmentDirections.actionDetailsToSearcher());
            }
        });

        SetUpPubDate(pubDetailsUiState);
        SetUpImageSlider(fotki);
    }

    private void SetUpPubDate(PubDetailsUiState pubDetailsUiState) {
        ((TextView)requireView().findViewById(R.id.name)).setText(pubDetailsUiState.getName());

        //setting open today info parameters
        if(pubDetailsUiState.getTimeOpenToday()!=null){
            if((pubDetailsUiState.getTimeOpenToday().substring(0,1)).equals("O")){ ((TextView)requireView().findViewById(R.id.TimeOTd))
                    .setTextColor( ContextCompat.getColor(getContext(), R.color.highlightOpen));
                ((TextView)requireView().findViewById(R.id.TimeOTd)).setShadowLayer(3,1.8f,1.3f,
                        ContextCompat.getColor(getContext(), R.color.highlightOpen));
            }else{
                ((TextView)requireView().findViewById(R.id.TimeOTd))
                        .setTextColor( ContextCompat.getColor(getContext(), R.color.highlightClose));
                ((TextView)requireView().findViewById(R.id.TimeOTd)).setShadowLayer(3,1.8f,1.3f,
                        ContextCompat.getColor(getContext(), R.color.highlightClose));
            }
            ((TextView)requireView().findViewById(R.id.TimeOTd)).setText(pubDetailsUiState.getTimeOpenToday());
        }


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
        popUpView.findViewById(R.id.dismissButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailImageViewPopUpWindow.dismiss();
                BlurImageView.setVisibility(View.GONE);
            }
        });

        ConstraintLayout constraintLayout=(ConstraintLayout) requireView().findViewById(R.id.ImageSlider);
        ShapeAppearanceModel shapeAppearanceModel=new ShapeAppearanceModel()
                .toBuilder()
                .setAllCorners(CornerFamily.ROUNDED,30)
                .build();
        //set params
        for(int i=0;i< fotki.size();i=i+3) {
            shapeableImageView = new ShapeableImageView(getContext());
            shapeableImageView = viewModel.CustomingBigShapeableImageView(shapeableImageView,constraintLayout, fotki.get(i),shapeAppearanceModel,i);
            //listner for popup
            shapeableImageView.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("MissingInflatedId")
                @Override
                public void onClick(View v) {
                    viewModel.takeScreenShot(layout,getContext());
                    BlurImageView.setBackground(new BitmapDrawable(getResources(),viewModel.getPubDetails().getValue().getCurrentScreen()));
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
                    shapeAppearanceModel,i+1);
            //listner for popup
            shapeableImageView.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("MissingInflatedId")
                @Override
                public void onClick(View v) {
                    viewModel.takeScreenShot(layout,getContext());
                    BlurImageView.setBackground(new BitmapDrawable(getResources(),viewModel.getPubDetails().getValue().getCurrentScreen()));
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
                    shapeAppearanceModel,i+2);
            //listner for popup
            shapeableImageView.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("MissingInflatedId")
                @Override
                public void onClick(View v) {
                    viewModel.takeScreenShot(layout,getContext());
                    BlurImageView.setBackground(new BitmapDrawable(getResources(),viewModel.getPubDetails().getValue().getCurrentScreen()));
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