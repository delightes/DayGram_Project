package com.example.daygram_diary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class update extends AppCompatActivity {


    //실제로 사용
    private DbOpenHelper mDbOpenHelper;
    TextView today_update;
    EditText my_write_update;
    ImageButton done_btn;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        intent = getIntent();

        /* UI */
        today_update = (TextView) findViewById(R.id.today); //가져온 날짜
        my_write_update = (EditText) findViewById(R.id.my_write); //사용자가 작성할 일기
        done_btn = (ImageButton) findViewById(R.id.done_btn);

        //1. today_update set
        today_update.setText(intent.getStringExtra("Time"));

        //2. content set
        my_write_update.setText(intent.getStringExtra("Content"));

        mDbOpenHelper = new DbOpenHelper(this);
        mDbOpenHelper.open(); //DB 열고
    }

    //업데이트
    public void onClick(View view) {
        switch (view.getId()) {
            /* 완료 버튼 눌리면 */
            case R.id.done_btn:
                Log.d("test","확인********"+intent.getStringExtra("Time")+"**********"+intent.getStringExtra("Content"));
                mDbOpenHelper.updateColumn(intent.getIntExtra("Id",10000), intent.getStringExtra("Day"), intent.getStringExtra("Date"), my_write_update.getText().toString().trim(), intent.getStringExtra("Time"));
                break;
        }
        /* UI - 이전 화면으로 전환 */
        Intent intent = new Intent(getApplicationContext(), second.class);
        startActivity(intent);

        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        mDbOpenHelper.close();
    }
}