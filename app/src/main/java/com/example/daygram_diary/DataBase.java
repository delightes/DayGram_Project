/* DB 세팅 1 */
package com.example.daygram_diary;

import android.provider.BaseColumns;

public final class DataBase {

    public static final class CreateDB implements BaseColumns {
        public static final String DAY = "day";
        public static final String DATE = "date";
        public static final String CONTENT = "content";
        public static final String TIME = "time";
        public static final String _TABLENAME = "diaryTbl";

        //1. 내부 데이터 베이스와 테이블 생성
        public static final String _CREATE =
                "CREATE TABLE IF NOT EXISTS " + _TABLENAME + "(" //CREATE 이전까지는 다 지워야함
                +_ID+" integer primary key autoincrement, " //다이어리 구분 아이디 - 정수형 자동 증가
                +DAY+" TEXT NOT NULL, " //요일 문자형
                +DATE+" TEXT NOT NULL, " //날짜 문자형
                +CONTENT + " TEXT NOT NULL,"
                +TIME +" TEXT NOT NULL);"; //내용 문자형
    }
}
