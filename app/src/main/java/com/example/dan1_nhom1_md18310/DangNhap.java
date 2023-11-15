package com.example.dan1_nhom1_md18310;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dan1_nhom1_md18310.DAO.AdminDAO;

public class DangNhap extends AppCompatActivity {

    EditText tendn, matkhaudn;
    Button btndangnhap,btnhuy;

    AdminDAO adminDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);

        tendn = findViewById(R.id.edt_tendn);
        matkhaudn = findViewById(R.id.edt_matkhaudn);

        btndangnhap = findViewById(R.id.btn_dang_nhap);
        AdminDAO adminDao = new AdminDAO(this);
        btndangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = tendn.getText().toString();
                String matkhau = matkhaudn.getText().toString();

                if (adminDao.checkDangNhap(user, matkhau)){
                    startActivity(new Intent(DangNhap.this, MainActivity.class));
                }else {
                    Toast.makeText(DangNhap.this, "Tài khoản or Mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}