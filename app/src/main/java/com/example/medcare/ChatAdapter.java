package com.example.medcare;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Chat> chats= new ArrayList<>();

    public ChatAdapter(Context context, ArrayList<Chat> messages) {
        this.context = context;
        this.chats = messages;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.chat_profile_item,parent,false);
        ViewHolder holder= new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        switch(chats.get(position).getUserCode()){
            case 1: FirebaseDatabase.getInstance().getReference("Users").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                        if(dataSnapshot.getKey().toString().equals(chats.get(position).getProfileID())){
                            holder.textView.setText(dataSnapshot.child("fullName").getValue().toString());
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            break;

            case 2: FirebaseDatabase.getInstance().getReference("Doctors").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                        if(dataSnapshot.getKey().toString().equals(chats.get(position).getProfileID())){
                            holder.textView.setText("Dr. " +dataSnapshot.child("fullName").getValue().toString());
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
                break;
        }
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(context,newMessage.class);
                intent.putExtra("receiverName",holder.textView.getText().toString());
                intent.putExtra("receiverID",chats.get(position).getProfileID());
                intent.putExtra("userCode",chats.get(position).getUserCode());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView= itemView.findViewById(R.id.messageMan);
        }
    }
}
