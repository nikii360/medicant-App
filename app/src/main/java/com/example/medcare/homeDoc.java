package com.example.medcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class homeDoc extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private CardView bookingList, appointmentList, chatBox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_doc);
        bottomNavigationView= findViewById(R.id.bottomNavigatorView1);
        bottomNavigationView.setSelectedItemId(R.id.myHome);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.myHome:
                        return true;
                    case R.id.myProfile: startActivity(new Intent(homeDoc.this,myProfile.class));
                        getIntent().putExtra("userCode",1);
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        bookingList= findViewById(R.id.bookingList);
        appointmentList= findViewById(R.id.appointmentList);
        chatBox= findViewById(R.id.chatBox);

        bookingList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(homeDoc.this,viewBookings.class));
            }
        });
        appointmentList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(homeDoc.this,appointmentSchedule.class));
            }
        });

        chatBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(homeDoc.this, com.example.medcare.chatBox.class);
                intent.putExtra("userCode",1);
                startActivity(intent);
            }
        });


    }

}