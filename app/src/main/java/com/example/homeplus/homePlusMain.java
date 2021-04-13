package com.example.homeplus;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;

import com.google.android.material.tabs.TabLayout;

public class homePlusMain extends AppCompatActivity {
    Toolbar toolbar;
    FragmentLivingRoom fragmentLivingRoom;
    FragmentKitchen fragmentKitchen;
    FragmentRoom fragmentRoom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_plus_main);
        toolbar=findViewById(R.id.toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);

        fragmentLivingRoom = new FragmentLivingRoom();
        fragmentKitchen = new FragmentKitchen();
        fragmentRoom = new FragmentRoom();

        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragmentLivingRoom).commit();

        TabLayout tabs = findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText("거실"));
        tabs.addTab(tabs.newTab().setText("부엌"));
        tabs.addTab(tabs.newTab().setText("방"));

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                Log.d("homePlusMain","선택된 탭 : " + position);
                Fragment selected = null;
                if(position==0){
                    selected = fragmentLivingRoom;
                }else if(position==1){
                    selected = fragmentKitchen;
                }else if(position==2){
                    selected = fragmentRoom;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.container, selected).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

}
