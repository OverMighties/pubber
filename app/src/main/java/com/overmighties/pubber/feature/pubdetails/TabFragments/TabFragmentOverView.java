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
import android.util.Log;
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
import com.overmighties.pubber.util.DayOfWeekConverter;
import com.overmighties.pubber.util.RatingToIVConverter;

import java.util.ArrayList;
import java.util.Objects;


public class TabFragmentOverView extends Fragment {
    public static final String TAG="TabFragmentOverView";
    private TabFragmentsViewModel fragmentsViewModel;
    private DetailsViewModel detailsViewModel;
    private ArrayList<DrinkDto> drinksDataSet1 = new ArrayList<>();
    private ArrayList <OpeningHoursDto> fakeOpeningHours = new ArrayList<>();
    public TabFragmentOverView() {super(R.layout.fragment_tab_over_view);}

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        detailsViewModel =new ViewModelProvider(requireActivity(),
                ViewModelProvider.Factory.from(DetailsViewModel.initializer)).get(DetailsViewModel.class);
        PubDetailsUiState pubDetailsUiState= detailsViewModel.getPubDetails().getValue();
        fragmentsViewModel=new ViewModelProvider(requireActivity(),
                ViewModelProvider.Factory.from(TabFragmentsViewModel.initializer)).get(TabFragmentsViewModel.class);
        fragmentsViewModel.getTabFragmentsUiState().setValue(new TabFragmentsUiState());
        TabFragmentsUiState tabFragmentsUiState=fragmentsViewModel.getTabFragmentsUiState().getValue();
        initFakeData();


        SetUpFragmentsAppearance();
        setUpFakeData();
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

    private void setUpFakeData(){
        fragmentsViewModel.SetCheckedChipsIds(requireView().findViewById(R.id.BeerListChG), requireView().findViewById(R.id.DrinksChG),drinksDataSet1);
    }

    private void SetUpFragmentsAppearance(){

        //setting-up custom google textview
        //rating
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();

        SpannableString string = new SpannableString("");


        //((TextView)requireView().findViewById(R.id.textView15)).setText(spannableStringBuilder);
        fragmentsViewModel.setUpGoogleTextView(requireView().findViewById(R.id.textView15), new TextAppearanceSpan(requireContext(), R.style.Google_highlight_custom_red),
                new TextAppearanceSpan(requireContext(), R.style.Google_highlight_custom_blue),new TextAppearanceSpan(requireContext(), R.style.Google_highlight_custom_green),
                new TextAppearanceSpan(requireContext(), R.style.Google_highlight_custom_yellow), new TextAppearanceSpan(requireContext(), R.style.Google_highlight_custom_blue),
                new TextAppearanceSpan(requireContext(), R.style.Google_highlight_custom_red));

        SetUpBeerIV(4.6f,((ConstraintLayout)requireView().findViewById(R.id.OvAverageCl)));
        SetUpBeerIV(4.7f,((ConstraintLayout)requireView().findViewById(R.id.OvgoogleCl)));
        SetUpBeerIV(4.0f,((ConstraintLayout)requireView().findViewById(R.id.OvTripAdvisorCl)));
        SetUpBeerIV(4.4f,((ConstraintLayout)requireView().findViewById(R.id.OvFacebookCl)));
        //SetUpBeerIV(4.1f,((ConstraintLayout)requireView().findViewById(R.id.OvClOurRPrice)));
        //SetUpBeerIV(4.6f,((ConstraintLayout)requireView().findViewById(R.id.OvClOurRVibe)));
        //SetUpBeerIV(4.0f,((ConstraintLayout)requireView().findViewById(R.id.OvClOurRService)));
        ArrayList<ImageView> imageViews = new ArrayList<>();
        for (int i=0;i<=4;i++){
            imageViews.add(new ImageView(requireContext()));
        }
       // new RatingToIVConverter().Convert(imageViews, 65, requireView().findViewById(R.id.OvClOurRGeneral), 4.5f, -5,35);
        //Review
        SpannableStringBuilder spannableStringIntrestingBuilder = new SpannableStringBuilder("Najtrafniejszy komentarz z ");
        spannableStringIntrestingBuilder.append(spannableStringBuilder);
        spannableStringIntrestingBuilder.append(":");
   //     ((TextView)requireView().findViewById(R.id.OvTvACom)).setText(spannableStringIntrestingBuilder);
    //    ((TextView)requireView().findViewById(R.id.OvTvACom)).setTextSize(18);
        spannableStringBuilder = new SpannableStringBuilder("Najtrafniejszy komentarz z ");
        string = new SpannableString("TripAdvisor");
        string.setSpan(new TextAppearanceSpan(requireContext(), R.style.TripAdvisor_highlight),0,11,0);
        spannableStringBuilder.append(string);
        spannableStringBuilder.append((":"));
   //     ((TextView)requireView().findViewById(R.id.textView33)).setText(spannableStringBuilder);
    //    ((TextView)requireView().findViewById(R.id.textView33)).setTextSize(18);
        //alcohol
        fragmentsViewModel.addUnderLineLink(requireView().findViewById(R.id.textView24),ContextCompat.getColor(requireContext(),R.color.highlight));
        //Coms Constrains layout
        /*
        fragmentsViewModel.setUpGoogleTextView(requireView().findViewById(R.id.OvTvComG), new TextAppearanceSpan(requireContext(), R.style.Google_highlight_custom_red),
                new TextAppearanceSpan(requireContext(), R.style.Google_highlight_custom_blue),new TextAppearanceSpan(requireContext(), R.style.Google_highlight_custom_green),
                new TextAppearanceSpan(requireContext(), R.style.Google_highlight_custom_yellow), new TextAppearanceSpan(requireContext(), R.style.Google_highlight_custom_blue),
                new TextAppearanceSpan(requireContext(), R.style.Google_highlight_custom_red));

         */
    }

    private void SetUpTime(){
        Integer Today = DayOfWeekConverter.getByCurrentDay().getNumeric();
        for(int i=0; i<=6; i++){
            if(i+Today>7){
                ((TextView) requireView().findViewById(TAB_OVERVIEW_TEXTVIEW_DAY_IDS[i])).setText(DayOfWeekConverter.polishDayOfWeekConverter(i + Today-7).getNormal());
                ((TextView) requireView().findViewById(TAB_OVERVIEW_TEXTVIEW_DAYTIME_IDS[i])).setText((detailsViewModel.getUiState().getValue().getOpeningHours().get(i +Today-8)).getTimeOpen() + "-" + (detailsViewModel.getUiState().getValue().getOpeningHours().get(i + Today-8)).getTimeClose());
            }
            else {
                ((TextView) requireView().findViewById(TAB_OVERVIEW_TEXTVIEW_DAY_IDS[i])).setText(DayOfWeekConverter.polishDayOfWeekConverter(i + Today).getNormal());
                ((TextView) requireView().findViewById(TAB_OVERVIEW_TEXTVIEW_DAYTIME_IDS[i])).setText((detailsViewModel.getUiState().getValue().getOpeningHours().get(i + Today-1)).getTimeOpen() + "-" + (detailsViewModel.getUiState().getValue().getOpeningHours().get(i + Today-1)).getTimeClose());
            }
        }
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
                if (((TextView)requireView().findViewById(R.id.OvTVPhoneNumber)).getText().toString().length() == 11)
                {
                    String uri = "tel:" + ((TextView)requireView().findViewById(R.id.OvTVPhoneNumber)).getText().toString() ;
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse(uri));
                    startActivity(intent);
                }
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
                fragmentsViewModel.addUnderLineLink(requireView().findViewById(R.id.textView24),ContextCompat.getColor(requireContext(),R.color.white));

            }
        });


        //fragmentsViewModel.adjustTextViewSize((TextView) requireView().findViewById(R.id.OvTvCom), "Bardzo przyjemne miejsce do spędzenia czasu wieczorem. Posiada ogromny asortyment gier planszowych i każdy znajdzie w nich coś dla siebie, żeby miło spędzić czas. Możliwe do zamówienia tylko 3 piwa lane z beczki, raczej typowe, oprócz tego bar posiada w swoim asortymencie najczęściej w miarę dużą ilość piw kraftowych w butelkach, więc myśle, że każdy piwosz znajdzie też coś dla siebie. Jest też możliwość zamówienia drinków i szotów, są one przyzwoite. Karta dań rozbudowana, głównie o typowe barowe przekąski, wykonanie normalne, jeśli ktoś byłby głodny znajdzie coś dla siebie.");
    }

    private void UnFoldTime(){
        if(requireView().findViewById(R.id.OvTvDay1).getVisibility() == View.VISIBLE){
            ((ImageView)requireView().findViewById(R.id.OvUnFoldTime)).setImageResource(R.drawable.ic_expand_more_primary);
            for(int i=0;i<=6;i++){
                ((TextView)requireView().findViewById(TAB_OVERVIEW_TEXTVIEW_DAY_IDS[i])).setVisibility(View.GONE);
                ((TextView)requireView().findViewById(TAB_OVERVIEW_TEXTVIEW_DAYTIME_IDS[i])).setVisibility(View.GONE);
                ((TextView)requireView().findViewById(R.id.WrapContentHolder2)).setVisibility(View.GONE);

            }
        }
        else{
            ((ImageView)requireView().findViewById(R.id.OvUnFoldTime)).setImageResource(R.drawable.ic_expand_less_primary);
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
            imageViews.add(new ImageView(requireContext()));
        }

        new RatingToIVConverter().Convert(imageViews, 36, constraintLayout, rating, 0,18);
    }


}