package com.example.chap9;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    boolean externalStorageReadable, externalStorageWriteable;
    boolean fileReadPermission, fileWritePermission;
    EditText content;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        content = findViewById(R.id.content);
        btn = findViewById(R.id.btn);
        btn.setOnClickListener(this);

        // 외부저장장치가 장착되어 있는가
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            externalStorageReadable = externalStorageWriteable = true;
            Log.i("externalStorageState", "외부저장장치 읽기/쓰기 가능");
        } else if (state.equals(Environment.MEDIA_MOUNTED_READ_ONLY)) {
            externalStorageReadable = true;
            externalStorageWriteable = false;
            Log.i("externalStorageState", "외부저장장치 읽기 가능");
        } else {
            externalStorageReadable = externalStorageWriteable = false;
            Log.i("externalStorageState", "외부저장장치 읽기/쓰기 거부됨");
        }

        // 퍼미션 체크
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            fileReadPermission = true;
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            fileWritePermission = true;
        }
        // 없으면 요청하기
        if (!fileReadPermission || !fileWritePermission) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.SEND_SMS,
                    Manifest.permission.CALL_PHONE,
                    Manifest.permission.INTERNET
            }, 200);

        }
    }

    // 요청한 결과 처리
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 200 && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                fileReadPermission = true;
            if (grantResults[1] == PackageManager.PERMISSION_GRANTED)
                fileWritePermission = true;
            if (fileReadPermission && fileWritePermission)
                Toast.makeText(this, "외부저장장치 접근 허용", Toast.LENGTH_SHORT).show();
            // SMS
            if (grantResults[2] == PackageManager.PERMISSION_GRANTED)
                Log.i("permission", "SMS 권한 허용");
            // CALL
            if (grantResults[3] == PackageManager.PERMISSION_GRANTED)
                Log.i("permission", "CALL 권한 허용");
            // INTERNET
            if (grantResults[4] == PackageManager.PERMISSION_GRANTED)
                Log.i("permission", "INTERNET 권한 허용");
        }
    }

    @Override
    public void onClick(View v) {
        String memo = content.getText().toString(); // 작성한 메모
        // 메모 작성하고 저장버튼 눌렀을 때
        if(fileReadPermission && fileWritePermission){
            // 파일 저장

            // 1. 파일 객체 만들어서 디렉토리 생성, 파일 생성
            // 2. FileWriter(outputStream) 외부저장장치 경로(Environment)에 저장
            String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/myApp";
            File dir = new File(dirPath);
            if(!dir.exists())
                dir.mkdir();

            FileWriter writer;
            try {
                writer = new FileWriter(dir + "/myFile.txt", true);
                writer.write(memo);
                writer.close();
                Toast.makeText(this, "파일 저장 완료", Toast.LENGTH_SHORT).show();
                // ReadActivity 실행
                Intent intent = new Intent(this, ReadActivity.class);
                startActivity(intent);
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "파일 저장 실패", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(this, "권한이 부여되지 않음", Toast.LENGTH_SHORT).show();
        }
    }
}