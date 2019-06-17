package com.example.mypreference;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText inputId, inputPw;
    Button btnLogin;
    CheckBox checkBox;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inputId = findViewById(R.id.inputId);
        inputPw = findViewById(R.id.inputPw);
        btnLogin = findViewById(R.id.btnLogin);
        checkBox = findViewById(R.id.checkBox);

        // SharedPreference 에 저장된 값 불러오기
        pref = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String id = pref.getString("id", "");
        boolean checkValue = pref.getBoolean("check", false);
        // 불러온 값 화면에 보여주기
        inputId.setText(id);
        checkBox.setChecked(checkValue);

        // 버튼 클릭하면 새로운 id 값, check 값 SharedPrefernce에 저장하기
        btnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = pref.edit();
                if(checkBox.isChecked()){
                    // 새로운 id 값 저장
                    editor.putString("id", inputId.getText().toString());
                    editor.putBoolean("check", true);
                    Toast.makeText(getApplicationContext(), "SharedPreference에 저장되었습니다.", Toast.LENGTH_SHORT).show();
                }
                else {
                    // id에 공백 넣어주기
                    editor.putString("id","");
                    editor.putBoolean("check", false);
                }
                editor.commit();
            }
        });
    }
}
