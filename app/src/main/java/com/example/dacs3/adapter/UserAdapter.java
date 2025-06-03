package com.example.dacs3.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dacs3.R;
import com.example.dacs3.model.Users;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class UserAdapter extends FirebaseRecyclerAdapter<Users, UserAdapter.usHolder> {
    public UserAdapter(@NonNull FirebaseRecyclerOptions<Users> options) {
        super(options);
    }

    @SuppressLint("RecyclerView")
    @Override
    protected void onBindViewHolder(@NonNull UserAdapter.usHolder usHolder, int position, @NonNull Users users) {



        usHolder.name.setText(users.getUsername());
        usHolder.birthday.setText(users.getBirthday());
        usHolder.email.setText(users.getEmail());



        usHolder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(usHolder.name.getContext());
                builder.setTitle("Xóa?");
                builder.setMessage("Xóa");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase.getInstance().getReference().child("users")
                                .child(getRef(position).getKey()).removeValue();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(usHolder.name.getContext(), "Cancelled", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });


    }


    @NonNull
    @Override
    public UserAdapter.usHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_user,parent,false);

        return new UserAdapter.usHolder(view);
    }

    class usHolder extends RecyclerView.ViewHolder{

        TextView name, birthday,email;
        Button btn_update, btn_delete;


        public usHolder(@NonNull View itemView) {
            super(itemView);

            name=(TextView) itemView.findViewById(R.id.rvnameUser);
            birthday=(TextView) itemView.findViewById(R.id.rvbirthdayuser);
            email=(TextView) itemView.findViewById(R.id.emailrvUser);

            btn_delete=(Button) itemView.findViewById(R.id.btn_delete_user);

        }
    }

}