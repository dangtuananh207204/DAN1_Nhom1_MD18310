package com.example.dan1_nhom1_md18310.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.dan1_nhom1_md18310.Model.QuanLyHang;
import com.example.dan1_nhom1_md18310.database.Dbhelper;

import java.util.ArrayList;

public class HangDao {
Dbhelper dbhelper;
public HangDao(Context context){
dbhelper=new Dbhelper(context);
}
public ArrayList<QuanLyHang> getDSHang(){
ArrayList<QuanLyHang> list=new ArrayList<>();
    SQLiteDatabase db=dbhelper.getReadableDatabase();
    Cursor cursor=db.rawQuery("SELECT h.maHang,h.tenHang,lh.tenLoai,h.giaHang,h.RAM,h.ROM,h.Mau,h.soLuong FROM HANG h INNER JOIN LOAIHANG lh ON h.maLoai = lh.maLoai",null);
    if(cursor.getCount()!=0){
        cursor.moveToFirst();
        do {
list.add(new QuanLyHang(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getInt(3),cursor.getString(4),cursor.getString(5),cursor.getString(6),cursor.getInt(7)));
        }while (cursor.moveToNext());
    }
    return list;
}


public boolean addHang(String tenHang,int maLoaiHang,int giaHang,String RAM,String ROM,String Mau,int soLuong){
SQLiteDatabase db=dbhelper.getWritableDatabase();
    ContentValues values =new ContentValues();
values.put("tenHang",tenHang);
values.put("maLoai",maLoaiHang);
values.put("giaHang",giaHang);
values.put("RAM",RAM);
values.put("ROM",ROM);
values.put("Mau",Mau);
values.put("soLuong",soLuong);
long check=db.insert("HANG",null,values);
if (check == -1) return false;
return true;
}
    public int xoaSach(int maHang) {
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        return db.delete("HANG", "maHang = ?", new String[]{String.valueOf(maHang)});
    }
    public boolean editHang(int maHang ,String tenHang,int maLoaiHang,int giaHang,String RAM,String ROM,String Mau,int soLuong) {
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tenHang", tenHang);
        values.put("maLoai", maLoaiHang);
        values.put("giaHang", giaHang);
        values.put("RAM", RAM);
        values.put("ROM", ROM);
        values.put("Mau", Mau);
        values.put("soLuong", soLuong);
long check=db.update("HANG",values,"maHang = ?",new String[]{String.valueOf(maHang)});
if (check==-1) return false;
return true;
    }



    public ArrayList<QuanLyHang> getDSHangTheoLoai(int maLoai) {
        ArrayList<QuanLyHang> list = new ArrayList<>();
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT h.maHang, h.tenHang, lh.tenLoai, h.giaHang, h.RAM, h.ROM, h.Mau, h.soLuong FROM HANG h INNER JOIN LOAIHANG lh ON h.maLoai = lh.maLoai WHERE h.maLoai = ?", new String[]{String.valueOf(maLoai)});
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                list.add(new QuanLyHang(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getInt(7)));
            } while (cursor.moveToNext());
        }
        return list;
    }

}
