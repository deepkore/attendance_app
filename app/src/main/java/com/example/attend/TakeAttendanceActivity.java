package com.example.attend;

import static com.example.attend.DatabaseHelper.COLUMN_ID;
import static com.example.attend.DatabaseHelper.COLUMN_NAME;
import static com.example.attend.DatabaseHelper.COLUMN_SECTION;
import static com.example.attend.DatabaseHelper.COLUMN_SEMESTER;
import static com.example.attend.DatabaseHelper.COLUMN_STREAM;
import static com.example.attend.DatabaseHelper.COLUMN_STUDENT_ID;
import static com.example.attend.DatabaseHelper.COLUMN_STUDENT_NAME;
import static com.example.attend.DatabaseHelper.COLUMN_SUBJECT;
import static com.example.attend.DatabaseHelper.TABLE_STUDENTS;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class TakeAttendanceActivity extends AppCompatActivity {
    private ImageButton calendar;
    private TextView date;
    private String facultyid,Stream,Semester,Section,Subject,currentDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_attendance);

        DatabaseHelper db = new DatabaseHelper(getBaseContext());

        Spinner streamSpinner = findViewById(R.id.spinnerStream);
        Spinner semesterSpinner =findViewById(R.id.spinnerSemester);
        Spinner sectionspinner = findViewById(R.id.spinnerSection);
        Spinner subjectspinner = findViewById(R.id.spinnerSubject);
        TextView todaysDate = findViewById(R.id.todaysDate);
        ListView listView = findViewById(R.id.listView);
        Button submitBtn = findViewById(R.id.submitBtn);
        final TakeAttendanceListAdapter[] adapter = {null};

        // Create an ArrayAdapter for the stream type spinner
        Intent intent = getIntent();
        if (intent != null) {
            facultyid = intent.getStringExtra("id");
        }


        List<String> streamData = new ArrayList<>();
        streamData.add("Select-Stream");
        List<String> semesterData = new ArrayList<>();
        semesterData.add("Select-Semester");

        List<String> sectionData = new ArrayList<>();
        sectionData.add("Select-Section");

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        currentDate = dateFormat.format(new Date());
        todaysDate.setText("Today's Date: " + currentDate);



            streamData.addAll(db.getSpinnerStreamsforfaculty(facultyid));
            ArrayAdapter<String> streamAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, streamData);
            streamAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            streamSpinner.setAdapter(streamAdapter);


            final ArrayList<Student>[] studentData = new ArrayList[]{null};

            streamSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Stream = parent.getItemAtPosition(position).toString();
                    //  populate the semester spinner based on the selected stream
                    semesterData.addAll(db.getSpinnerSemesterforfaculty(facultyid,Stream));
                    ArrayAdapter<String> semesterAdapter = new ArrayAdapter<>(TakeAttendanceActivity.this, android.R.layout.simple_spinner_item, semesterData);
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


                    sectionData.addAll(db.getspinnerSectionforfaculty(facultyid,Stream,Semester));
                    ArrayAdapter<String> sectionAdapter = new ArrayAdapter<>(TakeAttendanceActivity.this, android.R.layout.simple_spinner_item, sectionData);
                    sectionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sectionspinner.setAdapter(sectionAdapter);
                // Debug: Log the values
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            // Create an ArrayAdapter for the stream type spinner


            // Create an ArrayAdapter for the stream type spinner

        sectionspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                 @Override
                 public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                     Stream= streamSpinner.getSelectedItem().toString();
                     Semester =semesterSpinner.getSelectedItem().toString();
                     Section = parent.getItemAtPosition(position).toString();

                     List <String> subjectData=new ArrayList<>();
                     subjectData.add("Select-Subject");
                     subjectData.addAll(db.getspinnerSubjectforfaculty(facultyid,Stream,Semester,Section));
                     ArrayAdapter<String> subjectAdapter = new ArrayAdapter<>(TakeAttendanceActivity.this, android.R.layout.simple_spinner_item,subjectData );
                     subjectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                     subjectspinner.setAdapter(subjectAdapter);
                 }

                 @Override
                 public void onNothingSelected(AdapterView<?> parent) {

                 }
             });
             subjectspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                 @Override
                 public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                     Stream =streamSpinner.getSelectedItem().toString();
                     Semester= semesterSpinner.getSelectedItem().toString();
                     Section = sectionspinner.getSelectedItem().toString();
                     Subject = parent.getItemAtPosition(position).toString();

                     studentData[0] = GetAllStudents(Stream, Semester,Section);
                     adapter[0] = new TakeAttendanceListAdapter(TakeAttendanceActivity.this, R.layout.takeattendancelistitem, studentData[0]);
                     listView.setAdapter(adapter[0]);
                 }
                 @Override
                 public void onNothingSelected(AdapterView<?> parent) {

                 }
             });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Stream = streamSpinner.getSelectedItem().toString();
                Semester = semesterSpinner.getSelectedItem().toString();
                Section = sectionspinner.getSelectedItem().toString();
                Subject = subjectspinner.getSelectedItem().toString();
                Log.d(Stream + "," + Semester + "," + Section, "hello");

                Map<String, String> attendanceMap = adapter[0].GetAttendanceMap();
                System.out.println(studentData[0]);
                if (Stream != "Select-Stream" && Semester != "Select-Semester" && Section != "Select-Section" && Subject != "Select-Subject") {
                    for (Student student : studentData[0]) {
                        markattendance(student, Subject, Semester, Section, currentDate,Stream, attendanceMap.get(student.ID));
                        Toast.makeText(TakeAttendanceActivity.this, "Attendance stored!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    write_total_classes_taken(facultyid,Stream,Semester,Section,Subject);

                } else {
                    Toast.makeText(TakeAttendanceActivity.this, "Enter all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
    public ArrayList<Student> GetAllStudents(String stream, String semester, String section) {
        ArrayList<Student> result = new ArrayList<>();

        DatabaseHelper databaseHelper = new DatabaseHelper(getBaseContext());
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        String selection = COLUMN_STREAM + " = ? AND " + COLUMN_SEMESTER + " = ? AND " + COLUMN_SECTION + " = ?";

        String[] selectionArgs = {stream, semester, section};

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_STUDENTS + " WHERE " + selection + ";", selectionArgs);
        while (cursor.moveToNext()) {
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
            @SuppressLint("Range") String ID = cursor.getString(cursor.getColumnIndex(COLUMN_ID));

            Student student = new Student(name, ID);
            result.add(student);
        }
        cursor.close();

        return result;

    }
    void markattendance (Student student, String Subject, String Semester,String Section, String currentDate,String Stream, String attendenceStatus)
    {
        DatabaseHelper databaseHelper = new DatabaseHelper(getBaseContext());
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_STUDENT_ID, student.ID);
        values.put(COLUMN_STUDENT_NAME, student.name);
        values.put(COLUMN_STREAM, Stream);
        values.put(COLUMN_SEMESTER, Semester);
        values.put(COLUMN_SECTION, Section);
        values.put(COLUMN_SUBJECT,Subject);
        values.put(DatabaseHelper.COLUMN_DATE, currentDate);
        values.put(DatabaseHelper.COLUMN_ATTENDANCE_STATUS, attendenceStatus);
        db.insert(DatabaseHelper.TABLE_ATTENDANCE, null, values);
        db.close();
    }
    void write_total_classes_taken(String facultyid,String stream,String semester,String section,String subject)
    {
        DatabaseHelper databaseHelper = new DatabaseHelper(getBaseContext());
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        String updateQuery = "UPDATE " + DatabaseHelper.TABLE_ASSIGN_CLASS +
                " SET " + DatabaseHelper.COLUMN_TOTAL_CLASSES + " = " + DatabaseHelper.COLUMN_TOTAL_CLASSES + " + 1 " +
                " WHERE " + DatabaseHelper.COLUMN_FACULTY_ID + " = ? " +
                " AND " + DatabaseHelper.COLUMN_STREAM + " = ? " +
                " AND " + DatabaseHelper.COLUMN_SEMESTER + " = ? " +
                " AND " + DatabaseHelper.COLUMN_SECTION + " = ? " +
                " AND " + DatabaseHelper.COLUMN_SUBJECT + " = ? ";

        String[] selectionArgs = {facultyid, stream, semester, section, subject};

        db.execSQL(updateQuery, selectionArgs);
        db.close();
    }
    }


