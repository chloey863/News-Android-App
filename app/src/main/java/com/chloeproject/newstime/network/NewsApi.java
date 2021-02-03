package com.chloeproject.newstime.network;

import com.chloeproject.newstime.model.NewsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * This interface acts as a CLIENT to the newsapi.org ("A free Restful API that returns JSON search
 * results for live and historic news articles from over 75,000 worldwide sources.")
 */
public interface NewsApi {

    @GET("top-headlines")
    Call<NewsResponse> getTopHeadlines(@Query("country") String country);

    @GET("everything")
    Call<NewsResponse> getEverything(
            @Query("q") String query, @Query("pageSize") int pageSize);
}

