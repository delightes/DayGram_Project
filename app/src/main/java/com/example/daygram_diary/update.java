package com.example.daygram_diary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Calendar;

public class update extends AppCompatActivity {


    //실제로 사용
    Calendar cal;
    TextView today_update;
    EditText my_write_update;
    ImageButton done_btn_update;


    /* DB - 변수 선언 */
    long ID = 0;
    String date_db;
    String day_db;
    EditText content;
    String content_db;
    private DbOpenHelper mDbOpenHelper;

    /* UI */

    String nowDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        /* UI */
        today_update = (TextView) findViewById(R.id.today_update); //가져온 날짜
        my_write_update = (EditText) findViewById(R.id.my_write_update); //사용자가 작성할 일기
        done_btn_update = (ImageButton) findViewById(R.id.done_btn_update);

        //1. today_update set
        cal = Calendar.getInstance();
        String year = Integer.toString(cal.get(Calendar.YEAR));

    }
}

        /*
        //누른 거 데이터베이스 행만 그대로 가져오기
        mDbOpenHelper.insertColumn(day_db, date_db, content_db);

        cal = Calendar.getInstance(); //캘린더에서 가져오기
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE / MMM dd / yyyy", new Locale("en", "US"));
        nowDay = simpleDateFormat.format(date);
        today.setText(nowDay);

        /* DB
        // 1. 액티비티에 있는 값 가져오기
        done_btn  = (ImageButton) findViewById(R.id.done_btn_update);
        content = (EditText) findViewById(R.id.my_write_update);

        // 2. 실제 데이터 베이스 오픈
        mDbOpenHelper = new DbOpenHelper(this);
        mDbOpenHelper.open();

    }

    public void onClick(View view) {
        switch (view.getId()) {
             완료 버튼 눌리면
           // case R.id.done_btn_update:
                /*Cursor iCursor = mDbOpenHelper.selectColumns();
                while(iCursor.moveToNext())
                {
                    String tempDay = iCursor.getString(iCursor.getColumnIndex("day"));
                    String tempDate = iCursor.getString(iCursor.getColumnIndex("date"));
                    String tempContent = iCursor.getString(iCursor.getColumnIndex("content"));

                int date = cal.get(Calendar.DAY_OF_WEEK);
                day_db = day_print(date); //요일 원하는 형태로 바꿈
                date_db = " "+cal.get(Calendar.DATE)+" ";
                content_db = content.getText().toString().trim(); //형변환
                mDbOpenHelper.insertColumn(day_db, date_db, content_db);
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
                    }
        }
        /* UI - 이전 화면으로 전환
        Intent intent = new Intent(getApplicationContext(), second.class);
        startActivity(intent);

        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
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
*/