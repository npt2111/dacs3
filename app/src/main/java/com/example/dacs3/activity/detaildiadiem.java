package com.example.dacs3.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.dacs3.R;
import com.example.dacs3.activity.LoaidulichButton.DiadiemMain;
import com.example.dacs3.activity.LoaidulichButton.users.DiadiemUser;
import com.example.dacs3.adapter.BinhluanAdapter;
import com.example.dacs3.model.Danhgia;
import com.example.dacs3.model.YeuThichModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class detaildiadiem extends AppCompatActivity {
    ImageView backpage,btn_ytDD;
    private RatingBar ScoreDD;
    private Button btn_dgDD;
    private EditText edtbinhluanDD;
    private DatabaseReference databaseReference;
    RecyclerView rv_dgdd;
    BinhluanAdapter binhluanAdapter;
    TextView scoreView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detaildiadiem);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ImageView imageView = findViewById(R.id.picImgDd);
        TextView titleView = findViewById(R.id.titleDd);
        TextView locationView = findViewById(R.id.locationDd);
        TextView descriptionView = findViewById(R.id.descriptionDd);
        scoreView = findViewById(R.id.scoreDd);


        btn_ytDD= (ImageView) findViewById(R.id.btn_ytDD);
        btn_ytDD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharetaikhoan = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
                boolean isAdmin = sharetaikhoan.getBoolean("isAdmin", false);
                boolean isUser = sharetaikhoan.getBoolean("isUser", false);
                if (isAdmin || isUser){
                    skyeuthich();
                }else{
                    Toast.makeText(detaildiadiem.this, "Đăng nhập để thực hiện!", Toast.LENGTH_SHORT).show();
                }
            }
        });


        ScoreDD = findViewById(R.id.ScoreDD);
        edtbinhluanDD = findViewById(R.id.edtbinhluanDD);
        btn_dgDD = findViewById(R.id.btn_dgDD);

        rv_dgdd = (RecyclerView) findViewById(R.id.rv_dgdd);
        rv_dgdd.setLayoutManager(new LinearLayoutManager(this));

        databaseReference = FirebaseDatabase.getInstance().getReference("danhgiadiadiem");
        btn_dgDD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharetaikhoan = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
                boolean isAdmin = sharetaikhoan.getBoolean("isAdmin", false);
                boolean isUser = sharetaikhoan.getBoolean("isUser", false);
                if (isAdmin || isUser){
                    submitRating();
                }else{
                    ScoreDD.setRating(0);
                    edtbinhluanDD.setText("");
                    Toast.makeText(detaildiadiem.this, "Đăng nhập để thực hiện!", Toast.LENGTH_SHORT).show();

                }
            }
        });



        backpage = findViewById(R.id.btn_backDd);
        backpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharetaikhoan = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
                boolean isAdmin = sharetaikhoan.getBoolean("isAdmin", false);
                boolean isUser = sharetaikhoan.getBoolean("isUser", false);
                if(isAdmin){
                    startActivity(new Intent(detaildiadiem.this, DiadiemMain.class));

                }else {
                    startActivity(new Intent(detaildiadiem.this, DiadiemUser.class));
                }
            }
        });

        calculateAverageScore();
        uploadtoBinhLuan();
        Intent intent = getIntent();
        if (intent != null) {
            String pic = intent.getStringExtra("pic");
            String title = intent.getStringExtra("title");
            String location = intent.getStringExtra("location");
            String description = intent.getStringExtra("description");
            String score = intent.getStringExtra("score");

            // Sử dụng Glide để tải hình ảnh từ URL hoặc từ tài nguyên nội bộ
            Glide.with(this)
                    .load(pic)
                    .into(imageView);

            titleView.setText(title);
            locationView.setText(location);
            descriptionView.setText(description);
            scoreView.setText(score);
        }

    }



    private void skyeuthich(){
        Intent intent = getIntent();
        String pic = intent.getStringExtra("pic");
        String title = intent.getStringExtra("title");
        String location = intent.getStringExtra("location");
        String description = intent.getStringExtra("description");
        String scoreStr = intent.getStringExtra("score");
        float score = Float.parseFloat(scoreStr);



        SharedPreferences sharetaikhoan = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        String username = sharetaikhoan.getString("username", "Guest");

        YeuThichModel yeuThichModel = new YeuThichModel(username,title,location,description,pic,score);


        FirebaseDatabase dbYT = FirebaseDatabase.getInstance();
        DatabaseReference referenceYT = dbYT.getReference("yeuthich");
        String idYT = referenceYT.push().getKey();
        referenceYT.child(idYT).setValue(yeuThichModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                Toast.makeText(detaildiadiem.this, "Đã thêm vào danh sách yêu thích", Toast.LENGTH_SHORT).show();

            }
        });


    }

    private void submitRating() {
        float score = ScoreDD.getRating();

        // Kiểm tra nếu rating hợp lệ
        if (score == 0) {
            Toast.makeText(detaildiadiem.this, "Vui lòng chọn số sao để đánh giá", Toast.LENGTH_SHORT).show();
            return;
        }
        SharedPreferences sharetaikhoan = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        String username = sharetaikhoan.getString("username", "Guest");
        String binhluan = edtbinhluanDD.getText().toString();
        Intent intent2 = getIntent();
        String title = intent2.getStringExtra("title");

        Danhgia danhgia = new Danhgia(title,binhluan,username,score);


        // Tạo một ID duy nhất cho mỗi đánh giá
        String idScore = databaseReference.push().getKey();


        // Lưu đánh giá vào Firebase
        databaseReference.child(idScore).setValue(danhgia).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(detaildiadiem.this, "Đánh giá thành công", Toast.LENGTH_SHORT).show();
                ScoreDD.setRating(0);
                edtbinhluanDD.setText("");
                recreate();
            } else {
                Toast.makeText(detaildiadiem.this, "Đánh giá thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void uploadtoBinhLuan() {
        Intent intent2 = getIntent();
        String title = intent2.getStringExtra("title");
        FirebaseRecyclerOptions<Danhgia> options =
                new FirebaseRecyclerOptions.Builder<Danhgia>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("danhgiadiadiem").orderByChild("title").equalTo(title), Danhgia.class)
                        .build();
        binhluanAdapter = new BinhluanAdapter(options);
        rv_dgdd.setAdapter(binhluanAdapter);
    }


    @Override
    protected void onStart() {
        super.onStart();
        binhluanAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        binhluanAdapter.stopListening();
    }
    private void calculateAverageScore() {
        Intent intent3 = getIntent();
        String title = intent3.getStringExtra("title");
        String pic = intent3.getStringExtra("pic");
        String location = intent3.getStringExtra("location");
        String description = intent3.getStringExtra("description");
        FirebaseDatabase.getInstance().getReference().child("danhgiadiadiem").orderByChild("title").equalTo(title).addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int totalScore = 0;
                int count = 0;
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Danhgia danhgia = dataSnapshot.getValue(Danhgia.class);
                    if (danhgia != null) {
                        totalScore += danhgia.getScore();
                        count++;
                    }
                }
                if (count > 0) {
                    float average = (float) totalScore / count;
                    scoreView.setText(String.format("%.1f", average));
                    float score = (float) average;

                    // Update data in Firebase
                    Map<String, Object> map2 = new HashMap<>();
                    map2.put("title", title);
                    map2.put("location", location);
                    map2.put("description", description);
                    map2.put("pic", pic);
                    map2.put("score", score);

                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Diadiem");

                    Query query = databaseReference.orderByChild("title").equalTo(title);
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                // Update the found child with the new data
                                snapshot.getRef().updateChildren(map2)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                // Handle success, e.g., dismiss dialog
                                                Log.d("FirebaseUpdate", "Update successful");
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                // Handle failure
                                                Log.e("FirebaseUpdate", "Update failed", e);
                                            }
                                        });
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            // Handle error
                            Log.e("FirebaseUpdate", "Query cancelled", databaseError.toException());
                        }
                    });

                } else {
                    scoreView.setText("0");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}