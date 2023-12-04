package com.example.attend;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AddStudentActivity extends AppCompatActivity {

    private EditText studentNameEditText, idEditText;
    private Button addStudentButton;

    private Spinner streamSpinner,semesterSpinner,subjectSpinner, yearSpinner, sectionSpinner;
    private DatabaseHelper databaseHelper;
    public static final String Table="Class";
    private  String Stream,Semester,Section;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        // Find the EditText fields and the Add Student button
        studentNameEditText = findViewById(R.id.etStudentName);
        idEditText = findViewById(R.id.etRegNo);
        streamSpinner = findViewById(R.id.etStream);
        semesterSpinner = findViewById(R.id.etSemester);
        //subjectSpinner = findViewById(R.id.etSubject);
//        yearSpinner = findViewById(R.id.etYear);
        sectionSpinner = findViewById(R.id.etSection);
        addStudentButton = findViewById(R.id.btnAddStudent);

        DatabaseHelper db = new DatabaseHelper(getBaseContext());
        // Create an ArrayAdapter for the stream type spinner

        List<String> streamData= new ArrayList<>();
        streamData.add("Select-Stream");
        streamData.addAll(db.getSpinnerStreams(Table));
        ArrayAdapter<String> streamAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, streamData);
        streamAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        streamSpinner.setAdapter(streamAdapter);


        // Create an ArrayAdapter for the semester type spinner
        streamSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Stream = parent.getItemAtPosition(position).toString();
                //  populate the semester spinner based on the selected stream
                List<String> semesterData = new ArrayList<>();
                semesterData.add("Select-Semester");
                semesterData.addAll(db.getSpinnerSemester(Table,Stream));
                ArrayAdapter<String> semesterAdapter = new ArrayAdapter<>(AddStudentActivity.this, android.R.layout.simple_spinner_item, semesterData);
                semesterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                semesterSpinner.setAdapter(semesterAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        semesterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Stream= streamSpinner.getSelectedItem().toString();
                Semester = parent.getItemAtPosition(position).toString();

                List<String> semesterData = new ArrayList<>();
                semesterData.add("Select-Section");
                semesterData.addAll(db.getspinnerSection(Table,Stream,Semester));
                ArrayAdapter<String> semesterAdapter = new ArrayAdapter<>(AddStudentActivity.this, android.R.layout.simple_spinner_item, semesterData);
                semesterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sectionSpinner.setAdapter(semesterAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        addStudentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the values from the EditText fields
                String studentName = studentNameEditText.getText().toString();
                String password = idEditText.getText().toString();
                String ID = idEditText.getText().toString();
                String stream = streamSpinner.getSelectedItem().toString();
                String semester = semesterSpinner.getSelectedItem().toString();
                String section = sectionSpinner.getSelectedItem().toString();

                if(!ID.isEmpty()&&!studentName.isEmpty()&&!stream.isEmpty()&&!semester.isEmpty()&&!section.isEmpty()&&!password.isEmpty())
                {
                    try {
                        Long.parseLong(ID);
                        Student student = new Student(studentName, ID,password, stream, semester, section);
                        DatabaseHelper db = new DatabaseHelper(getBaseContext());
                        if(!db.studentExists(ID)) {
                            db.AddStudent(student);
                            // Successfully added the student
                            Toast.makeText(AddStudentActivity.this, "Student added successfully", Toast.LENGTH_SHORT).show();
                            studentNameEditText.setText("");
                            idEditText.setText("");
                        }
                        else
                        {
                            Toast.makeText(AddStudentActivity.this,"Student already exists",Toast.LENGTH_SHORT).show();
                            idEditText.setText("");
                        }

                    }
                    catch (NumberFormatException e)
                    {
                        Toast.makeText(AddStudentActivity.this,"Student id should be a number",Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(AddStudentActivity.this,"enter all fields",Toast.LENGTH_SHORT).show();
                }


            }
        });
    }
//    private boolean insertStudent(String studentName, String regNo, String stream,String semester,String subject, String year, String section) {
//        ContentValues values = new ContentValues();
//        values.put(DatabaseHelper.COLUMN_STUDENT_NAME, studentName);
//        values.put(DatabaseHelper.COLUMN_REG_NO, regNo);
//        values.put(DatabaseHelper.COLUMN_STREAM,stream);
//        values.put(DatabaseHelper.COLUMN_SEMESTER, semester);
//        values.put(DatabaseHelper.COLUMN_SUBJECT, subject);
//        values.put(DatabaseHelper.COLUMN_YEAR, year);
//        values.put(DatabaseHelper.COLUMN_SECTION, section);
//
//        try {
//            long newRowId = databaseHelper.getWritableDatabase().insert(DatabaseHelper.TABLE_STUDENTS, null, values);
//            return newRowId != -1;
//        }
//        catch (SQLException e)
//        {
//            Log.e("DatabaseError", "Error inserting student: " + e.getMessage());
//            return false;
//        }
//    }
}