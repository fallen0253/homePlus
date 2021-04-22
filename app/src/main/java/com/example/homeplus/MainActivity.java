package com.example.homeplus;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    /*상현 2021-04-18 회원가입 요청코드*/
    public static final int REQUEST_CODE_JOINMEBER = 101;
    /*상현 2021-04-18 로그인 요청코드*/
    public static final int REQUEST_CODE_HOMEPLUS = 102;
    Button btnJoinMember, btnHomePlus;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnJoinMember=findViewById(R.id.btnJoinMember);
        btnHomePlus=findViewById(R.id.btnHomePlus);
        /*상현 2021-04-18 회원가입 액티비티 호출 */
        btnJoinMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),joinMember.class);
                startActivityForResult(intent, REQUEST_CODE_JOINMEBER);
            }
        });
        /*상현 2021-04-18 홈플러스 메인 액티비티 호출*/
        btnHomePlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),homePlusMain.class);
                startActivityForResult(intent,REQUEST_CODE_HOMEPLUS);
            }
        });
    }
    /*상현 2021-04-18 액티비티 응답 구별 메서드*/
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
    // [START on_start_check_user]
   /* @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            reload();
        }else{
            Toast.makeText(getApplicationContext(),"현재 접속중이 아닙니다.",Toast.LENGTH_LONG).show();
        }
    }
    private void reload() { }

    private void updateUI(FirebaseUser user) {

    }*/
}