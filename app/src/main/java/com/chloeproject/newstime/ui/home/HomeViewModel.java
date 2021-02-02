package com.chloeproject.newstime.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.chloeproject.newstime.model.NewsResponse;
import com.chloeproject.newstime.repository.NewsRepository;

public class HomeViewModel extends ViewModel {
    private final NewsRepository repository;
    private final MutableLiveData<String> countryInput = new MutableLiveData<>();

    public HomeViewModel(NewsRepository newsRepository) {
        this.repository = newsRepository;
    }

    /**
     * setCountryInput will be called by View
     */
    public void setCountryInput(String country) {
        countryInput.setValue(country);
    }

    /**
     * Mapping a response string to the format in repository's getTopHeadlines
     */
    public LiveData<NewsResponse> getTopHeadlines() {
        return Transformations.switchMap(countryInput, repository::getTopHeadlines);
    }
}

