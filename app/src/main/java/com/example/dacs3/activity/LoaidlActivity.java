package com.example.dacs3.activity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.dacs3.R;
import com.example.dacs3.adapter.Loaidulich;
import com.example.dacs3.model.Loaidl;
import com.example.dacs3.retrofit.ApiDuLich;
import com.example.dacs3.retrofit.RetrofitClient;
import com.example.dacs3.ultil.Utils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class LoaidlActivity extends AppCompatActivity {
    Loaidulich loaidulich;
    List<Loaidl> mangloaidl;
    ListView listViewloaidl;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiDuLich apiDuLich;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_loaidl);
        apiDuLich = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiDuLich.class);
        Anhxa();
        if(isConnected(this)){
            Toast.makeText(getApplicationContext(),"ok",Toast.LENGTH_LONG).show();
            getLoaidulich();
        }else{
            Toast.makeText(getApplicationContext(),"No Internet",Toast.LENGTH_LONG).show();
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void getLoaidulich() {
//        compositeDisposable.add(apiDuLich.getLoaidl()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(
//                        LoaidlModel -> {
//                            if(LoaidlModel.isSuccess()){
//                                Toast.makeText(getApplicationContext(), LoaidlModel.getResult().get(0).getTenloai(), Toast.LENGTH_LONG).show();
//                            }
//                        }
//                )
//        );
    }

    private void Anhxa() {
        listViewloaidl = findViewById(R.id.listViewloaidl);
        mangloaidl = new ArrayList<>();
        //khoi tao adapter
        loaidulich = new Loaidulich(getApplicationContext(), mangloaidl);
        listViewloaidl.setAdapter(loaidulich);
    }
    private  boolean isConnected (Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if(wifi != null && wifi.isConnected() || (mobile != null && mobile.isConnected())){
            return true;
        }else{
            return false;
        }
    }

}