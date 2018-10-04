package fr.uha.ensisa.huynhphuc.mynews;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.res.TypedArrayUtils;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ArticlesListActivity extends AppCompatActivity {

    private ArrayList<Article> articles = new ArrayList<Article>();
    private ArrayList<Article> savedArticles = new ArrayList<Article>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles_list);

        Bundle bundle = getIntent().getExtras();
        articles.addAll(bundle.<Article>getParcelableArrayList("data"));

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

    /**
     * Return the list of articles saved
     * @return
     */
    public ArrayList<Article> getArticlesSaved(){
        ArrayList<Article> articlesSaved = new ArrayList<Article>();
        for(Article article : articles){
            if(article.isSaved()){
                articlesSaved.add(article);
            }
        }
        return articlesSaved;
    }

    /**
     * On key down will give the list of saved articles to the Main Activity
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            Intent intent = new Intent(this,MainActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("saved", this.getArticlesSaved());
            intent.putExtras(bundle);
            startActivity(intent);
        }
        return super.onKeyDown(keyCode, event);
    }
}
