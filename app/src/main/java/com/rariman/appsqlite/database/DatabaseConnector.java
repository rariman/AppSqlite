package com.rariman.appsqlite.database;


import android.content.ContentValues;
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
        Cursor cursor = database.query(DatabaseOpenHelper.TABLE_BOOKS, columns, null, null, null, null, DatabaseOpenHelper.COLUMN_TITLE);

        while (cursor.moveToNext())
        {
            int index0 = cursor.getColumnIndex(DatabaseOpenHelper.COLUMN_TITLE);
            int index1 = cursor.getColumnIndex(DatabaseOpenHelper.DESCRIPTION_TITLE);

            String title = cursor.getString(index0);
            String description = cursor.getString(index1);

            Book book = new Book(title, description);

            bookList.add(book);
        }

        return bookList;
    }

    public long insertNewBook(String title, String description)
    {
        ContentValues newBook = new ContentValues();
        newBook.put(DatabaseOpenHelper.COLUMN_TITLE, title);
        newBook.put(DatabaseOpenHelper.DESCRIPTION_TITLE, description);
        open();
        long newRowId = database.insert(DatabaseOpenHelper.TABLE_BOOKS, null, newBook);
        close();

        return newRowId;
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
                                 "(" + COLUMN_ID + " integer primary key autoincrement," +
                                 COLUMN_TITLE + " TEXT, " +
                                 DESCRIPTION_TITLE + " TEXT);";
            db.execSQL(createQuery);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }

}
