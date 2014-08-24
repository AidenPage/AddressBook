package com.addressBook.aiden.addressbook;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuInflater;
import android.database.Cursor;
import android.os.AsyncTask;
import android.widget.TextView;

import java.util.Objects;

/**
 * Created by Aidem on 2014/08/23.
 */
public class ViewContact extends Activity {

    private long rowID;
    private TextView firstNameTextView;
    private TextView lastNameTextView;
    private TextView phoneTextView;
    private TextView emailTextView;
    private TextView streetTextView;
    private TextView cityTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_book);

        firstNameTextView = (TextView) findViewById(R.id.FirstEditText);
        lastNameTextView = (TextView) findViewById(R.id.LastEditText);
        phoneTextView = (TextView) findViewById(R.id.CellNumberEditText);
        emailTextView = (TextView) findViewById(R.id.EmailAddressEditText);
        streetTextView = (TextView) findViewById(R.id.StreetsEditText);
        cityTextView = (TextView) findViewById(R.id.CitysEditText);

        Bundle extras = getIntent().getExtras();
        rowID = extras.getLong("row_id");
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        new LoadContactTask().execute(rowID);
    }

    private class LoadContactTask extends AsyncTask<Long,Objects,Cursor>
    {
        DatabaseConnector dbConn = new DatabaseConnector((ViewContact.this));


        @Override
        protected Cursor doInBackground(Long... params) {
            dbConn.open();
            return dbConn.getOneContact(params[0]);
        }

        @Override
        protected void onPostExecute(Cursor result)
        {
            super.onPostExecute(result);
            result.moveToFirst();

            int firstNameIndex = result.getColumnIndex("firstName");
            int LastNameIndex = result.getColumnIndex("lastName");
            int phoneIndex = result.getColumnIndex("phone");
            int emailIndex = result.getColumnIndex("email");
            int streetIndex = result.getColumnIndex("street");
            int cityIndex = result.getColumnIndex("email");

            firstNameTextView.setText(result.getString(firstNameIndex));
            lastNameTextView.setText(result.getString(LastNameIndex));
            phoneTextView.setText(result.getString(phoneIndex));
            emailTextView.setText(result.getString(emailIndex));
            streetTextView.setText(result.getString(streetIndex));
            cityTextView.setText(result.getString(cityIndex));

            result.close();
            dbConn.close();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu(menu);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_edit_details,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.edit_contact:
                Intent addEditContact = new Intent(this,AddEditContact.class);
                addEditContact.putExtra("row_id",rowID);
                addEditContact.putExtra("firstName",firstNameTextView.getText());
                addEditContact.putExtra("lastName",lastNameTextView.getText());
                addEditContact.putExtra("phone",phoneTextView.getText());
                addEditContact.putExtra("email",emailTextView.getText());
                addEditContact.putExtra("street",streetTextView.getText());
                addEditContact.putExtra("city",cityTextView.getText());
                startActivity(addEditContact);
            return true;
            case R.id.delete_contact:
                deleteContact();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }

    }

    private void deleteContact() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ViewContact.this);
        builder.setTitle(R.string.confirmTitle);
        builder.setMessage(R.string.confirmMessage);

        builder.setPositiveButton(R.string.button_delete, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final DatabaseConnector dbConn = new DatabaseConnector(ViewContact.this);

                AsyncTask<Long,Object,Object> deleTask = new AsyncTask<Long, Object, Object>() {
                    @Override
                    protected Object doInBackground(Long... params) {
                        dbConn.deleteContact(params[0]);
                        return null;
                    }

                    @Override
                    protected  void onPostExecute(Object result)
                    {
                        finish();
                    }

                };
                deleTask.execute(new Long[]{rowID});
            }
        });

        builder.setNegativeButton(R.string.button_cancel,null);
        builder.show();
    }
}