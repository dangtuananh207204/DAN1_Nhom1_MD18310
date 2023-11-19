package com.example.dan1_nhom1_md18310;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dan1_nhom1_md18310.DAO.AdminDAO;

public class FragmentTaoTaikhoan extends Fragment {

    EditText maADEditText;
    EditText hoTenEditText;
    EditText matKhauEditText;
    Button createEmployeeAccountButton;

    AdminDAO adminDAO;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tao_taikhoan, container, false);
        // Inflate the layout for this fragment
        maADEditText = view.findViewById(R.id.maADEditText);
        hoTenEditText = view.findViewById(R.id.hoTenEditText);
        matKhauEditText = view.findViewById(R.id.matKhauEditText);
        createEmployeeAccountButton = view.findViewById(R.id.createEmployeeAccountButton);

        adminDAO = new AdminDAO(requireContext());

        createEmployeeAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String maAD = maADEditText.getText().toString();
                String hoTen = hoTenEditText.getText().toString();
                String matKhau = matKhauEditText.getText().toString();
                if (!maAD.isEmpty() && !hoTen.isEmpty() && !matKhau.isEmpty()) {
                    // Gọi phương thức createEmployeeAccount với thông tin tài khoản nhân viên cần tạo
                    boolean success = adminDAO.taotaikhoan(maAD, hoTen, matKhau);

                    // Kiểm tra xem việc tạo tài khoản nhân viên có thành công hay không
                    if (success) {
                        Toast.makeText(requireContext(), "Tạo tài khoản nhân viên thành công", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(requireContext(), "Tài Khoản Nhân Viên Đã Tồn Tại", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(requireContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
}