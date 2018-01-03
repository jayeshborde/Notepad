package com.homelane.notepad.database;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by Dell User on 10/28/2015.
 */
public class DatabaseOpenHelper extends SQLiteAssetHelper {
    private static final String DATABASE_NAME = "notesdb.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
}