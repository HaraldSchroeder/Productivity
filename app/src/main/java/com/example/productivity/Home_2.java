package com.example.productivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsoluteLayout;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Home_2 extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    // Store the view of the stopwatch/timer respectively
    private View stopwatch;
    private View timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_2);

        getSupportActionBar().setTitle("Home_2");

        stopwatch = (View) findViewById(R.id.stopwatch);
        timer = (View) findViewById(R.id.timer);

        // Create and set dropdown selection of clock types
        Spinner dropdown_clockSelection = (Spinner) findViewById(R.id.clock_selection);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.clock_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdown_clockSelection.setAdapter(adapter);
        dropdown_clockSelection.setOnItemSelectedListener(this);
    }

    // Depending on the button that was pressed, the respective activity is loaded (menu)
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

    // Clock type selection of drop down menu
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
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    // Make stopwatch view visible
    private void displayStopwatch() {
        timer.setVisibility(View.GONE);
        stopwatch.setVisibility(View.VISIBLE);
    }

    // Start stopwatch: start Stopwatch Activity
    public void startStopwatch(View view) {
        Intent loadPage = new Intent(this, Stopwatch.class);
        startActivity(loadPage);
    }

    // Make timer view visible
    private void displayTimer() {
        Log.d("test", "timer");
        stopwatch.setVisibility(View.GONE);
        timer.setVisibility(View.VISIBLE);
    }

    // Start Timer: start Timer Activity
    // Pass parameters that the user chooses beforehand
    public void startTimer(View view) {
        Intent loadPage = new Intent(this, Timer.class);
        int hours = 1;
        int minutes = 10;
        loadPage.putExtra("hours", hours);
        loadPage.putExtra("minutes", minutes);
        startActivity(loadPage);
    }

    // Open popup window to add tag
    public void editTags(View view) {

        Context ctxt = getApplicationContext();//(Context) getLayoutInflater().inflate(R.layout.popup, null).getContext();
        View popup = getLayoutInflater().inflate(R.layout.popup, null);
        LinearLayout tag_area = (LinearLayout) popup.findViewById(R.id.tag_area);
        //Context ctxt = tag_area.getContext();
        //LinearLayout testitest = (LinearLayout) findViewById(R.id.testitest);

        Popup.open(popup,(RelativeLayout) findViewById(R.id.rl_root), (Button) findViewById(R.id.bt_editTags), (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE), tag_area, ctxt);
    }

    public void closePopup(View view) {
        Popup.close();
    }

    public void onClick(View view) {
        Popup.selectTags(view);
    }

    public void testSth(View view) {
        if (!DBSimulator.dbSessionsInitialized) {
            DBSimulator.InitializeSessionArray();
            DBSimulator.dbSessionsInitialized = true;
        }
        Log.d("pelikan", DBSimulator.dbSessions.length + "");
    }


}


