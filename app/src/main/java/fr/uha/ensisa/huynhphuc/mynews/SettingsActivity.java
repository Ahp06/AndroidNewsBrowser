package fr.uha.ensisa.huynhphuc.mynews;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class SettingsActivity extends AppCompatActivity {

    private Settings settings;
    private Spinner language_spinner;
    private Spinner pageSize_spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Bundle bundle = getIntent().getExtras();
        this.settings = bundle.getParcelable("settings");
        Log.d("Settings(SettingsActv)=",  this.settings.toString());

        this.language_spinner = (Spinner) findViewById(R.id.language_spinner);
        this.pageSize_spinner = (Spinner) findViewById(R.id.pageSize_spinner);

        ArrayAdapter<CharSequence> language_adapter = ArrayAdapter.createFromResource(this,
                R.array.language_array, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> pageSize_adapter = ArrayAdapter.createFromResource(this,
                R.array.pageSize_array, android.R.layout.simple_spinner_item);


        language_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pageSize_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        language_spinner.setAdapter(language_adapter);
        pageSize_spinner.setAdapter(pageSize_adapter);

        language_spinner.setSelection(getIndex(language_spinner,settings.getSetting("language").getValue()));
        pageSize_spinner.setSelection(getIndex(pageSize_spinner,settings.getSetting("pageSize").getValue()));

        Button valid_button = (Button) findViewById(R.id.valid_button);
        valid_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String language = language_spinner.getSelectedItem().toString();
                String pageSize = pageSize_spinner.getSelectedItem().toString();

                Bundle bundle = new Bundle();
                settings.getSetting("language").setValue(language);
                settings.getSetting("pageSize").setValue(pageSize);

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
}
