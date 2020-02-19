package com.example.sqliteofek;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

public class EditStudentsActivity extends AppCompatActivity {

    EditText ed_name, ed_address, ed_phone, ed_fatherName, ed_fatherPhone, ed_motherName, ed_motherPhone ,ed_homePhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_students);

    }
}
