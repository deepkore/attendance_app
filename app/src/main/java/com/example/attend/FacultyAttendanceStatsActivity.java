package com.example.attend;

import static com.example.attend.DatabaseHelper.COLUMN_ATTENDANCE_STATUS;
import static com.example.attend.DatabaseHelper.COLUMN_SECTION;
import static com.example.attend.DatabaseHelper.COLUMN_SEMESTER;
import static com.example.attend.DatabaseHelper.COLUMN_STREAM;
import static com.example.attend.DatabaseHelper.COLUMN_STUDENT_ID;
import static com.example.attend.DatabaseHelper.COLUMN_STUDENT_NAME;
import static com.example.attend.DatabaseHelper.COLUMN_SUBJECT;
import static com.example.attend.DatabaseHelper.TABLE_ATTENDANCE;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FacultyAttendanceStatsActivity extends AppCompatActivity {


    private TextView total_classes_takenTextview;
    private ListView listView;
    private Spinner streamSpinner,semesterSpinner,sectionSpinner,subjectSpinner;
    private String facultyid,Stream,Semester,Section,Subject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_attendance_stats);

        DatabaseHelper db =  new DatabaseHelper(getBaseContext());
        streamSpinner=findViewById(R.id.spinnerStream);
        semesterSpinner =findViewById(R.id.spinnerSemester);
        sectionSpinner =findViewById(R.id.spinnerSection);
        subjectSpinner =findViewById(R.id.spinnerSubject);
        total_classes_takenTextview=findViewById(R.id.totalclasses);
        listView =findViewById(R.id.listview);

        final FacultyAttendanceStatsAdapter[] adapter = {null};

        List<String> streamData = new ArrayList<>();
        streamData.add("Select-Stream");
        List<String> semesterData = new ArrayList<>();
        semesterData.add("Select-Semester");
        List <String> subjectData=new ArrayList<>();
        subjectData.add("Select-Subject");
        List<String> sectionData = new ArrayList<>();
        sectionData.add("Select-Section");

        Intent intent = getIntent();
        if (intent != null) {
            facultyid = intent.getStringExtra("id");
        }

        streamData.addAll(db.getSpinnerStreamsforfaculty(facultyid));
        ArrayAdapter<String> streamAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, streamData);
        streamAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        streamSpinner.setAdapter(streamAdapter);


        final ArrayList<Student> [] studentData = new ArrayList[]{null};

        streamSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Stream = parent.getItemAtPosition(position).toString();
                //  populate the semester spinner based on the selected stream

                semesterData.addAll(db.getSpinnerSemesterforfaculty(facultyid,Stream));
                ArrayAdapter<String> semesterAdapter = new ArrayAdapter<>(FacultyAttendanceStatsActivity.this, android.R.layout.simple_spinner_item, semesterData);
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
                ArrayAdapter<String> sectionAdapter = new ArrayAdapter<>(FacultyAttendanceStatsActivity.this, android.R.layout.simple_spinner_item, sectionData);
                sectionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sectionSpinner.setAdapter(sectionAdapter);
                // Debug: Log the values
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sectionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Stream= streamSpinner.getSelectedItem().toString();
                Semester =semesterSpinner.getSelectedItem().toString();
                Section = parent.getItemAtPosition(position).toString();


                subjectData.addAll(db.getspinnerSubjectforfaculty(facultyid,Stream,Semester,Section));
                ArrayAdapter<String> subjectAdapter = new ArrayAdapter<>(FacultyAttendanceStatsActivity.this, android.R.layout.simple_spinner_item,subjectData );
                subjectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                subjectSpinner.setAdapter(subjectAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        subjectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Stream =streamSpinner.getSelectedItem().toString();
                Semester= semesterSpinner.getSelectedItem().toString();
                Section = sectionSpinner.getSelectedItem().toString();
                Subject = parent.getItemAtPosition(position).toString();
                int totalclasses=db.read_total_classes_taken(Stream,Semester,Section,Subject);
                if(totalclasses!=-1) {
                    total_classes_takenTextview.setText("TOTAL CLASSES :" +totalclasses);
                }

                List<studentattendancedata> studentattendancesummary= getAttendanceSummaryForStudents(Stream,Semester,Section,Subject);

                adapter[0] = new FacultyAttendanceStatsAdapter(FacultyAttendanceStatsActivity.this, R.layout.faculty_attendance_stats_listitem, studentattendancesummary);
                listView.setAdapter(adapter[0]);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    public List<studentattendancedata> getAttendanceSummaryForStudents(String stream, String semester, String section, String subject) {
        DatabaseHelper db1 =  new DatabaseHelper(getBaseContext());
        int totalClasses=  db1.read_total_classes_taken(stream,semester,section,subject);
        List<studentattendancedata> summaryList = new ArrayList<>();

        DatabaseHelper databaseHelper = new DatabaseHelper(getBaseContext());
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        String query = "SELECT " +COLUMN_STUDENT_NAME+","+ COLUMN_STUDENT_ID + ", COUNT(*) AS present_count " +
                "FROM " + TABLE_ATTENDANCE + " " +
                "WHERE " + COLUMN_STREAM + " = ? AND " + COLUMN_SEMESTER + " = ? AND " +
                COLUMN_SECTION + " = ? AND " + COLUMN_SUBJECT + " = ? AND " +
                COLUMN_ATTENDANCE_STATUS + " = 'present' " +
                "GROUP BY " + COLUMN_STUDENT_ID;

        String[] selectionArgs = {stream, semester, section, subject};

        Cursor cursor = db.rawQuery(query, selectionArgs);
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String studentName = cursor.getString(cursor.getColumnIndex(COLUMN_STUDENT_NAME));
                @SuppressLint("Range") String studentId = cursor.getString(cursor.getColumnIndex( COLUMN_STUDENT_ID));
                @SuppressLint("Range")   int presentCount = cursor.getInt(cursor.getColumnIndex("present_count"));
                Log.d(studentId+","+studentId+","+presentCount,"cursor");

                float percentage = (totalClasses > 0) ? (float) presentCount / totalClasses * 100 : 0.0f;

                studentattendancedata summary = new studentattendancedata( studentId,studentName, presentCount, percentage);
                summaryList.add(summary);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return summaryList;
    }
}
