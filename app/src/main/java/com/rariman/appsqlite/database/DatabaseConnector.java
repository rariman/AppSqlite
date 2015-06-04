package com.rariman.appsqlite.database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseConnector{
    private static final String DATABASE_NAME = "BookDataBase";
    private static final int DATABASE_VERSION = 1;


    private class DatabaseOpenHelper extends SQLiteOpenHelper
    {
        private static final String TABLE_BOOKS = "books";
        private static final String COLUMN_ID= "_id";
        private static final String COLUMN_TITLE = "title";
        private static final String DESCRIPTION_TITLE = "description";

        public DatabaseOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String createQuery = "CREATE TABLE " + TABLE_BOOKS +
                                 "(" + COLUMN_ID + " integer primary key autoincrement" +
                                 COLUMN_TITLE + " TEXT, " +
                                 DESCRIPTION_TITLE + " TEXT);";
            db.execSQL(createQuery);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }

}
