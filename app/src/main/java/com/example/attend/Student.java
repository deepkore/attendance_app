package com.example.attend;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Student {

    String name;
    String  password, stream, subject, section, sem,ID;


    public Student(String name, String ID, String password, String stream,  String sem, String section){
        this.name = name;
        this.ID =ID;
        this.password = password;
        this.stream = stream;
        this.section = section;
        this.sem = sem;
    }

    public Student(String name, String ID){
        this.name = name;
        this.ID = ID;

    }
    public Student (String name,String stream,String sem,String section)
    {
        this.name= name;
        this.stream= stream;
        this.sem= sem;
        this.section=section;
    }


    public String getName() {
        return name;
    }

    public String getID() {
        return ID;
    }

    public static ArrayList<String> GetStudentNamesFromStudents(List<Student> studentList){
        ArrayList<String> result = new ArrayList<>();
        for(Student student: studentList){
                result.add(student.name);
        }

        return result;
    }
}
