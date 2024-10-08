package com.overmighties.pubber.feature.search;


import static com.overmighties.pubber.app.Constants.SORT_POP_UP_IDS;
import static com.overmighties.pubber.app.navigation.PubberNavRoutes.ACCOUNT_DETAILS_FRAGMENT;
import static com.overmighties.pubber.app.navigation.PubberNavRoutes.DICTIONARY_FRAGMENT;
import static com.overmighties.pubber.app.navigation.PubberNavRoutes.MAP_FRAGMENT;
import static com.overmighties.pubber.app.navigation.PubberNavRoutes.SEARCHER_FRAGMENT;
import static com.overmighties.pubber.app.navigation.PubberNavRoutes.SETTINGS_FRAGMENT;
import static com.overmighties.pubber.app.navigation.PubberNavRoutes.SPLASH_FRAGMENT;
import static com.overmighties.pubber.app.navigation.PubberNavRoutes.getNavDirections;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.TextAppearanceSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Pair;
import android.view.ContextThemeWrapper;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.Constraints;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.overmighties.pubber.R;
import com.overmighties.pubber.app.AppContainer;
import com.overmighties.pubber.app.PubberApp;
import com.overmighties.pubber.app.basic.BaseFragmentWithPermission;
import com.overmighties.pubber.app.designsystem.NavigationBar;
import com.overmighties.pubber.app.settings.SettingsHandler;
import com.overmighties.pubber.feature.pubdetails.DetailsViewModel;
import com.overmighties.pubber.feature.search.util.PubListSelectListener;
import com.overmighties.pubber.feature.search.util.SortPubsBy;
import com.overmighties.pubber.util.DimensionsConverter;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class SearcherFragment extends BaseFragmentWithPermission implements PubListSelectListener {

    public static final String TAG = "SearcherFragment";
    public static final int REFRESH_MIN_TIME_MS=1500;
    private RecyclerView recycler;
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pubListViewModel = new ViewModelProvider(requireActivity()).get(PubListViewModel.class);
        detailsViewModel=new ViewModelProvider(requireActivity(),
                ViewModelProvider.Factory.from(DetailsViewModel.initializer)).get(DetailsViewModel.class);
        //check screen width to determine pubcardview's chips size
        DisplayMetrics displayMetrics = new DisplayMetrics();
        requireActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        if (displayMetrics.xdpi <= 380){
            pubListViewModel.getSearcherUiState().getValue().setChipTag("Small");
        }
        if(!requireActivity().findViewById(R.id.main_bottomNavView).isShown())
            NavigationBar.smoothPopUp(requireActivity().findViewById(R.id.main_bottomNavView), 200);
        //determine used language
        pubListViewModel.getFilterFragmentUiState().getValue().setLanguage(SettingsHandler.LanguageHelper.getLanguage(requireContext()));
    }


    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG,"onViewCreated");
        recycler = requireView().findViewById(R.id.searcher_recyclerView_pubList);
        navController=Navigation.findNavController(requireActivity(),R.id.main_navHostFragment_container);
        swipeRefreshLayout = requireActivity().findViewById(R.id.searcher_swipeRefresh_pubList);
        AppContainer appContainer = ((PubberApp) requireActivity().getApplication()).appContainer;

        if(pubListViewModel.getSortedAndFilteredPubsUiState().getValue()==null ||
                pubListViewModel.getSortedAndFilteredPubsUiState().getValue().getIsLoading()){
            pubListViewModel.fetchPubsFromRepo(0);
        }

        //gives me null pointer exception
        //actionOnLocationAvailable(null);
        setUpRecyclerViews();
        setUpTopAppBar();
        initSearchView();
        setUpListeners();

        //switch navigation checks
        if(appContainer.getAccountDataSource().currentUser()==null){
            navController.navigate(getNavDirections(SEARCHER_FRAGMENT,SPLASH_FRAGMENT));
        }

        if ((requireActivity().getIntent().hasExtra("openSettings") && requireActivity().getIntent().getBooleanExtra("openSettings", false))) {
            navController.navigate(getNavDirections(SEARCHER_FRAGMENT, SETTINGS_FRAGMENT));
            requireActivity().getIntent().removeExtra("city");
            requireActivity().getIntent().removeExtra("openSettings");
        }

        if(pubListViewModel.getSearcherUiState().getValue().getLinkPubId() != null){
            int n = 0;
            for(var pub:pubListViewModel.get_originalPubData().getValue()){
                if(pubListViewModel.getSearcherUiState().getValue().getLinkPubId() == pub.getId()){
                    pubListViewModel.getSearcherUiState().getValue().setLinkPubId(null);
                    pubListViewModel.setPubDetails(n, detailsViewModel);
                    NavHostFragment.findNavController(requireParentFragment()).navigate(SearcherFragmentDirections.actionSearcherToDetails());
                    break;
                }
                n+=1;
            }
            if(pubListViewModel.get_originalPubData().getValue().size() == n){
                pubListViewModel.getSearcherUiState().getValue().setLinkPubId(null);
                Log.e(TAG, "App Link got invalid Pub Id for city");
            }
        }

    }

    private void setUpRecyclerViews(){
        swipeRefreshLayout.setOnRefreshListener(()->{
            swipeRefreshLayout.setRefreshing(true);
            pubListViewModel.fetchPubsFromRepo(REFRESH_MIN_TIME_MS);
            if(pubListViewModel.getSearcherUiState().getValue().getSearchedPubs().getValue() != null)
                pubListViewModel.sort(pubListViewModel.getSearcherUiState().getValue().getSortPubsBy().getValue());
        });

        swipeRefreshLayout.setRefreshing(true);
        adapter = new ListPubAdapter(pubListViewModel.getSortedAndFilteredPubsUiState().getValue(),this, pubListViewModel.getSearcherUiState().getValue().getChipTag());
        recycler.setAdapter(adapter);

        pubListViewModel.getSortedAndFilteredPubsUiState().observe(getViewLifecycleOwner(), pubs-> {
            if(pubs==null || pubs.getPubItems()==null|| pubs.getPubItems().isEmpty())
                recycler.setVisibility(View.GONE);
            else {
                adapter = new ListPubAdapter(pubs,this, pubListViewModel.getSearcherUiState().getValue().getChipTag());
                recycler.setAdapter(adapter);
                recycler.setVisibility(View.VISIBLE);
                pubListViewModel.getSearcherUiState().getValue().getSearchedPubs().setValue(new Pair<>(pubs, false));
            }
            swipeRefreshLayout.setRefreshing(false);
        });
        pubListViewModel.getSearcherUiState().getValue().getSearchedPubs().observe(getViewLifecycleOwner(), pubs -> {
            if(pubs.second) {
                if (pubs == null || pubs.first.getPubItems() == null || pubs.first.getPubItems().isEmpty())
                    recycler.setVisibility(View.GONE);
                else {
                    adapter = new ListPubAdapter(pubs.first, this, pubListViewModel.getSearcherUiState().getValue().getChipTag());
                    recycler.setAdapter(adapter);
                    recycler.setVisibility(View.VISIBLE);
                }
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        pubListViewModel.getSearcherUiState().getValue().getSortPubsBy().observe(getViewLifecycleOwner(), type->{
            if(pubListViewModel.getSearcherUiState().getValue().getSearchedPubs().getValue() != null)
                pubListViewModel.sort(type);
        });
    }

    private void setUpTopAppBar(){
        MaterialToolbar topAppBar = requireView().findViewById(R.id.searcher_topAppBarView);
        topAppBar.setOnMenuItemClickListener(menuItem->{
            if(menuItem.getItemId()==R.id.account_item){
                if(requireActivity().findViewById(R.id.main_bottomNavView).isShown())
                    NavigationBar.smoothHide(requireActivity().findViewById(R.id.main_bottomNavView), 200);
                navController.navigate(getNavDirections(SEARCHER_FRAGMENT,ACCOUNT_DETAILS_FRAGMENT));
                return true;
            }
            if(menuItem.getItemId()==R.id.settings_item){
                if(requireActivity().findViewById(R.id.main_bottomNavView).isShown())
                    NavigationBar.smoothHide(requireActivity().findViewById(R.id.main_bottomNavView), 200);
                navController.navigate(getNavDirections(SEARCHER_FRAGMENT,SETTINGS_FRAGMENT));
                return true;
            }
            if(menuItem.getItemId()==R.id.expand_item){
                PopupMenu popupMenu = new PopupMenu(new ContextThemeWrapper(requireContext(), R.style.CustomPopupMenu), topAppBar.findViewById(menuItem.getItemId()));
                popupMenu.getMenu().add(0, 1, 0, getString(R.string.dictionary)).setIcon(R.drawable.ic_dictionary);
                enablePopupMenuIcons(popupMenu);
                popupMenu.setOnMenuItemClickListener((item -> {
                    if(item.getItemId() == 1){
                        if(requireActivity().findViewById(R.id.main_bottomNavView).isShown())
                            NavigationBar.smoothHide(requireActivity().findViewById(R.id.main_bottomNavView), 200);
                        navController.navigate(getNavDirections(SEARCHER_FRAGMENT,DICTIONARY_FRAGMENT));
                        return true;
                    }
                    return false;
                }));
                popupMenu.show();
                return true;
            }
            return false;
        });
        topAppBar.setTitle(pubListViewModel.getCityConstraint().getValue().toString());
        topAppBar.setNavigationOnClickListener(v->{
            navController.popBackStack();
        });
    }

    private void enablePopupMenuIcons(PopupMenu popupMenu) {
        try {
            Field[] fields = popupMenu.getClass().getDeclaredFields();
            for (Field field : fields) {
                if ("mPopup".equals(field.getName())) {
                    field.setAccessible(true);
                    Object menuPopupHelper = field.get(popupMenu);
                    Class<?> classPopupHelper = Class.forName(menuPopupHelper.getClass().getName());
                    Method setForceIcons = classPopupHelper.getMethod("setForceShowIcon", boolean.class);
                    setForceIcons.invoke(menuPopupHelper, true);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
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

        requireView().findViewById(R.id.searcher_cl_content).setOnClickListener(v->{
            if (!searchview.isIconified()){
                searchview.setIconified(true);
                InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(requireView().getWindowToken(), 0);
                }
                searchview.clearFocus();
            }
        });

        recycler.setOnTouchListener((view, motionEvent) -> {
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

    private void setUpListeners()
    {
        requireView().findViewById(R.id.searcher_text_sortBy).setOnClickListener(v -> showSortBottomSheer());
        requireView().findViewById(R.id.searcher_image_sortBy).setOnClickListener(v -> showSortBottomSheer());
        requireView().findViewById(R.id.searcher_image_filtration).setOnClickListener(v -> navController.navigate(SearcherFragmentDirections.actionSearcherToFilter()));

        requireActivity().findViewById(R.id.searcher_FAB_map).setOnClickListener(v -> {
            navController.navigate(getNavDirections(SEARCHER_FRAGMENT,MAP_FRAGMENT));
        });
    }
    private void showSortBottomSheer(){
        ((ImageView)requireView().findViewById(R.id.searcher_image_sortBy)).setImageResource(R.drawable.ic_expand_less_primary);
        View bottomSheetView = getLayoutInflater().inflate(R.layout.fragment_searcher_sort_bottom_sheet, null);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(requireContext());
        bottomSheetDialog.setContentView(bottomSheetView);
        buttonsListenersForSortPopUpWindow(bottomSheetDialog, bottomSheetView);
        checkCurrentSortRadioButtons(bottomSheetView);
        bottomSheetDialog.show();
    }
    private void checkCurrentSortRadioButtons(View bottomSheetView) {
        String word = ((TextView) requireView().findViewById(R.id.searcher_text_sortBy)).getText().toString();
        if(getString(R.string.sort_relevance).equals(word)) {
            ((RadioButton) bottomSheetView.findViewById(R.id.searcherBSh_radioButton_relevance)).setChecked(true);
        }
        if(getString(R.string.sort_rating).equals(word)) {
            ((RadioButton) bottomSheetView.findViewById(R.id.searcherBSh_radioButton_rating)).setChecked(true);
        }
        if(getString(R.string.sort_alphabetical).equals(word)) {
            ((RadioButton) bottomSheetView.findViewById(R.id.searcherBSh_radioButton_alphabetical)).setChecked(true);
        }
        if(getString(R.string.sort_distance).equals(word)) {
            ((RadioButton) bottomSheetView.findViewById(R.id.searcherBSh_radioButton_distance)).setChecked(true);
        }

    }
    private void buttonsListenersForSortPopUpWindow(BottomSheetDialog bottomSheetDialog, View bottomSheetView) {

        for(var radioButtonId:SORT_POP_UP_IDS){
            bottomSheetView.findViewById(radioButtonId).setOnClickListener(v->{
                for(var id: SORT_POP_UP_IDS){
                    ((RadioButton)bottomSheetView.findViewById(id)).setChecked(false);
                }
                ((RadioButton)v).setChecked(true);
            });
        }
        bottomSheetDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                ((ImageView)requireView().findViewById(R.id.searcher_image_sortBy)).setImageResource(R.drawable.ic_expand_more_primary);
                TextView text = requireActivity().findViewById(R.id.searcher_text_sortBy);
                if (((RadioButton) bottomSheetView.findViewById(R.id.searcherBSh_radioButton_relevance)).isChecked()) {
                    setSortTextView(SortPubsBy.RELEVANCE);
                } else if (((RadioButton) bottomSheetView.findViewById(R.id.searcherBSh_radioButton_alphabetical)).isChecked()) {
                    setSortTextView(SortPubsBy.ALPHABETICAL);
                } else if (((RadioButton) bottomSheetView.findViewById(R.id.searcherBSh_radioButton_rating)).isChecked()) {
                    setSortTextView(SortPubsBy.RATING);
                } else{
                    setSortTextView(SortPubsBy.DISTANCE);
                }
            }
        });
    }

    public void setSortTextView(SortPubsBy sortingBy){
        TextView text = requireActivity().findViewById(R.id.searcher_text_sortBy);
        switch(sortingBy) {
            case RELEVANCE:
                text.setText( requireActivity().getString(R.string.sort_relevance));
                pubListViewModel.getSearcherUiState().getValue().getSortPubsBy().setValue(SortPubsBy.RELEVANCE);
                break;
            case ALPHABETICAL:
                text.setText(R.string.sort_alphabetical);
                pubListViewModel.getSearcherUiState().getValue().getSortPubsBy().setValue(SortPubsBy.ALPHABETICAL);
                break;
            case RATING:
                text.setText(getString(R.string.sort_rating));
                pubListViewModel.getSearcherUiState().getValue().getSortPubsBy().setValue(SortPubsBy.RATING);
                break;
            case DISTANCE:
                text.setText(getString(R.string.sort_distance));
                pubListViewModel.getSearcherUiState().getValue().getSortPubsBy().setValue(SortPubsBy.DISTANCE);
                break;
        }

    }



    @Override
    public void onResume() {
        super.onResume();
        requireActivity().findViewById(R.id.main_topAppBarLayout_back).setVisibility(View.GONE);
        if(pubListViewModel.getSearcherUiState().getValue().getSearchedPubs().getValue() != null)
            setSortTextView(pubListViewModel.getSearcherUiState().getValue().getSortPubsBy().getValue());
        if(requireActivity().findViewById(R.id.main_bottomNavView).getVisibility()==View.GONE)
            NavigationBar.smoothPopUp(requireActivity().findViewById(R.id.main_bottomNavView), 200);

        if(detailsViewModel.getOpenedPubPosition() != null) {
            recycler.scrollToPosition(detailsViewModel.getOpenedPubPosition());
            detailsViewModel.setOpenedPubPosition(null);
        }

    }




    @Override
    public void onItemClicked(int position) {
        View imageView = recycler.getChildAt(position).findViewById(R.id.pubRVR_image);
        int[] location = new int[2];
        imageView.getLocationOnScreen(location);
        ConstraintLayout constraintLayout = requireView().findViewById(R.id.searcher_cl_content);
        int[] layoutLocation = new int[2];
        constraintLayout.getLocationOnScreen(layoutLocation);

        ConstraintLayout view = new ConstraintLayout(requireContext());
        view.setId(View.generateViewId());
        view.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.surface));
        view.setLayoutParams( new Constraints.LayoutParams(
                (int)DimensionsConverter.pxFromDp(requireContext(), 84),
                (int)DimensionsConverter.pxFromDp(requireContext(), 82)
        ));
        constraintLayout.addView(view);
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(constraintLayout);
        constraintSet.connect(view.getId(), ConstraintSet.START, R.id.searcher_cl_content, ConstraintSet.START,
                (int)DimensionsConverter.pxFromDp(requireContext(), 25));
        constraintSet.connect(view.getId(), ConstraintSet.TOP, R.id.searcher_cl_content, ConstraintSet.TOP,
                 location[1]-layoutLocation[1]);
        constraintSet.applyTo(constraintLayout);
        view.setZ(2);
        requireView().findViewById(R.id.searcher_cardView_filtration).setZ(1);
        requireView().findViewById(R.id.searcher_cardView_search).setZ(1);
        requireView().findViewById(R.id.searcher_FAB_map).setVisibility(View.INVISIBLE);




        view.post(new Runnable() {
            @Override
            public void run() {

                int screenWidth = getResources().getDisplayMetrics().widthPixels;
                int screenHeight = getResources().getDisplayMetrics().heightPixels;
                float scaleX = (float) screenWidth / view.getWidth();
                float scaleY = (float) screenHeight / view.getHeight();


                ScaleAnimation scaleAnimation = new ScaleAnimation(
                        1f, scaleX+1, 1f, scaleY+1,
                        ScaleAnimation.RELATIVE_TO_SELF, DimensionsConverter.pxFromDp(requireContext(), 25)/screenWidth,
                        ScaleAnimation.RELATIVE_TO_SELF, (float)location[1]/(float)screenHeight);
                scaleAnimation.setDuration(300);
                scaleAnimation.setFillAfter(true);
                scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {}

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        NavigationBar.hideTransitionTopAndBottomBar(requireView().findViewById(R.id.searcher_topAppBarLayout), requireView().findViewById(R.id.searcher_topAppBarView), requireActivity().findViewById(R.id.main_bottomNavView),(int)(300*((float)location[1]/(float)screenHeight)));
                        view.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                NavHostFragment.findNavController(requireParentFragment()).navigate(SearcherFragmentDirections.actionSearcherToDetails());
                            }
                        }, (int)(300*((float)location[1]/(float)screenHeight)));
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {}
                });


                view.startAnimation(scaleAnimation);


            }


        });
        detailsViewModel.setOpenedPubPosition(position);
        pubListViewModel.setPubDetails(position,detailsViewModel);
    }

}