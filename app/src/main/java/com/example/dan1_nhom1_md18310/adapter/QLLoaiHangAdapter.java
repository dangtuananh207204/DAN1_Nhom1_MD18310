package com.example.dan1_nhom1_md18310.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dan1_nhom1_md18310.DAO.LoaiHangDAO;
import com.example.dan1_nhom1_md18310.Model.IteamClick;
import com.example.dan1_nhom1_md18310.Model.QuanLyLoaiHang;
import com.example.dan1_nhom1_md18310.R;

import java.util.ArrayList;

public class QLLoaiHangAdapter extends RecyclerView.Adapter<QLLoaiHangAdapter.ViewHolder> {
    private Context context;
    private ArrayList<QuanLyLoaiHang> list;

    private IteamClick iteamClick;




    public QLLoaiHangAdapter(Context context, ArrayList<QuanLyLoaiHang> list, IteamClick iteamClick) {
        this.context = context;
        this.list = list;
        this.iteamClick = iteamClick;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_loaihang,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtMaLoai.setText("Mã loại: "+String.valueOf(list.get(position).getId()));
        holder.txtTenLoai.setText("Tên loại: "+list.get(position).getTenLoai());

        holder.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoaiHangDAO loaiHangDAO = new LoaiHangDAO(context);
                int check = loaiHangDAO.DeleteLoaiHang(list.get(holder.getAdapterPosition()).getId());
                switch (check){
                    case 1:
                        list.clear();
                        list = loaiHangDAO.getDSLoaiHang();
                        Toast.makeText(context, "Đã xóa", Toast.LENGTH_SHORT).show();
                        notifyDataSetChanged();
                        break;
                    case -1:
                        Toast.makeText(context, "Ko thể xóa loại hàng này vì đã có trong hàng", Toast.LENGTH_SHORT).show();
                        break;
                    case 0:
                        Toast.makeText(context, "Xóa loại sách ko thành công", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }
        });

        holder.iv_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QuanLyLoaiHang quanLyLoaiHang = list.get(holder.getAdapterPosition());
                iteamClick.onClickLoaiHang(quanLyLoaiHang);

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtMaLoai,txtTenLoai;
        ImageView iv_delete,iv_update;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMaLoai = itemView.findViewById(R.id.txtMa);
            txtTenLoai = itemView.findViewById(R.id.txtTenHang);
            iv_delete = itemView.findViewById(R.id.iv_delete);
            iv_update = itemView.findViewById(R.id.iv_edit);
        }
    }
}
