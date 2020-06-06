package com.example.attendancesystemqr;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase databases;
    DatabaseReference myRefs;
    public void ShowAttendance(View view){
        Intent i = new Intent(this,Attendance.class);
        startActivity(i);
    }

    String res;
    String[] separated;
    public void ScanCode (View view){

        IntentIntegrator intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.initiateScan();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(intentResult!=null){
            if(intentResult.getContents()==null){
                Toast.makeText(this, "Try again !", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(this,MainActivity.class);
                startActivity(i);
            }
            else{
                res=intentResult.getContents();
                separated = res.split(":");
                databases = FirebaseDatabase.getInstance();
                SimpleDateFormat currentDate = new SimpleDateFormat("dd-MM-yyyy");
                Date todayDate = new Date();
                final String thisDate = currentDate.format(todayDate);
                myRefs=databases.getReference("Attendance").child(thisDate);
                myRefs.child(separated[1]).child("Reg").setValue(separated[1]);
                Toast.makeText(MainActivity.this, "Marked Successfully", Toast.LENGTH_SHORT).show();
                           }

        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
