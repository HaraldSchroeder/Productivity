package com.example.productivity;

import android.graphics.Color;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

public class DBSimulator {

    public static String[][] dbTags = new String[30][2];
    public static boolean initialized = false;

    public static List<String> dbTagsList = new ArrayList<String>();
    public static boolean listInitialized = false;

    public static String[][] dbSessions = new String[4][4];
    public static boolean dbSessionsInitialized = false;

    private int start_of_week = 1; // 1 = Sunday, 2 = Monday
    private Calendar c;

    public static void InitializeTestArray() {
        for (int i = 0; i < dbTags.length; i++) {
            dbTags[i][0] = Integer.toString(GenerateRandomColor());
            dbTags[i][1] = createRandomWord((new Random()).nextInt(10-3) + 3);
        }
    }

    public static int GenerateRandomColor() {
        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));;
        return color;
    }

    public static void AddTag(String tag) {
        String[][] dbTagsNew = new String[dbTags.length + 1][2];
        for (int i = 0; i < dbTags.length; i++) {
            dbTagsNew[i][0] = dbTags[i][0];
            dbTagsNew[i][1] = dbTags[i][1];
        }
        dbTagsNew[dbTags.length][0] = Integer.toString(GenerateRandomColor());
        dbTagsNew[dbTags.length][1] = tag;
        dbTags = dbTagsNew;
    }

    public static void RenameTag(String tagname, String tagnameNew) {
        for (int i = 0; i < dbTags.length; i++) {
            if (dbTags[i][1].equals(tagname)) {
                dbTags[i][1] = tagnameNew;
            }
        }
    }

    public static void DeleteTag(String tagname) {
        String[][] dbTagsNew = new String[dbTags.length - 1][2];
        int j = 0;
        for (int i = 0; i < dbTags.length; i++) {
            if (dbTags[i][1].equals(tagname)) {
                continue;
            }
            dbTagsNew[j][0] = dbTags[i][0];
            dbTagsNew[j][1] = dbTags[i][1];
            j++;
        }
        dbTags = dbTagsNew;
    }

    public static int FindTag(String tagname) {
        int index = -1;

        for (int i = 0; i < dbTags.length; i++) {
            if (dbTags[i][1].equals(tagname)) {
                index = i;
                break;
            }
        }

        return index;
    }

    public static void InitializeSessionArray() {
        for (int i = 0; i < dbSessions.length; i++) {
            LocalDate dt = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd. LLLL yyyy");
            String formatted_date = dt.format(formatter);

            Date currentTime = Calendar.getInstance().getTime();

            dbSessions[i][0] = "col" + i;                  // Color
            dbSessions[i][1] = "tag" + i;                  // Tagname
            dbSessions[i][2] = currentTime + "";        // Date and time of session
            dbSessions[i][3] = "" + (i * 10);            // Duration
        }
    }


    private static String createRandomWord(int word_length){
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        Random r = new Random();
        String word = "";
        for(int i = 0; i < word_length; i++){
            word += alphabet.charAt(r.nextInt(alphabet.length()));
        }
        return word;
    }

    public static void printDBList() {
        for(String tag : dbTagsList)
            Log.d("undertale", tag);
    }

    public static void addToDBTagList(String tag) {
        dbTagsList.add(tag);
    }

    public static void deleteFromDBTagList(String tag) {
        dbTagsList.remove(tag);
    }

    public static void initializeDBTagList() {
        addToDBTagList("PELIKAN");
        /*

        for (int i = 0; i < 10; i++) {
            addToDBTagList(createRandomWord((new Random()).nextInt(10-3) + 3));
            //Log.d("test", "initializeDBTagList: " + dbTagsList.get(dbTagsList.size() - 1));
        }

         */
    }

}
