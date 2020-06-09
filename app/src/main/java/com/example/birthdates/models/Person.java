package com.example.birthdates.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "person_table")
public class Person {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;
    private Date bday;

    public Person(String name, Date bday) {
        this.name = name;
        this.bday = bday;
    }

    public void setId(int id) {
        this.id = id;
    }

    /* Getters */
    public int getId() {
        return id;
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
}
