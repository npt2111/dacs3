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

public class skThemDiaDiem extends AppCompatActivity {
    Button btn_insert_dd, btn_chonanh_dd;
    TextView backpage;
    ImageView hinhanh_dd;
    EditText edt_insert_ten_dd,edt_insert_diachi_dd, edt_insert_gioithieu_dd;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri imageUri;

    FirebaseStorage dbImg = FirebaseStorage.getInstance();
    StorageReference storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sk_them_dia_diem);
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
        btn_insert_dd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                uploadimg();

            }
        });

        btn_chonanh_dd.setOnClickListener(new View.OnClickListener() {
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
                        Toast.makeText(skThemDiaDiem.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        } else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            hinhanh_dd.setImageURI(imageUri);
            // Do something with the selected image, e.g., display it in an ImageView
        }
    }
    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
    private void uploadImageUrlToDatabase(String pic) {
        String title = edt_insert_ten_dd.getText().toString();
        String location = edt_insert_diachi_dd.getText().toString();
        String description = edt_insert_gioithieu_dd.getText().toString();

        Float score = 0.0F;
        Amthuc amthuc = new Amthuc(title,location,description,pic,score);

        if(title.isEmpty() || location.isEmpty() || description.isEmpty()){
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
        }else{
            FirebaseDatabase dbDD = FirebaseDatabase.getInstance();
            DatabaseReference referenceDD = dbDD.getReference("Diadiem");
            storageReference = FirebaseStorage.getInstance().getReference("images");
            referenceDD.child(title).setValue(amthuc).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    edt_insert_ten_dd.setText("");
                    edt_insert_diachi_dd.setText("");
                    edt_insert_gioithieu_dd.setText("");
                    hinhanh_dd.setImageURI(null);
                    Toast.makeText(skThemDiaDiem.this, "Nhập thành công!", Toast.LENGTH_SHORT).show();

                }
            });
        }





    }



    private void Anhxa() {

        btn_insert_dd = findViewById(R.id.btn_insert_dd);
        backpage = findViewById(R.id.backpage);
        edt_insert_ten_dd = findViewById(R.id.edt_insert_ten_dd);
        edt_insert_diachi_dd = findViewById(R.id.edt_insert_diachi_dd);
        edt_insert_gioithieu_dd = findViewById(R.id.edt_insert_gioithieu_dd);
        hinhanh_dd = findViewById(R.id.hinhanh_dd);
        btn_chonanh_dd = findViewById(R.id.btn_chonanh_dd);
    }

}