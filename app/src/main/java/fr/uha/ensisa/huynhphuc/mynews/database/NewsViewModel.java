package fr.uha.ensisa.huynhphuc.mynews.database;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class NewsViewModel extends AndroidViewModel {

    private NewsRepository mRepository;

    private LiveData<List<Article>> saved_articles;

    public NewsViewModel (Application application) {
        super(application);
        mRepository = new NewsRepository(application);
        saved_articles = mRepository.getSavedArticles();
    }

    public LiveData<List<Article>> getSavedArticles() { return saved_articles; }

    public void insert(Article article) { mRepository.insert(article); }
}
