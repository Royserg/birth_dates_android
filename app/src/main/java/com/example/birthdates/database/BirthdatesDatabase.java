package com.example.birthdates.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.birthdates.dao.EventDao;
import com.example.birthdates.dao.PersonDao;
import com.example.birthdates.models.Event;
import com.example.birthdates.models.Person;

import java.util.Calendar;

@Database(entities = {Person.class, Event.class}, version = 1 )
@TypeConverters({Converters.class})
public abstract class BirthdatesDatabase extends RoomDatabase {

    private static BirthdatesDatabase instance;

    public abstract PersonDao personDao();
    public abstract EventDao eventDao();

    public static synchronized BirthdatesDatabase getInstance(Context ctx) {
        if (instance == null) {
            instance = Room.databaseBuilder(ctx.getApplicationContext(),  BirthdatesDatabase.class, "person_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private PersonDao personDao;
        private EventDao eventDao;

        public PopulateDbAsyncTask(BirthdatesDatabase db) {
            this.personDao = db.personDao();
            this.eventDao = db.eventDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Calendar calendar = Calendar.getInstance();

            personDao.insert(new Person("Sample Person", calendar.getTime()));
            eventDao.insert(new Event("1st Event", "Sample Event", calendar.getTime()));

            return null;
        }
    }
}
