package com.example.dacs3.activity;

import android.content.Intent;
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
import com.example.dacs3.model.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterUser extends AppCompatActivity {
    private EditText edtEmail, edtPassword, edtHovaten, edtBirthday, edtConPassword, usernameUser;
    private Button btn_signup;
    private TextView signin;


    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register_user);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Anhxa();

        signin.setOnClickListener(v -> {
            Intent intentlogin = new Intent(RegisterUser.this, LoginUser.class);
            startActivity(intentlogin);
        });
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                String hovaten = edtHovaten.getText().toString();
                String birthday = edtBirthday.getText().toString();
                String email = edtEmail.getText().toString();
                String password = edtPassword.getText().toString();
                String confirmpassword = edtConPassword.getText().toString();
                String username = usernameUser.getText().toString();

                FirebaseDatabase dbUS = FirebaseDatabase.getInstance();
                DatabaseReference referenceAd = dbUS.getReference("users");
                referenceAd.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.hasChild(username)){
                            usernameUser.setError("Username đã tồn tại!");
                            usernameUser.requestFocus();

                        }else{
                            if (email.isEmpty() || password.isEmpty() || username.isEmpty() || confirmpassword.isEmpty() || hovaten.isEmpty() || birthday.isEmpty()) {
                                Toast.makeText(RegisterUser.this, "Vui lòng điền đủ thông tin!", Toast.LENGTH_SHORT).show();
                            } else if (!email.matches(emailPattern)) {
                                edtEmail.setError("Nhập lại Email!");
                                edtEmail.requestFocus();
                            } else if (password.length() < 6) {
                                edtPassword.setError("Mật khẩu phải dài ít nhất 6 ký tự!");                }
                            else if (!password.equals(confirmpassword)) {
                                edtConPassword.setError("Mật khẩu không khớp!");
                            }
                            else {


                                Users users= new  Users(hovaten,birthday,username, email, password);


                                referenceAd.child(username).setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(RegisterUser.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                                        sendUserToNextActivity();
                                    }
                                });




                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



            }
        });
    }
    private void Anhxa() {
        edtEmail= findViewById(R.id.emailUser);
        edtPassword = findViewById(R.id.passwordUser);
        btn_signup = findViewById(R.id.btn_signupUser);
        signin = findViewById(R.id.signinUser);
        edtHovaten = findViewById(R.id.hovatenUser);
        edtBirthday = findViewById(R.id.birthdayUser);
        edtConPassword = findViewById(R.id.conpasswordUser);
        usernameUser = findViewById(R.id.usernameUser);

    }
    private void sendUserToNextActivity() {
        Intent intent2 = new Intent(RegisterUser.this, LoginUser.class);
        startActivity(intent2);
    }

}