package com.example.sqliteofek;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class EditStudentsActivity extends AppCompatActivity {

    EditText ed_name, ed_address, ed_phone, ed_fatherName, ed_fatherPhone, ed_motherName, ed_motherPhone ,ed_homePhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_students);

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

        finish();
    }
}
