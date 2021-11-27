package com.example.medcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class registerUser extends AppCompatActivity {
    private TextInputEditText userFname, userLname, userMail, userPwd,userRePwd, userDob,userAddress, userContact;
    private RadioGroup userGender;
    private MaterialAutoCompleteTextView userRegion;
    private DatePickerDialog datePickerDialog;
    private FirebaseAuth mAuth;
    private Button signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth= FirebaseAuth.getInstance();

        userFname= findViewById(R.id.userFname);
        userLname= findViewById(R.id.userLname);
        userMail= findViewById(R.id.userMail);
        userPwd= findViewById(R.id.userPwd);
        userRePwd= findViewById(R.id.userRePwd);
        userDob= findViewById(R.id.userDob);
        userAddress= findViewById(R.id.userAddress);
        userContact = findViewById(R.id.userContact);
        userGender= findViewById(R.id.userGender);
        userRegion= findViewById(R.id.userRegion);

        String States[]= getResources().getStringArray(R.array.regionList);
        ArrayAdapter<String> adapter= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,States);
        userRegion.setAdapter(adapter);

        userDob.setInputType(InputType.TYPE_NULL);
        userDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar= Calendar.getInstance();
                int day= calendar.get(Calendar.DAY_OF_MONTH);
                int month= calendar.get(Calendar.MONTH);
                int year= calendar.get(Calendar.YEAR);
                datePickerDialog= new DatePickerDialog(registerUser.this, R.style.CalenderStyle,new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        userDob.setText(dayOfMonth + " | " + (month + 1) + " | " + year);
                    }
                    },year,month,day);
                datePickerDialog.show();
            }
        });

        final int finalUserCode=2;
        signUp= findViewById(R.id.userRegister);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                registerNewUser(finalUserCode);


            }
        });




    }

    private boolean registerNewUser(int code){
        String firstName= userFname.getText().toString();
        String lastName= userLname.getText().toString();
        String fullName= userFname.getText().toString() +" "+userLname.getText().toString();
        String mailId= userMail.getText().toString();
        String password1= userPwd.getText().toString();
        String password2= userRePwd.getText().toString();
        int selectedId= userGender.getCheckedRadioButtonId();
        RadioButton selectedGender= findViewById(selectedId);
        String gender= selectedGender.getText().toString();
        String region= userRegion.getText().toString();
        String dob= userDob.getText().toString();
        String address= userAddress.getText().toString();
        String contactNo= userContact.getText().toString();

        if(firstName.isEmpty()){
            userFname.setError("Please fill in all the required details");
            userFname.requestFocus();
            return false;
        }
        if(password1.isEmpty()){
            userPwd.setError("Please fill in all the required details");
            userPwd.requestFocus();
            return false;
        }
        if(lastName.isEmpty()){
            userLname.setError("Please fill in all the required details");
            userLname.requestFocus();
            return false;
        }
        if(mailId.isEmpty()){
            userMail.setError("Please fill in all the required details");
            userMail.requestFocus();
            return false;
        }
        if(password2.isEmpty()){
            userRePwd.setError("Please fill in all the required details");
            userRePwd.requestFocus();
            return false;
        }
        if(!password1.equals(password2)){
            userRePwd.setError("Passwords do not match");
            userRePwd.requestFocus();
            return false;
        }
        if(region.isEmpty()){
            userRegion.setError("Please fill in all the required details");
            userRegion.requestFocus();
            return false;
        }
        if(dob.isEmpty()){
            userDob.setError("Please fill in all the required details");
            userDob.requestFocus();
            return false;
        }
        if(address.isEmpty()){
            userAddress.setError("Please fill in all the required details");
            userAddress.requestFocus();
            return false;
        }
        if(contactNo.isEmpty()){
            userContact.setError("Please fill in all the required details");
            userContact.requestFocus();
            return false;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(mailId).matches()){
            userMail.setError("Invalid Email Address");
            userMail.requestFocus();
            return false;


        }
        mAuth.createUserWithEmailAndPassword(mailId,password1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    User newUser= new User(fullName,mailId,gender,dob,region,address,contactNo);
                    FirebaseDatabase.getInstance().getReference("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(newUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(registerUser.this, "User has been registered successfully", Toast.LENGTH_SHORT).show();
                                Intent intent= new Intent(registerUser.this,loginActivity.class);
                                intent.putExtra("userCode",code);
                                startActivity(intent);

                            }else{
                                Toast.makeText(registerUser.this, "Failed to register!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else{
                    Toast.makeText(registerUser.this, "Failed to register!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return false;
    }

}