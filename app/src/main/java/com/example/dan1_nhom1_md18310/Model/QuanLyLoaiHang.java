package com.example.dan1_nhom1_md18310.Model;

public class QuanLyLoaiHang {
    private int id;

    private String tenLoai;

    public QuanLyLoaiHang() {
    }

    public QuanLyLoaiHang(int id, String tenLoai) {
        this.id = id;
        this.tenLoai = tenLoai;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenLoai() {
        return tenLoai;
    }

    public void setTenLoai(String tenLoai) {
        this.tenLoai = tenLoai;
    }
}
