package fr.uha.ensisa.huynhphuc.mynews;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@SuppressLint("ValidFragment")
public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    private String date;
    private int year;
    private int month;
    private int day;

    public DatePickerFragment(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date convertedDate = sdf.parse(date);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(convertedDate);

        this.year = calendar.get(Calendar.YEAR);
        this.month = calendar.get(Calendar.DAY_OF_MONTH);
        this.day = calendar.get(Calendar.DAY_OF_WEEK);

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
    }

    public String getDate() {
        return date;
    }

}
