package com.example.doctalk;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class Profile extends AppCompatActivity {
    TextView fullName, email, phone, userType;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    private String userID;
    private FirebaseUser user;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        fullName = findViewById(R.id.profileUserName);
        email = findViewById(R.id.profileUserEmail);
        phone = findViewById(R.id.profileUserPhone);
        userType = findViewById(R.id.profileUserType);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        user = fAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        databaseReference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserRegistration userRegistration = dataSnapshot.getValue(UserRegistration.class);

                if(userRegistration != null)
                {
                    String Name = userRegistration.fullname;
                    String Email = userRegistration.email;
                    String Phone = userRegistration.phone;
                    String UserType = userRegistration.userType;

                    fullName.setText(Name);
                    email.setText(Email);
                    phone.setText(Phone);
                    userType.setText(UserType);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(Profile.this,"Something is Wrong!",Toast.LENGTH_SHORT).show();

            }
        });
    }
}