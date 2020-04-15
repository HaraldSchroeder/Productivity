package com.example.productivity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.InputType;
import android.text.method.KeyListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.Random;

public class TagsSelectionPopup {


    public void createTagsSelectionPopup(int x,int y,int w,int h,Context context,LinearLayout lt){
        Log.d("PIMMEL","test1");
        //popup window
        PopupWindow popup = new PopupWindow(context);
        //fade in/out animation
        //popup.setAnimationStyle(R.style.Animation);
        //set popup width to window width
        popup.setWidth(dpToPx(w,context));
        popup.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);


        //create first linear layout. It defines the orientiation (vertical) and the background color of the popup
        LinearLayout ground_layout = new LinearLayout(context);
        LinearLayout.LayoutParams ground_layout_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);

        ground_layout.setLayoutParams(ground_layout_params);
        ground_layout.setPadding(20,10,20,10);
        ground_layout.setOrientation(LinearLayout.VERTICAL);
        ground_layout.setBackgroundColor(context.getResources().getColor(R.color.default_blue_light));

        //header for tags area
        TextView tags_area_header = new TextView(context);
        tags_area_header.setText("Wähle deine Tags");
        tags_area_header.setTextSize(TypedValue.COMPLEX_UNIT_SP,25);
        ground_layout.addView(tags_area_header);

        //scrollview for the following tags area layout
        ScrollView tags_area_scrollview = new ScrollView(context);
        LinearLayout.LayoutParams tags_area_scrollview_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                dpToPx(200,context));
        tags_area_scrollview_params.setMargins(0,10,0,30);
        tags_area_scrollview.setLayoutParams(tags_area_scrollview_params);
        tags_area_scrollview.setBackgroundColor(context.getResources().getColor(R.color.green));

        //create tags selection box as linear layout (vertical) which contains multiple linear layouts (horizontal) as rows of tags
        LinearLayout tags_area_layout = new LinearLayout(context);
        LinearLayout.LayoutParams tags_area_layout_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        tags_area_layout.setLayoutParams(tags_area_layout_params);
        tags_area_layout.setOrientation(LinearLayout.VERTICAL);
        tags_area_layout.setPadding(5,5,5,5);
        tags_area_layout.setBackgroundColor(context.getResources().getColor(R.color.green));
        Log.d("PIMMEL","test2");
        DBSimulator.InitializeTestArray();

        //fill tags area with tags (rows of tags)
        LinearLayout tag_row = createTagRow(context);
        for(int i = 0; i < DBSimulator.dbTags.length; i++){
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(5,5,5,5);
            Button tag = new Button(context);
            tag.setTag(i);
            tag.setLayoutParams(params);
            tag.setPadding(10,5,10,5);
            tag.setText(DBSimulator.dbTags[i][1]);
            tag.setBackgroundColor(Color.argb(255, 255, 255, 255));
            tag.setMinHeight(0);
            tag.setMinimumHeight(0);
            tag.setMinWidth(0);
            tag.setMinimumWidth(0);
            tag.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String id = v.getTag().toString();
                    if(((ColorDrawable)v.getBackground()).getColor() == Integer.parseInt(DBSimulator.dbTags[Integer.parseInt(id)][0])){
                        v.setBackgroundColor(Color.argb(255, 255, 255, 255));
                    }
                    else{
                        v.setBackgroundColor(Integer.parseInt(DBSimulator.dbTags[Integer.parseInt(id)][0]));
                    }

                }
            });

            tag.measure(0, 0);
            tag_row.measure(0, 0);
            double tag_row_width = tag_row.getMeasuredWidth();
            double text_view_width = tag.getMeasuredWidth();
            if(pxToDp((int)tag_row_width,context) + pxToDp((int)text_view_width,context) >= 290){
                tags_area_layout.addView(tag_row);
                tag_row = createTagRow(context);
            }
            tag_row.addView(tag);
        }
        tags_area_layout.addView(tag_row);
        tags_area_scrollview.addView(tags_area_layout);
        ground_layout.addView(tags_area_scrollview);

        //header for tags area
        TextView comment_header = new TextView(context);
        comment_header.setText("Kommentar");
        comment_header.setTextSize(TypedValue.COMPLEX_UNIT_SP,25);
        ground_layout.addView(comment_header);
        Log.d("PIMMEL","test3");
        //edit text area
        EditText edit_text_area = new EditText(context);
        LinearLayout.LayoutParams edit_text_area_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                dpToPx(100,context));
        edit_text_area_params.setMargins(0,10,0,10);
        edit_text_area.setLayoutParams(edit_text_area_params);
        edit_text_area.setBackgroundColor(context.getResources().getColor(R.color.green));
        edit_text_area.setGravity(Gravity.TOP);
        edit_text_area.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        edit_text_area.setHint("Dein Kommentar für die kommende Lernsession");
        edit_text_area.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
        ground_layout.addView(edit_text_area);


        //linearlayout (horizontal) which will contain a cancel and confirm button
        LinearLayout button_layout = new LinearLayout(context);
        LinearLayout.LayoutParams button_layout_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        button_layout.setLayoutParams(button_layout_params);
        button_layout.setOrientation(LinearLayout.HORIZONTAL);
        button_layout.setGravity(Gravity.CENTER);

        //cancel button
        Button cancel_button = new Button(context);
        cancel_button.setText("Cancel");
        button_layout.addView(cancel_button);

        //confirm button
        Button confirm_button = new Button(context);
        confirm_button.setText("Confirm");
        button_layout.addView(confirm_button);

        ground_layout.addView(button_layout);

        Log.d("PIMMEL","test4");
        popup.setFocusable(true);




        //finally, add the layout containing the tags selection and a comment box to the popup
        popup.setContentView(ground_layout);
        popup.setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(R.color.green)));

        popup.showAtLocation(lt, Gravity.CENTER, 0, 0);
        popup.update(0, 0, popup.getWidth(), popup.getHeight());
        Log.d("PIMMEL","test5");
    }


    private static int dpToPx(int dp,Context context) {
        float density = context.getResources()
                .getDisplayMetrics()
                .density;
        return Math.round((float) dp * density);
    }

    public float pxToDp(float px,Context context) {
        return px / ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }


    private static int spToPx(float sp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics());
    }


    private LinearLayout createTagRow(Context context) {
        LinearLayout.LayoutParams layout_params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        LinearLayout tag_row = new LinearLayout(context);
        tag_row.setLayoutParams(layout_params);
        tag_row.setOrientation(LinearLayout.HORIZONTAL);
        return tag_row;
    }


}
