package com.example.medcare;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class myDBHelper extends SQLiteOpenHelper {
    private static String DB_NAME= "MedCareDB.db";
    private static final int DB_VERSION= 2;
    private static String DB_PATH= "/data/user/0/com.example.medcare/databases/";
    SQLiteDatabase myDB;
    private final Context mContext;
    public static final String table1= "Symptoms";
    public static final String table2= "Diseases";

    public myDBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, factory, DB_VERSION);
        this.mContext= context;
    }

    private boolean checkDatabase(){
        try{
            final String mPath= DB_PATH+DB_NAME;
            final File file= new File(mPath);
            if(file.exists()){
                return true;
            }else{
                return false;
            }
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }
    private void copyDatabase() throws IOException {
        try{
            InputStream inputStream= mContext.getAssets().open(DB_NAME);
            String outFileName= DB_PATH+DB_NAME;
            OutputStream outputStream=new FileOutputStream(outFileName);

            byte[] buffer= new byte[1024];
            int length;

            while((length= inputStream.read(buffer))>0){
                outputStream.write(buffer,0,length);
            }
            outputStream.flush();
            outputStream.close();
            inputStream.close();

        }catch(Exception e){
            e.printStackTrace();
        }

    }

    private void createDatabase()throws IOException{
        boolean DBexists= checkDatabase();
        if(!DBexists){
            this.getReadableDatabase();
            this.close();
            try{
                copyDatabase();
            }catch(IOException e){
                e.printStackTrace();
                throw new Error("Error copying Database");
            }finally {
                this.close();

            }
        }
    }

    public synchronized void close(){
        if(myDB!=null) {
            myDB.close();
        }
        SQLiteDatabase.releaseMemory();
        super.close();

    }

    public String[] getSymptomList(){
        try{
            createDatabase();
        }catch(IOException e){
            e.printStackTrace();
        }
        SQLiteDatabase db= this.getReadableDatabase();
        String query= "SELECT * FROM "+ table1;
        Cursor cursor= db.rawQuery(query,null);
        String[] symptomList= new String[132];
        if(cursor.moveToFirst()){
            int pos=0;
            do{
                String name= cursor.getString(1);
                symptomList[pos]=name;
                pos++;
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return symptomList;
    }

    public Disease getDiseaseInfo(int ID){
        try{
            createDatabase();
        }catch(IOException e){
            e.printStackTrace();
        }
        SQLiteDatabase db= this.getReadableDatabase();
        String query= "SELECT * FROM "+ table2+ " WHERE ID="+ ID;
        String name="";
        String info="";
        Cursor cursor= db.rawQuery(query,null);
        Disease disease= new Disease();
        if(cursor.moveToFirst()){
            name= cursor.getString(1);
            info= cursor.getString(2);
            disease.setDiseaseName(name);
            disease.setDiseaseInfo(info);
            disease.setID(ID);
        }

        cursor.close();
        db.close();
        return disease;

    }



    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
