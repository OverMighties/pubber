package com.overmighties.pubber.feature.pubdetails;

import static com.overmighties.pubber.app.Constants.TAB_OVERVIEW_TEXTVIEW_DAYTIME_IDS;
import static com.overmighties.pubber.app.Constants.TAB_OVERVIEW_TEXTVIEW_DAY_IDS;
import static com.overmighties.pubber.core.data_test.TestRepoPubsDataSet.LOREM_IPSUM_20;
import static com.overmighties.pubber.core.data_test.TestRepoPubsDataSet.LOREM_IPSUM_200;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RenderEffect;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.util.Log;
import android.util.Pair;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.app.ShareCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;


import com.google.android.flexbox.FlexboxLayout;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.shape.CornerFamily;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.overmighties.pubber.R;

import com.overmighties.pubber.app.designsystem.AlcoholAlertDialog.AlcoholAlertDialog;
import com.overmighties.pubber.app.designsystem.AlcoholAlertDialog.AlcoholAlertDialogUiState;
import com.overmighties.pubber.app.designsystem.ViewPagerSlideTransformer;
import com.overmighties.pubber.app.designsystem.ViewPagerSliderAdapter;
import com.overmighties.pubber.feature.dictionary.DictionarySearchFragmentDirections;
import com.overmighties.pubber.feature.pubdetails.stateholders.DetailsCommentCardViewUiState;
import com.overmighties.pubber.feature.pubdetails.stateholders.PubDetailsUiState;
import com.overmighties.pubber.feature.search.PubListViewModel;
import com.overmighties.pubber.feature.search.util.PriceType;
import com.overmighties.pubber.util.DayOfWeekConverter;
import com.overmighties.pubber.util.DimensionsConverter;
import com.overmighties.pubber.util.RatingToIVConverter;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class DetailsFragment extends Fragment
{
    public static final String TAG = "DetailsFragment";

    public DetailsFragment() {super(R.layout.details);}

    private NavController navController;
    private DetailsViewModel viewModel;
    private PubListViewModel pubListViewModel;

    private final List<Integer> idList=new ArrayList<>();

    public ViewPager viewPager;
    private ImageView BlurImageView;
    private ConstraintLayout layout;
    private ShapeableImageView shapeableImageView;
    private DetailsCommentsAdapter adapter;

    private final List<Integer> testPhotos =new ArrayList<>();
    private final static List<DetailsCommentCardViewUiState> testComments = new ArrayList<>();

    @Override
    public void onViewCreated(@NonNull View v, Bundle savedInstanceState)
    {
        navController= Navigation.findNavController(requireActivity(),R.id.main_navHostFragment_container);
        pubListViewModel = new ViewModelProvider(requireActivity())
                .get(PubListViewModel.class);
        viewModel=new ViewModelProvider(requireActivity(),
                ViewModelProvider.Factory.from(DetailsViewModel.initializer)).get(DetailsViewModel.class);

        if(requireActivity().findViewById(R.id.main_bottomNavView).getVisibility() == View.INVISIBLE || requireActivity().findViewById(R.id.main_bottomNavView).getVisibility() == View.VISIBLE)
            requireActivity().findViewById(R.id.main_bottomNavView).setVisibility(View.GONE);

        PubDetailsUiState pubDetailsUiState= DetailsViewModel.getPubDetails().getValue();
        viewModel.setUiState(pubDetailsUiState);
        //test data
        prepareFakeData();
        BlurImageView=requireView().findViewById(R.id.details_image_blur);
        layout= requireView().findViewById(R.id.details_fragment);


        setUpPubData(pubDetailsUiState);
        setUpImageSlider();
        setUpListener();
    }

    private void prepareFakeData(){
        testPhotos.add(R.drawable.test_photo_1);
        testPhotos.add(R.drawable.test_photo_2);
        testPhotos.add(R.drawable.test_photo_3);
        testPhotos.add(R.drawable.test_photo_4);
        testPhotos.add(R.drawable.test_photo_5);
        testPhotos.add(R.drawable.test_photo_6);
        testComments.add(new DetailsCommentCardViewUiState("Potato1", getString(R.string.google), 3.2F, "40-60zł", LOREM_IPSUM_20));
        testComments.add(new DetailsCommentCardViewUiState("Potato2", getString(R.string.tripadvisor), 4.2F, null, LOREM_IPSUM_200));
        testComments.add(new DetailsCommentCardViewUiState("Potato3", getString(R.string.app_name), 0.0F, "40-60zł", LOREM_IPSUM_200 + LOREM_IPSUM_200));
    }


    private void setUpPubData(PubDetailsUiState pubDetailsUiState) {
        ((ImageView) requireView().findViewById(R.id.details_image_favourite)).setTag(R.drawable.ic_heart_empty);
        if(pubDetailsUiState.isFavourite()) {
            ((ImageView) requireView().findViewById(R.id.details_image_favourite)).setImageResource(R.drawable.ic_heart_full);
            ((ImageView) requireView().findViewById(R.id.details_image_favourite)).setTag(R.drawable.ic_heart_full);
        }

        if(pubDetailsUiState.getName()!=null)
            ((TextView)requireView().findViewById(R.id.details_text_name)).setText(pubDetailsUiState.getName());
        //setting open today info parameters
        if(pubDetailsUiState.getTimeOpenToday()!=null){
            if(pubDetailsUiState.getTimeOpenToday().charAt(0) == 'O'){ ((TextView)requireView().findViewById(R.id.details_text_time))
                    .setTextColor( ContextCompat.getColor(getContext(), R.color.highlight_open));
                ((TextView)requireView().findViewById(R.id.details_text_time)).setShadowLayer(1,0.5f,0.5f,
                        ContextCompat.getColor(getContext(), R.color.highlight_open));
            }else{
                ((TextView)requireView().findViewById(R.id.details_text_time))
                        .setTextColor( ContextCompat.getColor(getContext(), R.color.highlight_close));
                ((TextView)requireView().findViewById(R.id.details_text_time)).setShadowLayer(1,0.5f,0.5f,
                        ContextCompat.getColor(getContext(), R.color.highlight_close));
            }
            ((TextView)requireView().findViewById(R.id.details_text_time)).setText(pubDetailsUiState.getTimeOpenToday());
        }
        //set up rating and rating's iv
        if(pubDetailsUiState.getRatings() != null) {
            ((TextView) requireView().findViewById(R.id.details_text_rating)).setText(pubDetailsUiState.getRatings().getAverageRating().toString());
            List<ImageView> imageViews = new ArrayList<>();
            imageViews.add(new ImageView(getContext()));
            imageViews.add(new ImageView(getContext()));
            imageViews.add(new ImageView(getContext()));
            imageViews.add(new ImageView(getContext()));
            imageViews.add(new ImageView(getContext()));
            new RatingToIVConverter().convert(imageViews, 32, requireView().findViewById(R.id.details_cl_ratingImageContainer),
                    pubDetailsUiState.getRatings().getAverageRating(), 11, 17, true, requireContext());
            ((TextView) requireView().findViewById(R.id.details_text_ratingCount)).setText("(" + pubDetailsUiState.getRatings().getRatingsCount() + ")");
        }
        //set up pub's price
        if(PriceType.getById(Math.toIntExact(pubDetailsUiState.getId())) != null)
            ((TextView)requireView().findViewById(R.id.details_text_price)).setText(PriceType.getById(Math.toIntExact(pubDetailsUiState.getId())).getIcon() + " • ");
        //distance
        if(pubDetailsUiState.getDistance()!=null){
            ((TextView)requireView().findViewById(R.id.details_text_distance)).setText(" "+String.valueOf(pubDetailsUiState.getDistance())+"km");
        }
        else{
            ((TextView)requireView().findViewById(R.id.details_text_distance)).setText(getString(R.string.not_calculated));
            ((TextView)requireView().findViewById(R.id.details_text_distance)).setTextColor(ContextCompat.getColor(requireContext(), R.color.primary));
        }
        //additiona info
        if(pubDetailsUiState.getReservable() != null && pubDetailsUiState.getReservable()){
            ((TextView)requireView().findViewById(R.id.details_text_reservable)).setTextColor(ContextCompat.getColor(requireContext(), R.color.highlight_open));
            ((TextView)requireView().findViewById(R.id.details_text_reservable)).setShadowLayer(1,0.5f,0.5f,
                    ContextCompat.getColor(getContext(), R.color.highlight_open));;
        }
        else{
            ((TextView)requireView().findViewById(R.id.details_text_reservable)).setTextColor(ContextCompat.getColor(requireContext(), R.color.highlight_close));
            ((TextView)requireView().findViewById(R.id.details_text_reservable)).setShadowLayer(1,0.5f,0.5f,
                    ContextCompat.getColor(getContext(), R.color.highlight_close));;
        }


        //chips
        if (pubDetailsUiState.getDrinks() != null) {
            for (var alcohol : pubDetailsUiState.getDrinks()) {
                if(alcohol.getType().equals("Beer")){
                    FlexboxLayout beerLayout = ((FlexboxLayout)requireView().findViewById(R.id.details_FbL_beers));
                    View alcoholChipView =  LayoutInflater.from(requireContext()).inflate(R.layout.alcohol_view_chip, beerLayout, false);
                    ((Chip) alcoholChipView.findViewById(R.id.detailsEditACV_chip_alcohol)).setText(alcohol.getName());
                    String styles = null;
                    if(alcohol.getDrinkStyles() != null && !alcohol.getDrinkStyles().isEmpty()){
                        styles="";
                        for(var style: alcohol.getDrinkStyles()){
                            if(alcohol.getDrinkStyles().get(alcohol.getDrinkStyles().size()-1).getName().equals(style.getName())) {
                                styles = styles + style.getName();
                            } else {
                                styles = styles + style.getName() + ", ";
                            }
                        }
                        ((TextView)alcoholChipView.findViewById(R.id.detailsEditACV_text_styles)).setText(styles);
                    } else {
                        ((TextView)alcoholChipView.findViewById(R.id.detailsEditACV_text_styles)).setVisibility(View.GONE);
                    }
                    //TODO add dialog showing
                    beerLayout.addView(alcoholChipView);
                } else{
                    Chip chip = new Chip(requireContext());
                    chip.setText(alcohol.getName());
                    chip.setPadding(16, 0, 16, 0);
                    chip.setChipCornerRadius(DimensionsConverter.pxFromDp(requireContext(), 8));
                    chip.setChipBackgroundColorResource(R.color.surface_container_low);
                    chip.setTextColor(requireContext().getResources().getColor(R.color.on_surface_variant));
                    chip.setTextSize(14);
                    chip.setChipStrokeColorResource(R.color.outline);
                    chip.setChipStrokeWidth(DimensionsConverter.pxFromDp(requireContext(), 0.7F));
                    ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
                    chip.setLayoutParams(params);
                    //TODO add dialog showing
                    ((ChipGroup)requireView().findViewById(R.id.details_chipGroup_drinks)).addView(chip);
                };

            }
        }

        //tags
        for(var tag:pubDetailsUiState.getTags()){
            Chip chip = new Chip(requireContext());
            chip.setText(tag.getName());
            chip.setPadding(16, 0, 16, 0);
            chip.setChipCornerRadius(DimensionsConverter.pxFromDp(requireContext(), 8));
            chip.setChipBackgroundColorResource(R.color.secondary_container);
            chip.setTextColor(requireContext().getResources().getColor(R.color.on_secondary_container));
            chip.setTextSize(14);
            chip.setChipStrokeWidth(DimensionsConverter.pxFromDp(requireContext(), 0F));
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            chip.setLayoutParams(params);

            ((ChipGroup)requireView().findViewById(R.id.details_chipGroup_tags)).addView(chip);
        }
        
        //Comments List
        adapter = new DetailsCommentsAdapter(testComments);
        ((RecyclerView)requireView().findViewById(R.id.details_recyclerView_comments)).setAdapter(adapter);

        //pub's open time info
        Integer Today = DayOfWeekConverter.getByCurrentDay().getNumeric();
        if(!(viewModel.getUiState().getValue().getOpeningHours().isEmpty()))
        {
            for (int i = 0; i <= 6; i++) {
                if (i + Today > 7) {
                    ((TextView) requireView().findViewById(TAB_OVERVIEW_TEXTVIEW_DAY_IDS[i])).setText(DayOfWeekConverter.polishDayOfWeekConverter(i + Today - 7).getNormal());
                    ((TextView) requireView().findViewById(TAB_OVERVIEW_TEXTVIEW_DAYTIME_IDS[i])).setText((viewModel.getUiState().getValue().getOpeningHours().get(i + Today - 8)).getTimeOpen() + "-" + (viewModel.getUiState().getValue().getOpeningHours().get(i + Today - 8)).getTimeClose());
                } else {
                    ((TextView) requireView().findViewById(TAB_OVERVIEW_TEXTVIEW_DAY_IDS[i])).setText(DayOfWeekConverter.polishDayOfWeekConverter(i + Today).getNormal());
                    ((TextView) requireView().findViewById(TAB_OVERVIEW_TEXTVIEW_DAYTIME_IDS[i])).setText((viewModel.getUiState().getValue().getOpeningHours().get(i + Today - 1)).getTimeOpen() + "-" + (viewModel.getUiState().getValue().getOpeningHours().get(i + Today - 1)).getTimeClose());
                }
            }
        }
    }


    private void setUpImageSlider()
    {
        View popUpView = LayoutInflater.from(requireActivity()).inflate(R.layout.detail_image_pop_up, null);
        viewPager=popUpView.findViewById(R.id.detailsImage_viewPager);
        viewPager.setAdapter(new ViewPagerSliderAdapter(testPhotos));
        viewPager.setPageTransformer(true,new ViewPagerSlideTransformer());

        final PopupWindow imagePopUpWindow = new PopupWindow(popUpView,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT, true);
        imagePopUpWindow.setOnDismissListener(() -> BlurImageView.setVisibility(View.GONE));
        popUpView.findViewById(R.id.detailsImage_image_dismiss).setOnClickListener(v -> imagePopUpWindow.dismiss());

        ConstraintLayout constraintLayout= requireView().findViewById(R.id.details_cl_imageSlider);
        ShapeAppearanceModel shapeAppearanceModel=new ShapeAppearanceModel()
                .toBuilder()
                .setAllCorners(CornerFamily.ROUNDED,30)
                .build();
        //set params
        for(int i=0;i< testPhotos.size();i+=3) {
            shapeableImageView = new ShapeableImageView(requireContext());
            shapeableImageView = viewModel.CustomingBigShapeableImageView(
                    shapeableImageView,constraintLayout, testPhotos.get(i),shapeAppearanceModel,(int) DimensionsConverter.pxFromDp(requireContext(),8 + i*100));
            shapeableImageView.setOnClickListener((v)->{
                showImagePopUp(imagePopUpWindow, popUpView, v);
            });
            //next ImageView
            shapeableImageView = new ShapeableImageView(requireContext());
            shapeableImageView=viewModel.CustomingSmallShapeableImageView(
                    shapeableImageView,constraintLayout, testPhotos.get(i+1),
                    shapeAppearanceModel,0, (int) DimensionsConverter.pxFromDp(requireContext(),192+i*(100)));
            shapeableImageView.setOnClickListener((v)->{
                showImagePopUp(imagePopUpWindow, popUpView, v);
            });
            //nextImageView
            shapeableImageView = new ShapeableImageView(requireContext());
            shapeableImageView=viewModel.CustomingSmallShapeableImageView(
                    shapeableImageView,constraintLayout, testPhotos.get(i+2),
                    shapeAppearanceModel,(int)DimensionsConverter.pxFromDp(requireContext(), 140), (int) DimensionsConverter.pxFromDp(requireContext(),192+i*(100)));
            shapeableImageView.setOnClickListener((v)->{
                showImagePopUp(imagePopUpWindow, popUpView, v);
            });
        }
        shapeableImageView = new ShapeableImageView(requireContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int)DimensionsConverter.pxFromDp(requireContext(), 170), (int)DimensionsConverter.pxFromDp(requireContext(), 270));
        shapeableImageView.setLayoutParams(params);
        shapeableImageView.setId(View.generateViewId());
        constraintLayout.addView(shapeableImageView);
        ConstraintSet constraintSet=new ConstraintSet();
        constraintSet.clone(constraintLayout);
        constraintSet.connect(shapeableImageView.getId(), ConstraintSet.START, constraintLayout.getId(), ConstraintSet.START, 300+(testPhotos.size()-3)*(280));
        constraintSet.connect(shapeableImageView.getId(), ConstraintSet.TOP, constraintLayout.getId(), ConstraintSet.TOP, (int)DimensionsConverter.pxFromDp(requireContext(), 140));
        constraintSet.applyTo(constraintLayout);
    }

    public void showImagePopUp(PopupWindow imagePopUpWindow, View popUpView, View clickedView){
        (requireActivity().findViewById(R.id.details_fragment))
                .post(() -> imagePopUpWindow.showAtLocation(requireActivity().findViewById(R.id.details_fragment), Gravity.BOTTOM, 0, 0));
        viewPager=popUpView.findViewById(R.id.detailsImage_viewPager);
        int n=0;
        for(int fotka:testPhotos)
        {
            if(ResourcesCompat.getDrawable(getResources(),fotka, null).getConstantState()
                    ==clickedView.getBackground().getConstantState()){
                break;
            }
            n++;
        }
        viewPager.setCurrentItem(n,false);
    }

    private void setUpListener(){
        requireView().findViewById(R.id.details_image_favourite).setOnClickListener(v->{
            ImageView heart =  requireView().findViewById(R.id.details_image_favourite);
            if((Integer)heart.getTag()==R.drawable.ic_heart_empty) {
                heart.setImageResource(R.drawable.ic_heart_full);
                heart.setTag(R.drawable.ic_heart_full);
                pubListViewModel.getFavouritePubState().setValue(new Pair<>(viewModel.getUiState().getValue().getId(), true));
            } else {
                heart.setImageResource(R.drawable.ic_heart_empty);
                heart.setTag(R.drawable.ic_heart_empty);
                pubListViewModel.getFavouritePubState().setValue(new Pair<>(viewModel.getUiState().getValue().getId(), false));
            }
        });

        requireView().findViewById(R.id.details_image_back).setOnClickListener(v -> NavHostFragment.findNavController(getParentFragment()).popBackStack());

        requireView().findViewById(R.id.details_chip_guide).setOnClickListener(v->{
            guide();
        });

        requireView().findViewById(R.id.details_chip_call).setOnClickListener(v->{
            call();
        });

        requireView().findViewById(R.id.details_chip_rate).setOnClickListener(v->{
            NavHostFragment.findNavController(getParentFragment()).navigate(DetailsFragmentDirections.actionDetailsFragmentToDetailsRateFragment());
        });

        requireView().findViewById(R.id.details_chip_edit).setOnClickListener(v->{
            showBottomSheetEdit();
        });

        requireView().findViewById(R.id.details_chip_share).setOnClickListener(v->{
            shareLink();
        });

        requireView().findViewById(R.id.details_image_expandRating).setOnClickListener(v->{
            NavHostFragment.findNavController(getParentFragment()).navigate(DetailsFragmentDirections.actionDetailsFragmentToDetailsRateFragment());
        });

        requireView().findViewById(R.id.details_text_distance).setOnClickListener(v->{
            if(viewModel.getUiState().getValue().getDistance()==null){
                //TODO show ad and then calculate distance to that pub
            }
        });


        requireView().findViewById(R.id.details_image_expandTime).setOnClickListener(v-> UnFoldTime());

        requireView().findViewById(R.id.details_text_adress).setOnLongClickListener(v->{
            setClipboard(getContext(), ((TextView) requireView().findViewById(R.id.details_text_adress)).getText().toString());
            return false;
        });

        requireView().findViewById(R.id.details_text_phoneNumber).setOnClickListener(v-> {
                if (((TextView)requireView().findViewById(R.id.details_text_phoneNumber)).getText().toString().length() == 11) {
                    String uri = "tel:" + ((TextView)requireView().findViewById(R.id.details_text_phoneNumber)).getText().toString() ;
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse(uri));
                    startActivity(intent);
                }
        });

        requireView().findViewById(R.id.details_text_phoneNumber).setOnLongClickListener(v->{
            if (((TextView)requireView().findViewById(R.id.details_text_phoneNumber)).getText().toString().length() == 11) {
                setClipboard(getContext(), ((TextView) requireView().findViewById(R.id.details_text_phoneNumber)).getText().toString());
            }
            return  false;
        });

        requireView().findViewById((R.id.details_text_websiteAdress)).setOnClickListener(v->{
            if (viewModel.getUiState().getValue().getWebsiteUrl() != null) {
                Intent viewIntent =
                        new Intent("android.intent.action.VIEW",
                                Uri.parse(((TextView) requireView().findViewById(R.id.details_text_websiteAdress)).getText().toString()));
                startActivity(viewIntent);
            }
        });

        requireView().findViewById(R.id.details_text_websiteAdress).setOnLongClickListener(v->{
            if (viewModel.getUiState().getValue().getWebsiteUrl() != null) {
                setClipboard(getContext(), ((TextView) requireView().findViewById(R.id.details_text_websiteAdress)).getText().toString());
            }
            return  false;
        });

        HorizontalScrollView expandableView = requireView().findViewById(R.id.details_horizontalSV_chipsExpandable);
        ((ScrollView)requireView().findViewById(R.id.details_scrollView)).setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if(DimensionsConverter.dpFromPx(requireContext(), scrollY)<=48F){
                    ViewGroup.MarginLayoutParams layoutParams =
                            (ViewGroup.MarginLayoutParams) expandableView.getLayoutParams();
                    layoutParams.bottomMargin = (int) -DimensionsConverter.pxFromDp(requireContext(), 48)+scrollY;
                    expandableView.setLayoutParams(layoutParams);
                }
                else{
                    ViewGroup.MarginLayoutParams layoutParams =
                            (ViewGroup.MarginLayoutParams) expandableView.getLayoutParams();
                    layoutParams.bottomMargin = 0;
                    expandableView.setLayoutParams(layoutParams);
                }
            }
        });

        requireView().findViewById(R.id.details_cardView_guideExpandable).setOnClickListener(v->{
            guide();
        });

        requireView().findViewById(R.id.details_chip_callExpandable).setOnClickListener(v->{
            call();
        });

        requireView().findViewById(R.id.details_chip_rateExpandable).setOnClickListener(v->{
            NavHostFragment.findNavController(getParentFragment()).navigate(DetailsFragmentDirections.actionDetailsFragmentToDetailsRateFragment());
        });

        requireView().findViewById(R.id.details_chip_editExpandable).setOnClickListener(v->{
            showBottomSheetEdit();
        });

        requireView().findViewById(R.id.details_chip_shareExpandable).setOnClickListener(v->{
            shareLink();
        });

    }

    private void guide(){
        Uri adress = Uri.parse("geo:0,0?q="+viewModel.getUiState().getValue().getAddress());
        Intent intent = new Intent(Intent.ACTION_VIEW, adress);
        intent.setPackage("com.google.android.apps.maps");
        try {
            startActivity(intent);
        }catch (ActivityNotFoundException e) {
            Log.e(TAG,"Guide intent: Activity not found ");
        }
    }

    private void call(){
        if(viewModel.getUiState().getValue().getPhoneNumber()!=null) {
            String uri = "tel:" + viewModel.getUiState().getValue().getPhoneNumber();
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse(uri));
            try{
                startActivity(intent);
            }catch (ActivityNotFoundException e){
                Log.e(TAG,"Call intent: Activity not found");
            }
        }
    }

    private void showBottomSheetEdit(){
        View bottomSheetView = getLayoutInflater().inflate(R.layout.details_bottom_sheet_edit, null);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(requireContext());
        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.findViewById(R.id.detailsBSh_ll_alcohols).setOnClickListener(v->{
            bottomSheetDialog.cancel();
            NavHostFragment.findNavController(getParentFragment()).navigate(DetailsFragmentDirections.actionDetailsFragmentToDetailsEditFragment());
        });
        bottomSheetDialog.findViewById(R.id.detailsBSh_ll_tags).setOnClickListener(v->{
            bottomSheetDialog.cancel();
            NavHostFragment.findNavController(getParentFragment()).navigate(DetailsFragmentDirections.actionDetailsFragmentToDetailsEditTagsFragment());
        });
        bottomSheetView.findViewById(R.id.detailsBSh_button_cancel).setOnClickListener(v->{
            bottomSheetDialog.cancel();
        });
        bottomSheetDialog.show();
    }

    private void shareLink(){
        String link = "https://pubber-4e5c8.firebaseapp.com/pub/" + viewModel.getUiState().getValue().getCity() + "/" +  viewModel.getUiState().getValue().getId().toString();

        Intent shareIntent = ShareCompat.IntentBuilder.from(requireActivity())
                .setType("text/plain")
                .setText(link)
                .getIntent();
        if(shareIntent.resolveActivity(requireContext().getPackageManager()) != null){
            startActivity(shareIntent);
        }
    }

    private void UnFoldTime(){
        if(requireView().findViewById(R.id.details_text_day1).getVisibility() == View.VISIBLE){
            ((ImageView)requireView().findViewById(R.id.details_image_expandTime)).setImageResource(R.drawable.ic_expand_more_on_surface_variation);
            for(int i=0;i<=6;i++){
                requireView().findViewById(TAB_OVERVIEW_TEXTVIEW_DAY_IDS[i]).setVisibility(View.GONE);
                requireView().findViewById(TAB_OVERVIEW_TEXTVIEW_DAYTIME_IDS[i]).setVisibility(View.GONE);
            }
        }
        else{
            ((ImageView)requireView().findViewById(R.id.details_image_expandTime)).setImageResource(R.drawable.ic_expand_less_on_surface_variation);
            for(int i=0;i<=6;i++){
                requireView().findViewById(TAB_OVERVIEW_TEXTVIEW_DAY_IDS[i]).setVisibility(View.VISIBLE);
                requireView().findViewById(TAB_OVERVIEW_TEXTVIEW_DAYTIME_IDS[i]).setVisibility(View.VISIBLE);
            }
        }
    }


    //Coping text to phone's clipboard
    private void setClipboard(Context context, String text) {
        if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(text);
        } else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", text);
            clipboard.setPrimaryClip(clip);
        }
    }



}