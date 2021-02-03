package com.chloeproject.newstime;

import android.app.Application;

import androidx.room.Room;

import com.ashokvarma.gander.Gander;
import com.ashokvarma.gander.imdb.GanderIMDB;
import com.chloeproject.newstime.database.NewsTimeDatabase;
import com.facebook.stetho.Stetho;

public class NewsTimeApplication extends Application {

    // create a database in the NewsTimeApplication, where the program is initialized.
    private NewsTimeDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();

        // initialization code for Gander debugger
        Gander.setGanderStorage(GanderIMDB.getInstance());

        // initialization code for Stetho debugger
        Stetho.initializeWithDefaults(this);

        // build a database
        database = Room.databaseBuilder(this, NewsTimeDatabase.class, "newstime_db").build();
    }

    public NewsTimeDatabase getDatabase() {
        return database;
    }
}
