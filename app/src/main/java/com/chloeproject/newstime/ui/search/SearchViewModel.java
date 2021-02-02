package com.chloeproject.newstime.ui.search;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.chloeproject.newstime.model.NewsResponse;
import com.chloeproject.newstime.repository.NewsRepository;

public class SearchViewModel extends ViewModel {

    private final NewsRepository repository;
    private final MutableLiveData<String> searchInput = new MutableLiveData<>();

    public SearchViewModel(NewsRepository newsRepository) {
        this.repository = newsRepository;
    }

    /**
     * setSearchInput will be called by View
     */
    public void setSearchInput(String query) {
        searchInput.setValue(query);
    }

    /**
     * Mapping a response string to the format in repository's searchNews
     */
    public LiveData<NewsResponse> searchNews() {
        return Transformations.switchMap(searchInput, repository::searchNews);
    }
}
