package com.example.dan1_nhom1_md18310.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.dan1_nhom1_md18310.database.Dbhelper;

public class AdminDAO {
    Dbhelper dbhelper;

    SharedPreferences sharedPreferences;

    public AdminDAO(Context context){
        dbhelper = new Dbhelper(context);
        sharedPreferences = context.getSharedPreferences("ThongTin",Context.MODE_PRIVATE);
    }
    public boolean checkDangNhap(String maAD, String matKhau){
        SQLiteDatabase sqLiteDatabase = dbhelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM ADMIN WHERE maAD =? AND matKhau =?", new String[]{maAD,matKhau});
        if (cursor.getCount()!=0){
            cursor.moveToFirst();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("maAD",cursor.getString(0));
            editor.putString("taiKhoan",cursor.getString(3));
            editor.commit();
            return  true;
        }else {
            return false;
        }
    }

    public boolean taotaikhoan(String maAD, String hoTen, String matKhau){
        SQLiteDatabase sqLiteDatabase = dbhelper.getWritableDatabase();

        if (checkTaiKhoan(sqLiteDatabase,maAD)){
            sqLiteDatabase.close();
            return false;
        }

        ContentValues values = new ContentValues();
        values.put("maAD",maAD);
        values.put("hoTen",hoTen);
        values.put("matKhau",matKhau);
        values.put("taiKhoan","nhanvien");

        sqLiteDatabase.insert("ADMIN",null,values);
        sqLiteDatabase.close();
        return  true;
    }

    private boolean checkTaiKhoan(SQLiteDatabase sqLiteDatabase, String maAD){
        String[] projection = {"maAD"};
        String selection = "maAD=?";
        String[] selectionArgs = {maAD};

        Cursor cursor = sqLiteDatabase.query("ADMIN", projection, selection, selectionArgs, null, null, null);

        boolean exists = cursor.getCount() > 0;

        cursor.close();
        return exists;
    }
}
