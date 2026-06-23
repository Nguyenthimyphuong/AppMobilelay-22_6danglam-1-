package com.example.appmobile.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appmobile.Activity.quanly_chitiet_ncc;
import com.example.appmobile.model.NhaCungCap;
import com.example.appmobile.R;

import java.util.ArrayList;

public class AdapterNCC extends ArrayAdapter<NhaCungCap> {

    Activity context;
    ArrayList<NhaCungCap> list;

    public AdapterNCC(Activity context,
                      ArrayList<NhaCungCap> list) {

        super(context, R.layout.item_ncc, list);

        this.context = context;
        this.list = list;
    }

    @Override
    public View getView(int position,
                        View convertView,
                        ViewGroup parent) {

        LayoutInflater inflater =
                context.getLayoutInflater();

        View row =
                inflater.inflate(
                        R.layout.item_ncc,
                        null,
                        true
                );

        // Ánh xạ ảnh NCC
        ImageView imgNCC =
                row.findViewById(R.id.imgNCC);

        // Ánh xạ thông tin NCC
        TextView txtMaNCC =
                row.findViewById(R.id.txtMaNCC);

        TextView txtTenNCC =
                row.findViewById(R.id.txtTenNCC);

        TextView txtDiaChi =
                row.findViewById(R.id.txtDiaChi);

        TextView txtSDT =
                row.findViewById(R.id.txtSDT);

        // Ánh xạ nút chi tiết
        Button btnChiTietNCC =
                row.findViewById(R.id.btnChiTietNCC);

        // Lấy NCC hiện tại
        NhaCungCap ncc =
                list.get(position);

        // Hiển thị dữ liệu
        imgNCC.setImageResource(R.drawable.client);

        txtMaNCC.setText("NCC" + ncc.getMaNCC());
        txtTenNCC.setText(ncc.getTenNCC());
        txtDiaChi.setText(ncc.getDiaChi());
        txtSDT.setText(ncc.getSdt());

        // Bấm Chi tiết để xem đúng NCC đó
        btnChiTietNCC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        context,
                        quanly_chitiet_ncc.class
                );

                intent.putExtra("maNCC", "NCC" + ncc.getMaNCC());
                intent.putExtra("tenNCC", ncc.getTenNCC());
                intent.putExtra("sdt", ncc.getSdt());
                intent.putExtra("diaChi", ncc.getDiaChi());
                intent.putExtra("sanPham", ncc.getSanPham());

                context.startActivity(intent);
            }
        });

        return row;
    }
}