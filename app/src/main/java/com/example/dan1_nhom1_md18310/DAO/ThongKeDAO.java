package com.example.dan1_nhom1_md18310.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.dan1_nhom1_md18310.database.Dbhelper;

public class ThongKeDAO {
    Dbhelper dbhelper;
    public ThongKeDAO(Context context){dbhelper = new Dbhelper(context);}
    public int getDoanhThu(String ngayBatDau, String ngayKetThuc){
        ngayBatDau = ngayBatDau.replace("/", "-");
        ngayKetThuc =  ngayKetThuc.replace("/", "-");
        SQLiteDatabase sqLiteDatabase = dbhelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT SUM(giaHoaDon) FROM HOADON WHERE substr(Ngay,1,10)  BETWEEN ? AND ?",new String[]{ngayBatDau,ngayKetThuc});
        // substr(Ngay,7) || substr(Ngay,4,2) || substr(Ngay,1,2)
        if(cursor.getCount() != 0){
            cursor.moveToFirst();
            return cursor.getInt(0);
        }
        return 0;
    }
}
