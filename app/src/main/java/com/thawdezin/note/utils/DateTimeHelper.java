package com.thawdezin.note.utils;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class DateTimeHelper {

    private static SimpleDateFormat dateFormat;
    private static SimpleDateFormat timeFormat;

    public static SimpleDateFormat getDateFormat(){
        dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.US);
       return dateFormat;
    }

    public static SimpleDateFormat getTimeFormat(){
        timeFormat = new SimpleDateFormat("hh:mm a", Locale.US);
        return timeFormat;
    }
}
