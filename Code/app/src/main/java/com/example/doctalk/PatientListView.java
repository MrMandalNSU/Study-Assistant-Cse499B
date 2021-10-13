package com.example.doctalk;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;

public class PatientListView extends AppCompatActivity {


    //ListView patientListView;
    //ArrayList<String> patientArrayList;
    //ArrayAdapter<String> patientArrayAdapter;
    DatabaseReference databaseReference;
    PatientHelperClass patientHelperClass;
    RecyclerView recyclerView;
    PatientAdapter patientAdapter;
    private String userID;
    private FirebaseUser user;
    String Name;




    @Override
        public void onCreate (Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_patient_list_view);

            databaseReference = FirebaseDatabase.getInstance().getReference();
            databaseReference.keepSynced(true);
            recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            //FirebaseAuth fAuth = FirebaseAuth.getInstance();

            SessionManager sessionManager = new SessionManager(this);
            sessionManager.getUserDetailFromSession();
            HashMap<String,String> userdetails = sessionManager.getUserDetailFromSession();

            String Name = userdetails.get(SessionManager.KEY_FULLNAME);

            Toast.makeText(PatientListView.this," "+Name,Toast.LENGTH_SHORT).show();




        //doctorListView = (ListView) findViewById(R.id.doctorListView);
            //doctorArrayList = new ArrayList<>();
            //doctorArrayAdapter = new ArrayAdapter<String>(this,R.layout.activity_doctor_extra_list,R.id.doctorList,doctorArrayList);


            FirebaseRecyclerOptions<PatientHelperClass> options =
                    new FirebaseRecyclerOptions.Builder<PatientHelperClass>()
                            .setQuery(FirebaseDatabase.getInstance().
                                            getReference().child("Patient").
                                            orderByChild("doctorName").equalTo(Name)
                                    , PatientHelperClass.class)
                            .build();

            patientAdapter = new PatientAdapter(options);
            recyclerView.setAdapter(patientAdapter);


        }

        @Override
        protected void onStart () {
            super.onStart();
            patientAdapter.startListening();
        }

        @Override
        protected void onStop () {
            super.onStop();
            patientAdapter.stopListening();
        }
    }





        /* patientHelperClass = new PatientHelperClass();
        databaseReference= FirebaseDatabase.getInstance().getReference("Patient");
        patientListView = (ListView) findViewById(R.id.patientListView);
        patientArrayList = new ArrayList<>();
        patientArrayAdapter = new ArrayAdapter<String>(this,R.layout.activity_patient_list,R.id.patientList,patientArrayList);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren())
                {
                    patientHelperClass = ds.getValue(PatientHelperClass.class);
                    patientArrayList.add(patientHelperClass.getName().toString());
                }

                patientListView.setAdapter(patientArrayAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



       patientListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


              PatientHelperClass patientHelperClass = (PatientHelperClass) patientListView.getItemAtPosition(position);


                //fetch the details of the item

               databaseReference.child(String.valueOf(position+1)).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            //get details of the item
                            String name = dataSnapshot.child("name").getValue(String.class);
                            String age = dataSnapshot.child("age").getValue(String.class);
                            String location = dataSnapshot.child("location").getValue(String.class);
                            String phone = dataSnapshot.child("phone").getValue(String.class);
                            String addiSymptoms = dataSnapshot.child("addiSymptoms").getValue(String.class);

                            //open another acivity and pass these
                            Intent intent = new Intent(PatientListView.this, PatientProfile.class);
                            intent.putExtra("name", name);
                            intent.putExtra("age", age);
                            intent.putExtra("location", location);
                            intent.putExtra("phone", phone);
                            intent.putExtra("addiSymptoms", addiSymptoms);
                            startActivity(intent);





                   }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
               });


            }
        }); */








