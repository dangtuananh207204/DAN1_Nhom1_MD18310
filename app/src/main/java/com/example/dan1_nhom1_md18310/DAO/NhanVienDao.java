package com.example.dan1_nhom1_md18310.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.dan1_nhom1_md18310.Model.NhanVien;
import com.example.dan1_nhom1_md18310.database.Dbhelper;

import java.util.ArrayList;

public class NhanVienDao {
    private Dbhelper dbhelper;
    public NhanVienDao(Context context){
dbhelper=new Dbhelper(context);
    }
    public ArrayList<NhanVien> getDS(){
        SQLiteDatabase sqLiteDatabase=dbhelper.getReadableDatabase();
        ArrayList<NhanVien> list=new ArrayList<>();
        Cursor cursor=sqLiteDatabase.rawQuery("SELECT * FROM NHANVIEN",null);
        if (cursor.getCount() > 0){
         cursor.moveToFirst();
      do {
        list.add(new NhanVien(cursor.getInt(0),cursor.getString(1),
                cursor.getString(2),cursor.getDouble(3)));

      }while (cursor.moveToNext());
}return list;
    }
    public boolean isNumberValid(String soDT) {
        if (soDT.charAt(0) == '0' && soDT.length() == 10) {
            return true;
        }
        return false;
    }
    public boolean themSpmoi(NhanVien nhanVien) {
        SQLiteDatabase sqLiteDatabase = dbhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("hoTen", nhanVien.getHoTen());
        contentValues.put("soDT", nhanVien.getSoDT());
        contentValues.put("luong", nhanVien.getLuong());
        long check = sqLiteDatabase.insert("NHANVIEN", null, contentValues);
        return check != -1;
    }
    public  boolean Xoasp(int maTV){
        SQLiteDatabase sqLiteDatabase=dbhelper.getWritableDatabase();
        int check=sqLiteDatabase.delete("NHANVIEN","maTV = ?",new String[]{String.valueOf(maTV)});
        if (check<=0) return false;
        return true;
    }
    public boolean Suanv(NhanVien nhanVien) {
        SQLiteDatabase sqLiteDatabase = dbhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("hoTen", nhanVien.getHoTen());
        contentValues.put("soDT", nhanVien.getSoDT());
        contentValues.put("luong", nhanVien.getLuong());
        int check=sqLiteDatabase.update("NHANVIEN",contentValues,"maTV = ? ",new String[]{String.valueOf(nhanVien.getMaTV())});
        if (check<=0) return false;
        return true;
    }
}
