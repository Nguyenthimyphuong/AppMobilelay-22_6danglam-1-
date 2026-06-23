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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appmobile.Activity.ChiTietKhachHangActivity;
import com.example.appmobile.R;
import com.example.appmobile.model.KhachHang;

import java.util.ArrayList;

public class KhachHangAdapter extends RecyclerView.Adapter<KhachHangAdapter.ViewHolder> {

    // Màn hình đang sử dụng adapter này
    Context context;

    // Danh sách khách hàng
    ArrayList<KhachHang> list;

    // Database dùng để xóa khách hàng
    SQLiteDatabase db;

    public KhachHangAdapter(Context context,
                            ArrayList<KhachHang> list,
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

        View view = LayoutInflater.from(context)
                .inflate(
                        R.layout.item_khachhang,
                        parent,
                        false
                );

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull ViewHolder holder,
            int position) {

        KhachHang kh = list.get(position);

        // Hiển thị thông tin khách hàng
        holder.txtMaKH.setText(
                String.format("KH%02d", kh.getId())
        );

        holder.txtTenKH.setText(kh.getHoten());
        holder.txtSDT.setText(kh.getSdt());
        holder.txtDiaChi.setText(kh.getDiachi());

        // Nút Chi tiết khách hàng
        holder.btnChiTiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*
                 * userId chính là id của khách hàng trong bảng users.
                 * userId này sẽ dùng để lấy hóa đơn của khách hàng đó.
                 */
                int userId = kh.getId();

                Intent intent = new Intent(
                        context,
                        ChiTietKhachHangActivity.class
                );

                // Gửi userId sang trang chi tiết
                intent.putExtra("userId", userId);

                // Gửi thông tin khách hàng sang trang chi tiết
                intent.putExtra("tenKH", kh.getHoten());
                intent.putExtra("diaChi", kh.getDiachi());
                intent.putExtra("soDT", kh.getSdt());

                context.startActivity(intent);
            }
        });

        // Nút Xóa khách hàng
        holder.btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder =
                        new AlertDialog.Builder(context);

                builder.setTitle("Xác nhận xóa");

                builder.setMessage(
                        "Bạn có chắc muốn xóa khách hàng "
                                + kh.getHoten()
                                + " không?"
                );

                builder.setPositiveButton("Xóa", (dialog, which) -> {

                    /*
                     * Bảng khách hàng của bạn là users.
                     * Cột khóa chính là id.
                     */
                    int result = db.delete(
                            "users",
                            "id=?",
                            new String[]{
                                    String.valueOf(kh.getId())
                            }
                    );

                    if (result > 0) {

                        list.remove(position);
                        notifyDataSetChanged();

                        Toast.makeText(
                                context,
                                "Đã xóa khách hàng",
                                Toast.LENGTH_SHORT
                        ).show();

                    } else {

                        Toast.makeText(
                                context,
                                "Xóa khách hàng thất bại",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                });

                builder.setNegativeButton("Hủy", null);

                builder.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder
            extends RecyclerView.ViewHolder {

        TextView txtMaKH;
        TextView txtTenKH;
        TextView txtSDT;
        TextView txtDiaChi;

        Button btnChiTiet;
        Button btnXoa;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtMaKH = itemView.findViewById(R.id.txtMaKH);
            txtTenKH = itemView.findViewById(R.id.txtTenKH);
            txtSDT = itemView.findViewById(R.id.txtSDT);
            txtDiaChi = itemView.findViewById(R.id.txtDiaChi);

            btnChiTiet = itemView.findViewById(R.id.btnChiTiet);
            btnXoa = itemView.findViewById(R.id.btnXoa);
        }
    }
}