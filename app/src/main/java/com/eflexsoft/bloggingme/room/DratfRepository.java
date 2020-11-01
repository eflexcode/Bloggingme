package com.eflexsoft.bloggingme.room;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import java.util.List;

public class DratfRepository {

    DraftDatabase draftDatabase;

    DraftDao draftDao;


    DratfRepository(Context context) {

        draftDatabase = DraftDatabase.getDraftDatabase(context);

        draftDao = draftDatabase.draftDao();


    }

    public void insert(DraftEntity draftEntity) {
        new InsertAsync(draftDao).execute(draftEntity);
    }

    public void update(DraftEntity draftEntity) {
        new UpdateAsync(draftDao).execute(draftEntity);
    }

    public void delete(DraftEntity draftEntity) {
        new DeleteAsync(draftDao).execute(draftEntity);
    }

    public void deleteAll() {
        new DeleteAllAsync(draftDao).execute();
    }

    public LiveData<List<DraftEntity>> getAll() {
        return draftDao.getAll();
    }

    class InsertAsync extends AsyncTask<DraftEntity, Void, Void> {

        DraftDao draftDao;

        InsertAsync(DraftDao draftDao) {
            this.draftDao = draftDao;
        }

        @Override
        protected Void doInBackground(DraftEntity... draftEntities) {

            draftDao.insert(draftEntities[0]);

            return null;
        }
    }

    class UpdateAsync extends AsyncTask<DraftEntity, Void, Void> {

        DraftDao draftDao;

        UpdateAsync(DraftDao draftDao) {
            this.draftDao = draftDao;
        }

        @Override
        protected Void doInBackground(DraftEntity... draftEntities) {

            draftDao.update(draftEntities[0]);

            return null;
        }
    }

    class DeleteAsync extends AsyncTask<DraftEntity, Void, Void> {

        DraftDao draftDao;

        DeleteAsync(DraftDao draftDao) {
            this.draftDao = draftDao;
        }

        @Override
        protected Void doInBackground(DraftEntity... draftEntities) {

            draftDao.delete(draftEntities[0]);

            return null;
        }
    }

    class DeleteAllAsync extends AsyncTask<Void, Void, Void> {

        DraftDao draftDao;

        DeleteAllAsync(DraftDao draftDao) {
            this.draftDao = draftDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            draftDao.deleteAll();

            return null;

        }
    }

}
