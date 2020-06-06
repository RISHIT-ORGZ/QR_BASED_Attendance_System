package com.example.attendancesystemqr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;


public class Info extends AppCompatActivity {


    FirebaseDatabase database;
    DatabaseReference myRef;
    EditText editText;
    Button button;
    String reg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        editText = findViewById(R.id.editText);
        button = findViewById(R.id.button);
        database = FirebaseDatabase.getInstance();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reg= editText.getText().toString();
                myRef = database.getReference().child("Users");
                myRef.orderByChild("Reg").equalTo(reg)
            .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            if(reg.length()==9){
                            Intent intent = new Intent(Info.this, Generating.class);
                                intent.putExtra("reg", reg);
                            startActivity(intent);
                            }
                            else {
                                Intent i = new Intent(Info.this, MainActivity.class);
                                startActivity(i);
                            }
                            }
                        else {
                            Toast.makeText(Info.this, "User Not Found", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }

        });

    }
}
