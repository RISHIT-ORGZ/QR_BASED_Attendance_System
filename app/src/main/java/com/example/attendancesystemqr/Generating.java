package com.example.attendancesystemqr;
//GENERATING QR CODE
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.util.Map;

public class Generating extends AppCompatActivity {

    FirebaseDatabase databases;
    DatabaseReference myRefs;
   ImageView imageView;
    String name1, reg1,eData;
    TextView nameId,regNo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generating);
        databases = FirebaseDatabase.getInstance();

        Bundle bundle = getIntent().getExtras();
        String regd= bundle.getString("reg");
        nameId = findViewById(R.id.nameId);
        regNo = findViewById(R.id.regNo);

        myRefs = databases.getReference().child("Users").child(regd);
        myRefs.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String,String> map = (Map<String, String>) dataSnapshot.getValue();
                name1 = map.get("Name");
                reg1 = map.get("Reg");
                regNo.setText(reg1);
                nameId.setText(name1);
                eData = name1 + ":" + reg1 ;
                imageView = findViewById(R.id.imageView);

                QRCodeWriter qrCodeWriter = new QRCodeWriter();
                try {
                    BitMatrix bitMatrix = qrCodeWriter.encode(eData, BarcodeFormat.QR_CODE,200,200);
                    Bitmap bitmap = Bitmap.createBitmap(200,200,Bitmap.Config.RGB_565);

                    for(int x=0;x<200;x++){
                        for(int y=0;y<200;y++){
                            bitmap.setPixel(x,y,bitMatrix.get(x,y)? Color.BLACK : Color.WHITE);
                        }
                    }
                    imageView.setImageBitmap(bitmap);
                } catch (WriterException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
