package com.chloeproject.newstime.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.chloeproject.newstime.model.Article;

import java.util.List;

/**
 * ArticleDao specifies the operations needed to access the article table
 */
@Dao
public interface ArticleDao {

    @Insert
    void saveArticle(Article article);

    /**
     * Returning (real-time, continuously update) live data of articles
     */
    @Query("SELECT * FROM article")
    LiveData<List<Article>> getAllArticles();

    @Delete
    void deleteArticle(Article article);

}
