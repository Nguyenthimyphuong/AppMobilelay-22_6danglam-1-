package com.example.appmobile.Activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appmobile.R;

public class ChiTietHoaDonActivity
        extends AppCompatActivity {

    TextView txtMaHD,
            txtTenKH,
            txtSDT,
            txtDiaChi,
            txtSanPham,
            txtTongTien,
            txtNgayDat;

    ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitiet_hoadon);

        txtMaHD = findViewById(R.id.txtMaHD);
        txtTenKH = findViewById(R.id.txtTenKH);
        txtSDT = findViewById(R.id.txtSDT);
        txtDiaChi = findViewById(R.id.txtDiaChi);
        txtSanPham = findViewById(R.id.txtSanPham);
        txtTongTien = findViewById(R.id.txtTongTien);
        txtNgayDat = findViewById(R.id.txtNgayDat);

        btnBack = findViewById(R.id.btnBack);

        txtMaHD.setText(
                "HD" + getIntent().getIntExtra("id", 0)
        );

        txtTenKH.setText(
                getIntent().getStringExtra("ten")
        );

        txtSDT.setText(
                getIntent().getStringExtra("sdt")
        );

        txtDiaChi.setText(
                getIntent().getStringExtra("diachi")
        );

        txtSanPham.setText(
                getIntent().getStringExtra("sanpham")
        );

        txtTongTien.setText(
                getIntent().getStringExtra("tongtien")
        );

        txtNgayDat.setText(
                getIntent().getStringExtra("ngaydat")
        );

        btnBack.setOnClickListener(v -> finish());
    }
}