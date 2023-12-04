package com.example.dan1_nhom1_md18310;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dan1_nhom1_md18310.Model.QuanLyHoaDon;

public class HoaDonCT extends AppCompatActivity {
    TextView maHang,tenHang,giaHang,Ram,Rom,Mau,soLuong;
    ImageView btnthoat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoa_don_ct);
        Bundle bundle=getIntent().getExtras();
        if (bundle==null){
             return;
        }
        QuanLyHoaDon hoaDon=(QuanLyHoaDon)bundle.get("ten");
         maHang=findViewById(R.id.txtMahang);
         tenHang=findViewById(R.id.txtTenHang);
         giaHang=findViewById(R.id.txtGiaHang);
         Ram=findViewById(R.id.txtRAM);
        Rom=findViewById(R.id.txtROM);
        Mau=findViewById(R.id.txtMau);
        soLuong=findViewById(R.id.txtsoLuong);
        btnthoat=findViewById(R.id.btnXoaSach_item);
        maHang.setText("Mã Hàng: "+hoaDon.getMaHoaDon());
        tenHang.setText("Tên Hàng: "+hoaDon.getTenHang());
        giaHang.setText("Giá Hàng:"+hoaDon.getGia()+"VNĐ");
        Ram.setText("Ram:"+hoaDon.getRAM());
        Rom.setText("Rom: "+hoaDon.getROM());
        Mau.setText("Màu:"+hoaDon.getMau());
        soLuong.setText("Số Lượng:"+hoaDon.getSoLuong());
        btnthoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}