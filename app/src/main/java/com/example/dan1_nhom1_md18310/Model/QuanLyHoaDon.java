package com.example.dan1_nhom1_md18310.Model;

import java.io.Serializable;

public class QuanLyHoaDon implements Serializable {
    private int maHoaDon;
    private String tenHang;

    private int soLuong;
    private int gia;
    private int trangThaiHoaDon;

    private  String ngay;
    private String RAM;
    private String ROM;
    private String Mau;

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

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public QuanLyHoaDon() {
    }

    public QuanLyHoaDon(int maHoaDon, String tenHang, int soLuong, int gia, String ngay,  int trangThaiHoaDon,String RAM, String ROM, String mau) {
        this.maHoaDon = maHoaDon;
        this.tenHang = tenHang;
        this.soLuong = soLuong;
        this.gia = gia;
        this.trangThaiHoaDon = trangThaiHoaDon;
        this.ngay = ngay;
        this.RAM = RAM;
        this.ROM = ROM;
        Mau = mau;
    }

    public QuanLyHoaDon(int maHoaDon, String tenHang, int soLuong, int gia, String ngay, int trangThaiHoaDon) {
        this.maHoaDon = maHoaDon;
        this.tenHang = tenHang;

        this.soLuong = soLuong;
        this.gia = gia;
        this.ngay = ngay;
        this.trangThaiHoaDon = trangThaiHoaDon;
    }

    public int getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(int maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public String getTenHang() {
        return tenHang;
    }

    public void setTenHang(String tenHang) {
        this.tenHang = tenHang;
    }


    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public int getTrangThaiHoaDon() {
        return trangThaiHoaDon;
    }

    public void setTrangThaiHoaDon(int trangThaiHoaDon) {
        this.trangThaiHoaDon = trangThaiHoaDon;
    }
}
