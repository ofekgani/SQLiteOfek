package com.example.sqliteofek;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class HelperDB extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "dbexam.db";
    private static final int DATABASE_VERSION = 1;
    String strCreate,strDelete;

    public HelperDB(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        strCreate = "Create Table "+ Students.TABLE_Students;
        strCreate +="("+ Students.KEY_ID+"INTEGER PRIMARY KEY,";
        strCreate += " "+Students.NAME+"TEXT,";
        strCreate += " "+Students.ADDRESS+"TEXT,";
        strCreate += " "+Students.PHONE+"TEXT,";
        strCreate += " "+Students.HOME_PHONE+"TEXT,";
        strCreate += " "+Students.FATHER_NAME+"TEXT,";
        strCreate += " "+Students.FATHER_PHONE+"TEXT,";
        strCreate += " "+Students.MOTHER_NAME+"TEXT,";
        strCreate += " "+Students.MOTHER_PHONE+"TEXT";
        strCreate += ");";
        db.execSQL(strCreate);

        strCreate = "Create Table "+ Grades.TABLE_Grades;
        strCreate +="("+ Grades.KEY_ID+"INTEGER PRIMARY KEY,";
        strCreate += " "+Grades.STUDENT_NAME+"TEXT,";
        strCreate += " "+Grades.SUBJECT+"TEXT,";
        strCreate += " "+Grades.QUARTER+"TEXT";
        strCreate += ");";
        db.execSQL(strCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        strDelete = "DROP TABLE IF EXISTS "+ Students.TABLE_Students;
        db.execSQL(strDelete);

        strDelete = "DROP TABLE IF EXISTS "+Grades.TABLE_Grades;
        db.execSQL(strDelete);

        onCreate(db);
    }
}
