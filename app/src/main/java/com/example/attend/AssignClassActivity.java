package com.example.attend;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AssignClassActivity extends AppCompatActivity {
    private Spinner spinnerStream, spinnerSemester,spinnerSection, spinnerSubject;
    private String  faculty,Stream,Semester,Subject, Section;
    ;
    private static final String Table ="class";
    private AutoCompleteTextView autoCompleteTextView;
    private Button btnAssignClass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_class);
            // Initialize Spinners
            autoCompleteTextView = findViewById(R.id.autoCompleteTextView);
            spinnerStream = findViewById(R.id.spinnerStream);
            spinnerSemester = findViewById(R.id.spinnerSemester);
            spinnerSection = findViewById(R.id.spinnerSection);
            spinnerSubject = findViewById(R.id.spinnerSubject);
            btnAssignClass =findViewById(R.id.btnAssignClass);

        DatabaseHelper db = new DatabaseHelper(getBaseContext());


        List <String> faculty_ids = db.getspinnerfacultyid();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, faculty_ids);
        autoCompleteTextView.setAdapter(adapter);


        List <String> streamData= new ArrayList<>();
        streamData.add("Select-Stream");
        streamData.addAll(db.getSpinnerStreams(Table));
        ArrayAdapter<String> streamAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, streamData);
        streamAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStream.setAdapter(streamAdapter);



        //Create spinner for stream to Assign class to faculty
        spinnerStream.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Get the selected stream and update the Stream variable
                Stream = parentView.getItemAtPosition(position).toString();
                //  populate the semester spinner based on the selected stream
                List<String> semesterData = new ArrayList<>();
                semesterData.add("Select-Semester");
                semesterData.addAll(db.getSpinnerSemester(Table,Stream));
                ArrayAdapter<String> semesterAdapter = new ArrayAdapter<>(AssignClassActivity.this, android.R.layout.simple_spinner_item, semesterData);
                semesterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerSemester.setAdapter(semesterAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Handle the case where nothing is selected (if needed)
            }
        });
        spinnerSemester.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Get the selected semester and update the spinner section
                Stream= spinnerStream.getSelectedItem().toString();
                Semester = parentView.getItemAtPosition(position).toString();

                List<String> semesterData = new ArrayList<>();
                semesterData.add("Select-Section");
                semesterData.addAll(db.getspinnerSection(Table,Stream,Semester));
                ArrayAdapter<String> semesterAdapter = new ArrayAdapter<>(AssignClassActivity.this, android.R.layout.simple_spinner_item, semesterData);
                semesterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerSection.setAdapter(semesterAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Handle the case where nothing is selected (if needed)
            }
        });
        spinnerSection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Stream= spinnerStream.getSelectedItem().toString();
                Semester =spinnerSemester.getSelectedItem().toString();
                List <String> subjectData=new ArrayList<>();
                subjectData.add("Select-Subject");
                subjectData.addAll(db.getspinnerSubject("Course",Stream,Semester));
                ArrayAdapter<String> subjectAdapter = new ArrayAdapter<>(AssignClassActivity.this, android.R.layout.simple_spinner_item,subjectData );
                subjectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerSubject.setAdapter(subjectAdapter);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnAssignClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                faculty = autoCompleteTextView.getText().toString();

                Stream = spinnerStream.getSelectedItem().toString();
                Semester = spinnerSemester.getSelectedItem().toString();
                Section = spinnerSection.getSelectedItem().toString();
                Subject = spinnerSubject.getSelectedItem().toString();

                if (Stream.equals("Select-Stream") || Semester.equals("Select-Semester") || Section.equals("Select-Section") || Subject.equals("Select-Subject")) {
                    // Display a toast message indicating that all fields need to be selected
                    Toast.makeText(AssignClassActivity.this, "Please select all fields", Toast.LENGTH_SHORT).show();
                } else {
                    DatabaseHelper db = new DatabaseHelper(getBaseContext());
                    try{
                        long intfaculty=Long.parseLong(faculty);
                        if(db.facultyExists(faculty)) {
                            if (!db.assignClassExists(Stream, Semester, Section, Subject)) {
                                // Proceed with class assignment
                                db.assignClass(intfaculty, Stream, Semester, Section, Subject);
                                Toast.makeText(AssignClassActivity.this, "Class assigned successfully", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(AssignClassActivity.this, "Class assignment already exists", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(AssignClassActivity.this,"Not a valid faculty id",Toast.LENGTH_SHORT).show();
                        }

                    }
                    catch (NumberFormatException e)
                    {
                        Toast.makeText(AssignClassActivity.this,"enter a valid Faculty id",Toast.LENGTH_SHORT).show();
                    }

                }


            }
        });


        }


    }


