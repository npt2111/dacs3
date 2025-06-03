package com.example.dacs3.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dacs3.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {
    EditText edtEmailLo, edtPasswordLo;
    Button btn_signinLo;
    TextView signupLo;

    FirebaseDatabase dblogin = FirebaseDatabase.getInstance();
    DatabaseReference reLogin = dblogin.getReference("admin");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);


       Anhxa();

        //
        signupLo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentlogin = new Intent(Login.this, Register.class);
                startActivity(intentlogin);
                finish();
            }
        });
        //
        btn_signinLo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmailLo.getText().toString();
                String password = edtPasswordLo.getText().toString();

                if(email.isEmpty() || password.isEmpty()){
                    Toast.makeText(Login.this, "Vui loòng nhập tài khoản, mật khẩu!", Toast.LENGTH_SHORT).show();
                }else{
                    reLogin.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(email)){
                                String getPassword = snapshot.child(email).child("password").getValue(String.class);
                                String getUsername = snapshot.child(email).child("username").getValue(String.class);
                                if(getPassword.equals(password)){
                                    SharedPreferences sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("username", getUsername);
                                    editor.putBoolean("isAdmin", true);
                                    editor.apply();
                                    Toast.makeText(Login.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(Login.this, MainActivity.class));
                                }else{
                                    Toast.makeText(Login.this, "Sai tài khoản, mât khẩu!", Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                Toast.makeText(Login.this, "Sai tài khoản, mât khẩu!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }


            }
        });

    }

    private void Anhxa() {
        edtEmailLo = findViewById(R.id.emailLo);
        edtPasswordLo = findViewById(R.id.passwordLo);
        btn_signinLo = findViewById(R.id.btn_signinLo);
        signupLo = findViewById(R.id.signupLo);
    }




}