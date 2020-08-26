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

import androidx.appcompat.app.AppCompatActivity;

public class Finish extends AppCompatActivity {

    private PopupWindow tagsPopup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finish);
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
        RelativeLayout rl_root = (RelativeLayout) findViewById(R.id.rl_root);
        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customView = layoutInflater.inflate(R.layout.popup, null);
        Button bt_editTags = (Button) findViewById(R.id.bt_editTags);
        Button bt_closePopup = (Button) customView.findViewById(R.id.bt_close);
        tagsPopup = new PopupWindow(customView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tagsPopup.showAtLocation(rl_root, Gravity.CENTER, 0, 0);
    }

    public void closePopup(View view) {
        tagsPopup.dismiss();
    }
}
