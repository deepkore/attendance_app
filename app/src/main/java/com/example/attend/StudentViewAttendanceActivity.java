package com.example.attend;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class StudentViewAttendanceActivity extends AppCompatActivity {
    TextView nameTextview,RegnoTextview,StreamTextview,SemTextview,SectionTextview,dateview,attendancestatus;
    Spinner spinnerSubject;
    ListView listView;
    String studentid;
    ImageView im;
    StudentViewAttendanceAdapter adapter=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_view_attendance);

        nameTextview = findViewById(R.id.nameTextView);
        RegnoTextview = findViewById(R.id.regNoTextView);
        StreamTextview =findViewById( R.id.streamTextView);
        SemTextview = findViewById(R.id.semesterTextView);
        SectionTextview =findViewById( R.id.sectionTextView);
        spinnerSubject =findViewById(R.id.spinnerSubject);
        listView = findViewById( R.id.subjectListView);
        dateview = findViewById(R.id.dateTextView);
        attendancestatus = findViewById(R.id.attendancestatusTextView);
        dateview.setText("");

        im.setImageResource(R.drawable.baseline_fastfood_24);

        Intent intent = getIntent();
        if (intent != null) {
            studentid = intent.getStringExtra("id");
        }

        DatabaseHelper db = new DatabaseHelper(getBaseContext());
        Student student = db.getstudentdetails(studentid);
        nameTextview.setText("Name :"+student.name);
        RegnoTextview.setText("Reg no : "+studentid);
        StreamTextview.setText("Stream : "+student.stream);
        SemTextview.setText("Sem : "+student.sem);
        SectionTextview.setText("Section : "+student.section);

        List <String> subjectData=new ArrayList<>();
        subjectData.add("Select-Subject");
        subjectData.addAll(db.getspinnerSubject("Course",student.stream,student.sem));
        ArrayAdapter<String> subjectAdapter = new ArrayAdapter<>(StudentViewAttendanceActivity.this, android.R.layout.simple_spinner_item,subjectData );
        subjectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSubject.setAdapter(subjectAdapter);


        spinnerSubject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String Subject = parent.getItemAtPosition(position).toString();
                List<studentattendancedata> subjectattendancelist = getAttendanceforstudent(studentid, Subject);
                if (subjectattendancelist != null) {
                    adapter = new StudentViewAttendanceAdapter(StudentViewAttendanceActivity.this, R.layout.student_view_attendance_itemlist, subjectattendancelist);
                    listView.setAdapter(adapter);
                    dateview.setText("Date");
                    dateview.setBackground(getDrawable(R.drawable.gridlines));
                    attendancestatus.setText("Attendance");
                    attendancestatus.setBackground(new ColorDrawable(ContextCompat.getColor(StudentViewAttendanceActivity.this, R.color.blue)));
                } else {
                    dateview.setText("No attendance found");
                    if (adapter != null) {
                        adapter.clear();
                        listView.setAdapter(adapter);
                    }
                    attendancestatus.setText("");
                    attendancestatus.setBackground(null);
                    dateview.setBackground(null);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
            });

    }

    public List<studentattendancedata> getAttendanceforstudent(String id,String subject)
    {
        List<studentattendancedata> studentattendancedata= new ArrayList<>();
        String query = " SELECT "+DatabaseHelper.COLUMN_DATE+","+DatabaseHelper.COLUMN_ATTENDANCE_STATUS+
                " FROM "+DatabaseHelper.TABLE_ATTENDANCE +
                " WHERE "+DatabaseHelper.COLUMN_STUDENT_ID+" =? AND "+
                DatabaseHelper.COLUMN_SUBJECT+" =? ";
        DatabaseHelper databaseHelper = new DatabaseHelper(getBaseContext());
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        String [] SelectionArgs = {id,subject};
        Log.d(subject,"subject");

        Cursor cursor =db.rawQuery(query,SelectionArgs);
        if(cursor.moveToFirst())
        {
            do{
                @SuppressLint("Range") String date = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DATE));
                @SuppressLint("Range") String attendance_status = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_ATTENDANCE_STATUS));
                Log.d(attendance_status,"attendance_Status");
                studentattendancedata studentdata =new studentattendancedata(date,attendance_status);
                studentattendancedata.add(studentdata);
            }while (cursor.moveToNext());

        }
        else{
            return  null;
        }


        return studentattendancedata;
    }

}
