package com.example.appmobile.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appmobile.Activity.ChiTietHoaDonActivity;
import com.example.appmobile.R;
import com.example.appmobile.model.HoaDon;

import java.util.ArrayList;

public class AdapterHoaDon
        extends RecyclerView.Adapter<AdapterHoaDon.ViewHolder> {

    Context context;
    ArrayList<HoaDon> list;
    SQLiteDatabase db;

    public AdapterHoaDon(
            Context context,
            ArrayList<HoaDon> list,
            SQLiteDatabase db) {

        this.context = context;
        this.list = list;
        this.db = db;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {

        View view = LayoutInflater
                .from(context)
                .inflate(
                        R.layout.item_hoadon,
                        parent,
                        false
                );

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull ViewHolder holder,
            int position) {

        HoaDon hd = list.get(position);

        holder.txtMaHD.setText(
                "HD" + hd.getId()
        );

        holder.txtTenKH.setText(
                hd.getHoTen()
        );

        holder.txtTongTien.setText(
                hd.getTongTien()
        );



        holder.btnChiTiet.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            context,
                            ChiTietHoaDonActivity.class
                    );

            intent.putExtra("id", hd.getId());
            intent.putExtra("ten", hd.getHoTen());
            intent.putExtra("sdt", hd.getSoDT());
            intent.putExtra("diachi", hd.getDiaChi());
            intent.putExtra("sanpham", hd.getSanPham());
            intent.putExtra("tongtien", hd.getTongTien());
                      intent.putExtra("ngaydat", hd.getNgayDat());

            context.startActivity(intent);
        });

        holder.btnXoa.setOnClickListener(v -> {

            AlertDialog.Builder builder =
                    new AlertDialog.Builder(context);

            builder.setTitle("Xóa hóa đơn");

            builder.setMessage(
                    "Bạn có chắc muốn xóa hóa đơn HD"
                            + hd.getId()
            );

            builder.setPositiveButton(
                    "Xóa",
                    (dialog, which) -> {

                        db.delete(
                                "hoadon",
                                "id=?",
                                new String[]{
                                        String.valueOf(
                                                hd.getId()
                                        )
                                }
                        );

                        list.remove(position);

                        notifyDataSetChanged();
                    });

            builder.setNegativeButton(
                    "Hủy",
                    null);

            builder.show();
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder
            extends RecyclerView.ViewHolder {

        TextView txtMaHD,
                txtTenKH,
                txtTongTien,
                txtTrangThai;

        Button btnChiTiet,
                btnXoa;

        public ViewHolder(
                @NonNull View itemView) {

            super(itemView);

            txtMaHD =
                    itemView.findViewById(R.id.txtMaHD);

            txtTenKH =
                    itemView.findViewById(R.id.txtTenKH);

            txtTongTien =
                    itemView.findViewById(R.id.txtTongTien);



            btnChiTiet =
                    itemView.findViewById(R.id.btnChiTiet);

            btnXoa =
                    itemView.findViewById(R.id.btnXoa);
        }
    }
}