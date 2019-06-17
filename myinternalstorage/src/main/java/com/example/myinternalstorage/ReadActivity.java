package com.example.myinternalstorage;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.io.FileReader;
import java.io.IOException;

public class ReadActivity extends AppCompatActivity {
    TextView fileResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);

        fileResult = findViewById(R.id.fileResult);

        String fileName = getFilesDir() + "/myTest/test.txt";
        try {
            FileReader reader = new FileReader(fileName);
            StringBuffer buffer = new StringBuffer();
            int c;
            buffer.append(fileName + "\n");
            while((c = reader.read()) != -1){
                buffer.append((char)c);
            }
            fileResult.setText(buffer.toString());
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
