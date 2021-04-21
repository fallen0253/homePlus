package com.example.homeplus;

import android.graphics.ColorSpace;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class FragmentRoom extends Fragment {


    public FragmentRoom() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /*상현 2021-04-21 뷰 위젯 생성 후 프레그먼트를 인플레이트 한다.*/
        View fragroom = inflater.inflate(R.layout.fragment_room,container,false);
        TextView text1 = fragroom.findViewById(R.id.text1);
        /*상현 2021-04-21 텍스트뷰 선택시 발생할 이벤트 작동 제대로 되나 실험*/
        text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text1.setTextColor(0xAA1e6de0);
            }
        });
        return fragroom;
    }
}