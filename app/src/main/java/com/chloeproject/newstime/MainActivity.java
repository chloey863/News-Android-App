package com.chloeproject.newstime;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.util.Log;

import com.chloeproject.newstime.model.NewsResponse;
import com.chloeproject.newstime.network.NewsApi;
import com.chloeproject.newstime.network.RetrofitClient;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);

        navController = navHostFragment.getNavController();

        // bind the bottomNavigationView and navController
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        // then, bind action bar and navController (update: removed to avoid crashing when
        // removing actionBar in themes.xml)
//        NavigationUI.setupActionBarWithNavController(this, navController);


//        // Testing a request to News API -> if success, will show in the notification drop-down bar:
//        // But this testing code should be in "Model", not in this "View"; Therefore, it's migrated to "NewsRepository" class
//        NewsApi newsApi = RetrofitClient.newInstance(this).create(NewsApi.class);
//        newsApi.getTopHeadlines("US").enqueue(new Callback<NewsResponse>() {
//            // if response is valid
//            @Override
//            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
//                if (response.isSuccessful()) {
//                    Log.d("getTopHeadlines", response.body().toString());
//                } else {
//                    Log.d("getTopHeadlines", response.toString());
//                }
//            }
//            // if response has error
//            @Override
//            public void onFailure(Call<NewsResponse> call, Throwable t) {
//                Log.d("getTopHeadlines", t.toString());
//            }
//        });
    }


    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp();
    }
}