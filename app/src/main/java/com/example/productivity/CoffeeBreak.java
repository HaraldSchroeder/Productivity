package com.example.productivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class CoffeeBreak extends AppCompatActivity {

    public int breakTime;
    //private String breakTime;
    private int productiveTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coffee_break);

        Intent intent = getIntent();
        if (intent != null) {
            productiveTime = intent.getIntExtra("productive_time", 0);

            breakTime = intent.getIntExtra("break_time", 0);
        }

        runTimer();
    }

    private void runTimer() {
        final TextView timeView = (TextView)findViewById(R.id.tv_breakTime);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override

            public void run() {
                // Format the seconds into hours, minutes,
                // and seconds.
                String time = getTime();

                // Set the text view text.
                timeView.setText(time);

                // If running is true, increment the
                // seconds variable.
                breakTime++;

                // Post the code again
                // with a delay of 1 second.
                handler.postDelayed(this, 1000);
            }
        });
    }

    private String getTime() {
        int hours = breakTime / 3600;
        int minutes = (breakTime % 3600) / 60;
        int secs = breakTime % 60;

        return String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, secs);
    }

    public void endBreak(View view) {
        Intent loadPage = new Intent(this, Stopwatch.class);
        //String breakTime = getTime();
        loadPage.putExtra("break_time", breakTime);
        loadPage.putExtra("productive_time", productiveTime);
        startActivity(loadPage);
    }
}
