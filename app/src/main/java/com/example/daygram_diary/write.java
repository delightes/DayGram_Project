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

    TextView today;
    EditText my_write;
    ImageButton done_btn;

    String nowDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        today = (TextView) findViewById(R.id.today); // 현재 날짜
        my_write = (EditText) findViewById(R.id.my_write); // 사용자가 작성한 일기
        done_btn = (ImageButton) findViewById(R.id.done_btn);

        // 현재 날짜
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE / MMM dd / yyyy", new Locale("en", "US"));
        nowDay = simpleDateFormat.format(date);
        today.setText(nowDay);
    }

    public void onClick(View view) {
        // 이전 화면으로 전환
        Intent intent = new Intent(getApplicationContext(), second.class);
        startActivity(intent);

        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
}