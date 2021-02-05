package com.example.daygram_diary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbOpenHelper {

    private static final String DATABASE_NAME = "diary.db";
    public static SQLiteDatabase mDB;
    private DatabaseHelper mDBHelper;
    private Context mCtx;

    private class DatabaseHelper extends SQLiteOpenHelper {

        // 생성자
        public DatabaseHelper(Context c) {
            super(c, DATABASE_NAME, null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DataBase.CreateDB._CREATE0); // 테이블 생성
        }

        // 버전 업그레이드 시 사용. 이전 버전을 지우고 새 버전을 생성.
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " +DataBase.CreateDB.TABLE_NAME);
            onCreate(db);
        }
    }
/*
    public DbOpenHelper(Context c) {
        this.mCtx = c;
    }

    // 데이터베이스를 열어서 사용할 수 있도록 함
    public DbOpenHelper open() throws SQLException {
        mDBHelper = new DatabaseHelper(mCtx, DATABASE_NAME, null, 1);
        mDB = mDBHelper.getWritableDatabase(); // 데이터베이스를 읽고 쓸 수 있도록 함
        return this;
    }
    
    public void create() {
        mDBHelper.onCreate(mDB);
    }

    // 데이터베이스를 닫음
    public void close() {
        mDB.close();
    }

    public long insertColumn (String date, String content) {
        ContentValues v = new ContentValues();
        v.put(DataBase.CreateDB.DATE, date);
        v.put(DataBase.CreateDB.CONTENT, content);
        return mDB.insert(DataBase.CreateDB.TABLE_NAME, null, v);
    }

    public Cursor selectColumns() {
        return mDB.query(DataBase.CreateDB.TABLE_NAME, null, null, null, null, null, null);
    }*/
}
