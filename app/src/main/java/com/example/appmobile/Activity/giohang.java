package com.example.appmobile.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appmobile.Adapter.Adapter_giohang;
import com.example.appmobile.R;
import com.example.appmobile.model.CartModel;

public class giohang extends AppCompatActivity {

    /*
     * userId dùng để biết người dùng nào đang đăng nhập.
     * userId này được truyền từ user_Page hoặc Adapter_item_user_page sang.
     */
    int userId;

    // Khai báo các thành phần giao diện
    ImageView btn_back;
    ListView lv_giohang;
    TextView txt_tong_tien;
    Button btn_thanhtoan;

    // Adapter dùng để hiển thị sản phẩm trong giỏ hàng
    Adapter_giohang adapterGiohang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giohang);

        /*
         * Nhận userId từ trang trước.
         * Nếu không nhận được thì userId = -1.
         */
        userId = getIntent().getIntExtra("userId", -1);

        // Ánh xạ giao diện
        btn_back = findViewById(R.id.btn_back);
        lv_giohang = findViewById(R.id.lv_giohang);
        txt_tong_tien = findViewById(R.id.txt_tong_tien);
        btn_thanhtoan = findViewById(R.id.btn_thanhtoan);

        // Đưa danh sách sản phẩm trong CartModel.cartList lên ListView
        adapterGiohang = new Adapter_giohang(giohang.this, CartModel.cartList);
        lv_giohang.setAdapter(adapterGiohang);

        // Tính và hiển thị tổng tiền
        capNhatTongTien();

        // Nút quay lại trang chủ sản phẩm
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(giohang.this, user_Page.class);

                // Gửi lại userId để không bị mất tài khoản đăng nhập
                intent.putExtra("userId", userId);

                startActivity(intent);
                finish();
            }
        });

        // Nút thanh toán
        btn_thanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*
                 * Nếu userId = -1 nghĩa là app đang mất thông tin tài khoản.
                 * Không cho thanh toán để tránh lưu hóa đơn sai người dùng.
                 */
                if (userId == -1) {
                    Toast.makeText(
                            giohang.this,
                            "Lỗi: Không nhận được thông tin người dùng",
                            Toast.LENGTH_SHORT
                    ).show();
                    return;
                }

                // Nếu giỏ hàng trống thì báo lỗi
                if (CartModel.cartList.size() == 0) {
                    Toast.makeText(
                            giohang.this,
                            "Giỏ hàng đang trống",
                            Toast.LENGTH_SHORT
                    ).show();
                } else {

                    /*
                     * Chuyển sang trang thanh toán.
                     * Bắt buộc gửi userId sang thanhtoan_user.
                     */
                    Intent intent = new Intent(giohang.this, thanhtoan_user.class);
                    intent.putExtra("userId", userId);
                    startActivity(intent);
                }
            }
        });
    }

    // Hàm tính tổng tiền trong giỏ hàng
    public void capNhatTongTien() {

        int tongTien = 0;

        // Duyệt qua từng sản phẩm trong giỏ hàng
        for (int i = 0; i < CartModel.cartList.size(); i++) {
            tongTien = tongTien + CartModel.cartList.get(i).thanhTien();
        }

        // Hiển thị tổng tiền
        txt_tong_tien.setText("Tổng tiền: " + dinhDangTien(tongTien));
    }

    // Hàm định dạng tiền: 120000 -> 120.000 VND
    private String dinhDangTien(int tien) {
        return String.format("%,d", tien).replace(",", ".") + " VND";
    }
}