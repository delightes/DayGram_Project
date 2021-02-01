package com.example.daygram_diary;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class write extends AppCompatActivity {

    TextView today;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        today = (TextView) findViewById(R.id.today);

        // 현재 날짜
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE / MMM dd / yyyy", new Locale("en","US"));
        String nowDay = simpleDateFormat.format(date);
        today.setText(nowDay);
    }
}