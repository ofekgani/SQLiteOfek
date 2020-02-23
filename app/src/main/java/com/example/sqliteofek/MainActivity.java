package com.example.sqliteofek;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    SQLiteDatabase db;
    HelperDB hlp;
    Cursor crsr;

    ListView listView;
    ArrayList<String> tbl = new ArrayList<>();
    ArrayAdapter adp;

    int tablechoise;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.list);

        hlp = new HelperDB(this);
        db = hlp.getWritableDatabase();
        db.close();

        listView.setOnItemClickListener(this);
        listView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);

        tablechoise=0;
        db = hlp.getReadableDatabase();
        crsr = db.query(Students.TABLE_Students, null, null, null, null, null, null);
        int col1 = crsr.getColumnIndex(Students.KEY_ID);
        int col2 = crsr.getColumnIndex(Students.NAME);
        int col3 = crsr.getColumnIndex(Students.PHONE);

        crsr.moveToFirst();
        while (!crsr.isAfterLast()) {
            int key = crsr.getInt(col1);
            String name = crsr.getString(col2);
            String phone = crsr.getString(col3);
            String tmp = "" + key + ", " + name + ", " + phone;
            tbl.add(tmp);
            crsr.moveToNext();
        }
        crsr.close();
        db.close();
        adp = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, tbl);
        listView.setAdapter(adp);

    }

    public void onClick_next(View view) {
        Intent intent = new Intent(this,EditStudentsActivity.class);
        startActivity(intent);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


    }

}
