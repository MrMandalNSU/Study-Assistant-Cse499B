package com.example.doctalk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Register extends AppCompatActivity {


    public static final String TAG = "TAG";
    EditText mfullname, mEmail, mPassword, mPhone,mLicense;
    Button mRegisterbtn;
    TextView mLoginbtn;
    FirebaseAuth fAuth;
    ProgressBar mProgressBar;
    FirebaseFirestore fStore;
    String userID;
    String mUserType;
    Spinner spinner;
    private GoogleSignInClient mGoogleSignInClient;
    private final static int RC_SIGN_IN = 123;

    boolean flag=true;

    Button verify;
    private FirebaseAuth mAuth;

    //Local database for doctor authentication
    public void rCheck(String LN) {

        ArrayList arr = new ArrayList();

        arr.add("123321"); //Doc1
        arr.add("234432"); //Doc2
        arr.add("345543"); //Doc3
        arr.add("456654"); //Doc4
        arr.add("567765"); //Doc5
        arr.add("678876"); //Doc6
        arr.add("789987"); //Doc7

        for(int i = 0; i < arr.size(); i++) {
            String ss = arr.get(i).toString();

            if(LN.equals(ss))
                flag=true;
        }
    }

    public static char getCharFromString(String str, int index)
    {
        return str.charAt(index);
    }


    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = mAuth.getCurrentUser();
        if(user!=null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mfullname = findViewById(R.id.fullname);
        mEmail = findViewById(R.id.Email);
        mPassword = findViewById(R.id.password);
        mPhone = findViewById(R.id.phone);
        mRegisterbtn = findViewById(R.id.registerbtn);
        mLoginbtn = findViewById(R.id.createtext);
        spinner = findViewById(R.id.spinner1);
        mLicense = findViewById(R.id.license);



        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        mProgressBar = findViewById(R.id.progressBar);

        ArrayAdapter<CharSequence> adapter=
                ArrayAdapter.createFromResource(this,R.array.UserType,R.layout.support_simple_spinner_dropdown_item);
        spinner.setPrompt("Select One");
        spinner.setAdapter(new NothingSelectedSpinnerAdapter(adapter, R.layout.contact_spinner_row_nothing_selected,
                        // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
                        this));


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                switch (position) {
                    case 1:
                        // set editbox visible
                        mLicense.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        // set editbox invivible
                        mLicense.setVisibility(View.INVISIBLE);
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // set editbox Gone
                mLicense.setVisibility(View.GONE);

            }
        });

        if (fAuth.getCurrentUser() != null) {

            startActivity(new Intent(getApplicationContext(), PatientClass.class));
            finish();
        }

        mRegisterbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                final String fullName = mfullname.getText().toString();
                final String phone = mPhone.getText().toString();
                final String userType = spinner.getSelectedItem().toString();
                final String licenseNumber = mLicense.getText().toString();

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

                // -- Previous DocAuth Method -- //
                /*
                if(!TextUtils.isEmpty(licenseNumber)) {

                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("DocAuth");
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                                String ss = (String) snapshot.getValue().toString();

                                if (licenseNumber.equals(ss) && licenseNumber.length()==6)
                                        flag = true;
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    rCheck(licenseNumber);

                    if(!flag) {
                        mLicense.setError("Invalid License Number");
                        return;
                    }
                } */
                //--------------------------------------------------------------------------------------//

                // --    New DocAuth Method    -- //

                /*  It will search for a pattern.
                    The valid pattern is 'D'+'-'+(two digit, the entry year)+(short form of the passed medical college)
                    For example, D-14DMC is a valid license number

               */

                if(!TextUtils.isEmpty(licenseNumber)) {

                    Integer sz = licenseNumber.length();

                    if(sz==7 || sz==8) {

                        String ss = licenseNumber;

                        if(!((getCharFromString(ss, 0))=='D' || (getCharFromString(ss, 0))=='d')) flag=false;
                        if((getCharFromString(ss, 1))!='-') flag=false;

                        if(!((getCharFromString(ss, 2))>='0' && (getCharFromString(ss, 2))<='9')) flag=false;
                        if(!((getCharFromString(ss, 3))>='0' && (getCharFromString(ss, 3))<='9')) flag=false;

                        if(!((getCharFromString(ss, sz-2))=='M' || (getCharFromString(ss, sz-2))=='m')) flag=false;
                        if(!((getCharFromString(ss, sz-1))=='C' || (getCharFromString(ss, sz-1))=='c')) flag=false;

                    }
                    else flag=false;

                    if(!flag) {
                        mLicense.setError("Invalid License Number");
                        flag=true;
                        return;
                    }
                }

                // --    New DocAuth Method    -- //



                mProgressBar.setVisibility(View.VISIBLE);


                // Register the user in Firebase

                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful() && TextUtils.isEmpty(licenseNumber)) {
                            Toast.makeText(Register.this, "Patient Profile Created Successfully", Toast.LENGTH_SHORT).show();

                            UserRegistration userRegistration = new UserRegistration(fullName,email,phone,userType);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).setValue(userRegistration)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()) {
                                        Toast.makeText(Register.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        Toast.makeText(Register.this,"Failed to Register",Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });

                            startActivity(new Intent(getApplicationContext(), PatientClass.class));

                        }

                        else if(task.isSuccessful())

                        {
                            Toast.makeText(Register.this, "Doctor Profile Created Successfully", Toast.LENGTH_SHORT).show();

                            UserRegistrationDoctor userRegistrationDoctor = new UserRegistrationDoctor(fullName,email,phone,userType,licenseNumber);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                                    .setValue(userRegistrationDoctor).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()) {
                                        Toast.makeText(Register.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        Toast.makeText(Register.this,"Failed to Register",Toast.LENGTH_SHORT).show();
                                    }


                                }
                            });

                            UserRegistrationDoctor userRegistrationDoctor1 = new UserRegistrationDoctor(fullName,email,phone,userType,licenseNumber);
                            FirebaseDatabase.getInstance().getReference("Doctors")
                                    .child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                                    .setValue(userRegistrationDoctor1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()) {
                                        Toast.makeText(Register.this, " Doctor Registraton Successful", Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        Toast.makeText(Register.this,"Failed to Register",Toast.LENGTH_SHORT).show();
                                    }


                                }
                            });


                            startActivity(new Intent(getApplicationContext(), DoctorClass.class));

                        }
                        else {
                            if(task.getException() instanceof FirebaseAuthUserCollisionException){
                                Toast.makeText(Register.this, "User is already registered", Toast.LENGTH_SHORT).show();

                                mProgressBar.setVisibility(View.INVISIBLE);
                                return;


                            }else{
                                Toast.makeText(Register.this, "Error"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                                mProgressBar.setVisibility(View.INVISIBLE);
                                return;


                            }

                        }
                    }

                });
            }
        });

        mLoginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });



        //Google Sign In Starts Here.

        mAuth = FirebaseAuth.getInstance();

        createRequest(); //google signIN

        findViewById(R.id.google_signIn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
    }

    //This function is requesting google for email ids as pop up
    private void createRequest() {

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                // ...
                Toast.makeText(this, "Failed: Error Occurred", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {

        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();

                            startActivity(new Intent(getApplicationContext(), MainActivity.class));

                        } else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(Register.this, "Sorry Authentication Failed", Toast.LENGTH_SHORT).show();
                            
                        }

                        // ...
                    }
                });
    }

}
