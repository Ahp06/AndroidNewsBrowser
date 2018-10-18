package fr.uha.ensisa.huynhphuc.mynews.database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class NewsRepository {

    private ArticleDAO articleDAO;
    private LiveData<List<Article>> saved_articles;

    NewsRepository(Application application) {
        NewsRoomDatabase db = NewsRoomDatabase.getDatabase(application);
        articleDAO = db.articleDAO();
        saved_articles = articleDAO.getSavedArticles();
    }

    LiveData<List<Article>> getSavedArticles() {
        return saved_articles;
    }

    public void insert (Article article) {
        new insertAsyncTask(articleDAO).execute(article);
    }

    private static class insertAsyncTask extends AsyncTask<Article, Void, Void> {

        private ArticleDAO mAsyncTaskDao;

        insertAsyncTask(ArticleDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Article... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

}
