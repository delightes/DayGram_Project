package com.example.daygram_diary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class second extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
    }

    public void onClick(View view) {
        Intent intent = new Intent(getApplicationContext(), write.class);
        startActivity(intent);

        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
}