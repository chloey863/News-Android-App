package com.chloeproject.newstime.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.chloeproject.newstime.model.NewsResponse;
import com.chloeproject.newstime.network.NewsApi;
import com.chloeproject.newstime.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsRepository {

    private final NewsApi newsApi;

    public NewsRepository(Context context) {
        newsApi = RetrofitClient.newInstance(context).create(NewsApi.class);
    }

    /**
     * Implementing getTopHeadlines API
     */
    public LiveData<NewsResponse> getTopHeadlines(String country) {
        // MutableLiveData is a data-stream, an object to be observed, can be setValue()
        MutableLiveData<NewsResponse> topHeadlinesLiveData = new MutableLiveData<>();

        // getting topHeadlines from News API:
        newsApi.getTopHeadlines(country)
                .enqueue(new Callback<NewsResponse>() {
                    // if request is valid, set the response to MutableLiveData
                    @Override
                    public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                        if (response.isSuccessful()) {
                            topHeadlinesLiveData.setValue(response.body());
                        } else {
                            topHeadlinesLiveData.setValue(null);
                        }
                    }
                    // if request has error, set the error msg to MutableLiveData
                    @Override
                    public void onFailure(Call<NewsResponse> call, Throwable t) {
                        topHeadlinesLiveData.setValue(null);
                    }
                });
        return topHeadlinesLiveData;
    }

    /**
     * Implement searchNews API
     */
    public LiveData<NewsResponse> searchNews(String query) {
        MutableLiveData<NewsResponse> everyThingLiveData = new MutableLiveData<>();
        newsApi.getEverything(query, 40)
                .enqueue(
                        new Callback<NewsResponse>() {
                            @Override
                            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                                if (response.isSuccessful()) {
                                    everyThingLiveData.setValue(response.body());
                                } else {
                                    everyThingLiveData.setValue(null);
                                }
                            }

                            @Override
                            public void onFailure(Call<NewsResponse> call, Throwable t) {
                                everyThingLiveData.setValue(null);
                            }
                        });
        return everyThingLiveData;
    }
}
