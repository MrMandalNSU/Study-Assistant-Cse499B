package com.example.doctalk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class DoctorProfile extends AppCompatActivity {


    TextView fullname, email, phone;
    String currentDoctorName,currentDoctorEmail,currentDoctorPhone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_profile);


        fullname = findViewById(R.id.doctorProfileUserName);
        email = findViewById(R.id.doctorProfileUserEmail);
        phone = findViewById(R.id.doctorProfileUserPhone);


        currentDoctorName= getIntent().getStringExtra("fullname");
        currentDoctorEmail= getIntent().getStringExtra("email");
        currentDoctorPhone= getIntent().getStringExtra("phone");

        fullname.setText(currentDoctorName);
        email.setText(currentDoctorEmail);
        phone.setText(currentDoctorPhone);











    }
}