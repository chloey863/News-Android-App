package com.chloeproject.newstime.ui.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chloeproject.newstime.R;
import com.chloeproject.newstime.databinding.FragmentHomeBinding;
import com.chloeproject.newstime.model.NewsResponse;
import com.chloeproject.newstime.repository.NewsRepository;
import com.chloeproject.newstime.repository.NewsViewModelFactory;

public class HomeFragment extends Fragment {
    private HomeViewModel viewModel;

    private FragmentHomeBinding binding;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // Note: LayoutInflater will translate the layout (written in xml) to Java language
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();

//        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    /**
     * use the ViewModels (from factory) in the Home Fragment
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        // refer to fragment lifecycle
        super.onViewCreated(view, savedInstanceState);

        NewsRepository repository = new NewsRepository(getContext());

        // use the viewModel from factory to create a viewModel for this Home Fragment
        viewModel = new ViewModelProvider(this, new NewsViewModelFactory(repository))
                .get(HomeViewModel.class);

        // set country as United States
        viewModel.setCountryInput("us");

        // get top headlines data to viewModel
        viewModel.getTopHeadlines().observe(getViewLifecycleOwner(), new Observer<NewsResponse>() {
            @Override // or use a Lambda function
            public void onChanged(NewsResponse newsResponse) {
                if (newsResponse != null) {
                    Log.d("HomeFragment", newsResponse.toString());
                }
            }
        });
    }

}