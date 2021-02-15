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
        Log.d(TAG, "=============================새로 시작했는데요. 데이터 베이스는 이제 열었어요==========================");
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
            Log.d(TAG, "(체크) 아이디는 " + CheckCursor.getInt(0));
            Log.d(TAG, "(체크) 요일은" + CheckCursor.getString(3));
            Log.d(TAG, "(체크) 내용은 " + CheckCursor.getString(2));
        }
        Log.d(TAG, "===============================이제 끝이에요===============================");

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
                Log.i(TAG, "조회해야 할 아이디="+position);

                Intent intent = new Intent(getApplicationContext(), update.class);
                /* putExtra의 첫 값은 식별 태그, 뒤에는 다음 화면에 넘길 값 */ //수정해야함
                intent.putExtra("Id","보낼값");
                intent.putExtra("Date", "보낼값");
                intent.putExtra("Content", "보낼 값");
                intent.putExtra("Time", "보낼 값");
                startActivity(intent);

                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                // 첫번째 인자 : 새로 불리는 액티비티의 효과
                // 두번째 인자 : 현재 액티비티의 효과
            }
        });
        /* 꾹 눌렀을 때 삭제 해줌
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i(TAG, "지울 위치 position = " + position);

                //리스트뷰의 포지션은 0부터 시작하므로 1을 더함
                boolean result = mDbOpenHelper.deleteColumn(position + 1);
                Log.i(TAG, "지운 결과 result = " + result);

                if (result) {
                    //정상적인 포지션을 가져왔을 경우 ArrayList의 포지션과 일치하는 index 정보를 삭제
                    mInfoArray.remove(position);

                    //어댑터에 ArrayList를 다시 세팅 후 값이 변경됬다고 어댑터에 알림
                    mAdapter.setArrayList(mInfoArray);
                    mAdapter.notifyDataSetChanged();
                }
                else {
                    //잘못된 포지션을 가져왔을 경우 다시 확인 요청
                    Toast.makeText(second.this, "인덱스가 이상합니다!", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
    */
    }

    // 5. do While 구문으로 커서 내용을 -> InfoClass 에 입력 -> 이를 ArrayList 에 추가 !
    private void doWhileCursorToArray () {

        mCursor = null;
        //-DB에 있는 모든 행을 커서에 가져옴
        mCursor = mDbOpenHelper.selectColumns();
        //컬럼의 갯수 확인
        Log.i(TAG, "Count = " + mCursor.getCount());

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
}