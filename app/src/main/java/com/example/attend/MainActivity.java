
package com.example.attend;

        import android.annotation.SuppressLint;
        import android.content.Intent;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.Spinner;
        import android.widget.TextView;
        import android.widget.Toast;
        import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText IDEditText, passwordEditText;
    private Spinner userTypeSpinner;
    private Button loginButton;
    private TextView signUpButton;
    private DatabaseHelper databaseHelper;
    private String[] credentials;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        IDEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        userTypeSpinner = findViewById(R.id.userTypeSpinner);
        loginButton = findViewById(R.id.loginButton);
        signUpButton = findViewById(R.id.signUpButton);
        databaseHelper = new DatabaseHelper(this);

        // Create an ArrayAdapter for the spinner
        String[] usertypes = {"Admin", "Student", "Faculty"};
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, usertypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userTypeSpinner.setAdapter(adapter);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = IDEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String userType = userTypeSpinner.getSelectedItem().toString();

                // Check user credentials and userType against the database

                if (!id.isEmpty() && !password.isEmpty() && !userType.isEmpty()) {
                    if (isValidCredentials1(id, password, userType)) {
                        Intent intent;
                        if (userType.equals("Faculty")) {
                            intent = new Intent(MainActivity.this, FacultyHomePageActivity.class);
                            intent.putExtra("Table", DatabaseHelper.TABLE_FACULTY);
                        } else if (userType.equals("Student")) {
                            intent = new Intent(MainActivity.this, StudentHomePageActivity.class);
                            intent.putExtra("Table", DatabaseHelper.TABLE_STUDENTS);
                        } else if (userType.equals("Admin")) {
                            intent = new Intent(MainActivity.this, admin_home_page.class);
                            intent.putExtra("Table", DatabaseHelper.TABLE_ADMIN);
                        } else {
                            // Handle the case for an unknown user type
                            return;
                        }

                        intent.putExtra("name", credentials[1]);
                        intent.putExtra("id", credentials[0]);
                        startActivity(intent);
                    } else {
                        Toast.makeText(MainActivity.this, "invalid credentials", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "enter all fields", Toast.LENGTH_SHORT).show();

                }
            }
        });
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigate to the registration page (RegistrationActivity)
//                Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });
    }


    private boolean isValidCredentials1(String ID, String password, String userType) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        // Define the table name based on the user type
        String Table = userType.toLowerCase() + "s";
        // Define the selection query as a raw SQL query
        String query = "SELECT " + DatabaseHelper.COLUMN_ID + "," +
                DatabaseHelper.COLUMN_NAME +
                // Include the name column
                " FROM " + Table +
                " WHERE " + DatabaseHelper.COLUMN_ID + " = ? AND " +
                DatabaseHelper.COLUMN_PASSWORD + " = ?";
        // Define the selection arguments
        String[] selectionArgs = {ID, password};
        Log.d(Table, "query");
        // Query the database using the raw query
        Cursor cursor = db.rawQuery(query, selectionArgs);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME));
                cursor.close();
                db.close();
                if (name != null) {
                    credentials = new String[]{ID, name, userType};
                    return true;
                }
            }
            cursor.close();
        }

        // Invalid credentials or name not found
        db.close();
        return false;
    }
}







