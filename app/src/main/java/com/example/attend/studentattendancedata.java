package com.example.attend;


import java.util.ArrayList;
import java.util.List;

public class studentattendancedata {
     String ID;
     String name,subject,attendanceStatus,date;

     int totalClassesAttended,totalclasses;
     double  attendancePercentage;

    public studentattendancedata(String ID,String name, int totalClassesAttended, double attendancePercentage) {
        this.ID=ID;
        this.name = name;
        this.totalClassesAttended = totalClassesAttended;
        this.attendancePercentage = attendancePercentage;
    }
    public studentattendancedata(String Subject,int totalClassesAttended,int totalclasses ,double attendancePercentage)
    {
        this.subject =Subject;
        this.totalClassesAttended = totalClassesAttended;
        this.totalclasses =totalclasses;
        this.attendancePercentage=attendancePercentage;
    }

    public studentattendancedata(String ID,String name,String attendanceStatus)
    {
        this.ID= ID;
        this.name=name;
        this.attendanceStatus=attendanceStatus;

    }
    public studentattendancedata (String date ,String attendanceStatus)
    {
        this.date =date;
        this.attendanceStatus=attendanceStatus;
    }

    public static ArrayList<String> GetStudentID(List<studentattendancedata> studentList){
        ArrayList<String> result = new ArrayList<>();
        for(studentattendancedata student: studentList){
            result.add(student.ID);
        }
        return result;
    }
    // Getters for name, totalClassesAttended, and attendancePercentage
}
