package com.example.attend;

import static com.example.attend.DatabaseHelper.COLUMN_ATTENDANCE_STATUS;
import static com.example.attend.DatabaseHelper.COLUMN_STUDENT_ID;
import static com.example.attend.DatabaseHelper.COLUMN_STUDENT_NAME;

import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FacultyViewAttendanceActivity extends AppCompatActivity {
    private LinearLayout selectDateButton;
    private TextView selectedDateTextView,totalStudentsTextview, presentTextview, absentTextview;
    private ListView listView;
    private int year, month, day,present,absent,totalStudents;
    private Spinner streamSpinner,semesterSpinner,sectionSpinner,subjectSpinner;
    private String facultyid,Stream,Semester,Section,Subject,date;
    FacultyViewAttendanceAdapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_view_attendance);


            DatabaseHelper db =  new DatabaseHelper(getBaseContext());
            selectDateButton = findViewById(R.id.selectdatebutton);
            selectedDateTextView = findViewById(R.id.viewdate);
            streamSpinner=findViewById(R.id.spinnerStream);
            semesterSpinner =findViewById(R.id.spinnerSemester);
            sectionSpinner =findViewById(R.id.spinnerSection);
            subjectSpinner =findViewById(R.id.spinnerSubject);
            totalStudentsTextview=findViewById(R.id.totalstudents);
            presentTextview =findViewById(R.id.present);
            absentTextview =findViewById( R.id.absent);
            listView =findViewById(R.id.listview);






            List<String> streamData = new ArrayList<>();
            streamData.add("Select-Stream");
            List<String> semesterData = new ArrayList<>();
            semesterData.add("Select-Semester");
            List <String> subjectData=new ArrayList<>();
            subjectData.add("Select-Subject");
            List<String> sectionData = new ArrayList<>();
            sectionData.add("Select-Section");
             Calendar calendar = Calendar.getInstance();
              int currentYear = calendar.get(Calendar.YEAR);
              int currentMonth = calendar.get(Calendar.MONTH);
             int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
             date = currentDay + "-" + (currentMonth + 1) + "-" + currentYear;
            Intent intent = getIntent();
            if (intent != null) {
                facultyid = intent.getStringExtra("id");
            }
            Log.d(facultyid,"facultyId");
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
                    ArrayAdapter<String> semesterAdapter = new ArrayAdapter<>(FacultyViewAttendanceActivity.this, android.R.layout.simple_spinner_item, semesterData);
                    semesterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    semesterSpinner.setAdapter(semesterAdapter);
                    totalStudentsTextview.setText("");
                    presentTextview.setText("");
                    absentTextview.setText("");
                    selectedDateTextView.setText("");
                    if(adapter!=null) {
                        adapter.clear();
                        listView.setAdapter(adapter);
                    }
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
                    ArrayAdapter<String> sectionAdapter = new ArrayAdapter<>(FacultyViewAttendanceActivity.this, android.R.layout.simple_spinner_item, sectionData);
                    sectionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sectionSpinner.setAdapter(sectionAdapter);
                    totalStudentsTextview.setText("");
                    presentTextview.setText("");
                    absentTextview.setText("");
                    selectedDateTextView.setText("");
                    if(adapter!=null) {
                        adapter.clear();
                        listView.setAdapter(adapter);
                    }

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
                    ArrayAdapter<String> subjectAdapter = new ArrayAdapter<>(FacultyViewAttendanceActivity.this, android.R.layout.simple_spinner_item,subjectData );
                    subjectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    subjectSpinner.setAdapter(subjectAdapter);
                    totalStudentsTextview.setText("");
                    presentTextview.setText("");
                    absentTextview.setText("");
                    selectedDateTextView.setText("");
                    if(adapter!=null) {
                        adapter.clear();
                        listView.setAdapter(adapter);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            subjectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    totalStudentsTextview.setText("");
                    presentTextview.setText("");
                    absentTextview.setText("");
                    selectedDateTextView.setText("");
                    if(adapter!=null) {
                        adapter.clear();
                        listView.setAdapter(adapter);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            selectDateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDatePickerDialog();
                }
            });

        }
    private void showDatePickerDialog() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                DatabaseHelper db = new DatabaseHelper(getBaseContext());
                selectedDateTextView.setText("Selected Date :"+dayOfMonth + "/" + (month + 1) + "/" + year);
                Stream = streamSpinner.getSelectedItem().toString();
                Semester = semesterSpinner.getSelectedItem().toString();
                Section = sectionSpinner.getSelectedItem().toString();
                Subject = subjectSpinner.getSelectedItem().toString();
                if (dayOfMonth<10){

                    date = "0"+dayOfMonth + "-" + (month + 1) + "-" + year;
                }
                else {
                    date = dayOfMonth + "-" + (month + 1) + "-" + year;
                }
                Log.d("" + date, "date");
                totalStudents = db.read_total_students(Stream, Semester, Section);

                if (date != null && !Stream.equals("Select-Stream") && !Semester.equals("Select-Semester")  && !Section.equals("Select-Section") && !Subject.equals("Select-Subject")) {
                    present = db.total_present_students(Stream, Semester, Section, Subject, date);
                    absent = totalStudents - present;
                    Log.d("" + present, "present");
                    if(present!=-1) {
                        totalStudentsTextview.setText("STUDENTS :" + totalStudents);
                        presentTextview.setText("PRESENT:" + present);
                        absentTextview.setText("ABSENT:" + absent);
                    }else{
                        totalStudentsTextview.setText("NO attendance record found ");
                        presentTextview.setText("");
                        absentTextview.setText("");
                    }
                } else {
                    Toast.makeText(FacultyViewAttendanceActivity.this,"enter all fields ",Toast.LENGTH_SHORT).show();
                }


                List<studentattendancedata> studentattendancesummary = getAttendanceSummaryForStudents(Stream, Semester, Section, Subject, date);
                adapter = new FacultyViewAttendanceAdapter(FacultyViewAttendanceActivity.this, R.layout.faculty_view_attendance_itemlist, studentattendancesummary);
                listView.setAdapter(adapter);

            }
        };

        // Get the current date

        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        // Create and show the DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                dateSetListener,
                currentYear,
                currentMonth,
                currentDay
        );
        datePickerDialog.show();

    }
        private  List<studentattendancedata> getAttendanceSummaryForStudents(String stream,String semester,String section,String subject,String date1){
            DatabaseHelper databaseHelper = new DatabaseHelper(getBaseContext());
            SQLiteDatabase db = databaseHelper.getReadableDatabase();
           List<studentattendancedata>  studentattendancedataList = new ArrayList<>();
            String query = "SELECT " + COLUMN_STUDENT_ID+","+DatabaseHelper.COLUMN_STUDENT_NAME+","+DatabaseHelper.COLUMN_ATTENDANCE_STATUS +
                    " FROM " + DatabaseHelper.TABLE_ATTENDANCE +
                    " WHERE " +
                    DatabaseHelper.COLUMN_STREAM + " =? AND " +
                    DatabaseHelper.COLUMN_SEMESTER + " =? AND " +
                    DatabaseHelper.COLUMN_SECTION + " =? AND " +
                    DatabaseHelper.COLUMN_SUBJECT + " =? AND " +
                    DatabaseHelper.COLUMN_DATE + " =? ";
            String[] SelectionArgs = {stream, semester, section, subject, date1};
            Cursor cursor = db.rawQuery(query,SelectionArgs);
            if(cursor.moveToFirst())
            {
                do{
                    @SuppressLint("Range" ) String studentId = cursor.getString(cursor.getColumnIndex(COLUMN_STUDENT_ID));
                    @SuppressLint("Range") String studentName = cursor.getString(cursor.getColumnIndex(COLUMN_STUDENT_NAME));
                    @SuppressLint("Range") String attendanceStatus= cursor.getString(cursor.getColumnIndex(COLUMN_ATTENDANCE_STATUS));
                    studentattendancedata studentattendancedata= new  studentattendancedata(studentId,studentName,attendanceStatus);
                    studentattendancedataList.add(studentattendancedata);
                }while (cursor.moveToNext());
            }
            return studentattendancedataList;
        }
}