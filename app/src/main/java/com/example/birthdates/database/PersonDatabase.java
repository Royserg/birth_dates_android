package com.example.birthdates.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.birthdates.dao.PersonDao;
import com.example.birthdates.models.Person;

import java.util.Calendar;

@Database(entities = {Person.class}, version = 1 )
@TypeConverters({Converters.class})
public abstract class PersonDatabase extends RoomDatabase {

    private static PersonDatabase instance;

    public abstract PersonDao personDao();

    public static synchronized PersonDatabase getInstance(Context ctx) {
        if (instance == null) {
            instance = Room.databaseBuilder(ctx.getApplicationContext(),  PersonDatabase.class, "person_database")
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

        public PopulateDbAsyncTask(PersonDatabase db) {
            this.personDao = db.personDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Calendar calendar = Calendar.getInstance();

            personDao.insert(new Person("John Doe", calendar.getTime()));
            personDao.insert(new Person("Natalie", calendar.getTime()));

            return null;
        }
    }
}
