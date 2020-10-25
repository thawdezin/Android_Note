package com.thawdezin.note.data.db;

import com.thawdezin.note.data.vos.MyNote;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class MyNoteDBAccess {
    private static final Realm realm = Realm.getDefaultInstance();
    private static MyNote noteIdResults;
    private static MyNote note;

    /**
     * Note Edit Method
     */
    public static void editNote(final MyNote results, final long millis, final String mainTitle, final String mainDescription) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Long num = (Long) realm.where(MyNote.class).max("id");
                if (num > results.getId()) {
                    note = realm.where(MyNote.class).equalTo("id", results.getId()).findFirst();
                    note.setTitle(mainTitle);
                    note.setDescription(mainDescription);
                    note.setDateTimeInstance(millis);
                    realm.insertOrUpdate(note);
                } else if (num == results.getId()) {
                    note = realm.where(MyNote.class).equalTo("id", num).findFirst();
                    note.setTitle(mainTitle);
                    note.setDescription(mainDescription);
                    note.setDateTimeInstance(millis);
                    realm.insertOrUpdate(note);
                } else {
                    int nextId = results.getId() + 1;
                    note = realm.createObject(MyNote.class, nextId);
                    note.setTitle(mainTitle);
                    note.setDescription(mainDescription);
                    note.setDateTimeInstance(millis);
                    realm.insertOrUpdate(note);
                }
            }
        });
    }

    /**
     * Note Create Method
     */
    public static void createNote(final long millis, final String mainTitle, final String mainDescription) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Number num = realm.where(MyNote.class).max("id");
                int nextId = (num == null) ? 1 : num.intValue() + 1;
                note = realm.createObject(MyNote.class, nextId);
                note.setTitle(mainTitle);
                note.setDescription(mainDescription);
                note.setDateTimeInstance(millis);
                realm.insertOrUpdate(note);
            }
        });
    }

    /**
     * getting note id method
     */
    public static MyNote getNoteId(int code) {
        noteIdResults = realm.where(MyNote.class).equalTo("id", code).findFirst();
        return noteIdResults;
    }

    /**
     * Note Delete
     */
    public static void deleteNote(final int noteIdResult) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<MyNote> rows = realm.where(MyNote.class).equalTo("id", noteIdResult).findAll();
                rows.deleteAllFromRealm();
            }
        });

    }

    /**
     * getting all Note from realm
     */
    public static List<MyNote> getAllNote() {
        List<MyNote> myNoteList = new ArrayList<>();
        RealmResults<MyNote> results = realm.where(MyNote.class)
                .findAll();
        RealmResults<MyNote> resultSorted = results.sort("dateTimeInstance", Sort.DESCENDING);
        myNoteList.addAll(resultSorted);
        return myNoteList;
    }

    public static void closeReam(){
        if(realm!=null){
            realm.close();
        }
    }
}
