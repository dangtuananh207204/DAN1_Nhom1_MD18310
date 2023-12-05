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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;

public class QLHoaDonFragment extends Fragment {

    RecyclerView recyclerView;
    HoaDonDAO hoaDonDAO;
    HangDao hangDao;
    ArrayList<QuanLyHoaDon> list;
    ArrayList<QuanLyHoaDon> list2;
    EditText edtim;
    HoaDonAdapter hoaDonAdapter;
//    Spinner spnLH,spnH;
private ArrayList<QuanLyHang> listH;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_q_l_hoa_don, container, false);

        recyclerView = view.findViewById(R.id.recycerQlHoaDon);
        FloatingActionButton actionButton = view.findViewById(R.id.floatAdd);
        hoaDonDAO = new HoaDonDAO(getContext());
        hangDao = new HangDao(getContext());
        list2=hoaDonDAO.getDSHoaDon();
        edtim=view.findViewById(R.id.edSerach);
        edtim.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            list.clear();
            for (QuanLyHoaDon hd:list2){
                if (String.valueOf(hd.getMaHoaDon()).contains(charSequence.toString())){
                 list.add(hd);
                }
            }hoaDonAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

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
                            Spinner spnTenHang = view.findViewById(R.id.spnTenHang);
                            EditText edtSoLuong = view.findViewById(R.id.edtSoLuong);


                            HangDao hangDao = new HangDao(getContext());
                            listH = hangDao.getDSHang();

                            // Tạo danh sách HashMap để lưu trữ dữ liệu cho Spinner
                            ArrayList<HashMap<String, Object>> listHM = new ArrayList<>();
                            for (QuanLyHang hang : listH) {
                                HashMap<String, Object> hd = new HashMap<>();
                                hd.put("tenHang", hang.getTenHang());
                                hd.put("giaHang", hang.getGiaHang());
                                hd.put("maHang", hang.getMaHang());  // Thêm maHang để lấy sau này
                                listHM.add(hd);
                            }
                            SimpleAdapter simpleAdapter = new SimpleAdapter(getContext(),
                                    listHM,
                                    android.R.layout.simple_list_item_1,
                                    new String[]{"tenHang"},
                                    new int[]{android.R.id.text1}
                            );
                            spnTenHang.setAdapter(simpleAdapter);

                            spnTenHang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    QuanLyHang quanLyHang = listH.get(position);
                                    int giaHang = quanLyHang.getGiaHang();
                                    edtSoLuong.setTag(giaHang);
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });



                            builder.setView(view)
                                    .setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            String soLuongStr = edtSoLuong.getText().toString();
                                            if (soLuongStr.isEmpty()) {
                                                // Hiển thị thông báo và không thêm hóa đơn
                                                Toast.makeText(getContext(), "Vui lòng nhập số lượng", Toast.LENGTH_SHORT).show();
                                            } else {
                                                // Kiểm tra liệu dữ liệu nhập vào chỉ là số
                                                try {
                                                    int soLuong = Integer.parseInt(soLuongStr);

                                                    // Tiếp tục xử lý khi số lượng hợp lệ
                                                    QuanLyHang quanLyHang = listH.get(spnTenHang.getSelectedItemPosition());
                                                    int maHang = quanLyHang.getMaHang();
                                                    String tenHang = quanLyHang.getTenHang();
                                                    int giaHoaDon = Integer.parseInt(edtSoLuong.getTag().toString()) * soLuong;
                                                    int traHang = 0;

                                                    Calendar calendar = Calendar.getInstance();
                                                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                                    String ngayHoaDon = dateFormat.format(calendar.getTime());

                                                    boolean success = hoaDonDAO.themHoaDon(maHang, soLuong, giaHoaDon, ngayHoaDon, traHang);
                                                    if (success) {
                                                        Toast.makeText(getContext(), "Thêm hóa đơn thành công", Toast.LENGTH_SHORT).show();
                                                        loadData();
                                                    } else {
                                                        Toast.makeText(getContext(), "Thêm hóa đơn thất bại", Toast.LENGTH_SHORT).show();
                                                    }
                                                } catch (NumberFormatException e) {
                                                    // Người dùng nhập không phải là số
                                                    Toast.makeText(getContext(), "Vui lòng nhập số lượng hợp lệ", Toast.LENGTH_SHORT).show();
                                                }
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


}