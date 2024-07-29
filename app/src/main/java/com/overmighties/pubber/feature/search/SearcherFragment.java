package com.overmighties.pubber.feature.search;


import static com.overmighties.pubber.app.Constants.SORT_POP_UP_IDS;
import static com.overmighties.pubber.app.navigation.PubberNavRoutes.SEARCHER_FRAGMENT;
import static com.overmighties.pubber.app.navigation.PubberNavRoutes.SPLASH_FRAGMENT;
import static com.overmighties.pubber.app.navigation.PubberNavRoutes.getNavDirections;

import android.content.Context;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.TextAppearanceSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.overmighties.pubber.app.PubberApp;
import com.overmighties.pubber.R;
import com.overmighties.pubber.app.AppContainer;
import com.overmighties.pubber.app.basic.BaseFragmentWithPermission;
import com.overmighties.pubber.app.ui.NavigationBar;
import com.overmighties.pubber.feature.search.stateholders.SelectListener;
import com.overmighties.pubber.feature.pubdetails.DetailsViewModel;
import com.overmighties.pubber.feature.search.util.SortPubsBy;

public class SearcherFragment extends BaseFragmentWithPermission implements SelectListener {

    public static final String TAG = "SearcherFragment";
    public static final int REFRESH_MIN_TIME_MS=1500;
    private RecyclerView recyclerView;
    private ListPubAdapter adapter;
    private NavController navController;
    private SearchView searchview;
    private PubListViewModel pubListViewModel;
    private DetailsViewModel detailsViewModel;
    private SwipeRefreshLayout swipeRefreshLayout;
    public SearcherFragment() {
        super(R.layout.fragment_searcher);
    }


    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        recyclerView = requireView().findViewById(R.id.Publista);
        navController=Navigation.findNavController(requireActivity(),R.id.nav_host_fragment);
        AppContainer appContainer = ((PubberApp) requireActivity().getApplication()).appContainer;
        pubListViewModel = new ViewModelProvider(requireActivity()).get(PubListViewModel.class);
        detailsViewModel=new ViewModelProvider(requireActivity(),
                ViewModelProvider.Factory.from(DetailsViewModel.initializer)).get(DetailsViewModel.class);
        //check screen width to determine pubcardview's chips size
        DisplayMetrics displayMetrics = new DisplayMetrics();
        requireActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        if (displayMetrics.xdpi <= 380){
            pubListViewModel.setChipTag("Small");
        }
        if(pubListViewModel.getSortedAndFilteredPubsUiState().getValue()==null ||
                pubListViewModel.getSortedAndFilteredPubsUiState().getValue().getIsLoading()){
            pubListViewModel.getPubsFromRepo(0);
        }
        swipeRefreshLayout = requireActivity().findViewById(R.id.swipeRefresh);
//        Log.i(TAG,"onViewCreated");
        swipeRefreshLayout.setOnRefreshListener(()->{
            swipeRefreshLayout.setRefreshing(true);
            pubListViewModel.getPubsFromRepo(REFRESH_MIN_TIME_MS);
        });
        //gives me null poiner exception
        //actionOnLocationAvailable(null);
        //swipeRefreshLayout.setRefreshing(true);
        adapter = new ListPubAdapter(pubListViewModel.getSortedAndFilteredPubsUiState().getValue(),this, pubListViewModel.getChipTag());
        recyclerView.setAdapter(adapter);
        //Setting listener to departure to FiltrationScreen
        requireView().findViewById(R.id.Filtration).setOnClickListener(v -> navController.navigate(SearcherFragmentDirections.actionSearcherToFilter()));
        if(!requireActivity().findViewById(R.id.bottom_nav_view).isShown())
            NavigationBar.smoothPopUp(requireActivity().findViewById(R.id.bottom_nav_view));
//        getString(R.string.page_title,getString(R.string.searcher_title));
        initSearchView();
        sortButtonsListeners();
        pubListViewModel.getSortedAndFilteredPubsUiState().observe(getViewLifecycleOwner(), pubs-> {
            if(pubs==null || pubs.getPubItems()==null|| pubs.getPubItems().isEmpty())
                recyclerView.setVisibility(View.GONE);
            else {
                adapter = new ListPubAdapter(pubs,this, pubListViewModel.getChipTag());
                recyclerView.setAdapter(adapter);
                recyclerView.setVisibility(View.VISIBLE);
            }
            swipeRefreshLayout.setRefreshing(false);
        });
        if(appContainer.getAccountDataSource().currentUser()==null){
            navController.navigate(getNavDirections(SEARCHER_FRAGMENT,SPLASH_FRAGMENT));
        }
    }
    private void sortButtonsListeners()
    {
        requireView().findViewById(R.id.textViewSortType_searcher).setOnClickListener(v -> showPopUpSortWindow());
        requireView().findViewById(R.id.sort_image).setOnClickListener(v -> showPopUpSortWindow());
    }
    private void showPopUpSortWindow(){
        NavigationBar.smoothHide(requireActivity().findViewById(R.id.bottom_nav_view));
        View popUpView = LayoutInflater.from(requireActivity()).inflate(R.layout.sort_pop_up, null);
        final PopupWindow sortPubsPopUpWindow = new PopupWindow(popUpView,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT, true);
        buttonsListenersForSortPopUpWindow(sortPubsPopUpWindow, popUpView);
        checkCurrentSortRadioButtons(popUpView);
        (requireActivity().findViewById(R.id.SearcherFragment))
                .post(() -> sortPubsPopUpWindow.showAtLocation(requireActivity().findViewById(R.id.SearcherFragment), Gravity.BOTTOM, 0, 0));
    }
    private void checkCurrentSortRadioButtons(View popupView) {
        String word = ((TextView) requireActivity().findViewById(R.id.textViewSortType_searcher)).getText().toString();
        if(getString(R.string.sort_relevance).equals(word)) {
            ((RadioButton) popupView.findViewById(R.id.radio_butt_relevance)).setChecked(true);
        }
        if(getString(R.string.sort_rating).equals(word)) {
            ((RadioButton) popupView.findViewById(R.id.radio_butt_rating)).setChecked(true);
        }
        if(getString(R.string.sort_alphabetical).equals(word)) {
            ((RadioButton) popupView.findViewById(R.id.radio_butt_alphabetical)).setChecked(true);
        }
        if(getString(R.string.sort_distance).equals(word)) {
            ((RadioButton) popupView.findViewById(R.id.radio_butt_distance)).setChecked(true);
        }

    }
    private void buttonsListenersForSortPopUpWindow(PopupWindow popupWindow, View popupView) {
        popupWindow.setOnDismissListener(() -> {
            TextView text = requireActivity().findViewById(R.id.textViewSortType_searcher);
            if (((RadioButton) popupView.findViewById(R.id.radio_butt_relevance)).isChecked()) {
                text.setText( requireActivity().getString(R.string.sort_relevance));
                pubListViewModel.sort(SortPubsBy.RELEVANCE);
            } else if (((RadioButton) popupView.findViewById(R.id.radio_butt_rating)).isChecked()) {
                text.setText(getString(R.string.sort_rating));
                pubListViewModel.sort(SortPubsBy.RATING);
            } else if (((RadioButton) popupView.findViewById(R.id.radio_butt_alphabetical)).isChecked()) {
                text.setText(getString(R.string.sort_alphabetical));
                pubListViewModel.sort(SortPubsBy.ALPHABETICAL);
            } else{
                text.setText(getString(R.string.sort_distance));
                pubListViewModel.sort(SortPubsBy.DISTANCE);
            }
            NavigationBar.smoothPopUp(requireActivity().findViewById(R.id.bottom_nav_view));
        });
        popupView.findViewById(R.id.dismiss).setOnClickListener(v -> popupWindow.dismiss());

        for (var id : SORT_POP_UP_IDS) {
            popupView.findViewById(id).setOnClickListener(v -> {
                if (((RadioButton) popupView.findViewById(id)).isChecked()) {
                    ((RadioButton) popupView.findViewById(R.id.radio_butt_rating)).setChecked(false);
                    ((RadioButton) popupView.findViewById(R.id.radio_butt_distance)).setChecked(false);
                    ((RadioButton) popupView.findViewById(R.id.radio_butt_alphabetical)).setChecked(false);
                    ((RadioButton) popupView.findViewById(R.id.radio_butt_relevance)).setChecked(false);
                    ((RadioButton) popupView.findViewById(id)).setChecked(true);
                }
            });
        }


    }
    private void initSearchView()
    {
        searchview= requireView().findViewById(R.id.searchView);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        SpannableString string = new SpannableString(getString(R.string.searchview_hint));
        string.setSpan(new TextAppearanceSpan(requireContext(), R.style.SearchView_Hint_Text),0,getString(R.string.searchview_hint).length(),0);
        searchview.setQueryHint(string);

        searchview.setOnCloseListener(() -> {
            searchview.setQueryHint("");
            return false;
        });

        searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchview.clearFocus(); return false;
            }
            @Override
            public boolean onQueryTextChange(String s)
            {
                pubListViewModel.search(s);
                return false;
            }
        });

        requireView().findViewById(R.id.constraintLayout).setOnClickListener(v->{
            if (!searchview.isIconified()){
                searchview.setIconified(true);
                InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(requireView().getWindowToken(), 0);
                }
                searchview.clearFocus();
            }
        });

        recyclerView.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                if (!searchview.isIconified()) {
                    searchview.setIconified(true);
                    InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(requireView().getWindowToken(), 0);
                    }
                    searchview.clearFocus();
                }
            }
            return false;
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        if(requireActivity().findViewById(R.id.bottom_nav_view).getVisibility()==View.GONE)
            NavigationBar.smoothPopUp(requireActivity().findViewById(R.id.bottom_nav_view));

    }




    @Override
    public void onItemClicked(int position) {
        NavHostFragment.findNavController(this).navigate(SearcherFragmentDirections.actionSearcherToDetails());
        pubListViewModel.setPubDetails(position,detailsViewModel);
    }
}