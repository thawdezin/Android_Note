package com.thawdezin.note.data.vos;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class MyNote extends RealmObject {

    @PrimaryKey
    private int id;
    @Required
    private String title;
    @Required
    private String description;
    private long dateTimeInstance;

    public MyNote() {
    }

//    public MyNote(int id, String title, String description, long dateTimeInstance) {
//        this.id = id;
//        this.title = title;
//        this.description = description;
//        this.dateTimeInstance = dateTimeInstance;
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getDateTimeInstance() {
        return dateTimeInstance;
    }

    public void setDateTimeInstance(long dateTimeInstance) {
        this.dateTimeInstance = dateTimeInstance;
    }

    @Override
    public String toString() {
        return "MyNote{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", dateTimeInstance=" + dateTimeInstance +
                '}';
    }
}
