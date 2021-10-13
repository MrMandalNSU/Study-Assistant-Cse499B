package com.example.doctalk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DoctorClass extends AppCompatActivity {

    private Button ocrbutton;
    private Button ocrbutton2;
    private Button profilebutton;
    private Button respondtopatient;

    String Name;
    DatabaseReference databaseReference;
    PatientHelperClass patientHelperClass;
    RecyclerView recyclerView;
    PatientAdapter patientAdapter;
    String userID;
    FirebaseUser user;

    FirebaseAuth firebaseAuth;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_class);



                ocrbutton = (Button) findViewById(R.id.ocr_btnDoc); //ocr button
                ocrbutton2 = (Button) findViewById(R.id.ocr_btnDoc2);
                profilebutton = (Button) findViewById(R.id.profile_btnDoc);
                respondtopatient = (Button) findViewById(R.id.symptoms_BtnDoc);
                FirebaseAuth fAuth;
                fAuth = FirebaseAuth.getInstance();
                user = fAuth.getCurrentUser();
                databaseReference = FirebaseDatabase.getInstance().getReference("Users");
                userID = user.getUid();


                ocrbutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent1 = new Intent(com.example.doctalk.DoctorClass.this,text_recognition.class);
                        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity( intent1);


                    }
                });

                ocrbutton2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent1 = new Intent(com.example.doctalk.DoctorClass.this,text_recognition2.class);
                        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity( intent1);


                    }
                });

                profilebutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent2 = new Intent(com.example.doctalk.DoctorClass.this,Profile.class);
                        intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity( intent2);

                    }
                });


                respondtopatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                databaseReference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        UserRegistrationDoctor userRegistrationDoctor = dataSnapshot.getValue(UserRegistrationDoctor.class);

                        if(userRegistrationDoctor != null)
                        {
                            String Name = userRegistrationDoctor.fullname;
                            String Email = userRegistrationDoctor.email;
                            String Phone = userRegistrationDoctor.phone;
                            String UserType = userRegistrationDoctor.userType;
                            String licenseNumber = userRegistrationDoctor.licenseNumber;

                            SessionManager sessionManager = new SessionManager(DoctorClass.this);
                            sessionManager.CreateLoginSession(Name,Email,Phone,UserType,licenseNumber);
                            startActivity(new Intent(getApplicationContext(),PatientListView.class));

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {


                    }
                });


            }
        });









    }

            public void logout(View view) {
                FirebaseAuth.getInstance().signOut(); //logout user
                startActivity(new Intent(getApplicationContext(),Login.class)); //after logout sending them to login activity again.
                finish();
            }
        }



