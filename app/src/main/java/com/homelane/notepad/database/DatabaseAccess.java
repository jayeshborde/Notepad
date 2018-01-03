package com.homelane.notepad.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.homelane.notepad.pojo.Note;

import java.util.ArrayList;
import java.util.List;


public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;
    public String TABLE_NOTE = "notes";

    public String KEY_ID = "id";
    public String KEY_TITLE = "title";
    public String KEY_TEXT = "text";
    public String KEY_IMAGE = "image";
    public String KEY_DATE_TIME = "created_at";
    public String KEY_IS_VISIBLE = "is_visible";


    public int id;
    public String text;
    public String title;
    public String image;
    public String dateTime;


    private DatabaseAccess(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);

    }

    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

    /*public Dinvishesh getDinvisheshByDate(String find_by_date){
        //Cursor cursor = database.rawQuery("SELECT * FROM "+TABLE_UKHANE,null);
        Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_DINVISHESH + " WHERE "+KEY_DATE+" = "+find_by_date, null);
        Dinvishesh dinvishesh = null;
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            id =cursor.getInt(0);
            date=cursor.getString(1);
            jagtik_divas=cursor.getString(2);
            thalak_ghatana=cursor.getString(3);
            birth=cursor.getString(4);
            death=cursor.getString(5);
            dinvishesh = new Dinvishesh(id,date,jagtik_divas,thalak_ghatana,birth,death);
            cursor.moveToNext();
        }
        cursor.close();
        return dinvishesh;
    }*/


    public int getNotesCount() {
        database = openHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT COUNT(*) FROM " + TABLE_NOTE,null);
        cursor.moveToFirst();
        int row_count=Integer.parseInt(cursor.getString(0));
        cursor.close();
        close();
        return row_count;

    }

    public Note getNote(int id){
        //Cursor cursor = database.rawQuery("SELECT * FROM "+TABLE_UKHANE,null);
        database = openHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_NOTE + " WHERE "+KEY_ID+" = "+id, null);
        Note note = null;
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            id =cursor.getInt(0);
            String title = cursor.getString(1);
            String text = cursor.getString(2);
            String image = cursor.getString(3);
            String dateTime =cursor.getString(4);
            note = new Note(id,title,text,image, dateTime);
            cursor.moveToNext();
        }
        cursor.close();
        return note;
    }



    public List<Note> getNoteList(){
        List<Note> list = new ArrayList<Note>();
        //Cursor cursor = database.rawQuery("SELECT * FROM "+TABLE_UKHANE,null);
        //Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_NOTE , null);
        Cursor cursor = database.query(TABLE_NOTE, new String[] { KEY_ID,
                        KEY_TITLE, KEY_TEXT, KEY_IMAGE, KEY_DATE_TIME }, KEY_IS_VISIBLE + "=?",
                new String[] { "0" }, null, null, KEY_ID +" DESC" , null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            if(cursor.getString(1)!=null){
                id =cursor.getInt(0);
                title = cursor.getString(1);
                text=cursor.getString(2);
                image=cursor.getString(3);
                dateTime=cursor.getString(4);
                list.add(new Note(id,title,text,image,dateTime));
            }
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public void addNote(Note note) {
        ContentValues values = new ContentValues();
        //values.put(KEY_OBJECT_ID, challenge.getObjectId()); // site title
        values.put(KEY_TITLE, note.getTitle());
        values.put(KEY_TEXT, note.getText());
        values.put(KEY_IMAGE, note.getImage());
        values.put(KEY_DATE_TIME, note.getDateTime());
        Long returnval=database.insert(TABLE_NOTE, null, values);
        System.out.println("Her in on addNote: "+returnval);
        close();
    }

    public int deleteNote(int note_id) {
        int delete = database.delete(TABLE_NOTE, KEY_ID + " = ?",
                new String[] { String.valueOf(note_id)});
        System.out.println("Her in on deleteSite: ");
        close();
        return delete;
    }





}