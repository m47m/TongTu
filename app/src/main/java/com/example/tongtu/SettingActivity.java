package com.example.tongtu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.ToggleButton;


public class SettingActivity extends AppCompatActivity {

    private ToggleButton btn_1;
    private ToggleButton btn_2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        btn_1 = (ToggleButton)findViewById(R.id.btn_1);
        btn_2 = (ToggleButton)findViewById(R.id.btn_2);

        btn_1.setChecked(true);
        btn_2.setChecked(false);

        btn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btn_1.isChecked()){
                    Toast.makeText(SettingActivity.this,"开关按钮打开",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(SettingActivity.this,"开关按钮关闭",Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btn_2.isChecked()){
                    Toast.makeText(SettingActivity.this,"开关按钮打开",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(SettingActivity.this,"开关按钮关闭",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void back(View view) {
        finish();
    }
}