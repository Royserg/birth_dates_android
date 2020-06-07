package com.example.birthdates.ui.screens.people;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.birthdates.models.Person;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PeopleViewModel extends ViewModel {

    private MutableLiveData<List<Person>> people = new MutableLiveData<>();

    @RequiresApi(api = Build.VERSION_CODES.O)
    public PeopleViewModel() {

        people.setValue(getDummyPeople());
    }

    public LiveData<List<Person>> getPeople() {
        return people;
    }

    private List<Person> getDummyPeople() {
        List<Person> personList = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();

        personList.add(new Person("Person 1", calendar.getTime()));
        personList.add(new Person("Person 2", calendar.getTime()));
        personList.add(new Person("Person 3", calendar.getTime()));
        personList.add(new Person("Person 4", calendar.getTime()));
        personList.add(new Person("Person 5", calendar.getTime()));
        personList.add(new Person("Person 6", calendar.getTime()));
        personList.add(new Person("Person 7", calendar.getTime()));
        personList.add(new Person("Person 8", calendar.getTime()));
        personList.add(new Person("Person 9", calendar.getTime()));
        personList.add(new Person("Person 10", calendar.getTime()));

        return personList;
    }
}