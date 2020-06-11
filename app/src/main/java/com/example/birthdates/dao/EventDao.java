package com.example.birthdates.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.birthdates.models.Event;

import java.util.List;

@Dao
public interface EventDao {

    @Insert
    void insert(Event event);

    @Update
    void update(Event event);

    @Delete
    void delete(Event event);

    @Query("DELETE FROM event_table")
    void deleteAllEvents();

    @Query("SELECT * FROM event_table WHERE id = :id")
    LiveData<Event> getEvent(int id);

    /*https://stackoverflow.com/a/55558051/8421735*/
    @Query("SELECT *, " +
            "strftime('%j', datetime(date/1000, 'unixepoch', 'localtime')) - strftime('%j', 'now', 'localtime') AS days_remaining " +
            "FROM event_table " +
            "ORDER BY days_remaining")
    LiveData<List<Event>> getAllEvents();

    @Query("SELECT * FROM event_table " +
            "WHERE strftime('%j', 'now', 'localtime') = strftime('%j', datetime(date/1000, 'unixepoch', 'localtime'))")
    LiveData<List<Event>> getTodayEvents();

}
