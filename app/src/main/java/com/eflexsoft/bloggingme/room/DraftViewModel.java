package com.eflexsoft.bloggingme.room;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import java.util.List;

public class DraftViewModel extends AndroidViewModel {

    DratfRepository repository;

    public DraftViewModel(@NonNull Application application) {
        super(application);

        repository = new DratfRepository(application);

    }

    public void insert(DraftEntity draftEntity) {
      repository.insert(draftEntity);
    }

    public void update(DraftEntity draftEntity) {
        repository.update(draftEntity);
    }

    public void delete(DraftEntity draftEntity) {
        repository.delete(draftEntity);
    }

    public void deleteAll() {
       repository.deleteAll();
    }

    public LiveData<List<DraftEntity>> getAll() {
        return   repository.getAll();
    }


}
