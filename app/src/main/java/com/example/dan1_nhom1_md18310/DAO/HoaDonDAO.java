package com.example.dan1_nhom1_md18310.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.dan1_nhom1_md18310.Model.QuanLyHoaDon;
import com.example.dan1_nhom1_md18310.database.Dbhelper;

import java.util.ArrayList;

public class HoaDonDAO {
    Dbhelper dbhelper;

    public HoaDonDAO(Context context) {
        dbhelper = new Dbhelper(context);
    }

    public ArrayList<QuanLyHoaDon> getDSHoaDon() {
        ArrayList<QuanLyHoaDon> list = new ArrayList<>();
        SQLiteDatabase sql = dbhelper.getReadableDatabase();
        Cursor cursor = sql.rawQuery(
                "SELECT hd.maHoaDon, h.tenHang, lh.tenLoai, hd.soLuongMua, hd.giaHoaDon, hd.traHang FROM HOADON hd INNER JOIN HANG h ON hd.maHang = h.maHang INNER JOIN LOAIHANG lh ON hd.maLoai = lh.maLoai ", null);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                list.add(new QuanLyHoaDon(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getInt(4), cursor.getInt(5)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public boolean thayDoiTraHang(int maHoaDon) {
        SQLiteDatabase sql = dbhelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("traHang", 1);
        long check = sql.update("HOADON", values, "maHoaDon=?", new String[]{String.valueOf(maHoaDon)});
        if (check == -1) {
            return false;
        } else {
            return true;
        }
    }

    public int xoaHoaDon(int maHoaDon){
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        return  db.delete("HOADON","maHoaDon =?", new String[]{String.valueOf(maHoaDon)});
    }

    public boolean themHoaDon(int maHang, int maLoai, int soLuong, int giaHoaDon, int traHang) {
        SQLiteDatabase sql = dbhelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("maHang", maHang);
        values.put("maLoai", maLoai);
        values.put("soLuongMua", soLuong);
        values.put("giaHoaDon", giaHoaDon);
        values.put("traHang", traHang);

        long check = sql.insert("HOADON", null, values);
        return check != -1;
    }



}
