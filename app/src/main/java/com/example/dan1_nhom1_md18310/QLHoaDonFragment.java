package com.example.dan1_nhom1_md18310;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.dan1_nhom1_md18310.DAO.HangDao;
import com.example.dan1_nhom1_md18310.DAO.HoaDonDAO;
import com.example.dan1_nhom1_md18310.DAO.LoaiHangDAO;
import com.example.dan1_nhom1_md18310.Model.QuanLyHang;
import com.example.dan1_nhom1_md18310.Model.QuanLyHoaDon;
import com.example.dan1_nhom1_md18310.Model.QuanLyLoaiHang;
import com.example.dan1_nhom1_md18310.adapter.HoaDonAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
public class QLHoaDonFragment extends Fragment {

    RecyclerView recyclerView;

    HoaDonDAO hoaDonDAO;
    HangDao hangDao;
    ArrayList<QuanLyHoaDon> list;
    HoaDonAdapter hoaDonAdapter;

    private int currentSelectedPosition = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_q_l_hoa_don, container, false);

        recyclerView = view.findViewById(R.id.recycerQlHoaDon);
        FloatingActionButton actionButton = view.findViewById(R.id.floatAdd);
        hoaDonDAO = new HoaDonDAO(getContext());
        hangDao = new HangDao(getContext());

        loadData();

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDiaLog();
            }
        });

        return view;
    }

    private void loadData() {
        list = hoaDonDAO.getDSHoaDon();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        hoaDonAdapter = new HoaDonAdapter(list, getContext());
        recyclerView.setAdapter(hoaDonAdapter);
    }

    private void showDiaLog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_them_hoa_don, null);

        Spinner spnTenLoaiHang = view.findViewById(R.id.spnTenLoaiHang);
        Spinner spnTenHang = view.findViewById(R.id.spnTenHang);
        EditText edtSoLuong = view.findViewById(R.id.edtSoLuong);
        // Tạo đối tượng LoaiHangDAO để thao tác với dữ liệu loại hàng
        LoaiHangDAO loaiHangDAO = new LoaiHangDAO(getContext());


        //  Lấy danh sách loại hàng từ cơ sở dữ liệu
        ArrayList<QuanLyLoaiHang> listLoaiHang = loaiHangDAO.getDSLoaiHang();
        //  Tạo Adapter cho Spinner loại hàng và thiết lập Adapter cho Spinner
        ArrayAdapter<QuanLyLoaiHang> loaiHangAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, listLoaiHang);
        loaiHangAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnTenLoaiHang.setAdapter(loaiHangAdapter);


        spnTenLoaiHang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Xử lý khi một loại hàng được chọn
                QuanLyLoaiHang selectedLoaiHang = (QuanLyLoaiHang) spnTenLoaiHang.getSelectedItem();
                int maLoaiHang = selectedLoaiHang.getId();

                // Lấy danh sách hàng từ cơ sở dữ liệu
                HangDao hangDao = new HangDao(getContext());
                ArrayList<QuanLyHang> listHang = hangDao.getDSHangTheoLoai(maLoaiHang);

                spnTenHang.setAdapter(null);
                if (listHang.isEmpty()) {
                    // Hiển thị thông báo cảnh báo hoặc thực hiện hành động phù hợp
                    Toast.makeText(getContext(), "Không có hàng trong loại này", Toast.LENGTH_SHORT).show();
                    // Có thể ngăn chặn tạo hóa đơn ở đây bằng cách return hoặc thực hiện hành động phù hợp khác
                    return;
                }
                // Tạo adapter cho Spinner hàng
                ArrayAdapter<QuanLyHang> hangAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, listHang);
                hangAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spnTenHang.setAdapter(hangAdapter);

                currentSelectedPosition = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        builder.setView(view)
                .setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Lấy thông tin từ người dùng
                        QuanLyLoaiHang selectedLoaiHang = (QuanLyLoaiHang) spnTenLoaiHang.getSelectedItem();
                        QuanLyHang selectedHang = (QuanLyHang) spnTenHang.getSelectedItem();

                        String soLuongStr = edtSoLuong.getText().toString();

                        // Kiểm tra xem số lượng có hợp lệ không
                        if (soLuongStr.isEmpty()) {
                            // Hiển thị thông báo cảnh báo nếu số lượng trống
                            Toast.makeText(getContext(), "Vui lòng nhập số lượng", Toast.LENGTH_SHORT).show();
                            return; // Ngăn chặn việc thêm hóa đơn khi số lượng không hợp lệ
                        }

                        // Chuyển đổi số lượng từ chuỗi sang số nguyên
                        int soLuong = Integer.parseInt(soLuongStr);

                        // Kiểm tra xem số lượng có lớn hơn 0 không
                        if (soLuong <= 0) {
                            // Hiển thị thông báo cảnh báo nếu số lượng không hợp lệ
                            Toast.makeText(getContext(), "Số lượng phải lớn hơn 0", Toast.LENGTH_SHORT).show();
                            return; // Ngăn chặn việc thêm hóa đơn khi số lượng không hợp lệ
                        }
                        // Kiểm tra xem có hàng nào trong loại hàng được chọn không
                        if (spnTenHang.getAdapter() == null || spnTenHang.getAdapter().getCount() == 0) {
                            // Hiển thị thông báo rằng loại hàng này không có hàng
                            Toast.makeText(getContext(), "Loại hàng này không có hàng", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        // Tính giá hóa đơn
                        int giaHoaDon = soLuong * selectedHang.getGiaHang();


                        // Thêm hóa đơn vào cơ sở dữ liệu
                        HoaDonDAO hoaDonDAO = new HoaDonDAO(getContext());
                        boolean result = hoaDonDAO.themHoaDon(selectedHang.getMaHang(), selectedLoaiHang.getId(), soLuong, giaHoaDon, 0);

                        // Kiểm tra kết quả và cập nhật danh sách hiển thị
                        if (result) {
                            Toast.makeText(getContext(), "Thêm hóa đơn thành công", Toast.LENGTH_SHORT).show();
                            loadData();
                            currentSelectedPosition++;
                        } else {
                            Toast.makeText(getContext(), "Thêm hóa đơn không thành công", Toast.LENGTH_SHORT).show();
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

    private void setupRecyclerView() {
        hoaDonAdapter = new HoaDonAdapter(list, getContext());

    }

}