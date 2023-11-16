package com.example.dan1_nhom1_md18310;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dan1_nhom1_md18310.DAO.NhanVienDao;
import com.example.dan1_nhom1_md18310.Model.NhanVien;
import com.example.dan1_nhom1_md18310.adapter.NhanVienAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.nio.FloatBuffer;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QLThanhVienFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QLThanhVienFragment extends Fragment {
private RecyclerView recyclerView;
private FloatingActionButton floatADD;
NhanVienDao nhanVienDao;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public QLThanhVienFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QLThanhVienFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QLThanhVienFragment newInstance(String param1, String param2) {
        QLThanhVienFragment fragment = new QLThanhVienFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_q_l_thanh_vien,container,false);
        recyclerView = view.findViewById(R.id.recyclerview);
        floatADD=view.findViewById(R.id.floatadd);
        nhanVienDao=new NhanVienDao(getContext());
        ArrayList<NhanVien> list= nhanVienDao.getDS();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        NhanVienAdapter adapter = new NhanVienAdapter(getContext(),list,nhanVienDao);
        recyclerView.setAdapter(adapter);
        floatADD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               showDialogThemnv();
            }
        });
        // Inflate the layout for this fragment
        return view;
    }
    private void showDialogThemnv() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialogthemnv, null);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
        EditText edtTen = view.findViewById(R.id.edt_ten);
        EditText edtluong = view.findViewById(R.id.edt_luong);
        EditText edtsoDT=view.findViewById(R.id.edt_DT);
        Button btthem = view.findViewById(R.id.bt_Signup);
        Button bthuy = view.findViewById(R.id.bt_Login);
        btthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenTV=edtTen.getText().toString().trim();

                if (tenTV.isEmpty()||edtsoDT.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getContext(), "Hãy Nhập Đầy Đủ Thông Tin", Toast.LENGTH_SHORT).show();
                } else {
                    if (Double.parseDouble(edtluong.getText().toString()) < 10000) {
                        Toast.makeText(getContext(), "Lương nhỏ hơn 10,000. Không thêm được.", Toast.LENGTH_SHORT).show();
                    }else if (!nhanVienDao.isNumberValid(edtsoDT.getText().toString())){
                        Toast.makeText(getContext(), "SDt k hop le", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        double luongValue=Double.parseDouble(edtluong.getText().toString());
                        NhanVien nhanVien = new NhanVien(tenTV,edtsoDT.getText().toString().trim(), luongValue);
                        boolean check = nhanVienDao.themSpmoi(nhanVien);
                        if (check) {
                            Toast.makeText(getContext(), "Thêm Thành Công", Toast.LENGTH_SHORT).show();
                            nhanVienDao=new NhanVienDao(getContext());
                            ArrayList<NhanVien> list= nhanVienDao.getDS();
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                            recyclerView.setLayoutManager(linearLayoutManager);
                            NhanVienAdapter adapter = new NhanVienAdapter(getContext(),list,nhanVienDao);
                            recyclerView.setAdapter(adapter);
                            alertDialog.dismiss();

                        } else {
                            Toast.makeText(getContext(), "Thêm Thất Bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
        bthuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }

}