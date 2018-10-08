package fr.uha.ensisa.huynhphuc.mynews;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
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
import java.util.Calendar;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    private EditText query;
    private boolean backPressedTwice = false;

    public class ArticleHttpRequest extends AsyncTask<String,Integer,String> {

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

                ArrayList<Article> articlesList = new ArrayList<Article>();
                for(int i = 0 ; i < articles.length() ; i++){
                    Article article = new Article(articles.optJSONObject(i));
                    articlesList.add(article);
                }

                DataHolder.setArticlesList(articlesList);


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

        if(item.getItemId() == R.id.saved_item){
            Intent intent = new Intent(this,SavedArticlesActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    /***
     *
     * Execute the query with all settings by launching an ArticleHttpRequest AsyncTask
     */
    public void executeQueryWithSettings(){
        String query = this.query.getText().toString();
        Log.d("Full query = ", DataHolder.getSettings().applySettings(query));
        new ArticleHttpRequest().execute(DataHolder.getSettings().applySettings(query));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if(DataHolder.getSettings() == null){
            Calendar current = Calendar.getInstance();
            int current_year = current.get(Calendar.YEAR);
            int current_day = current.get(Calendar.DAY_OF_MONTH);
            int current_month = current.get(Calendar.MONTH);
            //Current date
            String to = current_year + "-" + (current_month+1) + "-" + current_day;

            Calendar before = current;
            before.add(Calendar.DAY_OF_WEEK, -7); // 7 days before today
            int before_year = before.get(Calendar.YEAR);
            int before_day = before.get(Calendar.DAY_OF_MONTH);
            int before_month = before.get(Calendar.MONTH);

            String from  = before_year + "-" + (before_month+1) + "-" + before_day;

            //default settings
            DataHolder.updateSettings(new Settings("fr","20","publishedAt",from,to));
        }

        //we execute the search if the user presses confirm with the keyboard
        this.query = findViewById(R.id.query);
        query.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    executeQueryWithSettings();
                    handled = true;
                }
                return handled;
            }
        });
        //Or if he clicks on the validate button
        Button btn = (Button) findViewById(R.id.queryButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                executeQueryWithSettings();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (this.backPressedTwice) {
            super.onBackPressed();
            return;
        }

        this.backPressedTwice = true;
        Toast.makeText(this, R.string.exit_application, Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                backPressedTwice = false;
            }
        }, 2000);

    }
}
