package com.example.birthdates.database;

import androidx.room.TypeConverter;

import java.util.Date;

public class Converters {

    @TypeConverter
    public static Date toDate(long l) {
        return new Date(l);
    }

    @TypeConverter
    public static Long fromDate(Date d) {
        return d == null ? null : d.getTime();
    }

//    @TypeConverter
//    public static Calendar toCalendar(Long l) {
//        Calendar c = Calendar.getInstance();
//        c.setTimeInMillis(l);
//        return c;
//    }
//
//    @TypeConverter
//    public static Long fromCalendar(Calendar c) {
//        return c == null ? null : c.getTime().getTime();
//    }
}
