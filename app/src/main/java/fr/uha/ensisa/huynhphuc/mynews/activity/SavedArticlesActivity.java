package fr.uha.ensisa.huynhphuc.mynews.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import fr.uha.ensisa.huynhphuc.mynews.DataHolder;
import fr.uha.ensisa.huynhphuc.mynews.R;
import fr.uha.ensisa.huynhphuc.mynews.adapter.SavedArticlesAdapter;
import fr.uha.ensisa.huynhphuc.mynews.database.Article;
import fr.uha.ensisa.huynhphuc.mynews.database.NewsViewModel;

public class SavedArticlesActivity extends AppCompatActivity {

    private ArrayList<Article> saved_articles;
    private SavedArticlesAdapter adapter;
    private NewsViewModel newsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_articles);

        newsViewModel = ViewModelProviders.of(this).get(NewsViewModel.class);

        newsViewModel.getSavedArticles().observe(this, new Observer<List<Article>>() {
            @Override
            public void onChanged(@Nullable final List<Article> articles) {
                // Update the cached copy of the words in the adapter.
                Log.d("NewsViewModel", "Saved changed");
                adapter.notifyDataSetChanged();
            }
        });

        this.saved_articles = DataHolder.getSavedArticles();

        final ListView listView = (ListView) findViewById(R.id.savedList);
        if (DataHolder.getSavedArticles().isEmpty()) {
            TextView emptyText = (TextView) findViewById(R.id.empty_saved);
            listView.setEmptyView(emptyText);
        } else {
            this.adapter = new SavedArticlesAdapter(this, saved_articles);
            listView.setAdapter(adapter);
        }

        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Article article = (Article) listView.getItemAtPosition(position);
                String url = article.getUrl();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            Intent intent = new Intent(this, MainActivity.class);
            DataHolder.getSavedArticles().removeAll(DataHolder.getToDelete());
            DataHolder.getToDelete().clear();

            startActivity(intent);
        }
        return super.onKeyDown(keyCode, event);
    }


}
