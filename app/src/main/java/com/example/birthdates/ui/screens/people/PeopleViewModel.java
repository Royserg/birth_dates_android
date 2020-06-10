package com.example.birthdates.ui.screens.people;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.birthdates.models.Person;
import com.example.birthdates.reopsitory.PersonRepository;

import java.util.List;

public class PeopleViewModel extends AndroidViewModel {

    private PersonRepository repository;
    private LiveData<List<Person>> allPersons;

    public PeopleViewModel(@NonNull Application application) {
        super(application);
        repository = new PersonRepository(application);
        allPersons = repository.getAllPersons();
    }

    public void insert(Person person) {
        repository.insert(person);
    }

    public void update(Person person) {
        repository.update(person);
    }

    public void delete(Person person) {
        repository.delete(person);
    }

    public void deleteAllPersons(Person person) {
        repository.deleteAllPersons();
    }

    public LiveData<Person> getPerson(int id) {
        return repository.getPerson(id);
    }

    public LiveData<List<Person>> getAllPersons() {
         return allPersons;
    }
}
