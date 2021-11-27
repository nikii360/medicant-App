package com.example.medcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button doctorBtn, userBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        doctorBtn= findViewById(R.id.doctor);
        userBtn=findViewById(R.id.patient);

        doctorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int value=1;
                Intent intent= new Intent(MainActivity.this,loginActivity.class);
                intent.putExtra("userCode",value);
                startActivity(intent);
            }
        });
        userBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int value=2;
                Intent intent= new Intent(MainActivity.this,loginActivity.class);
                intent.putExtra("userCode",value);
                startActivity(intent);
            }
        });
    }
}