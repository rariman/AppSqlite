package com.rariman.appsqlite.database;


import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.rariman.appsqlite.domain.Book;

import java.util.ArrayList;
import java.util.List;

public class DatabaseConnector{
    private static final String DATABASE_NAME = "BookDataBase";
    private static final int DATABASE_VERSION = 1;
    private DatabaseOpenHelper databaseOpenHelper;
    private SQLiteDatabase database;

    public DatabaseConnector(Context context) {
        databaseOpenHelper = new DatabaseOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void open() throws SQLException
    {
        database = databaseOpenHelper.getWritableDatabase();
    }

    public void close()
    {
        if (database != null)
            database.close();
    }

    public List<Book> getAllBooks()
    {
        List<Book> bookList = new ArrayList<>();
        String[] columns = {DatabaseOpenHelper.COLUMN_ID, DatabaseOpenHelper.COLUMN_TITLE, DatabaseOpenHelper.DESCRIPTION_TITLE};
        Cursor cursor = database.query(DATABASE_NAME, columns, null, null, null, null, DatabaseOpenHelper.COLUMN_TITLE);

        //TODO: get data from cursor and add it to bookList

        return bookList;
    }

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
