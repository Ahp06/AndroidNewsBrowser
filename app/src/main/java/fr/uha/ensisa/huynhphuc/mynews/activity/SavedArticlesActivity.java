package fr.uha.ensisa.huynhphuc.mynews.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import fr.uha.ensisa.huynhphuc.mynews.utils.DataHolder;
import fr.uha.ensisa.huynhphuc.mynews.R;
import fr.uha.ensisa.huynhphuc.mynews.adapter.SavedArticlesAdapter;
import fr.uha.ensisa.huynhphuc.mynews.model.Article;

public class SavedArticlesActivity extends AppCompatActivity {

    private ArrayList<Article> saved_articles;
    private SavedArticlesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_articles);

        this.saved_articles = DataHolder.readSaved(this.getApplicationContext());

        Log.d("Read saved", "saved = " + this.saved_articles);

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
            DataHolder.writeSaved(getApplicationContext());

            startActivity(intent);
        }
        return super.onKeyDown(keyCode, event);
    }


}
