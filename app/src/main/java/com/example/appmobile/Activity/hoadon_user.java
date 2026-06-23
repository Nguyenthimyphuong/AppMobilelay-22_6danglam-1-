package com.example.appmobile.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appmobile.R;
import com.example.appmobile.model.CartModel;

public class hoadon_user extends AppCompatActivity {

    // Biến lưu id người dùng đang đăng nhập
    int userId;

    // Nút quay lại ở trang hóa đơn
    ImageView btn_back_hoadon;

    // TextView hiển thị mã đơn hàng và thông tin người nhận
    TextView txt_ma_don_hang, txt_thong_tin_nguoi_nhan;

    // TextView hiển thị sản phẩm đã mua, tổng tiền và trạng thái đơn hàng
    TextView txt_san_pham_da_mua, txt_tong_tien_hoadon, txt_trang_thai;

    // Nút tiếp tục mua sắm
    Button btn_tiep_tuc_mua_sam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoadon_user);

        // Ánh xạ các thành phần giao diện từ file XML sang Java
        btn_back_hoadon = findViewById(R.id.btn_back_hoadon);
        txt_ma_don_hang = findViewById(R.id.txt_ma_don_hang);
        txt_thong_tin_nguoi_nhan = findViewById(R.id.txt_thong_tin_nguoi_nhan);
        txt_san_pham_da_mua = findViewById(R.id.txt_san_pham_da_mua);
        txt_tong_tien_hoadon = findViewById(R.id.txt_tong_tien_hoadon);
        txt_trang_thai = findViewById(R.id.txt_trang_thai);
        btn_tiep_tuc_mua_sam = findViewById(R.id.btn_tiep_tuc_mua_sam);

        // Nhận dữ liệu từ trang thanh toán gửi sang
        Intent intent = getIntent();

        // Nhận userId để khi quay về trang chủ vẫn biết user đang đăng nhập
        userId = intent.getIntExtra("userId", -1);

        /*
         * Ở đây trước đó có Toast test:
         * "hoadon = userId"
         * Mình đã xóa để không hiện thông báo code lên màn hình nữa.
         */

        // Nhận thông tin đơn hàng từ Intent
        String maDonHang = intent.getStringExtra("maDonHang");
        String hoTen = intent.getStringExtra("hoTen");
        String soDienThoai = intent.getStringExtra("soDienThoai");
        String diaChi = intent.getStringExtra("diaChi");
        String ghiChu = intent.getStringExtra("ghiChu");
        String hinhThucThanhToan = intent.getStringExtra("hinhThucThanhToan");
        String sanPhamDaMua = intent.getStringExtra("sanPhamDaMua");
        String tongTien = intent.getStringExtra("tongTien");

        // Hiển thị mã đơn hàng
        txt_ma_don_hang.setText("Mã đơn hàng: " + maDonHang);

        // Ghép thông tin người nhận thành một đoạn để hiển thị trên hóa đơn
        String thongTinNguoiNhan =
                "Họ tên: " + hoTen + "\n"
                        + "Số điện thoại: " + soDienThoai + "\n"
                        + "Địa chỉ: " + diaChi + "\n"
                        + "Ghi chú: " + ghiChu + "\n"
                        + "Hình thức thanh toán: " + hinhThucThanhToan;

        // Hiển thị thông tin người nhận
        txt_thong_tin_nguoi_nhan.setText(thongTinNguoiNhan);

        // Hiển thị danh sách sản phẩm đã mua
        txt_san_pham_da_mua.setText(sanPhamDaMua);

        // Hiển thị tổng tiền
        txt_tong_tien_hoadon.setText("Tổng tiền: " + tongTien);

        // Hiển thị trạng thái đơn hàng mặc định
        txt_trang_thai.setVisibility(View.GONE);

        // Bấm mũi tên quay về trang chủ sản phẩm
        btn_back_hoadon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quayVeTrangChu();
            }
        });

        // Bấm tiếp tục mua sắm thì cũng quay về trang chủ sản phẩm
        btn_tiep_tuc_mua_sam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quayVeTrangChu();
            }
        });
    }

    // Hàm quay về trang chủ và xóa giỏ hàng sau khi đặt hàng xong
    private void quayVeTrangChu() {

        // Xóa toàn bộ sản phẩm trong giỏ hàng
        CartModel.cartList.clear();

        // Chuyển về trang chủ sản phẩm
        Intent intent = new Intent(hoadon_user.this, user_Page.class);

        // Gửi lại userId để giữ trạng thái người dùng
        intent.putExtra("userId", userId);

        startActivity(intent);
        finish();
    }
}