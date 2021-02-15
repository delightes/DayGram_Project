package com.example.daygram_diary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class second extends AppCompatActivity {

    TextView second_today;

    private static final String TAG = "TestDataBase";
    private DbOpenHelper mDbOpenHelper;
    private Cursor mCursor;
    private InfoClass mInfoClass;
    private ArrayList<InfoClass> mInfoArray;
    private CustomAdapter mAdapter;
    private Cursor CheckCursor;
    private Cursor PutCursor;

    /*
     * 레이아웃 세팅하는 메소드
     */
    private ListView mListView;

    private void setLayout() {
        mListView = (ListView) findViewById(R.id.text_list); //리스트뷰 변수에 담아오기
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);


        second_today = (TextView) findViewById(R.id.second_today);

        // 1. 현재 날짜
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("| MMM | yyyy |", new Locale("en", "US"));
        String nowDay = simpleDateFormat.format(date);
        second_today.setText(nowDay);

        setLayout(); //리스트뷰 실제로 담음.

        // 2. 데이터 베이스 생성 및 오픈
        Log.d(TAG, "****************************데이터 베이스 열음****************************");
        mDbOpenHelper = new DbOpenHelper(this);
        try {
            mDbOpenHelper.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //무슨 값 있는지 확인 - 없애야 함
        CheckCursor = mDbOpenHelper.selectColumns();
        while (CheckCursor.moveToNext()) {
            //-커서 내용 로그에 출력
            Log.d(TAG, ">>>>>>>>>> 아이디 : " + CheckCursor.getInt(0)+" <<<<<<<<<<");
            Log.d(TAG, ">>>>>>>>>> 날짜 : " + CheckCursor.getString(4)+" <<<<<<<<<<");
            Log.d(TAG, ">>>>>>>>>> 내용 :" + CheckCursor.getString(3)+" <<<<<<<<<<");
        }
        Log.d(TAG, "===============================데이터베이스 닫음===============================");

        // ArrayList 초기화
        mInfoArray = new ArrayList<InfoClass>();

        //mInfoArray에 cursor 값 추가
        doWhileCursorToArray();

        // 리스트뷰에 사용할 어댑터 초기화 (파라메터 Context, ArrayList<InfoClass>)
        mAdapter = new CustomAdapter(this, mInfoArray);
        mListView.setAdapter(mAdapter);

        mAdapter.setArrayList(mInfoArray); //추가한거임 없애도 됨
        mAdapter.notifyDataSetChanged(); //추가한거임 없애도 됨

        // 4. 리스트뷰의 아이템을 눌렀을 경우 조회하기 위해 리스너 오버라이딩
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i(TAG, "*************조회 아이디***************="+position+1);

                Intent intent = new Intent(getApplicationContext(), update.class);
                intent.putExtra("Id", position+1);
                intent.putExtra("Content", Select_content(PutCursor,position+1));
                intent.putExtra("Day",Select_day(PutCursor,position+1));
                intent.putExtra("Date", Select_date(PutCursor,position+1));
                intent.putExtra("Content", Select_content(PutCursor,position+1));
                intent.putExtra("Time", Select_time(PutCursor,position+1));
                startActivity(intent);

                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                mDbOpenHelper.close();
            }
        });
    }

    // 5. do While 구문으로 커서 내용을 -> InfoClass 에 입력 -> 이를 ArrayList 에 추가 !
    private void doWhileCursorToArray () {

        mCursor = null;
        //-DB에 있는 모든 행을 커서에 가져옴
        mCursor = mDbOpenHelper.selectColumns();

        while (mCursor.moveToNext()) {
            //-커서 내용을 InfoClass 에 입력
            mInfoClass = new InfoClass(
                    mCursor.getInt(mCursor.getColumnIndex("_id")),
                    mCursor.getString(mCursor.getColumnIndex("day")),
                    mCursor.getString(mCursor.getColumnIndex("date")),
                    mCursor.getString(mCursor.getColumnIndex("content")),
                    mCursor.getString(mCursor.getColumnIndex("time"))
                    );
            //-입력된 InfoClass 값을 InfoArray에 추가
            mInfoArray.add(mInfoClass);
        }
        //Cursor 닫기
        mCursor.close();
    }

    //액티비티가 종료 될 때 디비를 닫아준다
    @Override
    protected void onDestroy () {
        mDbOpenHelper.close();
        super.onDestroy();
    }

    public void onClick (View view){
        Intent intent = new Intent(getApplicationContext(), write.class);
        startActivity(intent);

        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }

    public String Select_day (Cursor cursor, int id)
    {
        cursor = mDbOpenHelper.getColumn(id);
        return cursor.getString(1);
    }

    public String Select_date (Cursor cursor, int id)
    {
        cursor = mDbOpenHelper.getColumn(id);
        return cursor.getString(2);
    }

    public String Select_content (Cursor cursor, int id)
    {
        cursor = mDbOpenHelper.getColumn(id);
        return cursor.getString(3);
    }

    public String Select_time (Cursor cursor, int id)
    {
        cursor = mDbOpenHelper.getColumn(id);
        return cursor.getString(4);
    }

}