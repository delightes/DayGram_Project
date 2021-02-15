/* DB 세팅 5 - 리스트 뷰에 사용할 어댑터 정의 */
package com.example.daygram_diary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private ArrayList<InfoClass> InfoArr;
    private ViewHolder holder;

    public CustomAdapter(Context c, ArrayList<InfoClass> array) {
        mInflater = LayoutInflater.from(c);
        InfoArr = array;
    }

    @Override
    public int getCount() {
        return InfoArr.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //리스트뷰에 넣을 아이템 뷰 convertview로 가져오기!
        View v = convertView;

        if (v == null) { //처음 convertview로 가져오면
            holder = new ViewHolder();
            v = mInflater.inflate(R.layout.listview_item, null); //inflate (view 객체화)를 최소화하여 매번 아이디를 가져와 속도저하 되는 것을 막기 위해

            holder.list_container = (RelativeLayout) v.findViewById(R.id.list_container);
            holder.listitem = (ImageView) v.findViewById(R.id.bg_view);
            holder.linear_1 = (LinearLayout) v.findViewById(R.id.linear_1);
            holder.linear_2 = (LinearLayout) v.findViewById(R.id.linear_2);
            holder.day = (TextView)v.findViewById(R.id.list_day_item);
            holder.date = (TextView)v.findViewById(R.id.list_date_item); //딱 한번만 xml 리소스에 접근해서 다 가져옴
            holder.content = (TextView)v.findViewById(R.id.list_content_item);

            v.setTag(holder);//그리고 set Tag해줘서 다음엔 재사용 가능하게
        }
        else { //이미 가져온 적 있으면
            holder = (ViewHolder)v.getTag(); //재사용하려고 getTag해줌.
        }

        //InfoClass를 생성하여 각 뷰의 포지션에 맞는 데이터를 가져옴
        InfoClass info = InfoArr.get(position);

        //리스트뷰의 아이템에 맞는 String 값을 입력
        holder.day.setText(info.day);
        holder.date.setText(info.date);
        holder.content.setText(info.content);

        return v;
    }

    //ArrayList Getter And Setter
    public void setArrayList(ArrayList<InfoClass> arrays) {
        this.InfoArr = arrays;
    }

    public ArrayList<InfoClass> getArrayList(){
        return InfoArr;
    }


    /**
     * ViewHolder Class 생성
     */
    private class ViewHolder {
        LinearLayout linear_1;
        LinearLayout linear_2;
        RelativeLayout list_container;
        TextView date;
        TextView day;
        TextView content;
        ImageView listitem;
    }
}
