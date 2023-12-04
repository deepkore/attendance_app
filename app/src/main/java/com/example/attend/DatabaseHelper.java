package com.example.attend;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "UserDatabase";
    private static final int DATABASE_VERSION = 2;

    // Table name and column names for users

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASSWORD = "password";

    // Table name and column names for students

    public static final String TABLE_STUDENTS = "students";
    public static final String COLUMN_STUDENT_ID = "student_id";

    public static final String COLUMN_STUDENT_NAME = "student_name";
    public static final String COLUMN_STREAM = "stream";
    public static final String COLUMN_SEMESTER = "semester";
    public static final String COLUMN_SUBJECT = "subject";
    public static final String COLUMN_YEAR = "year";
    public static final String COLUMN_SECTION = "section";


    public static final String TABLE_ATTENDANCE = "attendance";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_ATTENDANCE_STATUS = "attendance_status";
    public static final String COLUMN_ATTENDANCE_ID = "id";

    public static final String TABLE_ASSIGN_CLASS = "assign_class";
    public  static final String COLUMN_TOTAL_CLASSES="total_classes";

    // faculty table
    public static final String TABLE_FACULTY = "facultys";
    private static final String COLUMN_FACULTY_NAME = "faculty_name";
    public static final String COLUMN_FACULTY_ID = "faculty_id";

    public static final String COLUMN_POSITION = "position";


    //admin table
    public static final String TABLE_ADMIN = "admins";



    //Studentclass table
    private static final String TABLE_CLASS = "class";

    private static final String COLUMN_CLASS_ID = "class_id";

    private static final String TABLE_COURSE = "course";

    public static final String COLUMN_COURSE_ID = "course_id";
    public static final String COLUMN_SUBJECT1 = "subject1";
    public static final String COLUMN_SUBJECT2 = "subject2";
    public static final String COLUMN_SUBJECT3 = "subject3";
    public static final String COLUMN_SUBJECT4 = "subject4";
    public static final String COLUMN_SUBJECT5 = "subject5";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_CLASS_TABLE = "CREATE TABLE " + TABLE_CLASS + " (" +
                COLUMN_CLASS_ID + " INTEGER PRIMARY KEY," +
                COLUMN_STREAM + " TEXT," +
                COLUMN_SEMESTER + " TEXT," +
                COLUMN_SECTION + " TEXT," +
                "UNIQUE (" + COLUMN_STREAM + ", " + COLUMN_SEMESTER + ", " + COLUMN_SECTION + "))";
        db.execSQL(CREATE_CLASS_TABLE);


        String CREATE_FACULTY_TABLE = "CREATE TABLE " + TABLE_FACULTY + " (" +
                COLUMN_ID + " TEXT PRIMARY KEY," +
                COLUMN_NAME + " TEXT ," +
                COLUMN_PASSWORD + " TEXT," +
                COLUMN_POSITION + " TEXT)";
        db.execSQL(CREATE_FACULTY_TABLE);

        // Create the students table
        String createStudentTable = "CREATE TABLE " + TABLE_STUDENTS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY , " +
                COLUMN_PASSWORD + " TEXT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_STREAM + " TEXT, " +
                COLUMN_SEMESTER + " TEXT, " +
                COLUMN_SECTION + " TEXT" +
                ")";
        db.execSQL(createStudentTable);

        String CREATE_ASSIGN_CLASS_TABLE = "CREATE TABLE " + TABLE_ASSIGN_CLASS + " (" +
                COLUMN_FACULTY_ID + " TEXT," +
                COLUMN_STREAM + " TEXT," +
                COLUMN_SEMESTER + " TEXT," +
                COLUMN_SECTION + " TEXT," +
               COLUMN_SUBJECT + " TEXT," +
                COLUMN_TOTAL_CLASSES + " INTEGER NOT NULL DEFAULT 0 ," + // New column definition
                "FOREIGN KEY (" + COLUMN_FACULTY_ID + ") REFERENCES " + TABLE_FACULTY + "(" + COLUMN_FACULTY_ID + "))";
        db.execSQL(CREATE_ASSIGN_CLASS_TABLE);



        String createAttendanceTable = "CREATE TABLE " + TABLE_ATTENDANCE + " (" +
                COLUMN_ATTENDANCE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_STUDENT_NAME + " TEXT, " +
                COLUMN_STUDENT_ID + " TEXT, " +
                COLUMN_STREAM+ " TEXT," +
                COLUMN_SECTION + " TEXT, " +
                COLUMN_SUBJECT + " TEXT, " +
                COLUMN_SEMESTER + " TEXT, " +
                COLUMN_DATE + " TEXT, " +
                COLUMN_ATTENDANCE_STATUS + " TEXT" +
                ")";
        db.execSQL(createAttendanceTable);
        // Define the table name



        String CREATE_COURSE_TABLE =
                "CREATE TABLE " + TABLE_COURSE + "(" +
                        COLUMN_COURSE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_STREAM + " TEXT, " +
                        COLUMN_SEMESTER + " TEXT, " +
                        COLUMN_SUBJECT1 + " TEXT, " +
                        COLUMN_SUBJECT2 + " TEXT, " +
                        COLUMN_SUBJECT3 + " TEXT, " +
                        COLUMN_SUBJECT4 + " TEXT, " +
                        COLUMN_SUBJECT5 + " TEXT, " +
                        "UNIQUE(" + COLUMN_STREAM + ", " + COLUMN_SEMESTER + ", " +
                        COLUMN_SUBJECT1 + ", " + COLUMN_SUBJECT2 + ", " + COLUMN_SUBJECT3 + ", " +
                        COLUMN_SUBJECT4 + ", " + COLUMN_SUBJECT5 + "))";
        db.execSQL(CREATE_COURSE_TABLE);
        String CREATE_ADMIN_TABLE =
                "CREATE TABLE " + TABLE_ADMIN + " (" +
                        COLUMN_ID + " INTEGER PRIMARY KEY , " +
                        COLUMN_NAME + " TEXT NOT NULL," +
                        COLUMN_PASSWORD + " TEXT NOT NULL);";
        db.execSQL(CREATE_ADMIN_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database upgrades, if needed

    }

    public void addAdmin(String ID, String name, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, ID);
        values.put(COLUMN_PASSWORD, password);
        values.put(COLUMN_NAME, name);
        db.insert(TABLE_ADMIN, null, values);
        db.close();

    }


    public void addCourse(String stream, String semester, String subject1, String subject2, String subject3, String subject4, String subject5) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_STREAM, stream);
        values.put(COLUMN_SEMESTER, semester);
        values.put(COLUMN_SUBJECT1, subject1);
        values.put(COLUMN_SUBJECT2, subject2);
        values.put(COLUMN_SUBJECT3, subject3);
        values.put(COLUMN_SUBJECT4, subject4);
        values.put(COLUMN_SUBJECT5, subject5);
        db.insert(TABLE_COURSE, null, values);

    }
    boolean CourseExists(String Stream,String Semester) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_COURSE + " WHERE " + COLUMN_STREAM + "=? AND " + COLUMN_SEMESTER +"=?";
        String[] selectionArgs = {Stream,Semester};
        Cursor cursor = db.rawQuery(query, selectionArgs);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.close();
            return true; // faculty already exists
        } else {
            return false; // faculty doesn't exist
        }
    }

    void addClass(String stream, String semester, String section) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_STREAM, stream);
        values.put(COLUMN_SEMESTER, semester);
        values.put(COLUMN_SECTION, section);
        db.insert(TABLE_CLASS, null, values);
    }
    boolean classExists(String Stream,String Semester,String Section) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_CLASS + " WHERE " + COLUMN_STREAM + "=? AND " + COLUMN_SEMESTER +"=? AND " +COLUMN_SECTION +"=?";
        String[] selectionArgs = {Stream,Semester,Section};
        Cursor cursor = db.rawQuery(query, selectionArgs);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.close();
            return true; // faculty already exists
        } else {
            return false; // faculty doesn't exist
        }
    }

    void AddStudent(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PASSWORD,student.password);
        values.put(COLUMN_ID, student.ID);
        values.put(COLUMN_NAME, student.name);
        values.put(COLUMN_STREAM, student.stream);
        values.put(COLUMN_SECTION, student.section);
        values.put(COLUMN_SEMESTER, student.sem);
        db.insert(TABLE_STUDENTS, null, values);
    }

    boolean studentExists(String studentID) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_STUDENTS + " WHERE " + COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(studentID)};
        Cursor cursor = db.rawQuery(query, selectionArgs);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.close();
            return true; // Student already exists
        } else {
            return false; // Student doesn't exist
        }
    }




    void addFaculty(Faculty faculty) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, faculty.facultyName);
        values.put(COLUMN_ID, faculty.facultyID);
        values.put(COLUMN_PASSWORD, faculty.password);
        values.put(COLUMN_POSITION, faculty.position);
        db.insert(TABLE_FACULTY, null, values);
    }

    boolean facultyExists(String facultyid) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_FACULTY + " WHERE " + COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(facultyid)};
        Cursor cursor = db.rawQuery(query, selectionArgs);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.close();
            return true; // faculty already exists
        } else {
            return false; // faculty doesn't exist
        }
    }


    void assignClass(long faculty, String stream, String Semester, String Section, String Subject) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_FACULTY_ID, faculty);
        values.put(COLUMN_STREAM, stream);
        values.put(COLUMN_SEMESTER, Semester);
        values.put(COLUMN_SECTION, Section);
        values.put(COLUMN_SUBJECT, Subject);
        db.insert(TABLE_ASSIGN_CLASS, null, values);

    }
    boolean assignClassExists(String stream, String semester, String section, String subject) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_ASSIGN_CLASS +
                " WHERE " + COLUMN_STREAM + " = ?" +
                " AND " + COLUMN_SEMESTER + " = ?" +
                " AND " + COLUMN_SECTION + " = ?" +
                " AND " + COLUMN_SUBJECT + " = ?";
        String[] selectionArgs = {stream, semester, section, subject};
        Cursor cursor = db.rawQuery(query, selectionArgs);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.close();
            return true; // Assignment already exists
        } else {
            return false; // Assignment doesn't exist
        }
    }


    public Student getstudentdetails(String id){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT "+ COLUMN_NAME+","+COLUMN_STREAM +","+COLUMN_SEMESTER+","+COLUMN_SECTION +
                " FROM "+ TABLE_STUDENTS + " WHERE "+ COLUMN_ID +" =?";
        String selctionArgs[]={id};
        Cursor cursor = db.rawQuery(query,selctionArgs);

        if( cursor.moveToFirst())
        {
            @SuppressLint("Range") String studentName = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
            @SuppressLint("Range") String Stream = cursor.getString(cursor.getColumnIndex( COLUMN_STREAM));
            @SuppressLint("Range") String Semester = cursor.getString(cursor.getColumnIndex(COLUMN_SEMESTER));
            @SuppressLint("Range") String Section = cursor.getString(cursor.getColumnIndex( COLUMN_SECTION));
            Student student = new Student(studentName,Stream,Semester,Section);
            return student;
        }
        return null;
    }
    public int read_total_students(String stream,String semester,String Section)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT COUNT(*) AS TotalStudents FROM "+TABLE_STUDENTS +" WHERE "+COLUMN_STREAM +" =? AND "+COLUMN_SEMESTER+" =? AND "+COLUMN_SECTION+ " =?" ;
        String[] SelectionArgs={stream,semester,Section};
        Cursor cursor = db.rawQuery(query,SelectionArgs);
        if(cursor.moveToFirst())
        {
            @SuppressLint("Range") int total_students= cursor.getInt(cursor.getColumnIndex("TotalStudents"));
            return total_students;
        }
        return  0;
    }
    public int total_present_students(String stream, String semester, String Section, String Subject, String date1) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT " + COLUMN_DATE + ", COUNT(*) AS no_of_present " +
                "FROM " + TABLE_ATTENDANCE +
                " WHERE " + COLUMN_STREAM + " =? AND " +
                COLUMN_SEMESTER + " =? AND " +
                COLUMN_SECTION + " =? AND " +
                COLUMN_SUBJECT + " =? AND " +
                COLUMN_ATTENDANCE_STATUS + "='present'"
                + " GROUP BY " + COLUMN_DATE +
                " HAVING " + COLUMN_DATE + " =?";

        String[] SelectionArgs = {stream, semester, Section, Subject, date1};
        Cursor cursor = db.rawQuery(query, SelectionArgs);

        if (cursor.moveToFirst()) {
            int dateColumnIndex = cursor.getColumnIndex(COLUMN_DATE);
            String datee = cursor.getString(dateColumnIndex);
            if (datee != null && !datee.isEmpty()) {
                @SuppressLint("Range") int total_present_students = cursor.getInt(cursor.getColumnIndex("no_of_present"));
//                Log.d(""+total_present_students, "total present students" );
                return total_present_students;
            }
        }
        cursor.close();
        return -1;
    }

    int read_total_classes_taken(String stream,String semester,String section,String subject)
    {
        SQLiteDatabase db =this.getReadableDatabase();
        String query =" SELECT "+ COLUMN_TOTAL_CLASSES +" FROM "+TABLE_ASSIGN_CLASS +
                " WHERE "+ COLUMN_STREAM + " = ? " +
                " AND " + COLUMN_SEMESTER + " = ? " +
                " AND " + COLUMN_SECTION + " = ? " +
                " AND " + COLUMN_SUBJECT + " = ? ";
        String [] selectionArgs={stream,semester,section,subject};
        Cursor cursor = db.rawQuery(query,selectionArgs);

        if (cursor !=null && cursor.moveToFirst())
        {

            @SuppressLint("Range") int classestaken= cursor.getInt(cursor.getColumnIndex(COLUMN_TOTAL_CLASSES));
            return classestaken;
        }

        return -1;
    }


    public List<String> getsubject(String Stream,String Semester)
    {
        List<String> subjects = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM "+ TABLE_COURSE +" WHERE "+COLUMN_STREAM+ " =?  AND " +COLUMN_SEMESTER+ "=?";
        String[] selectionArgs ={Stream,Semester};
        Cursor cursor = db.rawQuery(query,selectionArgs);
        if(cursor.moveToFirst())
        {
            @SuppressLint("Range") String Subject1= cursor.getString(cursor.getColumnIndex(COLUMN_SUBJECT1));
            @SuppressLint("Range") String Subject2= cursor.getString(cursor.getColumnIndex(COLUMN_SUBJECT2));
            @SuppressLint("Range") String Subject3= cursor.getString(cursor.getColumnIndex(COLUMN_SUBJECT3));
            @SuppressLint("Range") String Subject4= cursor.getString(cursor.getColumnIndex(COLUMN_SUBJECT4));
            @SuppressLint("Range") String Subject5= cursor.getString(cursor.getColumnIndex(COLUMN_SUBJECT5));
            subjects.add(Subject1);
            subjects.add(Subject2);
            subjects.add(Subject3);
            subjects.add(Subject4);
            subjects.add(Subject5);

        }
        return  subjects;
    }


    public List<String> getspinnerfacultyid() {
        List<String> Facultyids = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT DISTINCT " + COLUMN_ID + " FROM " + TABLE_FACULTY + " Faculty", null);

        if (cursor.moveToFirst()) {
            do {
                Facultyids.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return Facultyids;
    }

    public List<String> getSpinnerStreams(String Table) {
        List<String> streams = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT DISTINCT " + COLUMN_STREAM + " FROM " + Table;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                streams.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return streams;
    }

    public List<String> getSpinnerSemester(String Table, String stream) {
        List<String> semester = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT DISTINCT semester FROM  " + Table + "  WHERE " + COLUMN_STREAM + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{stream});

        if (cursor.moveToFirst()) {
            do {
                semester.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return semester;
    }

    public List<String> getspinnerSection(String Table, String stream, String semester) {
        List<String> sections = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        if (stream != null && semester != null) {
            String query = "SELECT DISTINCT section FROM " + Table + " WHERE " + COLUMN_STREAM + " = ? AND " + COLUMN_SEMESTER + " = ?";
            Cursor cursor = db.rawQuery(query, new String[]{stream, semester});

            if (cursor.moveToFirst()) {
                do {
                    sections.add(cursor.getString(0));
                } while (cursor.moveToNext());
            }

            cursor.close();
            db.close();

        }
        return sections;
    }


    public List<String> getspinnerSubject(String Table, String stream, String semester) {
        List<String> subjects = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        if (stream != null && semester != null) {
            String query = "SELECT " + COLUMN_SUBJECT1 + "," +
                    COLUMN_SUBJECT2 + "," +
                    COLUMN_SUBJECT3 + ", " +
                    COLUMN_SUBJECT4 + ", " +
                    COLUMN_SUBJECT5 + " FROM " + Table + " WHERE " + COLUMN_STREAM + " = ? AND " + COLUMN_SEMESTER + " = ?";
            Cursor cursor = db.rawQuery(query, new String[]{stream, semester});

            if (cursor.moveToFirst()) {
                for (int i = 0; i < cursor.getColumnCount(); i++) {
                    String subject = cursor.getString(i);
                    if (subject != null) {
                        subjects.add(subject);
                    }
                }
            }
        }

        return subjects;
    }

    public List<String> getSpinnerStreamsforfaculty(String faculty_id) {
        List<String> streams = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT DISTINCT " + COLUMN_STREAM + " FROM " + TABLE_ASSIGN_CLASS + " WHERE " + COLUMN_FACULTY_ID + "=?";
        Cursor cursor = db.rawQuery(query, new String[]{faculty_id});
        Log.d(cursor.toString() ,"cursor");
        if (cursor.moveToFirst()) {
            do {
                streams.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return streams;
    }

    public List<String> getSpinnerSemesterforfaculty(String facultyid, String stream) {
        List<String> semester = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT DISTINCT semester FROM  " + TABLE_ASSIGN_CLASS + "  WHERE " + COLUMN_FACULTY_ID + "=? AND " + COLUMN_STREAM + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{facultyid, stream});

        if (cursor.moveToFirst()) {
            do {
                semester.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return semester;
    }

    public List<String> getspinnerSectionforfaculty(String facultyid, String stream, String semester) {
        List<String> sections = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        if (stream != null && semester != null) {
            String query = "SELECT DISTINCT section FROM " + TABLE_ASSIGN_CLASS + " WHERE " + COLUMN_FACULTY_ID + " =? AND " + COLUMN_STREAM + " = ? AND " + COLUMN_SEMESTER + " = ?";
            Cursor cursor = db.rawQuery(query, new String[]{facultyid, stream, semester});

            if (cursor.moveToFirst()) {
                do {
                    sections.add(cursor.getString(0));
                } while (cursor.moveToNext());
            }

            cursor.close();
            db.close();

        }
        return sections;
    }

    public List<String> getspinnerSubjectforfaculty(String facultyid, String stream, String semester, String section) {
        List<String> subjects = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();


        if (stream != null && semester != null && section != null) {
            String query = "SELECT " + COLUMN_SUBJECT +
                    " FROM " + TABLE_ASSIGN_CLASS + " WHERE " + COLUMN_FACULTY_ID + " = ? AND " +
                    COLUMN_STREAM + " = ? AND " + COLUMN_SEMESTER + " = ? AND " + COLUMN_SECTION + " = ?";


            Cursor cursor = db.rawQuery(query, new String[]{facultyid, stream, semester, section});
            System.out.println(cursor);
            if (cursor.moveToFirst()) {
                do {
                    subjects.add(cursor.getString(0));
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();

        }
        return subjects;

    }
    public int updatepass(String Table,String oldpass,String newpass)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PASSWORD,newpass);
        return db.update(Table,values,COLUMN_PASSWORD + "=?",new String[]{oldpass});
    }
}







