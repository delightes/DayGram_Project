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
    boolean first = true;

    private static final String TAG = "TestDataBase";
    private DbOpenHelper mDbOpenHelper;
    private Cursor mCursor;
    private InfoClass mInfoClass;
    private ArrayList<InfoClass> mInfoArray;
    private CustomAdapter mAdapter;

    String sort = "date"; //지워도 될듯

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
        setLayout();

        // 2. 데이터 베이스 생성 및 오픈
        mDbOpenHelper = new DbOpenHelper(this);
        try {
            mDbOpenHelper.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        /* 너무 보기 힘들어서 일단 초기화 - 없애야함
        mDbOpenHelper.deleteAll();*/

        // 임의 값 입력력
        mDbOpenHelper.insertColumn("날짜", "테스트");

        // ArrayList 초기화
        mInfoArray = new ArrayList<InfoClass>();

        doWhileCursorToArray();

        // 3. 값이 제대로 입력되었는지 확인하기 위해 로그 찍기
        for (InfoClass i : mInfoArray) {
                Log.d(TAG, "ID = " + i._id);
                Log.d(TAG, "date= " + i.date);
                Log.d(TAG, "Content= " + i.content);
        }

        // 리스트뷰에 사용할 어댑터 초기화 (파라메터 Context, ArrayList<InfoClass>)
        mAdapter = new CustomAdapter(this, mInfoArray);
        mListView.setAdapter(mAdapter);

        // 4. 리스트뷰의 아이템을 길게 눌렀을 경우 삭제하기 위해 리스너 오버라이딩
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i(TAG, "position = " + position);

                //리스트뷰의 포지션은 0부터 시작하므로 1을 더함
                boolean result = mDbOpenHelper.deleteColumn(position + 1);
                Log.i(TAG, "result = " + result);

                if (result) {
                    //정상적인 포지션을 가져왔을 경우 ArrayList의 포지션과 일치하는 index 정보를 삭제
                    mInfoArray.remove(position);

                    //어댑터에 ArrayList를 다시 세팅 후 값이 변경됬다고 어댑터에 알림
                    mAdapter.setArrayList(mInfoArray);
                    mAdapter.notifyDataSetChanged();
                } else {
                    //잘못된 포지션을 가져왔을 경우 다시 확인 요청
                    Toast.makeText(second.this, "인덱스가 이상합니다!", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
    }

    // 5. do While 구문으로 커서 내용을 -> InfoClass 에 입력 -> 이를 ArrayList 에 추가 !
    private void doWhileCursorToArray() {

        mCursor = null;
        //-DB에 있는 모든 컬럼을 커서에 가져옴
        mCursor = mDbOpenHelper.selectColumns();
        //컬럼의 갯수 확인
        Log.i(TAG, "Count = " + mCursor.getCount());

        while (mCursor.moveToNext()) {
            //-커서 내용을 InfoClass 에 입력
            mInfoClass = new InfoClass(
                    mCursor.getInt(mCursor.getColumnIndex("_id")),
                    mCursor.getString(mCursor.getColumnIndex("date")),
                    mCursor.getString(mCursor.getColumnIndex("content"))
            );
            //-입력된 InfoClass 값을 InfoArray에 추가
            mInfoArray.add(mInfoClass);
        }
        //Cursor 닫기
        mCursor.close();
    }

    /*
     * 레이아웃 세팅하는 메소드
     */
    private ListView mListView;

    private void setLayout() {
        mListView = (ListView) findViewById(R.id.text_list);
    }

    //액티비티가 종료 될 때 디비를 닫아준다
    @Override
    protected void onDestroy() {
        mDbOpenHelper.close();
        super.onDestroy();
    }

    public void onClick(View view) {
        Intent intent = new Intent(getApplicationContext(), write.class);
        startActivity(intent);

        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }

}
        //여기서부터 다시 수정
        /* 2. DB에 생성된 일기 관리할 데이터 리스트 생성.
        final ArrayList<String> diaries = new ArrayList<String> ();
        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.test_list_item, diaries);
        final ListView list = (ListView) findViewById(R.id.text_list);
        list.setAdapter(adapter);


        // 3. 실제 데이터 베이스 생성 및 표시
        if(first == false) //처음 열리는 게 아니라면
        {
            mDbOpenHelper = new DbOpenHelper(this);
            mDbOpenHelper.open();
            mDbOpenHelper.create();

            // 3. 조회
            showDatabase(sort); //?

        }
        else //처음 열리면 (first == true)
        {
            mDbOpenHelper = new DbOpenHelper(this);
            mDbOpenHelper.open();
            mDbOpenHelper.create();

            first = false;
        }
    }

    public String setTextLength(String text, int length){
        if(text.length()<length){
            int gap = length - text.length();
            for (int i=0; i<gap; i++){
                text = text + " ";
            }
        }
        return text;
    }

    public void showDatabase(String sort){
        Cursor iCursor = mDbOpenHelper.sortColumn(sort);
        Log.d("showDatabase", "DB Size: " + iCursor.getCount());
        arrayData.clear();
        arrayIndex.clear();
        while(iCursor.moveToNext()){
            String tempIndex = iCursor.getString(iCursor.getColumnIndex("_id"));
            String tempDate = iCursor.getString(iCursor.getColumnIndex("date"));
            tempDate = setTextLength(tempDate,10);
            String tempContent = iCursor.getString(iCursor.getColumnIndex("content"));
            tempContent = setTextLength(tempContent,10);

            String Result = tempIndex + tempDate + tempContent;
            arrayData.add(Result);
            arrayIndex.add(tempIndex);
        }
         */ //여기까진 내비둬야함