package com.example.appmobile.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appmobile.DatabaseHelper;
import com.example.appmobile.R;

import java.util.ArrayList;

public class ChiTietKhachHangActivity extends AppCompatActivity {

    // TextView hiển thị thông tin khách hàng
    TextView txtMa;
    TextView txtTen;
    TextView txtDiaChi;
    TextView txtSDT;
    TextView txtNgayDK;

    // Nút quay lại
    ImageView btnBack;

    // Nút xóa khách hàng
    Button btnXoaKH;

    // ListView hiển thị các đơn hàng khách đã đặt
    ListView lvHoaDon;

    // DatabaseHelper dùng để thao tác SQLite
    DatabaseHelper databaseHelper;

    // userId của khách hàng đang xem chi tiết
    int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitiet_khachhang);

        // Ánh xạ các thành phần giao diện
        txtMa = findViewById(R.id.txt_maKH);
        txtTen = findViewById(R.id.txt_tenKH);
        txtDiaChi = findViewById(R.id.txt_diachi);
        txtSDT = findViewById(R.id.txt_sdt);
        txtNgayDK = findViewById(R.id.txt_ngayDK);

        btnBack = findViewById(R.id.btn_back);
        btnXoaKH = findViewById(R.id.btn_xoaKH);

        lvHoaDon = findViewById(R.id.lv_hoaDon);

        // Khởi tạo database
        databaseHelper = new DatabaseHelper(this);

        // Nhận userId từ màn hình danh sách khách hàng gửi sang
        userId = getIntent().getIntExtra("userId", -1);

        // Nếu không nhận được userId thì báo lỗi
        if (userId == -1) {
            Toast.makeText(
                    ChiTietKhachHangActivity.this,
                    "Lỗi: Không nhận được mã khách hàng",
                    Toast.LENGTH_SHORT
            ).show();

            finish();
            return;
        }

        // Hiển thị thông tin khách hàng
        txtMa.setText("KH" + String.format("%02d", userId));
        txtTen.setText(getIntent().getStringExtra("tenKH"));
        txtDiaChi.setText(getIntent().getStringExtra("diaChi"));
        txtSDT.setText(getIntent().getStringExtra("soDT"));

        /*
         * Nếu chưa có ngày đăng ký thì hiện tạm là "Chưa có thông tin"
         * để tránh bị hiện chữ null.
         */
        String ngayDK = getIntent().getStringExtra("ngayDK");

        if (ngayDK == null || ngayDK.isEmpty()) {
            txtNgayDK.setText("Chưa có thông tin");
        } else {
            txtNgayDK.setText(ngayDK);
        }

        // Load danh sách đơn hàng của khách hàng
        loadHoaDon();

        // Nút quay lại
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // Nút xóa khách hàng
        btnXoaKH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xoaKhachHang();
            }
        });
    }

    // Hàm load các đơn hàng của khách hàng theo userId
    private void loadHoaDon() {

        // Lấy hóa đơn theo userId
        Cursor cursor = databaseHelper.getHoaDonByUserId(userId);

        // Danh sách nội dung hóa đơn để đưa lên ListView
        ArrayList<String> listHoaDon = new ArrayList<>();

        // Nếu khách hàng có hóa đơn
        if (cursor.moveToFirst()) {

            do {
                int idHoaDon = cursor.getInt(
                        cursor.getColumnIndexOrThrow("id")
                );

                String sanPham = cursor.getString(
                        cursor.getColumnIndexOrThrow("sanPham")
                );

                String tongTien = cursor.getString(
                        cursor.getColumnIndexOrThrow("tongTien")
                );

                String hinhThucThanhToan = cursor.getString(
                        cursor.getColumnIndexOrThrow("hinhThucThanhToan")
                );

                String ngayDat = cursor.getString(
                        cursor.getColumnIndexOrThrow("ngayDat")
                );

                /*
                 * Ghép thông tin hóa đơn thành một đoạn chữ dễ đọc
                 */
                String noiDungHoaDon =
                        "Mã hóa đơn: HD" + idHoaDon + "\n"
                                + "Sản phẩm đã mua:\n" + sanPham
                                + "Tổng tiền: " + tongTien + "\n"
                                + "Thanh toán: " + hinhThucThanhToan + "\n"
                                + "Ngày đặt: " + ngayDat;

                listHoaDon.add(noiDungHoaDon);

            } while (cursor.moveToNext());

        } else {

            // Nếu khách hàng chưa đặt đơn nào
            listHoaDon.add("Khách hàng này chưa có đơn hàng nào");
        }

        // Đóng cursor sau khi dùng xong
        cursor.close();

        // Đưa danh sách hóa đơn lên ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                ChiTietKhachHangActivity.this,
                android.R.layout.simple_list_item_1,
                listHoaDon
        );

        lvHoaDon.setAdapter(adapter);
    }

    // Hàm xóa khách hàng
    private void xoaKhachHang() {

        AlertDialog.Builder builder =
                new AlertDialog.Builder(ChiTietKhachHangActivity.this);

        builder.setTitle("Xác nhận");
        builder.setMessage("Bạn có chắc muốn xóa khách hàng này không?");

        builder.setPositiveButton(
                "Xóa",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        SQLiteDatabase db = databaseHelper.getWritableDatabase();

                        // Xóa khách hàng trong bảng users theo id
                        db.delete(
                                "users",
                                "id=?",
                                new String[]{String.valueOf(userId)}
                        );

                        Toast.makeText(
                                ChiTietKhachHangActivity.this,
                                "Đã xóa khách hàng",
                                Toast.LENGTH_SHORT
                        ).show();

                        finish();
                    }
                }
        );

        builder.setNegativeButton(
                "Hủy",
                null
        );

        builder.show();
    }
}