package com.example.medcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class homeUser extends AppCompatActivity {
    private CardView diseaseTracker, viewDoctors, bookingList, chatBox;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_user);

        diseaseTracker= findViewById(R.id.diseaseTracker);
        viewDoctors= findViewById(R.id.viewDoctors);
        bookingList= findViewById(R.id.appointmentList);
        chatBox= findViewById(R.id.chatBox);

        diseaseTracker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(homeUser.this,diseaseDiagnosis.class);
                startActivity(intent);
            }
        });

        viewDoctors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(homeUser.this, viewDoctors.class);
                startActivity(intent);
            }
        });
        bookingList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(homeUser.this, bookingHistory.class);
                startActivity(intent);
            }
        });
        chatBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(homeUser.this, com.example.medcare.chatBox.class);
                intent.putExtra("userCode",2);
                startActivity(intent);
            }
        });

        bottomNavigationView= findViewById(R.id.bottomNavigatorView1);
        bottomNavigationView.setSelectedItemId(R.id.myHome);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.myHome:
                        return true;
                    case R.id.myProfile: Intent intent= new Intent(homeUser.this,myProfile.class);
                        intent.putExtra("userCode",2);
                        startActivity(intent);
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

    }
}