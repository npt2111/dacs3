package com.example.dacs3.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dacs3.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DanhgiaUD extends AppCompatActivity {
    private RatingBar ratingBar;
    private Button btnSubmitRating;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_danhgia_ud);

        // Ánh xạ các view
        ratingBar = findViewById(R.id.ratingBar);
        btnSubmitRating = findViewById(R.id.btnSubmitRating);

        // Khởi tạo Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("DanhgiaSanPham");

        // Thiết lập sự kiện click cho nút submit rating
        btnSubmitRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharetaikhoan = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
                boolean isAdmin = sharetaikhoan.getBoolean("isAdmin", false);
                boolean isUser = sharetaikhoan.getBoolean("isUser", false);
                if (isAdmin || isUser){
                    submitRating();
                }else{
                    Toast.makeText(DanhgiaUD.this, "Đăng nhập để thực hiện!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void submitRating() {
        float score = ratingBar.getRating();

        // Kiểm tra nếu rating hợp lệ
        if (score == 0) {
            Toast.makeText(DanhgiaUD.this, "Vui lòng chọn số sao để đánh giá", Toast.LENGTH_SHORT).show();
            return;
        }
        SharedPreferences sharetaikhoan = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        String username = sharetaikhoan.getString("username", "Guest");

        // Tạo một ID duy nhất cho mỗi đánh giá
        String idScore = databaseReference.push().getKey();

        // Tạo đối tượng rating
        Score userScore = new Score(score);

        // Lưu đánh giá vào Firebase
        databaseReference.child(username).setValue(userScore).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(DanhgiaUD.this, "Đánh giá thành công", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(DanhgiaUD.this, "Đánh giá thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Định nghĩa class Rating để ánh xạ dữ liệu
    public static class Score {
        public float score;

        public Score() {
            // Default constructor required for calls to DataSnapshot.getValue(Rating.class)
        }

        public Score(float score) {
            this.score = score;
        }
    }
}