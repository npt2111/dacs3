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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.dacs3.R;
import com.example.dacs3.model.Dichuyen;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class skThemDC extends AppCompatActivity {
    Button btn_insert_amthuc, btn_chonanh;
    TextView backpage;
    ImageView hinhanh_amthuc;
    EditText edt_insert_ten_amthuc,edt_insert_diachi_amthuc, edt_insert_sdt_dc;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri imageUri;
    private RadioGroup radioGroup;
    String selectedOption;
    private StorageReference storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sk_them_dc);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Anhxa();
        sukien();
    }
    private void Anhxa() {

        btn_insert_amthuc = findViewById(R.id.btn_insert_DC);
        backpage = findViewById(R.id.backpage);
        edt_insert_ten_amthuc = findViewById(R.id.edt_insert_ten_dichuyen);
        edt_insert_diachi_amthuc = findViewById(R.id.edt_insert_diachi_dichuyen);
        edt_insert_sdt_dc = findViewById(R.id.edt_insert_sdt_dc);
        hinhanh_amthuc = findViewById(R.id.hinhanh_DC);
        btn_chonanh = findViewById(R.id.btn_chonanhDC);
        radioGroup = findViewById(R.id.radioGroup);
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
                        Toast.makeText(skThemDC.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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

        int selectedId = radioGroup.getCheckedRadioButtonId();
        if (selectedId != -1) {
            RadioButton selectedRadioButton = findViewById(selectedId);
            String selectedOption = selectedRadioButton.getText().toString();
            String loai = selectedOption;
            String tenxe = edt_insert_ten_amthuc.getText().toString();
            String sdt = edt_insert_diachi_amthuc.getText().toString();
            String  diachi = edt_insert_sdt_dc.getText().toString();
            Dichuyen dichuyen = new Dichuyen(loai,tenxe,diachi,sdt,pic);

            if(loai.isEmpty() || tenxe.isEmpty() || sdt.isEmpty() || diachi.isEmpty()){
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            }else{

                FirebaseDatabase dbAT = FirebaseDatabase.getInstance();
                DatabaseReference referenceAT = dbAT.getReference("Dichuyen");
                storageReference = FirebaseStorage.getInstance().getReference("images");

                referenceAT.child(tenxe).setValue(dichuyen).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        radioGroup.clearCheck();
                        edt_insert_ten_amthuc.setText("");
                        edt_insert_diachi_amthuc.setText("");
                        edt_insert_sdt_dc.setText("");
                        hinhanh_amthuc.setImageURI(null);
                        Toast.makeText(skThemDC.this, "Nhập thành công!", Toast.LENGTH_SHORT).show();

                    }
                });

            }


        } else {
            Toast.makeText(skThemDC.this, "Vui lòng chọn một tùy chọn", Toast.LENGTH_SHORT).show();
        }



    }



}