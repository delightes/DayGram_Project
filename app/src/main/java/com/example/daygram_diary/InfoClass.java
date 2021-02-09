/* DB 세팅 4 - 데이터 클래스 코드 - Getter & Setter */
package com.example.daygram_diary;

public class InfoClass {
    public int _id;
    public String date;
    public String content;

    //생성자
    public InfoClass(){}

    /**
     * 실질적으로 값을 입력할 때 사용되는 생성자(getter and setter)
     * @param _id       테이블 아이디
     * @param date      날짜
     * @param content   일기 내용
     */
    public InfoClass(int _id, String date, String content) {
        this._id = _id;
        this.date = date;
        this.content = content;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}