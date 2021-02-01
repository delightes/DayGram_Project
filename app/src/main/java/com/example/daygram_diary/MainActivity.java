package com.example.daygram_diary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    ConstraintLayout start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start = (ConstraintLayout) findViewById(R.id.app_start);
    }


    public void onClick(View view) {
        Intent intent = new Intent(getApplicationContext(), second.class);
        startActivity(intent);

        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        // 첫번째 인자 : 새로 불리는 액티비티의 효과
        // 두번째 인자 : 현재 액티비티의 효과
    }
}