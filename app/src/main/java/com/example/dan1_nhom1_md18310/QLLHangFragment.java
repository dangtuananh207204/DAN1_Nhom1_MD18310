package com.example.dan1_nhom1_md18310;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.dan1_nhom1_md18310.DAO.HangDao;
import com.example.dan1_nhom1_md18310.DAO.LoaiHangDAO;
import com.example.dan1_nhom1_md18310.Model.QuanLyHang;
import com.example.dan1_nhom1_md18310.Model.QuanLyLoaiHang;
import com.example.dan1_nhom1_md18310.Model.IteamClick;
import com.example.dan1_nhom1_md18310.adapter.QLLoaiHangAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;


public class QLLHangFragment extends Fragment{

RecyclerView recyclerView;

LoaiHangDAO loaiHangDAO;

QLLoaiHangAdapter qlLoaiHangAdapter;
EditText edtLoaiHang;
ArrayList<QuanLyLoaiHang> list;

FloatingActionButton floatingActionButton;

int maloai;


// Biến flag để theo dõi trạng thái của việc chọn dữ liệu
private boolean isItemSelected = false;
    Button btnSua;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_q_l_l_hang, container, false);

            recyclerView = view.findViewById(R.id.recycleLoaiHang);
            edtLoaiHang = view.findViewById(R.id.edt_LoaiHang);
        btnSua = view.findViewById(R.id.btn_Sua);
//        Button btnThem = view.findViewById(R.id.btn_Them);

        floatingActionButton = view.findViewById(R.id.floatAdd);

        loaiHangDAO = new LoaiHangDAO(getContext());
        loadData();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDiaLogThem();
            }
        });



        btnSua.setVisibility(View.GONE); // Ẩn nút sửa khi chưa chọn dữ liệu
        edtLoaiHang.setVisibility(View.GONE);
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenLoai = edtLoaiHang.getText().toString();
                if (!TextUtils.isEmpty(tenLoai)) {
                    QuanLyLoaiHang quanLyLoaiHang = new QuanLyLoaiHang(maloai, tenLoai);
                    if (loaiHangDAO.editLoaiHang(quanLyLoaiHang)) {
                        loadData();
                        edtLoaiHang.setText("");
                        isItemSelected = false;
                        edtLoaiHang.setVisibility(View.GONE);
                        btnSua.setVisibility(View.GONE);
                        Toast.makeText(getContext(), "Sửa Thành Công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Sửa Không thành công", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Vui lòng nhập dữ liệu trước khi sửa", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }


    private void showDiaLogThem() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_them_qllh, null);
        EditText edtThemLoaiHang = view.findViewById(R.id.edtThemLoaiHang);

        builder.setView(view)
                .setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String tenLoai = edtThemLoaiHang.getText().toString();
                        if (!TextUtils.isEmpty(tenLoai)) { // Kiểm tra xem có dữ liệu nhập vào không
                            if (loaiHangDAO.themLoaiHang(tenLoai)){
                                loadData();
                                edtThemLoaiHang.setText("");
                                Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getContext(), "Vui lòng nhập dữ liệu trước khi thêm", Toast.LENGTH_SHORT).show();
                        }

                    }
                })
                .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        //  Tạo và hiển thị AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();

    }



    public void loadData(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        list = loaiHangDAO.getDSLoaiHang();

        qlLoaiHangAdapter = new QLLoaiHangAdapter(getContext(),list, new IteamClick(){
            @Override
            public void onClickLoaiHang(QuanLyLoaiHang quanLyLoaiHang){

                edtLoaiHang.setVisibility(View.VISIBLE);
                btnSua.setVisibility(View.VISIBLE);
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