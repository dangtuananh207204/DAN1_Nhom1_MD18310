package com.example.dan1_nhom1_md18310.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dan1_nhom1_md18310.DAO.HangDao;
import com.example.dan1_nhom1_md18310.DAO.LoaiHangDAO;
import com.example.dan1_nhom1_md18310.Model.QuanLyHang;
import com.example.dan1_nhom1_md18310.Model.QuanLyLoaiHang;
import com.example.dan1_nhom1_md18310.R;

import java.util.ArrayList;
import java.util.HashMap;

public class QuanLyHangAdapter extends RecyclerView.Adapter<QuanLyHangAdapter.ViewHolder>  {
    private Context context;
    private ArrayList<QuanLyHang> list;

    public QuanLyHangAdapter(Context context, ArrayList<QuanLyHang> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view= inflater.inflate(R.layout.itemhang,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
           holder.tvmaHang.setText("Mã hàng: "+list.get(position).getMaHang());
        holder.tvtenHang.setText("Tên Hàng: "+list.get(position).getTenHang());
        holder.tvtenLoai.setText("Tên Loại: "+list.get(position).getTenLoai());
        holder.tvgiaHang.setText("Giá Hàng: "+list.get(position).getGiaHang());
        holder.tvRam.setText("Ram: "+list.get(position).getRAM());
        holder.tvRom.setText("Rom: "+list.get(position).getROM());
        holder.tvMau.setText("Màu: "+list.get(position).getMau());
        holder.tvsoLuong.setText("Số Lượng: "+list.get(position).getSoLuong());
        holder.btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Xác nhận xóa sách");
                builder.setMessage("Bạn có chắc chắn muốn xóa sách này?");
                builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        HangDao dao = new HangDao(context);
                        int maHang =list.get(holder.getAdapterPosition()).getMaHang();

                        // Xóa sách khỏi cơ sở dữ liệu
                        int check = dao.xoaSach(maHang);
                        if (check > 0) {
                            // Xóa thành công, cập nhật danh sách sách
                            list.remove(holder.getAdapterPosition());
                            notifyDataSetChanged();
                            Toast.makeText(context, "Xóa sách thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Xóa sách không thành công", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("Hủy", null);

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        holder.btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogedit(list.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView btnXoa,btnSua;
        TextView tvmaHang,tvtenHang,tvtenLoai,tvgiaHang,tvRam,tvRom,tvMau,tvsoLuong;
    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        tvmaHang=itemView.findViewById(R.id.txtMahang);
        tvtenHang=itemView.findViewById(R.id.txtTenHang);
        tvtenLoai=itemView.findViewById(R.id.txtTenLoai);
        tvgiaHang=itemView.findViewById(R.id.txtGiaHang);
        tvRam=itemView.findViewById(R.id.txtRAM);
        tvRom=itemView.findViewById(R.id.txtROM);
        tvMau=itemView.findViewById(R.id.txtMau);
        tvsoLuong=itemView.findViewById(R.id.txtsoLuong);
        btnSua = itemView.findViewById(R.id.btnSuaSach_item);
        btnXoa = itemView.findViewById(R.id.btnXoaSach_item);
    }
}
    private void showDialogedit(QuanLyHang quanLyHang){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialogsuahang,null);
        builder.setView(view);
        EditText edtTenHang = view.findViewById(R.id.edtTenHang);
        Spinner spinner =view.findViewById(R.id.spnLoaiHang);
        EditText edtGia = view.findViewById(R.id.edtGiaHang);
        EditText edtRAM = view.findViewById(R.id.edtRam);
        EditText edtROM = view.findViewById(R.id.edtRom);
        EditText edtMAU = view.findViewById(R.id.edtMau);
        EditText edtsoLuong = view.findViewById(R.id.edtsoLuong);
        edtTenHang.setText(quanLyHang.getTenHang());
        edtGia.setText(String.valueOf(quanLyHang.getGiaHang()));
        edtRAM.setText(quanLyHang.getRAM());
        edtROM.setText(quanLyHang.getROM());
        edtMAU.setText(quanLyHang.getMau());
        edtsoLuong.setText(String.valueOf(quanLyHang.getSoLuong()));
        SimpleAdapter simpleAdapter = new SimpleAdapter(
                context,
                getList(),
                android.R.layout.simple_list_item_1,
                new String[]{"tenLoai"},
                new int[]{android.R.id.text1}
        );
        spinner.setAdapter(simpleAdapter);
        builder.setPositiveButton("Sửa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(edtGia.getText().toString().equals("")||
                        edtTenHang.getText().toString().equals("")){
                    Toast.makeText(context, "Không để trống dữ liệu", Toast.LENGTH_SHORT).show();
                }else {
                    String ten = edtTenHang.getText().toString();
                    String ram=edtRAM.getText().toString();
                    String rom=edtROM.getText().toString();
                    String mau=edtMAU.getText().toString();
                    int soLuong=Integer.parseInt(edtsoLuong.getText().toString());
                    int gia = Integer.parseInt(edtGia.getText().toString());
                    HashMap<String,Object> hs = (HashMap<String, Object>) spinner.getSelectedItem();
                    int maloai = (int) hs.get("maLoai");
                    HangDao dao=new HangDao(context);
                    boolean check = dao.editHang(quanLyHang.getMaHang(),ten,maloai,gia,ram,rom,mau,soLuong);
                    if(check){
                        Toast.makeText(context, "Sửa mới thành công", Toast.LENGTH_SHORT).show();
                        list.clear();
                        list = dao.getDSHang();
                        notifyDataSetChanged();
                    } else {
                        Toast.makeText(context, "Sửa mới thất bại", Toast.LENGTH_SHORT).show();
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
        LoaiHangDAO loaiHangDAO = new LoaiHangDAO(context);
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
