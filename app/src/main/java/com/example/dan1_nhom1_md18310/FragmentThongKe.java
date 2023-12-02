package com.example.dan1_nhom1_md18310;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dan1_nhom1_md18310.DAO.ThongKeDAO;

import java.util.Calendar;


public class FragmentThongKe extends Fragment {

    Button btnTuNgay,btnDenNgay,btnDoanhThu;
    EditText edtTuNgay,edtDenNgay;

    TextView txtKetQua;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_thong_ke, container, false);
        btnTuNgay = view.findViewById(R.id.btnTuNgay);
        btnDenNgay = view.findViewById(R.id.btnDenNgay);
        btnDoanhThu = view.findViewById(R.id.btnDoanhThu);
        edtTuNgay = view.findViewById(R.id.edtTuNgay);
        edtDenNgay = view.findViewById(R.id.edtDenNgay);
        txtKetQua = view.findViewById(R.id.txtKetQua);
        Calendar calendar = Calendar.getInstance();
        btnTuNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String ngay ="";
                        String thang = "";
                        if(dayOfMonth<10){
                            ngay ="0"+dayOfMonth;
                        }else {
                            ngay = String.valueOf(dayOfMonth);
                        }

                        if((month +1) <10){
                            thang ="0"+(month+1);
                        }else {
                            thang = String.valueOf((month+1));
                        }
                        edtTuNgay.setText(year+"/"+thang+"/"+ngay);
                    }
                },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        btnDenNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String ngay ="";
                        String thang = "";
                        if(dayOfMonth<10){
                            ngay ="0"+dayOfMonth;
                        }else {
                            ngay = String.valueOf(dayOfMonth);
                        }

                        if((month +1) <10){
                            thang ="0"+(month+1);
                        }else {
                            thang = String.valueOf((month+1));
                        }
                        edtDenNgay.setText(year+"/"+thang+"/"+ngay);
                    }
                },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                );
                datePickerDialog.show();
            }
        });

        btnDoanhThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThongKeDAO thongKeDAO = new ThongKeDAO(getContext());
                String ngaybatdau = edtTuNgay.getText().toString();
                String ngayketthuc = edtDenNgay.getText().toString();
                if (ngaybatdau.isEmpty() || ngayketthuc.isEmpty()) {
                    // Hiển thị thông báo yêu cầu chọn cả hai ngày
                      Toast.makeText(getContext(), "Vui lòng chọn ngày cho cả 2", Toast.LENGTH_SHORT).show();
                    return;
                }
                int doanhthu = thongKeDAO.getDoanhThu(ngaybatdau,ngayketthuc);
                txtKetQua.setText(doanhthu+"VND");
            }
        });


        return view;
    }
}