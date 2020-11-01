package com.eflexsoft.bloggingme.room;

import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DraftDao {

    @Insert
    void insert(DraftEntity draftEntity);

    @Update
    void update(DraftEntity draftEntity);

    @Delete
    void delete(DraftEntity draftEntity);

    @Query("DELETE  FROM offlineStories")
    void deleteAll();

    @Query("SELECT * FROM OFFLINESTORIES ORDER BY ID")
    LiveData<List<DraftEntity>> getAll();

}
