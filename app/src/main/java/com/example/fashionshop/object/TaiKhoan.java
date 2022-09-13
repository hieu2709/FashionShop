package com.example.fashionshop.object;

import java.io.Serializable;

public class TaiKhoan implements Serializable {
    private String tentaikhoan;
    private String matkhau;
    private String tennguoidung;
    private String sdt ;

    public TaiKhoan() {
    }

    public TaiKhoan(String tentaikhoan, String matkhau, String tennguoidung, String sdt) {
        this.tentaikhoan = tentaikhoan;
        this.matkhau = matkhau;
        this.tennguoidung = tennguoidung;
        this.sdt = sdt ;

    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getTentaikhoan() {
        return tentaikhoan;
    }

    public void setTentaikhoan(String tentaikhoan) {
        this.tentaikhoan = tentaikhoan;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }

    public String getTennguoidung() {
        return tennguoidung;
    }

    public void setTennguoidung(String tennguoidung) {
        this.tennguoidung = tennguoidung;
    }

    @Override
    public String toString() {
        return "TaiKhoan{" +
                "tentaikhoan='" + tentaikhoan + '\'' +
                ", matkhau='" + matkhau + '\'' +
                ", tennguoidung='" + tennguoidung + '\'' +
                ", sdt='" + sdt + '\'' +
                '}';
    }
}



