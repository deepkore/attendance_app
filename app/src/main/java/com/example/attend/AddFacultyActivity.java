package com.example.attend;

import static java.lang.Long.parseLong;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddFacultyActivity extends AppCompatActivity {
    private EditText editTextName,editTextPassword,editTextID;
    private Spinner spinnerDesignation;
    private DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_faculty);
        databaseHelper = new DatabaseHelper(this);


        Button btnAddFaculty = (Button) findViewById(R.id.btnAddFaculty);
        editTextName=findViewById(R.id.name);
        editTextID=findViewById(R.id.facultyid);
        editTextPassword=findViewById(R.id.password);
        spinnerDesignation=findViewById(R.id.designation);

       String DesignationData []= {"Select-Designation","Associate Proffesor","Assistant Proffesor"};
        ArrayAdapter  designationAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,DesignationData);
        designationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDesignation.setAdapter(designationAdapter);

        btnAddFaculty.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 String Name = editTextName.getText().toString();
                 String ID = editTextID.getText().toString();
                 String Password = editTextPassword.getText().toString();
                 String Designation = spinnerDesignation.getSelectedItem().toString();

                 if (!ID.isEmpty() && !Name.isEmpty() && !Password.isEmpty() && Designation != "Select-Designation") {
                     try {
                         Long.parseLong(ID);
                         Faculty faculty= new Faculty(Name,ID,Password,Designation);
                         DatabaseHelper db = new DatabaseHelper(getBaseContext());
                         if (!db.facultyExists(ID))
                         {
                             db.addFaculty(faculty);
                             Toast.makeText(getApplicationContext(), "Faculty Added", Toast.LENGTH_SHORT).show();
                             finish();
                         }
                         else {
                             Toast.makeText(AddFacultyActivity.this,"faculty id already exists",Toast.LENGTH_SHORT).show();
                         }

                     } catch (NumberFormatException e) {
                         Toast.makeText(AddFacultyActivity.this, "faculty id should be number", Toast.LENGTH_SHORT).show();
                     }
                 } else {
                     Toast.makeText(AddFacultyActivity.this, "Enter all the fields", Toast.LENGTH_SHORT).show();

                 }
             }
         });



    }
}