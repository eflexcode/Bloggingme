package com.eflexsoft.bloggingme.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {DraftEntity.class}, version = 1)
public abstract class DraftDatabase extends RoomDatabase {

    private static DraftDatabase draftDatabase;

    public abstract DraftDao draftDao();

    public  static synchronized DraftDatabase getDraftDatabase(Context context) {

        if (draftDatabase == null) {
            draftDatabase = Room.databaseBuilder(context, DraftDatabase.class, "StoriesDatabase").build();
        }

        return draftDatabase;
    }
}
