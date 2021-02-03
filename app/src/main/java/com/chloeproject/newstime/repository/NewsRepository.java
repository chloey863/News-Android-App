package com.chloeproject.newstime.repository;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.chloeproject.newstime.NewsTimeApplication;
import com.chloeproject.newstime.database.NewsTimeDatabase;
import com.chloeproject.newstime.model.Article;
import com.chloeproject.newstime.model.NewsResponse;
import com.chloeproject.newstime.network.NewsApi;
import com.chloeproject.newstime.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * NewsRepository to support Retrofit, contains all Model for all fragments
 */
public class NewsRepository {

    private final NewsApi newsApi;
    private final NewsTimeDatabase database;

    public NewsRepository(Context context) {
        newsApi = RetrofitClient.newInstance(context).create(NewsApi.class);

        //The database instance is provided by casting the application context into NewsTimeApplication.
        database = ((NewsTimeApplication) context.getApplicationContext()).getDatabase();
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

    /**
     * Functionality: save (favorite) article that user likes.
     * Note: Database query accessing the disk storage can be very slow sometimes. We do not want
     * it to run on the default main UI thread. So we use an AsyncTask to dispatch the query work
     * to a background thread.
     * (Manually implement ASYNC)
     */
    private static class FavoriteAsyncTask<Article> extends AsyncTask<Article, Void, Boolean> {

        private final NewsTimeDatabase database;
        private final MutableLiveData<Boolean> liveData;

        private FavoriteAsyncTask(NewsTimeDatabase database, MutableLiveData<Boolean> liveData) {
            this.database = database;
            this.liveData = liveData;
        }

        /**
         * Implement doInBackground() for AsyncTask Interface
         * Everything inside doInBackground would be executed on a separate "background" thread.
         * @param: Article... (var args)
         */
        @Override
        protected Boolean doInBackground(Article... articles) {
            Article article = articles[0];
            try {
                // fixme:
                database.articleDao().saveArticle((com.chloeproject.newstime.model.Article) article);
            } catch (Exception e) {
                return false;
            }
            return true;
        }

        /**
         * After doInBackground finishes, onPostExecute would be executed back on the main UI
         * "foreground" thread.
         */
        @Override
        protected void onPostExecute(Boolean success) {
            liveData.setValue(success);
        }
    }

    /**
     * API for saving the favorite live data (article) to database
     */
    public LiveData<Boolean> favoriteArticle(Article article) {
        MutableLiveData<Boolean> resultLiveData = new MutableLiveData<>();
        new FavoriteAsyncTask(database, resultLiveData).execute(article);
        return resultLiveData;
    }
}
