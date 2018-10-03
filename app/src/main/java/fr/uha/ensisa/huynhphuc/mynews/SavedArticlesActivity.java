package fr.uha.ensisa.huynhphuc.mynews;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class SavedArticlesActivity extends AppCompatActivity {

    private ArrayList<Article> saved_articles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_articles);

        saved_articles = new ArrayList<Article>();
        if(this.getIntent().hasExtra("saved")){
            Bundle bundle = getIntent().getExtras();
            saved_articles.addAll(bundle.<Article>getParcelableArrayList("saved"));
        }

        final ListView listView = (ListView) findViewById(R.id.savedList);
        SavedArticlesAdapter adapter = new SavedArticlesAdapter(this,saved_articles);
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

    // à implémenter car si l'util supprime une sauvegarde alors la liste à changée
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            /*Intent intent = new Intent(this,MainActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("saved", this.getArticlesSaved());
            intent.putExtras(bundle);
            startActivity(intent);*/
        }
        return super.onKeyDown(keyCode, event);
    }
}
