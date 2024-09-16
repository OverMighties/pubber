package com.overmighties.pubber.feature.pubdetails;

import static com.overmighties.pubber.app.Constants.TAB_OVERVIEW_TEXTVIEW_DAYTIME_IDS;
import static com.overmighties.pubber.app.Constants.TAB_OVERVIEW_TEXTVIEW_DAY_IDS;
import static com.overmighties.pubber.core.data_test.TestRepoPubsDataSet.LOREM_IPSUM_20;
import static com.overmighties.pubber.core.data_test.TestRepoPubsDataSet.LOREM_IPSUM_200;
import static com.overmighties.pubber.feature.pubdetails.DetailsViewModel.dpToPx;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
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

import com.overmighties.pubber.app.designsystem.ViewPagerSlideTransformer;
import com.overmighties.pubber.app.designsystem.ViewPagerSliderAdapter;
import com.overmighties.pubber.feature.pubdetails.chipsfragments.DetailsDrinkListAdapter;
import com.overmighties.pubber.feature.pubdetails.stateholders.DetailsCommentCardViewUiState;
import com.overmighties.pubber.feature.pubdetails.stateholders.PubDetailsUiState;
import com.overmighties.pubber.feature.search.PubListViewModel;
import com.overmighties.pubber.feature.search.util.PriceType;
import com.overmighties.pubber.util.DayOfWeekConverter;
import com.overmighties.pubber.util.DimensionsConverter;
import com.overmighties.pubber.util.RatingToIVConverter;


import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public class DetailsFragment extends Fragment
{
    public static final String TAG = "DetailsFragment";

    private ImageView BlurImageView;
    private final List<Integer> testPhotos =new ArrayList<>();
    private final static List<DetailsCommentCardViewUiState> testComments = new ArrayList<>();
    private DetailsViewModel viewModel;
    private PubListViewModel pubListViewModel;

    public DetailsFragment() {super(R.layout.details);}
    private final List<Integer> idList=new ArrayList<>();

    public ViewPager viewPager;
    private ConstraintLayout layout;
    private ShapeableImageView shapeableImageView;
    private DetailsCommentsAdapter adapter;

    @Override
    public void onViewCreated(@NonNull View v, Bundle savedInstanceState)
    {
        pubListViewModel = new ViewModelProvider(this,
                ViewModelProvider.Factory.from(PubListViewModel.initializer))
                .get(PubListViewModel.class);
        viewModel=new ViewModelProvider(requireActivity(),
                ViewModelProvider.Factory.from(DetailsViewModel.initializer)).get(DetailsViewModel.class);

        if(requireActivity().findViewById(R.id.bottom_nav_view).getVisibility() == View.INVISIBLE || requireActivity().findViewById(R.id.bottom_nav_view).getVisibility() == View.VISIBLE)
            requireActivity().findViewById(R.id.bottom_nav_view).setVisibility(View.GONE);

        PubDetailsUiState pubDetailsUiState= DetailsViewModel.getPubDetails().getValue();
        viewModel.setUiState(pubDetailsUiState);
        //test data
        prepareFakeData();
        BlurImageView=requireView().findViewById(R.id.blur);
        layout= requireView().findViewById(R.id.detail);


        setUpPubData(pubDetailsUiState);
        setUpImageSlider();
        setUpListener();
        setUpBottomExpandableLayout();

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
        if(pubDetailsUiState.getName()!=null)
            ((TextView)requireView().findViewById(R.id.name)).setText(pubDetailsUiState.getName());
        //setting open today info parameters
        if(pubDetailsUiState.getTimeOpenToday()!=null){
            if(pubDetailsUiState.getTimeOpenToday().charAt(0) == 'O'){ ((TextView)requireView().findViewById(R.id.OvTVTime))
                    .setTextColor( ContextCompat.getColor(getContext(), R.color.highlight_open));
                ((TextView)requireView().findViewById(R.id.OvTVTime)).setShadowLayer(1,0.5f,0.5f,
                        ContextCompat.getColor(getContext(), R.color.highlight_open));
            }else{
                ((TextView)requireView().findViewById(R.id.OvTVTime))
                        .setTextColor( ContextCompat.getColor(getContext(), R.color.highlight_close));
                ((TextView)requireView().findViewById(R.id.OvTVTime)).setShadowLayer(1,0.5f,0.5f,
                        ContextCompat.getColor(getContext(), R.color.highlight_close));
            }
            ((TextView)requireView().findViewById(R.id.OvTVTime)).setText(pubDetailsUiState.getTimeOpenToday());
        }
        //set up rating and rating's iv
        if(pubDetailsUiState.getRatings() != null) {
            ((TextView) requireView().findViewById(R.id.PubRating)).setText(pubDetailsUiState.getRatings().getAverageRating().toString());
            List<ImageView> imageViews = new ArrayList<>();
            imageViews.add(new ImageView(getContext()));
            imageViews.add(new ImageView(getContext()));
            imageViews.add(new ImageView(getContext()));
            imageViews.add(new ImageView(getContext()));
            imageViews.add(new ImageView(getContext()));
            new RatingToIVConverter().convert(imageViews, 32, requireView().findViewById(R.id.PubRatingIV),
                    pubDetailsUiState.getRatings().getAverageRating(), 11, 17);
            ((TextView) requireView().findViewById(R.id.PubRatingCount)).setText("(" + pubDetailsUiState.getRatings().getRatingsCount() + ")");
        }
        //set up pub's price
        if(PriceType.getById(Math.toIntExact(pubDetailsUiState.getId())) != null)
            ((TextView)requireView().findViewById(R.id.PubPrice)).setText(PriceType.getById(Math.toIntExact(pubDetailsUiState.getId())).getIcon() + " • ");
        //distance
        if(pubDetailsUiState.getDistance()!=null){
            ((TextView)requireView().findViewById(R.id.PubDistance)).setText(" "+String.valueOf(pubDetailsUiState.getDistance())+"km");
        }
        else{
            ((TextView)requireView().findViewById(R.id.PubDistance)).setText(getString(R.string.not_calculated));
            ((TextView)requireView().findViewById(R.id.PubDistance)).setTextColor(ContextCompat.getColor(requireContext(), R.color.primary));
        }
        //additiona info
        if(pubDetailsUiState.getReservable() != null && pubDetailsUiState.getReservable()){
            ((TextView)requireView().findViewById(R.id.PubReserve)).setTextColor(ContextCompat.getColor(requireContext(), R.color.highlight_open));
            ((TextView)requireView().findViewById(R.id.PubReserve)).setShadowLayer(1,0.5f,0.5f,
                    ContextCompat.getColor(getContext(), R.color.highlight_open));;
        }
        else{
            ((TextView)requireView().findViewById(R.id.PubReserve)).setTextColor(ContextCompat.getColor(requireContext(), R.color.highlight_close));
            ((TextView)requireView().findViewById(R.id.PubReserve)).setShadowLayer(1,0.5f,0.5f,
                    ContextCompat.getColor(getContext(), R.color.highlight_close));;
        }


        //chips
        if (pubDetailsUiState.getDrinks() != null) {
            for (var alcohol : pubDetailsUiState.getDrinks()) {
                if(alcohol.getType().equals("Beer")){
                    FlexboxLayout beerLayout = ((FlexboxLayout)requireView().findViewById(R.id.chipGroupBeersLayout));
                    View alcoholChipView =  LayoutInflater.from(requireContext()).inflate(R.layout.alcohol_view_chip, beerLayout, false);

                    ((Chip) alcoholChipView.findViewById(R.id.chipAlcohol)).setText(alcohol.getName());
                    String styles = null;
                    if(alcohol.getDrinkStyles() != null && !alcohol.getDrinkStyles().isEmpty()){
                        styles="";
                        for(var style: alcohol.getDrinkStyles()){
                            if(alcohol.getDrinkStyles().get(alcohol.getDrinkStyles().size()-1).getName().equals(style.getName())) {
                                styles = styles + style.getName();
                            }
                            else{
                                styles = styles + style.getName() + ", ";
                            }
                        }
                        ((TextView)alcoholChipView.findViewById(R.id.textViewAlcoholStyles)).setText(styles);
                    }
                    else{
                        ((TextView)alcoholChipView.findViewById(R.id.textViewAlcoholStyles)).setVisibility(View.GONE);
                    }
                    beerLayout.addView(alcoholChipView);
                }
                else{
                    Chip chip = new Chip(requireContext());
                    chip.setText(alcohol.getName());
                    chip.setPadding(16, 0, 16, 0);
                    chip.setChipCornerRadius(DimensionsConverter.pxFromDp(requireContext(), 8));
                    chip.setChipBackgroundColorResource(R.color.surface_container_highest);
                    chip.setTextColor(requireContext().getResources().getColor(R.color.on_surface_variant));
                    chip.setTextSize(14);
                    chip.setChipStrokeColorResource(R.color.outline);
                    chip.setChipStrokeWidth(DimensionsConverter.pxFromDp(requireContext(), 0.7F));
                    ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
                    chip.setLayoutParams(params);
                    ((ChipGroup)requireView().findViewById(R.id.chipGroupDrinks)).addView(chip);
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
            ((ChipGroup)requireView().findViewById(R.id.chipGroupTags)).addView(chip);
        }
        
        //Comments List
        adapter = new DetailsCommentsAdapter(testComments);
        ((RecyclerView)requireView().findViewById(R.id.commentsRecyclerView)).setAdapter(adapter);

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
        viewPager=popUpView.findViewById(R.id.viewPager);
        viewPager.setAdapter(new ViewPagerSliderAdapter(testPhotos));
        viewPager.setPageTransformer(true,new ViewPagerSlideTransformer());

        final PopupWindow DetailImageViewPopUpWindow = new PopupWindow(popUpView,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT, true);
        DetailImageViewPopUpWindow.setOnDismissListener(() -> BlurImageView.setVisibility(View.GONE));
        popUpView.findViewById(R.id.dismissButton).setOnClickListener(v -> DetailImageViewPopUpWindow.dismiss());

        ConstraintLayout constraintLayout= requireView().findViewById(R.id.ImageSlider);
        ShapeAppearanceModel shapeAppearanceModel=new ShapeAppearanceModel()
                .toBuilder()
                .setAllCorners(CornerFamily.ROUNDED,30)
                .build();
        //set params
        for(int i=0;i< testPhotos.size();i+=3) {
            shapeableImageView = new ShapeableImageView(requireContext());
            shapeableImageView = viewModel.CustomingBigShapeableImageView(shapeableImageView,constraintLayout, testPhotos.get(i),shapeAppearanceModel,i, getContext());
            //listner for popup
            shapeableImageView.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("MissingInflatedId")
                @Override
                public void onClick(View v) {
                    viewModel.takeScreenShot(layout,getContext());
                    BlurImageView.setBackground(new BitmapDrawable(getResources(), DetailsViewModel.getPubDetails().getValue().getCurrentScreen()));
                    BlurImageView.setVisibility(View.VISIBLE);

                    (requireActivity().findViewById(R.id.detail))
                            .post(() -> DetailImageViewPopUpWindow.showAtLocation(requireActivity().findViewById(R.id.detail), Gravity.BOTTOM, 0, 0));
                    viewPager=popUpView.findViewById(R.id.viewPager);
                    int n=0;
                    for(Integer photoId:testPhotos)
                    {
                        if( ContextCompat.getDrawable(requireContext(), photoId).getConstantState()==v.getBackground().getConstantState()){
                            break;
                        }
                        n++;
                    }

                    viewPager.setCurrentItem(n,false);



                }
            });
            //next ImageView
            shapeableImageView = new ShapeableImageView(getContext());
            shapeableImageView=viewModel.CustomingSmallShapeableImageView(shapeableImageView,constraintLayout, testPhotos.get(i+1),
                    shapeAppearanceModel,i+1, getContext());
            //listener for popup
            shapeableImageView.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("MissingInflatedId")
                @Override
                public void onClick(View v) {
                    viewModel.takeScreenShot(layout,getContext());
                    BlurImageView.setBackground(new BitmapDrawable(getResources(), DetailsViewModel.getPubDetails().getValue().getCurrentScreen()));
                    BlurImageView.setVisibility(View.VISIBLE);

                    (requireActivity().findViewById(R.id.detail))
                            .post(() -> DetailImageViewPopUpWindow.showAtLocation(requireActivity().findViewById(R.id.detail), Gravity.BOTTOM, 0, 0));
                    viewPager=popUpView.findViewById(R.id.viewPager);

                    int n=0;
                    for(int fotka:testPhotos)
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
            shapeableImageView=viewModel.CustomingSmallShapeableImageView(shapeableImageView,constraintLayout, testPhotos.get(i+2),
                    shapeAppearanceModel,i+2, getContext());
            //listener for popup
            shapeableImageView.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("MissingInflatedId")
                @Override
                public void onClick(View v) {
                    viewModel.takeScreenShot(layout,getContext());
                    BlurImageView.setBackground(new BitmapDrawable(getResources(), DetailsViewModel.getPubDetails().getValue().getCurrentScreen()));
                    BlurImageView.setVisibility(View.VISIBLE);

                    (requireActivity().findViewById(R.id.detail))
                            .post(() -> DetailImageViewPopUpWindow.showAtLocation(requireActivity().findViewById(R.id.detail), Gravity.BOTTOM, 0, 0));
                    viewPager=popUpView.findViewById(R.id.viewPager);

                    int n=0;
                    for(int fotka:testPhotos)
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
        constraintSet.connect(shapeableImageView.getId(), ConstraintSet.START, constraintLayout.getId(), ConstraintSet.START, 480+(testPhotos.size()-3)*(280));
        constraintSet.connect(shapeableImageView.getId(), ConstraintSet.TOP, constraintLayout.getId(), ConstraintSet.TOP, dpToPx(140));
        constraintSet.applyTo(constraintLayout);
    }


    private void setUpListener(){

        requireView().findViewById(R.id.CloseButton).setOnClickListener(v1 -> NavHostFragment.findNavController(getParentFragment()).navigate(DetailsFragmentDirections.actionDetailsToSearcher()));

        requireView().findViewById(R.id.chipGuide).setOnClickListener(v->{
            Uri adress = Uri.parse("geo:0,0?q="+viewModel.getUiState().getValue().getAddress());
            Intent intent = new Intent(Intent.ACTION_VIEW, adress);
            intent.setPackage("com.google.android.apps.maps");
            try {
                startActivity(intent);
            }catch (ActivityNotFoundException e) {
                Log.e(TAG,"Activity not found ");
            }
        });

        requireView().findViewById(R.id.chipCall).setOnClickListener(v->{
            if(viewModel.getUiState().getValue().getPhoneNumber()!=null) {
                String uri = "tel:" + viewModel.getUiState().getValue().getPhoneNumber();
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse(uri));
                startActivity(intent);
            }
        });

        requireView().findViewById(R.id.chipRate).setOnClickListener(v->{
            NavHostFragment.findNavController(getParentFragment()).navigate(DetailsFragmentDirections.actionDetailsFragmentToDetailsRateFragment());
        });

        requireView().findViewById(R.id.chipEdit).setOnClickListener(v->{
            View bottomSheetView = getLayoutInflater().inflate(R.layout.details_bottom_sheet_edit, null);
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(requireContext());
            bottomSheetDialog.setContentView(bottomSheetView);
            bottomSheetDialog.findViewById(R.id.linear_layout_alcohols).setOnClickListener(v1->{
                bottomSheetDialog.hide();
                NavHostFragment.findNavController(getParentFragment()).navigate(DetailsFragmentDirections.actionDetailsFragmentToDetailsEditFragment());
            });
            bottomSheetDialog.show();
        });

        requireView().findViewById(R.id.chipShare).setOnClickListener(v->{
            String link = "https://pubber-4e5c8.firebaseapp.com/pub/" + viewModel.getUiState().getValue().getCity() + "/" +  viewModel.getUiState().getValue().getId().toString();

            Intent shareIntent = ShareCompat.IntentBuilder.from(requireActivity())
                    .setType("text/plain")
                    .setText(link)
                    .getIntent();
            if(shareIntent.resolveActivity(requireContext().getPackageManager()) != null){
                startActivity(shareIntent);
            }

        });

        requireView().findViewById(R.id.expandRatingCardView).setOnClickListener(v->{
            NavHostFragment.findNavController(getParentFragment()).navigate(DetailsFragmentDirections.actionDetailsFragmentToDetailsRateFragment());
        });

        requireView().findViewById(R.id.PubDistance).setOnClickListener(v->{
            if(viewModel.getUiState().getValue().getDistance()==null){
                //TODO show ad and then calculate distance to that pub
            }
        });


        requireView().findViewById(R.id.OvUnFoldTime).setOnClickListener(v-> UnFoldTime());

        requireView().findViewById(R.id.OvTVAdress).setOnLongClickListener(v->{
            setClipboard(getContext(), ((TextView) requireView().findViewById(R.id.OvTVAdress)).getText().toString());
            return false;
        });

        requireView().findViewById(R.id.OvTVPhoneNumber).setOnClickListener(v-> {
                if (((TextView)requireView().findViewById(R.id.OvTVPhoneNumber)).getText().toString().length() == 11) {
                    String uri = "tel:" + ((TextView)requireView().findViewById(R.id.OvTVPhoneNumber)).getText().toString() ;
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse(uri));
                    startActivity(intent);
                }
        });

        requireView().findViewById(R.id.OvTVPhoneNumber).setOnLongClickListener(v->{
            if (((TextView)requireView().findViewById(R.id.OvTVPhoneNumber)).getText().toString().length() == 11) {
                setClipboard(getContext(), ((TextView) requireView().findViewById(R.id.OvTVPhoneNumber)).getText().toString());
            }
            return  false;
        });

        requireView().findViewById((R.id.OvTVWebsiteAdress)).setOnClickListener(v->{
            if (viewModel.getUiState().getValue().getWebsiteUrl() != null) {
                Intent viewIntent =
                        new Intent("android.intent.action.VIEW",
                                Uri.parse(((TextView) requireView().findViewById(R.id.OvTVWebsiteAdress)).getText().toString()));
                startActivity(viewIntent);
            }
        });

        requireView().findViewById(R.id.OvTVWebsiteAdress).setOnLongClickListener(v->{
            if (viewModel.getUiState().getValue().getWebsiteUrl() != null) {
                setClipboard(getContext(), ((TextView) requireView().findViewById(R.id.OvTVWebsiteAdress)).getText().toString());
            }
            return  false;
        });

    }

    private void UnFoldTime(){
        if(requireView().findViewById(R.id.OvTvDay1).getVisibility() == View.VISIBLE){
            ((ImageView)requireView().findViewById(R.id.OvUnFoldTime)).setImageResource(R.drawable.ic_expand_more_on_surface_variation);
            for(int i=0;i<=6;i++){
                requireView().findViewById(TAB_OVERVIEW_TEXTVIEW_DAY_IDS[i]).setVisibility(View.GONE);
                requireView().findViewById(TAB_OVERVIEW_TEXTVIEW_DAYTIME_IDS[i]).setVisibility(View.GONE);
            }
        }
        else{
            ((ImageView)requireView().findViewById(R.id.OvUnFoldTime)).setImageResource(R.drawable.ic_expand_less_on_surface_variation);
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

    private void setUpBottomExpandableLayout(){
        ConstraintLayout expandableView = requireView().findViewById(R.id.detailsExpandableView);
        ((ScrollView)requireView().findViewById(R.id.detailsScrollView)).setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if(DimensionsConverter.dpFromPx(requireContext(), scrollY)<=48F){
                    ConstraintLayout.LayoutParams layoutParams =
                            (ConstraintLayout.LayoutParams) expandableView.getLayoutParams();
                    layoutParams.bottomMargin = (int) -DimensionsConverter.pxFromDp(requireContext(), 48)+scrollY;
                    expandableView.setLayoutParams(layoutParams);
                }
                else{
                    ConstraintLayout.LayoutParams layoutParams =
                            (ConstraintLayout.LayoutParams) expandableView.getLayoutParams();
                    layoutParams.bottomMargin = 0;
                    expandableView.setLayoutParams(layoutParams);
                }
            }
        });

        requireView().findViewById(R.id.cardViewGuideExpandable).setOnClickListener(v->{
            Uri adress = Uri.parse("geo:0,0?q="+viewModel.getUiState().getValue().getAddress());
            Intent intent = new Intent(Intent.ACTION_VIEW, adress);
            intent.setPackage("com.google.android.apps.maps");
            try {
                startActivity(intent);
            }catch (ActivityNotFoundException e) {
                Log.e(TAG,"Activity not found ");
            }
        });

        requireView().findViewById(R.id.chipCallExpandable).setOnClickListener(v->{
            if(viewModel.getUiState().getValue().getPhoneNumber()!=null) {
                String uri = "tel:" + viewModel.getUiState().getValue().getPhoneNumber();
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse(uri));
                startActivity(intent);
            }
        });

        requireView().findViewById(R.id.chipRateExpandable).setOnClickListener(v->{
            NavHostFragment.findNavController(getParentFragment()).navigate(DetailsFragmentDirections.actionDetailsFragmentToDetailsRateFragment());
        });

        requireView().findViewById(R.id.chipEditExpandable).setOnClickListener(v->{
            View bottomSheetView = getLayoutInflater().inflate(R.layout.details_bottom_sheet_edit, null);
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(requireContext());
            bottomSheetDialog.setContentView(bottomSheetView);
            bottomSheetDialog.findViewById(R.id.linear_layout_alcohols).setOnClickListener(v1->{
                bottomSheetDialog.hide();
                NavHostFragment.findNavController(getParentFragment()).navigate(DetailsFragmentDirections.actionDetailsFragmentToDetailsEditFragment());
            });
            bottomSheetDialog.show();
        });

        requireView().findViewById(R.id.chipShareExpandable).setOnClickListener(v->{
            String link = "https://pubber-4e5c8.firebaseapp.com/pub/" + viewModel.getUiState().getValue().getCity() + "/" +  viewModel.getUiState().getValue().getId().toString();

            Intent shareIntent = ShareCompat.IntentBuilder.from(requireActivity())
                    .setType("text/plain")
                    .setText(link)
                    .getIntent();
            if(shareIntent.resolveActivity(requireContext().getPackageManager()) != null){
                startActivity(shareIntent);
            }

        });
    }


}