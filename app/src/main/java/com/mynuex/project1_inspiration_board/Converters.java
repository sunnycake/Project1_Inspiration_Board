package com.mynuex.project1_inspiration_board;

import androidx.room.TypeConverter;

import java.util.Date;

public class Converters {
    // Method receives a timestamp and converts it to a date object
    // Room uses this first method when reading from the database
    @TypeConverter
    public static Date toDate(long timestamp) {
        return new Date(timestamp);
    }


    // Method receives a date object and converts it to a timestamp long
    // Room will use this method when writing into the database
    // This is the one we want Room to use.
    @TypeConverter
    public static long toTimestamp(Date date) {
        return date == null ? 0 : date.getTime();
    }
}
