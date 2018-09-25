package fr.uha.ensisa.huynhphuc.mynews;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Spinner language_spinner = (Spinner) findViewById(R.id.language_spinner);
        Spinner category_spinner = (Spinner) findViewById(R.id.category_spinner);
        Spinner pageSize_spinner = (Spinner) findViewById(R.id.pageSize_spinner);

        ArrayAdapter<CharSequence> language_adapter = ArrayAdapter.createFromResource(this,
                R.array.language_array, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> category_adapter = ArrayAdapter.createFromResource(this,
                R.array.category_array, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> pageSize_adapter = ArrayAdapter.createFromResource(this,
                R.array.pageSize_array, android.R.layout.simple_spinner_item);



        language_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pageSize_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        language_spinner.setAdapter(language_adapter);
        category_spinner.setAdapter(category_adapter);
        pageSize_spinner.setAdapter(pageSize_adapter);

    }
}
