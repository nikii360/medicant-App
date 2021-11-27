package com.example.medcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class loginActivity extends AppCompatActivity {
    private TextView signUp, fgtPwd;
    private Button loginBtn;
    private TextInputEditText loginMail, loginPwd;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        int userCode=1;
        Bundle extras= getIntent().getExtras();
        if(extras!= null){
            userCode= extras.getInt("userCode");
            Log.w("USER CODE:",""+userCode);
        }
        mAuth= FirebaseAuth.getInstance();

        signUp= findViewById(R.id.signUp);
        fgtPwd= findViewById(R.id.fgtPwd);
        loginBtn= findViewById(R.id.loginBtn);
        loginMail= findViewById(R.id.loginMail);
        loginPwd= findViewById(R.id.loginPwd);

        int finalUserCode = userCode;
        fgtPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(loginActivity.this, forgotPassword.class);
                intent.putExtra("userCode",finalUserCode);
                startActivity(intent);
            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(finalUserCode ==1) {
                    Intent intent = new Intent(loginActivity.this, registerDoc.class);

                    startActivity(intent);
                }
                if(finalUserCode==2){
                    Intent intent = new Intent(loginActivity.this, registerUser.class);
                    startActivity(intent);
                }
            }
        });
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser(finalUserCode);
            }
        });


    }
    private void loginUser(int value){
        String mailId= loginMail.getText().toString();
        String Password= loginPwd.getText().toString();
        if(mailId.isEmpty()){
            loginMail.setError("Email ID is missing");
            loginMail.requestFocus();
            return;
        }
        if(Password.isEmpty()){
            loginPwd.setError("Password is missing");
            loginPwd.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(mailId,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Log.w("User Code",":"+value);
                    String ID= mAuth.getCurrentUser().getUid().toString();
                    switch(value){
                        case 1: FirebaseDatabase.getInstance().getReference().child("Doctors").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                                    if(dataSnapshot.getKey().equals(ID)){
                                        Intent intent1 = new Intent(loginActivity.this, homeDoc.class);

                                        startActivity(intent1);

                                    }
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                        break;

                        case 2: FirebaseDatabase.getInstance().getReference().child("Users").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                                    if(dataSnapshot.getKey().equals(ID)){
                                        Intent intent1 = new Intent(loginActivity.this, homeUser.class);

                                        startActivity(intent1);
                                    }
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                            break;

                    }
                }else{
                    Toast.makeText(loginActivity.this, "Incorrect Credentials! Try again!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}