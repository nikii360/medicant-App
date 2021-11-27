package com.example.medcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class newMessage extends AppCompatActivity {
    private DatabaseReference reference;
    private ImageButton imageButton;
    private EditText editText;
    private RecyclerView recyclerView;
    private MessageAdapter messageAdapter;
    private List<Message> messages;
    int userCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_message);


        imageButton= findViewById(R.id.messageBtn);
        editText= findViewById(R.id.messageTxt);
        recyclerView= findViewById(R.id.chatList);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayout= new LinearLayoutManager(this);
        linearLayout.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras= getIntent().getExtras();
        String receiverID= extras.getString("receiverID");
        userCode= extras.getInt("userCode");

        String titleName= extras.getString("receiverName");


        setTitle(titleName);
        String userID= FirebaseAuth.getInstance().getCurrentUser().getUid().toString();

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = editText.getText().toString();
                if (message.equals("")) {
                    Toast.makeText(newMessage.this, "You cannot send an empty Message", Toast.LENGTH_SHORT).show();
                    return;
                }

                addMessage(message, receiverID, userID);
                editText.setText("");

            }
        });
        readMessage(userID,receiverID);


    }
    private void addMessage(String message,String receiverID, String userID){
        reference= FirebaseDatabase.getInstance().getReference("Chats");
        String messageID= reference.push().getKey().toString();
        HashMap<String, Object> hashMap= new HashMap<>();
        hashMap.put("message",message);
        hashMap.put("receiver",receiverID);
        hashMap.put("sender",userID);
        reference.child(messageID).setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(newMessage.this, "Message sent successfully...", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(newMessage.this, "Error!! Try Again!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void readMessage(String userID, String receiverID){
       messages= new ArrayList<>();
        reference= FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messages.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Message message= dataSnapshot.getValue(Message.class);
                    if((message.getSender().equals(userID) && message.getReceiver().equals(receiverID))|| (message.getReceiver().equals(userID) && message.getSender().equals(receiverID))){
                        messages.add(message);
                    }
                    messageAdapter= new MessageAdapter(newMessage.this,messages);
                    recyclerView.setAdapter(messageAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id= item.getItemId();
        Bundle extras= getIntent().getExtras();
        userCode= extras.getInt("userCode");
        if(id==android.R.id.home){

            Intent intent= new Intent(newMessage.this,chatBox.class);
            intent.putExtra("userCode",userCode);
            Log.v("USER CODE: ", ""+userCode);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}