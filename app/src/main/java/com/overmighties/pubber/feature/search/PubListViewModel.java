package com.overmighties.pubber.feature.search;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

import static androidx.lifecycle.SavedStateHandleSupport.createSavedStateHandle;
import static androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY;


import static com.overmighties.pubber.core.data.mappers.PubDtoMapper.mapFromDtoDrinks;

import android.util.Log;
import android.util.Pair;

import com.overmighties.pubber.app.PubberApp;
import com.overmighties.pubber.core.data.DrinksRepository;
import com.overmighties.pubber.core.data.PubsRepository;
import com.overmighties.pubber.core.drinksdataset.DrinksDataSet;
import com.overmighties.pubber.core.model.Drink;
import com.overmighties.pubber.core.model.OpeningHours;
import com.overmighties.pubber.core.model.Pub;
import com.overmighties.pubber.core.savedpubs.SavedPubsHandler;
import com.overmighties.pubber.feature.pubdetails.DetailsViewModel;
import com.overmighties.pubber.feature.pubdetails.stateholders.PubDetailsUiState;
import com.overmighties.pubber.feature.search.stateholders.FilterFragmentUiState;
import com.overmighties.pubber.feature.search.stateholders.FilterUiState;
import com.overmighties.pubber.feature.search.stateholders.PubItemCardViewUiState;
import com.overmighties.pubber.feature.search.stateholders.PubsCardViewUiState;
import com.overmighties.pubber.feature.search.stateholders.SearcherUiState;
import com.overmighties.pubber.feature.search.util.FilterUtil;
import com.overmighties.pubber.feature.search.util.PriceType;
import com.overmighties.pubber.feature.search.util.PubFiltrationState;
import com.overmighties.pubber.util.TimePeriodConverter;
import com.overmighties.pubber.util.TimePeriod;
import com.overmighties.pubber.util.DayOfWeek;
import com.overmighties.pubber.feature.search.util.SortPubsBy;
import com.overmighties.pubber.feature.search.util.SortUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import lombok.Getter;
import lombok.Setter;

public class PubListViewModel extends ViewModel {
    public static final String TAG="PubListViewModel";
    //Temporary as a prefix in name of variable means that it will be changed in feature and now they are only in testing purpose
    public static final Float TEMPORARY_DISTANCE=20.f;
    public static final Boolean TEMPORARY_BOOKMARK=false;
    public static final String CONTENT_PROVIDED ="Content provided";
    private final PubsRepository pubsRepository;
    private final SavedPubsHandler savedPubsHandler;
    @Setter
    private boolean isFirstPub = true;
    @Setter
    private boolean isImperfectFound = false;
    @Getter
    private final MutableLiveData<Pair<Long, Boolean>> favouritePubState = new MutableLiveData<>(new Pair<>(-1L, null));
    @Getter
    private final MutableLiveData<Boolean> isSavedDataRetrieved;
    private final MutableLiveData<List<Drink>> originalDrinksData=new MutableLiveData<>(null);
    @Getter
    private final LiveData<List<Drink>> _originalDrinksData=originalDrinksData;
    private final MutableLiveData<List<Pub>> originalPubData=new MutableLiveData<>(null);
    @Getter
    private final LiveData<List<Pub>> _originalPubData=originalPubData;
    @Getter
    private final MutableLiveData<Boolean> pubDataLoaded = new MutableLiveData<>(false);
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
    private final MutableLiveData<FilterFragmentUiState> filterFragmentUiState = new MutableLiveData<>(new FilterFragmentUiState());
    @Getter
    private final MutableLiveData<SearcherUiState> searcherUiState = new MutableLiveData<>(new SearcherUiState());
    @Getter
    private final CompositeDisposable disposables = new CompositeDisposable();
    public static final ViewModelInitializer<PubListViewModel> initializer = new ViewModelInitializer<>(
            PubListViewModel.class,
            creationExtras -> {
                PubberApp app = (PubberApp) creationExtras.get(APPLICATION_KEY);
                assert app != null;
                SavedStateHandle savedStateHandle = createSavedStateHandle(creationExtras);

                return new PubListViewModel(app.appContainer.getPubsRepository(),  app.appContainer.getDrinksRepository(), app.appContainer.getSavedPubsDataStore(),savedStateHandle);
            }
    );

    public PubListViewModel(PubsRepository pubsRepository, DrinksRepository drinksRepository, SavedPubsHandler savedPubsHandler,SavedStateHandle savedStateHandle) {
        this.pubsRepository=pubsRepository;
        this.savedPubsHandler = savedPubsHandler;
        this.isSavedDataRetrieved = savedPubsHandler.getIsDataFetched();
    }

    public void fetchDrinksFromRepo(final int DELAY_TIME_MS){
        /*
        Disposable d = Objects.requireNonNull(drinksRepository).getDrinks()
                .delay(DELAY_TIME_MS, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(drinks->{
                    originalDrinksData.setValue(drinks);
                    },
                    err-> Log.e(TAG, "fetchDrinksFromRepo can't get drinks due to" + err.getLocalizedMessage())
                );
        disposables.add(d);
         */
        temFetchDrinkFromRepo();
    }
    //temporary solution cause drinkRepository doesn't work properly
    private void temFetchDrinkFromRepo(){
        originalDrinksData.setValue(mapFromDtoDrinks(DrinksDataSet.getInstance().getDataSet()));
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
                    pubDataLoaded.setValue(true);
                    searcherUiState.getValue().setPubs(pubs.stream().map(pub ->new Pair<>(pub, new PubFiltrationState(-1, null, null))).collect(Collectors.toList()));
                    sort(searcherUiState.getValue().getLastSortPubsBy());
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
        List<Pair<Pub, PubFiltrationState>> pubs=new ArrayList<>();
        for(Pair<Pub, PubFiltrationState> pubUiState: Objects.requireNonNull(searcherUiState.getValue().getPubs())) {
            if(pubUiState.first.getName().toLowerCase().contains(prompt.toLowerCase()))
                pubs.add(pubUiState);
        }
        sortedAndFilteredPubsUiState.setValue(new PubsCardViewUiState(false,CONTENT_PROVIDED,
                SortUtil.sortingPubData(pubs, SortPubsBy.RELEVANCE)
                        .stream()
                        .map(this::mapPubToUiState)
                        .collect(Collectors.toList())));
        pubs.clear();
        searchText.setValue(prompt);


    }

    public void sort(SortPubsBy sort)
    {
        sortedAndFilteredPubsUiState.setValue(new PubsCardViewUiState(false,CONTENT_PROVIDED,
                SortUtil.sortingPubData(searcherUiState.getValue().getPubs(), sort).stream().map(this::mapPubToUiState).collect(Collectors.toList())));
    }
    public void filter(FilterUiState filter)
    {
        filterUiState.setValue(filter);
        FilterUtil sorted = new FilterUtil(
                filter,
                Objects.requireNonNull(_originalPubData.getValue()),TEMPORARY_DISTANCE, cityConstraint.getValue())
                .filterByAll();
        searcherUiState.getValue().setPubs(sorted.getFilteredPubs());
        List<PubItemCardViewUiState> filteredPubs= SortUtil.sortingPubData(sorted.getFilteredPubs(), searcherUiState.getValue().getLastSortPubsBy())
                .stream().map(this::mapPubToUiState).collect(Collectors.toList());
        sortedAndFilteredPubsUiState.setValue(new PubsCardViewUiState(false,CONTENT_PROVIDED,filteredPubs));

    }

    //Temporary objects means that it will be changed in feature and now they are only in testing purpose
    public PubItemCardViewUiState mapPubToUiState(Pair<Pub, PubFiltrationState> pub)
    {
        TimePeriod openInfo = TimePeriod.NONE;
        try {
            if(pub.first.getOpeningHours()!= null){
                if(!pub.first.getOpeningHours().isEmpty()){
                    openInfo=getPubTimeOpenToday(pub.first.getOpeningHours());
                    pub.first.setTimeOpenToday(openInfo.getTime());
                }
            }
            else
                openInfo=new TimePeriod(null,false);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        //name
        String pubName = null;
        if(pub.first.getName().length()>20) {
            if (pub.first.getName().charAt(19) == ' ')
                pubName = pub.first.getName().substring(0, 19) + "...";
            else
                pubName = pub.first.getName().substring(0, 20) + "...";
        } else {
            pubName = pub.first.getName();
        }
        //determing whether compatibility and divider should be shown. True divider and compatibility, null only compatibility
        Boolean isFirstImperfect = false;
        if (!isImperfectFound){
            if(!isFirstPub){
                if(!pub.second.getCompatibility().equals(pub.second.getAllCompatibility())) {
                    isFirstImperfect = true;
                    isImperfectFound = true;
                }
            } else {
                if(pub.second.getCompatibility() == -1 || !pub.second.getCompatibility().equals(pub.second.getAllCompatibility()))
                    isImperfectFound = true;
                isFirstPub = false;
            }
        }
        if(pub.second.getCompatibility() == -1)
            isFirstImperfect = null;
        return new PubItemCardViewUiState(pub.first.getPubId(),TEMPORARY_BOOKMARK, pubName,pub.first.getIconPath(),
                openInfo.getTime(), openInfo.isType(), TEMPORARY_DISTANCE,
                PriceType.getById(pub.first.getRatings().getOurCost()).getIcon(),
                pub.first.getRatings().getAverageRating(),
                pub.first.getRatings().getRatingsCount(), pub.first.getAddress(),
                pub.first.getDrinks(), isFirstImperfect,
                new Pair<>(pub.second.getCompatibility(), pub.second.getAllCompatibility()), pub.first.isFavourite());
    }
    public TimePeriod getPubTimeOpenToday(List<OpeningHours> open_hours) throws ParseException {
        //initialazing dates
        SimpleDateFormat parser = new SimpleDateFormat("HH:mm");
        Date timeOpenToday=parser.parse((open_hours.get(DayOfWeek.getByCurrentDay().getNumeric()-1)).getTimeOpen());
        Date timeCloseToday=parser.parse((open_hours.get(DayOfWeek.getByCurrentDay().getNumeric()-1)).getTimeClose());
        Date timeOpenYesterday;
        Date timeCloseYesterday;
        //check if it is week's split
        if(DayOfWeek.getByCurrentDay().getNumeric()-2==-1){
            timeOpenYesterday = parser.parse((open_hours.get(6)).getTimeOpen());
            timeCloseYesterday = parser.parse((open_hours.get(6)).getTimeClose());
        }
        else {
            timeOpenYesterday = parser.parse((open_hours.get(DayOfWeek.getByCurrentDay().getNumeric() - 2)).getTimeOpen());
            timeCloseYesterday = parser.parse((open_hours.get(DayOfWeek.getByCurrentDay().getNumeric() - 2)).getTimeClose());
        }

        TimePeriod time= TimePeriodConverter.createFromOpeningTimes(timeOpenToday,timeCloseToday,timeOpenYesterday,timeCloseYesterday);
        time.convertToPolish();

        return time;
    }

    public void setPubDetails(int position, DetailsViewModel detailsViewModel){
        Pub pub= Objects.requireNonNull(_originalPubData.getValue()).get(position);
        PubDetailsUiState pubDetailsUiState=new PubDetailsUiState(pub.getPubId(), cityConstraint.getValue(),pub.getName(), pub.getAddress(),pub.getPhoneNumber(),
                pub.getWebsiteUrl(),pub.getIconPath(),pub.getDescription(),pub.getReservable(),pub.getTakeout(),pub.getRatings(),pub.getOpeningHours(),
                pub.getDrinks(),pub.getPhotos(),null,pub.getTags(),pub.getTimeOpenToday(), null, null, null, pub.isFavourite());
        detailsViewModel.setPubDetails(pubDetailsUiState);
    }

    public void retrievePubData(){
        savedPubsHandler.retriveSavedPubs();
    }
    public void savePub(Optional<Pub> pub){
        pub.ifPresentOrElse(
                p->{
                    if(getSavedData().stream().noneMatch(pubData-> pubData.getPubId().equals(p.getPubId()))) {
                        p.setFavourite(true);
                        getSavedData().add(p);
                        savedPubsHandler.addPub(p);
                    }
                    },
                () -> Log.e(TAG, "Save Pub: Pub not found")
        );
    }

    public void deletePub(Long pubId){
        List<Pub> list = savedPubsHandler.getSavedPubsList();
        OptionalInt index = IntStream.range(0, list.size())
                .filter(i -> list.get(i).getPubId().equals(pubId))
                .findFirst();
        if(index.isPresent()){
            savedPubsHandler.getSavedPubsList().remove(index.getAsInt());
            savedPubsHandler.deletePub(pubId);
        }
    }

    public List<Pub> getSavedData(){
        return savedPubsHandler.getSavedPubsList();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposables.clear();
    }
}
