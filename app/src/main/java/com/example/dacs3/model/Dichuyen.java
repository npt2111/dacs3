package com.example.dacs3.model;

public class Dichuyen {
    String loai,tenxe,sdt,diachi,pic;
    public Dichuyen(){

    }

    public Dichuyen(String loai, String tenxe, String sdt, String diachi, String pic) {
        this.loai = loai;
        this.tenxe = tenxe;
        this.sdt = sdt;
        this.diachi = diachi;
        this.pic = pic;
    }

    public String getLoai() {
        return loai;
    }

    public void setLoai(String loai) {
        this.loai = loai;
    }

    public String getTenxe() {
        return tenxe;
    }

    public void setTenxe(String tenxe) {
        this.tenxe = tenxe;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
}
