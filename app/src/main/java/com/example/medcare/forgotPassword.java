package com.example.medcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ValueEventListener;

public class forgotPassword extends AppCompatActivity {
    private TextView textView;
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        textView= findViewById(R.id.ResetPwdMail);
        button= findViewById(R.id.resetPwdBtn);

        Bundle extras= getIntent().getExtras();
        int userCode= extras.getInt("userCode");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(textView.getText().toString().isEmpty()){
                    textView.setError("Email is required!");
                    textView.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(textView.getText().toString()).matches()){
                    textView.setError("Invalid Email ID");
                    textView.requestFocus();
                    return;
                }
                FirebaseAuth.getInstance().sendPasswordResetEmail(textView.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(forgotPassword.this, "Check your email to reset your password...", Toast.LENGTH_SHORT).show();
                            Intent intent= new Intent(forgotPassword.this,loginActivity.class);
                            intent.putExtra("userCode",userCode);
                            startActivity(intent);
                            return;
                        }else{
                            Toast.makeText(forgotPassword.this, "Error!! Try Again!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}