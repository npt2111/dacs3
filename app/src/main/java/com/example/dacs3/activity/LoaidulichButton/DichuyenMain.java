package com.example.dacs3.activity.LoaidulichButton;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import com.example.dacs3.adapter.DichuyeAdapter;
import com.example.dacs3.model.Dichuyen;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class DichuyenMain extends AppCompatActivity {
    TextView icback;
    RecyclerView rv_dc;
    DichuyeAdapter dichuyeAdapter;
    SearchView edtTKDC;
    Button btn_taxi, btn_bus, btn_thuexe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dichuyen_main);
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
                finish();
            }
        });


        edtTKDC.clearFocus();
        edtTKDC.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Query query = FirebaseDatabase.getInstance().getReference().child("Dichuyen")
                        .orderByChild("tenxe")  // Thay "title" bằng trường bạn muốn tìm kiếm
                        .startAt(newText)
                        .endAt(newText + "\uf8ff");

                // Tạo FirebaseRecyclerOptions với truy vấn tìm kiếm
                FirebaseRecyclerOptions<Dichuyen> options =
                        new FirebaseRecyclerOptions.Builder<Dichuyen>()
                                .setQuery(query, Dichuyen.class)
                                .build();

                // Cập nhật adapter với tùy chọn mới
                dichuyeAdapter = new DichuyeAdapter(options);
                dichuyeAdapter.startListening();
                rv_dc.setAdapter(dichuyeAdapter);

                return true;

            }
        });


        //skbutton
        btn_taxi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Query query = FirebaseDatabase.getInstance().getReference().child("Dichuyen")
                        .orderByChild("loai")
                        .startAt("Taxi")
                        .endAt("Taxi" + "\uf8ff");

                FirebaseRecyclerOptions<Dichuyen> options =
                        new FirebaseRecyclerOptions.Builder<Dichuyen>()
                                .setQuery(query, Dichuyen.class)
                                .build();

                dichuyeAdapter = new DichuyeAdapter(options);
                dichuyeAdapter.startListening();
                rv_dc.setAdapter(dichuyeAdapter);
            }
        });
        btn_bus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Query query = FirebaseDatabase.getInstance().getReference().child("Dichuyen")
                        .orderByChild("loai")
                        .startAt("Xe buýt")
                        .endAt("Xe buýt" + "\uf8ff");

                FirebaseRecyclerOptions<Dichuyen> options =
                        new FirebaseRecyclerOptions.Builder<Dichuyen>()
                                .setQuery(query, Dichuyen.class)
                                .build();

                dichuyeAdapter = new DichuyeAdapter(options);
                dichuyeAdapter.startListening();
                rv_dc.setAdapter(dichuyeAdapter);
            }
        });
        btn_thuexe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Query query = FirebaseDatabase.getInstance().getReference().child("Dichuyen")
                        .orderByChild("loai")
                        .startAt("Thuê xe máy-oto")
                        .endAt("Thuê xe máy-oto" + "\uf8ff");

                FirebaseRecyclerOptions<Dichuyen> options =
                        new FirebaseRecyclerOptions.Builder<Dichuyen>()
                                .setQuery(query, Dichuyen.class)
                                .build();

                dichuyeAdapter = new DichuyeAdapter(options);
                dichuyeAdapter.startListening();
                rv_dc.setAdapter(dichuyeAdapter);
            }
        });


    }
    private void Anhxa() {
        icback = findViewById(R.id.backpage);
        rv_dc = (RecyclerView) findViewById(R.id.rv_dc);
        rv_dc.setLayoutManager(new LinearLayoutManager(this));
        edtTKDC = findViewById(R.id.edtTKDC);
        btn_taxi = findViewById(R.id.btn_taxi);
        btn_bus = findViewById(R.id.btn_bus);
        btn_thuexe = findViewById(R.id.btn_thuexe);

    }
    private void uploadtoRV() {
        FirebaseRecyclerOptions<Dichuyen> opdc =
                new FirebaseRecyclerOptions.Builder<Dichuyen>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Dichuyen"), Dichuyen.class)
                        .build();
        dichuyeAdapter = new DichuyeAdapter(opdc);
        rv_dc.setAdapter(dichuyeAdapter);
    }


    @Override
    protected void onStart() {
        super.onStart();
        dichuyeAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        dichuyeAdapter.stopListening();
    }

}