package com.example.productivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.triggertrap.seekarc.SeekArc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Home extends AppCompatActivity {

    private final int HOUR_MODE = 0;
    private final int MINUTE_MODE = 1;

    public int max_progress = 100;

    public double max_hour = 24;
    public double max_minute = 59;

    public int time_mode = 0; //0 indicates hours, 1 minutes (will be switched by clicking the hour/minute button

    public int progress_hour = 0; //indicates the current progress of hours
    public int progress_minute = 0; //indicates the current progress of minutes

    //indicates the range of 1 hour in the circular progress seekbar
    double range_of_hour = max_progress / max_hour;
    //indicates the range of 1 minute in the circular progress seekbar
    double range_of_minute = max_progress / max_minute;

    private SeekArc circular_seekbar;
    private Button tree_selector_button;
    private Button hour_button;
    private Button minute_button;
    private Button pen_button;
    private Button start_button;
    private TextView hour_textview;
    private TextView minute_textview;
    private PopupWindow popUp;

    private boolean clicked = false;

    String[] tags;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        getSupportActionBar().setTitle("Home");

        //init global widgetobjects
        circular_seekbar = (SeekArc) findViewById(R.id.CircularSeekbar);
        tree_selector_button = (Button) findViewById(R.id.TreeSelectorButton);
        hour_button = (Button) findViewById(R.id.HourButton);
        minute_button = (Button) findViewById(R.id.MinuteButton);
        pen_button = (Button) findViewById(R.id.PenButton);
        start_button = (Button) findViewById(R.id.StartButton);
        hour_textview = (TextView) findViewById(R.id.HourTextView);
        minute_textview = (TextView) findViewById(R.id.MinuteTextView);


        //add listeners
        circular_seekbar.setOnSeekArcChangeListener(new SeekArc.OnSeekArcChangeListener() {
            @Override
            public void onProgressChanged(SeekArc seekArc, int progress, boolean b) {
                if (isModeHour(time_mode)) {
                    progress_hour = progress;
                    writeTime(progress_hour, range_of_hour, hour_textview);
                } else {
                    progress_minute = progress;
                    writeTime(progress_minute, range_of_minute, minute_textview);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekArc seekArc) {
            }

            @Override
            public void onStopTrackingTouch(SeekArc seekArc) {
            }
        });


        tree_selector_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Tree Selector Button pressed", "true");
            }
        });


        hour_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                time_mode = HOUR_MODE;
                circular_seekbar.setProgress(progress_hour);
                hour_button.setBackgroundColor(getResources().getColor(R.color.time_button_pressed));
                minute_button.setBackgroundColor(getResources().getColor(R.color.time_button_unpressed));
            }
        });


        minute_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                time_mode = MINUTE_MODE;
                circular_seekbar.setProgress(progress_minute);
                hour_button.setBackgroundColor(getResources().getColor(R.color.time_button_unpressed));
                minute_button.setBackgroundColor(getResources().getColor(R.color.time_button_pressed));
            }
        });


        pen_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DisplayMetrics displayMetrics = Home.this.getResources().getDisplayMetrics();
                float dpHeight = displayMetrics.heightPixels / displayMetrics.density;
                float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
                TagsSelectionPopup popup = new TagsSelectionPopup();
                popup.createTagsSelectionPopup((int)(0.05 * dpWidth),83,(int)(0.9 * dpWidth),0,Home.this,(LinearLayout) findViewById(R.id.Content));
                //popup.createTagsSelectionPopup(40,500,324,0,Home.this,(LinearLayout) findViewById(R.id.Content));
            }
        });
        /* //use this for the button in the popupwindow
        pen_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tags == null || tags.length == 0){
                    TextView def_text = (TextView) findViewById(R.id.TagDefaultText);
                    def_text.setVisibility(View.GONE);
                    tags = getTagNames();
                    int[] tag_colors = getTagColors();


                    LinearLayout tag_row = createTagRow();

                    for(int i = 0; i < tags.length; i++){


                        //Log.d("Name",tags[i]);
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        params.setMargins(5,5,5,5);

                        TextView tv = new TextView(Home.this);
                        tv.setLayoutParams(params);
                        tv.setPadding(5,5,5,5);
                        tv.setText(tags[i]);
                        tv.setBackgroundColor(tag_colors[i]);


                        tv.measure(0, 0);
                        tag_row.measure(0, 0);
                        double tag_row_width = tag_row.getMeasuredWidth();
                        double text_view_width = tv.getMeasuredWidth();



                        if(pxToDp((int)tag_row_width) + pxToDp((int)text_view_width) >= 240){
                            tag_row = createTagRow();
                        }
                        tag_row.addView(tv);
                        Log.d("Measured Width","" + tag_row_width);
                        Log.d("GetWidth","" + tag_row.getWidth());
                    }

                }


            }
        });
        */
    }



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

    private LinearLayout createTagRow() {
        LinearLayout.LayoutParams layout_params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        LinearLayout tag_row = new LinearLayout(Home.this);
        tag_row.setLayoutParams(layout_params);
        tag_row.setOrientation(LinearLayout.HORIZONTAL);
        ((LinearLayout) (findViewById(R.id.TagsArea))).addView(tag_row);
        return tag_row;
    }



    private boolean isModeHour(int time_mode) {
        return time_mode == HOUR_MODE;
    }


    private int getTime(int progress, double range) {
        return (int) Math.ceil(progress / range);
    }

    private void writeTime(int progress, double range, TextView time_textview) {
        int time = getTime(progress, range);
        String time_text = time + "";
        if (time_text.length() == 1) time_text = "0" + time_text;
        time_textview.setText(time_text);
    }


    //simulate a super simple db
    private String[] getTagNames() {
        return new String[]{"Kacken", "Furzen", "Dünnpfiff", "Stinken", "Ballern", "Netflix", "Leben", "Schlafen", "Himmel", "Sonne", "Mond", "Sterne"};
    }

    private int[] getTagColors() {
        return new int[]{Color.parseColor("#a1db97"),
                Color.parseColor("#b2eca8"),
                Color.parseColor("#c31db9"),
                Color.parseColor("#d42eca"),
                Color.parseColor("#e531db"),
                Color.parseColor("#1642ec"),
                Color.parseColor("#27531d"),
                Color.parseColor("#38642e"),
                Color.parseColor("#497531"),
                Color.parseColor("#5a8642"),
                Color.parseColor("#6b9753"),
                Color.parseColor("#7ca864")};
    }

    public float pxToDp(float px) {
        return px / ((float) getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }
}
