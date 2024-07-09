package com.overmighties.pubber.app.navigation;

import androidx.navigation.NavDirections;

import com.overmighties.pubber.app.ui.PlaceChoiceFragmentDirections;
import com.overmighties.pubber.feature.account.AccountDetailsFragmentDirections;
import com.overmighties.pubber.feature.auth.SignInFragmentDirections;
import com.overmighties.pubber.feature.auth.SignUpFragmentDirections;
import com.overmighties.pubber.feature.auth.SplashFragmentDirections;
import com.overmighties.pubber.feature.search.SearcherFragmentDirections;
import com.overmighties.pubber.feature.settings.SettingsFragmentDirections;


public final class PubberNavRoutes {
    public static final String SPLASH_FRAGMENT="SplashFragment";
    public static final String SEARCHER_FRAGMENT="SearcherFragment";
    public static final String SIGN_IN_FRAGMENT="SignInFragment";
    public static final String SIGN_UP_FRAGMENT="SignUpFragment";
    public static final String ACCOUNT_FRAGMENT="AccountFragment";
    public static final String SETTINGS_FRAGMENT="SettingsFragment";
    public static final String PLACE_CHOICE_FRAGMENT="PlaceChoiceFragment";
    public static NavDirections getNavDirections(String from , String to){
        switch (from) {
            case SPLASH_FRAGMENT:
                switch (to) {
                    case SIGN_IN_FRAGMENT:
                        return SplashFragmentDirections.actionSplashFragmentToSignInFragment();
                    case SIGN_UP_FRAGMENT:
                        return SplashFragmentDirections.actionSplashFragmentToSignUpFragment();
                    case SEARCHER_FRAGMENT:
                        return SplashFragmentDirections.actionGlobalPlaceChoiceNavDir();
                }
            case PLACE_CHOICE_FRAGMENT:
                return PlaceChoiceFragmentDirections.actionPlaceChoiceNavDirToSearcherGraph();
            case SIGN_UP_FRAGMENT:
                return SignUpFragmentDirections.actionGlobalPlaceChoiceNavDir();
            case SIGN_IN_FRAGMENT:
                switch (to) {
                    case PLACE_CHOICE_FRAGMENT:
                        return SignInFragmentDirections.actionGlobalPlaceChoiceNavDir();
                    case SIGN_UP_FRAGMENT:
                        return SignInFragmentDirections.actionSignInFragmentToSignUpFragment();
                }
            case SEARCHER_FRAGMENT:
                return SearcherFragmentDirections.actionSearcherToLogInGraph();
            case ACCOUNT_FRAGMENT:
                return AccountDetailsFragmentDirections.actionAccountDetailsFragmentToLoginGraph();
            case SETTINGS_FRAGMENT:
                return SettingsFragmentDirections.actionSettingsNavDirToLoginGraph();
        }
        return null;
    }
}
