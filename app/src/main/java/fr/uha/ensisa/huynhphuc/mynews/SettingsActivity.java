package fr.uha.ensisa.huynhphuc.mynews;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SettingsActivity extends AppCompatActivity {

    private Settings settings;
    private Spinner language_spinner;
    private Spinner pageSize_spinner;
    private Spinner sortBy_spinner;
    private DialogFragment from_fragment;
    private DialogFragment to_fragment;
    private TextView from_date_choosen;
    private TextView to_date_choosen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Bundle bundle = getIntent().getExtras();
        this.settings = bundle.getParcelable("settings");

        Log.d("settings from", "=" + settings.getFrom());
        Log.d("settings to", "=" + settings.getTo());


        this.language_spinner = (Spinner) findViewById(R.id.language_spinner);
        this.pageSize_spinner = (Spinner) findViewById(R.id.pageSize_spinner);
        this.sortBy_spinner = (Spinner) findViewById(R.id.sortBy_spinner);
        this.from_date_choosen = (TextView) findViewById(R.id.from_date_choosen);
        this.to_date_choosen = (TextView) findViewById(R.id.to_date_choosen);

        try {
            this.from_fragment = new DatePickerFragment(this, "from", this.settings.getFrom());
            this.to_fragment  = new DatePickerFragment(this, "to", this.settings.getTo());
            this.from_date_choosen.setText("Du " + this.settings.getFrom());
            this.to_date_choosen.setText("Au " + this.settings.getTo());
        } catch (ParseException e) {
            e.printStackTrace();
        }


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

                String from = ((DatePickerFragment) from_fragment).getDate();
                String to = ((DatePickerFragment) to_fragment).getDate();

                Bundle bundle = new Bundle();
                settings.setLanguage(language);
                settings.setPageSize(pageSize);
                settings.setSortBy(sortBy);
                if(!from.equals("")) settings.setFrom(from);
                if(!to.equals("")) settings.setTo(to);

                try {
                    if (checkDates(from,to)){
                        bundle.putParcelable("settings", settings);
                        Intent intent = new Intent(v.getContext(), MainActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    } else {
                        Toast.makeText(SettingsActivity.this, "Choix des dates incorrect", Toast.LENGTH_SHORT).show();
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        });

    }

    public boolean checkDates(String from, String to) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date from_date = sdf.parse(from);
        Date to_date = sdf.parse(to);

        int ok = to_date.compareTo(from_date);
        // ok = 1 -> to_date > from_date
        // ok = 0 -> to_date = from_date
        // ok = -1 -> to_date < from_date

        return ok == 1;
    }

    /**
     * Return the specific index of an element in a spinner
     * @param spinner
     * @param myString
     * @return
     */
    public int getIndex(Spinner spinner, String myString){
        int index = 0;
        for (int i = 0 ; i < spinner.getCount(); i++){
            if (spinner.getItemAtPosition(i).equals(myString)){
                index = i;
            }
        }
        return index;
    }

    /**
     * Show the "from" DatePickerDialog
     * @param view
     */
    public void showFromDatePicker(View view) {
        this.from_fragment.show(getSupportFragmentManager(), "datePicker");
    }

    /**
     * Show the "to" DatePickerDialog
     * @param view
     */
    public void showToDatePicker(View view) {
        this.to_fragment.show(getSupportFragmentManager(), "datePicker");
    }
}
