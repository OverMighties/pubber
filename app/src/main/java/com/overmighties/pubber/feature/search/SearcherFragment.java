package com.overmighties.pubber.feature.search;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.overmighties.pubber.app.PubberApp;
import com.overmighties.pubber.util.SelectListener;
import com.overmighties.pubber.R;
import com.overmighties.pubber.app.AppContainer;
import com.overmighties.pubber.app.NavigationBar;
import com.overmighties.pubber.data.FilterData;
import com.overmighties.pubber.data.model.PubUiState;
import com.overmighties.pubber.test_data.TestData;
import com.overmighties.pubber.util.FilterUtil;
import com.overmighties.pubber.util.ListPubAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SearcherFragment extends Fragment implements SelectListener {

    public static final String TAG = "SearcherFragment";
    private RecyclerView recyclerView;
    private ListPubAdapter adapter;

    private SearchView searchview;
    private PubListViewModel viewModel;

    public SearcherFragment() {
        super(R.layout.searcher);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        recyclerView = (RecyclerView) requireView().findViewById(R.id.Publista);

        AppContainer appContainer = ((PubberApp) getActivity().getApplication()).appContainer;

        TestData.initDataSets();
        adapter = new ListPubAdapter(TestData.getPubDataList(),this);
        recyclerView.setAdapter(adapter);
        viewModel = new ViewModelProvider(requireActivity(),
                ViewModelProvider.Factory.from(PubListViewModel.initializer))
                .get(PubListViewModel.class);
        final Observer<FilterData> nameObserver = filtration -> {
            Log.d(TAG, "onChanged: filtration changed");
            filtrationOfTestDataList(filtration);
        };

        final Observer<List<PubUiState>> name= sorting->{
            if(AppContainer.getInstance().getPubSearchingContainer().getListOfFiltratedPubs().getValue()!=null) {
                adapter = new ListPubAdapter(AppContainer.getInstance().getPubSearchingContainer().getListOfSortedPubs().getValue(), this);
                recyclerView.setAdapter(adapter);
            }
        };

        AppContainer.getInstance().getPubSearchingContainer().getListOfSortedPubs().observe(getViewLifecycleOwner(),name);

        AppContainer.getInstance()
                .getPubSearchingContainer()
                .getFiltrationOfPubs()
                .observe(getViewLifecycleOwner(), nameObserver);
        //Refresh publist
        ((SwipeRefreshLayout) requireView().findViewById(R.id.swipeRefresh)).setOnRefreshListener(() -> {

            filtrationOfTestDataList(
                    AppContainer.getInstance()
                    .getPubSearchingContainer()
                    .getFiltrationOfPubs().getValue());
            ((SwipeRefreshLayout) requireView().findViewById(R.id.swipeRefresh)).setRefreshing(false);
        });
        //Setting listener to departure to FiltrationScreen
        ((ImageView) requireView().findViewById(R.id.Filtration)).setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(SearcherFragmentDirections.searcherToFiltration());

        });
        NavigationBar.smoothPopUp(getActivity().findViewById(R.id.nav_view));
        initSearchView();
        listeners();



    }
    private void listeners()
    {
        ((TextView)requireView().findViewById(R.id.textViewSortType_searcher)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppContainer.getInstance().getPubSearchingContainer().getPopupInformation().setValue(((TextView)requireView().findViewById(R.id.textViewSortType_searcher)).getText().toString());
            }
        });
        ((ImageView)requireView().findViewById(R.id.sort_image)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppContainer.getInstance().getPubSearchingContainer().getPopupInformation().setValue(((TextView)requireView().findViewById(R.id.textViewSortType_searcher)).getText().toString());

            }
        });
    }


    private void filtrationOfTestDataList(FilterData filterData) {
        ArrayList<PubUiState> filtrated;
        Log.d(TAG, "filtrationOfTestDataList: filtraion");
        FilterUtil filter=new FilterUtil(filterData, TestData.getPubDataList());
        if (filterData == null || filterData.equals(new FilterData.Builder().build())) {
            filtrated = TestData.getPubDataList();
        } else {
            filtrated=filter
                    .ratingFilter()
                    .distanceFilter()
                    .drinksFilter()
                    .priceFilter()
                    .isOpenFilter()
                    .getPubDataArrayList();
        }
        adapter = new ListPubAdapter(filtrated,this);
        AppContainer.getInstance()
                .getPubSearchingContainer()
                .getListOfFiltratedPubs()
                .setValue(filtrated);
        recyclerView.setAdapter(adapter);

    }
    private void initSearchView()
    {
        searchview=(SearchView)requireView().findViewById(R.id.searchView);
        searchview.setQueryHint("Wyszukaj tutaj");
        searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s)
            {
                ArrayList<PubUiState> filtreredpubs=new ArrayList<>();
                for(PubUiState pub: Objects.requireNonNull(AppContainer.getInstance()
                        .getPubSearchingContainer()
                        .getListOfFiltratedPubs()
                        .getValue()))
                {
                    if(pub.getName().toLowerCase().contains(s.toLowerCase()))
                    {
                        filtreredpubs.add(pub);
                    }
                }
                adapter = new ListPubAdapter( filtreredpubs,SearcherFragment.this);
                recyclerView.setAdapter(adapter);

                return false;
            }
        });

    }


    @Override
    public void onResume() {
        super.onResume();
        if(getActivity().findViewById(R.id.nav_view).getVisibility()==View.GONE)
            NavigationBar.smoothPopUp(getActivity().findViewById(R.id.nav_view));
    }

    @Override
    public void onItemClicked(int position)
    {
        Navigation.findNavController(requireView()).navigate(SearcherFragmentDirections.searcherToDetail());

    }





}