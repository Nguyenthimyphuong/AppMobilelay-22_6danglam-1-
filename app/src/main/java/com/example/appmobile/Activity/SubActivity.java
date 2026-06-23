package com.example.appmobile.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appmobile.R;
import com.example.appmobile.model.CartModel;

public class SubActivity extends AppCompatActivity {

    // Hiển thị tên, giá, số lượng
    TextView txtTenSP, txtGiaSP, txtSoLuong;

    // Ảnh sản phẩm và nút quay lại
    ImageView imgProduct, btn_back;

    // Nút trừ, cộng số lượng
    TextView btnTru, btnCong;

    // Nút thêm vào giỏ hàng
    Button btn_giohang;

    // Mã sản phẩm
    int idSP;

    // Thông tin sản phẩm
    int image;
    String name;
    int price;

    // Số lượng người dùng chọn mua, mặc định là 1
    int soLuong = 1;

    // Số lượng sản phẩm còn trong kho
    int soLuongTon;

    // Tài khoản đang đăng nhập
    int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sub);

        // Nhận userId từ trang chủ truyền sang
        userId = getIntent().getIntExtra("userId", -1);

        // Ánh xạ giao diện
        txtTenSP = findViewById(R.id.txt_nameProduct);
        txtGiaSP = findViewById(R.id.txt_priceProduct);
        txtSoLuong = findViewById(R.id.txt_so_luong_chitiet);

        imgProduct = findViewById(R.id.img_sub_Product);
        btn_back = findViewById(R.id.btn_back);

        btnTru = findViewById(R.id.btn_tru_chitiet);
        btnCong = findViewById(R.id.btn_cong_chitiet);
        btn_giohang = findViewById(R.id.btn_giohang);

        // Nhận dữ liệu sản phẩm từ user_Page
        Intent intent = getIntent();

        idSP = intent.getIntExtra("idSP", -1);
        image = intent.getIntExtra("image", 0);
        name = intent.getStringExtra("name");
        price = intent.getIntExtra("price", 0);

        // Nhận số lượng còn trong kho từ trang chủ
        soLuongTon = intent.getIntExtra("soLuong", 1);

        // Nếu vì lý do nào đó số lượng tồn nhỏ hơn 1 thì vẫn đặt tối thiểu là 1
        if (soLuongTon < 1) {
            soLuongTon = 1;
        }

        // Hiển thị dữ liệu sản phẩm lên giao diện
        txtTenSP.setText(name);
        imgProduct.setImageResource(image);
        txtGiaSP.setText(dinhDangTien(price));
        txtSoLuong.setText(String.valueOf(soLuong));

        // Nút quay lại trang trước
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // Nút giảm số lượng
        btnTru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Không cho số lượng nhỏ hơn 1
                if (soLuong > 1) {
                    soLuong--;
                    txtSoLuong.setText(String.valueOf(soLuong));
                }
            }
        });

        // Nút tăng số lượng
        btnCong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Chỉ cho tăng nếu số lượng chọn mua còn nhỏ hơn số lượng trong kho
                if (soLuong < soLuongTon) {
                    soLuong++;
                    txtSoLuong.setText(String.valueOf(soLuong));
                } else {
                    Toast.makeText(
                            SubActivity.this,
                            "Số lượng trong kho chỉ còn " + soLuongTon + " sản phẩm",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }
        });

        // Nút thêm vào giỏ hàng
        btn_giohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean themThanhCong = themVaoGioHang();

                if (themThanhCong) {
                    Toast.makeText(
                            SubActivity.this,
                            "Đã thêm vào giỏ hàng",
                            Toast.LENGTH_SHORT
                    ).show();

                    Intent intentGiohang = new Intent(SubActivity.this, giohang.class);
                    intentGiohang.putExtra("userId", userId);
                    startActivity(intentGiohang);
                }
            }
        });
    }

    // Hàm thêm sản phẩm vào giỏ hàng
    private boolean themVaoGioHang() {

        boolean daTonTai = false;

        // Duyệt qua giỏ hàng để kiểm tra sản phẩm đã có chưa
        for (int i = 0; i < CartModel.cartList.size(); i++) {

            // So sánh bằng idSP để xác định đúng sản phẩm
            if (CartModel.cartList.get(i).getIdSP() == idSP) {

                int soLuongCu = CartModel.cartList.get(i).getSoLuong();

                // Kiểm tra tổng số lượng trong giỏ + số lượng muốn thêm có vượt kho không
                if (soLuongCu + soLuong > soLuongTon) {

                    int soLuongCoTheThem = soLuongTon - soLuongCu;

                    if (soLuongCoTheThem <= 0) {
                        Toast.makeText(
                                SubActivity.this,
                                "Bạn đã thêm hết số lượng hiện có trong kho (" + soLuongTon + " sản phẩm)",
                                Toast.LENGTH_SHORT
                        ).show();
                    } else {
                        Toast.makeText(
                                SubActivity.this,
                                "Số lượng trong kho chỉ còn " + soLuongTon
                                        + " sản phẩm. Bạn chỉ có thể thêm tối đa "
                                        + soLuongCoTheThem + " sản phẩm nữa",
                                Toast.LENGTH_SHORT
                        ).show();
                    }

                    return false;
                }

                // Nếu sản phẩm đã có trong giỏ thì cộng thêm số lượng
                CartModel.cartList.get(i).setSoLuong(soLuongCu + soLuong);

                daTonTai = true;
                break;
            }
        }

        // Nếu sản phẩm chưa có trong giỏ thì thêm mới
        if (!daTonTai) {

            CartModel cart = new CartModel(
                    idSP,
                    name,
                    price,
                    soLuong,
                    image
            );

            CartModel.cartList.add(cart);
        }

        return true;
    }

    // Hàm định dạng tiền: 120000 -> 120.000 VND
    private String dinhDangTien(int tien) {
        return String.format("%,d", tien).replace(",", ".") + " VND";
    }
}