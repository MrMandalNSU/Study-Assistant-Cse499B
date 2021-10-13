package com.example.doctalk;

import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.Objects;

import javax.net.ssl.SSLSession;

import de.hdodenhof.circleimageview.CircleImageView;

public class DoctorAdapter extends FirebaseRecyclerAdapter<UserRegistrationDoctor,DoctorAdapter.DoctorViewHolder> {


    public DoctorAdapter(@NonNull FirebaseRecyclerOptions<UserRegistrationDoctor> options) {
        super(options);
    }

    @Override
    public void onBindViewHolder(@NonNull final DoctorViewHolder holder,
                                 final int position,
                                 @NonNull final UserRegistrationDoctor model) {



        holder.fullname.setText(model.getFullname());
        holder.email.setText(Objects.requireNonNull(model).getEmail());




        holder.doctorImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(holder.fullname.getContext(),DoctorProfile.class);
                intent.putExtra("fullname",model.getFullname());
                intent.putExtra("email",model.getEmail());
                intent.putExtra("phone",model.getPhone());



                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                holder.doctorImageView.getContext().startActivity(intent);

            }
        });



    }

    @NonNull
    @Override
    public DoctorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_doctor_extra_list,parent,false);
        return new DoctorViewHolder(view);
    }

    class DoctorViewHolder extends RecyclerView.ViewHolder{

        CircleImageView doctorImageView;
        TextView fullname,email;



        public DoctorViewHolder(@NonNull View itemView) {
            super(itemView);

            doctorImageView = (CircleImageView)itemView.findViewById(R.id.doctorimageview);
            fullname= (TextView)itemView.findViewById(R.id.doctorname);
            email= (TextView) itemView.findViewById(R.id.doctoremail);


        }

    }


}
