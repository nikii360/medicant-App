package com.example.medcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class myProfile extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    private TextView textView1, textView2, textView3, textView4, textView5,textView6;
    private Button logOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        Bundle extras= getIntent().getExtras();
        final int userCode= extras.getInt("userCode");

        textView1= findViewById(R.id.user_name);
        textView2= findViewById(R.id.user_gender);
        textView3= findViewById(R.id.user_age);
        textView4= findViewById(R.id.user_mail);
        textView5= findViewById(R.id.user_contactNo);
        textView6 = findViewById(R.id.user_addy);

        switch (userCode){
            case 1: FirebaseDatabase.getInstance().getReference("Doctors").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    textView1.setText(textView1.getText().toString()+snapshot.child("fullName").getValue().toString());
                    textView2.setText(textView2.getText().toString()+snapshot.child("gender").getValue().toString());
                    textView3.setText(textView3.getText().toString()+snapshot.child("dob").getValue().toString());
                    textView4.setText(textView4.getText().toString()+snapshot.child("mailId").getValue().toString());
                    textView5.setText(textView5.getText().toString()+snapshot.child("contactNo").getValue().toString());
                    textView6.setText(textView6.getText().toString()+snapshot.child("address").getValue().toString());

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            break;

            case 2: FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    textView1.setText(textView1.getText().toString()+snapshot.child("fullName").getValue().toString());
                    textView2.setText(textView2.getText().toString()+snapshot.child("gender").getValue().toString());
                    textView3.setText(textView3.getText().toString()+snapshot.child("dob").getValue().toString());
                    textView4.setText(textView4.getText().toString()+snapshot.child("mailId").getValue().toString());
                    textView5.setText(textView5.getText().toString()+snapshot.child("contactNo").getValue().toString());
                    textView6.setText(textView6.getText().toString()+snapshot.child("address").getValue().toString());

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            break;
        }

        logOut= findViewById(R.id.logOut);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(myProfile.this,MainActivity.class));
            }
        });


        bottomNavigationView= findViewById(R.id.bottomNavigatorView3);
        bottomNavigationView.setSelectedItemId(R.id.myProfile);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId()){
                    case R.id.myHome: if(userCode==2){
                        startActivity(new Intent(myProfile.this,homeUser.class));

                        overridePendingTransition(0,0);
                        return true;}
                        startActivity(new Intent(myProfile.this, homeDoc.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.myProfile:
                        return true;
                }
                return false;
            }
        });

    }
}