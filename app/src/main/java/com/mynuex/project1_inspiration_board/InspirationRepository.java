package com.mynuex.project1_inspiration_board;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class InspirationRepository {

    private InspirationDao inspirationDao;

    private LiveData<List<Inspiration>> allInspirations;

    public InspirationRepository(Application application) {
        InspirationDatabase db = InspirationDatabase.getDatabase(application);
        inspirationDao = db.mInspirationDao();
        allInspirations = inspirationDao.getAllInspirations();
    }

    // insert data asynchronously (in the background)
    public void insert(Inspiration inspiration) {
        new InsertInspirationAsync(inspirationDao).execute(inspiration);
    }

    public void update(Inspiration inspiration) {
        new UpdateInspirationAsync(inspirationDao).execute(inspiration);
    }

    public void delete(Inspiration inspiration) {
        new DeleteInspirationAsync(inspirationDao).execute(inspiration);
    }

    public void deleteByID(int id) {
        new DeleteInspirationIDAsync(inspirationDao).execute(id);
    }

    private static class InsertInspirationAsync extends AsyncTask<Inspiration, Void, Void> {
        private InspirationDao inspirationDao;

        private InsertInspirationAsync(InspirationDao inspirationDao) {
            this.inspirationDao = inspirationDao;
        }

        @Override
        protected Void doInBackground(Inspiration... inspirations) {
            inspirationDao.insert(inspirations[0]);
            return null;
        }
    }

    private static class UpdateInspirationAsync extends AsyncTask<Inspiration, Void, Void> {
        private InspirationDao inspirationDao;

        private UpdateInspirationAsync(InspirationDao inspirationDao) {
            this.inspirationDao = inspirationDao;
        }

        @Override
        protected Void doInBackground(Inspiration... inspirations) {
            inspirationDao.update(inspirations[0]);
            return null;
        }
    }

    private static class DeleteInspirationAsync extends AsyncTask<Inspiration, Void, Void> {
        private InspirationDao inspirationDao;

        private DeleteInspirationAsync(InspirationDao inspirationDao) {
            this.inspirationDao = inspirationDao;
        }

        @Override
        protected Void doInBackground(Inspiration... inspirations) {
            inspirationDao.delete(inspirations[0]);
            return null;
        }
    }

    private static class DeleteInspirationIDAsync extends AsyncTask<Integer, Void, Void> {
        private InspirationDao inspirationDao;

        private DeleteInspirationIDAsync(InspirationDao inspirationDao) {
            this.inspirationDao = inspirationDao;
        }

        @Override
        protected Void doInBackground(Integer... id) {
            inspirationDao.deleteById(id[0]);
            return null;
        }
    }
    public LiveData<List<Inspiration>> getAllInspirations() {
        return inspirationDao.getAllInspirations();
    }
}
