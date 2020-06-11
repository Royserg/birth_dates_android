package com.example.birthdates.reopsitory;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.birthdates.dao.EventDao;
import com.example.birthdates.database.BirthdatesDatabase;
import com.example.birthdates.models.Event;

import java.util.List;

public class EventRepository {
    private EventDao eventDao;
    private LiveData<List<Event>> allEvents;

    public EventRepository(Application application) {
        BirthdatesDatabase database = BirthdatesDatabase.getInstance(application);
        eventDao = database.eventDao();
        allEvents = eventDao.getAllEvents();
    }

    public void insert(Event event) {
        new EventRepository.InsertEventAsyncTask(eventDao).execute(event);
    }

    public void update(Event event) {
        new EventRepository.UpdateEventAsyncTask(eventDao).execute(event);
    }

    public void delete(Event event) {
        new EventRepository.DeleteEventAsyncTask(eventDao).execute(event);
    }

    public void deleteAllEvents() {
        new EventRepository.DeleteAllEventsAsyncTask(eventDao).execute( );
    }

    public LiveData<Event> getEvent(int id) {
        return eventDao.getEvent(id);
    }

    public LiveData<List<Event>> getAllEvents() {
        return allEvents;
    }

    public LiveData<List<Event>> getTodayEvents() {
        return eventDao.getTodayEvents();
    }

    /**
     * ======== Async Tasks ============
     * Queries have to be executed on the background thread - Room prevents them on the main thread
     * That's why it is needed to have async tasks running on the background
     * */
    private static class InsertEventAsyncTask extends AsyncTask<Event, Void, Void> {
        private EventDao eventDao;

        private InsertEventAsyncTask(EventDao eventDao) {
            this.eventDao = eventDao;
        }

        @Override
        protected Void doInBackground(Event... events) {
            eventDao.insert(events[0]);
            return null;
        }
    }

    private static class UpdateEventAsyncTask extends AsyncTask<Event, Void, Void> {
        private EventDao eventDao;

        private UpdateEventAsyncTask(EventDao eventDao) {
            this.eventDao = eventDao;
        }

        @Override
        protected Void doInBackground(Event... events) {
            eventDao.update(events[0]);
            return null;
        }
    }

    private static class DeleteEventAsyncTask extends AsyncTask<Event, Void, Void> {
        private EventDao eventDao;

        private DeleteEventAsyncTask(EventDao eventDao) {
            this.eventDao = eventDao;
        }

        @Override
        protected Void doInBackground(Event... events) {
            eventDao.delete(events[0]);
            return null;
        }
    }

    private static class DeleteAllEventsAsyncTask extends AsyncTask<Void , Void, Void> {
        private EventDao eventDao;

        private DeleteAllEventsAsyncTask(EventDao eventDao) {
            this.eventDao = eventDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            eventDao.deleteAllEvents();
            return null;
        }
    }

    private static class GetEventAsyncTask extends AsyncTask<Integer , Void, LiveData<Event>> {
        private EventDao eventDao;

        private GetEventAsyncTask(EventDao eventDao) {
            this.eventDao = eventDao;
        }


        @Override
        protected LiveData<Event> doInBackground(Integer... integers) {
            return eventDao.getEvent(integers[0]);
        }
    }
}
