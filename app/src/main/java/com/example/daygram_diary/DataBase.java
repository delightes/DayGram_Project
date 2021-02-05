package com.example.daygram_diary;

import android.provider.BaseColumns;

public final class DataBase {

    public static final class CreateDB implements BaseColumns {
        public static final String DATE = "date";
        public static final String CONTENT = "content";
        public static final String TABLE_NAME = "diaryTbl";

        // 테이블 생성
        public static final String _CREATE0 = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
                +DATE+" TEXT NOT NULL, "
                +CONTENT + " TEXT NOT NULL);";
    }
}
