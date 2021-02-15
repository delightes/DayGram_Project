package com.example.daygram_diary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Calendar;

public class write extends AppCompatActivity {

    /* DB - 변수 선언 */
    String date_db;
    String day_db;
    EditText content;
    String content_db;
    private DbOpenHelper mDbOpenHelper;

    /* UI */
    Calendar cal;
    TextView today;
    EditText my_write;
    ImageButton done_btn;
    String nowDay;
    Cursor iCursor;
    int count;
    Cursor jCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        /* UI */
        today = (TextView) findViewById(R.id.today); // 현재 날짜
        my_write = (EditText) findViewById(R.id.my_write); // 사용자가 작성한 일기
        done_btn = (ImageButton) findViewById(R.id.done_btn);


        cal = Calendar.getInstance(); //캘린더에서 가져오기
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
        switch (view.getId())
        {
            /* 완료 버튼 눌리면 */
            case R.id.done_btn:
                Log.d("Test","왜 안되세요ㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠ");
                jCursor = mDbOpenHelper.selectColumns();
                count = jCursor.getCount();
                Log.d("Test","개수 :::::::::::::::::::::::::::::::::::::::::: "+count+" ");

                if (count == 0) //한개도 없으면
                {
                    int date = cal.get(Calendar.DAY_OF_WEEK);
                    day_db = day_print(date); //요일 원하는 형태로 바꿈
                    date_db = " "+cal.get(Calendar.DATE)+" ";
                    content_db = content.getText().toString().trim(); //형변환
                    mDbOpenHelper.insertColumn(day_db, date_db, content_db, nowDay);
                }
                else //하나라도 있으면
                {
                    if(nowDay.equals(mDbOpenHelper.getMatchTime(nowDay).getString(4))) //DB에 오늘 날짜와 똑같은 날짜가 있으면
                    {
                        iCursor = mDbOpenHelper.getMatchTime(nowDay);

                        //업데이트하기
                        mDbOpenHelper.updateColumn(iCursor.getInt(0), iCursor.getString(1),
                                iCursor.getString(2), content.getText().toString().trim(), nowDay);
                    }
                    else //같은 날짜 아니면
                    {
                        int date = cal.get(Calendar.DAY_OF_WEEK);
                        day_db = day_print(date); //요일 원하는 형태로 바꿈
                        date_db = " "+cal.get(Calendar.DATE)+" ";
                        content_db = content.getText().toString().trim(); //형변환
                        mDbOpenHelper.insertColumn(day_db, date_db, content_db, nowDay);
                    }
                }

                break;
        }
        /* UI - 이전 화면으로 전환 */
        Intent intent = new Intent(getApplicationContext(), second.class);
        startActivity(intent);

        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        mDbOpenHelper.close();
    }

    public String day_print(int day)
    {
        String day_return=" ";

        switch (day) {
            case 1:
                day_return = "MON";
                break;
            case 2:
                day_return = "TUE";
                break;
            case 3:
                day_return = "WED";
                break;
            case 4:
                day_return = "THU";
                break;
            case 5:
                day_return = "FRI";
                break;
            case 6:
                day_return = "SAT";
                break;
            case 7:
                day_return = "SUN";
                break;
        }
        return day_return;
    }
}