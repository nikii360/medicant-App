package com.example.medcare;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class DoctorsRecViewAdapter extends RecyclerView.Adapter<DoctorsRecViewAdapter.ViewHolder> {

    private ArrayList<Doctor> doctors= new ArrayList<>();
    private Context context;


    public DoctorsRecViewAdapter(ArrayList<Doctor> doctors,Context context) {
        this.doctors=doctors;
        this.context= context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.doctor_list_item,parent,false);
        ViewHolder holder= new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView1.setText(doctors.get(position).getFullName());
        holder.textView2.setText(doctors.get(position).getSpecialties());
    }

    @Override
    public int getItemCount() {
        return doctors.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private TextView textView1, textView2;
        private ImageView imageView;
        private Button button1, button2;
        private Context mContext;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mContext= itemView.getContext();
            textView1= itemView.findViewById(R.id.doctorName);
            textView2= itemView.findViewById(R.id.doctorSpecialty);
            button1= itemView.findViewById(R.id.bookBtn);
            button2=itemView.findViewById(R.id.chatBtn);


            button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Doctor doctor= doctors.get(getAdapterPosition());
                    String id= doctor.getID();
                    Intent intent= new Intent(mContext,bookingAppointment.class);
                    intent.putExtra("docID",id);
                    mContext.startActivity(intent);
                }
            });
            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Doctor doctor= doctors.get(getAdapterPosition());
                    String id= doctor.getID();
                    String name= doctor.getFullName();
                    Intent intent= new Intent(mContext,newMessage.class);
                    intent.putExtra("receiverID",id);
                    intent.putExtra("receiverName",name);
                    mContext.startActivity(intent);
                }
            });
        }
    }
}
