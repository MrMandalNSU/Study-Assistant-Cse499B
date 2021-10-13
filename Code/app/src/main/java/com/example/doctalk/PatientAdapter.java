package com.example.doctalk;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class PatientAdapter extends FirebaseRecyclerAdapter<PatientHelperClass,PatientAdapter.PatientViewHolder> {

    public PatientAdapter(@NonNull FirebaseRecyclerOptions<PatientHelperClass> options) {
        super(options);
    }

    @Override
    public void onBindViewHolder(@NonNull final PatientAdapter.PatientViewHolder holder,
                                 final int position, @NonNull final PatientHelperClass model) {

        holder.name.setText(model.getName());
        holder.addiSymptoms.setText(Objects.requireNonNull(model).getAddiSymptoms());







        holder.patientImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(holder.name.getContext(),PatientProfile.class);

                intent.putExtra("name",model.getName());
                intent.putExtra("age",model.getAge());
                intent.putExtra("phone",model.getPhone());
                intent.putExtra("addiSymptoms",model.getAddiSymptoms());
                intent.putExtra("gender",model.getGender());
                intent.putExtra("location",model.getLocation());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                holder.patientImageView.getContext().startActivity(intent);

            }
        });








    }

    @NonNull
    @Override
    public PatientAdapter.PatientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_patient_list,parent,false);
        return new PatientAdapter.PatientViewHolder(view);
    }

    class PatientViewHolder extends RecyclerView.ViewHolder{

        CircleImageView patientImageView;
        TextView name,addiSymptoms;


        public PatientViewHolder(@NonNull View itemView) {
            super(itemView);

            patientImageView = (CircleImageView)itemView.findViewById(R.id.patientimageview);
            name= (TextView)itemView.findViewById(R.id.patientname);
            addiSymptoms= (TextView) itemView.findViewById(R.id.patientsymptoms);


        }

    }


}



