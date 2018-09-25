package fr.uha.ensisa.huynhphuc.mynews;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class ArticlesListActivity extends AppCompatActivity {

    private ArrayList<Article> articles = new ArrayList<Article>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles_list);

        Bundle bundle = getIntent().getExtras();

        articles.addAll(bundle.<Article>getParcelableArrayList("data"));
        Log.i("articles into Listview ",articles.toString());

        final ListView listView = (ListView) findViewById(R.id.articlesList);
        ArticlesAdapter adapter = new ArticlesAdapter(this,articles);
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
