package com.example.dan1_nhom1_md18310.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dan1_nhom1_md18310.DAO.NhanVienDao;
import com.example.dan1_nhom1_md18310.Model.NhanVien;
import com.example.dan1_nhom1_md18310.R;

import java.util.ArrayList;

public class NhanVienAdapter extends RecyclerView.Adapter<NhanVienAdapter.ViewHolder> {
    private Context context;
    private ArrayList<NhanVien> list;
    private NhanVienDao nhanVienDao;

    public NhanVienAdapter(Context context, ArrayList<NhanVien> list, NhanVienDao nhanVienDao) {
        this.context = context;
        this.list = list;
        this.nhanVienDao = nhanVienDao;
    }

    public NhanVienAdapter(Context context, ArrayList<NhanVien> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=((Activity)context).getLayoutInflater();
        View view=inflater.inflate(R.layout.itemnhanvien,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    holder.txthoTen.setText(list.get(position).getHoTen());
    holder.txtsoDt.setText("SDT: "+list.get(position).getSoDT());
    holder.txtluong.setText("Lương: "+list.get(position).getLuong());
        holder.ibtxoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDelealog(list.get(holder.getAdapterPosition()).getHoTen(),list.get(holder.getAdapterPosition()).getMaTV());
            }
        });
        holder.ibtchinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlogedit(list.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txthoTen,txtluong,txtsoDt;
        ImageButton ibtxoa,ibtchinh;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txthoTen=itemView.findViewById(R.id.tv_name);
            txtluong=itemView.findViewById(R.id.tv_luong);
            txtsoDt=itemView.findViewById(R.id.tv_dt);
            ibtchinh=itemView.findViewById(R.id.btn_edit);
            ibtxoa=itemView.findViewById(R.id.btn_delete);

        }
    }
    private void showDelealog(String hoTen,int maTV){
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setTitle("Thông Báo");
        builder.setMessage("Bạn có muốn xóa sản phẩm \"" + hoTen +"\"không ?");
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                boolean check=nhanVienDao.Xoasp(maTV);
                if (check){
                    Toast.makeText(context,"DELETE SUCCESS",Toast.LENGTH_SHORT).show();
                    list.clear();
                    list=nhanVienDao.getDS();
                    notifyDataSetChanged();
                }else {
                    Toast.makeText(context,"DELETE FAIL",Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Hủy",null);
        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }
    private void  showAlogedit(NhanVien nhanVien){
        AlertDialog.Builder builder= new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view=inflater.inflate(R.layout.dialogsuanv,null);
        builder.setView(view);
        AlertDialog alertDialog=builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
        EditText edtten=view.findViewById(R.id.edt_ten);
        EditText edtsoDT=view.findViewById(R.id.edt_DT);
        EditText edtluong=view.findViewById(R.id.edt_luong);
        Button btchinh=view.findViewById(R.id.bt_Signup);
        Button bthuy=view.findViewById(R.id.bt_Login);
        edtten.setText(nhanVien.getHoTen());
        edtsoDT.setText(nhanVien.getSoDT());
        edtluong.setText(String.valueOf(nhanVien.getLuong()));
        btchinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int maTV= nhanVien.getMaTV();
                String tenNV=edtten.getText().toString();
                String SDT=edtsoDT.getText().toString();
                if (tenNV.length()==0||SDT.length()==0){
                    Toast.makeText(context,"Nhập Thông Tin Đầy Đủ",Toast.LENGTH_SHORT).show();
                }else{
                    if (Double.parseDouble(edtluong.getText().toString()) < 10000) {
                        Toast.makeText(context, "Lương nhỏ hơn 10,000. Không thêm được.", Toast.LENGTH_SHORT).show();
                    } else {
                        double luongValue=Double.parseDouble(edtluong.getText().toString());
                       NhanVien nhanVien1=new NhanVien(maTV,tenNV,SDT,luongValue);
                        boolean check=nhanVienDao.Suanv(nhanVien1);
                        if (check){
                            Toast.makeText(context,"Chỉnh Sửa Thành Công",Toast.LENGTH_SHORT).show();
                            list.clear();
                            list=nhanVienDao.getDS();
                            notifyDataSetChanged();
                            alertDialog.dismiss();

                        }else {
                            Toast.makeText(context, "Chỉnh Sửa Thất Bại", Toast.LENGTH_SHORT).show();
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
