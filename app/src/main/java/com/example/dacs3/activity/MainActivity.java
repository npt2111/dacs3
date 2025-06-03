package com.example.dacs3.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dacs3.R;
import com.example.dacs3.activity.LoaidulichButton.AmthucMain;
import com.example.dacs3.activity.LoaidulichButton.DiadiemMain;
import com.example.dacs3.activity.LoaidulichButton.DichuyenMain;
import com.example.dacs3.activity.LoaidulichButton.DulichMain;
import com.example.dacs3.activity.LoaidulichButton.users.AmthucUser;
import com.example.dacs3.activity.LoaidulichButton.users.DiadiemUser;
import com.example.dacs3.activity.LoaidulichButton.users.DichuyenUser;
import com.example.dacs3.activity.LoaidulichButton.users.DulichUser;
import com.example.dacs3.adapter.CategoryAdapter;
import com.example.dacs3.adapter.PupolarAdapter;
import com.example.dacs3.model.Amthuc;
import com.example.dacs3.model.CategoryDomain;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    ViewFlipper viewFlipper;
   // RecyclerView recyclerViewmhc;
    NavigationView navigationView;
    ListView listViewmhc;
    DrawerLayout drawerLayou;

    PupolarAdapter pupolarAdapter;
    // từ viewflipper xuống
    private RecyclerView.Adapter adapterPopular, adapterCat;
    private  RecyclerView recyclerViewPopular, recyclerViewCategory;

    //cardview
    CardView Camthuc, Cdulich, Cdichuyen, Cdiadiem;

    //see all
    TextView saDl,saDD,saAT,saDC;

    //nav
    LinearLayout btn_trangchu, btn_yeuthich, btn_taikhoan, btn_caidat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Anhxa();
      //  ActionBar();
        ActionViewFlipper();

        //recyclerview
        initRecyclerView();

//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("message");
//
//        myRef.setValue("Hello, World!");


        //Sự kiện


        //sự kiện cardview
        Camthuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharetaikhoan = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
                boolean isAdmin = sharetaikhoan.getBoolean("isAdmin", false);
                boolean isUser = sharetaikhoan.getBoolean("isUser", false);
                if(isAdmin){
                    Intent int_Camthuc = new Intent(MainActivity.this, AmthucMain.class);
                    startActivity(int_Camthuc);
                }else {
                    startActivity(new Intent(MainActivity.this, AmthucUser.class));
                }

            }
        });
        Cdulich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharetaikhoan = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
                boolean isAdmin = sharetaikhoan.getBoolean("isAdmin", false);
                boolean isUser = sharetaikhoan.getBoolean("isUser", false);
                if(isAdmin){
                    Intent int_Cdulich = new Intent(MainActivity.this, DulichMain.class);
                    startActivity(int_Cdulich);
                }else {
                    startActivity(new Intent(MainActivity.this, DulichUser.class));
                }

            }
        });
        Cdichuyen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharetaikhoan = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
                boolean isAdmin = sharetaikhoan.getBoolean("isAdmin", false);
                boolean isUser = sharetaikhoan.getBoolean("isUser", false);
                if(isAdmin){
                    Intent int_Cdichuyen = new Intent(MainActivity.this, DichuyenMain.class);
                    startActivity(int_Cdichuyen);
                }else {
                    startActivity(new Intent(MainActivity.this, DichuyenUser.class));
                }

            }
        });
        Cdiadiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharetaikhoan = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
                boolean isAdmin = sharetaikhoan.getBoolean("isAdmin", false);
                boolean isUser = sharetaikhoan.getBoolean("isUser", false);
                if(isAdmin){
                    Intent int_Cdiadiem = new Intent(MainActivity.this, DiadiemMain.class);
                    startActivity(int_Cdiadiem);
                }else {
                    startActivity(new Intent(MainActivity.this, DiadiemUser.class));
                }

            }
        });

        //sự kiện content
        saDl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharetaikhoan = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
                boolean isAdmin = sharetaikhoan.getBoolean("isAdmin", false);
                boolean isUser = sharetaikhoan.getBoolean("isUser", false);
                if(isAdmin){
                    Intent int_Cdulich2 = new Intent(MainActivity.this, DulichMain.class);
                    startActivity(int_Cdulich2);
                }else {
                    startActivity(new Intent(MainActivity.this, DulichUser.class));
                }
            }
        });
        saDD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharetaikhoan = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
                boolean isAdmin = sharetaikhoan.getBoolean("isAdmin", false);
                boolean isUser = sharetaikhoan.getBoolean("isUser", false);
                if(isAdmin){
                    Intent int_Cdiadiem2 = new Intent(MainActivity.this, DiadiemMain.class);
                    startActivity(int_Cdiadiem2);
                }else {
                    startActivity(new Intent(MainActivity.this, DiadiemUser.class));
                }
            }
        });
        saAT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharetaikhoan = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
                boolean isAdmin = sharetaikhoan.getBoolean("isAdmin", false);
                boolean isUser = sharetaikhoan.getBoolean("isUser", false);
                if(isAdmin){
                    Intent int_Camthuc2 = new Intent(MainActivity.this, AmthucMain.class);
                    startActivity(int_Camthuc2);
                }else {
                    startActivity(new Intent(MainActivity.this, AmthucUser.class));
                }
            }
        });
        saDC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent int_saDC = new Intent(MainActivity.this, DichuyenMain.class);
                startActivity(int_saDC);  SharedPreferences sharetaikhoan = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
                boolean isAdmin = sharetaikhoan.getBoolean("isAdmin", false);
                boolean isUser = sharetaikhoan.getBoolean("isUser", false);
                if(isAdmin){
                    Intent int_Cdichuyen2 = new Intent(MainActivity.this, DichuyenMain.class);
                    startActivity(int_Cdichuyen2);
                }else {
                    startActivity(new Intent(MainActivity.this, DichuyenUser.class));
                }
            }
        });


        //nav
        btn_trangchu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_trangchu = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent_trangchu);
            }
        });
        btn_yeuthich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharetaikhoan = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
                boolean isAdmin = sharetaikhoan.getBoolean("isAdmin", false);
                boolean isUser = sharetaikhoan.getBoolean("isUser", false);
                if(isAdmin || isUser){
                    Intent intent_yeuthich = new Intent(MainActivity.this, yeuthichMain.class);
                    startActivity(intent_yeuthich);
                }else {
                    startActivity(new Intent(MainActivity.this, YeuthichNoLogin.class));
                }

            }
        });
        btn_taikhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_taikhoan = new Intent(MainActivity.this, TaikhoanMain.class);
                startActivity(intent_taikhoan);
            }
        });
        btn_caidat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_caidat = new Intent(MainActivity.this, SettingMain.class);
                startActivity(intent_caidat);
            }
        });



        //OnCreate
    }

    private void ActionViewFlipper() {


        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);
        Animation animation_slide_in = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in_right);
        Animation animation_slide_out = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_out_right);
        viewFlipper.setInAnimation(animation_slide_in);
        viewFlipper.setOutAnimation(animation_slide_out);
    }


    //Ánh xạ
    private void Anhxa(){
        toolbar = findViewById(R.id.toolbarmhc);
        viewFlipper = findViewById(R.id.viewFlipper);
       // recyclerViewmhc = findViewById(R.id.recyclerview);
        navigationView = findViewById(R.id.nagigationview);
//        listViewmhc = findViewById(R.id.listviewmhc);
        drawerLayou = findViewById(R.id.drawerlayout);

        //see all
        saDl = findViewById(R.id.seeall_dulich);
        saDD = findViewById(R.id.seeall_diadiem);
        saAT = findViewById(R.id.seeall_amthuc);
        saDC = findViewById(R.id.seeall_dichuyen);


        //cardview
        Camthuc = findViewById(R.id.card_amthuc);
        Cdulich = findViewById(R.id.card_dulich);
        Cdichuyen = findViewById(R.id.card_dichuyen);
        Cdiadiem = findViewById(R.id.card_diadiem);

        btn_trangchu = findViewById(R.id.btn_trangchu);
        btn_yeuthich = findViewById(R.id.btn_yeuthich);
        btn_taikhoan = findViewById(R.id.btn_taikhoan);
        btn_caidat = findViewById(R.id.btn_caidat);
    }


    ////
//    private void ActionBar() {
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        toolbar.setNavigationIcon(R.drawable.ic_menu);
//        toolbar.setNavigationOnClickListener(v -> drawerLayou.openDrawer(GravityCompat.START));
//    }








   /////
    private void initRecyclerView(){
//du lich
        FirebaseRecyclerOptions<Amthuc> options =
                new FirebaseRecyclerOptions.Builder<Amthuc>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Dulich").limitToFirst(3), Amthuc.class)
                        .build();
        pupolarAdapter = new PupolarAdapter(options);

        recyclerViewPopular=findViewById(R.id.view_pop_dulich);
        recyclerViewPopular.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        pupolarAdapter.startListening();
        recyclerViewPopular.setAdapter(pupolarAdapter);

//dia diem
        FirebaseRecyclerOptions<Amthuc> options2 =
                new FirebaseRecyclerOptions.Builder<Amthuc>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Diadiem").limitToFirst(3), Amthuc.class)
                        .build();
        pupolarAdapter = new PupolarAdapter(options2);
        recyclerViewPopular=findViewById(R.id.view_pop_diadiem);
        recyclerViewPopular.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        pupolarAdapter.startListening();
        recyclerViewPopular.setAdapter(pupolarAdapter);


        FirebaseRecyclerOptions<Amthuc> options3 =
                new FirebaseRecyclerOptions.Builder<Amthuc>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Amthuc").limitToFirst(3), Amthuc.class)
                        .build();
        pupolarAdapter = new PupolarAdapter(options3);
        recyclerViewPopular=findViewById(R.id.view_pop_amthuc);
        recyclerViewPopular.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        pupolarAdapter.startListening();
        recyclerViewPopular.setAdapter(pupolarAdapter);


////di chuyen
        ArrayList<CategoryDomain> catlist = new ArrayList<>();
        catlist.add(new CategoryDomain("Tàu hỏa","ic_train"));
        catlist.add(new CategoryDomain("Xe buýt","ic_bus"));
        catlist.add(new CategoryDomain("Xe máy","ic_moto"));
        catlist.add(new CategoryDomain("Taxi","ic_taxi"));
        catlist.add(new CategoryDomain("Thuê xe tự lái","ic_car"));


        recyclerViewCategory=findViewById(R.id.view_cat);
        recyclerViewCategory.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        adapterCat=new CategoryAdapter(catlist);
        recyclerViewCategory.setAdapter(adapterCat);

    }



}