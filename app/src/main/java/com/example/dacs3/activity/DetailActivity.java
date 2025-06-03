package com.example.dacs3.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.dacs3.R;
import com.example.dacs3.model.PopularDomain;

public class DetailActivity extends AppCompatActivity {
    private TextView titleTxt, locationTxt, descriptionTxt, scoreTxt;
    private PopularDomain item;
    private ImageView picImg, btnback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail);
        initView();
        setVariable();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setVariable() {
        item = (PopularDomain) getIntent().getSerializableExtra("object");
        titleTxt.setText(item.getTitle());
        scoreTxt.setText(""+(int) item.getScore());
        locationTxt.setText(item.getLocation());
        descriptionTxt.setText(item.getDescription());

        int drawableResId = getResources().getIdentifier(item.getPic(), "drawable", getPackageName());

        Glide.with(this)
                .load(drawableResId)
                .into(picImg);
    }

    private void initView() {
        titleTxt = findViewById(R.id.titleTxt);
        locationTxt = findViewById(R.id.locationTxt);
        scoreTxt = findViewById(R.id.scoreTxt);
        descriptionTxt = findViewById(R.id.descriptionTxt);
        picImg = findViewById(R.id.picImg);


        //
        btnback = findViewById(R.id.btnback);
    }
}