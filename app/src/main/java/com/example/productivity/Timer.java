package com.example.productivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class Timer extends AppCompatActivity {

    private int hours;
    private int minutes;
    private View bt_pause;
    private View bt_continue;

    private int seconds = 0;
    private boolean running;
    private boolean wasRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            hours = extras.getInt("hours");
            minutes = extras.getInt("minutes");
        }

        seconds = hours * 60 * 60 + minutes * 60;

        bt_pause = (View) findViewById(R.id.bt_pause);
        bt_continue = (View) findViewById(R.id.bt_continue);

        if (savedInstanceState != null) {
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }

        onClickStart(bt_continue);
        runTimer();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("seconds", seconds);
        savedInstanceState.putBoolean("running", running);
        savedInstanceState.putBoolean("wasRunning", wasRunning);
    }

    @Override
    protected void onPause() {
        super.onPause();
        wasRunning = running;
        running = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (wasRunning) {
            running = true;
        }
    }

    public void onClickStart(View view) {
        running = true;
        bt_continue.setVisibility(View.GONE);
        bt_pause.setVisibility(View.VISIBLE);
    }

    public void onClickStop(View view) {
        running = false;
        bt_pause.setVisibility(View.GONE);
        bt_continue.setVisibility(View.VISIBLE);
    }

    private void runTimer() {
        final TextView timeView = (TextView)findViewById(R.id.tv_timeview);

        final Handler handler = new Handler();

        handler.post(new Runnable() {
            @Override

            public void run() {
                hours = seconds / 3600;
                minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;

                String time = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, secs);

                timeView.setText(time);

                if (running) {
                    seconds--;
                }

                handler.postDelayed(this, 1000);
            }
        });
    }

    public void endSession(View view) {
        Intent loadPage = new Intent(this, Finish.class);
        startActivity(loadPage);
    }

}
