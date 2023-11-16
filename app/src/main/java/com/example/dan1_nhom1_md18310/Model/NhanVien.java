package com.example.dan1_nhom1_md18310.Model;

public class NhanVien {
    private int maTV;
    private String hoTen;
    private String soDT;
    private double luong;

    public int getMaTV() {
        return maTV;
    }

    public void setMaTV(int maTV) {
        this.maTV = maTV;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public double getLuong() {
        return luong;
    }

    public void setLuong(double luong) {
        this.luong = luong;
    }
// Constructors, getters, and setters

    public NhanVien(int maTV, String hoTen, String soDT, double luong) {
        this.maTV = maTV;
        this.hoTen = hoTen;
        this.soDT = soDT;
        this.luong = luong;
    }

    public NhanVien(String hoTen, String soDT, double luong) {
        this.hoTen = hoTen;
        this.soDT = soDT;
        this.luong = luong;
    }

    // Getters and setters

    public String getSoDT() {
        return soDT;
    }

    public void setSoDT(String soDT) {
        this.soDT = soDT;
    }

    // Other methods
}