package com.example.attend;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddCourseActivity extends AppCompatActivity {

    private EditText etSubject1, etSubject2, etSubject3, etSubject4, etSubject5;
    private Spinner spinnerStream, spinnerSemester;
    private Button btnAddCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        etSubject1 = findViewById(R.id.etSubject1);
        etSubject2 = findViewById(R.id.etSubject2);
        etSubject3 = findViewById(R.id.etSubject3);
        etSubject4 = findViewById(R.id.etSubject4);
        etSubject5 = findViewById(R.id.etSubject5);
        spinnerStream = findViewById(R.id.etStream);
        spinnerSemester = findViewById(R.id.etSemester);
        btnAddCourse = findViewById(R.id.btnAddCourse);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.stream_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStream.setAdapter(adapter);

// Create separate adapters for UG and PG semester options
        ArrayAdapter<CharSequence> semAdapterUG = ArrayAdapter.createFromResource(this, R.array.semester_typesug, android.R.layout.simple_spinner_item);
        semAdapterUG.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<CharSequence> semAdapterPG = ArrayAdapter.createFromResource(this, R.array.semester_typespg, android.R.layout.simple_spinner_item);
        semAdapterPG.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

// Set the UG semester adapter initially
        spinnerSemester.setAdapter(semAdapterUG);

        spinnerStream.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedStream = spinnerStream.getSelectedItem().toString();

                // Check the selected stream
                if (selectedStream.equals("MCA") || selectedStream.equals("MTech DSE")) {
                    // Set PG semester adapter if it's a PG stream
                    spinnerSemester.setAdapter(semAdapterPG);
                } else {
                    // Set UG semester adapter for other streams
                    spinnerSemester.setAdapter(semAdapterUG);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle nothing selected event
            }
        });

        btnAddCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get values from UI elements
                String subject1 = etSubject1.getText().toString();
                String subject2 = etSubject2.getText().toString();
                String subject3 = etSubject3.getText().toString();
                String subject4 = etSubject4.getText().toString();
                String subject5 = etSubject5.getText().toString();
                String stream = spinnerStream.getSelectedItem().toString();
                String semester = spinnerSemester.getSelectedItem().toString();

                // Perform database insertion here

                // You need to implement the code to insert these values into your "course" table
                // using a SQLite database or another database solution.

                // Display a message indicating that the course has been added
                DatabaseHelper db = new DatabaseHelper(getBaseContext());

                if (!stream.equals("Select Stream") && !semester.equals( "Select Semester") && !subject1.isEmpty() && !subject2.isEmpty() && !subject3.isEmpty() && !subject4.isEmpty() && !subject5.isEmpty()) {
                    // Display a toast message if any of the fields are empty
                    if (!db.CourseExists(stream,semester)) {
                        db.addCourse(stream, semester,subject1,subject2,subject3,subject4,subject5 );
                        Toast.makeText(AddCourseActivity.this, "Course Added successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        Toast.makeText(AddCourseActivity.this,"Course already exists",Toast.LENGTH_SHORT).show();
                    }

                } else {
                    // All fields are non-empty, so add the course to the database
                    Toast.makeText(AddCourseActivity.this, "Please enter all values", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }
}
