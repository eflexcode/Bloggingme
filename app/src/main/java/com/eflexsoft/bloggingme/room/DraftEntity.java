package com.eflexsoft.bloggingme.room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "offlineStories")
public class DraftEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String draftTitle;

    private String draftBody;


    public DraftEntity(String draftTitle, String draftBody) {
        this.draftTitle = draftTitle;
        this.draftBody = draftBody;
    }

    public String getDraftTitle() {
        return draftTitle;
    }

    public void setDraftTitle(String draftTitle) {
        this.draftTitle = draftTitle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDraftBody() {
        return draftBody;
    }

    public void setDraftBody(String draftBody) {
        this.draftBody = draftBody;
    }
}
