package com.example.homeplus;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;

import com.google.android.material.bottomnavigation.BottomNavigationMenu;
import com.google.android.material.tabs.TabLayout;

public class homePlusMain extends AppCompatActivity {
    /*상현 2021-04-18 프래그먼트 거실, 부엌, 방 선언*/
    FragmentLivingRoom fragmentLivingRoom;
    FragmentKitchen fragmentKitchen;
    FragmentRoom fragmentRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_plus_main);

        /*상현 2021-04-18 액션바 선언*/
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.show();
        actionBar.setTitle("Home Plus");
        /*상현 2021-04-18 프래그먼트 객체 생성 선언*/
        fragmentLivingRoom = new FragmentLivingRoom();
        fragmentKitchen = new FragmentKitchen();
        fragmentRoom = new FragmentRoom();
        /*상현 2021-04-18 프래그먼트 매니저 참조*/
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragmentLivingRoom).commit();
        /*상현 2021-04-21 위젯 탭 연결 후 탭추가 */
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText("거실"));
        tabs.addTab(tabs.newTab().setText("부엌"));
        tabs.addTab(tabs.newTab().setText("방"));

        /*상현 2021-04-21 탭을 선택 시 발생할 이벤트*/
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
    /*상현 2021-04-21 옵션메뉴 생성 메뉴 인플레이트*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.homeplusmenu,menu);
        return true;
    }
}
