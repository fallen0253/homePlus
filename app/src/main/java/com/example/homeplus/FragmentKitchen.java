package com.example.homeplus;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


public class FragmentKitchen extends Fragment {


    public FragmentKitchen() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    /*상현 2021-04-21 프래그먼트 인플레이트 */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragKitchen = inflater.inflate(R.layout.fragment_kitchen,container,false);
        ImageView ivSecurity = fragKitchen.findViewById(R.id.ivSecurity);

        return fragKitchen;
    }
}