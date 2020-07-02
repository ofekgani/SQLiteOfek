package com.example.sqliteofek;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class EditStudentsActivity extends AppCompatActivity {
    /**
     * @author Ofek gani
     * @version 1.0
     * @since 02/7
     */

    SQLiteDatabase db;
    HelperDB hlp;
    Cursor crsr;

    EditText ed_name, ed_address, ed_phone, ed_fatherName, ed_fatherPhone, ed_motherName, ed_motherPhone ,ed_homePhone;

    Intent gi;
    int idPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_students);

        gi = getIntent ();
        idPosition = gi.getIntExtra ("id",-1);
        Toast.makeText (EditStudentsActivity.this,""+idPosition,Toast.LENGTH_SHORT).show ();

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

        if(idPosition > 0){
            hlp=new HelperDB(this);
            db = hlp.getReadableDatabase();
            crsr = db.query(Students.TABLE_Students, null, null, null, null, null, null);
            crsr.move (idPosition);
            ed_name.setText(""+crsr.getString(crsr.getColumnIndex(Students.NAME)));
            ed_phone.setText(""+crsr.getString(crsr.getColumnIndex(Students.PHONE)));
            db.close ();
            crsr.close ();
        }
    }

    /**
     * back to main activity
     * @param view
     */
    public void onClick_back1(View view) {
        Intent t = new Intent(this, MainActivity.class);
        startActivity(t);
    }

    /**
     * save all data when user press "save"
     * @param view
     */
    public void onClick_save(View view) {
        String name, address, phone, homePhone, fatherPhone, fatherName, motherPhone, motherName;

        name = ed_name.getText().toString();
        address = ed_address.getText().toString();
        phone = ed_phone.getText().toString();
        homePhone = ed_homePhone.getText().toString();
        fatherName = ed_fatherName.getText().toString();
        fatherPhone = ed_fatherPhone.getText().toString();
        motherName = ed_motherName.getText().toString();
        motherPhone = ed_motherPhone.getText().toString();

        ContentValues cv = new ContentValues ();

        cv.put (Students.NAME,name);
        cv.put (Students.ADDRESS,address);
        cv.put (Students.PHONE,phone);
        cv.put (Students.HOME_PHONE,homePhone);
        cv.put (Students.FATHER_NAME,fatherName);
        cv.put (Students.FATHER_PHONE,fatherPhone);
        cv.put (Students.MOTHER_NAME,motherName);
        cv.put (Students.MOTHER_PHONE,motherPhone);

        if(idPosition > 0){
            hlp=new HelperDB(this);
            db = hlp.getReadableDatabase();
            crsr = db.query(Students.TABLE_Students, null, null, null, null, null, null);
            crsr.move (idPosition);
            String id = crsr.getString(crsr.getColumnIndex(Students.KEY_ID));
            cv.put (Students.KEY_ID,id);
            db.update (Students.TABLE_Students,cv,"ID = ?", new String[]{id});
            db.close();
        }
        else
        {
            db = hlp.getWritableDatabase();
            db.insert(Students.TABLE_Students, null, cv);
            db.close();
        }
    }
}
