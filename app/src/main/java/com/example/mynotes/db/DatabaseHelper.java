package com.example.mynotes.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.mynotes.pojo.Note;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Notes.db";
    public static final String TABLE_NAME = "notes";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "NOTE_TEXT";
    public static final String COL_3 = "DATE";
    public static final int DB_VERSION = 2;

    SQLiteDatabase db;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DB_VERSION);
        db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME
                + "(ID INTEGER PRIMARY KEY AUTOINCREMENT,NOTE_TEXT TEXT,DATE TEXT)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String noteText, String date){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_2,noteText);
        values.put(COL_3,date);
        long result = db.insert(TABLE_NAME,null, values);
        return (result != -1);
    }

    public boolean insertDeletedNote(Note deletedNote){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_1, deletedNote.getId());
        values.put(COL_2, deletedNote.getNoteText());
        values.put(COL_3, deletedNote.getDate());
        long result = db.insert(TABLE_NAME, null, values);
        return (result != -1);
    }

    public Cursor getAllData(){
        db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM "+ TABLE_NAME, null);
    }

    public boolean updateData(String id,String noteText, String date){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_1,id);
        values.put(COL_2,noteText);
        values.put(COL_3,date);
        db.update(TABLE_NAME,values,"ID = ?", new String[] {id});
        return true;
    }

    public Integer deleteRecord(String id){
        db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID= ?", new String[] {id});
    }

}
