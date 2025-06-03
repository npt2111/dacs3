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
import com.example.dacs3.adapter.DiadiemAdapter;
import com.example.dacs3.model.Amthuc;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class DiadiemMain extends AppCompatActivity {
    TextView icback;
    RecyclerView rv_dd;
    com.example.dacs3.adapter.DiadiemAdapter diadiemAdapter;
    SearchView edtTKDD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_diadiem_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Anhxa();

        icback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DiadiemMain.this, MainActivity.class));
            }
        });
        uploadtoRV();

        edtTKDD.clearFocus();
        edtTKDD.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Query query = FirebaseDatabase.getInstance().getReference().child("Diadiem")
                        .orderByChild("title")  // Thay "title" bằng trường bạn muốn tìm kiếm
                        .startAt(newText)
                        .endAt(newText + "\uf8ff");

                // Tạo FirebaseRecyclerOptions với truy vấn tìm kiếm
                FirebaseRecyclerOptions<Amthuc> options =
                        new FirebaseRecyclerOptions.Builder<Amthuc>()
                                .setQuery(query, Amthuc.class)
                                .build();

                // Cập nhật adapter với tùy chọn mới
                diadiemAdapter = new DiadiemAdapter(options);
                diadiemAdapter.startListening();
                rv_dd.setAdapter(diadiemAdapter);

                return true;

            }
        });

    }
    private void Anhxa() {
        icback = findViewById(R.id.backpage);
        rv_dd = (RecyclerView) findViewById(R.id.rv_dd);
        rv_dd.setLayoutManager(new LinearLayoutManager(this));
        edtTKDD = findViewById(R.id.edtTKDD);
    }
    private void uploadtoRV() {
        FirebaseRecyclerOptions<Amthuc> options =
                new FirebaseRecyclerOptions.Builder<Amthuc>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Diadiem"), Amthuc.class)
                        .build();
        diadiemAdapter = new DiadiemAdapter(options);
        rv_dd.setAdapter(diadiemAdapter);
    }
    @Override
    protected void onStart() {
        super.onStart();
        diadiemAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        diadiemAdapter.stopListening();
    }
}