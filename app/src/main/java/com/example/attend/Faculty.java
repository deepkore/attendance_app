package com.example.attend;

public class Faculty {

    String facultyName,password,position,facultyID;

    public Faculty(String facultyName,String facultyID,String password,String position)
    {
        this.facultyID=facultyID;
        this.facultyName=facultyName;
        this.password=password;
        this.position=position;
    }

    public String getFacultyName()
    {
        return facultyName;
    }
    public String getFacultyId()
    {
        return facultyID;
    }




}
