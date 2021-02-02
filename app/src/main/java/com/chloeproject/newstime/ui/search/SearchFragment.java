package com.chloeproject.newstime.ui.search;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chloeproject.newstime.R;
import com.chloeproject.newstime.repository.NewsRepository;
import com.chloeproject.newstime.repository.NewsViewModelFactory;

public class SearchFragment extends Fragment {

    private SearchViewModel viewModel;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    /**
     * use the ViewModels (from factory) in the Search Fragment
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        // refer to fragment lifecycle
        super.onViewCreated(view, savedInstanceState);

        NewsRepository repository = new NewsRepository(getContext());

        // use the viewModel from factory to create a viewModel for this Search Fragment
        viewModel = new ViewModelProvider(this, new NewsViewModelFactory(repository))
                .get(SearchViewModel.class);

        // set the query to search as "Covid-19"
        viewModel.setSearchInput("Covid-19");

        // get searchNews() data to viewModel
        viewModel
                .searchNews()
                .observe(
                        getViewLifecycleOwner(),
                        newsResponse -> { // or replace this Lambda with a anonymous class
                            if (newsResponse != null) {
                                Log.d("SearchFragment", newsResponse.toString());
                            }
                        });
    }
}