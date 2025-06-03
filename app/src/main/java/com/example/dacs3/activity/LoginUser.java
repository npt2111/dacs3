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
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.dacs3.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginUser extends AppCompatActivity {
    EditText edtusernameLo, edtPasswordLo;
    Button btn_signinLo;
    TextView signupLo;

    FirebaseDatabase dblogin = FirebaseDatabase.getInstance();
    DatabaseReference reLogin = dblogin.getReference("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_user);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Anhxa();
        signupLo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentlogin = new Intent(LoginUser.this, RegisterUser.class);
                startActivity(intentlogin);
                finish();
            }
        });
        //
        btn_signinLo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtusernameLo.getText().toString();
                String password = edtPasswordLo.getText().toString();

                if(email.isEmpty() || password.isEmpty()){
                    Toast.makeText(LoginUser.this, "Vui loòng nhập tài khoản, mật khẩu!", Toast.LENGTH_SHORT).show();
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
                                    editor.putBoolean("isUser", true);
                                    editor.apply();
                                    Toast.makeText(LoginUser.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(LoginUser.this, MainActivity.class));
                                }else{
                                    Toast.makeText(LoginUser.this, "Sai tài khoản, mât khẩu!", Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                Toast.makeText(LoginUser.this, "Sai tài khoản, mât khẩu!", Toast.LENGTH_SHORT).show();
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
            edtusernameLo = findViewById(R.id.usernameUsLo);
            edtPasswordLo = findViewById(R.id.passwordUsLo);
            btn_signinLo = findViewById(R.id.btn_signinUsLo);
            signupLo = findViewById(R.id.signupUsLo);

    }
}