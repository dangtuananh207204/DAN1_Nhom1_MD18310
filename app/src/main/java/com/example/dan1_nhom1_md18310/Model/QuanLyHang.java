package com.example.dan1_nhom1_md18310.Model;

import java.io.Serializable;

public class QuanLyHang implements Serializable {





private int maHang;

private String tenHang;
private String tenLoai;
private int giaHang;
private int maLoai;
private String RAM;
private String ROM;
private String Mau;
private int soLuong;

    public int getMaHang() {
        return maHang;
    }

    public void setMaHang(int maHang) {
        this.maHang = maHang;
    }

    public String getTenHang() {
        return tenHang;
    }

    public void setTenHang(String tenHang) {
        this.tenHang = tenHang;
    }

    public String getTenLoai() {
        return tenLoai;
    }

    public void setTenLoai(String tenLoai) {
        this.tenLoai = tenLoai;
    }

    public int getGiaHang() {
        return giaHang;
    }

    public void setGiaHang(int giaHang) {
        this.giaHang = giaHang;
    }

    public int getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(int maLoai) {
        this.maLoai = maLoai;
    }

    public String getRAM() {
        return RAM;
    }

    public void setRAM(String RAM) {
        this.RAM = RAM;
    }

    public String getROM() {
        return ROM;
    }

    public void setROM(String ROM) {
        this.ROM = ROM;
    }

    public String getMau() {
        return Mau;
    }

    public void setMau(String mau) {
        Mau = mau;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public QuanLyHang(int maHang, String tenHang, String tenLoai, int giaHang, String RAM, String ROM, String mau, int soLuong) {
        this.maHang = maHang;
        this.tenHang = tenHang;
        this.tenLoai = tenLoai;
        this.giaHang = giaHang;
        this.RAM = RAM;
        this.ROM = ROM;
        Mau = mau;
        this.soLuong = soLuong;
    }
}
