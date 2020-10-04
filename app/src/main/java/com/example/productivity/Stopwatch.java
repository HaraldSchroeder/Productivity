package com.example.productivity;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

// credit goes to: https://www.geeksforgeeks.org/how-to-create-a-stopwatch-app-using-android-studio/

public class Stopwatch extends AppCompatActivity {

    // Use seconds, running and wasRunning respectively
    // to record the number of seconds passed,
    // whether the stopwatch is running and
    // whether the stopwatch was running
    // before the activity was paused.

    // Number of seconds displayed
    // on the stopwatch.
    private int seconds = 0;

    // Is the stopwatch running?
    private boolean running;

    private boolean wasRunning;

    private View bt_pause;
    private View bt_continue;

    private int breakTime;
    private int productiveTime;

    //private Popup popup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stopwatch);
        if (savedInstanceState != null) {

            // Get the previous state of the stopwatch
            // if the activity has been
            // destroyed and recreated.
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }

        Intent intent = getIntent();
        if (intent != null) {

            seconds = intent.getIntExtra("productive_time", 0);

            breakTime = intent.getIntExtra("break_time", 0);
        }

        Log.d("pelikan", productiveTime + "  " + breakTime);

        bt_pause = (View) findViewById(R.id.bt_pause);
        bt_continue = (View) findViewById(R.id.bt_continue);

        //popup = new Popup();

        onClickStart(bt_continue);
        runTimer();
    }

    // Save the state of the stopwatch
    // if it's about to be destroyed.
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("seconds", seconds);
        savedInstanceState.putBoolean("running", running);
        savedInstanceState.putBoolean("wasRunning", wasRunning);
    }

    // If the activity is paused,
    // stop the stopwatch.
    @Override
    protected void onPause() {
        super.onPause();
        wasRunning = running;
        running = false;
    }

    // If the activity is resumed,
    // start the stopwatch
    // again if it was running previously.
    @Override
    protected void onResume() {
        super.onResume();
        if (wasRunning) {
            running = true;
        }
    }

    // Start the stopwatch running
    // when the Start button is clicked.
    // Below method gets called
    // when the Start button is clicked.
    public void onClickStart(View view) {
        running = true;
        bt_continue.setVisibility(View.GONE);
        bt_pause.setVisibility(View.VISIBLE);
    }

    // Stop the stopwatch running
    // when the Stop button is clicked.
    // Below method gets called
    // when the Stop button is clicked.
    public void onClickStop(View view) {
        running = false;
        //bt_pause.setVisibility(View.GONE);
        //bt_continue.setVisibility(View.VISIBLE);

        Intent loadPage = new Intent(this, CoffeeBreak.class);
        //productiveTime = getTime();
        loadPage.putExtra("productive_time", seconds);
        loadPage.putExtra("break_time", breakTime);
        startActivity(loadPage);
    }

    // Reset the stopwatch when
    // the Reset button is clicked.
    // Below method gets called
    // when the Reset button is clicked.
    public void onClickReset(View view) {
        running = false;
        seconds = 0;
    }

    // Sets the NUmber of seconds on the timer.
    // The runTimer() method uses a Handler
    // to increment the seconds and
    // update the text view.
    private void runTimer() {

        // Get the text view.
        final TextView timeView = (TextView)findViewById(R.id.tv_time);

        // Creates a new Handler
        final Handler handler = new Handler();

        // Call the post() method,
        // passing in a new Runnable.
        // The post() method processes
        // code without a delay,
        // so the code in the Runnable
        // will run almost immediately.
        handler.post(new Runnable() {
            @Override

            public void run() {
                // Format the seconds into hours, minutes,
                // and seconds.
                String time = getTime(seconds);

                // Set the text view text.
                timeView.setText(time);

                // If running is true, increment the
                // seconds variable.
                if (running) {
                    seconds++;
                }

                // Post the code again
                // with a delay of 1 second.
                handler.postDelayed(this, 1000);
            }
        });
    }

    private String getTime(int t) {
        int hours = t / 3600;
        int minutes = (t % 3600) / 60;
        int secs = t % 60;

        return String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, secs);
    }

    public void endSession(View view) {
        Intent loadPage = new Intent(this, Finish.class);
        //productiveTime = getTime();
        loadPage.putExtra("overall_productive_time", getTime(seconds));
        loadPage.putExtra("overall_break_time", getTime(breakTime));
        startActivity(loadPage);
    }

    public void editTags(View view) {
        LinearLayout tag_area = (LinearLayout) getLayoutInflater().inflate(R.layout.popup, null).findViewById(R.id.tag_area);
        //Popup.open((RelativeLayout) findViewById(R.id.rl_root), (Button) findViewById(R.id.bt_editTags), (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE), tag_area);
    }

    public void closePopup(View view) {
        Popup.close();
    }

    public void onClick(View view) {
        Popup.selectTags(view);
    }

}
