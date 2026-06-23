package com.example.appmobile.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appmobile.R;

public class lichsuhoatdong extends AppCompatActivity {

    // Nút quay lại trên thanh tiêu đề
    ImageView btn_back;

    // TextView dùng để hiển thị lịch sử hoạt động
    TextView historyAddProduct;

    // Biến lưu nội dung lịch sử lấy từ SharedPreferences
    String historys = "";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        // Gắn giao diện XML cho màn hình lịch sử
        setContentView(R.layout.activity_lichsuhoatdong);

        /*
         * Ánh xạ nút quay lại.
         * Lưu ý: Trong file XML phải có ImageView id là btn_back.
         */
        btn_back = findViewById(R.id.btn_back);

        // Ánh xạ TextView hiển thị lịch sử
        historyAddProduct = findViewById(R.id.history);

        /*
         * Lấy dữ liệu lịch sử đã lưu trong SharedPreferences.
         * SharedPreferences dùng để lưu dữ liệu nhỏ trong app.
         */
        SharedPreferences sharedPreferences = getSharedPreferences(
                "historyAddProductss",
                MODE_PRIVATE
        );

        // Lấy chuỗi lịch sử đã lưu, nếu chưa có thì lấy chuỗi rỗng
        historys = sharedPreferences.getString(
                "historyAddProductss",
                ""
        );

        // Nếu chưa có lịch sử thì hiển thị thông báo dễ hiểu hơn
        if (historys.equals("")) {
            historyAddProduct.setText("Chưa có lịch sử hoạt động");
        } else {
            historyAddProduct.setText(historys);
        }

        /*
         * Cho phép TextView cuộn nếu nội dung lịch sử dài.
         * Cách này giữ nguyên ý tưởng ban đầu của bài.
         */
        if (historys.length() > 100) {
            historyAddProduct.setMovementMethod(new ScrollingMovementMethod());
            historyAddProduct.setMaxLines(15);
        }

        /*
         * Xử lý nút quay lại.
         * Vì đây là màn hình lịch sử của admin,
         * nên bấm quay lại sẽ về trang chủ quản trị.
         */
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        lichsuhoatdong.this,
                        quantri_page.class
                );

                startActivity(intent);

                // Đóng màn hình lịch sử hiện tại
                finish();
            }
        });
    }
}