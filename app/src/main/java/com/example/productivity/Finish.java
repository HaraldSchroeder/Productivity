package com.example.productivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Finish extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finish);

        Intent intent = getIntent();
        TextView tv_timeview = (TextView) findViewById(R.id.tv_timeview);
        tv_timeview.setText(intent.getStringExtra("overall_productive_time"));
        TextView tv_breakTime = (TextView) findViewById(R.id.tv_breakTime);
        tv_breakTime.setText(intent.getStringExtra("overall_break_time"));
    }

    public void returnHome(View view) {
        Intent loadPage = new Intent(this, Home_2.class);
        startActivity(loadPage);
    }

    public void repeatSession(View view) {
        Intent loadPage = new Intent(this, Stopwatch.class); // TODO: forward to Stopwatch or Timer class depending on the page before
        startActivity(loadPage);
    }

    public void editTags(View view) {
        //Popup.open((RelativeLayout) findViewById(R.id.rl_root), (Button) findViewById(R.id.bt_editTags), (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
    }

    public void closePopup(View view) {
        Popup.close();
    }

    public void onClick(View view) {
        Popup.selectTags(view);
    }
}
