package com.example.doctalk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class PatientClass extends AppCompatActivity {

    private Button ocrbutton;
    private Button ocrbutton2;
    private Button profilebutton;
    private Button doctorList;
    private Button bookUrAppointment;
    private Button notepad;
    private Button calculator;
    private Button gsearch;
    private Button counter;
    private Button chat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_class);





        ocrbutton = (Button) findViewById(R.id.ocr_btn); //ocr button
        //ocrbutton2 = (Button) findViewById(R.id.ocr_btn2);
        profilebutton = (Button) findViewById(R.id.profile_btn);
        doctorList = (Button) findViewById(R.id.doc_List);
        //bookUrAppointment = (Button) findViewById(R.id.symptoms_Btn);

        notepad = (Button) findViewById(R.id.np_btn);

        //calculator = (Button) findViewById(R.id.calculator_btn);

        gsearch = (Button) findViewById(R.id.gsearch_btn);

        counter = (Button) findViewById(R.id.counter_btn);

        //chat = (Button) findViewById(R.id.chat_btn);





        /*

        bookUrAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(PatientClass.this,Patient_Info.class);
                intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity( intent2);


            }
        });

        */

        ocrbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(PatientClass.this,text_recognition.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity( intent1);


            }
        });

        /*
        ocrbutton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(PatientClass.this,text_recognition2.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity( intent1);


            }
        });

        */


        profilebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent(PatientClass.this,profilepage.class);
                intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity( intent3);

            }
        });

        doctorList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent4 = new Intent(com.example.doctalk.PatientClass.this,booklist.class);
                intent4.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity( intent4);

            }
        });

        notepad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent5 = new Intent(com.example.doctalk.PatientClass.this,TextEditor.class);
                intent5.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity( intent5);
            }
        });


        gsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent7 = new Intent(com.example.doctalk.PatientClass.this,GSearch.class);
                intent7.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity( intent7);
            }
        });

        counter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent8 = new Intent(com.example.doctalk.PatientClass.this,Counter.class);
                intent8.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity( intent8);
            }
        });

        /*
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent9 = new Intent(com.example.doctalk.PatientClass.this,Chat.class);
                intent9.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity( intent9);
            }
        });
         */

    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut(); //logout user
        startActivity(new Intent(getApplicationContext(),Login.class)); //after logout sending them to login activity again.
        finish();
    }

    public void openApp(View view) {
        Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.miui.calculator");

        if(launchIntent != null) {
            startActivity(launchIntent);
        } else {
            Toast.makeText(PatientClass.this, "No app found", Toast.LENGTH_LONG).show();
        }
    }

    public void openApp1(View view) {
        Intent launchIntent = getPackageManager().getLaunchIntentForPackage("net.penguincoders.doit");

        if(launchIntent != null) {
            startActivity(launchIntent);
        } else {
            Toast.makeText(PatientClass.this, "No app found", Toast.LENGTH_LONG).show();
        }
    }

    public void openApp2(View view) {
        Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.yourdeveloeperhere.quickchatapp");

        if(launchIntent != null) {
            startActivity(launchIntent);
        } else {
            Toast.makeText(PatientClass.this, "No app found", Toast.LENGTH_LONG).show();
        }
    }

}

