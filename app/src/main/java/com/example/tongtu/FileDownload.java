package com.example.tongtu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class FileDownload extends AppCompatActivity {
    private Intent intent;
    private String file_name;
    private String file_size_cloud;
    private String file_time;

    private ImageView img_file_class;
    private TextView text_file_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_download);
        intent = this.getIntent();

        file_name = intent.getStringExtra("file_name");

        img_file_class = (ImageView)findViewById(R.id.img_file_class);
        text_file_name = (TextView)findViewById(R.id.text_file_name);

        text_file_name.setText(file_name);
        img_file_class.setImageResource(intent.getIntExtra("file_class",R.drawable.document_type_new));
    }

    public void back(View view) {
        finish();
    }

    public void download_file(View view) {



    }
}