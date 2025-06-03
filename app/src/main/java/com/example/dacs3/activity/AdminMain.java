package com.example.dacs3.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.dacs3.R;
import com.example.dacs3.activity.sukien.SkThemDuLich;
import com.example.dacs3.activity.sukien.skThem;
import com.example.dacs3.activity.sukien.skThemDC;
import com.example.dacs3.activity.sukien.skThemDiaDiem;

public class AdminMain extends AppCompatActivity {
    TextView icback;
    LinearLayout dm_amthuc, dm_dulich, dm_dichuyen, dm_diadiem, dm_nguoidung;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Anhxa();

        icback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        dm_amthuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent int_at = new Intent(AdminMain.this, skThem.class);
                startActivity(int_at);
            }
        });
        dm_dulich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent int_dl = new Intent(AdminMain.this, SkThemDuLich.class);
                startActivity(int_dl);
            }
        });
        dm_diadiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent int_dd = new Intent(AdminMain.this, skThemDiaDiem.class);
                startActivity(int_dd);
            }
        });
        dm_nguoidung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent int_nd = new Intent(AdminMain.this, qlyNguoidung.class);
                startActivity(int_nd);
            }
        });
        dm_dichuyen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminMain.this, skThemDC.class));
            }
        });

    }

    private void Anhxa() {
        icback = findViewById(R.id.backpage);
        dm_amthuc = findViewById(R.id.dm_amthuc);
        dm_dulich = findViewById(R.id.dm_dulich);
        dm_dichuyen = findViewById(R.id.dm_dichuyen);
        dm_diadiem = findViewById(R.id.dm_diadiem);
        dm_nguoidung = findViewById(R.id.dm_nguoidung);
    }
}