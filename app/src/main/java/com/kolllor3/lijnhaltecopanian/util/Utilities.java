package com.kolllor3.lijnhaltecopanian.util;

import android.os.AsyncTask;

import java.text.DateFormat;
import java.util.Locale;
import java.util.Random;

public class Utilities {

    public static boolean isNull(Object obj){
        return obj == null;
    }

    public static boolean isNotNull(Object obj){
        return obj != null;
    }

    public static boolean isNotNullOrNotEmpty(String input){
        return Utilities.isNotNull(input) && !input.isEmpty();
    }

    public static String getRandomString(int length){
        Random r = new Random();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++)
            builder.append((char)(r.nextInt(96) + 32));
        return builder.toString();
    }

    public static String getStringFromDate(long date){
        DateFormat format = DateFormat.getDateInstance(DateFormat.SHORT, Locale.getDefault());
        return format.format(date);
    }

    public static void doInBackground(Runnable runnable){
        AsyncTask.execute(runnable);
    }
}
