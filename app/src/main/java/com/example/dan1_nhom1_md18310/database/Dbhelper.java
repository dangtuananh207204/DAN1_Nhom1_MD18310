package com.example.dan1_nhom1_md18310.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Dbhelper extends SQLiteOpenHelper {
    public Dbhelper(Context context){
        super(context,"DAN1",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String Admin = "CREATE TABLE ADMIN(maAD TEXT PRIMARY KEY,hoTen TEXT ,matKhau TEXT,taiKhoan TEXT)";
        db.execSQL(Admin);
        String Nhanvien= "CREATE TABLE NHANVIEN(maTV INTEGER PRIMARY KEY AUTOINCREMENT,hoTen TEXT,luong INTEGER)";
        db.execSQL(Nhanvien);
        String LoaiHang= "CREATE TABLE LOAIHANG(maLoai INTEGER PRIMARY KEY AUTOINCREMENT,tenLoai TEXT)";
        db.execSQL(LoaiHang);
        String Hang= "CREATE TABLE HANG(maHang INTEGER PRIMARY KEY AUTOINCREMENT,tenHang TEXT,giaHang INTEGER,maLoai INTEGER REFERENCES LOAIHANG(maLoai),RAM TEXT,ROM TEXT,Mau TEXT,soLuong INTEGER)";
        db.execSQL(Hang);
        String HoaDon="CREATE TABLE HOADON(maHoaDon INTEGER PRIMARY KEY AUTOINCREMENT,maHang INTEGER REFERENCES LOAIHANG(maHang),maLoai INTEGER REFERENCES LOAIHANG(maLoai),giaHoadon INTEGER,ngay TEXT, ngayTra INTEGER )";
        db.execSQL(HoaDon);
        String HoaDonCT="CREATE TABLE HOADONCT(maHoaDon INTEGER PRIMARY KEY AUTOINCREMENT,maHang INTEGER REFERENCES LOAIHANG(maHang),maLoai INTEGER REFERENCES LOAIHANG(maLoai),giaHoadon INTEGER,RAM TEXT,ROM TEXT,Mau TEXT,soLuong INTEGER)";
        db.execSQL(HoaDonCT);
        String eAdmin="INSERT INTO ADMIN VALUES('tunm','Minh Tu','123','admin'),('toan','Quoc Toan','123','nhanvien')";
        db.execSQL(eAdmin);
        String eNhanvien="INSERT INTO NHANVIEN VALUES(1,'Minh Tu',100000),(2,'QuocToan',10000990)";
        db.execSQL(eNhanvien);
        String eLoaiHang="INSERT INTO LOAIHANG VALUES(1,'Iphone'),(2,'SamSung'),(3,'Oppo')";
        db.execSQL(eLoaiHang);
        String eHang="INSERT INTO HANG VALUES(1,'Iphone 15',35000000,1,'8gb','128','Xanh',100),(2,'SamSung JS',12000000,2,'8gb','128','Cam',34)";
        db.execSQL(eHang);
        String eHoaDon="INSERT INTO HOADON VALUES(1,1,1,123333333,'12/12/2022',1),(2,2,2,12333333333,'23/11/2022',0)";
        db.execSQL(eHoaDon);
        String eHoaDonCT="INSERT INTO HOADONCT VALUES(1,1,1,123333333,'8gb','128','Xanh',2),(2,2,2,12333333333,'8gb','128','Cam',4)";
        db.execSQL(eHoaDonCT);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion){
            db.execSQL("DROP TABLE IF EXISTS ADMIN");
            db.execSQL("DROP TABLE IF EXISTS NHANVIEN");
            db.execSQL("DROP TABLE IF EXISTS LOAIHANG");
            db.execSQL("DROP TABLE IF EXISTS HANG");
            db.execSQL("DROP TABLE IF EXISTS HOADON");
            db.execSQL("DROP TABLE IF EXISTS HOADONCT");
            onCreate(db);
        }
    }
}
