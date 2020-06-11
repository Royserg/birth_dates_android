package com.example.birthdates.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "event_table")
public class Event implements Comparable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;
    private String description;
    private Date date;

    @ColumnInfo(name = "days_remaining")
    private int daysRemaining;

    public Event(String name, String description, Date date) {
        this.name = name;
        this.description = description;
        this.date = date;
    }

    /* Setter */

    public void setId(int id) {
        this.id = id;
    }

    public void setDaysRemaining(int daysRemaining) { this.daysRemaining = daysRemaining; }

    /* Getters */
    public int getId() { return id; }

    public int getDaysRemaining() { return daysRemaining; }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
