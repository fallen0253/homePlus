package com.example.homeplus;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_JOINMEBER = 101;
    public static final int REQUEST_CODE_HOMEPLUS = 102;
    Button btnJoinMember, btnHomePlus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnJoinMember=findViewById(R.id.btnJoinMember);
        btnHomePlus=findViewById(R.id.btnHomePlus);
        btnJoinMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),joinMember.class);
                startActivityForResult(intent, REQUEST_CODE_JOINMEBER);
            }
        });
        btnHomePlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),homePlusMain.class);
                startActivityForResult(intent,REQUEST_CODE_HOMEPLUS);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       if(requestCode == REQUEST_CODE_JOINMEBER){
            Toast.makeText(getApplicationContext(),"onActivityResult 메서드 호출됨. 요청 코드 : "
                    + requestCode + ", 결과 코드 : " + resultCode,Toast.LENGTH_SHORT).show();
        if(resultCode == RESULT_OK){
            String BACK = data.getStringExtra("BACK");
            Toast.makeText(getApplicationContext(),"응답으로 전달된 BACK : " + BACK,Toast.LENGTH_SHORT).show();
        }
        }
    }
}