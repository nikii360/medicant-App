package com.example.medcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.MultiAutoCompleteTextView;
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

public class registerDoc extends AppCompatActivity {
    private TextInputEditText docFname, docLname, docMail, docPwd,docRePwd, docDob,docAddress, docContact, docAcademicQ, docExperience;
    private RadioGroup docGender;
    private MultiAutoCompleteTextView docSpecialty;
    private MaterialAutoCompleteTextView docRegion;
    private DatePickerDialog datePickerDialog;
    private FirebaseAuth mAuth;
    private Button signUp;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_doc);
        mAuth= FirebaseAuth.getInstance();

        docFname= findViewById(R.id.docFname);
        docLname= findViewById(R.id.docLname);
        docMail= findViewById(R.id.docMail);
        docPwd= findViewById(R.id.docPwd);
        docRePwd= findViewById(R.id.docRePwd);
        docDob= findViewById(R.id.docDob);
        docAddress= findViewById(R.id.docAddress);
        docContact = findViewById(R.id.docContact);
        docGender= findViewById(R.id.docGender);
        docRegion= findViewById(R.id.docRegion);
        docSpecialty= findViewById(R.id.docSpecialty);
        docAcademicQ= findViewById(R.id.docAcademicQ);
        docExperience= findViewById(R.id.docExperience);

        String[] States= getResources().getStringArray(R.array.regionList);
        ArrayAdapter<String> adapter= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,States);
        docRegion.setAdapter(adapter);

        String[] Specialties = getResources().getStringArray(R.array.specialties);
        docSpecialty.setThreshold(1);
        docSpecialty.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        ArrayAdapter<String> arrayAdapter= new ArrayAdapter<>(registerDoc.this, android.R.layout.simple_list_item_1,Specialties);
        docSpecialty.setAdapter(arrayAdapter);

        docDob.setInputType(InputType.TYPE_NULL);
        docDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar= Calendar.getInstance();
                int day= calendar.get(Calendar.DAY_OF_MONTH);
                int month= calendar.get(Calendar.MONTH);
                int year= calendar.get(Calendar.YEAR);
                datePickerDialog= new DatePickerDialog(registerDoc.this, R.style.CalenderStyle,new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        docDob.setText(dayOfMonth + " | " + (month + 1) + " | " + year);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        final int finalUserCode=1;
        signUp= findViewById(R.id.docRegister);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerNewdoc(finalUserCode);


            }
        });




    }
    private int Flag=0;
    private boolean registerNewdoc(int code){
        String firstName= docFname.getText().toString();
        String lastName= docLname.getText().toString();
        String fullName= docFname.getText().toString() +" "+docLname.getText().toString();
        String mailId= docMail.getText().toString();
        String password1= docPwd.getText().toString();
        String password2= docRePwd.getText().toString();
        int selectedId= docGender.getCheckedRadioButtonId();
        RadioButton selectedGender= findViewById(selectedId);
        String gender= selectedGender.getText().toString();
        String region= docRegion.getText().toString();
        String dob= docDob.getText().toString();
        String address= docAddress.getText().toString();
        String contactNo= docContact.getText().toString();
        String Specialization= docSpecialty.getText().toString();
        String AQ= docAcademicQ.getText().toString();
        String Exp= docExperience.getText().toString();
        if(Specialization.isEmpty()){
            docSpecialty.setError("Please fill in all the required details");
            docSpecialty.requestFocus();
            return false;
        }
        if(AQ.isEmpty()){
            docAcademicQ.setError("Please fill in all the required details");
            docAcademicQ.requestFocus();
            return false;
        }
        if(Exp.isEmpty()){
            docExperience.setError("Please fill in all the required details");
            docExperience.requestFocus();
            return false;
        }
        if(firstName.isEmpty()){
            docFname.setError("Please fill in all the required details");
            docFname.requestFocus();
            return false;
        }
        if(password1.isEmpty()){
            docPwd.setError("Please fill in all the required details");
            docPwd.requestFocus();
            return false;
        }
        if(lastName.isEmpty()){
            docLname.setError("Please fill in all the required details");
            docLname.requestFocus();
            return false;
        }
        if(mailId.isEmpty()){
            docMail.setError("Please fill in all the required details");
            docMail.requestFocus();
            return false;
        }
        if(password2.isEmpty()){
            docRePwd.setError("Please fill in all the required details");
            docRePwd.requestFocus();
            return false;
        }
        if(!password1.equals(password2)){
            docRePwd.setError("Passwords do not match");
            docRePwd.requestFocus();
            return false;
        }
        if(region.isEmpty()){
            docRegion.setError("Please fill in all the required details");
            docRegion.requestFocus();
            return false;
        }
        if(dob.isEmpty()){
            docDob.setError("Please fill in all the required details");
            docDob.requestFocus();
            return false;
        }
        if(address.isEmpty()){
            docAddress.setError("Please fill in all the required details");
            docAddress.requestFocus();
            return false;
        }
        if(contactNo.isEmpty()){
            docContact.setError("Please fill in all the required details");
            docContact.requestFocus();
            return false;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(mailId).matches()){
            docMail.setError("Invalid Email Address");
            docMail.requestFocus();
            return false;


        }

        mAuth.createUserWithEmailAndPassword(mailId,password1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Doctor newdoc= new Doctor(fullName,mailId,gender,dob,region,address,contactNo,Specialization,AQ,Exp);
                    FirebaseDatabase.getInstance().getReference("Doctors")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(newdoc).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(registerDoc.this, "User has been registered successfully", Toast.LENGTH_LONG).show();
                                Intent intent= new Intent(registerDoc.this,loginActivity.class);
                                intent.putExtra("userCode",code);
                                startActivity(intent);

                            }else{
                                Toast.makeText(registerDoc.this, "Failed to register!", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }else{
                    Toast.makeText(registerDoc.this, "Failed to register!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return false;

    }
}