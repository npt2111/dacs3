package com.example.dacs3.retrofit;

import android.database.Observable;

import com.example.dacs3.model.LoaidlModel;

import retrofit2.http.GET;

public interface ApiDuLich {
    @GET("loaidulich.php")
    Observable<LoaidlModel> getLoaidl();
}
