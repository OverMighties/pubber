package com.overmighties.pubber.app.navigation;

import androidx.navigation.NavDirections;

import com.overmighties.pubber.feature.account.AccountDetailsFragmentDirections;
import com.overmighties.pubber.feature.auth.SignInFragmentDirections;
import com.overmighties.pubber.feature.auth.SignUpFragmentDirections;
import com.overmighties.pubber.feature.auth.SplashFragmentDirections;
import com.overmighties.pubber.feature.search.SearcherFragmentDirections;


public final class PubberNavRoutes {
    public static final String SPLASH_FRAGMENT="SplashFragment";
    public static final String SEARCHER_FRAGMENT="searcher";
    public static final String SIGN_IN_FRAGMENT="SignInFragment";
    public static final String SIGN_UP_FRAGMENT="SignUpFragment";
    public static final String ACCOUNT_FRAGMENT="AccountFragment";
    public static NavDirections getNavDirections(String from , String to){
        switch (from) {
            case SPLASH_FRAGMENT:
                switch (to) {
                    case SIGN_IN_FRAGMENT:
                        return SplashFragmentDirections.actionSplashFragmentToSignInFragment();
                    case SIGN_UP_FRAGMENT:
                        return SplashFragmentDirections.actionSplashFragmentToSignUpFragment();
                    case SEARCHER_FRAGMENT:
                        return SplashFragmentDirections.actionGlobalSearcher();
                }
            case SIGN_UP_FRAGMENT:
                return SignUpFragmentDirections.actionGlobalSearcher();
            case SIGN_IN_FRAGMENT:
                switch (to) {
                    case SEARCHER_FRAGMENT:
                        return SignInFragmentDirections.actionGlobalSearcher();
                    case SIGN_UP_FRAGMENT:
                        return SignInFragmentDirections.actionSignInFragmentToSignUpFragment();
                }
            case SEARCHER_FRAGMENT:
                return SearcherFragmentDirections.actionSearcherToLogInGraph();
            case ACCOUNT_FRAGMENT:
                return AccountDetailsFragmentDirections.actionAccountDetailsToSplash();
        }
        return null;
    }
}
