package com.overmighties.pubber.feature.search;


import static com.overmighties.pubber.app.Constants.SORT_POP_UP_IDS;
import static com.overmighties.pubber.app.navigation.PubberNavRoutes.SEARCHER_FRAGMENT;
import static com.overmighties.pubber.app.navigation.PubberNavRoutes.SPLASH_FRAGMENT;
import static com.overmighties.pubber.app.navigation.PubberNavRoutes.getNavDirections;

import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.TextAppearanceSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
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

import com.google.android.material.search.SearchBar;
import com.overmighties.pubber.app.PubberApp;
import com.overmighties.pubber.R;
import com.overmighties.pubber.app.AppContainer;
import com.overmighties.pubber.app.ui.NavigationBar;
import com.overmighties.pubber.feature.search.stateholders.SelectListener;
import com.overmighties.pubber.feature.pubdetails.DetailsViewModel;
import com.overmighties.pubber.util.SortPubsBy;

public class SearcherFragment extends Fragment implements SelectListener {

    public static final String TAG = "SearcherFragment";
    public static final int REFRESH_MIN_TIME_MS=1500;
    private RecyclerView recyclerView;
    private ListPubAdapter adapter;
    private NavController navController;
    private SearchView searchview;
    private PubListViewModel viewModel;
    private SwipeRefreshLayout swipeRefreshLayout;
    public SearcherFragment() {
        super(R.layout.fragment_searcher);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        recyclerView = (RecyclerView) requireView().findViewById(R.id.Publista);
        navController=Navigation.findNavController(requireActivity(),R.id.nav_host_fragment);
        AppContainer appContainer = ((PubberApp) getActivity().getApplication()).appContainer;
        viewModel = new ViewModelProvider(requireActivity(),
                ViewModelProvider.Factory.from(PubListViewModel.initializer))
                .get(PubListViewModel.class);
        swipeRefreshLayout = getActivity().findViewById(R.id.swipeRefresh);
        swipeRefreshLayout.setOnRefreshListener(()->{
            swipeRefreshLayout.setRefreshing(true);
            viewModel.getPubsFromRepo(REFRESH_MIN_TIME_MS);
        });
        swipeRefreshLayout.setRefreshing(true);
        viewModel.getPubsFromRepo(0);
        adapter = new ListPubAdapter(viewModel.getSortedAndFilteredPubsUiState().getValue(),this);
        recyclerView.setAdapter(adapter);
        //Setting listener to departure to FiltrationScreen
        ((ImageView) requireView().findViewById(R.id.Filtration)).setOnClickListener(v -> {
            navController.navigate(SearcherFragmentDirections.actionSearcherToFilter());
        });
        if(!getActivity().findViewById(R.id.bottom_nav_view).isShown())
            NavigationBar.smoothPopUp(getActivity().findViewById(R.id.bottom_nav_view));
        if(!getActivity().findViewById(R.id.top_app_bar_view).isShown())
            NavigationBar.smoothPopUp(getActivity().findViewById(R.id.top_app_bar_view));
        getString(R.string.page_title,getString(R.string.searcher_title));
        initSearchView();
        sortButtonsListeners();
        viewModel.getSortedAndFilteredPubsUiState().observe(getViewLifecycleOwner(), pubs-> {
            if(pubs==null || pubs.getPubItems()==null|| pubs.getPubItems().isEmpty())
                recyclerView.setVisibility(View.GONE);
            else {
                adapter = new ListPubAdapter(pubs,this);
                recyclerView.setAdapter(adapter);
                recyclerView.setVisibility(View.VISIBLE);
            }
            swipeRefreshLayout.setRefreshing(false);
        });
        if(!appContainer.getAccountDataSource().hasUser()){
            navController.navigate(getNavDirections(SEARCHER_FRAGMENT,SPLASH_FRAGMENT));
        }

    }
    private void sortButtonsListeners()
    {
        ((TextView)requireView().findViewById(R.id.textViewSortType_searcher)).setOnClickListener(v -> showPopUpSortWindow());
        ((ImageView)requireView().findViewById(R.id.sort_image)).setOnClickListener(v -> showPopUpSortWindow());
    }
    private void showPopUpSortWindow(){
        NavigationBar.smoothHide(getActivity().findViewById(R.id.bottom_nav_view));
        NavigationBar.smoothHide(getActivity().findViewById(R.id.top_app_bar_view));
        View popUpView = LayoutInflater.from(getActivity()).inflate(R.layout.sort_pop_up, null);
        final PopupWindow sortPubsPopUpWindow = new PopupWindow(popUpView,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT, true);
        buttonsListenersForSortPopUpWindow(sortPubsPopUpWindow, popUpView);
        checkCurrentSortRadioButtons(popUpView);
        (getActivity().findViewById(R.id.SearcherFragment))
                .post(() -> sortPubsPopUpWindow.showAtLocation(getActivity().findViewById(R.id.SearcherFragment), Gravity.BOTTOM, 0, 0));
    }
    private void checkCurrentSortRadioButtons(View popupView) {
        String word = ((TextView) getActivity().findViewById(R.id.textViewSortType_searcher)).getText().toString();
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
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                TextView text = getActivity().findViewById(R.id.textViewSortType_searcher);
                if (((RadioButton) popupView.findViewById(R.id.radio_butt_relevance)).isChecked()) {
                    text.setText( getActivity().getString(R.string.sort_relevance));
                    viewModel.sort(SortPubsBy.RELEVANCE);
                } else if (((RadioButton) popupView.findViewById(R.id.radio_butt_rating)).isChecked()) {
                    text.setText(getString(R.string.sort_rating));
                    viewModel.sort(SortPubsBy.RATING);
                } else if (((RadioButton) popupView.findViewById(R.id.radio_butt_alphabetical)).isChecked()) {
                    text.setText(getString(R.string.sort_alphabetical));
                    viewModel.sort(SortPubsBy.ALPHABETICAL);
                } else{
                    text.setText(getString(R.string.sort_distance));
                    viewModel.sort(SortPubsBy.DISTANCE);
                }
                NavigationBar.smoothPopUp(getActivity().findViewById(R.id.bottom_nav_view));
            }
        });
        ((ConstraintLayout) popupView.findViewById(R.id.dismiss)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        for (var id : SORT_POP_UP_IDS) {
            ((RadioButton) popupView.findViewById(id)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (((RadioButton) popupView.findViewById(id)).isChecked()) {
                        ((RadioButton) popupView.findViewById(R.id.radio_butt_rating)).setChecked(false);
                        ((RadioButton) popupView.findViewById(R.id.radio_butt_distance)).setChecked(false);
                        ((RadioButton) popupView.findViewById(R.id.radio_butt_alphabetical)).setChecked(false);
                        ((RadioButton) popupView.findViewById(R.id.radio_butt_relevance)).setChecked(false);
                        ((RadioButton) popupView.findViewById(id)).setChecked(true);
                    } else {
                    }
                }
            });
        }


    }
    private void initSearchView()
    {
        searchview=(SearchView)requireView().findViewById(R.id.searchView);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        SpannableString string = new SpannableString("Wyszukaj Tutaj");
        string.setSpan(new TextAppearanceSpan(getContext(), R.style.SearchView_Hint_Text),0,14,0);
        searchview.setQueryHint(string);
        searchview.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                searchview.setQueryHint("");
                return false;
            }
        });

        searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s)
            {
                viewModel.search(s);
                return false;
            }
        });

    }
    @Override
    public void onResume() {
        super.onResume();
        if(getActivity().findViewById(R.id.bottom_nav_view).getVisibility()==View.GONE)
            NavigationBar.smoothPopUp(getActivity().findViewById(R.id.bottom_nav_view));

    }

    @Override
    public void onItemClicked(int position) {
        NavHostFragment.findNavController(this).navigate(SearcherFragmentDirections.actionSearcherToDetails());
        DetailsViewModel detailsViewModel=new ViewModelProvider(getActivity(),
                ViewModelProvider.Factory.from(DetailsViewModel.initializer)).get(DetailsViewModel.class);
        viewModel.setPubDetail(position,detailsViewModel);
    }
}