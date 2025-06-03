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

import com.example.dacs3.R;
import com.example.dacs3.model.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Register extends AppCompatActivity {
  private   EditText edtEmailRe, edtPasswordRe, edtHovaten, edtBirthday, edtConPassword, usernameRe;
   private Button btn_signup;
 private    TextView signin;








    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);


       Anhxa();

        signin.setOnClickListener(v -> {
            Intent intentlogin = new Intent(Register.this, Login.class);
            startActivity(intentlogin);
        });
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                String hovaten = edtHovaten.getText().toString();
                String birthday = edtBirthday.getText().toString();
                String email = edtEmailRe.getText().toString();
                String password = edtPasswordRe.getText().toString();
                String confirmpassword = edtConPassword.getText().toString();
                String username = usernameRe.getText().toString();

                FirebaseDatabase dbAd = FirebaseDatabase.getInstance();
                DatabaseReference referenceAd = dbAd.getReference("admin");
                referenceAd.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.hasChild(username)){
                            usernameRe.setError("Username đã tồn tại!");
                            usernameRe.requestFocus();

                        }else{
                            if (email.isEmpty() || password.isEmpty() || username.isEmpty() || confirmpassword.isEmpty() || hovaten.isEmpty() || birthday.isEmpty()) {
                                Toast.makeText(Register.this, "Vui lòng điền đủ thông tin!", Toast.LENGTH_SHORT).show();
                            } else if (!email.matches(emailPattern)) {
                                edtEmailRe.setError("Nhập lại Email!");
                                edtEmailRe.requestFocus();
                            } else if (password.length() < 6) {
                                edtPasswordRe.setError("Mật khẩu phải dài ít nhất 6 ký tự!");                }
                            else if (!password.equals(confirmpassword)) {
                                edtConPassword.setError("Mật khẩu không khớp!");
                            }
                            else {


                                Users users= new  Users(hovaten,birthday,username, email, password);


                                referenceAd.child(username).setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(Register.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
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
      //  progressBar = findViewById(R.id.progressBar);
        edtEmailRe = findViewById(R.id.emailRe);
        edtPasswordRe = findViewById(R.id.passwordRe);
        btn_signup = findViewById(R.id.btn_signupRe);
        signin = findViewById(R.id.signinRe);
        edtHovaten = findViewById(R.id.hovatenRe);
        edtBirthday = findViewById(R.id.birthdayRe);
        edtConPassword = findViewById(R.id.conpasswordRe);
        usernameRe = findViewById(R.id.usernameRe);


    }



    private void sendUserToNextActivity() {
        Intent intent2 = new Intent(Register.this, Login.class);
        startActivity(intent2);
    }


}