package com.example.dan1_nhom1_md18310;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dan1_nhom1_md18310.DAO.LoaiHangDAO;
import com.example.dan1_nhom1_md18310.Model.QuanLyLoaiHang;
import com.example.dan1_nhom1_md18310.Model.IteamClick;
import com.example.dan1_nhom1_md18310.adapter.QLLoaiHangAdapter;

import java.util.ArrayList;


public class QLLHangFragment extends Fragment {

RecyclerView recyclerView;

LoaiHangDAO loaiHangDAO;

QLLoaiHangAdapter qlLoaiHangAdapter;
EditText edtLoaiHang;
ArrayList<QuanLyLoaiHang> list;

int maloai;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_q_l_l_hang, container, false);

            recyclerView = view.findViewById(R.id.recycleLoaiHang);
            edtLoaiHang = view.findViewById(R.id.edt_LoaiHang);
        Button btnThem = view.findViewById(R.id.btn_Them);
        Button btnSua = view.findViewById(R.id.btn_Sua);

        loaiHangDAO = new LoaiHangDAO(getContext());
        loadData();

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenLoai = edtLoaiHang.getText().toString();
                if (loaiHangDAO.themLoaiHang(tenLoai)){
                    loadData();
                    edtLoaiHang.setText("");
                    Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenLoai = edtLoaiHang.getText().toString();
                QuanLyLoaiHang quanLyLoaiHang = new QuanLyLoaiHang(maloai,tenLoai);
                if (loaiHangDAO.editLoaiHang(quanLyLoaiHang)){
                    loadData();
                    edtLoaiHang.setText("");
                    Toast.makeText(getContext(), "Sửa Thành Công", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getContext(), "Sửa Không thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });



        return view;
    }

    public void loadData(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        list = loaiHangDAO.getDSLoaiHang();

        qlLoaiHangAdapter = new QLLoaiHangAdapter(getContext(),list, new IteamClick(){
            @Override
            public void onClickLoaiHang(QuanLyLoaiHang quanLyLoaiHang){
                edtLoaiHang.setText(quanLyLoaiHang.getTenLoai());
                maloai = quanLyLoaiHang.getId();
            }
        });
        recyclerView.setAdapter(qlLoaiHangAdapter);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

}