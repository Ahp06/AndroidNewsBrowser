package fr.uha.ensisa.huynhphuc.mynews;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class SavedArticlesActivity extends AppCompatActivity {

    private ArrayList<Article> saved_articles = new ArrayList<Article>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_articles);

        Bundle bundle = getIntent().getExtras();

        saved_articles.addAll(bundle.<Article>getParcelableArrayList("data"));
        Log.i("articles into Listview ",saved_articles.toString());

        final ListView listView = (ListView) findViewById(R.id.articlesList);
        ArticlesAdapter adapter = new ArticlesAdapter(this,saved_articles);
        listView.setAdapter(adapter);

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
}