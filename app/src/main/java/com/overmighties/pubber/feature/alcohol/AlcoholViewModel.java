package com.overmighties.pubber.feature.alcohol;

import static androidx.lifecycle.SavedStateHandleSupport.createSavedStateHandle;
import static androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

import com.overmighties.pubber.app.PubberApp;
import com.overmighties.pubber.core.model.Drink;
import com.overmighties.pubber.feature.pubdetails.DetailsViewModel;
import com.overmighties.pubber.feature.pubdetails.stateholders.PubDetailsUiState;
import com.overmighties.pubber.feature.search.PubListViewModel;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import lombok.Getter;

public class AlcoholViewModel extends ViewModel {


    private final MutableLiveData<AlcoholUiState> uiState=
            new MutableLiveData<> (new AlcoholUiState());
    public LiveData<AlcoholUiState> getUiState(){
        return uiState;
    }
    @Getter
    private List<Drink> drinkList;

    private final ExecutorService executorService;

    private boolean taskDone;

    public static final ViewModelInitializer<AlcoholViewModel> initializer=new ViewModelInitializer<>(
            AlcoholViewModel.class,
            creationExtras -> {
                PubberApp pubberApp=(PubberApp) creationExtras.get(APPLICATION_KEY);
                assert pubberApp!=null;
                SavedStateHandle savedStateHandle=createSavedStateHandle(creationExtras);

                return new AlcoholViewModel(savedStateHandle);
            }
    );

    public AlcoholViewModel(SavedStateHandle savedStateHandle){
        executorService = Executors.newSingleThreadExecutor();

    }

    public void setAlcoholData(List<Drink> list){
        drinkList = list;
    }

    public void checkBackBroundTask(){
        if(!taskDone)
            setUiStateData();
        else
            taskDone = false;

    }

    public void setUiStateDataBackground(){
        executorService.submit(()->{
           setUiStateData();
           taskDone = true;
        });
    }

    private void  setUiStateData(){
        for(var drink:drinkList){
            if(uiState.getValue().getDrink_id() == drink.getDrinkId()){
                uiState.getValue().changeAllData(
                        drink.getName(),
                        //drink.getBeer().getBrewery
                        "temporary Brewery",
                        drink.getBeer().getShortDescription(),
                        drink.getBeer().getLongDescription(),
                        //drink.getBeer().getBreweryDes,
                        "temporary Brewery description",
                        drink.getBeer().getPhotoUrl(),
                        //drink.getBeer().getHopiness()
                        "4.7",
                        "2.3",
                        "5.6"
                );
            }
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        executorService.shutdown();
    }
}
