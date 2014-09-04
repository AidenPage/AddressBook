package com.addressBook.aiden.addressbook;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
/**
 * Created by Aidem on 2014/08/23.
 */
public class DatabaseConnector {
    private static final String DATABASE_NAME="UserContacts";
    private SQLiteDatabase db;
    private DatabaseOpenHelper bdHelper;

    public DatabaseConnector(Context context)
    {
        bdHelper = new DatabaseOpenHelper(context,DATABASE_NAME,null,1);
    }

    public void open()
    {
        db = bdHelper.getWritableDatabase();
    }

    public void close()
    {
        if(db != null)
            db.close();
    }

    public Cursor getAllContacts()
    {
        return db.query("contacts",new String[]{"_id","lastName"},null,null,null,null,"lastName");
    }

    public Cursor getOneContact(Long ID)
    {

        return db.query("contacts",null,"_id="+ID,null,null,null,null);
    }

    public void insertContact(String firstName, String lastName, String phone, String email, String street, String city)
    {
        ContentValues newContact = new ContentValues();
        newContact.put("firstName",firstName);
        newContact.put("lastName", lastName);
        newContact.put("email", email);
        newContact.put("phone",phone);
        newContact.put("street",street);
        newContact.put("city",city);
        open();
        db.insert("contacts",null,newContact);
        close();

    }


    public void deleteContact(Long ID) {
        open();
        db.delete("contacts","_id="+ID,null);
        close();
    }

    public void updateContact(long rowID, String firstName, String lastName, String phone, String email, String street, String city) {

        ContentValues editContact = new ContentValues();
        editContact.put("firstName",firstName);
        editContact.put("lastName", lastName);
        editContact.put("email", email);
        editContact.put("phone",phone);
        editContact.put("street",street);
        editContact.put("city",city);
        open();
        db.update("contacts",editContact,"_id="+rowID,null);
        close();
    }
}
