package com.eflexsoft.bloggingme.model;

public class Draft {

    private String draftTitle;

    private String draftBody;

    private int id;

    public Draft(String draftTitle, String draftBody) {
        this.draftTitle = draftTitle;
        this.draftBody = draftBody;
    }

    public String getDraftTitle() {
        return draftTitle;
    }

    public void setDraftTitle(String draftTitle) {
        this.draftTitle = draftTitle;
    }

    public String getDraftBody() {
        return draftBody;
    }

    public void setDraftBody(String draftBody) {
        this.draftBody = draftBody;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
