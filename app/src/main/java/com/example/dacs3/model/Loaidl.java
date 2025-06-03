package com.example.dacs3.model;

public class Loaidl {
    int id;
    String tenloai;
    String hinhanhloaidl;

    public Loaidl(int id, String tenloai, String hinhanhloaidl) {
        this.id = id;
        this.tenloai = tenloai;
        this.hinhanhloaidl = hinhanhloaidl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenloai() {
        return tenloai;
    }

    public void setTenloai(String tenloai) {
        this.tenloai = tenloai;
    }

    public String getHinhanhloaidl() {
        return hinhanhloaidl;
    }

    public void setHinhanhloaidl(String hinhanhloaidl) {
        this.hinhanhloaidl = hinhanhloaidl;
    }
}
