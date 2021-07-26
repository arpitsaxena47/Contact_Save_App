package com.arpit.contactsaveapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // creating a new variable for our edit text and button.
    private EditText nameEdt, phoneEdt;
    private Button addContactEdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameEdt = findViewById(R.id.idEdtName);
        phoneEdt = findViewById(R.id.idEdtPhoneNumber);

        addContactEdt = findViewById(R.id.idBtnAddContact);

        addContactEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // on below line we are getting text from our edit text.
                String name = nameEdt.getText().toString();
                String phone = phoneEdt.getText().toString();

                if (TextUtils.isEmpty(name)  && TextUtils.isEmpty(phone)) {
                    Toast.makeText(MainActivity.this, "Please enter the data in all fields. ", Toast.LENGTH_SHORT).show();
                } else {
                    if(phone.length() != 10)
                    {
                        Toast.makeText(MainActivity.this, "Please enter Correct Phone Number... ", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        // calling a method to add contact.
                        addContact(name,  phone);
                    }

                }
            }
        });

    }

    private void addContact(String name, String phone) {
        // in this method we are calling an intent and passing data to that
        // intent for adding a new contact.
        Intent contactIntent = new Intent(ContactsContract.Intents.Insert.ACTION);
        contactIntent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
        contactIntent
                .putExtra(ContactsContract.Intents.Insert.NAME, name)
                .putExtra(ContactsContract.Intents.Insert.PHONE, phone);
        startActivityForResult(contactIntent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // in on activity result method.
        if (requestCode == 1) {

            if (resultCode == Activity.RESULT_OK) {

                Toast.makeText(this, "Contact Saved Successfully...", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(MainActivity.this, MainActivity.class);
                startActivity(i);
            }
            // else we are displaying a message as contact addition has cancelled.
            if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(this, "Cancelled Added Contact",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
}
