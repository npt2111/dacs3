package com.example.dacs3.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.dacs3.R;

public class SettingMain extends AppCompatActivity {
    TextView icback;
    private TextView tvWelcome;
    LinearLayout btn_trangchu, btn_yeuthich, btn_taikhoan, btn_caidat;
    Button btn_st_dg,btn_st_tk, btn_profile;
Button btn_st_dx, btn_st_dn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_setting_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Anhxa();
        SharedPreferences sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "Guest");
        boolean isAdmin = sharedPreferences.getBoolean("isAdmin", false);
        boolean isUser = sharedPreferences.getBoolean("isUser", false);

        // Display welcome message
        String welcomeMessage = (isAdmin ? "Admin" : "User") + ": " + username;
        tvWelcome.setText(welcomeMessage);


        btn_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingMain.this, TaikhoanMain.class));
            }
        });

        icback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (isAdmin || isUser) {
            btn_st_dn.setVisibility(View.GONE);
        }else{
            btn_st_dx.setVisibility(View.GONE);
        }

        btn_st_dx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
        btn_st_dn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingMain.this, SelectLogin.class));
            }
        });
        btn_st_dg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingMain.this, DanhgiaUD.class));
            }
        });

//        intent_insert.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intentInsert = new Intent(SettingMain.this, skThem.class);
//                startActivity(intentInsert);
//            }
//        });

//        //nav
//        btn_trangchu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent_trangchu = new Intent(SettingMain.this, MainActivity.class);
//                startActivity(intent_trangchu);
//            }
//        });
//        btn_yeuthich.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent_yeuthich = new Intent(SettingMain.this, yeuthichMain.class);
//                startActivity(intent_yeuthich);
//            }
//        });
//        btn_taikhoan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent_taikhoan = new Intent(SettingMain.this, TaikhoanMain.class);
//                startActivity(intent_taikhoan);
//            }
//        });
//        btn_caidat.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent_caidat = new Intent(SettingMain.this, SettingMain.class);
//                startActivity(intent_caidat);
//            }
//        });

    }
    private void logout(){
        SharedPreferences sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        // Navigate back to login activity
        Intent intent = new Intent(SettingMain.this, MainActivity.class);
        startActivity(intent);
        Toast.makeText(this, "Đã đăng xuất.", Toast.LENGTH_SHORT).show();
        finish();
    }
    private void Anhxa() {
        icback = findViewById(R.id.backpage);
        tvWelcome = findViewById(R.id.tv_welcome);



        btn_st_dx = findViewById(R.id.btn_st_dx);
        btn_st_dn = findViewById(R.id.btn_st_dn);
        btn_st_dg = findViewById(R.id.btn_st_dg);
        btn_profile = findViewById(R.id.btn_profile);
       // intent_insert = findViewById(R.id.intent_insert);
//        btn_trangchu = findViewById(R.id.btn_trangchu);
//        btn_yeuthich = findViewById(R.id.btn_yeuthich);
//        btn_taikhoan = findViewById(R.id.btn_taikhoan);
//        btn_caidat = findViewById(R.id.btn_caidat);
    }
}