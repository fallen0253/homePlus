package com.example.homeplus;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.VibrationAttributes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


public class FragmentLivingRoom extends Fragment {
    ImageView ivLight;
    String str;
    homePlusMain hpm=new homePlusMain();
    public FragmentLivingRoom() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
    /* 상현 2021-04-21 거실 */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /*상현 2021-04-26 XML 자바 연동*/
        CheckBox cbDate,cbWeather,cbDust,cbUserInput;
        View fraglivingroom = inflater.inflate(R.layout.fragment_living_room,container,false);
        Switch swLED = fraglivingroom.findViewById(R.id.swLED);
        TextView tvContent = fraglivingroom.findViewById(R.id.tvContent);
        cbDate = fraglivingroom.findViewById(R.id.cbDate);
        cbWeather = fraglivingroom.findViewById(R.id.cbWeather);
        cbDust = fraglivingroom.findViewById(R.id.cbDust);
        cbUserInput = fraglivingroom.findViewById(R.id.cbUserInput);
        EditText edtInput = fraglivingroom.findViewById(R.id.edtInput);
        ImageView ivShutDown = fraglivingroom.findViewById(R.id.ivShutDown);
        ivLight=fraglivingroom.findViewById(R.id.ivLight);

        ivLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
                str="11";
                hpm.sendData(str);
            }
        });

        return fraglivingroom;

    }
}