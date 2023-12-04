package com.example.attend;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class UpdateProfileActivity extends AppCompatActivity {

    private EditText  editOldPassword,editPassword, editPasswordNew;
    private Button  updatePasswordbtn,changePasswordbtn;

    private DatabaseHelper databaseHelper;
    String Table;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        editOldPassword = findViewById(R.id.editoldPassword);
        editPassword = findViewById(R.id.editPassword);
        editPasswordNew = findViewById(R.id.editConfirmPassword);
       // updatePasswordbtn = findViewById(R.id.updatePasswordButton);
        changePasswordbtn = findViewById(R.id.changePasswordButton);

        databaseHelper = new DatabaseHelper(this);
        Intent intent = getIntent();
        if (intent != null) {

            Table = intent.getStringExtra("Table");
        }
        // Click listener for the "View Profile" button
        /*updatePasswordbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigate to the AddStreamActivity (implement this activity) to add a new stream.
                Intent intent = new Intent(UpdateProfileActivity.this, FacultyHomePageActivity.class);
                startActivity(intent);
            }
        });*/

        // Click listener for the "Change Password" button
        changePasswordbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldpass,newpass,confirmpass;
//                try {
                    oldpass = editOldPassword.getText().toString();
                    newpass = editPassword.getText().toString();
                    confirmpass = editPasswordNew.getText().toString();
                    if (oldpass.equals("")||newpass.equals("")||confirmpass.equals(""))
                    {
                        Toast.makeText(UpdateProfileActivity.this, "Mandatory Fileds", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        if (newpass.equals(confirmpass)) {

                            int updatepass = databaseHelper.updatepass(Table,oldpass, newpass);
                            if (updatepass == 1) {
                                editOldPassword.setText("");
                                editPassword.setText("");
                                editPasswordNew.setText("");
                                Toast.makeText(UpdateProfileActivity.this, "Password Changed Successfully", Toast.LENGTH_SHORT).show();
                                //Intent intent = new Intent(UpdateProfileActivity.this,ViewProfileActivity.class);
                                //startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(UpdateProfileActivity.this, "Enter Correct Password", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(UpdateProfileActivity.this, "Password Mismatched", Toast.LENGTH_SHORT).show();
                        }
//                    }
                }
//                catch (Exception e)
//                {
//                    e.getMessage();
//                }
            }
        });
    }
}

