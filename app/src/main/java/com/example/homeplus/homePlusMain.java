package com.example.homeplus;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationMenu;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static com.example.homeplus.MainActivity.REQUEST_CODE_HOMEPLUS;

public class homePlusMain extends AppCompatActivity {
    /*상현 2021-04-18 프래그먼트 거실, 부엌, 방 선언*/
    FragmentLivingRoom fragmentLivingRoom;
    FragmentKitchen fragmentKitchen;
    FragmentRoom fragmentRoom;
    FirebaseAuth mAuth;

    ImageView ivConnect;

    BluetoothAdapter btAdapter; //클래스
    int paireDeviceCount=0; //블루투스에 연결된 장치 수
    Set<BluetoothDevice> devices; //블루투스 장치
    BluetoothDevice remoteDevice; //내가 사용할 블루투스 장치
    BluetoothSocket bluetoothSocket; //블루투스 통신
    OutputStream outputStream=null;
    InputStream inputStream=null;
    Thread wokerThread=null;
    String strDelimiter="\n";
    char charDelimiter='\n';
    byte readBuffer[];
    int readBufferPosition;
    String str; //아두이노에 전달한 값을 저장하는 변수

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_plus_main);

        /*리나 2021-04-24 블루투스 연결 상태 확인*/
        ivConnect=findViewById(R.id.ivConnect);
        ivConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBluetooth();
            }
        });
        /*리나 2021-04-24 로그인 상태 확인*/
        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser() == null){
            //해당 페이지 종료
            finish();
            //로그인하지 않았다면  main으로 이동
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }

        //유저가 있다면, null이 아니면 계속 진행
        FirebaseUser user = mAuth.getCurrentUser();
        //textViewUserEmail의 내용을 변경해 준다.
        showToast(user.getEmail()+"으로 로그인 성공");

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

    /*리나 2021-04-24 메뉴 클릭 시 동작 (로그아웃, 회원탈퇴)*/
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logOut:
                mAuth.signOut();
                finish();
                startActivity(new Intent(this, MainActivity.class));
                Log.i("Test_result : ", "로그아웃.");
                break;
            case R.id.deleteMember:
                AlertDialog.Builder alert_confirm = new AlertDialog.Builder(homePlusMain.this);
                alert_confirm.setMessage("정말 계정을 삭제 할까요?").setCancelable(false).setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                user.delete()
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                showToast("계정이 삭제 되었습니다.");
                                                finish();
                                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                            }
                                        });
                            }
                        }
                );
                alert_confirm.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        showToast("회원탈퇴를 취소하셨습니다.");
                    }
                });
                alert_confirm.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    /*리나 2021-04-24 토스트메세지*/
    void showToast(String msg) {
        Toast.makeText(homePlusMain.this, msg, Toast.LENGTH_SHORT).show();
    }

    //스마트폰의 블루투스 지원 여부 검사
    void checkBluetooth(){
        btAdapter=BluetoothAdapter.getDefaultAdapter();
        if(btAdapter==null){
            //showToast("블루투스를 지원하지 않는 장치입니다.");
            AlertDialog.Builder builder=new AlertDialog.Builder(homePlusMain.this);
            builder.setTitle("블루투스");
            builder.setMessage("블루투스를 지원하지 않은 폰입니다.\n확인을 누르시면 앱이 종료됩니다.");
            builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            AlertDialog dialog=builder.create();
            dialog.show();
        }else {
            //장치가 블루투스를 지원하는 경우
            if(!btAdapter.isEnabled()){
                Intent intent=new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(intent, 10);
            } else{
                selectDevice();
            }
        }
    }

    //페어링된 장치 목록 출력 및 선택
    void selectDevice(){
        devices=btAdapter.getBondedDevices(); //블루투스 장치 목록 가져옴
        paireDeviceCount=devices.size();
        if(paireDeviceCount==0){
            //연결된 장치가 없음
            showToast("페어링된 장치가 없습니다.");
        }else{
            //연결된 장치가 있을 경우
            AlertDialog.Builder builder=new AlertDialog.Builder(homePlusMain.this);
            builder.setTitle("블루투스 장치 선택");
            List<String> listItems=new ArrayList<String>();
            for(BluetoothDevice device:devices){
                listItems.add((device.getName()));
            }
            listItems.add("취소");
            final CharSequence[] items=listItems.toArray(new CharSequence[listItems.size()]);
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(which==paireDeviceCount){
                        showToast("취소를 선택했습니다.");
                    }else{
                        connectToSelectedDevice(items[which].toString());
                    }
                }
            });
            builder.setCancelable(false); //뒤로가기 버튼 금지
            AlertDialog dlg=builder.create();
            dlg.show();
        }
    }

    //선택한 블루투스 장치와의 연결
    void connectToSelectedDevice(String selectedDeviceName){
        remoteDevice=getDeviceFormBoundedList(selectedDeviceName);
        UUID uuid=UUID.fromString("00001101-0000-1000-8000-00805f9b34fb"); //블루투스 장치 고유번호
        try{
            bluetoothSocket=remoteDevice.createRfcommSocketToServiceRecord(uuid);
            bluetoothSocket.connect();//기기와 연결이 완료
            ivConnect.setImageResource(R.drawable.bluetooth_on);
            outputStream=bluetoothSocket.getOutputStream();
            inputStream=bluetoothSocket.getInputStream();
            beginListenForData();
        }catch (Exception e){
            showToast("소켓 연결이 되지 않았습니다.");
        }
    }

    //데이터 수신 준비 및 처리
    void beginListenForData(){
        final Handler handler=new Handler();
        readBuffer=new byte[1024]; //아두이노에서 받는 수신버퍼 크기
        readBufferPosition=0; //버퍼 내 수신 문자 저장 위치
        //문자열 수신 쓰레드
        wokerThread=new Thread(new Runnable() {
            @Override
            public void run() {
                while(!Thread.currentThread().isInterrupted()){ //쓰레드가 중단된 상태가 아닐 경우
                    try{
                        int byteAvailalbe=inputStream.available(); //수신데이터가 있는지 확인
                        if(byteAvailalbe>0){
                            //아두이노에서 보낸 데이터가 있음
                            byte[] packetBytes=new byte[byteAvailalbe];
                            inputStream.read(packetBytes);
                            for(int i=0; i<byteAvailalbe; i++){
                                byte b=packetBytes[i];
                                if(b==charDelimiter){
                                    byte[] encodeByte=new byte[readBufferPosition];
                                    System.arraycopy(readBuffer, 0, encodeByte, 0, encodeByte.length);
                                    final String data=new String(encodeByte, "US-ASCII");
                                    readBufferPosition=0;
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            //data변수에 수신된 문자열에 대한 처리 작업

                                        }
                                    });
                                }else{
                                    readBuffer[readBufferPosition++]=b;
                                }
                            }
                        }
                    }catch (IOException e){
                        showToast("데이터 수신 중 오류가 발생했습니다.");
                    }
                }
            }
        });
        wokerThread.start();
    }

    //데이터 송신(아두이노로 전송)
    public void sendData(String msg) {
        msg+=strDelimiter;
        try{
            outputStream.write(msg.getBytes()); //문자열 전송
        }catch (Exception e){
            showToast("문자열 전송 도중에 오류가 발생했습니다.");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try{
            wokerThread.interrupt();
            inputStream.close();
            outputStream.close();
            bluetoothSocket.close();
        }catch (Exception e){
            showToast("앱 종료 중 에러 발생");
        }
    }

    //페어링된 블루투스 장치를 이름으로 찾기
    BluetoothDevice getDeviceFormBoundedList(String name){
        BluetoothDevice selectedDevice=null;
        for(BluetoothDevice device : devices){
            if(name.equals(device.getName())){ //대화상자에서 선태한 리스트 이름
                selectedDevice=device;
                break;
            }
        }
        return selectedDevice;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 10:
                if(resultCode==RESULT_OK){
                    selectDevice();
                } else if(resultCode==RESULT_CANCELED){
                    showToast("블루투스 활성화를 취소했습니다.");
                }
                break;
        }
    }
}
