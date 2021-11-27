package com.example.medcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class viewDoctors extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DatabaseReference reference;
    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_doctors);

        bottomNavigationView= findViewById(R.id.bottomNavigatorView1);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.myHome:startActivity(new Intent(viewDoctors.this,homeUser.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.myProfile: Intent intent=new Intent(viewDoctors.this,myProfile.class);
                        intent.putExtra("userCode",2);
                        startActivity(intent);

                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });



        final ArrayList<Doctor> doctors= new ArrayList<>();
        recyclerView= findViewById(R.id.doctorList);
        DoctorsRecViewAdapter adapter= new DoctorsRecViewAdapter(doctors,this);

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));


        DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("Doctors");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                doctors.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    String ID= dataSnapshot.getKey().toString();
                    String name= dataSnapshot.child("fullName").getValue().toString();

                    String mail= dataSnapshot.child("mailId").getValue().toString();
                    String gender= dataSnapshot.child("gender").getValue().toString();
                    String dob= dataSnapshot.child("dob").getValue().toString();
                    String region= dataSnapshot.child("region").getValue().toString();
                    String address= dataSnapshot.child("address").getValue().toString();
                    String contactNo= dataSnapshot.child("contactNo").getValue().toString();
                    String specialties= dataSnapshot.child("specialties").getValue().toString();
                    specialties= specialties.substring(0,specialties.length()-2);
                    String aq= dataSnapshot.child("academicQualification").getValue().toString();
                    String exp= dataSnapshot.child("experience").getValue().toString();

                    Doctor doctor= new Doctor(ID,name,mail,gender,dob,region, address,contactNo,specialties,aq,exp);
                    FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.child("region").getValue().toString().equals(doctor.getRegion())){
                                doctors.add(doctor);
                            }adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }
}