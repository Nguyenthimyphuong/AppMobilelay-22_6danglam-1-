package com.example.appmobile.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appmobile.R;

public class quantri_page extends AppCompatActivity {

    // Khai báo các nút chức năng trong trang quản trị
    TableLayout btn_qlkh, btn_qlncc, btn_qlsp;
    TableLayout btn_qlbill, btn_history, btn_qlthongke;

    // Nút quay lại
    ImageView btn_back_admin;

    // Nút đăng xuất
    Button btn_logout_admin;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Gắn giao diện XML cho trang quản trị
        setContentView(R.layout.activity_quantri_page);

        // Ánh xạ nút quay lại và nút đăng xuất
        btn_back_admin = findViewById(R.id.btn_back_admin);
        btn_logout_admin = findViewById(R.id.btn_logout_admin);

        // Xử lý nút quay lại màn hình chính
        btn_back_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        quantri_page.this,
                        MainActivity.class
                );
                startActivity(intent);
                finish();
            }
        });

        // Xử lý nút đăng xuất admin
        btn_logout_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(
                        quantri_page.this,
                        "Đăng xuất thành công",
                        Toast.LENGTH_SHORT
                ).show();

                Intent intent = new Intent(
                        quantri_page.this,
                        MainActivity.class
                );
                startActivity(intent);
                finish();
            }
        });

        // Chức năng quản lý khách hàng
        btn_qlkh = findViewById(R.id.btn_qlkh);
        btn_qlkh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        quantri_page.this,
                        DanhSachKhachHangActivity.class
                );
                startActivity(intent);
            }
        });

        // Chức năng quản lý nhà cung cấp
        btn_qlncc = findViewById(R.id.btn_qlncc);
        btn_qlncc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        quantri_page.this,
                        quanly_nhacungcap.class
                );
                startActivity(intent);
            }
        });

        // Chức năng quản lý sản phẩm
        btn_qlsp = findViewById(R.id.btn_qlsp);
        btn_qlsp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        quantri_page.this,
                        quanly_sanpham.class
                );
                startActivity(intent);
            }
        });

        // Chức năng quản lý hóa đơn
        btn_qlbill = findViewById(R.id.btn_qlbill);
        btn_qlbill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        quantri_page.this,
                        DanhSachHoaDonActivity.class
                );
                startActivity(intent);
            }
        });

        // Chức năng xem lịch sử hoạt động
        btn_history = findViewById(R.id.btn_history);
        btn_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        quantri_page.this,
                        lichsuhoatdong.class
                );
                startActivity(intent);
            }
        });

        // Chức năng thống kê
        btn_qlthongke = findViewById(R.id.btn_qlthongke);
        btn_qlthongke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        quantri_page.this,
                        thongke.class
                );
                startActivity(intent);
            }
        });
    }
}