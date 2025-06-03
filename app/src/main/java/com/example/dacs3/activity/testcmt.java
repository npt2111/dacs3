package com.example.dacs3.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dacs3.R;
import com.example.dacs3.adapter.BinhluanAdapter;
import com.example.dacs3.model.Danhgia;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class testcmt extends AppCompatActivity {
    TextView icback;
    RecyclerView rv_test;
    BinhluanAdapter blAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_testcmt);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        rv_test = (RecyclerView) findViewById(R.id.rv_testcmt);
        rv_test.setLayoutManager(new LinearLayoutManager(this));
        uploadtoRV();

    }
    private void uploadtoRV() {
        FirebaseRecyclerOptions<Danhgia> options =
                new FirebaseRecyclerOptions.Builder<Danhgia>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("danhgiaamthuc"), Danhgia.class)
                        .build();

        blAdapter = new BinhluanAdapter(options);
        rv_test.setAdapter(blAdapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (blAdapter != null) {
            blAdapter.startListening();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (blAdapter != null) {
            blAdapter.stopListening();
        }
    }

}