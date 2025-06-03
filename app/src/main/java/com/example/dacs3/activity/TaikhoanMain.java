package com.example.dacs3.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.dacs3.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TaikhoanMain extends AppCompatActivity {
    TextView icback;
    LinearLayout ln_admin;
   // LinearLayout btn_trangchu, btn_yeuthich, btn_taikhoan, btn_caidat;
   private TextView tv_showWelcome, pr_username, pr_email, pr_birthday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_taikhoan_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Anhxa();

        SharedPreferences sharetaikhoan = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        String username = sharetaikhoan.getString("username", "Guest");
        boolean isAdmin = sharetaikhoan.getBoolean("isAdmin", false);
        boolean isUser = sharetaikhoan.getBoolean("isUser", false);

        // Display welcome message
        if (isAdmin){
            String welcomeMessage =  (isAdmin ? "Admin" : "User") + ": " + username;
            tv_showWelcome.setText(welcomeMessage);

            FirebaseDatabase dbtaikhoan = FirebaseDatabase.getInstance();
            DatabaseReference reTK = dbtaikhoan.getReference("admin");
            reTK.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.hasChild(username)){
                        String getEmail = snapshot.child(username).child("email").getValue(String.class);
                        String getUsername = snapshot.child(username).child("hovaten").getValue(String.class);
                        String getbirthday = snapshot.child(username).child("birthday").getValue(String.class);

                        pr_username.setText(getUsername);
                        pr_email.setText(getEmail);
                        pr_birthday.setText(getbirthday);


                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }else if(isUser){
            String weluser =  (isUser ? "User" : "Admin") + ": " + username;
            tv_showWelcome.setText(weluser);

            FirebaseDatabase dbtaikhoanus = FirebaseDatabase.getInstance();
            DatabaseReference reTKuser = dbtaikhoanus.getReference("users");
            reTKuser.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.hasChild(username)){
                        String getEmail = snapshot.child(username).child("email").getValue(String.class);
                        String getUsername = snapshot.child(username).child("hovaten").getValue(String.class);
                        String getbirthday = snapshot.child(username).child("birthday").getValue(String.class);

                        pr_username.setText(getUsername);
                        pr_email.setText(getEmail);
                        pr_birthday.setText(getbirthday);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

//        Dialog dialog = new Dialog(this);
//        dialog.setContentView(R.layout.activity_taikhoan_main);

        if (!isAdmin) {
            ln_admin.setVisibility(View.GONE);
        }
        ln_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent int_admin = new Intent(TaikhoanMain.this, AdminMain.class);
                startActivity(int_admin);
            }
        });


        // Hiển thị Dialog
//        dialog.show();

        icback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }
    private void showAdminDialog() {
        // Tạo Dialog

    }
    private void Anhxa() {
        icback =(TextView) findViewById(R.id.backpage);
        ln_admin =(LinearLayout) findViewById(R.id.ln_admin);
        tv_showWelcome =(TextView) findViewById(R.id.tv_showWelcome);
        pr_username =(TextView) findViewById(R.id.pr_username);
        pr_email =(TextView) findViewById(R.id.pr_email);
        pr_birthday =(TextView) findViewById(R.id.pr_birthday);


    }
}