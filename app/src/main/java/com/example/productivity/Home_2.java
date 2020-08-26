package com.example.productivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.strictmode.IntentReceiverLeakedViolation;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class Home_2 extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private View stopwatch;
    private View timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_2);

        getSupportActionBar().setTitle("Home_2");

        stopwatch = (View) findViewById(R.id.stopwatch);
        timer = (View) findViewById(R.id.timer);

        Spinner dropdown_clockSelection = (Spinner) findViewById(R.id.clock_selection);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.clock_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdown_clockSelection.setAdapter(adapter);
        dropdown_clockSelection.setOnItemSelectedListener(this);
    }

    // Depending on the button that was pressed, the respective activity is loaded
    public void LoadPage(View view) {
        String btn_text = ((Button) view).getText().toString();

        if (btn_text.equals("Tags")) {
            Intent loadPage = new Intent(this, Tags.class);
            startActivity(loadPage);
        } else if (btn_text.equals("Settings")) {
            Intent loadPage = new Intent(this, Settings.class);
            startActivity(loadPage);
        } else if (btn_text.equals("Wiese")) {
            Intent loadPage = new Intent(this, Wiese.class);
            startActivity(loadPage);
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        switch (position) {
            case 0:
                displayStopwatch();
                break;
            case 1:
                displayTimer();
                break;
            default:
                break;
        }


        Log.d("dropdown", "TESTTEST");
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private void displayStopwatch() {
        Log.d("test", "stopwatch");
        timer.setVisibility(View.GONE);
        stopwatch.setVisibility(View.VISIBLE);
    }

    public void startStopwatch(View view) {
        Intent loadPage = new Intent(this, Stopwatch.class);
        startActivity(loadPage);
    }

    private void displayTimer() {
        Log.d("test", "timer");
        stopwatch.setVisibility(View.GONE);
        timer.setVisibility(View.VISIBLE);
    }

    public void startTimer(View view) {
        Intent loadPage = new Intent(this, Timer.class);
        int hours = 1;
        int minutes = 10;
        loadPage.putExtra("hours", hours);
        loadPage.putExtra("minutes", minutes);
        startActivity(loadPage);
    }


}


