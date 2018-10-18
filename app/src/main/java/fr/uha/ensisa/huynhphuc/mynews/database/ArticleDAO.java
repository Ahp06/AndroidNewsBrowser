package fr.uha.ensisa.huynhphuc.mynews.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.ArrayList;
import java.util.List;

import fr.uha.ensisa.huynhphuc.mynews.database.Article;

@Dao
public interface ArticleDAO {

    @Insert
    public void insert(Article article);

    @Query("SELECT * from saved_article")
    LiveData<List<Article>> getSavedArticles();

    @Query("DELETE FROM saved_article WHERE saved_ID = :ID")
    void deleteSaved(int ID);


}
