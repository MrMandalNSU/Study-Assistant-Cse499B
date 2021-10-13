package com.example.doctalk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.ArrayList;

public class DoctorList extends AppCompatActivity{


    //ListView doctorListView;
    //ArrayList<String> doctorArrayList;
    //ArrayAdapter<String> doctorArrayAdapter;
    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    DoctorAdapter doctorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_list);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.keepSynced(true);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));




        //doctorListView = (ListView) findViewById(R.id.doctorListView);
        //doctorArrayList = new ArrayList<>();
        //doctorArrayAdapter = new ArrayAdapter<String>(this,R.layout.activity_doctor_extra_list,R.id.doctorList,doctorArrayList);


        FirebaseRecyclerOptions<UserRegistrationDoctor> options =
                new FirebaseRecyclerOptions.Builder<UserRegistrationDoctor>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Users").orderByChild("userType").equalTo("Doctor"), UserRegistrationDoctor.class)
                        .build();

        doctorAdapter = new DoctorAdapter(options);
        recyclerView.setAdapter(doctorAdapter);


    }
    @Override
    protected void onStart() {
        super.onStart();
        doctorAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        doctorAdapter.stopListening();
    }








        //databaseReference.child("Users").orderByChild("userType").equalTo("Doctor").addValueEventListener(new ValueEventListener() {
          //  @Override
            //public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              //  for(DataSnapshot data: dataSnapshot.getChildren()){
                //    String tempName = data.child("fullname").getValue(String.class);
                  //  doctorArrayList.add(tempName);

                    //with tempName you can access their usernames and you will only get the usernames with session 2011, so you can directly populate your listView from here and use it

                //}
                //doctorListView.setAdapter(doctorArrayAdapter);

            //}


            //@Override
            //public void onCancelled(@NonNull DatabaseError databaseError) {

            //}
        //});







    }
