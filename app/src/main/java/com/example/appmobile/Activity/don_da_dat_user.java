package com.example.appmobile.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appmobile.DatabaseHelper;
import com.example.appmobile.R;

import java.util.ArrayList;

public class don_da_dat_user extends AppCompatActivity {

    // Nút quay lại trên thanh tiêu đề
    ImageView btn_back_don_da_dat;

    // Nút về trang chủ ở cuối màn hình
    Button btn_ve_trang_chu;

    // ListView hiển thị danh sách đơn đã đặt
    ListView lv_don_da_dat;

    // userId của tài khoản đang đăng nhập
    int userId;

    // DatabaseHelper dùng để lấy dữ liệu hóa đơn
    DatabaseHelper databaseHelper;

    // Danh sách dạng chữ để đưa lên ListView
    ArrayList<String> danhSachDonHang;

    // Adapter để gắn dữ liệu vào ListView
    ArrayAdapter<String> adapterDonHang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_don_da_dat_user);

        /*
         * Nhận userId từ trang khác gửi sang.
         * userId dùng để chỉ hiển thị đơn hàng của đúng tài khoản đang đăng nhập.
         */
        userId = getIntent().getIntExtra("userId", -1);

        // Khởi tạo database
        databaseHelper = new DatabaseHelper(this);

        // Ánh xạ đúng id theo XML bạn vừa gửi
        btn_back_don_da_dat = findViewById(R.id.btn_back_don_da_dat);
        btn_ve_trang_chu = findViewById(R.id.btn_ve_trang_chu);
        lv_don_da_dat = findViewById(R.id.lv_don_da_dat);

        // Khởi tạo danh sách đơn hàng
        danhSachDonHang = new ArrayList<>();

        /*
         * Dùng adapter mặc định của Android để hiển thị danh sách.
         * Mỗi phần tử trong danhSachDonHang sẽ là 1 đơn hàng.
         */
        adapterDonHang = new ArrayAdapter<>(
                don_da_dat_user.this,
                android.R.layout.simple_list_item_1,
                danhSachDonHang
        );

        // Gắn adapter vào ListView
        lv_don_da_dat.setAdapter(adapterDonHang);

        // Load danh sách đơn đã đặt
        loadDonDaDat();

        // Nút quay lại
        btn_back_don_da_dat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                veTrangChu();
            }
        });

        // Nút về trang chủ
        btn_ve_trang_chu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                veTrangChu();
            }
        });
    }

    // Hàm lấy danh sách đơn hàng đã đặt của người dùng
    private void loadDonDaDat() {

        // Nếu userId bị lỗi thì không thể lấy đơn hàng
        if (userId == -1) {
            Toast.makeText(
                    don_da_dat_user.this,
                    "Không xác định được tài khoản đăng nhập",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        // Xóa dữ liệu cũ trước khi load lại
        danhSachDonHang.clear();

        try {
            // Mở database chứa bảng hoadon
            SQLiteDatabase db = databaseHelper.getReadableDatabase();

            /*
             * Lấy hóa đơn theo userId.
             * Chỉ lấy đơn hàng của tài khoản đang đăng nhập.
             */
            Cursor cursor = db.rawQuery(
                    "SELECT * FROM hoadon WHERE userId = ? ORDER BY id DESC",
                    new String[]{String.valueOf(userId)}
            );

            // Nếu có dữ liệu hóa đơn
            if (cursor.moveToFirst()) {

                do {
                    // Lấy dữ liệu từng cột trong bảng hoadon
                    int id = cursor.getInt(
                            cursor.getColumnIndexOrThrow("id")
                    );

                    String hoTen = cursor.getString(
                            cursor.getColumnIndexOrThrow("hoTen")
                    );

                    String soDienThoai = cursor.getString(
                            cursor.getColumnIndexOrThrow("soDienThoai")
                    );

                    String diaChi = cursor.getString(
                            cursor.getColumnIndexOrThrow("diaChi")
                    );

                    String hinhThucThanhToan = cursor.getString(
                            cursor.getColumnIndexOrThrow("hinhThucThanhToan")
                    );

                    String sanPham = cursor.getString(
                            cursor.getColumnIndexOrThrow("sanPham")
                    );

                    String tongTien = cursor.getString(
                            cursor.getColumnIndexOrThrow("tongTien")
                    );

                    /*
                     * Lấy ngày đặt.
                     * Cột này được lưu từ thanhtoan_user.java.
                     * Ví dụ: 22/06/2026 00:05
                     */
                    String ngayDat = cursor.getString(
                            cursor.getColumnIndexOrThrow("ngayDat")
                    );

                    // Ghép nội dung 1 đơn hàng thành chuỗi để hiển thị
                    String noiDungDonHang =
                            "Mã đơn hàng: HD" + id + "\n" +
                                    "Ngày đặt: " + ngayDat + "\n" +
                                    "Người nhận: " + hoTen + "\n" +
                                    "SĐT: " + soDienThoai + "\n" +
                                    "Địa chỉ: " + diaChi + "\n\n" +
                                    "Sản phẩm đã mua:\n" +
                                    sanPham + "\n" +
                                    "Tổng tiền: " + tongTien + "\n" +
                                    "Thanh toán: " + hinhThucThanhToan;

                    // Thêm đơn hàng vào danh sách
                    danhSachDonHang.add(noiDungDonHang);

                } while (cursor.moveToNext());

            } else {

                // Nếu chưa có đơn hàng nào
                danhSachDonHang.add("Bạn chưa có đơn hàng nào");
            }

            cursor.close();

            // Cập nhật lại ListView
            adapterDonHang.notifyDataSetChanged();

        } catch (Exception e) {

            Toast.makeText(
                    don_da_dat_user.this,
                    "Lỗi khi tải đơn hàng",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    // Hàm quay về trang chủ người dùng
    private void veTrangChu() {

        Intent intent = new Intent(don_da_dat_user.this, user_Page.class);

        // Gửi lại userId để trang chủ vẫn biết tài khoản đang đăng nhập
        intent.putExtra("userId", userId);

        startActivity(intent);
        finish();
    }
}