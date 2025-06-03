package com.example.dacs3.activity;

import android.content.SharedPreferences;
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
import com.example.dacs3.adapter.YeuthichAdapter;
import com.example.dacs3.model.YeuThichModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class yeuthichMain extends AppCompatActivity {
    TextView icbackyt;
    RecyclerView rv_yt;
    YeuthichAdapter yeuthichAdapter;
//    LinearLayout btn_trangchuyt, btn_yeuthichyt, btn_taikhoanyt, btn_caidatyt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_yeuthich_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Anhxayt();
        uploadtoBinhLuan();

        icbackyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


//        //nav
//        btn_trangchuyt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent_trangchuyt = new Intent(yeuthichMain.this, MainActivity.class);
//                startActivity(intent_trangchuyt);
//            }
//        });
//        btn_yeuthichyt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent_yeuthichyt = new Intent(yeuthichMain.this, yeuthichMain.class);
//                startActivity(intent_yeuthichyt);
//            }
//        });
//        btn_taikhoanyt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent_taikhoanyt = new Intent(yeuthichMain.this, TaikhoanMain.class);
//                startActivity(intent_taikhoanyt);
//            }
//        });
//        btn_caidatyt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent_caidatyt = new Intent(yeuthichMain.this, SettingMain.class);
//                startActivity(intent_caidatyt);
//            }
//        });


    }
    private void Anhxayt() {
        icbackyt = findViewById(R.id.backpageyt);
        rv_yt = (RecyclerView) findViewById(R.id.rv_yt);
        rv_yt.setLayoutManager(new LinearLayoutManager(this));
//        btn_trangchuyt = findViewById(R.id.btn_trangchuyt);
//        btn_yeuthichyt = findViewById(R.id.btn_yeuthichyt);
//        btn_taikhoanyt = findViewById(R.id.btn_taikhoanyt);
//        btn_caidatyt = findViewById(R.id.btn_caidatyt);
    }
    private void uploadtoBinhLuan() {
        SharedPreferences sharetaikhoan = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        String username = sharetaikhoan.getString("username", "Guest");
        FirebaseRecyclerOptions<YeuThichModel> options =
                new FirebaseRecyclerOptions.Builder<YeuThichModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("yeuthich").orderByChild("username").equalTo(username), YeuThichModel.class)
                        .build();
        yeuthichAdapter = new YeuthichAdapter(options);
        rv_yt.setAdapter(yeuthichAdapter);
    }


    @Override
    protected void onStart() {
        super.onStart();
        yeuthichAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        yeuthichAdapter.stopListening();
    }

}