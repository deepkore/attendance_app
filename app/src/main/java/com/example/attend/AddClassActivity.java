package com.example.attend;

import static android.app.ProgressDialog.show;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class AddClassActivity extends AppCompatActivity {
    private Spinner spinnerStream, spinnerSemester, spinnerSection;
    private  String Stream,Semester,Section;
    private Button btnAddClass;
    private static  final String Table="Course";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class);

        // Initialize Spinners and Button
        spinnerStream = findViewById(R.id.spinnerStream);
        spinnerSemester = findViewById(R.id.spinnerSemester);
        spinnerSection = findViewById(R.id.spinnerSection);
        btnAddClass = findViewById(R.id.btnAddClass);
        DatabaseHelper db = new DatabaseHelper(getBaseContext());


        List<String> streamData= new ArrayList<>();
        streamData.add("Select-Stream");
        streamData.addAll(db.getSpinnerStreams(Table));
        ArrayAdapter<String> streamAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, streamData);
        streamAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStream.setAdapter(streamAdapter);


        // Create an ArrayAdapter for the semester type spinner
        spinnerStream.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Get the selected stream and update the Stream variable
                Stream = parentView.getItemAtPosition(position).toString();

                // After selecting a stream, you can also update the semester spinner based on the selected stream
                // Add code here to populate the semester spinner based on the selected stream
                List<String> semesterData = new ArrayList<>();
                semesterData.add("Select-Semester");
                semesterData.addAll(db.getSpinnerSemester(Table,Stream));
                ArrayAdapter<String> semesterAdapter = new ArrayAdapter<>(AddClassActivity.this, android.R.layout.simple_spinner_item, semesterData);
                semesterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerSemester.setAdapter(semesterAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Handle the case where nothing is selected (if needed)
            }
        });


        ArrayAdapter<CharSequence> sectionAdapter = ArrayAdapter.createFromResource(this, R.array.section_types, android.R.layout.simple_spinner_item);
        sectionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSection.setAdapter(sectionAdapter);



        btnAddClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Stream =spinnerStream.getSelectedItem().toString();
                Semester = spinnerSemester.getSelectedItem().toString();
                Section = spinnerSection.getSelectedItem().toString();

                
                DatabaseHelper db = new DatabaseHelper(getBaseContext());
                if(Stream !="Select-Stream" && Semester!="Select-Semester" && Section!="Select-Section" ) {
                    if (!db.classExists(Stream,Semester,Section)) {
                        db.addClass(Stream, Semester, Section);
                        Toast.makeText(AddClassActivity.this, "Class Added successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        Toast.makeText(AddClassActivity.this,"Class already exists",Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(AddClassActivity.this, "Enter all the fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}




