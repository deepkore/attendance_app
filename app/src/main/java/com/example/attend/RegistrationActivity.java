package com.example.attend;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegistrationActivity extends AppCompatActivity {

    private EditText IDEditText, passwordEditText,nameEditText;
    private Spinner userTypeSpinner;
    private Button registerButton;


    private DatabaseHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        nameEditText = findViewById(R.id.nameEditText);
        IDEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        userTypeSpinner = findViewById(R.id.userTypeSpinner);
        registerButton = findViewById(R.id.registerButton);
        db = new DatabaseHelper(this);

        // Create an ArrayAdapter for the user type spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.user_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userTypeSpinner.setAdapter(adapter);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name= nameEditText.getText().toString();
                String ID = IDEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String userType = userTypeSpinner.getSelectedItem().toString();
                if(!name.isEmpty() && !ID.isEmpty()&&!password.isEmpty()) {
                    try {
                        Long.parseLong(ID);
                        db.addAdmin(ID,name, password);
                        Toast.makeText(RegistrationActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } catch (NumberFormatException e) {
                        Toast.makeText(RegistrationActivity.this, "admin id is not a number", Toast.LENGTH_SHORT).show();
                    }
                }
                    else {
                        Toast.makeText(RegistrationActivity.this, "enter all fields", Toast.LENGTH_SHORT).show();
//                    // Registration failed
//                    // You can add error handling or UI feedback here
//                    // For example, displaying an error message
                    }
            }
        });
    }

                // Insert the registration data into the database
//                if (insertUserData(email, password, userType)) {
//                    // Registration successful
//                    // You can add additional handling or UI feedback here
//                    // For example, displaying a success message and navigating to the login page
//                    Toast.makeText(RegistrationActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
//                    startActivity(intent);
//                    finish(); // Close the RegistrationActivity
//                } else {
//                    Toast.makeText(RegistrationActivity.this, "Invalid Email ID", Toast.LENGTH_SHORT).show();
//                    // Registration failed
//                    // You can add error handling or UI feedback here
//                    // For example, displaying an error message
//                }

//    private boolean insertUserData(String email, String password, String userType) {
//        // Insert user data into the database
//        ContentValues values = new ContentValues();
//        values.put(DatabaseHelper.COLUMN_EMAIL, email);
//        values.put(DatabaseHelper.COLUMN_PASSWORD, password);
//        values.put(DatabaseHelper.COLUMN_USER_TYPE, userType);
//
//        long newRowId = db.getWritableDatabase().insert(DatabaseHelper.TABLE_USERS, null, values);
//        return newRowId != -1;
//    }
}