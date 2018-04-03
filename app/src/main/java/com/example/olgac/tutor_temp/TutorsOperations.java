package com.example.olgac.tutor_temp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.olgac.tutor_temp.model.Tutor;

import java.util.ArrayList;
import java.util.List;

import OpenHelper.SQLite_OpenHelper;

/**
 * Created by olgac on 3/25/2018.
 */

public class TutorsOperations {

    public static final String LOGTAG = "TUTORS_MNGMNT_SYS";

    SQLiteOpenHelper dbhandler;
    SQLiteDatabase database;

    private static final String[] allColumns = {
            SQLite_OpenHelper.COLUMN_ID,
            SQLite_OpenHelper.COLUMN_FIRST_NAME,
            SQLite_OpenHelper.COLUMN_LAST_NAME,
            SQLite_OpenHelper.COLUMN_EMAIL,
            SQLite_OpenHelper.COLUMN_PHONE,
            SQLite_OpenHelper.COLUMN_CAMPUS,
            SQLite_OpenHelper.COLUMN_SUBJECT,
            SQLite_OpenHelper.COLUMN_SKILL
    };

    public TutorsOperations(Context context){
        dbhandler = new SQLite_OpenHelper(context);
    }

    public void open(){
        Log.i(LOGTAG,"Database Opened");
        database = dbhandler.getWritableDatabase();
    }
    public void close(){
        Log.i(LOGTAG, "Database Closed");
        dbhandler.close();
    }
    public Tutor addTutor(Tutor tutor){
        ContentValues values  = new ContentValues();
        values.put(SQLite_OpenHelper.COLUMN_FIRST_NAME,tutor.getFirstName());
        values.put(SQLite_OpenHelper.COLUMN_LAST_NAME,tutor.getLastName());
        values.put(SQLite_OpenHelper.COLUMN_EMAIL, tutor.getEmail());
        values.put(SQLite_OpenHelper.COLUMN_PHONE, tutor.getPhone());
        values.put(SQLite_OpenHelper.COLUMN_CAMPUS, tutor.getCampus());
        values.put(SQLite_OpenHelper.COLUMN_SUBJECT, tutor.getSubject());
        //values.put(SQLite_OpenHelper.COLUMN_SKILL, tutor.getSkill());
        long insertid = database.insert(SQLite_OpenHelper.TABLE_TUTORS,null,values);
        tutor.setTutorId(insertid);
        return tutor;
    }

    // Getting single Tutor
    public Tutor getTutor(long id) {

        Cursor cursor = database.query(SQLite_OpenHelper.TABLE_TUTORS,allColumns,
                SQLite_OpenHelper.COLUMN_ID + "=?",new String[]{String.valueOf(id)},
                null,null, null,null);
        if (cursor != null)
            cursor.moveToFirst();

        Tutor tutorRec = new Tutor(
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getInt(5),
                cursor.getInt(6));
        // return Tutor
        return tutorRec;
    }

    // Getting all Tutors
    public List<Tutor> getAllTutors() {

        Cursor cursor = database.query(SQLite_OpenHelper.TABLE_TUTORS,allColumns,null,
                null,null, null, null);

        List<Tutor> tutors = new ArrayList<>();
        if(cursor.getCount() > 0){
            while(cursor.moveToNext()){
                Tutor tutor = new Tutor();
                tutor.setTutorId(cursor.getLong(cursor.getColumnIndex(SQLite_OpenHelper.COLUMN_ID)));
                tutor.setFirstName(cursor.getString(cursor.getColumnIndex(SQLite_OpenHelper.COLUMN_FIRST_NAME)));
                tutor.setLastName(cursor.getString(cursor.getColumnIndex(SQLite_OpenHelper.COLUMN_LAST_NAME)));
                tutor.setEmail(cursor.getString(cursor.getColumnIndex(SQLite_OpenHelper.COLUMN_EMAIL)));
                tutor.setPhone(cursor.getString(cursor.getColumnIndex(SQLite_OpenHelper.COLUMN_PHONE)));
                tutor.setCampus(cursor.getInt(cursor.getColumnIndex(SQLite_OpenHelper.COLUMN_CAMPUS)));
                tutor.setSubject(cursor.getInt(cursor.getColumnIndex(SQLite_OpenHelper.COLUMN_SUBJECT)));
               // tutor.setSkill(cursor.getString(cursor.getColumnIndex(SQLite_OpenHelper.COLUMN_CAMPUS)));
                tutors.add(tutor);
            }
        }
        // return All Tutors
        return tutors;
    }

    // Updating Tutor
    public int updateTutor(Tutor tutor) {

        ContentValues values = new ContentValues();
        values.put(SQLite_OpenHelper.COLUMN_FIRST_NAME, tutor.getFirstName());
        values.put(SQLite_OpenHelper.COLUMN_LAST_NAME, tutor.getLastName());
        values.put(SQLite_OpenHelper.COLUMN_EMAIL, tutor.getEmail());
        values.put(SQLite_OpenHelper.COLUMN_PHONE, tutor.getPhone());
        values.put(SQLite_OpenHelper.COLUMN_CAMPUS, tutor.getCampus());
        values.put(SQLite_OpenHelper.COLUMN_SUBJECT, tutor.getSubject());
        //values.put(SQLite_OpenHelper.COLUMN_SKILL, tutor.getSkill());

        // updating row
        return database.update(SQLite_OpenHelper.TABLE_TUTORS, values,
                SQLite_OpenHelper.COLUMN_ID + "=?",new String[]
                        { String.valueOf(tutor.getTutorId())});
    }

    // Deleting Tutor
    public void removeEmployee(Tutor tutor) {

        database.delete(SQLite_OpenHelper.TABLE_TUTORS,
                SQLite_OpenHelper.COLUMN_ID + "=" + tutor.getTutorId(),
                null);
    }
}
