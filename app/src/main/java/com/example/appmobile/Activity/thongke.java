package com.example.appmobile.Activity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class thongke extends AppCompatActivity {

    /*
     * Nút quay lại.
     * Trong activity_thongke.xml nút này là ImageView,
     * nên bên Java cũng phải khai báo ImageView.
     */
    ImageView btn_back_thongke;

    // Ô nhập ngày cần thống kê
    EditText edtNgayThongKe;

    // Nút lấy ngày hôm nay và nút thống kê
    Button btnHomNay, btnThongKe;

    // Các TextView hiển thị kết quả thống kê
    TextView txtNgayDangXem;
    TextView txtSoDonTrongNgay;
    TextView txtDoanhThuTrongNgay;
    TextView txtDanhSachDonTrongNgay;

    // DatabaseHelper dùng để đọc dữ liệu hóa đơn
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongke);

        // Khởi tạo database
        databaseHelper = new DatabaseHelper(this);

        // Ánh xạ nút quay lại
        btn_back_thongke = findViewById(R.id.btn_back_thongke);

        // Ánh xạ ô nhập ngày
        edtNgayThongKe = findViewById(R.id.edtNgayThongKe);

        // Ánh xạ 2 nút chức năng
        btnHomNay = findViewById(R.id.btnHomNay);
        btnThongKe = findViewById(R.id.btnThongKe);

        // Ánh xạ các TextView hiển thị kết quả
        txtNgayDangXem = findViewById(R.id.txtNgayDangXem);
        txtSoDonTrongNgay = findViewById(R.id.txtSoDonTrongNgay);
        txtDoanhThuTrongNgay = findViewById(R.id.txtDoanhThuTrongNgay);
        txtDanhSachDonTrongNgay = findViewById(R.id.txtDanhSachDonTrongNgay);

        // Khi mở trang thống kê, tự động lấy ngày hôm nay
        String ngayHomNay = layNgayHomNay();

        // Đưa ngày hôm nay vào ô nhập
        edtNgayThongKe.setText(ngayHomNay);

        // Thống kê luôn ngày hôm nay
        thongKeTheoNgay(ngayHomNay);

        // Bấm nút quay lại thì đóng trang thống kê
        btn_back_thongke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // Bấm nút Hôm nay
        btnHomNay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Lấy ngày hôm nay
                String ngayHomNay = layNgayHomNay();

                // Đưa ngày hôm nay vào ô nhập
                edtNgayThongKe.setText(ngayHomNay);

                // Thống kê ngày hôm nay
                thongKeTheoNgay(ngayHomNay);
            }
        });

        // Bấm nút Thống kê
        btnThongKe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Lấy ngày người dùng nhập
                String ngayCanThongKe = edtNgayThongKe.getText().toString().trim();

                // Nếu chưa nhập ngày thì báo lỗi
                if (ngayCanThongKe.isEmpty()) {
                    Toast.makeText(
                            thongke.this,
                            "Vui lòng nhập ngày cần thống kê",
                            Toast.LENGTH_SHORT
                    ).show();
                    return;
                }

                /*
                 * Kiểm tra định dạng ngày.
                 * Định dạng đúng: dd/MM/yyyy
                 * Ví dụ: 22/06/2026
                 */
                if (!ngayCanThongKe.matches("\\d{2}/\\d{2}/\\d{4}")) {
                    Toast.makeText(
                            thongke.this,
                            "Ngày phải có dạng dd/MM/yyyy. Ví dụ: 22/06/2026",
                            Toast.LENGTH_SHORT
                    ).show();
                    return;
                }

                // Thống kê theo ngày người dùng nhập
                thongKeTheoNgay(ngayCanThongKe);
            }
        });
    }

    /*
     * Hàm thống kê hóa đơn theo ngày.
     *
     * Trong thanhtoan_user.java, ngày đặt đang lưu dạng:
     * 22/06/2026 08:39
     *
     * Khi người dùng nhập:
     * 22/06/2026
     *
     * Ta dùng LIKE '22/06/2026%' để lấy tất cả đơn trong ngày đó.
     */
    private void thongKeTheoNgay(String ngayCanThongKe) {

        // Biến đếm số đơn trong ngày
        int soDonTrongNgay = 0;

        // Biến cộng tổng doanh thu trong ngày
        long doanhThuTrongNgay = 0;

        // Chuỗi dùng để hiển thị danh sách đơn
        StringBuilder danhSachDon = new StringBuilder();

        // Hiển thị ngày đang xem
        txtNgayDangXem.setText("Ngày đang xem: " + ngayCanThongKe);

        try {
            // Mở database để đọc dữ liệu
            SQLiteDatabase db = databaseHelper.getReadableDatabase();

            /*
             * Lấy hóa đơn trong bảng hoadon theo ngày.
             * Cột ngayDat lưu dạng dd/MM/yyyy HH:mm.
             */
            Cursor cursor = db.rawQuery(
                    "SELECT id, hoTen, tongTien, ngayDat FROM hoadon WHERE ngayDat LIKE ? ORDER BY id DESC",
                    new String[]{ngayCanThongKe + "%"}
            );

            // Duyệt từng hóa đơn tìm được
            while (cursor.moveToNext()) {

                // Lấy mã hóa đơn
                int id = cursor.getInt(
                        cursor.getColumnIndexOrThrow("id")
                );

                // Lấy tên khách hàng
                String hoTen = cursor.getString(
                        cursor.getColumnIndexOrThrow("hoTen")
                );

                // Lấy tổng tiền dạng chữ, ví dụ: 150.000 VND
                String tongTien = cursor.getString(
                        cursor.getColumnIndexOrThrow("tongTien")
                );

                // Lấy ngày đặt đầy đủ, ví dụ: 22/06/2026 08:39
                String ngayDat = cursor.getString(
                        cursor.getColumnIndexOrThrow("ngayDat")
                );

                // Mỗi dòng trong cursor là 1 đơn hàng
                soDonTrongNgay++;

                // Cộng tổng doanh thu
                doanhThuTrongNgay = doanhThuTrongNgay + chuyenTienThanhSo(tongTien);

                // Ghép nội dung đơn hàng để hiển thị
                danhSachDon.append("HD")
                        .append(id)
                        .append(" - ")
                        .append(hoTen)
                        .append("\n")
                        .append("Ngày đặt: ")
                        .append(ngayDat)
                        .append("\n")
                        .append("Tổng tiền: ")
                        .append(tongTien)
                        .append("\n\n");
            }

            // Đóng cursor sau khi dùng xong
            cursor.close();

            // Nếu không có đơn nào trong ngày đó
            if (soDonTrongNgay == 0) {
                danhSachDon.append("Không có đơn hàng nào trong ngày ")
                        .append(ngayCanThongKe);
            }

            // Hiển thị số đơn
            txtSoDonTrongNgay.setText(soDonTrongNgay + " đơn");

            // Hiển thị doanh thu
            txtDoanhThuTrongNgay.setText(dinhDangTien(doanhThuTrongNgay));

            // Hiển thị danh sách đơn
            txtDanhSachDonTrongNgay.setText(danhSachDon.toString());

        } catch (Exception e) {

            // Nếu chưa có bảng hóa đơn hoặc lỗi đọc dữ liệu
            txtSoDonTrongNgay.setText("0 đơn");
            txtDoanhThuTrongNgay.setText("0 VND");
            txtDanhSachDonTrongNgay.setText("Chưa có dữ liệu hóa đơn");

            Toast.makeText(
                    thongke.this,
                    "Chưa có dữ liệu hóa đơn để thống kê",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    // Hàm lấy ngày hôm nay theo dạng dd/MM/yyyy
    private String layNgayHomNay() {

        SimpleDateFormat sdf = new SimpleDateFormat(
                "dd/MM/yyyy",
                Locale.getDefault()
        );

        return sdf.format(new Date());
    }

    /*
     * Chuyển tiền từ dạng chữ sang số.
     *
     * Ví dụ:
     * "150.000 VND" -> 150000
     * "1.250.000 VND" -> 1250000
     */
    private long chuyenTienThanhSo(String tienDangChu) {

        // Nếu chuỗi tiền bị null thì trả về 0
        if (tienDangChu == null) {
            return 0;
        }

        /*
         * Xóa tất cả ký tự không phải số.
         * Ví dụ:
         * "150.000 VND" -> "150000"
         */
        String chiLaySo = tienDangChu.replaceAll("[^0-9]", "");

        // Nếu không còn số nào thì trả về 0
        if (chiLaySo.isEmpty()) {
            return 0;
        }

        // Chuyển chuỗi số thành số long
        return Long.parseLong(chiLaySo);
    }

    // Định dạng tiền: 150000 -> 150.000 VND
    private String dinhDangTien(long tien) {
        return String.format(Locale.US, "%,d", tien).replace(",", ".") + " VND";
    }
}