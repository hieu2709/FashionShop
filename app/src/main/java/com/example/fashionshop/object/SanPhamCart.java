package com.example.fashionshop.object;

import java.io.Serializable;

public class SanPhamCart implements Serializable {
    private String tensp;
    private int gia;
    private int soluong;
    private String hinhanh;
    private  int id;
    private String tentaikhoan;

    public SanPhamCart(){}

    public SanPhamCart(String tensp, int gia, int soluong, String hinhanh, int id, String tentaikhoan) {
        this.tensp = tensp;
        this.gia = gia;
        this.soluong = soluong;
        this.hinhanh = hinhanh;
        this.id = id;
        this.tentaikhoan=tentaikhoan;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTentaikhoan() {
        return tentaikhoan;
    }

    public void setTentaikhoan(String tentaikhoan) {
        this.tentaikhoan = tentaikhoan;
    }
}
