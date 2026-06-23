package com.example.appmobile.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appmobile.DatabaseHelper;
import com.example.appmobile.R;

public class DangKy extends AppCompatActivity {

    EditText edtUsername, edtPassWord;
    EditText edtHoTen,
            edtSDT,
            edtDiaChi;

    Button btnDangKy;

    ImageView btn_back_dangky;
    TextView txt_quay_lai_dang_nhap;

    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);

        // Ánh xạ giao diện
        edtUsername = findViewById(R.id.edtUsername);
        edtPassWord = findViewById(R.id.edtPassWord);
        edtHoTen = findViewById(R.id.edtHoTen);
        edtSDT = findViewById(R.id.edtSDT);
        edtDiaChi = findViewById(R.id.edtDiaChi);
        btnDangKy = findViewById(R.id.btnDangKy);

        btn_back_dangky = findViewById(R.id.btn_back_dangky);
        txt_quay_lai_dang_nhap = findViewById(R.id.txt_quay_lai_dang_nhap);

        db = new DatabaseHelper(this);

        // Bấm mũi tên quay lại trang đăng nhập
        btn_back_dangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DangKy.this, login.class);
                startActivity(intent);
                finish();
            }
        });

        // Bấm dòng chữ quay lại đăng nhập
        txt_quay_lai_dang_nhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DangKy.this, login.class);
                startActivity(intent);
                finish();
            }
        });

        // Xử lý nút đăng ký
        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = edtUsername.getText().toString().trim();
                String password = edtPassWord.getText().toString().trim();
                String hoten = edtHoTen.getText().toString().trim();
                String sdt = edtSDT.getText().toString().trim();
                String diachi = edtDiaChi.getText().toString().trim();

                if(username.isEmpty() || password.isEmpty() || hoten.isEmpty() || sdt.isEmpty() || diachi.isEmpty()) {

                    Toast.makeText(DangKy.this,
                            "Vui lòng nhập đầy đủ thông tin",
                            Toast.LENGTH_SHORT
                    ).show();

                    return;
                }

                if (username.equalsIgnoreCase("ADMIN")) {
                    Toast.makeText(
                            DangKy.this,
                            "Không được sử dụng tài khoản ADMIN",
                            Toast.LENGTH_SHORT
                    ).show();
                    return;
                }

                boolean result =
                        db.registerUser(
                                username,
                                password,
                                hoten,
                                sdt,
                                diachi
                        );

                if (result) {
                    Toast.makeText(
                            DangKy.this,
                            "Đăng ký thành công",
                            Toast.LENGTH_SHORT
                    ).show();

                    Intent intent = new Intent(DangKy.this, login.class);
                    startActivity(intent);
                    finish();

                } else {
                    Toast.makeText(
                            DangKy.this,
                            "Tài khoản đã tồn tại",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }
        });
    }
}