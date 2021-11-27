package com.example.medcare;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class BookingRecViewAdapter extends RecyclerView.Adapter<BookingRecViewAdapter.ViewHolder>{
    private ArrayList<Booking> bookings= new ArrayList<>();
    private Context context;

    public BookingRecViewAdapter(Context context, ArrayList<Booking> bookings) {
        this.context= context;
        this.bookings= bookings;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.booking_list_item,parent,false);
        ViewHolder holder= new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FirebaseDatabase.getInstance().getReference("Users").child(bookings.get(position).getUser_id()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                holder.textView1.setText(snapshot.child("fullName").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        holder.textView2.setText(bookings.get(position).getDate());
        holder.textView3.setText(bookings.get(position).getTime());
        holder.acceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap hashMap= new HashMap();
                hashMap.put("booking_status","active");
                FirebaseDatabase.getInstance().getReference("Bookings").child(bookings.get(holder.getAdapterPosition()).getBooking_id()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()){
//                            bookings.remove(bookings.get(getAdapterPosition()));
//                            notifyItemRemoved(holder.getAdapterPosition());
//                            notifyItemRangeChanged(holder.getAdapterPosition(),bookings.size());
                            Toast.makeText(context, "Booking Confirmed...", Toast.LENGTH_SHORT).show();
//                            cardView.setVisibility(View.GONE);

                        }else{
                            Toast.makeText(context, "Error!! Try Again!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }

        });
        holder.denyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap hashMap= new HashMap();
                hashMap.put("booking_status","cancelled");
                FirebaseDatabase.getInstance().getReference("Bookings").child(bookings.get(holder.getAdapterPosition()).getBooking_id()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
//                            bookings.remove(bookings.get(position));
//                            notifyItemRemoved(holder.getAdapterPosition());
//                            notifyItemRangeChanged(getAdapterPosition(),bookings.size());
                            Toast.makeText(context, "Booking Cancelled...", Toast.LENGTH_SHORT).show();
//                                cardView.setVisibility(View.GONE);


                        } else {
                            Toast.makeText(context, "Error! Try Again!", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookings.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView textView1, textView2, textView3;
        private ImageButton acceptBtn, denyBtn;
        private Context mContext;
        private CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView1= itemView.findViewById(R.id.item_heading);
            textView2= itemView.findViewById(R.id.booking_item_date);
            textView3= itemView.findViewById(R.id.booking_item_time);
            acceptBtn= itemView.findViewById(R.id.booking_activeBtn);
            denyBtn= itemView.findViewById(R.id.booking_cancelBtn);
            mContext= itemView.getContext();
            cardView= itemView.findViewById(R.id.bookingItem);


        }
    }
}
