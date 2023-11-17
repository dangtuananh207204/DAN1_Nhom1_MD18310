package com.example.dan1_nhom1_md18310.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.dan1_nhom1_md18310.Model.QuanLyLoaiHang;
import com.example.dan1_nhom1_md18310.database.Dbhelper;

import java.util.ArrayList;

public class LoaiHangDAO {

    Dbhelper dbhelper;

    public LoaiHangDAO(Context context){
        dbhelper = new Dbhelper(context);
    }
    
    public ArrayList<QuanLyLoaiHang> getDSLoaiHang(){
        ArrayList<QuanLyLoaiHang> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase =dbhelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM LOAIHANG",null);
        if (cursor.getCount()!=0){
            cursor.moveToFirst();
            do {
                list.add(new QuanLyLoaiHang(cursor.getInt(0),cursor.getString(1)));
            }while (cursor.moveToNext());
        }

        return list;
    }

    public int DeleteLoaiHang(int id){
        SQLiteDatabase sqLiteDatabase = dbhelper.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM HANG WHERE maLoai =?", new String[]{String.valueOf(id)});
        if (cursor.getCount()!=0){
            return -1;
        }
        long check = sqLiteDatabase.delete("LOAIHANG","maLoai =?",new String[]{String.valueOf(id)});
        if (check == -1){
            return 0;
        }
        return 1;
    }

    public boolean editLoaiHang(QuanLyLoaiHang quanLyLoaiHang){
        SQLiteDatabase sqLiteDatabase = dbhelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("tenLoai",quanLyLoaiHang.getTenLoai());

        long check = sqLiteDatabase.update("LOAIHANG",values,"maLoai =?", new String[]{String.valueOf(quanLyLoaiHang.getId())});

        if (check == -1)
            return false;
        return true;
    }

    public  boolean themLoaiHang(String tenLoai){
        SQLiteDatabase sqLiteDatabase = dbhelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tenLoai",tenLoai);
        long check = sqLiteDatabase.insert("LOAIHANG",null,values);
        if(check ==-1){
            return false;
        }else {
            return true;
        }

    }
}
