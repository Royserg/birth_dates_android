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

    @Query("SELECT * FROM person_table WHERE id = :id")
    LiveData<Person> getPerson(int id);

    /*https://stackoverflow.com/a/55558051/8421735*/
    @Query("SELECT *, CASE " +
            "WHEN strftime('%j', datetime(bday/1000, 'unixepoch', 'localtime')) - strftime('%j', 'now', 'localtime') >= 0 THEN strftime('%j', datetime(bday/1000, 'unixepoch', 'localtime')) - strftime('%j', 'now', 'localtime') " +
            "ELSE strftime('%j', datetime(bday/1000, 'unixepoch', 'localtime')) - strftime('%j', 'now', 'localtime') + strftime('%j', strftime('%Y-12-31', 'now')) " +
            "END AS days_remaining " +
            "FROM person_table " +
            "ORDER BY days_remaining")
    LiveData<List<Person>> getAllPersons();

    @Query("SELECT * FROM person_table " +
            "WHERE strftime('%j', 'now', 'localtime') = strftime('%j', datetime(bday/1000, 'unixepoch', 'localtime'))")
    LiveData<List<Person>> getBirthdayPersons();
}
