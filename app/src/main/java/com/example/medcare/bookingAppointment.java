package com.example.medcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.TimeZone;

public class bookingAppointment extends AppCompatActivity {
    private DatePicker datePicker;
    private TimePicker timePicker;
    private Button button;
    private TextView textView;
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_appointment);

        datePicker= findViewById(R.id.date_of_booking);
        timePicker= findViewById(R.id.time_of_booking);
        button= findViewById(R.id.confirm_button);
        textView= findViewById(R.id.heading2);

        Bundle extras= getIntent().getExtras();
        String docID="";
        if(extras!= null){
            docID= extras.getString("docID");
            Log.w("USER CODE:",""+docID);
        }
        FirebaseDatabase.getInstance().getReference("Doctors").child(docID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String doctor_name="Dr. "+ snapshot.child("fullName").getValue().toString();
                textView.setText(doctor_name);
                textView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        Calendar calendar= Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("UTC+5:30"));
        year= calendar.get(Calendar.YEAR);
        month= calendar.get(Calendar.MONTH);
        day= calendar.get(Calendar.DAY_OF_MONTH);
        hour= calendar.get(Calendar.HOUR);
        minute= calendar.get(Calendar.MINUTE);


        datePicker.init(year, month - 1, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                bookingAppointment.this.year= year;
                bookingAppointment.this.month= monthOfYear +1;
                bookingAppointment.this.day= dayOfMonth;
            }
        });

        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                bookingAppointment.this.hour= hourOfDay;
                bookingAppointment.this.minute= minute;
            }
        });


        String finalDocID = docID;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date_of_booking=day+"-"+month+"-"+year;
                String time_of_booking= String.format("%02d:%02d", hour, minute);
                String userID= FirebaseAuth.getInstance().getCurrentUser().getUid();

                DatabaseReference bookingRef= FirebaseDatabase.getInstance().getReference("Bookings");
                String bookingID= bookingRef.push().getKey();
                Booking newBooking= new Booking(bookingID,userID, finalDocID,date_of_booking,time_of_booking);
                bookingRef.child(bookingID).setValue(newBooking).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(bookingAppointment.this, "Applied for Booking..", Toast.LENGTH_SHORT).show();
                            Toast.makeText(bookingAppointment.this, "Now kindly wait patiently for the doctor's confirmation", Toast.LENGTH_LONG).show();
                            Intent intent= new Intent(bookingAppointment.this, viewDoctors.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(bookingAppointment.this, "Error!! Try again!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }
        });

    }
}