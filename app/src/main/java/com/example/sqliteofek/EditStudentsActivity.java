package com.example.sqliteofek;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class EditStudentsActivity extends AppCompatActivity {

    SQLiteDatabase db;
    HelperDB hlp;

    EditText ed_name, ed_address, ed_phone, ed_fatherName, ed_fatherPhone, ed_motherName, ed_motherPhone ,ed_homePhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_students);

        hlp = new HelperDB(this);
        db = hlp.getWritableDatabase();
        db.close();

        ed_name = findViewById(R.id.ed_name);
        ed_address = findViewById(R.id.ed_address);
        ed_phone = findViewById(R.id.ed_phone);
        ed_homePhone = findViewById(R.id.ed_phoneHome);
        ed_fatherName = findViewById(R.id.ed_fatherName);
        ed_fatherPhone = findViewById(R.id.ed_fatherPhone);
        ed_motherName = findViewById(R.id.ed_motherName);
        ed_motherPhone = findViewById(R.id.ed_motherPhone);
    }

    public void onClick_back1(View view) {
        String name, address, phone, homePhone, fatherPhone, fatherName, motherPhone, motherName;
        name = ed_name.getText().toString();
        address = ed_name.getText().toString();
        phone = ed_name.getText().toString();
        homePhone = ed_name.getText().toString();
        fatherName = ed_name.getText().toString();
        fatherPhone = ed_name.getText().toString();
        motherName = ed_name.getText().toString();
        motherPhone = ed_name.getText().toString();

        ContentValues cv = new ContentValues ();
        cv.put (Students.NAME,name);
        cv.put (Students.ADDRESS,address);
        cv.put (Students.PHONE,phone);
        cv.put (Students.HOME_PHONE,homePhone);
        cv.put (Students.FATHER_NAME,fatherName);
        cv.put (Students.FATHER_PHONE,fatherPhone);
        cv.put (Students.MOTHER_NAME,motherName);
        cv.put (Students.MOTHER_PHONE,motherPhone);

        db = hlp.getWritableDatabase();
        db.insert(Students.TABLE_Students, null, cv);
        db.close();
        finish();
    }
}
