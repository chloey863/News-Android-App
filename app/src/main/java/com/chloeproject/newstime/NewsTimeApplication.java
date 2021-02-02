package com.chloeproject.newstime;

import android.app.Application;

import com.ashokvarma.gander.Gander;
import com.ashokvarma.gander.imdb.GanderIMDB;
import com.facebook.stetho.Stetho;

public class NewsTimeApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // initialization code for Gander debugger
        Gander.setGanderStorage(GanderIMDB.getInstance());

        // initialization code for Stetho debugger
        Stetho.initializeWithDefaults(this);
    }
}
