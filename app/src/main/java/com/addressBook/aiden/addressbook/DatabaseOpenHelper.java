package com.addressBook.aiden.addressbook;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Aidem on 2014/08/24.
 */
public class DatabaseOpenHelper extends SQLiteOpenHelper
{

    public DatabaseOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTale="CREATE TABLE contacts "+
                "(_id integer primary key autoincrement, "+
                "firstName Text, lastName Text, phone Text, "+
                "email Text,street Text,city Text); ";
        db.execSQL(createTale);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
