package com.example.medcare;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

public class HistoryRecViewAdapter extends RecyclerView.Adapter<HistoryRecViewAdapter.ViewHolder>{
    private ArrayList<Booking> bookings= new ArrayList<>();
    private Context context;
    public HistoryRecViewAdapter(Context context,ArrayList<Booking> bookings) {
        this.bookings= bookings;
        this.context= context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.bhistory_list_item, parent, false);
        ViewHolder holder= new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FirebaseDatabase.getInstance().getReference("Doctors").child(bookings.get(position).getDoc_id()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                holder.textView1.setText("Dr. "+snapshot.child("fullName").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        holder.textView2.setText(bookings.get(position).getDate());
        holder.textView3.setText(bookings.get(position).getTime());
        holder.denyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference("Bookings").child(bookings.get(holder.getAdapterPosition()).getBooking_id()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
//                            bookings.remove(bookings.get(holder.getAdapterPosition()));
//                            notifyItemRemoved(holder.getAdapterPosition());
//                            notifyItemRangeChanged(holder.getAdapterPosition(),bookings.size());
                            Toast.makeText(context, "Booking deleted successfully", Toast.LENGTH_SHORT).show();
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
        private CardView cardView;
        private TextView textView1,textView2, textView3;
        private Context mContext;
        private ImageButton denyBtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView= itemView.findViewById(R.id.historyItem);
            textView1= itemView.findViewById(R.id.history_item_heading);
            textView2= itemView.findViewById(R.id.history_item_date);
            textView3= itemView.findViewById(R.id.history_item_time);
            denyBtn= itemView.findViewById(R.id.history_cancelBtn);
            mContext= itemView.getContext();


        }
    }
}
