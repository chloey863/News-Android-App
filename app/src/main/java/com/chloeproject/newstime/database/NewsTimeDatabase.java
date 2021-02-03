package com.chloeproject.newstime.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.chloeproject.newstime.model.Article;

// entities specifies the tables the database contains.
// version specifies a current version. Need to increase the version and define the migration
// strategy if introducing / modifying the current version.
// exportSchema option is for dumping a database schema to file system, no need.

@Database(entities = {Article.class}, version = 1, exportSchema = false)
public abstract class NewsTimeDatabase extends RoomDatabase {

    // abstract because, we do not implement it. The Room annotation processor will.
    public abstract ArticleDao articleDao();
}
