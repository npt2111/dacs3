package com.example.dacs3.activity.LoaidulichButton.users;

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
import com.example.dacs3.adapter.user.DichuyenUserAdapter;
import com.example.dacs3.model.Dichuyen;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class DichuyenUser extends AppCompatActivity {
    TextView icback;
    RecyclerView rv_dcUS;
    DichuyenUserAdapter dichuyenUserAdapter;
    SearchView edtTKDCUS;
    Button btn_taxiUS, btn_busUS, btn_thuexeUS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dichuyen_user);
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


        edtTKDCUS.clearFocus();
        edtTKDCUS.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
                dichuyenUserAdapter = new DichuyenUserAdapter(options);
                dichuyenUserAdapter.startListening();
                rv_dcUS.setAdapter(dichuyenUserAdapter);

                return true;

            }
        });


        //skbutton
        btn_taxiUS.setOnClickListener(new View.OnClickListener() {
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

                dichuyenUserAdapter = new DichuyenUserAdapter(options);
                dichuyenUserAdapter.startListening();
                rv_dcUS.setAdapter(dichuyenUserAdapter);
            }
        });
        btn_busUS.setOnClickListener(new View.OnClickListener() {
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

                dichuyenUserAdapter = new DichuyenUserAdapter(options);
                dichuyenUserAdapter.startListening();
                rv_dcUS.setAdapter(dichuyenUserAdapter);
            }
        });
        btn_thuexeUS.setOnClickListener(new View.OnClickListener() {
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

                dichuyenUserAdapter = new DichuyenUserAdapter(options);
                dichuyenUserAdapter.startListening();
                rv_dcUS.setAdapter(dichuyenUserAdapter);
            }
        });

    }
    private void Anhxa() {
        icback = findViewById(R.id.backpage);
        rv_dcUS = (RecyclerView) findViewById(R.id.rv_dcUS);
        rv_dcUS.setLayoutManager(new LinearLayoutManager(this));
        edtTKDCUS = findViewById(R.id.edtTKDCUS);
        btn_taxiUS = findViewById(R.id.btn_taxiUS);
        btn_busUS = findViewById(R.id.btn_busUS);
        btn_thuexeUS = findViewById(R.id.btn_thuexeUS);

    }
    private void uploadtoRV() {
        FirebaseRecyclerOptions<Dichuyen> opdc =
                new FirebaseRecyclerOptions.Builder<Dichuyen>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Dichuyen"), Dichuyen.class)
                        .build();
        dichuyenUserAdapter = new DichuyenUserAdapter(opdc);
        rv_dcUS.setAdapter(dichuyenUserAdapter);
    }


    @Override
    protected void onStart() {
        super.onStart();
        dichuyenUserAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        dichuyenUserAdapter.stopListening();
    }
}