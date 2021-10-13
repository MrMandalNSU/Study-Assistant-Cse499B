package com.example.doctalk;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Objects;

public class Login extends AppCompatActivity {
    EditText mEmail, mPassword;
    Button mLoginbtn;
    TextView mCreatebtn;
    ProgressBar mProgressBar;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;
    String userType;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmail = findViewById(R.id.Email);
        mPassword = findViewById(R.id.password);
        mProgressBar = findViewById(R.id.progressBar2);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        mLoginbtn = findViewById(R.id.loginbtn);
        mCreatebtn = findViewById(R.id.createtext);


        mLoginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    mEmail.setError("Email is Required.");
                    return;

                }

                if (TextUtils.isEmpty(password)) {
                    mPassword.setError("Password is Required");
                    return;
                }

                if (password.length() < 6) {
                    mPassword.setError("Password is too short ");
                    return;
                }


                mProgressBar.setVisibility(View.VISIBLE);

                // Authenticate User.

                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        final String patient = new String("Patient");
                        if(task.isSuccessful())
                        {
                            String UserId = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();
                            databaseReference= FirebaseDatabase.getInstance().getReference().child("Users").child(UserId);
                            databaseReference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    String type = Objects.requireNonNull(dataSnapshot.child("userType").getValue()).toString();

                                    if(type.equals("Doctor"))
                                    {
                                        Toast.makeText(Login.this, "Welcome Teacher", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(getApplicationContext(), DoctorClass.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);

                                    }

                                    if (type.equals("Patient"))
                                    {
                                        Toast.makeText(Login.this, "Welcome Student", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(getApplicationContext(), PatientClass.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);

                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                            //Toast.makeText(Login.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();

                        }
                        else
                        {
                            Toast.makeText(Login.this, "Error !" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            mProgressBar.setVisibility(View.GONE);
                        }
                    }
                });

            }
        });


        mCreatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(),Register.class));
            }
        });

    }
}