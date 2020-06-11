package com.example.birthdates.ui.screens.events;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.birthdates.models.Event;
import com.example.birthdates.reopsitory.EventRepository;

import java.util.List;

public class EventsViewModel extends AndroidViewModel {

    private EventRepository repository;
    private LiveData<List<Event>> allEvents;

    public EventsViewModel(@NonNull Application application) {
        super(application);
        repository = new EventRepository(application);
        allEvents = repository.getAllEvents();
    }

    public void insert(Event event) {
        repository.insert(event);
    }

    public void update(Event event) {
        repository.update(event);
    }

    public void delete(Event event) {
        repository.delete(event);
    }

    public void deleteAllEvents(Event event) {
        repository.deleteAllEvents();
    }

    public LiveData<Event> getEvent(int id) {
        return repository.getEvent(id);
    }

    public LiveData<List<Event>> getAllEvents() {
        return allEvents;
    }


}