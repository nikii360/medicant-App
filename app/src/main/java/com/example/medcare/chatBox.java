package com.example.medcare;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class chatBox extends AppCompatActivity {
    private RecyclerView profilesList;
    private BottomNavigationView bottomNavigationView;
    int userCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_box);

        Bundle extras= getIntent().getExtras();
        userCode= extras.getInt("userCode");
        Log.v("USER CODE: ", ""+userCode);

        bottomNavigationView= findViewById(R.id.bottomNavigatorView1);
        bottomNavigationView.setSelectedItemId(R.id.myHome);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.myHome:if(userCode==1){
                        startActivity(new Intent(chatBox.this, homeDoc.class));
                    }else{
                        startActivity(new Intent(chatBox.this,homeUser.class));
                    }

                        return true;
                    case R.id.myProfile: Intent intent=new Intent(chatBox.this,myProfile.class);
                        intent.putExtra("userCode", userCode);
                        startActivity(intent);
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        profilesList= findViewById(R.id.profilesList);
        ArrayList<Chat> chats= new ArrayList<>();
        ChatAdapter adapter= new ChatAdapter(this,chats);
        profilesList.setHasFixedSize(true);
        profilesList.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        profilesList.setAdapter(adapter);

        final String currID= FirebaseAuth.getInstance().getCurrentUser().getUid().toString();

        FirebaseDatabase.getInstance().getReference("Chats").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chats.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    if(dataSnapshot.child("sender").getValue().toString().equals(currID)){
                        Chat chat= new Chat(dataSnapshot.child("receiver").getValue().toString(),userCode);
                        boolean is_added= true;
                        for(int i=0;i<chats.size();i++){
                            if(chat.getProfileID().equals(chats.get(i).getProfileID())){
                                is_added=false;
                                break;
                            }
                        }
                        if(is_added){
                            chats.add(chat);
                        }

                    }else if(dataSnapshot.child("receiver").getValue().toString().equals(currID)){
                        Chat chat= new Chat(dataSnapshot.child("sender").getValue().toString(),userCode);
                        boolean is_added= true;
                        for(int i=0;i<chats.size();i++){
                            if(chat.getProfileID().equals(chats.get(i).getProfileID())){
                                is_added=false;
                                break;
                            }
                        }
                        if(is_added){
                            chats.add(chat);
                        }

                    }
                }adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


}