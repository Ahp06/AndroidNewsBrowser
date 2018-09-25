package fr.uha.ensisa.huynhphuc.mynews;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.constraint.Group;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    private EditText query;

    public class ArticleHttpRequest extends AsyncTask<String,Integer,String> {

        //private ProgressBar bar;
        private ArrayList<Article> articlesList = new ArrayList<Article>();

        @Override
        protected String doInBackground(String... urls) {
            String result = "";
            URL url;
            HttpsURLConnection urlConnection = null;

            try {
                url = new URL(urls[0]);
                urlConnection = (HttpsURLConnection) url.openConnection();

                if (result != null) {
                    String response = read(urlConnection.getInputStream());
                    this.parseJSON(response);
                    return result;
                }
            } catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        public void onPostExecute(String msg){
            final EditText editText = (EditText) findViewById(R.id.query);
            Intent intent = new Intent(editText.getContext(),ArticlesListActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("data", articlesList);
            intent.putExtras(bundle);
            startActivity(intent);

            editText.getText().clear();
        }

        /***
         * Read the URL stream and return all articles into a String (in JSON format)
         * @param stream
         * @return
         * @throws IOException
         */
        public String read(InputStream stream) throws IOException {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
            String line;
            String result = "";
            while ((line = bufferedReader.readLine()) != null) {
                result += line;
            }

            // Close stream
            if (null != stream) {
                stream.close();
            }
            return result;
        }

        /***
         * Parse the string result in JSON format and create a list of article object
         * @param result
         */
        public void parseJSON(String result) {
            try {
                JSONObject response = new JSONObject(result);
                JSONArray articles = response.optJSONArray("articles");

                for(int i = 0 ; i < articles.length() ; i++){
                    Article article = new Article(articles.optJSONObject(i));
                    articlesList.add(article);
                    //Log.i("Articles"+i ,article.toString());
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = this.getMenuInflater();
        inflater.inflate(R.menu.settings_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        if(item.getItemId() == R.id.settings_item){
            Intent intent = new Intent(this,SettingsActivity.class);
            startActivity(intent);
        }

        /*if(item.isChecked()){
            item.setChecked(false);
        }else {
            item.setChecked(true);
            //Toast.makeText(getApplicationContext(),item.getTitle(),Toast.LENGTH_LONG).show();
        }*/

        return super.onOptionsItemSelected(item);
    }

    public void executeQueryWithSettings(EditText edit_query){

        /*Group category_group = (Group) this.findViewById(R.id.category_group);
        Group country_group = (Group) this.findViewById(R.id.country_group);
        Group pageSize_group = (Group) this.findViewById(R.id.pageSize_group);
        */

        Settings settings = new Settings();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.query = findViewById(R.id.query);
        query.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    String queryText = query.getText().toString();
                    Log.d("Your query : " , queryText);
                    String url =  "https://newsapi.org/v2/everything?q="
                            + queryText + "&" +
                            "from=2018-09-14&"+
                            "language=fr&" +
                            "apiKey=18b73b4602ee45b0a0d206ff0c619d23";

                    new ArticleHttpRequest().execute(url);

                    handled = true;
                }
                return handled;
            }
        });

        Button btn = (Button) findViewById(R.id.queryButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String queryText = query.getText().toString();
                Log.d("Your query : " , queryText);
                String url =  "https://newsapi.org/v2/everything?q="
                        + queryText + "&" +
                        "from=2018-09-14&"+
                        "language=fr&" +
                        "apiKey=18b73b4602ee45b0a0d206ff0c619d23";

                new ArticleHttpRequest().execute(url);
            }
        });


    }
}
