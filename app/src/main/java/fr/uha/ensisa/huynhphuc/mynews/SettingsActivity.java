package fr.uha.ensisa.huynhphuc.mynews;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class SettingsActivity extends AppCompatActivity {

    private Settings settings;
    private Spinner language_spinner;
    private Spinner pageSize_spinner;
    private Spinner sortBy_spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Bundle bundle = getIntent().getExtras();
        this.settings = bundle.getParcelable("settings");

        this.language_spinner = (Spinner) findViewById(R.id.language_spinner);
        this.pageSize_spinner = (Spinner) findViewById(R.id.pageSize_spinner);
        this.sortBy_spinner = (Spinner) findViewById(R.id.sortBy_spinner);

        ArrayAdapter<CharSequence> language_adapter = ArrayAdapter.createFromResource(this,
                R.array.language_array, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> pageSize_adapter = ArrayAdapter.createFromResource(this,
                R.array.pageSize_array, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> sortBy_adapter = ArrayAdapter.createFromResource(this,
                R.array.sortBy_array, android.R.layout.simple_spinner_item);


        language_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pageSize_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortBy_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        language_spinner.setAdapter(language_adapter);
        pageSize_spinner.setAdapter(pageSize_adapter);
        sortBy_spinner.setAdapter(sortBy_adapter);

        language_spinner.setSelection(getIndex(language_spinner,settings.getLanguage()));
        pageSize_spinner.setSelection(getIndex(pageSize_spinner,settings.getPageSize()));
        sortBy_spinner.setSelection(getIndex(sortBy_spinner,settings.getSortBy()));

        Button valid_button = (Button) findViewById(R.id.valid_button);
        valid_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String language = language_spinner.getSelectedItem().toString();
                String pageSize = pageSize_spinner.getSelectedItem().toString();
                String sortBy = sortBy_spinner.getSelectedItem().toString();

                Bundle bundle = new Bundle();
                settings.setLanguage(language);
                settings.setPageSize(pageSize);
                settings.setSortBy(sortBy);

                bundle.putParcelable("settings", settings);

                Intent intent = new Intent(v.getContext(), MainActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }

    public int getIndex(Spinner spinner, String myString){

        int index = 0;

        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).equals(myString)){
                index = i;
            }
        }
        return index;
    }

    public void showDatePickerDialog(View view) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }


}
