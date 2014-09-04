package com.addressBook.aiden.addressbook;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.app.AlertDialog;
import android.os.AsyncTask;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Aidem on 2014/08/24.
 */
public class AddEditContact extends Activity{

    private long rowID;

    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText phoneEditText;
    private EditText emailEditText;
    private EditText streetEditText;
    private EditText cityEditText;

    @Override
    public void onCreate(Bundle saveInstanceState)
    {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.add_edit_details);

        firstNameEditText = (EditText) findViewById(R.id.FirstNameEditText);
        lastNameEditText = (EditText) findViewById(R.id.LastNameEditText);
        phoneEditText = (EditText) findViewById(R.id.PhoneEditText);
        emailEditText = (EditText) findViewById(R.id.EmailEditText);
        streetEditText = (EditText) findViewById(R.id.StreetEditText);
        cityEditText = (EditText) findViewById(R.id.CityEditText);

        Bundle extras = getIntent().getExtras();
        if(extras != null)
        {
            rowID = extras.getLong("row_id");
            firstNameEditText.setText(extras.getString("firstName"));
            lastNameEditText.setText(extras.getString("lastName"));
            phoneEditText.setText(extras.getString("phone"));
            emailEditText.setText(extras.getString("email"));
            streetEditText.setText(extras.getString("street"));
            cityEditText.setText(extras.getString("city"));
        }

        Button saveContactButton = (Button) findViewById(R.id.saveContactButton);
        saveContactButton.setOnClickListener(saveContactButtonClicked);
    }

    OnClickListener saveContactButtonClicked = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (lastNameEditText.getText().length()!= 0)
            {
                final AsyncTask<Object,Object,Object> saveContacttask = new AsyncTask<Object, Object, Object>() {
                    @Override
                    protected Object doInBackground(Object... params) {
                        saveContact();
                        return null;
                    }
                    @Override
                    protected  void onPostExecute(Object result)
                    {
                        finish();
                    }
                };
                saveContacttask.execute((Object[])null);
            }
            else
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(AddEditContact.this);
                builder.setTitle(R.string.errorTitle);
                builder.setMessage(R.string.errorMessage);
                builder.setPositiveButton(R.string.errorButton,null);
                builder.show();
            }
        }
    };

    private  void saveContact()
    {
        DatabaseConnector dbConn = new DatabaseConnector(this);
        if(getIntent().getExtras() == null)
        {
            dbConn.insertContact(firstNameEditText.getText().toString(),
                                 lastNameEditText.getText().toString(),
                                 phoneEditText.getText().toString(),
                                 emailEditText.getText().toString(),
                                 streetEditText.getText().toString(),
                                 cityEditText.getText().toString());
        }
        else
        {
            dbConn.updateContact(rowID,
                    firstNameEditText.getText().toString(),
                    lastNameEditText.getText().toString(),
                    phoneEditText.getText().toString(),
                    emailEditText.getText().toString(),
                    streetEditText.getText().toString(),
                    cityEditText.getText().toString());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.activity_address_book,menu);
        return true;
    }
}
