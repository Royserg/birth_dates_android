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

}
