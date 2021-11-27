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

public class appointmentSchedule extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private RecyclerView recyclerView;
    private DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_schedule);

        bottomNavigationView= findViewById(R.id.bottomNavigatorView1);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.myHome:startActivity(new Intent(appointmentSchedule.this,homeDoc.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.myProfile: Intent intent= new Intent(appointmentSchedule.this,myProfile.class);
                        intent.putExtra("userCode",1);
                        startActivity(intent);
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        final ArrayList<Booking> bookings= new ArrayList<>();
        recyclerView= findViewById(R.id.appointment_list);
        AppointmentRecViewAdapter adapter= new AppointmentRecViewAdapter(this,bookings);

//        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));


        reference= FirebaseDatabase.getInstance().getReference().child("Bookings");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bookings.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    String ID= dataSnapshot.getKey().toString();
                    String user_ID= dataSnapshot.child("user_id").getValue().toString();

                    String doc_ID= dataSnapshot.child("doc_id").getValue().toString();
                    String date= dataSnapshot.child("date").getValue().toString();
                    String time= dataSnapshot.child("time").getValue().toString();
                    String status= dataSnapshot.child("booking_status").getValue().toString();

                    Booking booking= new Booking(ID,user_ID,doc_ID,date,time,status);
                    if(booking.getBooking_status().equals("active")&& FirebaseAuth.getInstance().getCurrentUser().getUid().toString().equals(booking.getDoc_id())){
                        Log.v("FOUND APPOINTMENT: ",booking.getBooking_id());
                        bookings.add(booking);}
                }adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}