package com.example.medcare;

import static android.view.View.GONE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Switch;
import android.widget.TextView;

import com.example.medcare.ml.Dpredictor;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputEditText;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class diseaseDiagnosis extends AppCompatActivity {
    private MultiAutoCompleteTextView multiAutoCompleteTextView;
    private TextView cardHeading, cardBody;
    private CardView diseaseInfo;
    private Button checkDisease;
    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disease_diagnosis);

        bottomNavigationView= findViewById(R.id.bottomNavigatorView2);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.myHome: startActivity(new Intent(diseaseDiagnosis.this,homeUser.class));
                    overridePendingTransition(0,0);
                    return true;
                    case R.id.myProfile: Intent intent= new Intent(diseaseDiagnosis.this,myProfile.class);
                    intent.putExtra("userCode",2);
                    startActivity(intent);
                    overridePendingTransition(0,0);
                    return true;
                }
                return false;
            }
        });

        multiAutoCompleteTextView= findViewById(R.id.symptoms);
        multiAutoCompleteTextView.setThreshold(1);
        multiAutoCompleteTextView.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        cardHeading= findViewById(R.id.diseaseHeading);
        cardBody= findViewById(R.id.diseaseBody);
        checkDisease= findViewById(R.id.checkDisease);
        diseaseInfo= findViewById(R.id.diseaseInfo);

        myDBHelper DBHelper= new myDBHelper(this,null,null,2);
        String Symptoms[]= DBHelper.getSymptomList();
        ArrayAdapter<String> adapter= new ArrayAdapter<String>(diseaseDiagnosis.this,android.R.layout.simple_list_item_1,Symptoms);
        multiAutoCompleteTextView.setAdapter(adapter);

        ArrayList<String> symptomList= new ArrayList<String>(Arrays.asList(Symptoms));


        checkDisease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float[] inputArray= new float[132];
                String s= multiAutoCompleteTextView.getText().toString();
                int start=0;
                for(int i=0;i<s.length();i++){
                    if(s.charAt(i)==','){
                        String symptom= s.substring(start,i);
                        start=i+2;
                        inputArray[symptomList.indexOf(symptom)]=1;
                    }
                }
                int result= showPrediction(inputArray);
                Disease disease= DBHelper.getDiseaseInfo(result);
                cardHeading.setText(disease.getDiseaseName());
                cardBody.setText(disease.getDiseaseInfo());
                diseaseInfo.setVisibility(View.VISIBLE);
//                float[] predictions= showPrediction(inputArray);
//                float[] probabilities= new float[3];
//                ArrayList<Disease> diseases= new ArrayList<>();
//                for(int i=0,j=0;i<predictions.length;i++){
//                    if(predictions[i]!=0.0f){
//                        Disease disease= DBHelper.getDiseaseInfo(i);
//                        diseases.add(disease);
//                        probabilities[j]=predictions[i];
//                        j++;
//                    }
//                }
//
//                cardBody.setText(diseases.get(0).getDiseaseName()+": "+probabilities[0]+"\n"+
//                        diseases.get(1).getDiseaseName()+": "+probabilities[1]+"\n"+diseases.get(2).getDiseaseName()+": "+probabilities[2]);
//                diseaseInfo.setVisibility(View.VISIBLE);

            }
        });


    }
    private int showPrediction(float[] inputArray) {
        float[] outputArray = new float[41];
        try {
            Dpredictor model = Dpredictor.newInstance(diseaseDiagnosis.this);

            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(132 * 4).order(ByteOrder.nativeOrder());
            byteBuffer.rewind();

            for (int i = 0; i < inputArray.length; i++) {
                byteBuffer.putFloat(inputArray[i]);
            }

            // Creates inputs for reference.
            TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 132}, DataType.FLOAT32);
            inputFeature0.loadBuffer(byteBuffer);

            // Runs model inference and gets result.
            Dpredictor.Outputs outputs = model.process(inputFeature0);
            outputArray = outputs.getOutputFeature0AsTensorBuffer().getFloatArray();

            // Releases model resources if no longer used.
            model.close();
        } catch (IOException e) {
            e.printStackTrace();

            // TODO Handle the exception
        }
        float maxNum = outputArray[0];
        int index = 0;
        for (int i = 0; i < outputArray.length; i++) {
            if (outputArray[i] > maxNum) {

                maxNum = outputArray[i];
                index = i;
            }
        }

        Log.v("ARGMAX: ", "" + index);
        return index;
//        float[] percentages= new float[41];
//        float maxNum= outputArray[0];
//        int index=0;
//        for(int i=0;i<outputArray.length;i++){
//            if(outputArray[i]>maxNum){
//                maxNum= outputArray[i];
//                index=i;
//            }
//        }
//        percentages[index]=maxNum*100;
//        outputArray[index]=-1;
//        maxNum=-1;
//        for(int i=0;i<outputArray.length;i++){
//            if(outputArray[i]>maxNum){
//                maxNum= outputArray[i];
//                index=i;
//            }
//        }
//        percentages[index]= maxNum*100;
//        outputArray[index]=-1;
//        maxNum=-1;
//        for(int i=0;i<outputArray.length;i++){
//            if(outputArray[i]>maxNum){
//                maxNum= outputArray[i];
//                index=i;
//            }
//        }
//        percentages[index]=maxNum*100;
//
//
//        return percentages;
//    }
    }
}