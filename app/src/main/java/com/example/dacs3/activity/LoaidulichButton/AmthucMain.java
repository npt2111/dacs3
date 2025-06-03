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
import com.example.dacs3.adapter.rvAdapter;
import com.example.dacs3.model.Amthuc;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class AmthucMain extends AppCompatActivity {
    TextView icback;
    RecyclerView rv_at;
    rvAdapter rvAdapter;
    FloatingActionButton btn_addnew_at;
    SearchView edtTKAT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_amthuc_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Anhxa();

        icback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AmthucMain.this, MainActivity.class));
            }
        });

        uploadtoRV();


        edtTKAT.clearFocus();
        edtTKAT.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Query query = FirebaseDatabase.getInstance().getReference().child("Amthuc")
                        .orderByChild("title")  // Thay "title" bằng trường bạn muốn tìm kiếm
                        .startAt(newText)
                        .endAt(newText + "\uf8ff");

                // Tạo FirebaseRecyclerOptions với truy vấn tìm kiếm
                FirebaseRecyclerOptions<Amthuc> options =
                        new FirebaseRecyclerOptions.Builder<Amthuc>()
                                .setQuery(query, Amthuc.class)
                                .build();

                // Cập nhật adapter với tùy chọn mới
                rvAdapter = new rvAdapter(options);
                rvAdapter.startListening();
                rv_at.setAdapter(rvAdapter);

                return true;

            }
        });


    }

    private void uploadtoRV() {
        FirebaseRecyclerOptions<Amthuc> options =
                new FirebaseRecyclerOptions.Builder<Amthuc>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Amthuc"), Amthuc.class)
                        .build();
                rvAdapter = new rvAdapter(options);
                rv_at.setAdapter(rvAdapter);
    }


    @Override
    protected void onStart() {
        super.onStart();
        rvAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        rvAdapter.stopListening();
    }




    private void Anhxa() {
        icback = findViewById(R.id.backpage);
        rv_at = (RecyclerView) findViewById(R.id.rv_at);
        rv_at.setLayoutManager(new LinearLayoutManager(this));
        edtTKAT = findViewById(R.id.edtTKAT);

     //   btn_addnew_at = (FloatingActionButton) findViewById(R.id.btn_addnew_at);
    }

}