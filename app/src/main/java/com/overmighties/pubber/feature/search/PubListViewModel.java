package com.overmighties.pubber.feature.search;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

import static androidx.lifecycle.SavedStateHandleSupport.createSavedStateHandle;
import static androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY;


import static java.lang.Math.ceil;

import android.content.res.Resources;
import android.util.Log;

import com.overmighties.pubber.app.PubberApp;
import com.overmighties.pubber.core.data.PubsRepository;
import com.overmighties.pubber.core.model.Pub;
import com.overmighties.pubber.feature.pubdetails.DetailsViewModel;
import com.overmighties.pubber.feature.pubdetails.PubDetailsUiState;
import com.overmighties.pubber.feature.search.stateholders.FilterUiState;
import com.overmighties.pubber.feature.search.stateholders.PubItemCardViewUiState;
import com.overmighties.pubber.feature.search.stateholders.PubsCardViewUiState;
import com.overmighties.pubber.util.DateTimetoCurrentTimeComparator;
import com.overmighties.pubber.util.DateType;
import com.overmighties.pubber.util.DayOfWeekConverter;
import com.overmighties.pubber.util.FilterUtil;
import com.overmighties.pubber.util.PriceType;
import com.overmighties.pubber.util.SortPubsBy;
import com.overmighties.pubber.util.SortUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.disposables.Disposable;
import lombok.Getter;

public class PubListViewModel extends ViewModel {
    public static final String TAG="PubListViewModel";
    public static final Float TEMPORARY_DISTANCE=20.f;
    public static final Integer TEMPORARY_IMAGE_ID=0;
    public static final Boolean TEMPORARY_BOOKMARK=false;

    public static final String OPEN="Open";
    public static final String CLOSED="Closed";
    public static final String CONTENT_PROVIDED ="Content provided";
    private final PubsRepository pubsRepository;
    private final MutableLiveData<List<Pub>> originalPubData=new MutableLiveData<>(null);
    private final LiveData<List<Pub>> _originalPubData=(LiveData<List<Pub>>) originalPubData;
    @Getter
    private final MutableLiveData<String> searchText=new MutableLiveData<>();
    @Getter
    private final MutableLiveData<SortPubsBy> sortType=new MutableLiveData<>();
    @Getter
    private final MutableLiveData<PubsCardViewUiState> sortedAndFilteredPubsUiState =new MutableLiveData<>(new PubsCardViewUiState());
    @Getter
    private final MutableLiveData<FilterUiState> filterUiState=new MutableLiveData<>();

    public static final ViewModelInitializer<PubListViewModel> initializer = new ViewModelInitializer<>(
            PubListViewModel.class,
            creationExtras -> {
                PubberApp app = (PubberApp) creationExtras.get(APPLICATION_KEY);
                assert app != null;
                SavedStateHandle savedStateHandle = createSavedStateHandle(creationExtras);

                return new PubListViewModel(app.appContainer.pubsRepository,  savedStateHandle);
            }
    );

    public PubListViewModel(PubsRepository pubsRepository, SavedStateHandle savedStateHandle) {
        this.pubsRepository=pubsRepository;
    }

    public void getPubsFromRepo()
    {
        if(originalPubData.getValue()==null) {
            if(pubsRepository==null)
            {
                Log.e(TAG, "getPubsFromRepo: kurwa null" );
            }
            Disposable d = Objects.requireNonNull(pubsRepository).getPubs()
                    .subscribe(pubs->{
                        originalPubData.setValue(pubs);
                        sortedAndFilteredPubsUiState.setValue(new PubsCardViewUiState(false,CONTENT_PROVIDED,
                                pubs.stream().map(this::mapPubToUiState).collect(Collectors.toList())));
                        },
                        err -> Log.e(TAG, "getPubs: Can't get pubs " + err.getLocalizedMessage())
                    );
            if (d.isDisposed()) {
                d.dispose();
            }

        }
    }
    public void search(String prompt)
    {
        setSearchText( prompt);
        List<PubItemCardViewUiState> pubs=new ArrayList<>();
        for(Pub pub: Objects.requireNonNull(_originalPubData.getValue()))
        {
            if(pub.getName().toLowerCase().contains(prompt.toLowerCase()))
            {
                pubs.add(mapPubToUiState(pub));
            }
        }
        sortedAndFilteredPubsUiState.setValue(new PubsCardViewUiState(false,CONTENT_PROVIDED,pubs));
    }
    private void setSearchText(String prompt)
    {
        searchText.setValue(prompt);
        filter(new FilterUiState());
        sort(SortPubsBy.RELEVANCE);
    }
    public void filter(FilterUiState filter)
    {
        filterUiState.setValue(filter);
        List<PubItemCardViewUiState> filteredPubs=new FilterUtil(filter,_originalPubData.getValue(),TEMPORARY_DISTANCE)
                .filterByAll()
                .getFilteredPubs().stream()
                .map(this::mapPubToUiState)
                .collect(Collectors.toList());
        sortedAndFilteredPubsUiState.setValue(new PubsCardViewUiState(false,CONTENT_PROVIDED,filteredPubs));

    }
    public void sort(SortPubsBy sort)
    {
        sortType.setValue(sort);
        List<PubItemCardViewUiState> pubs =_originalPubData.getValue().stream()
                .map(this::mapPubToUiState)
                .collect(Collectors.toList());
        SortUtil.sortingPubData(pubs,sort);
        sortedAndFilteredPubsUiState.setValue(new PubsCardViewUiState(false,CONTENT_PROVIDED,pubs));
    }

    public PubItemCardViewUiState mapPubToUiState(Pub pub)
    {
        DateType openInfo;
        try { if(pub.getOpeningHours().size()!=0){openInfo=getPubTimeOpenToday(pub);
            pub.setTimeOpenToday(openInfo.getTime());
        }
            else {openInfo=new DateType(null,false);}
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return new PubItemCardViewUiState(pub.getId(),TEMPORARY_BOOKMARK,pub.getName(),TEMPORARY_IMAGE_ID,
                pub.getTimeOpenToday(), openInfo.getType(),
                TEMPORARY_DISTANCE,
                PriceType.getById(pub.getRatings().getOurCost()).getIcon(),
                pub.getRatings().getOurServiceQuality(),pub.getRatings().getAverageRating());
    }
    public DateType getPubTimeOpenToday(Pub pub) throws ParseException {
        String text;
        String koncowka ="";
        boolean otwarte=false;

        //initialazing dates
        SimpleDateFormat parser = new SimpleDateFormat("HH:mm");
        Date timeOpenToday=parser.parse(((pub.getOpeningHours()).get(DayOfWeekConverter.getByCurrentDay().getNumeric()-1)).getTimeOpen());
        Date timeCloseToday=parser.parse(((pub.getOpeningHours()).get(DayOfWeekConverter.getByCurrentDay().getNumeric()-1)).getTimeClose());
        Date timeOpenYesterday;
        Date timeCloseYesterday;
        if(DayOfWeekConverter.getByCurrentDay().getNumeric()-2==-1){
            timeOpenYesterday = parser.parse(((pub.getOpeningHours()).get(6)).getTimeOpen());
            timeCloseYesterday = parser.parse(((pub.getOpeningHours()).get(6)).getTimeClose());
        }
        else {
           timeOpenYesterday = parser.parse(((pub.getOpeningHours()).get(DayOfWeekConverter.getByCurrentDay().getNumeric() - 2)).getTimeOpen());
           timeCloseYesterday = parser.parse(((pub.getOpeningHours()).get(DayOfWeekConverter.getByCurrentDay().getNumeric() - 2)).getTimeClose());
        }

        DateType time=(new DateTimetoCurrentTimeComparator()).dateTimetoCurrentTimeComparator(timeOpenToday,timeCloseToday,timeOpenYesterday,timeCloseYesterday);
        if(Float.valueOf(time.getTime())==1){koncowka="a";}
        if(Float.valueOf(time.getTime())<=4.5 && Float.valueOf(time.getTime())!=1){koncowka="y";}
        if(Float.valueOf(time.getTime())>=5){
            if(ceil(Float.valueOf(time.getTime()))!=Float.valueOf(time.getTime())){koncowka="y";}
        }
        if(time.getTime().substring(time.getTime().length()-1,time.getTime().length()).equals("0")){
            time.setTime(time.getTime().substring(0,time.getTime().length()-2));
        }
        if(time.getType()){
            text="Otwarte jeszcze "+time.getTime()+" godzin"+koncowka;
            otwarte=true;
        }
        else {
            text="Zamknięte jeszcze "+time.getTime()+" godzin"+koncowka;
            otwarte=false;
        }
        //używam tutaj dateType tylko do przeniesiania danych, bo jest to najwygodniejsza forma, a myśle, że nia warto dodawać kolejnej classy tylko po to
        DateType dateType=new DateType(text,otwarte);

        return dateType;
    }

    public void setPubDetail(int position,DetailsViewModel detailsViewModel){
        Pub pub=_originalPubData.getValue().get(position);
        PubDetailsUiState pubDetailsUiState=new PubDetailsUiState(pub.getId(), pub.getName(), pub.getAddress(),pub.getPhoneNumber(),
                pub.getWebsiteUrl(),pub.getIconPath(),pub.getDescription(),pub.getReservable(),pub.getTakeout(),pub.getRatings(),pub.getOpeningHours(),
                pub.getDrinks(),pub.getPhotos(),null,pub.getTimeOpenToday());
        DetailsViewModel.setPubDetails(pubDetailsUiState);
    }
    public static int dpToPx(int dp)
    {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }


}
