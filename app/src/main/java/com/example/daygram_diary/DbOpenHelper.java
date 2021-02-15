/* DB 세팅 2 - 데이터 베이스 사용할 수 있도록 구문 미리 작성 */
package com.example.daygram_diary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbOpenHelper {

    private static final String DATABASE_NAME = "diary.db";
    private static final int DATABASE_VERSION = 1;
    public static SQLiteDatabase mDB;
    private DatabaseHelper mDBHelper;
    private Context mCtx;

    private class DatabaseHelper extends SQLiteOpenHelper {

        // 생성자
        public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        // 1. onCreate : 최초 DB를 만들 때 한번만 호출
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DataBase.CreateDB._CREATE); // 테이블 생성
        }

        // 2. onUpgrade : 버전 업그레이드 시 사용. 이전 버전을 지우고 새 버전을 생성.
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            //업데이트를 했는데 DB가 존재할 경우 onCreate() 다시 호출
            db.execSQL("DROP TABLE IF EXISTS " +DataBase.CreateDB._TABLENAME);
            onCreate(db);
        }
    }

    //DbOpenHelper 생성자
    public DbOpenHelper(Context c) {
        this.mCtx = c;
    }

    // 데이터베이스를 열어서 실제 사용할 수 있도록 함
    public DbOpenHelper open() throws SQLException {
        mDBHelper = new DatabaseHelper(mCtx, DATABASE_NAME, null, DATABASE_VERSION);
        mDB = mDBHelper.getWritableDatabase(); // 데이터베이스를 읽고 쓸 수 있도록 함
        return this;
    }

    // 데이터 베이스 다 사용해서 닫는 메소드
    public void close() {
        mDB.close();
    }

    // 3. 데이터 삽입 (INSERT 구문)

    /**
     *  데이터베이스에 사용자가 입력한 값을 추가가하는 소드
     * @param day           요일
     * @param date          날짜
     * @param content       일기 내용
     * @param time          시간
     * @return              SQLiteDataBase에 입력한 값을 insert
     */

    public long insertColumn (String day, String date, String content, String time) {
        ContentValues v = new ContentValues();
        v.put(DataBase.CreateDB.DAY, day);
        v.put(DataBase.CreateDB.DATE, date);
        v.put(DataBase.CreateDB.CONTENT, content);
        v.put(DataBase.CreateDB.TIME, time);
        return mDB.insert(DataBase.CreateDB._TABLENAME, null, v);
    }

    // 4. 데이터 조회 (SELECT 구문)

    public Cursor selectColumns() {
        return mDB.query(DataBase.CreateDB._TABLENAME, null, null, null, null, null, null);
    }

    // 5. 데이터 수정 (UPDATE 구문)

    /**
     *  데이터베이스에 사용자가 입력한 값을 추가가하는 소드
     * @param id           데이터베이스 아이디
     * @param day           요일
     * @param date          날짜
     * @param content       일기 내용
     * @param time          시간
     * @return              SQLiteDataBase에 입력한 값을 insert
     */

    public boolean updateColumn(long id, String day, String date, String content, String time){
        ContentValues values = new ContentValues();
        values.put(DataBase.CreateDB.DAY, day);
        values.put(DataBase.CreateDB.DATE, date);
        values.put(DataBase.CreateDB.CONTENT, content);
        values.put(DataBase.CreateDB.TIME, time);

        return mDB.update(DataBase.CreateDB._TABLENAME, values, "_id=" + id, null) > 0;
    }

    // 6. 데이터 삭제 (DELETE 구문) - 해당 아이디만 가져와서 삭제!
    public boolean deleteColumn(long id){
        return mDB.delete(DataBase.CreateDB._TABLENAME, "_id="+id, null) > 0;
    }

    // 7. 데이터 삭제 (DELETE 구문) - 전부 삭제
    public boolean deleteAll(){
        return mDB.delete(DataBase.CreateDB._TABLENAME, null, null) > 0;
    }

    // 8. ID 컬럼 얻어오기
    public Cursor getColumn(int id) {
        Cursor c = mDB.query(DataBase.CreateDB._TABLENAME, null,
                "_id="+id, null, null, null, null);
        //받아온 컬럼이 null이 아니고 0번째가 아닐경우 제일 처음으로 보냄
        if (c != null && c.getCount() != 0)
            c.moveToFirst();
        return c;
    }

    // 9. 시간 값으로 검색하기 (rawQuery)
    public Cursor getMatchTime(String time) {
        Cursor c = mDB.rawQuery( "Select * from "+DataBase.CreateDB._TABLENAME+" where time == " + "'" + time + "'", null);
        //받아온 컬럼이 null이 아니고 0번째가 아닐경우 제일 처음으로 보냄
        if (c != null && c.getCount() != 0)
            c.moveToFirst();
        return c;
    }

 // + sort by column
    public Cursor sortColumn(String sort){
        Cursor c = mDB.rawQuery( "SELECT * FROM"+DataBase.CreateDB._TABLENAME+" ORDER BY " + sort + ";", null);
        return c;
    }
}
