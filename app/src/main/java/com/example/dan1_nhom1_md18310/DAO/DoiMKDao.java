package com.example.dan1_nhom1_md18310.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.dan1_nhom1_md18310.database.Dbhelper;

public class DoiMKDao {
    Dbhelper dbHelper;
    public DoiMKDao(Context context){
        dbHelper = new Dbhelper((context));
    }

    //checkLogin


    public int doiMatKhau(String username, String passOld, String passNew){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM ADMIN WHERE maAD = ? AND matKhau = ?", new String[] {username, passOld});
        if(cursor.getCount() != 0 ){
            ContentValues contentValues = new ContentValues();
            contentValues.put("matKhau", passNew);
            int check = sqLiteDatabase.update("Admin", contentValues, "maAD = ?", new String[]{username});
            if (check == -1){
                return -1;
            }
            return 1;
        }
        return 0;
    }
}
