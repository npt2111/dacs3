package com.example.dacs3.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dacs3.R;
import com.example.dacs3.adapter.UserAdapter;
import com.example.dacs3.model.Users;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class qlyNguoidung extends AppCompatActivity {
    TextView icback;
    RecyclerView rv_user;
    UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_qly_nguoidung);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Anhxa();
        uploadtoRV();

        icback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(qlyNguoidung.this, MainActivity.class));
            }
        });
    }
    private void uploadtoRV() {
        FirebaseRecyclerOptions<Users> options =
                new FirebaseRecyclerOptions.Builder<Users>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("users"), Users.class)
                        .build();
        userAdapter = new UserAdapter(options);
        rv_user.setAdapter(userAdapter);
    }


    @Override
    protected void onStart() {
        super.onStart();
        userAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        userAdapter.stopListening();
    }


    private void Anhxa() {
        icback = findViewById(R.id.backpage);
        rv_user = (RecyclerView) findViewById(R.id.rv_user);
        rv_user.setLayoutManager(new LinearLayoutManager(this));
    }
}