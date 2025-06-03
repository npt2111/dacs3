package com.example.dacs3.activity.sukien;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.dacs3.R;
import com.example.dacs3.model.Amthuc;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class skThem extends AppCompatActivity {
    Button btn_insert_amthuc, btn_chonanh;
    TextView backpage;
    ImageView  hinhanh_amthuc;
    EditText edt_insert_ten_amthuc,edt_insert_diachi_amthuc, edt_insert_gioithieu_amthuc;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri imageUri;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sk_them);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Anhxa();
        sukien();
    }

    private void sukien() {
        backpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_insert_amthuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                uploadimg();

            }
        });

        btn_chonanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chonanh();
            }
        });


    }
    private void chonanh(){

        Intent intentchonanh = new Intent();
        intentchonanh.setType("image/*");
        intentchonanh.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intentchonanh, PICK_IMAGE_REQUEST);
    }
    private void uploadimg(){
        if (imageUri != null) {
            // Thay đổi `images/` thành thư mục bạn muốn lưu trữ trong Firebase Storage
            StorageReference fileReference = FirebaseStorage.getInstance().getReference("images")
                    .child(System.currentTimeMillis() + "." + getFileExtension(imageUri));

            fileReference.putFile(imageUri)
                    .addOnSuccessListener(taskSnapshot -> fileReference.getDownloadUrl().addOnSuccessListener(uri -> {
                        // Lưu URL của ảnh vào Firebase Realtime Database
                        String pic = uri.toString();
                        uploadImageUrlToDatabase(pic);
                    }))
                    .addOnFailureListener(e -> {
                        // Xử lý lỗi nếu việc tải ảnh thất bại
                        Toast.makeText(skThem.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        } else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            hinhanh_amthuc.setImageURI(imageUri);
            // Do something with the selected image, e.g., display it in an ImageView
        }
    }
    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
    private void uploadImageUrlToDatabase(String pic) {

        String title = edt_insert_ten_amthuc.getText().toString();
        String location = edt_insert_diachi_amthuc.getText().toString();
        String description = edt_insert_gioithieu_amthuc.getText().toString();
        Float score = Float.valueOf(0);
        Amthuc amthuc = new Amthuc(title,location,description,pic,score);

        if (title.isEmpty() || location.isEmpty() || description.isEmpty()){
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
        }else{
            FirebaseDatabase dbAT = FirebaseDatabase.getInstance();
            DatabaseReference referenceAT = dbAT.getReference("Amthuc");
            storageReference = FirebaseStorage.getInstance().getReference("images");

            String id = referenceAT.push().getKey();

            referenceAT.child(id).setValue(amthuc).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    edt_insert_ten_amthuc.setText("");
                    edt_insert_diachi_amthuc.setText("");
                    edt_insert_gioithieu_amthuc.setText("");
                    hinhanh_amthuc.setImageURI(null);
                    Toast.makeText(skThem.this, "Nhập thành công!", Toast.LENGTH_SHORT).show();

                }
            });
        }



    }

    private void Anhxa() {

        btn_insert_amthuc = findViewById(R.id.btn_insert_amthuc);
        backpage = findViewById(R.id.backpage);
        edt_insert_ten_amthuc = findViewById(R.id.edt_insert_ten_amthuc);
        edt_insert_diachi_amthuc = findViewById(R.id.edt_insert_diachi_amthuc);
        edt_insert_gioithieu_amthuc = findViewById(R.id.edt_insert_gioithieu_amthuc);
        hinhanh_amthuc = findViewById(R.id.hinhanh_amthuc);
        btn_chonanh = findViewById(R.id.btn_chonanh);
    }
}