package com.example.sqliteofek;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class EditGradesActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    /**
     * @author Ofek gani
     * @version 1.0
     * @since 02/7
     */

    SQLiteDatabase db;
    HelperDB hlp;
    Cursor crsr;

    EditText ed_grade, ed_quarter, ed_subject;
    Button btn_add,btn_save,btn_back,btn_delete;

    int col1, col2, col3;
    String[] selectionArgs = new String[1];

    ListView gradeList;
    ArrayList<String> tbl = new ArrayList<String> ();
    ArrayAdapter<String> adp;

    Intent gi;
    int studentID,gradeID = -1;

    boolean itemSelected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_grades);

        gi = getIntent ();
        studentID = gi.getIntExtra ("id",-1);

        hlp = new HelperDB(this);
        db = hlp.getWritableDatabase();
        db.close();

        ed_grade = findViewById(R.id.ed_grade);
        ed_quarter = findViewById(R.id.ed_quarter);
        ed_subject = findViewById(R.id.ed_subject);

        gradeList = findViewById(R.id.gradeList);

        btn_add = findViewById(R.id.btn_addGrade);
        btn_back = findViewById(R.id.btn_backToStudent);
        btn_delete = findViewById(R.id.btn_deleteGrade);
        btn_save = findViewById(R.id.btn_saveGrade);

        selectionArgs[0] = String.valueOf (studentID);

        readGradesData (selectionArgs,null);

        gradeList.setOnItemClickListener(this);
        gradeList.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        adp = new ArrayAdapter<String> (this, R.layout.support_simple_spinner_dropdown_item, tbl);
        gradeList.setAdapter(adp);
    }

    /**
     * get index from table and return index`s data
     * @param selectionArgs sort all grades table by id student`s grades
     * @param order order by data
     */
    private void readGradesData(String[] selectionArgs,String order) {

        if(!tbl.isEmpty ())
        {
            tbl.clear ();
        }

        db = hlp.getReadableDatabase();
        crsr = db.query( Grades.TABLE_Grades, null, Grades.STUDENT_ID+ "= ?", selectionArgs, order, null, null);

        col1 = crsr.getColumnIndex(Grades.SUBJECT);
        col2 = crsr.getColumnIndex(Grades.QUARTER);
        col3 = crsr.getColumnIndex(Grades.GRADE);
        crsr.moveToFirst();
        while (!crsr.isAfterLast()) {
            String subject = crsr.getString(col1);
            String quarter = crsr.getString(col2);
            String grade = crsr.getString(col3);

            String tmp = "Subject: "+ subject +", Quarter: " + quarter + ", Grade: " + grade;
            tbl.add(tmp);

            //set gradeID to last item in list to update or delete if item in the list selected.
            if(itemSelected){
                gradeID = tbl.size ();
            }

            crsr.moveToNext();
        }

        //check if there is list to refreshing.
        if(adp != null)
        {
            adp.notifyDataSetChanged ();
        }

        crsr.close();
        db.close();
    }

    /**
     *
     * @param view
     */
    public void onClick_backToStudent(View view) {
        finish ();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        gradeID = i + 1;

        if(gradeID > 0){
            hlp=new HelperDB(this);
            db = hlp.getReadableDatabase();
            crsr = db.query(Grades.TABLE_Grades, null, Grades.STUDENT_ID+ "= ?", selectionArgs, null, null, null);

            //set the edit text`s text to grade`s data
            crsr.move (gradeID);
            ed_grade.setText(""+crsr.getString(crsr.getColumnIndex(Grades.GRADE)));
            ed_quarter.setText(""+crsr.getString(crsr.getColumnIndex(Grades.QUARTER)));
            ed_subject.setText(""+crsr.getString(crsr.getColumnIndex(Grades.SUBJECT)));

            crsr.close ();
        }
    }

    /**
     * add new data to table when the user press on "add".
     * @param view
     */
    public void onClick_addGrade(View view) {
        String st_grade, st_subject, st_quarter;

        st_grade = ed_grade.getText().toString();
        st_subject = ed_subject.getText().toString();
        st_quarter = ed_quarter.getText().toString();

        ContentValues cv = new ContentValues ();

        cv.put (Grades.STUDENT_ID,studentID);
        cv.put (Grades.GRADE,st_grade);
        cv.put (Grades.SUBJECT,st_subject);
        cv.put (Grades.QUARTER,st_quarter);

        db = hlp.getWritableDatabase();
        db.insert(Grades.TABLE_Grades, null, cv);
        db.close();

        //update the list
        readGradesData (selectionArgs,null);
    }

    /**
     * save the data in table by cursor.
     * @param view
     */
    public void onClick_saveGrade(View view) {
        if(gradeID > 0){
            String st_grade, st_subject, st_quarter;

            st_grade = ed_grade.getText().toString();
            st_subject = ed_subject.getText().toString();
            st_quarter = ed_quarter.getText().toString();

            ContentValues cv = new ContentValues ();

            cv.put (Grades.GRADE,st_grade);
            cv.put (Grades.SUBJECT,st_subject);
            cv.put (Grades.QUARTER,st_quarter);

            db = hlp.getReadableDatabase();
            crsr = db.query(Grades.TABLE_Grades, null, Grades.STUDENT_ID+ "= ?", selectionArgs, null, null, null);

            crsr.move (gradeID);

            String id = crsr.getString(crsr.getColumnIndex(Grades.KEY_ID));
            cv.put (Grades.KEY_ID,id);
            db.update (Grades.TABLE_Grades,cv,"ID = ?", new String[]{id});
            db.close();

            //refresh list
            readGradesData (selectionArgs,null);
        }
    }

    /**
     * delete sellected data when the user press "delete"
     * @param view
     */
    public void onClick_deleteGrade(View view) {
        db = hlp.getReadableDatabase();
        crsr = db.query(Grades.TABLE_Grades, null, Grades.STUDENT_ID+ "= ?", selectionArgs, null, null, null);

        crsr.move (gradeID);

        db.delete ( Students.TABLE_Students, "ID = ?", new String[]{"" + gradeID});
        tbl.remove (gradeID-1);
        adp.notifyDataSetChanged ();
        db.close ();
    }

    public void onClick_order(View view) {
        readGradesData (selectionArgs,Grades.GRADE);
    }
}
