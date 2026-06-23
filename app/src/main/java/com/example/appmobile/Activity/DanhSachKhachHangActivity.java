package com.example.appmobile.Activity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appmobile.Adapter.KhachHangAdapter;
import com.example.appmobile.DatabaseHelper;
import com.example.appmobile.R;
import com.example.appmobile.model.KhachHang;

import java.util.ArrayList;

public class DanhSachKhachHangActivity extends AppCompatActivity {

    // Nút quay lại
    ImageView btnBack;

    // Ô nhập tìm kiếm
    EditText edtSearch;

    // Nút tìm kiếm
    Button btnSearch;

    // RecyclerView hiển thị danh sách khách hàng
    RecyclerView rvKhachHang;

    // Hiển thị tổng số khách hàng
    TextView txtTongKH;

    // DatabaseHelper dùng để mở database UserDB
    DatabaseHelper databaseHelper;

    // Database dùng cho adapter
    SQLiteDatabase db;

    // Adapter khách hàng
    KhachHangAdapter adapter;

    // Danh sách toàn bộ khách hàng
    ArrayList<KhachHang> list = new ArrayList<>();

    // Danh sách khách hàng sau khi tìm kiếm
    ArrayList<KhachHang> listTimKiem = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danhsach_khachhang);

        // Ánh xạ giao diện
        btnBack = findViewById(R.id.btn_back);
        edtSearch = findViewById(R.id.edtSearch);
        btnSearch = findViewById(R.id.btnSearch);
        rvKhachHang = findViewById(R.id.rvKhachHang);
        txtTongKH = findViewById(R.id.txtTongKH);

        // Khởi tạo DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        /*
         * Dùng database từ DatabaseHelper.
         * Không dùng dbQLKH.db nữa vì khách hàng đang nằm trong UserDB.
         */
        db = databaseHelper.getWritableDatabase();

        // Load dữ liệu khách hàng
        loadData();

        // Hiển thị tổng khách hàng
        txtTongKH.setText("Tổng khách hàng: " + list.size());

        // Khởi tạo adapter
        adapter = new KhachHangAdapter(
                DanhSachKhachHangActivity.this,
                list,
                db
        );

        // Hiển thị RecyclerView theo chiều dọc
        rvKhachHang.setLayoutManager(
                new LinearLayoutManager(this)
        );

        // Gắn adapter vào RecyclerView
        rvKhachHang.setAdapter(adapter);

        // Nút quay lại
        btnBack.setOnClickListener(v -> {
            finish();
        });

        // Nút tìm kiếm
        btnSearch.setOnClickListener(v -> {

            String keyword = edtSearch.getText()
                    .toString()
                    .trim()
                    .toLowerCase();

            // Nếu không nhập gì thì hiện lại toàn bộ khách hàng
            if (keyword.isEmpty()) {

                adapter = new KhachHangAdapter(
                        DanhSachKhachHangActivity.this,
                        list,
                        db
                );

                rvKhachHang.setAdapter(adapter);

                txtTongKH.setText("Tổng khách hàng: " + list.size());

                return;
            }

            // Xóa danh sách tìm kiếm cũ
            listTimKiem.clear();

            // Tìm khách hàng theo họ tên
            for (int i = 0; i < list.size(); i++) {

                KhachHang kh = list.get(i);

                if (kh.getHoten().toLowerCase().contains(keyword)) {
                    listTimKiem.add(kh);
                }
            }

            // Hiển thị danh sách tìm kiếm
            adapter = new KhachHangAdapter(
                    DanhSachKhachHangActivity.this,
                    listTimKiem,
                    db
            );

            rvKhachHang.setAdapter(adapter);

            txtTongKH.setText("Kết quả tìm thấy: " + listTimKiem.size());
        });
    }

    // Hàm lấy dữ liệu khách hàng từ bảng users
    private void loadData() {

        // Xóa dữ liệu cũ trước khi load lại
        list.clear();

        SQLiteDatabase readableDb = databaseHelper.getReadableDatabase();

        Cursor cursor = readableDb.rawQuery(
                "SELECT * FROM users",
                null
        );

        while (cursor.moveToNext()) {

            int id = cursor.getInt(
                    cursor.getColumnIndexOrThrow("id")
            );

            String hoTen = cursor.getString(
                    cursor.getColumnIndexOrThrow("hoten")
            );

            String sdt = cursor.getString(
                    cursor.getColumnIndexOrThrow("sdt")
            );

            String diaChi = cursor.getString(
                    cursor.getColumnIndexOrThrow("diachi")
            );

            /*
             * id và userId dùng chung id của bảng users.
             * Khi bấm chi tiết khách hàng, userId này sẽ dùng để lấy hóa đơn.
             */
            list.add(
                    new KhachHang(
                            id,
                            id,
                            hoTen,
                            sdt,
                            diaChi
                    )
            );
        }

        cursor.close();
    }
}