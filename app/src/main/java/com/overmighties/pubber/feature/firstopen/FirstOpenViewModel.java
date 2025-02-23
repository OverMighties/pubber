package com.overmighties.pubber.feature.firstopen;

import static androidx.lifecycle.SavedStateHandleSupport.createSavedStateHandle;
import static androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

import com.overmighties.pubber.app.PubberApp;
import com.overmighties.pubber.app.settings.SettingsHandler;

public class FirstOpenViewModel extends ViewModel {

    private final MutableLiveData<FirstOpenUiState> uiState=
            new MutableLiveData<> (new FirstOpenUiState());
    public LiveData<FirstOpenUiState> getUiState(){
        return uiState;
    }

    public enum Language{
        Polish("pl", "Polski"),
        English("en", "English"),
        Ukrainian("uk", "Український");
        private String languageCode;
        private String nativeName;
        Language(String languageCode, String nativeName){

            this.languageCode = languageCode;
            this.nativeName = nativeName;
        }
        public String getLanguageCode() {
            return this.languageCode;
        }
        public String getNativeName() {return this.nativeName;}
    };
    public enum Theme{
        Dark(SettingsHandler.ThemeHelper.THEME_DARK),
        Light(SettingsHandler.ThemeHelper.THEME_LIGHT);
        private String theme;
        Theme(String theme){
            this.theme = theme;
        }

        public String getTheme() {
            return this.theme;
        }
        public static Theme getThemeByName(String name){
            if(name.equals(SettingsHandler.ThemeHelper.THEME_LIGHT)){
                return Light;
            }
            return Dark;
        }
    };

    public static final ViewModelInitializer<FirstOpenViewModel> initializer=new ViewModelInitializer<>(
            FirstOpenViewModel.class,
            creationExtras -> {
                PubberApp pubberApp=(PubberApp) creationExtras.get(APPLICATION_KEY);
                assert pubberApp!=null;
                SavedStateHandle savedStateHandle=createSavedStateHandle(creationExtras);

                return new FirstOpenViewModel(savedStateHandle);
            }
    );

    public FirstOpenViewModel(SavedStateHandle savedStateHandle){}
}
