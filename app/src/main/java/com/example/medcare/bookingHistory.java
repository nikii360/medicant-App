package com.example.medcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class bookingHistory extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private ImageButton button1, button2,button3, button4, button5, button6;
    private RecyclerView recyclerView1, recyclerView2, recyclerView3;
    private DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_history);

        bottomNavigationView= findViewById(R.id.bottomNavigatorView1);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.myHome:
                        Intent intent=new Intent(bookingHistory.this, homeUser.class);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.myProfile:
                        Intent intent1 = new Intent(bookingHistory.this, myProfile.class);
                        intent1.putExtra("userCode", 2);
                        startActivity(intent1);
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });

        recyclerView1= findViewById(R.id.active_bookingList);
        recyclerView2= findViewById(R.id.waitlisted_bookingList);
        recyclerView3= findViewById(R.id.cancelled_bookingList);
//        recyclerView3.setNestedScrollingEnabled(false);

        final ArrayList<Booking> activeBookings= new ArrayList<>();
        final ArrayList<Booking> waitlistedBookings= new ArrayList<>();
        final ArrayList<Booking> cancelledBookings= new ArrayList<>();
        HistoryRecViewAdapter adapter1= new HistoryRecViewAdapter(this,activeBookings);
        HistoryRecViewAdapter adapter2= new HistoryRecViewAdapter(this,waitlistedBookings);
        HistoryRecViewAdapter adapter3= new HistoryRecViewAdapter(this,cancelledBookings);

//        recyclerView1.setHasFixedSize(true);
//        recyclerView2.setHasFixedSize(true);
//        recyclerView3.setHasFixedSize(true);
        recyclerView1.setAdapter(adapter1);
        recyclerView1.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerView2.setAdapter(adapter2);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerView3.setAdapter(adapter3);
        recyclerView3.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        reference= FirebaseDatabase.getInstance().getReference("Bookings");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                activeBookings.clear();
                waitlistedBookings.clear();
                cancelledBookings.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    String ID = dataSnapshot.getKey().toString();
                    String user_ID = dataSnapshot.child("user_id").getValue().toString();

                    String doc_ID = dataSnapshot.child("doc_id").getValue().toString();
                    String date = dataSnapshot.child("date").getValue().toString();
                    String time = dataSnapshot.child("time").getValue().toString();
                    String status = dataSnapshot.child("booking_status").getValue().toString();

                    Booking booking = new Booking(ID, user_ID, doc_ID, date, time, status);
                    if(booking.getUser_id().toString().equals(FirebaseAuth.getInstance().getCurrentUser().getUid().toString())){
                        switch(booking.getBooking_status()){
                            case "active": activeBookings.add(booking);
                            break;
                            case "waitlist": waitlistedBookings.add(booking);
                            break;
                            case "cancelled": cancelledBookings.add(booking);
                            break;
                        }
                    }
                }adapter1.notifyDataSetChanged();
                adapter2.notifyDataSetChanged();
                adapter3.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        button1= findViewById(R.id.active_expandLessBtn);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(recyclerView1.getVisibility()== View.VISIBLE){
                    recyclerView1.setVisibility(View.GONE);
                }
            }
        });

        button2= findViewById(R.id.active_expandMoreBtn);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(recyclerView1.getVisibility()== View.GONE){
                    recyclerView1.setVisibility(View.VISIBLE);
                    adapter1.notifyDataSetChanged();
                }
            }
        });

        button3= findViewById(R.id.waitlist_expandMoreBtn);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(recyclerView2.getVisibility()== View.GONE){
                    recyclerView2.setVisibility(View.VISIBLE);
                    adapter2.notifyDataSetChanged();
                }
            }
        });

        button4= findViewById(R.id.waitlist_expandLessBtn);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(recyclerView2.getVisibility()== View.VISIBLE){
                    recyclerView2.setVisibility(View.GONE);
                }
            }
        });

        button5= findViewById(R.id.cancelled_expandMoreBtn);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(recyclerView3.getVisibility()== View.GONE){
                    recyclerView3.setVisibility(View.VISIBLE);
                    adapter3.notifyDataSetChanged();
                }
            }
        });

        button6= findViewById(R.id.cancelled_expandLessBtn);
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(recyclerView3.getVisibility()== View.VISIBLE){
                    recyclerView3.setVisibility(View.GONE);
                }
            }
        });
    }
}