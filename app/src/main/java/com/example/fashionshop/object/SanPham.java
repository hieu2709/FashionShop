package com.example.fashionshop.object;

import java.io.Serializable;

public class SanPham implements Serializable{
    private String tensp;
    private int gia;
    private String loaisp;
    private String mota;
    private int soluong;
    private String hinhanh;
    private  int id;

   public SanPham(){}

    public SanPham(int id,String tensp, int gia, String loaisp, String mota, int soluong, String hinhanh) {
       this.id =id;
        this.tensp = tensp;
        this.gia = gia;
        this.loaisp = loaisp;
        this.mota = mota;
        this.soluong = soluong;
        this.hinhanh = hinhanh;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public String getLoaisp() {
        return loaisp;
    }

    public void setLoaisp(String loaisp) {
        this.loaisp = loaisp;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

}
