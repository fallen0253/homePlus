package com.example.homeplus;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class joinMember extends AppCompatActivity {
    Button btnBack;
    FirebaseAuth mAuth;
    EditText edtEmail, edtPw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_member);
        btnBack=findViewById(R.id.btnBack);
        mAuth = FirebaseAuth.getInstance();
        edtEmail=findViewById(R.id.edtEmail);
        edtPw=findViewById(R.id.edtPw);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               createAccount();
            }
        });
    }
    //2021-04-22 상현 신규 계정 만들기
    private void createAccount() {
        //사용자가 입력하는 email, password를 가져온다.
        String email = edtEmail.getText().toString().trim();
        String password = edtPw.getText().toString().trim();
        //2021-04-22 상현 email과 password가 비었는지 확인한다.
        if(TextUtils.isEmpty(email)){
            Toast.makeText(getApplicationContext(),"Email을 입력해주세요.",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(getApplicationContext(),"Password를 입력해 주세요.",Toast.LENGTH_LONG).show();
            return;
        }
        //2021-04-22 상현 계정생성
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                            intent.putExtra("BACK", "로그인 성공!!");
                            setResult(RESULT_OK,intent);
                            finish();
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(joinMember.this);
                            builder.setTitle("에러유형");
                            builder.setMessage("-이미 등록된 이메일-\n-암호 최소 6자리 이상-\n-서버에러-");
                            builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            AlertDialog dialog = builder.create();
                            dialog.show();
                            updateUI(null);
                        }
                    }
                });
        //2021-04-22 상현 계정생성 종료
    }
    private void updateUI(FirebaseUser user){

    }
}