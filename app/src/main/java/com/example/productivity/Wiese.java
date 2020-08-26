package com.example.productivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Wiese extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Button btn_active; // References the currently pressed button (day, week, month, year)
    private int btn_active_color; // currently active
    private int btn_passive_color; // not active

    private TextView tv_date;
    private LocalDate currently_selected_date;

    private int start_of_week = 1; // 1 = Sunday, 2 = Monday
    private Calendar c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wiese);

        getSupportActionBar().setTitle("Wiese");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Button "day" is active as default
        btn_active_color = ((ColorDrawable) findViewById(R.id.btn_day).getBackground()).getColor();
        btn_passive_color = ((ColorDrawable) findViewById(R.id.btn_week).getBackground()).getColor();
        btn_active = findViewById(R.id.btn_day);


        // Initialize (simulated) DB connection
        if (!DBSimulator.dbSessionsInitialized) {
            DBSimulator.InitializeSessionArray();
            DBSimulator.dbSessionsInitialized = true;
        }

        c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_WEEK, start_of_week);


        tv_date = findViewById(R.id.tv_date);
        currently_selected_date = GetCurrentDate();
        tv_date.setText(currently_selected_date.toString());

        SelectDateRange(btn_active);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem)
    {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    public void SelectDateRange(View view) {
        TextView tv_test = findViewById(R.id.tv_test); // zum Debuggen - Textfeld in der Mitte des Screens
        String btn_txt = ((Button)view).getText().toString(); // text of pressed button
        //Log.d("button", "btn_active: " + view.getBackground());

        if (btn_txt.equals("Day")) {
            tv_test.setText("Data of day");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd. LLLL yyyy");
            String formatted_date = currently_selected_date.format(formatter);
            tv_date.setText(formatted_date);
        } else if (btn_txt.equals("Week")) {
            tv_test.setText("Data of week");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.LL.yyyy");
            LocalDate first_day_of_week = currently_selected_date.with(WeekFields.of(Locale.GERMANY).dayOfWeek(), 1L);
            LocalDate last_day_of_week = first_day_of_week.plusDays(6);
            tv_date.setText(first_day_of_week.format(formatter) + " to " + last_day_of_week.format(formatter));
        } else if (btn_txt.equals("Month")) {
            tv_test.setText("Data of month");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("LLLL yyyy");
            String formatted_date = currently_selected_date.format(formatter);
            tv_date.setText(formatted_date);
        } else if (btn_txt.equals("Year")) {
            tv_test.setText("Data of year");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy");
            String formatted_date = currently_selected_date.format(formatter);
            tv_date.setText(formatted_date);
        }

        btn_active.setBackgroundColor(btn_passive_color); // color former selected button with passive color to show de-selection
        view.setBackgroundColor(btn_active_color); // color now selected button with active color to show selection
        btn_active = (Button) view; // pressed button is currently activated button


    }

    private LocalDate GetCurrentDate() {
        return LocalDate.now();
    }

    // If button L is pressed, a date range before the currently selected date is
    // displayed, depending on the currently selected date range (day, week, etc.). If, for
    // example, Day is selected and you press on button L, the day before the currently selected
    // date is calculated. If Week is selected and you press button L, the week before the
    // currently selected week is displayed
    // Button R works analogously, except for date ranges after the currently selected one
    public void ChangeSelectedDateRange(View view) {
        String btn_txt = ((Button)view).getText().toString(); // text of pressed button

        if (btn_txt.equals("L")) {
            if (btn_active.getText().toString().equals("Day")) {
                currently_selected_date = currently_selected_date.minusDays(1);
            } else if (btn_active.getText().toString().equals("Week")) {
                currently_selected_date = currently_selected_date.minusWeeks(1);
            } else if (btn_active.getText().toString().equals("Month")) {
                currently_selected_date = currently_selected_date.minusMonths(1);
            } else if (btn_active.getText().toString().equals("Year")) {
                currently_selected_date = currently_selected_date.minusYears(1);
            }
        } else if (btn_txt.equals("R")) {
            if (btn_active.getText().toString().equals("Day")) {
                currently_selected_date = currently_selected_date.plusDays(1);
            } else if (btn_active.getText().toString().equals("Week")) {
                currently_selected_date = currently_selected_date.plusWeeks(1);
            } else if (btn_active.getText().toString().equals("Month")) {
                currently_selected_date = currently_selected_date.plusMonths(1);
            } else if (btn_active.getText().toString().equals("Year")) {
                currently_selected_date = currently_selected_date.plusYears(1);
            }
        }

        SelectDateRange((View)btn_active);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

/*
Cool functionality:
- average amount of productive time per day/week/month/year
- distribution of productive time over day...
- average time when user is most productive

 */




}
