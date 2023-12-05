package com.example.dan1_nhom1_md18310;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.dan1_nhom1_md18310.DAO.HangDao;
import com.example.dan1_nhom1_md18310.DAO.LoaiHangDAO;
import com.example.dan1_nhom1_md18310.Model.QuanLyHang;
import com.example.dan1_nhom1_md18310.Model.QuanLyLoaiHang;
import com.example.dan1_nhom1_md18310.adapter.QuanLyHangAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QLHangFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QLHangFragment extends Fragment {
    RecyclerView recyclerView;
   QuanLyHangAdapter adapter;
    HangDao hangDao;
    ArrayList<QuanLyHang> list=new ArrayList<>();
    ArrayList<QuanLyHang> list2=new ArrayList<>();
    FloatingActionButton btnAddSach;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public QLHangFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QLHangFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QLHangFragment newInstance(String param1, String param2) {
        QLHangFragment fragment = new QLHangFragment();
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
        View view = inflater.inflate(R.layout.fragment_q_l_hang, container, false);
        recyclerView = view.findViewById(R.id.recyclerview);
        btnAddSach = view.findViewById(R.id.floatadd);

        hangDao = new HangDao(getContext());
        list = hangDao.getDSHang();
        list2=hangDao.getDSHang();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new QuanLyHangAdapter(getContext(), list);
        recyclerView.setAdapter(adapter);
        btnAddSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogthem();
            }
        });
        EditText edtim=view.findViewById(R.id.edSerach);
        edtim.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                list.clear();
             for (QuanLyHang qlh:list2){
             if (qlh.getTenHang().contains(charSequence.toString())){
                list.add(qlh);
             }
             }
             adapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        return view;
    }
    private void showDialogthem(){

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialogthem,null);
        builder.setView(view);
        EditText edtTenHang = view.findViewById(R.id.edtTenHang);
        Spinner spinner =view.findViewById(R.id.spnLoaiHang);
        EditText edtGia = view.findViewById(R.id.edtGiaHang);
        EditText edtRAM = view.findViewById(R.id.edtRam);
        EditText edtROM = view.findViewById(R.id.edtRom);
        EditText edtMAU = view.findViewById(R.id.edtMau);
        EditText edtsoLuong = view.findViewById(R.id.edtsoLuong);
        SimpleAdapter simpleAdapter = new SimpleAdapter(
                getContext(),
                getList(),
                android.R.layout.simple_list_item_1,
                new String[]{"tenLoai"},
                new int[]{android.R.id.text1}
        );
        spinner.setAdapter(simpleAdapter);
        builder.setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(edtGia.getText().toString().equals("")||
                        edtTenHang.getText().toString().equals("")){
                    Toast.makeText(getContext(), "Không để trống dữ liệu", Toast.LENGTH_SHORT).show();
                }else {
                    String ten = edtTenHang.getText().toString();
                    String ram=edtRAM.getText().toString();
                    String rom=edtROM.getText().toString();
                    String mau=edtMAU.getText().toString();
                    int soLuong=Integer.parseInt(edtsoLuong.getText().toString());
                    int gia = Integer.parseInt(edtGia.getText().toString());
                    HashMap<String,Object> hs = (HashMap<String, Object>) spinner.getSelectedItem();
                    int maloai = (int) hs.get("maLoai");
                    boolean check = hangDao.addHang(ten,maloai,gia,ram,rom,mau,soLuong);
                    if(check){
                        Toast.makeText(getContext(), "Thêm mới thành công", Toast.LENGTH_SHORT).show();
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                        ArrayList<QuanLyHang> list=hangDao.getDSHang();
                        recyclerView.setLayoutManager(linearLayoutManager);
                        QuanLyHangAdapter adapter = new QuanLyHangAdapter(getContext(),list);
                        recyclerView.setAdapter(adapter);
                    } else {
                        Toast.makeText(getContext(), "Thêm mới thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private ArrayList<HashMap<String,Object>> getList(){
        LoaiHangDAO loaiHangDAO = new LoaiHangDAO(getContext());
        ArrayList<QuanLyLoaiHang> list1 = loaiHangDAO.getDSLoaiHang();
        ArrayList<HashMap<String,Object>> listHM = new ArrayList<>();
        for(QuanLyLoaiHang quanLyHang : list1){
            HashMap<String,Object> hs = new HashMap<>();
            hs.put("maLoai",quanLyHang.getId());
            hs.put("tenLoai",quanLyHang.getTenLoai());
            listHM.add(hs);
        }
        return listHM;
    }

}