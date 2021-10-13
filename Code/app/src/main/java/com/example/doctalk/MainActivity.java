package com.example.doctalk;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.Button;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private Button ocrbutton;
    private Button patientsymptoms;
    private Button profilebutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ocrbutton = (Button) findViewById(R.id.ocr_btn); //ocr button
        patientsymptoms = (Button) findViewById(R.id.symptoms_Btn);
        profilebutton = (Button) findViewById(R.id.profile_btn);



        ocrbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(MainActivity.this,text_recognition.class);
                startActivity( intent1);
                finish();

            }
        });


        patientsymptoms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(MainActivity.this,Patient_Info.class);
                startActivity( intent2);
                finish();

            }
        });

        profilebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent(MainActivity.this,Profile.class);
                startActivity( intent3);
            }
        });

    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut(); //logout user
        startActivity(new Intent(getApplicationContext(),Login.class)); //after logout sending them to login activity again.
        finish();
    }

}