package com.example.dacs3.activity.LoaidulichButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dacs3.R;
import com.example.dacs3.activity.MainActivity;
import com.example.dacs3.adapter.DulichAdapter;
import com.example.dacs3.model.Amthuc;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class DulichMain extends AppCompatActivity {
    TextView icback;
    RecyclerView rv_dl;
    com.example.dacs3.adapter.DulichAdapter dulichAdapter;
    SearchView edtTKDL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dulich_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Anhxa();

        icback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DulichMain.this, MainActivity.class));
            }
        });

        uploadtoRV();



        edtTKDL.clearFocus();
        edtTKDL.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Query query = FirebaseDatabase.getInstance().getReference().child("Dulich")
                        .orderByChild("title")  // Thay "title" bằng trường bạn muốn tìm kiếm
                        .startAt(newText)
                        .endAt(newText + "\uf8ff");

                // Tạo FirebaseRecyclerOptions với truy vấn tìm kiếm
                FirebaseRecyclerOptions<Amthuc> options =
                        new FirebaseRecyclerOptions.Builder<Amthuc>()
                                .setQuery(query, Amthuc.class)
                                .build();

                // Cập nhật adapter với tùy chọn mới
                dulichAdapter = new DulichAdapter(options);
                dulichAdapter.startListening();
                rv_dl.setAdapter(dulichAdapter);

                return true;

            }
        });

    }
    private void uploadtoRV() {
        FirebaseRecyclerOptions<Amthuc> options =
                new FirebaseRecyclerOptions.Builder<Amthuc>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Dulich"), Amthuc.class)
                        .build();
        dulichAdapter = new DulichAdapter(options);
        rv_dl.setAdapter(dulichAdapter);
    }

    private void Anhxa() {
        icback = findViewById(R.id.backpage);
        rv_dl = (RecyclerView) findViewById(R.id.rv_dl);
        rv_dl.setLayoutManager(new LinearLayoutManager(this));
        edtTKDL = findViewById(R.id.edtTKDL);

    }
    @Override
    protected void onStart() {
        super.onStart();
        dulichAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        dulichAdapter.stopListening();
    }
}