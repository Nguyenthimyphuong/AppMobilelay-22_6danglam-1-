package com.example.appmobile.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appmobile.R;

public class quanly_chitiet_ncc extends AppCompatActivity {

    ImageView btnBackChiTietNCC;

    TextView txtMaNCC;
    TextView txtTenNCC;
    TextView txtSDT;
    TextView txtDiaChi;
    TextView txtSanPham;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quanly_chitiet_ncc);

        // Ánh xạ giao diện
        btnBackChiTietNCC = findViewById(R.id.btnBackChiTietNCC);

        txtMaNCC = findViewById(R.id.txtMaNCC);
        txtTenNCC = findViewById(R.id.txtTenNCC);
        txtSDT = findViewById(R.id.txtSDT);
        txtDiaChi = findViewById(R.id.txtDiaChi);
        txtSanPham = findViewById(R.id.txtSanPham);

        // Nhận dữ liệu từ trang quản lý nhà cung cấp gửi sang
        String maNCC = getIntent().getStringExtra("maNCC");
        String tenNCC = getIntent().getStringExtra("tenNCC");
        String sdt = getIntent().getStringExtra("sdt");
        String diaChi = getIntent().getStringExtra("diaChi");
        String sanPham = getIntent().getStringExtra("sanPham");

        // Nếu sản phẩm bị null thì hiện nội dung mặc định
        if (sanPham == null || sanPham.trim().isEmpty()) {
            sanPham = "Chưa có thông tin sản phẩm cung cấp";
        }

        // Hiển thị dữ liệu lên giao diện
        txtMaNCC.setText("Mã NCC: " + maNCC);
        txtTenNCC.setText("Tên NCC: " + tenNCC);
        txtSDT.setText("SĐT: " + sdt);
        txtDiaChi.setText("Địa chỉ: " + diaChi);
        txtSanPham.setText(sanPham);

        // Nút quay lại trang quản lý nhà cung cấp
        btnBackChiTietNCC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}