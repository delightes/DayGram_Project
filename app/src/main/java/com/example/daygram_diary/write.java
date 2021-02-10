package com.example.daygram_diary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class write extends AppCompatActivity {

    /* DB - 변수 선언 */
    long ID = 0;
    String date_db;
    EditText content;
    String content_db;
    private DbOpenHelper mDbOpenHelper;

    /* UI */
    TextView today;
    EditText my_write;
    ImageButton done_btn;
    String nowDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        /* UI */
        today = (TextView) findViewById(R.id.today); // 현재 날짜
        my_write = (EditText) findViewById(R.id.my_write); // 사용자가 작성한 일기
        done_btn = (ImageButton) findViewById(R.id.done_btn);
        //done_btn.setOnClickListener((View.OnClickListener) this);

        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE / MMM dd / yyyy", new Locale("en", "US"));
        nowDay = simpleDateFormat.format(date);
        today.setText(nowDay);

        /* DB */
        // 1. 액티비티에 있는 값 가져오기
        done_btn  = (ImageButton) findViewById(R.id.done_btn);
        content = (EditText) findViewById(R.id.my_write);

        // 2. 실제 데이터 베이스 오픈
        mDbOpenHelper = new DbOpenHelper(this);
        mDbOpenHelper.open();

    }

    public void onClick(View view) {
        switch (view.getId()) {
            /* 완료 버튼 눌리면 */
            case R.id.done_btn:
                Cursor iCursor = mDbOpenHelper.selectColumns();
                while(iCursor.moveToNext())
                {
                    String tempDate = iCursor.getString(iCursor.getColumnIndex("date"));
                    String tempContent = iCursor.getString(iCursor.getColumnIndex("content"));

                    date_db = nowDay; //형변환
                    content_db = content.getText().toString().trim(); //형변환
                    mDbOpenHelper.insertColumn(date_db, content_db);
                    break;

                    /*
                    if (tempDate == nowDay && tempContent != content.getText().toString()) // 같은 날에 만들어지면 UPDATE
                    {
                        content_db = content.getText().toString().trim();
                        mDbOpenHelper.updateColumn(ID, nowDay, content_db);
                    }
                    else //다른 날에 만들어지면 INSERT
                    {
                        ID++;
                        date_db = nowDay; //형변환
                        content_db = content.getText().toString(); //형변환
                        mDbOpenHelper.insertColumn(date_db, content_db);
                        break;
                    }*/
                }


        }
        /* UI - 이전 화면으로 전환 */
        Intent intent = new Intent(getApplicationContext(), second.class);
        startActivity(intent);

        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
}