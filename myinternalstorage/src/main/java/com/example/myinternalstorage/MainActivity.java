package com.example.myinternalstorage;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText content;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        content = findViewById(R.id.content);
        btn = findViewById(R.id.btn);
        btn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        String memo = content.getText().toString(); // 작성한 메모
        // 메모 작성하고 저장버튼 눌렀을 때
            // 파일 저장
            // 1. 파일 객체 만들어서 디렉토리 생성, 파일 생성
            // 2. FileWriter(outputStream) 내부저장장치 경로에 저장
            String dirPath = getFilesDir() + "/myTest";
            File dir = new File(dirPath);
            if(!dir.exists())
                dir.mkdir();

            FileWriter writer;
            try {
                writer = new FileWriter(dir + "/test.txt", true);
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
}