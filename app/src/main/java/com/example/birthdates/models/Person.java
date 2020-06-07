package com.example.birthdates.models;

import java.util.Date;

public class Person {

    public String name;
    public Date bday;

    public Person(String name, Date bday) {
        this.name = name;
        this.bday = bday;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", bday=" + bday +
                '}';
    }
}
