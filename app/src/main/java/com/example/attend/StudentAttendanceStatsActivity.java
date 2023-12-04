package com.example.attend;

import static com.example.attend.DatabaseHelper.COLUMN_ATTENDANCE_STATUS;
import static com.example.attend.DatabaseHelper.COLUMN_STUDENT_ID;
import static com.example.attend.DatabaseHelper.COLUMN_SUBJECT;
import static com.example.attend.DatabaseHelper.TABLE_ATTENDANCE;


import static java.security.AccessController.getContext;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class StudentAttendanceStatsActivity extends AppCompatActivity {
    TextView nameTextview,RegnoTextview,StreamTextview,SemTextview,SectionTextview;
    ListView listView,listView2;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_attendance_stats);

        nameTextview = findViewById(R.id.nameTextView);
        RegnoTextview = findViewById(R.id.regNoTextView);
        StreamTextview =findViewById( R.id.streamTextView);
        SemTextview = findViewById(R.id.semesterTextView);
        SectionTextview =findViewById( R.id.sectionTextView);
        listView = findViewById( R.id.subjectListView);
        listView2 = findViewById( R.id.subjectListView1);

        Intent intent = getIntent();
        if (intent != null) {
            id = intent.getStringExtra("id");
        }

        DatabaseHelper db = new DatabaseHelper(getBaseContext());
        Student student = db.getstudentdetails(id);
        nameTextview.setText("Name :"+student.name);
        RegnoTextview.setText("Reg no : "+id);
        StreamTextview.setText("Stream : "+student.stream);
        SemTextview.setText("Sem : "+student.sem);
        SectionTextview.setText("Section : "+student.section);

       List <studentattendancedata> subjectattendancelist = getAttendanceforstudent(id,student.stream,student.sem,student.section);
        StudentAttendanceStatsAdapter adapter = new StudentAttendanceStatsAdapter(StudentAttendanceStatsActivity.this,R.layout.student_attendance_stats_listitem,subjectattendancelist);
        listView.setAdapter(adapter);
        ArrayList<String> listitem =new ArrayList<>();
        listitem.add("hello");
        ArrayAdapter <String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,listitem);
        listView2.setAdapter(adapter1);
    }

    public List<studentattendancedata> getAttendanceforstudent(String id,String stream,String semester,String section)
    {
        DatabaseHelper db1 = new DatabaseHelper(getBaseContext());
        List<studentattendancedata> subjectattendancelist = new ArrayList<>();
        List<String> subjects = db1.getsubject(stream,semester);
        DatabaseHelper databaseHelper = new DatabaseHelper(getBaseContext());
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        for (String subject :subjects)
        {
            String Query = "SELECT "+COLUMN_SUBJECT+","+"COUNT(*) As present_count " + " FROM "  +TABLE_ATTENDANCE+ " WHERE "+ COLUMN_STUDENT_ID+"=? AND "+ COLUMN_ATTENDANCE_STATUS+"='present' AND "+COLUMN_SUBJECT +"=?" ;
            String []SelectionArgs ={id,subject};
            Cursor cursor = db.rawQuery(Query,SelectionArgs);
            if(cursor!=null && cursor.moveToFirst())
            {

                @SuppressLint("Range") String Subject = cursor.getString(cursor.getColumnIndex(COLUMN_SUBJECT));
                if(Subject!=null) {
                    @SuppressLint("Range") int presentCount = cursor.getInt(cursor.getColumnIndex("present_count"));
                    int totalClasses = db1.read_total_classes_taken(stream, semester, section, Subject);

                    float percentage = (totalClasses > 0) ? (float) presentCount / totalClasses * 100 : 0.0f;

                    studentattendancedata subjectattendance = new studentattendancedata(Subject, presentCount, totalClasses, percentage);
                    subjectattendancelist.add(subjectattendance);
                }
                else{
                    studentattendancedata subjectattendance = new studentattendancedata(subject, 0, 0, 0.00);
                    subjectattendancelist.add(subjectattendance);
                }
            }

        }

        return  subjectattendancelist;
    }
}