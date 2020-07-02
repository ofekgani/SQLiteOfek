package com.example.sqliteofek;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    /**
     * @author Ofek gani
     * @version 1.0
     * @since 02/7
     */

    ListView listView;

    SQLiteDatabase db;
    HelperDB hlp;
    Cursor crsr;

    int col2, col3;

    ArrayList<String> tbl = new ArrayList<String> ();
    ArrayAdapter<String> adp;

    int idPosition;
    boolean itemSelected = false;

    AlertDialog.Builder adb;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.list);

        intent = new Intent(this,EditStudentsActivity.class);

        readFromTable ();

        buildList ( tbl );
    }

    /**
     *
     * get data from student table and build array of string
     */
    private void readFromTable(){
        hlp=new HelperDB(this);

        db = hlp.getReadableDatabase();
        crsr = db.query(Students.TABLE_Students, null, null, null, null, null, null);
        col2 = getRow (Students.NAME);
        col3 = getRow (Students.PHONE);
        crsr.moveToFirst();
        while (!crsr.isAfterLast()) {
            String name = getRowData(col2);
            String phone = getRowData(col3);
            String tmp = "Name: " + name + ", Phone: " + phone;
            tbl.add(tmp);
            crsr.moveToNext();
        }
        crsr.close();
        db.close();
    }

    //set adapter to list

    /**
     * set adapter to list
     * @param tbl list`s String of data from table
     */
    private void buildList(ArrayList<String> tbl){
        listView.setOnItemClickListener(this);
        listView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        adp = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, tbl);
        listView.setAdapter(adp);
    }

    /**
     * get row from table and return index
     * @param index index from table
     * @return the index of cursor
     */
    private int getRow(String index){
        return crsr.getColumnIndex ( index );
    }

    /**
     * get index from table and return index`s data
     * @param index index from table
     * @return data of index
     */
    private String getRowData(int index){
        return crsr.getString(index);
    }

    /**
     *
     * @param view
     */
    public void onClick_new(View view) {
        intent.putExtra ("id" , -1);
        startActivity(intent);
    }

    /**
     *
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        idPosition = position+1; //student`s id by position in list

        Toast.makeText (MainActivity.this,""+idPosition,Toast.LENGTH_SHORT).show ();

        itemSelected = true; //item in list selected.

    }

    /**
     * when the user press "edit", save the student id to edit his data in other activity.
     * @param view
     */
    public void onClick_edit(View view) {
        if(itemSelected){
            intent.putExtra ("id" , idPosition);
            Toast.makeText (MainActivity.this,""+idPosition,Toast.LENGTH_SHORT).show ();
            startActivity (intent);
        }
    }

    /**
     * delete selected data when the user press "delete".
     * @param view
     */
    public void onClick_delete(View view) {
        if(itemSelected) {
            deleteData ();
            if(tbl.isEmpty ())
            {
                itemSelected = false;
            }
        }
    }

    /**
     * delete data selected.
     */
    private void deleteData() {
        db = hlp.getWritableDatabase ();
        db.delete ( Students.TABLE_Students, "ID = ?", new String[]{"" + idPosition});
        tbl.remove (idPosition-1);
        adp.notifyDataSetChanged ();
        db.close ();
    }

    /**
     * open alert dialog that show all student data when the user press "show".
     * @param view
     */
    public void onClick_show(View view) {
        if(itemSelected) {
            adb = new AlertDialog.Builder (this);

            db = hlp.getReadableDatabase ();
            crsr = db.query (Students.TABLE_Students, null, null, null, null, null, null);
            crsr.move (idPosition);

            adb.setTitle ("Information");

            StringBuffer buffer = new StringBuffer ();
            buffer.append ("Name: " + crsr.getString (crsr.getColumnIndex (Students.NAME)) + "\n");
            buffer.append ("Phone: " + crsr.getString (crsr.getColumnIndex (Students.PHONE)) + "\n");
            buffer.append ("Home Phone: " + crsr.getString (crsr.getColumnIndex (Students.HOME_PHONE)) + "\n");
            buffer.append ("Address: " + crsr.getString (crsr.getColumnIndex (Students.ADDRESS)) + "\n");
            buffer.append ("Father name: " + crsr.getString (crsr.getColumnIndex (Students.FATHER_NAME)) + "\n");
            buffer.append ("Father phone: " + crsr.getString (crsr.getColumnIndex (Students.FATHER_PHONE)) + "\n");
            buffer.append ("Mother name: " + crsr.getString (crsr.getColumnIndex (Students.MOTHER_NAME)) + "\n");
            buffer.append ("Mother phone: " + crsr.getString (crsr.getColumnIndex (Students.MOTHER_PHONE)) + "\n");

            adb.setMessage ("" + buffer.toString ());

            adb.setPositiveButton ("OK", new DialogInterface.OnClickListener () {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel ();
                }
            });
            AlertDialog ad = adb.create ();
            ad.show ();
        }
    }

    /**
     * take the user to grades activity, there the user can edit student`s grades.
     * @param view
     */
    public void onClick_showGrades(View view) {
        if(itemSelected) {
            Intent t = new Intent (this, EditGradesActivity.class);
            t.putExtra ("id", idPosition);
            startActivity (t);
        }
    }
}
