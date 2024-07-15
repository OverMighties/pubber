package com.overmighties.pubber.feature.pubdetails;

import static com.overmighties.pubber.app.Constants.TAB_OVERVIEW_TEXTVIEW_DAYTIME_IDS;
import static com.overmighties.pubber.app.Constants.TAB_OVERVIEW_TEXTVIEW_DAY_IDS;
import static com.overmighties.pubber.feature.pubdetails.DetailsViewModel.dpToPx;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.TextAppearanceSpan;
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


import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.shape.CornerFamily;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.overmighties.pubber.R;

import com.overmighties.pubber.app.ui.NavigationBar;
import com.overmighties.pubber.app.ui.ViewPagerSlideTransformer;
import com.overmighties.pubber.app.ui.ViewPagerSliderAdapter;
import com.overmighties.pubber.core.network.model.DrinkDto;
import com.overmighties.pubber.core.network.model.OpeningHoursDto;
import com.overmighties.pubber.util.DayOfWeekConverter;
import com.overmighties.pubber.util.RatingToIVConverter;


import java.util.ArrayList;
import java.util.List;


public class DetailsFragment extends Fragment
{

    private ImageView BlurImageView;
    private final List<Integer> testPhotos =new ArrayList<>();
    private DetailsViewModel viewModel;

    public DetailsFragment() {super(R.layout.details);}
    private final List<Integer> idList=new ArrayList<>();

    public ViewPager viewPager;
    private ConstraintLayout layout;
    private ShapeableImageView shapeableImageView;
    private final List<DrinkDto> drinksDataSet1 = new ArrayList<>();
    private final List <OpeningHoursDto> fakeOpeningHours = new ArrayList<>();

    public void onViewCreated(@NonNull View v, Bundle savedInstanceState)
    {
        NavigationBar.smoothHide(requireActivity().findViewById(R.id.bottom_nav_view));
        viewModel=new ViewModelProvider(requireActivity(),
                ViewModelProvider.Factory.from(DetailsViewModel.initializer)).get(DetailsViewModel.class);
        PubDetailsUiState pubDetailsUiState= DetailsViewModel.getPubDetails().getValue();
        viewModel.setUiState(pubDetailsUiState);
        testPhotos.add(R.drawable.test_photo_1);
        testPhotos.add(R.drawable.test_photo_2);
        testPhotos.add(R.drawable.test_photo_3);
        testPhotos.add(R.drawable.test_photo_4);
        testPhotos.add(R.drawable.test_photo_5);
        testPhotos.add(R.drawable.test_photo_6);
        BlurImageView=requireView().findViewById(R.id.blur);
        layout= requireView().findViewById(R.id.detail);
        //listener for closing button
        requireView().findViewById(R.id.CloseButton).setOnClickListener(v1 -> NavHostFragment.findNavController(getParentFragment()).navigate(DetailsFragmentDirections.actionDetailsToSearcher()));


        setUpPubData(pubDetailsUiState);
        setUpImageSlider();
        setUpFragmentsAppearance();
        setUpFakeData();
        setUpTime();
        setUpListener();
    }


    private void setUpPubData(PubDetailsUiState pubDetailsUiState) {
        ((TextView)requireView().findViewById(R.id.name)).setText(pubDetailsUiState.getName());
        //setting open today info parameters
        if(pubDetailsUiState.getTimeOpenToday()!=null){
            if(pubDetailsUiState.getTimeOpenToday().charAt(0) == 'O'){ ((TextView)requireView().findViewById(R.id.TimeOTd))
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
        List<ImageView> imageViews = new ArrayList<>();
        imageViews.add(new ImageView(getContext()));
        imageViews.add(new ImageView(getContext()));
        imageViews.add(new ImageView(getContext()));
        imageViews.add(new ImageView(getContext()));
        imageViews.add(new ImageView(getContext()));
        new RatingToIVConverter().convert(imageViews, 37, requireView().findViewById(R.id.PubRatingIV), pubDetailsUiState.getRatings().getAverageRating(), 6,20);
        //setUpRating();
    }

    private void setUpRating(){
        List<ImageView> imageViews = new ArrayList<>();
        imageViews.add(new ImageView(getContext()));
        imageViews.add(new ImageView(getContext()));
        imageViews.add(new ImageView(getContext()));
        imageViews.add(new ImageView(getContext()));
        imageViews.add(new ImageView(getContext()));

      //  new RatingToIVConverter().Convert(imageViews, 35, requireView().findViewById(R.id.PubRatingIV), , 0,20);
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


    private void initFakeData(){
        fakeOpeningHours.add(new OpeningHoursDto("MONDAY","18:00","01:00"));
        fakeOpeningHours.add(new OpeningHoursDto("TUESDAY","18:00","01:00"));
        fakeOpeningHours.add(new OpeningHoursDto("WEDNESDAY","18:00","01:00"));
        fakeOpeningHours.add(new OpeningHoursDto("THURSDAY","18:00","01:00"));
        fakeOpeningHours.add(new OpeningHoursDto("FRIDAY","18:00","04:00"));
        fakeOpeningHours.add(new OpeningHoursDto("SATURDAY","18:00","04:00"));
        fakeOpeningHours.add(new OpeningHoursDto("SUNDAY","15:00","01:00"));

        drinksDataSet1.add(new DrinkDto("AleBrowar","Beer"));
        drinksDataSet1.add(new DrinkDto("Amber","Beer"));
        drinksDataSet1.add(new DrinkDto("Artezan","Beer"));
        drinksDataSet1.add(new DrinkDto("Sexonthebeach","Cocktail"));
    }

    private void setUpFakeData(){
        viewModel.setCheckedChipsIds(requireView().findViewById(R.id.BeerListChG), requireView().findViewById(R.id.DrinksChG),drinksDataSet1);
    }

    private void setUpFragmentsAppearance(){

        //setting-up custom google textview
        //rating
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        ArrayList<ImageView> imageViews = new ArrayList<>();
        for (int i=0;i<=4;i++){
            imageViews.add(new ImageView(getContext()));
        }
        //Review
        SpannableStringBuilder spannableStringIntrestingBuilder = new SpannableStringBuilder("Najtrafniejszy komentarz z ");
        spannableStringIntrestingBuilder.append(spannableStringBuilder);
        spannableStringIntrestingBuilder.append(":");
        spannableStringBuilder = new SpannableStringBuilder("Najtrafniejszy komentarz z ");
        SpannableString string = new SpannableString("TripAdvisor");
        string.setSpan(new TextAppearanceSpan(getContext(), R.style.TripAdvisor_highlight),0,11,0);
        spannableStringBuilder.append(string);
        spannableStringBuilder.append((":"));
        //alcohol
        viewModel.addUnderLineLink(requireView().findViewById(R.id.textView24),ContextCompat.getColor(getContext(),R.color.highlight));
        //Coms Constrains layout
        /*
        viewmodel.setUpGoogleTextView(requireView().findViewById(R.id.OvTvComG), new TextAppearanceSpan(getContext(), R.style.Google_highlight_custom_red),
                new TextAppearanceSpan(getContext(), R.style.Google_highlight_custom_blue),new TextAppearanceSpan(getContext(), R.style.Google_highlight_custom_green),
                new TextAppearanceSpan(getContext(), R.style.Google_highlight_custom_yellow), new TextAppearanceSpan(getContext(), R.style.Google_highlight_custom_blue),
                new TextAppearanceSpan(getContext(), R.style.Google_highlight_custom_red));

         */
    }

    private void setUpTime(){
        Integer Today = DayOfWeekConverter.getByCurrentDay().getNumeric();
        for(int i=0; i<=6; i++){
            if(i+Today>7){
                ((TextView) requireView().findViewById(TAB_OVERVIEW_TEXTVIEW_DAY_IDS[i])).setText(DayOfWeekConverter.polishDayOfWeekConverter(i + Today-7).getNormal());
                ((TextView) requireView().findViewById(TAB_OVERVIEW_TEXTVIEW_DAYTIME_IDS[i])).setText((viewModel.getUiState().getValue().getOpeningHours().get(i +Today-8)).getTimeOpen() + "-" + (viewModel.getUiState().getValue().getOpeningHours().get(i + Today-8)).getTimeClose());
            }
            else {
                ((TextView) requireView().findViewById(TAB_OVERVIEW_TEXTVIEW_DAY_IDS[i])).setText(DayOfWeekConverter.polishDayOfWeekConverter(i + Today).getNormal());
                ((TextView) requireView().findViewById(TAB_OVERVIEW_TEXTVIEW_DAYTIME_IDS[i])).setText((viewModel.getUiState().getValue().getOpeningHours().get(i + Today-1)).getTimeOpen() + "-" + (viewModel.getUiState().getValue().getOpeningHours().get(i + Today-1)).getTimeClose());
            }
        }
    }

    private void setUpListener(){
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
        requireView().findViewById(R.id.textView24).setOnClickListener(v-> {

                if(requireView().findViewById(R.id.textView25).getVisibility()==View.VISIBLE){
                    ((TextView)requireView().findViewById(R.id.textView24)).setText("Wyświetl Alkohole");
                    requireView().findViewById(R.id.textView25).setVisibility(View.GONE);
                    requireView().findViewById(R.id.textView26).setVisibility(View.GONE);
                    for (Integer id:viewModel.getUiState().getValue().getIdsOfBeerChips()){
                        requireView().findViewById(id).setVisibility(View.GONE);
                    }
                    for(Integer id:viewModel.getUiState().getValue().getIdsOfDrinkChips()){
                        requireView().findViewById(id).setVisibility(View.GONE);
                    }


                }
                else{
                    ((TextView)requireView().findViewById(R.id.textView24)).setText("Ukryj Alkohole");
                    requireView().findViewById(R.id.textView25).setVisibility(View.VISIBLE);
                    requireView().findViewById(R.id.textView26).setVisibility(View.VISIBLE);
                    for (Integer id:viewModel.getUiState().getValue().getIdsOfBeerChips()){
                        requireView().findViewById(id).setVisibility(View.VISIBLE);
                    }
                    for(Integer id:viewModel.getUiState().getValue().getIdsOfDrinkChips()){
                        requireView().findViewById(id).setVisibility(View.VISIBLE);
                    }
                }
                viewModel.addUnderLineLink(requireView().findViewById(R.id.textView24),ContextCompat.getColor(getContext(),R.color.white));

        });
    }

    private void UnFoldTime(){
        if(requireView().findViewById(R.id.OvTvDay1).getVisibility() == View.VISIBLE){
            ((ImageView)requireView().findViewById(R.id.OvUnFoldTime)).setImageResource(R.drawable.ic_expand_more_primary);
            for(int i=0;i<=6;i++){
                requireView().findViewById(TAB_OVERVIEW_TEXTVIEW_DAY_IDS[i]).setVisibility(View.GONE);
                requireView().findViewById(TAB_OVERVIEW_TEXTVIEW_DAYTIME_IDS[i]).setVisibility(View.GONE);
            }
        }
        else{
            ((ImageView)requireView().findViewById(R.id.OvUnFoldTime)).setImageResource(R.drawable.ic_expand_less_primary);
            for(int i=0;i<=6;i++){
                requireView().findViewById(TAB_OVERVIEW_TEXTVIEW_DAY_IDS[i]).setVisibility(View.VISIBLE);
                requireView().findViewById(TAB_OVERVIEW_TEXTVIEW_DAYTIME_IDS[i]).setVisibility(View.VISIBLE);
            }
        }
    }
    private void SetUpBeerIV(float rating, ConstraintLayout constraintLayout){
        ArrayList<ImageView> imageViews = new ArrayList<>();
        for (int i=0;i<=4;i++){
            imageViews.add(new ImageView(getContext()));
        }

        new RatingToIVConverter().convert(imageViews, 36, constraintLayout, rating, 0,18);
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