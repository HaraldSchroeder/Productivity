package com.example.productivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
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

import java.util.List;

public class Popup {

    public static List<String> selectedTags;
    public static PopupWindow tagsPopup;
    public static boolean newSession = false;

    public static void open(View popup,RelativeLayout rl_root, Button edit, LayoutInflater layoutInflater, LinearLayout tag_area, Context ctxt) {
        loadTags(tag_area, ctxt);
        Button bt_editTags = edit;
        //Button bt_closePopup = (Button) customView.findViewById(R.id.bt_close);
        tagsPopup = new PopupWindow(popup, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tagsPopup.showAtLocation(rl_root, Gravity.CENTER, 0, 0);
    }

    public static void close() {
        tagsPopup.dismiss();
    }

    public static void selectTags(View view) {
        int textColor = ((TextView)view).getCurrentTextColor();
        if (textColor != Color.GREEN) {
            ((TextView)view).setTextColor(Color.GREEN);
            insertTagIntoDB((String)((TextView)view).getText());
        } else {
            ((TextView)view).setTextColor(Color.BLACK);
            deleteFromDB((String)((TextView)view).getText());
        }
    }

    public static void insertTagIntoDB(String tag) {
        DBSimulator.addToDBTagList(tag);
        DBSimulator.printDBList();
    }

    public static void deleteFromDB(String tag) {
        DBSimulator.deleteFromDBTagList(tag);
        DBSimulator.printDBList();
    }

    private static void loadTags(LinearLayout tag_area, Context ctxt) {
        if (!DBSimulator.listInitialized) {
            DBSimulator.initializeDBTagList();
            DBSimulator.listInitialized = true;
        }
            /*
            RelativeLayout rl_tag = new RelativeLayout(ctxt);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            );
            rl_tag.setLayoutParams(params);

            TextView tagname = new TextView(ctxt);
            //tagname.setText(DBSimulator.dbTagsList.get(DBSimulator.dbTagsList.size() - 1));
            tagname.setText("PELIKAN TEST");
            RelativeLayout.LayoutParams tagstringParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            tagname.setLayoutParams(tagstringParams);
            rl_tag.addView(tagname);

            tag_area.addView(rl_tag);
            */
            DBSimulator.addToDBTagList("leo");
            DBSimulator.addToDBTagList("leo2");
            DBSimulator.addToDBTagList("leo3");
            DBSimulator.addToDBTagList("leo4");
            DBSimulator.printDBList();

            for(String tag : DBSimulator.dbTagsList) {
                RelativeLayout rl_tag = new RelativeLayout(ctxt);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.MATCH_PARENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT
                );
                rl_tag.setPadding(5, 5, 5, 5);
                rl_tag.setLayoutParams(params);

                TextView tagname = new TextView(ctxt);
                tagname.setText(tag);
                RelativeLayout.LayoutParams tagstringParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                tagstringParams.setMargins(100, 0 , 0, 0);
                tagname.setLayoutParams(tagstringParams);
                rl_tag.addView(tagname);

                tag_area.addView(rl_tag);
            }
    }

}
