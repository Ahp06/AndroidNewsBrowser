package fr.uha.ensisa.huynhphuc.mynews.utils;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import fr.uha.ensisa.huynhphuc.mynews.R;
import fr.uha.ensisa.huynhphuc.mynews.activity.SettingsActivity;

@SuppressLint("ValidFragment")
public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    private SettingsActivity settingsActivity;
    private String date;
    private String id; // from or to
    private int year;
    private int month;
    private int day;

    public DatePickerFragment(SettingsActivity settingsActivity, String id, String date) throws ParseException {
        this.settingsActivity = settingsActivity;
        this.id = id;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date convertedDate = sdf.parse(date);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(convertedDate);

        this.year = calendar.get(Calendar.YEAR);
        this.month = calendar.get(Calendar.MONTH);
        this.day = calendar.get(Calendar.DAY_OF_MONTH);
        this.date = year + "-" + (month+1) + "-" + day;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new DatePickerDialog(getActivity(), this, this.year, this.month, this.day);
    }

    /**
     * Set the date data member of this class according to news API date format.
     * The month value is incremented because in the Android SDK, months are indexed starting at 0.
     * @param view
     * @param year
     * @param month
     * @param day
     */
    public void onDateSet(DatePicker view, int year, int month, int day) {
        this.date = year + "-" + (month+1) + "-" + day ;

        TextView from_date_choosen = (TextView) this.settingsActivity.findViewById(R.id.from_date_choosen);
        TextView to_date_choosen = (TextView) this.settingsActivity.findViewById(R.id.to_date_choosen);

        if(this.id.equals("from")){
            from_date_choosen.setText("Du " + this.getDate());
        } else {
            to_date_choosen.setText("Au " + this.getDate());
        }

    }

    public String getDate() {
        return date;
    }

}
