package com.example.birthdates.ui.screens.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.birthdates.models.Event;
import com.example.birthdates.models.Person;
import com.example.birthdates.reopsitory.EventRepository;
import com.example.birthdates.reopsitory.PersonRepository;

import java.util.List;

public class HomeViewModel extends AndroidViewModel {

    PersonRepository personRepository;
    EventRepository eventRepository;

    private LiveData<List<Person>> birthdayPersons;
    private LiveData<List<Event>> todayEvents;


    public HomeViewModel(@NonNull Application application) {
        super(application);
        personRepository = new PersonRepository(application);
        eventRepository = new EventRepository(application);
        birthdayPersons = personRepository.getBirthdayPersons();
        todayEvents = eventRepository.getTodayEvents();
    }

    public LiveData<List<Person>> getBirthdayPersons() {
        return birthdayPersons;
    }

    public LiveData<List<Event>> getTodayEvents() {
        return todayEvents;
    }

}
