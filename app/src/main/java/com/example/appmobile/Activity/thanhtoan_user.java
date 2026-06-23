package com.example.appmobile.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appmobile.DatabaseHelper;
import com.example.appmobile.R;
import com.example.appmobile.model.CartModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class thanhtoan_user extends AppCompatActivity {

    // Nút quay lại trang giỏ hàng
    ImageView btn_back_thanhtoan;

    // Các ô nhập thông tin người nhận
    EditText edt_ho_ten, edt_so_dien_thoai, edt_dia_chi, edt_ghi_chu;

    // Nhóm chọn hình thức thanh toán
    RadioGroup rg_thanh_toan;

    // TextView hiển thị chi tiết đơn hàng và tổng tiền
    TextView txt_chi_tiet_don_hang, txt_tong_tien_thanhtoan;

    // Nút đặt hàng
    Button btn_dat_hang;

    /*
     * userId dùng để xác định tài khoản đang đặt hàng.
     * Hóa đơn lưu vào SQLite phải có userId này.
     */
    int userId;

    // DatabaseHelper dùng để lưu hóa đơn vào bảng hoadon
    DatabaseHelper databaseHelper;

    // Tổng tiền của đơn hàng
    int tongTien = 0;

    // Nội dung sản phẩm trong đơn hàng
    String noiDungSanPham = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanhtoan_user);

        /*
         * Nhận userId từ giohang gửi sang.
         * Nếu không nhận được thì userId = -1.
         */
        userId = getIntent().getIntExtra("userId", -1);

        // Khởi tạo database
        databaseHelper = new DatabaseHelper(this);

        // Ánh xạ giao diện
        btn_back_thanhtoan = findViewById(R.id.btn_back_thanhtoan);

        edt_ho_ten = findViewById(R.id.edt_ho_ten);
        edt_so_dien_thoai = findViewById(R.id.edt_so_dien_thoai);
        edt_dia_chi = findViewById(R.id.edt_dia_chi);
        edt_ghi_chu = findViewById(R.id.edt_ghi_chu);

        rg_thanh_toan = findViewById(R.id.rg_thanh_toan);

        txt_chi_tiet_don_hang = findViewById(R.id.txt_chi_tiet_don_hang);
        txt_tong_tien_thanhtoan = findViewById(R.id.txt_tong_tien_thanhtoan);

        btn_dat_hang = findViewById(R.id.btn_dat_hang);

        // Hiển thị sản phẩm trong giỏ hàng
        hienThiDonHang();

        // Nút quay lại giỏ hàng
        btn_back_thanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(thanhtoan_user.this, giohang.class);

                // Gửi lại userId để không mất tài khoản đăng nhập
                intent.putExtra("userId", userId);

                startActivity(intent);
                finish();
            }
        });

        // Nút đặt hàng
        btn_dat_hang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLyDatHang();
            }
        });
    }

    // Hiển thị sản phẩm trong giỏ hàng
    private void hienThiDonHang() {

        StringBuilder noiDung = new StringBuilder();

        // Tính lại tổng tiền từ đầu
        tongTien = 0;

        // Nếu giỏ hàng trống
        if (CartModel.cartList.size() == 0) {
            txt_chi_tiet_don_hang.setText("Giỏ hàng đang trống");
            txt_tong_tien_thanhtoan.setText("Tổng tiền: 0 VND");
            return;
        }

        // Duyệt qua từng sản phẩm trong giỏ hàng
        for (int i = 0; i < CartModel.cartList.size(); i++) {

            CartModel cart = CartModel.cartList.get(i);

            noiDung.append(i + 1)
                    .append(". ")
                    .append(cart.getTenSP())
                    .append("\n")
                    .append("Giá: ")
                    .append(dinhDangTien(cart.getGiaSP()))
                    .append("\n")
                    .append("Số lượng: ")
                    .append(cart.getSoLuong())
                    .append("\n")
                    .append("Thành tiền: ")
                    .append(dinhDangTien(cart.thanhTien()))
                    .append("\n\n");

            // Cộng tiền từng sản phẩm
            tongTien = tongTien + cart.thanhTien();
        }

        // Lưu nội dung sản phẩm để đưa vào hóa đơn
        noiDungSanPham = noiDung.toString();

        // Hiển thị lên giao diện
        txt_chi_tiet_don_hang.setText(noiDungSanPham);
        txt_tong_tien_thanhtoan.setText("Tổng tiền: " + dinhDangTien(tongTien));
    }

    // Xử lý đặt hàng
    private void xuLyDatHang() {

        /*
         * Nếu userId = -1 nghĩa là bị mất thông tin tài khoản.
         * Không cho lưu hóa đơn để tránh đơn hàng bị gộp sai tài khoản.
         */
        if (userId == -1) {
            Toast.makeText(
                    thanhtoan_user.this,
                    "Lỗi: Không xác định được tài khoản đăng nhập",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        // Kiểm tra giỏ hàng
        if (CartModel.cartList.size() == 0) {
            Toast.makeText(
                    thanhtoan_user.this,
                    "Giỏ hàng đang trống",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        /*
         * Kiểm tra số lượng sản phẩm admin thêm.
         * Nếu không đủ số lượng thì dừng lại, không cho đặt hàng.
         */
        if (!kiemTraSoLuongTrongKho()) {
            return;
        }

        // Lấy thông tin người nhận
        String hoTen = edt_ho_ten.getText().toString().trim();
        String soDienThoai = edt_so_dien_thoai.getText().toString().trim();
        String diaChi = edt_dia_chi.getText().toString().trim();
        String ghiChu = edt_ghi_chu.getText().toString().trim();

        // Kiểm tra họ tên
        if (hoTen.isEmpty()) {
            Toast.makeText(
                    thanhtoan_user.this,
                    "Vui lòng nhập họ tên người nhận",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        // Kiểm tra số điện thoại
        if (soDienThoai.isEmpty()) {
            Toast.makeText(
                    thanhtoan_user.this,
                    "Vui lòng nhập số điện thoại",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        // Số điện thoại phải gồm 10 số và bắt đầu bằng số 0
        if (!soDienThoai.matches("^0[0-9]{9}$")) {
            Toast.makeText(
                    thanhtoan_user.this,
                    "Số điện thoại phải gồm 10 số và bắt đầu bằng 0",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        // Kiểm tra địa chỉ
        if (diaChi.isEmpty()) {
            Toast.makeText(
                    thanhtoan_user.this,
                    "Vui lòng nhập địa chỉ nhận hàng",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        // Nếu không nhập ghi chú thì mặc định là Không có
        if (ghiChu.isEmpty()) {
            ghiChu = "Không có";
        }

        // Lấy hình thức thanh toán
        String hinhThucThanhToan;

        int idThanhToan = rg_thanh_toan.getCheckedRadioButtonId();

        if (idThanhToan == R.id.rb_chuyen_khoan) {
            hinhThucThanhToan = "Chuyển khoản ngân hàng";
        } else {
            hinhThucThanhToan = "Thanh toán khi nhận hàng COD";
        }

        /*
         * Lấy ngày giờ hiện tại để lưu vào hóa đơn.
         * Ví dụ kết quả: 21/06/2026 22:55
         */
        SimpleDateFormat sdf = new SimpleDateFormat(
                "dd/MM/yyyy HH:mm",
                Locale.getDefault()
        );

        String ngayDat = sdf.format(new Date());

        /*
         * Lưu hóa đơn vào bảng hoadon.
         * Quan trọng nhất là truyền đúng userId và ngày đặt thật.
         */
        boolean result = databaseHelper.insertHoaDon(
                userId,
                hoTen,
                soDienThoai,
                diaChi,
                ghiChu,
                hinhThucThanhToan,
                noiDungSanPham,
                dinhDangTien(tongTien),
                ngayDat
        );

        // Nếu lưu hóa đơn thành công
        if (result) {

            // Trừ số lượng sản phẩm trong kho
            capNhatSoLuongSanPhamSauKhiDatHang();

            // Xóa giỏ hàng sau khi đặt hàng
            CartModel.cartList.clear();

            Toast.makeText(
                    thanhtoan_user.this,
                    "Đặt hàng thành công",
                    Toast.LENGTH_SHORT
            ).show();

            // Chuyển sang trang lịch sử mua hàng
            Intent intent = new Intent(thanhtoan_user.this, don_da_dat_user.class);

            // Gửi userId để trang lịch sử chỉ hiện đơn của tài khoản hiện tại
            intent.putExtra("userId", userId);

            startActivity(intent);
            finish();

        } else {

            Toast.makeText(
                    thanhtoan_user.this,
                    "Lưu hóa đơn thất bại",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    /*
     * Hàm kiểm tra số lượng sản phẩm trong kho.
     *
     * Chỉ kiểm tra sản phẩm do admin thêm:
     * idSP > 0
     *
     * Không kiểm tra sản phẩm mặc định:
     * idSP <= 0
     */
    private boolean kiemTraSoLuongTrongKho() {

        SQLiteDatabase db = openOrCreateDatabase(
                "dbQLNV.db",
                MODE_PRIVATE,
                null
        );

        for (int i = 0; i < CartModel.cartList.size(); i++) {

            CartModel cart = CartModel.cartList.get(i);

            int idSP = cart.getIdSP();
            int soLuongMua = cart.getSoLuong();

            /*
             * idSP <= 0 là sản phẩm mặc định.
             * Sản phẩm mặc định mua bao nhiêu cũng được nên bỏ qua.
             */
            if (idSP <= 0) {
                continue;
            }

            /*
             * idSP > 0 là sản phẩm admin thêm vào.
             * Cần kiểm tra số lượng còn trong bảng SanPham.
             */
            Cursor cursor = db.rawQuery(
                    "SELECT tenSP, soLuong FROM SanPham WHERE idSP = ?",
                    new String[]{String.valueOf(idSP)}
            );

            if (cursor.moveToFirst()) {

                String tenSP = cursor.getString(
                        cursor.getColumnIndexOrThrow("tenSP")
                );

                int soLuongCon = cursor.getInt(
                        cursor.getColumnIndexOrThrow("soLuong")
                );

                cursor.close();

                if (soLuongMua > soLuongCon) {
                    db.close();

                    Toast.makeText(
                            thanhtoan_user.this,
                            "Sản phẩm " + tenSP + " chỉ còn " + soLuongCon + " sản phẩm",
                            Toast.LENGTH_LONG
                    ).show();

                    return false;
                }

            } else {

                cursor.close();
                db.close();

                Toast.makeText(
                        thanhtoan_user.this,
                        "Không tìm thấy sản phẩm trong kho",
                        Toast.LENGTH_SHORT
                ).show();

                return false;
            }
        }

        db.close();

        return true;
    }

    // Trừ số lượng sản phẩm trong kho sau khi đặt hàng
    private void capNhatSoLuongSanPhamSauKhiDatHang() {

        SQLiteDatabase db = openOrCreateDatabase(
                "dbQLNV.db",
                MODE_PRIVATE,
                null
        );

        // Duyệt từng sản phẩm trong giỏ hàng
        for (int i = 0; i < CartModel.cartList.size(); i++) {

            int idSP = CartModel.cartList.get(i).getIdSP();
            int soLuongMua = CartModel.cartList.get(i).getSoLuong();

            /*
             * idSP > 0 là sản phẩm admin thêm trong SQLite.
             * idSP <= 0 là sản phẩm mặc định nên không trừ kho.
             */
            if (idSP > 0) {
                db.execSQL(
                        "UPDATE SanPham SET soLuong = soLuong - ? WHERE idSP = ?",
                        new Object[]{soLuongMua, idSP}
                );
            }
        }

        db.close();
    }

    // Hàm định dạng tiền: 120000 -> 120.000 VND
    private String dinhDangTien(int tien) {
        return String.format("%,d", tien).replace(",", ".") + " VND";
    }
}