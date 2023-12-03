package com.example.dan1_nhom1_md18310.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dan1_nhom1_md18310.DAO.HoaDonDAO;
import com.example.dan1_nhom1_md18310.DAO.LoaiHangDAO;
import com.example.dan1_nhom1_md18310.HoaDonCT;
import com.example.dan1_nhom1_md18310.Model.QuanLyHang;
import com.example.dan1_nhom1_md18310.Model.QuanLyHoaDon;
import com.example.dan1_nhom1_md18310.Model.QuanLyLoaiHang;
import com.example.dan1_nhom1_md18310.QLHoaDonFragment;
import com.example.dan1_nhom1_md18310.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class HoaDonAdapter extends RecyclerView.Adapter<HoaDonAdapter.ViewHolder> {




    private ArrayList<QuanLyHoaDon> list;
    private Context context;
    private QLHoaDonFragment qlHoaDonFragment;


    public HoaDonAdapter(ArrayList<QuanLyHoaDon> list, Context context) {
        this.list = list;
        this.context = context;
        setHasStableIds(true);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_hoadon, parent,false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        QuanLyHoaDon hoaDon = list.get(position);

        holder.txtMaHoaDon.setText("Mã hóa đơn: "+list.get(position).getMaHoaDon());
        holder.txtTenHang.setText("Tên hàng: "+list.get(position).getTenHang());
        holder.txtSoLuong.setText("Số lượng: "+list.get(position).getSoLuong());
        holder.txtGia.setText("Giá: "+list.get(position).getGia());
        holder.txtNgay.setText("Ngày: "+list.get(position).getNgay());

        String trangthai= "";
        if (list.get(position).getTrangThaiHoaDon()==1){
            trangthai = "Đã giao hàng";
            holder.btnTrahang.setVisibility(View.GONE);
        }else {
            trangthai= "Chưa giao hàng";
            holder.btnTrahang.setVisibility(View.VISIBLE);
        }
        if (list.get(position).getTrangThaiHoaDon() ==1){
            holder.txtTrangThai.setTextColor(ContextCompat.getColor(context,R.color.green));
        }
        else {
            holder.txtTrangThai.setTextColor(ContextCompat.getColor(context,R.color.red));
        }
        holder.txtTrangThai.setText("Trạng thái: "+trangthai);

        holder.btnTrahang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HoaDonDAO hoaDonDAO = new HoaDonDAO(context);
                boolean kiemtra = hoaDonDAO.thayDoiTraHang(list.get(holder.getAdapterPosition()).getMaHoaDon());
                if (kiemtra){
                    list.clear();
                    list = hoaDonDAO.getDSHoaDon();
                    notifyDataSetChanged();
                }else {
                    Toast.makeText(context, "Thay đổi trạng thái không thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.btnDeleteHD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Xác nhận xóa hóa đơn");
                builder.setMessage("Bạn có chắc chắn muốn xóa hóa này?");
                builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        HoaDonDAO hoaDonDAO = new HoaDonDAO(context);
                        int maHoaDon = list.get(holder.getAdapterPosition()).getMaHoaDon();

                        int check = hoaDonDAO.xoaHoaDon(maHoaDon);
                        if (check > 0) {
                            // Xóa thành công, cập nhật danh sách
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
        holder.chitietHoaDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           onclick(hoaDon);
            }
        });

    }
    private void onclick(QuanLyHoaDon hoaDon){
        Intent intent=new Intent(context, HoaDonCT.class);
        Bundle bundle=new Bundle();
        bundle.putSerializable("ten",hoaDon);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }



    @Override
    public int getItemCount() {
        return list.size();
    }


    public  class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtMaHoaDon, txtTenHang,txtGia,txtSoLuong,txtNgay,txtTrangThai;
        Button btnTrahang;
        ImageView btnDeleteHD,chitietHoaDon;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtMaHoaDon= itemView.findViewById(R.id.txtmaHoaDon);
            txtTenHang= itemView.findViewById(R.id.txtTenHang);
            txtGia = itemView.findViewById(R.id.txtGiaHang);
            txtSoLuong= itemView.findViewById(R.id.txtsoLuong);
            txtTrangThai = itemView.findViewById(R.id.txtTrangThai);
            txtNgay= itemView.findViewById(R.id.txtNgay);
            btnTrahang = itemView.findViewById(R.id.btnTrangThai);

            btnDeleteHD= itemView.findViewById(R.id.btnDeleteHD);
            chitietHoaDon = itemView.findViewById(R.id.chitietHoaDon);

        }
    }

}
