package fr.uha.ensisa.huynhphuc.mynews.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import fr.uha.ensisa.huynhphuc.mynews.R;
import fr.uha.ensisa.huynhphuc.mynews.utils.DataHolder;

public class HistoryActivity extends AppCompatActivity {

    private ListView lv_history;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        ArrayList<String> history = DataHolder.getHistory();

        this.lv_history = (ListView) findViewById(R.id.history);

        if (!history.isEmpty()) {
            this.adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, history);
            lv_history.setAdapter(adapter);
        } else {
            TextView emptyText = (TextView) findViewById(R.id.empty_history);
            lv_history.setEmptyView(emptyText);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = this.getMenuInflater();
        inflater.inflate(R.menu.history_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.delete_history) {
            DataHolder.getHistory().clear();
            DataHolder.writeData(this, "history");

            TextView emptyText = (TextView) findViewById(R.id.empty_history);
            lv_history.setEmptyView(emptyText);
        }

        return super.onOptionsItemSelected(item);
    }


}
