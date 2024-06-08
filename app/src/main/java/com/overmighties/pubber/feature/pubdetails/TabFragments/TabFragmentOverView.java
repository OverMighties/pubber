package com.overmighties.pubber.feature.pubdetails.TabFragments;

import static com.overmighties.pubber.app.Constants.TAB_OVERVIEW_TEXTVIEW_DAY_IDS;
import static com.overmighties.pubber.app.Constants.TAB_OVERVIEW_TEXTVIEW_DAYTIME_IDS;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.TextAppearanceSpan;
import android.view.View;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;
import android.net.Uri;
import androidx.core.content.ContextCompat;

import com.overmighties.pubber.R;
import com.overmighties.pubber.core.network.model.DrinkDto;
import com.overmighties.pubber.core.network.model.OpeningHoursDto;
import com.overmighties.pubber.feature.pubdetails.DetailsViewModel;
import com.overmighties.pubber.feature.pubdetails.PubDetailsUiState;
import com.overmighties.pubber.feature.pubdetails.TabFragments.TabFragmentsUiState;
import com.overmighties.pubber.feature.pubdetails.TabFragments.TabFragmentsViewModel;
import com.overmighties.pubber.util.DayOfWeekConverter;
import com.overmighties.pubber.util.RatingToIVConverter;

import java.util.ArrayList;


public class TabFragmentOverView extends Fragment {
    private TabFragmentsViewModel fragmentsViewModel;
    private DetailsViewModel pubViewModel;
    private ArrayList<DrinkDto> drinksDataSet1 = new ArrayList<>();
    private ArrayList <OpeningHoursDto> fakeOpeningHours = new ArrayList<>();
    public TabFragmentOverView() {super(R.layout.fragment_tab_over_view);}

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        pubViewModel=new ViewModelProvider(getActivity(),
                ViewModelProvider.Factory.from(DetailsViewModel.initializer)).get(DetailsViewModel.class);
        PubDetailsUiState pubDetailsUiState=pubViewModel.getPubDetails().getValue();
        fragmentsViewModel=new ViewModelProvider(getActivity(),
                ViewModelProvider.Factory.from(TabFragmentsViewModel.initializer)).get(TabFragmentsViewModel.class);
        fragmentsViewModel.getTabFragmentsUiState().setValue(new TabFragmentsUiState());
        TabFragmentsUiState tabFragmentsUiState=fragmentsViewModel.getTabFragmentsUiState().getValue();
        initFakeData();


        SetUpData();
        SetUpFragmentsAppearance();
        SetUpTime();
        SetUpListener(tabFragmentsUiState);
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

    private void SetUpData(){
        fragmentsViewModel.SetCheckedChipsIds(requireView().findViewById(R.id.BeerListChG), requireView().findViewById(R.id.DrinksChG),drinksDataSet1);
    }

    private void SetUpFragmentsAppearance(){
        //nie działa nwm czemu, możesz spojrzeć jak będziesz miał chwile
        /*
        fragmentsViewModel.setUpGoogleTextView(requireView().findViewById(R.id.textView15),new TextAppearanceSpan(getContext(),R.style.Google_highlight_custom_red),
                new TextAppearanceSpan(getContext(),R.style.Google_highlight_custom_blue),new TextAppearanceSpan(getContext(),R.style.Google_highlight_custom_green),
                new TextAppearanceSpan(getContext(),R.style.Google_highlight_custom_yellow));

         */

        //setting-up custom google textview
        //rating
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();

        SpannableString string = new SpannableString("G");
        string.setSpan(new TextAppearanceSpan(getContext(),R.style.Google_highlight_custom_blue),0,1,0);
        spannableStringBuilder.append(string);
        string = new SpannableString("o");
        string.setSpan(new TextAppearanceSpan(getContext(),R.style.Google_highlight_custom_red),0,1,0);
        spannableStringBuilder.append(string);
        string = new SpannableString("o");
        string.setSpan(new TextAppearanceSpan(getContext(),R.style.Google_highlight_custom_yellow),0,1,0);
        spannableStringBuilder.append(string);
        string = new SpannableString("g");
        string.setSpan(new TextAppearanceSpan(getContext(),R.style.Google_highlight_custom_blue),0,1,0);
        spannableStringBuilder.append(string);
        string = new SpannableString("l");
        string.setSpan(new TextAppearanceSpan(getContext(),R.style.Google_highlight_custom_green),0,1,0);
        spannableStringBuilder.append(string);
        string = new SpannableString("e");
        string.setSpan(new TextAppearanceSpan(getContext(),R.style.Google_highlight_custom_red),0,1,0);
        spannableStringBuilder.append(string);

        ((TextView)requireView().findViewById(R.id.textView15)).setText(spannableStringBuilder);

        SetUpBeerIV(4.7f,((ConstraintLayout)requireView().findViewById(R.id.OvgoogleCl)));
        SetUpBeerIV(4.0f,((ConstraintLayout)requireView().findViewById(R.id.OvTripAdvisorCl)));
        SetUpBeerIV(4.4f,((ConstraintLayout)requireView().findViewById(R.id.OvFacebookCl)));
        //SetUpBeerIV(4.1f,((ConstraintLayout)requireView().findViewById(R.id.OvClOurRPrice)));
        //SetUpBeerIV(4.6f,((ConstraintLayout)requireView().findViewById(R.id.OvClOurRVibe)));
        //SetUpBeerIV(4.0f,((ConstraintLayout)requireView().findViewById(R.id.OvClOurRService)));
        ArrayList<ImageView> imageViews = new ArrayList<>();
        for (int i=0;i<=4;i++){
            imageViews.add(new ImageView(getContext()));
        }
        new RatingToIVConverter().Convert(imageViews, 65, requireView().findViewById(R.id.OvClOurRGeneral), 4.5f, -5,35);
        //Review
        SpannableStringBuilder spannableStringIntrestingBuilder = new SpannableStringBuilder("Najtrafniejszy komentarz z ");
        spannableStringIntrestingBuilder.append(spannableStringBuilder);
        spannableStringIntrestingBuilder.append(":");
        ((TextView)requireView().findViewById(R.id.textView31)).setText(spannableStringIntrestingBuilder);
        ((TextView)requireView().findViewById(R.id.textView31)).setTextSize(18);
        spannableStringBuilder = new SpannableStringBuilder("Najtrafniejszy komentarz z ");
        string = new SpannableString("TripAdvisor");
        string.setSpan(new TextAppearanceSpan(getContext(), R.style.TripAdvisor_highlight),0,11,0);
        spannableStringBuilder.append(string);
        spannableStringBuilder.append((":"));
        ((TextView)requireView().findViewById(R.id.textView33)).setText(spannableStringBuilder);
        ((TextView)requireView().findViewById(R.id.textView33)).setTextSize(18);
        //alcohol
        fragmentsViewModel.addUnderLineLink(requireView().findViewById(R.id.textView24),ContextCompat.getColor(getContext(),R.color.highlight));
    }

    private void SetUpTime(){
        Integer Today = DayOfWeekConverter.getByCurrentDay().getNumeric();
        for(int i=0; i<=6; i++){
            if(i+Today>7){
                ((TextView) requireView().findViewById(TAB_OVERVIEW_TEXTVIEW_DAY_IDS[i])).setText(DayOfWeekConverter.PolsihDayOfWeekConverter(i + Today-7).getNormal());
                ((TextView) requireView().findViewById(TAB_OVERVIEW_TEXTVIEW_DAYTIME_IDS[i])).setText((pubViewModel.getUiState().getValue().getOpeningHours().get(i +Today-8)).getTimeOpen() + "-" + (pubViewModel.getUiState().getValue().getOpeningHours().get(i + Today-8)).getTimeClose());
            }
            else {
                ((TextView) requireView().findViewById(TAB_OVERVIEW_TEXTVIEW_DAY_IDS[i])).setText(DayOfWeekConverter.PolsihDayOfWeekConverter(i + Today).getNormal());
                ((TextView) requireView().findViewById(TAB_OVERVIEW_TEXTVIEW_DAYTIME_IDS[i])).setText((pubViewModel.getUiState().getValue().getOpeningHours().get(i + Today-1)).getTimeOpen() + "-" + (pubViewModel.getUiState().getValue().getOpeningHours().get(i + Today-1)).getTimeClose());
            }
        }
        /*
        for(int i=0; i<=6; i++){
            if(i+Today>7){
                ((TextView) requireView().findViewById(TAB_OVERVIEW_TEXTVIEW_DAY_IDS[i])).setText(DayOfWeekConverter.PolsihDayOfWeekConverter(i + Today-7).getNormal());
                ((TextView) requireView().findViewById(TAB_OVERVIEW_TEXTVIEW_DAYTIME_IDS[i])).setText((fakeOpeningHours.get(i +Today-8)).getTimeOpen() + "-" + (fakeOpeningHours.get(i + Today-8)).getTimeClose());
            }
            else {
                ((TextView) requireView().findViewById(TAB_OVERVIEW_TEXTVIEW_DAY_IDS[i])).setText(DayOfWeekConverter.PolsihDayOfWeekConverter(i + Today).getNormal());
                ((TextView) requireView().findViewById(TAB_OVERVIEW_TEXTVIEW_DAYTIME_IDS[i])).setText((fakeOpeningHours.get(i + Today-1)).getTimeOpen() + "-" + (fakeOpeningHours.get(i + Today-1)).getTimeClose());
            }
        }

         */
    }

    private void SetUpListener(TabFragmentsUiState tabFragmentsUiState){
        requireView().findViewById(R.id.OvUnFoldTime).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UnFoldTime();
            }
        });
        requireView().findViewById(R.id.OvTVPhoneNumber).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                String uri = "tel:" + ((TextView)requireView().findViewById(R.id.OvTVPhoneNumber)).getText().toString() ;
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse(uri));
                startActivity(intent);

                 */
            }
        });
        requireView().findViewById((R.id.OvTVWebsiteAdress)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewIntent =
                        new Intent("android.intent.action.VIEW",
                                Uri.parse(((TextView)requireView().findViewById(R.id.OvTVWebsiteAdress)).getText().toString()));
                startActivity(viewIntent);
            }
        });
        requireView().findViewById(R.id.textView24).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(requireView().findViewById(R.id.textView25).getVisibility()==View.VISIBLE){
                    ((TextView)requireView().findViewById(R.id.textView24)).setText("Wyświetl Alkohole");
                    requireView().findViewById(R.id.textView25).setVisibility(View.GONE);
                    requireView().findViewById(R.id.textView26).setVisibility(View.GONE);
                    for (Integer id:tabFragmentsUiState.getIdsOfBeerChips()){
                        requireView().findViewById(id).setVisibility(View.GONE);
                    }
                    for(Integer id:tabFragmentsUiState.getIdsOfDrinkChips()){
                        requireView().findViewById(id).setVisibility(View.GONE);
                    }


                }
                else{
                    ((TextView)requireView().findViewById(R.id.textView24)).setText("Ukryj Alkohole");
                    requireView().findViewById(R.id.textView25).setVisibility(View.VISIBLE);
                    requireView().findViewById(R.id.textView26).setVisibility(View.VISIBLE);
                    for (Integer id:tabFragmentsUiState.getIdsOfBeerChips()){
                        requireView().findViewById(id).setVisibility(View.VISIBLE);
                    }
                    for(Integer id:tabFragmentsUiState.getIdsOfDrinkChips()){
                        requireView().findViewById(id).setVisibility(View.VISIBLE);
                    }
                }
                fragmentsViewModel.addUnderLineLink(requireView().findViewById(R.id.textView24),ContextCompat.getColor(getContext(),R.color.white));

            }
        });

        requireView().findViewById(R.id.OvTVShowOurR).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (requireView().findViewById(R.id.OvClOurRating).getVisibility()==View.VISIBLE){
                    requireView().findViewById(R.id.OvClOurRating).setVisibility(View.GONE);
                    ((TextView)requireView().findViewById(R.id.OvTVShowOurR)).setText("Pokaż recenzje Pubbera");
                }
                else{
                    requireView().findViewById(R.id.OvClOurRating).setVisibility(View.VISIBLE);
                    ((TextView)requireView().findViewById(R.id.OvTVShowOurR)).setText("Ukryj recenzje Pubbera :(");
                }
            }
        });
    }

    private void UnFoldTime(){
        if(requireView().findViewById(R.id.OvTvDay1).getVisibility() == View.VISIBLE){
            ((ImageView)requireView().findViewById(R.id.OvUnFoldTime)).setImageResource(R.drawable.ic_expand_less_primary);
            for(int i=0;i<=6;i++){
                ((TextView)requireView().findViewById(TAB_OVERVIEW_TEXTVIEW_DAY_IDS[i])).setVisibility(View.GONE);
                ((TextView)requireView().findViewById(TAB_OVERVIEW_TEXTVIEW_DAYTIME_IDS[i])).setVisibility(View.GONE);
                ((TextView)requireView().findViewById(R.id.WrapContentHolder2)).setVisibility(View.GONE);

            }
        }
        else{
            ((ImageView)requireView().findViewById(R.id.OvUnFoldTime)).setImageResource(R.drawable.ic_expand_more_primary);
            for(int i=0;i<=6;i++){
                ((TextView)requireView().findViewById(TAB_OVERVIEW_TEXTVIEW_DAY_IDS[i])).setVisibility(View.VISIBLE);
                ((TextView)requireView().findViewById(TAB_OVERVIEW_TEXTVIEW_DAYTIME_IDS[i])).setVisibility(View.VISIBLE);
                ((TextView)requireView().findViewById(R.id.WrapContentHolder2)).setVisibility(View.INVISIBLE);

            }
        }
    }
    private void SetUpBeerIV(float rating, ConstraintLayout constraintLayout){
        ArrayList<ImageView> imageViews = new ArrayList<>();
        for (int i=0;i<=4;i++){
            imageViews.add(new ImageView(getContext()));
        }

        new RatingToIVConverter().Convert(imageViews, 50, constraintLayout, rating, -7,20);
    }


}