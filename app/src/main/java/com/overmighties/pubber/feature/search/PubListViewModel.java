package com.overmighties.pubber.feature.search;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

import static androidx.lifecycle.SavedStateHandleSupport.createSavedStateHandle;
import static androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY;


import android.util.Log;

import com.overmighties.pubber.app.PubberApp;
import com.overmighties.pubber.core.data.PubsRepository;
import com.overmighties.pubber.core.model.OpeningHours;
import com.overmighties.pubber.core.model.Pub;
import com.overmighties.pubber.feature.pubdetails.DetailsViewModel;
import com.overmighties.pubber.feature.pubdetails.stateholders.PubDetailsUiState;
import com.overmighties.pubber.feature.search.stateholders.FilterUiState;
import com.overmighties.pubber.feature.search.stateholders.PubItemCardViewUiState;
import com.overmighties.pubber.feature.search.stateholders.PubsCardViewUiState;
import com.overmighties.pubber.feature.search.util.FilterUtil;
import com.overmighties.pubber.feature.search.util.PriceType;
import com.overmighties.pubber.util.DateTimeToCurrentTimeComparator;
import com.overmighties.pubber.util.DateType;
import com.overmighties.pubber.util.DayOfWeekConverter;
import com.overmighties.pubber.feature.search.util.SortPubsBy;
import com.overmighties.pubber.feature.search.util.SortUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import lombok.Getter;
import lombok.Setter;

public class PubListViewModel extends ViewModel {
    public static final String TAG="PubListViewModel";
    //Temporary as a prefix in name of variable means that it will be changed in feature and now they are only in testing purpose
    public static final Float TEMPORARY_DISTANCE=20.f;
    public static final Integer TEMPORARY_IMAGE_ID=0;
    public static final Boolean TEMPORARY_BOOKMARK=false;
    public static final String TEMPORARY_OPEN ="Open";
    public static final String CLOSED="Closed";
    public static final String CONTENT_PROVIDED ="Content provided";
    public static final String DETAILS_TRANSITION_NAME = "details_transition";
    @Setter
    @Getter
    public String ChipTag = "Normal";
    @Setter
    @Getter
    public boolean searchviewClicked = false;
    @Setter
    @Getter
    private String brewery_textview = null;
    @Setter
    @Getter
    private String style_textview = null;
    @Setter
    @Getter
    private Long linkPubId = null;

    private final PubsRepository pubsRepository;
    private final MutableLiveData<List<Pub>> originalPubData=new MutableLiveData<>(null);
    @Getter
    private final LiveData<List<Pub>> _originalPubData=originalPubData;
    @Getter
    private final MutableLiveData<String> cityConstraint=new MutableLiveData<>();
    @Getter
    private final MutableLiveData<String> searchText=new MutableLiveData<>();
    @Getter
    private final MutableLiveData<SortPubsBy> sortType=new MutableLiveData<>();
    @Getter
    private final MutableLiveData<PubsCardViewUiState> sortedAndFilteredPubsUiState =new MutableLiveData<>(new PubsCardViewUiState());
    @Getter
    private final MutableLiveData<FilterUiState> filterUiState=new MutableLiveData<>();
    @Getter
    private final CompositeDisposable disposables = new CompositeDisposable();
    public static final ViewModelInitializer<PubListViewModel> initializer = new ViewModelInitializer<>(
            PubListViewModel.class,
            creationExtras -> {
                PubberApp app = (PubberApp) creationExtras.get(APPLICATION_KEY);
                assert app != null;
                SavedStateHandle savedStateHandle = createSavedStateHandle(creationExtras);

                return new PubListViewModel(app.appContainer.getPubsRepository(),  savedStateHandle);
            }
    );

    public PubListViewModel(PubsRepository pubsRepository, SavedStateHandle savedStateHandle) {
        this.pubsRepository=pubsRepository;
    }

    public void fetchPubsFromRepo(final int DELAY_TIME_MS)
    {
        if(pubsRepository==null)
            Log.e(TAG, "fetchPubsFromRepo returned null: PubsRepository didn't return anything" );
        Disposable d = Objects.requireNonNull(pubsRepository).getPubs()
                .delay(DELAY_TIME_MS, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(pubs->{
                    originalPubData.setValue(pubs);
                    sortedAndFilteredPubsUiState.setValue(new PubsCardViewUiState(false,CONTENT_PROVIDED,
                            pubs.stream().map(this::mapPubToUiState).collect(Collectors.toList())));
                    if(filterUiState.getValue()!=null)
                        filter(filterUiState.getValue());
                    },
                    err -> Log.e(TAG, "fetchPubsFromRepo can't get pubs due to :" + err.getLocalizedMessage())
                );
        disposables.add(d);
    }
    public void setCityConstraint(String city)
    {
        cityConstraint.setValue(city);
    }
    public void search(String prompt)
    {
        setSearchText( prompt);
        List<PubItemCardViewUiState> pubs=new ArrayList<>();
        for(Pub pub: Objects.requireNonNull(_originalPubData.getValue())) {
            if(pub.getName().toLowerCase().contains(prompt.toLowerCase()))
                pubs.add(mapPubToUiState(pub));
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
        List<PubItemCardViewUiState> filteredPubs=new FilterUtil(filter, Objects.requireNonNull(_originalPubData.getValue()),TEMPORARY_DISTANCE, cityConstraint.getValue())
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
    //Temporary objects means that it will be changed in feature and now they are only in testing purpose
    public PubItemCardViewUiState mapPubToUiState(Pub pub)
    {
        DateType openInfo = DateType.NONE;
        try {
            if(pub.getOpeningHours()!= null){
                if(!pub.getOpeningHours().isEmpty()){
                    openInfo=getPubTimeOpenToday(pub);
                    pub.setTimeOpenToday(openInfo.getTime());
                }
            }
            else
                openInfo=new DateType(null,false);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return new PubItemCardViewUiState(pub.getId(),TEMPORARY_BOOKMARK,pub.getName(),pub.getIconPath(),
                openInfo.getTime(), openInfo.isType(),
                TEMPORARY_DISTANCE,
                PriceType.getById(pub.getRatings().getOurCost()).getIcon(),
                pub.getRatings().getAverageRating(), pub.getRatings().getRatingsCount(), pub.getAddress(), pub.getDrinks());
    }
    public DateType getPubTimeOpenToday(Pub pub) throws ParseException {
        //initialazing dates
        SimpleDateFormat parser = new SimpleDateFormat("HH:mm");
        List<OpeningHours> open_hours = pub.getOpeningHours();
        Date timeOpenToday=parser.parse((open_hours.get(DayOfWeekConverter.getByCurrentDay().getNumeric()-1)).getTimeOpen());
        Date timeCloseToday=parser.parse((open_hours.get(DayOfWeekConverter.getByCurrentDay().getNumeric()-1)).getTimeClose());
        Date timeOpenYesterday;
        Date timeCloseYesterday;
        //check if it is week's split
        if(DayOfWeekConverter.getByCurrentDay().getNumeric()-2==-1){
            timeOpenYesterday = parser.parse((open_hours.get(6)).getTimeOpen());
            timeCloseYesterday = parser.parse((open_hours.get(6)).getTimeClose());
        }
        else {
            timeOpenYesterday = parser.parse((open_hours.get(DayOfWeekConverter.getByCurrentDay().getNumeric() - 2)).getTimeOpen());
            timeCloseYesterday = parser.parse((open_hours.get(DayOfWeekConverter.getByCurrentDay().getNumeric() - 2)).getTimeClose());
        }

        DateType time= DateTimeToCurrentTimeComparator.dateTimeToCurrentTimeComparator(timeOpenToday,timeCloseToday,timeOpenYesterday,timeCloseYesterday);
        time.convertToPolish();

        return time;
    }

    public void setPubDetails(int position, DetailsViewModel detailsViewModel){
        Pub pub= Objects.requireNonNull(_originalPubData.getValue()).get(position);
        PubDetailsUiState pubDetailsUiState=new PubDetailsUiState(pub.getId(), cityConstraint.getValue(),pub.getName(), pub.getAddress(),pub.getPhoneNumber(),
                pub.getWebsiteUrl(),pub.getIconPath(),pub.getDescription(),pub.getReservable(),pub.getTakeout(),pub.getRatings(),pub.getOpeningHours(),
                pub.getDrinks(),pub.getPhotos(),null,pub.getTags(),pub.getTimeOpenToday(), null, null, null);
        detailsViewModel.setPubDetails(pubDetailsUiState);
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        disposables.clear();
    }
}
