package com.example.productivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Wiese extends AppCompatActivity {

    private int btn_active_color;
    private int btn_passive_color;
    private Button btn_active;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wiese);

        getSupportActionBar().setTitle("Wiese");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btn_active_color = ((ColorDrawable) findViewById(R.id.btn_day).getBackground()).getColor();
        btn_passive_color = ((ColorDrawable) findViewById(R.id.btn_week).getBackground()).getColor();
        btn_active = findViewById(R.id.btn_day);
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

    public void ShowData(View view) {
        TextView tv_test = findViewById(R.id.tv_test);
        String btn_txt = ((Button)view).getText().toString();

        //Log.d("button", "btn_active: " + view.getBackground());


        if (btn_txt.equals("Day")) {
            tv_test.setText("Data of day");
            view.setBackgroundColor(btn_active_color);
        } else if (btn_txt.equals("Week")) {
            tv_test.setText("Data of week");
            view.setBackgroundColor(btn_active_color);
        } else if (btn_txt.equals("Month")) {
            tv_test.setText("Data of month");
            view.setBackgroundColor(btn_active_color);
        } else if (btn_txt.equals("Year")) {
            tv_test.setText("Data of year");
            view.setBackgroundColor(btn_active_color);
        }
        btn_active.setBackgroundColor(btn_passive_color);
        btn_active = (Button) view;
    }
}
