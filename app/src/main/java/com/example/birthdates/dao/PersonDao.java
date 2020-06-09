package com.example.birthdates.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.birthdates.models.Person;

import java.util.List;

@Dao
public interface PersonDao {

    @Insert
    void insert(Person person);

    @Update
    void update(Person person);

    @Delete
    void delete(Person person);

    @Query("DELETE FROM person_table")
    void deleteAllPersons();

    @Query("SELECT * FROM person_table ORDER BY name ASC")
    LiveData<List<Person>> getAllPersons();
}
