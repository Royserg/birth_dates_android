package com.example.birthdates.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "person_table")
public class Person implements Comparable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "days_remaining")
    private int daysRemaining;

    private String name;
    private Date bday;

    public Person(String name, Date bday) {
        this.name = name;
        this.bday = bday;
    }

    /* Setter */
    public void setId(int id) {
        this.id = id;
    }

    public void setDaysRemaining(int daysRemaining) {
        this.daysRemaining = daysRemaining;
    }

    /* Getters */
    public int getId() {
        return id;
    }

    public int getDaysRemaining() {
        return daysRemaining;
    }

    public String getName() {
        return name;
    }

    public Date getBday() {
        return bday;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", bday=" + bday +
                '}';
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
