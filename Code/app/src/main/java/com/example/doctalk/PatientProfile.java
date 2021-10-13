package com.example.doctalk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class PatientProfile extends AppCompatActivity {


    TextView name, age,location, phone, addiSymptoms;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    private String phoneID;
    private FirebaseUser user;
    private DatabaseReference databaseReference;
    String currentPatientName,currentPatientAge,currentPatientLocation,currentPatientPhone,currentPatientAddiSymptoms;
    String position;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_profile);

        name = findViewById(R.id.patientProfileUserName);
        age = findViewById(R.id.patientProfileUserAge);
        location = findViewById(R.id.patientProfileUserLocation);
        phone = findViewById(R.id.patientProfileUserPhone);
        addiSymptoms = findViewById(R.id.patientProfileUserSymptoms);


        currentPatientName = getIntent().getExtras().get("name").toString();
        currentPatientAge = getIntent().getExtras().get("age").toString();
        currentPatientLocation = getIntent().getExtras().get("location").toString();
        currentPatientAddiSymptoms = getIntent().getExtras().get("addiSymptoms").toString();
        currentPatientPhone = getIntent().getExtras().get("phone").toString();



        name.setText(currentPatientName);
        age.setText(currentPatientAge);
        location.setText(currentPatientLocation);
        phone.setText(currentPatientPhone);
        addiSymptoms.setText(currentPatientAddiSymptoms);








    }
}