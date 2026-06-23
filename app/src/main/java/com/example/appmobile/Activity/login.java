package com.example.appmobile.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appmobile.DatabaseHelper;
import com.example.appmobile.R;
import com.example.appmobile.model.CartModel;

public class login extends AppCompatActivity {

    // Ô nhập tài khoản, mật khẩu
    EditText edt_us, edt_pw;

    // Nút đăng nhập
    Button btn_login;

    // Nút quay lại
    ImageView btn_back_login;

    // Database kiểm tra tài khoản
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        // Khởi tạo database
        databaseHelper = new DatabaseHelper(this);

        // Ánh xạ giao diện
        edt_us = findViewById(R.id.edt_us);
        edt_pw = findViewById(R.id.edt_pw);
        btn_login = findViewById(R.id.btn_login);
        btn_back_login = findViewById(R.id.btn_back_login);

        // Quay lại trang chính
        btn_back_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Xử lý đăng nhập
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String US = edt_us.getText().toString().trim();
                String PW = edt_pw.getText().toString().trim();

                // Không cho để trống
                if (US.isEmpty() || PW.isEmpty()) {
                    Toast.makeText(
                            login.this,
                            "Tài khoản hoặc mật khẩu không được để trống",
                            Toast.LENGTH_SHORT
                    ).show();
                    return;
                }

                // Tài khoản admin cố định
                if (US.equalsIgnoreCase("ADMIN") && PW.equals("123")) {
                    Intent adminPage = new Intent(login.this, quantri_page.class);
                    startActivity(adminPage);
                    finish();
                    return;
                }

                // Kiểm tra tài khoản user
                boolean checkUser = databaseHelper.checkUser(US, PW);

                if (checkUser) {

                    // Xóa giỏ cũ để tài khoản mới không dính sản phẩm cũ
                    CartModel.cartList.clear();

                    // Lấy userId của tài khoản đang đăng nhập
                    int userId = databaseHelper.getUserId(US, PW);

                    // Sang trang người dùng
                    Intent intent = new Intent(login.this, user_Page.class);

                    // Gửi userId sang trang chủ user
                    intent.putExtra("userId", userId);

                    startActivity(intent);
                    finish();

                } else {
                    Toast.makeText(
                            login.this,
                            "Tài khoản hoặc mật khẩu không chính xác",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }
        });
    }
}