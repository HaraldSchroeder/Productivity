package com.example.productivity;

import android.graphics.Color;

import java.util.Random;

public class DBSimulator {

    public static String[][] dbTags = new String[5][2];
    public static boolean initialized = false;

    public static String[][] dbSessions = new String[5][4];

    public static void InitializeTestArray() {
        for (int i = 0; i < dbTags.length; i++) {
            dbTags[i][0] = Integer.toString(GenerateRandomColor());
            dbTags[i][1] = "tagstring" + i;
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
            dbSessions[i][0] = i + "";
            dbSessions[i][1] = (i * 2 + 5) + "";
            dbSessions[i][2] = "tag01, tag02";
            dbSessions[i][3] = "date and time";
        }
    }

}
