package com.example.appmobile.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.appmobile.Activity.giohang;
import com.example.appmobile.Item.item_user_page;
import com.example.appmobile.R;
import com.example.appmobile.model.CartModel;

import java.util.ArrayList;

public class Adapter_item_user_page extends ArrayAdapter<item_user_page> {

    Activity context;
    int id_Layout;
    ArrayList<item_user_page> myProductUserPage;

    public Adapter_item_user_page(@NonNull Activity context,
                                  int id_Layout,
                                  ArrayList<item_user_page> myProductUserPage) {
        super(context, id_Layout, myProductUserPage);

        this.context = context;
        this.id_Layout = id_Layout;
        this.myProductUserPage = myProductUserPage;
    }

    @NonNull
    @Override
    public View getView(int position,
                        @Nullable View convertView,
                        @NonNull ViewGroup parent) {

        // Nạp giao diện của từng sản phẩm
        LayoutInflater layoutInflater = context.getLayoutInflater();
        convertView = layoutInflater.inflate(id_Layout, parent, false);

        // Lấy sản phẩm hiện tại
        item_user_page item_user = myProductUserPage.get(position);

        // Ánh xạ giao diện trong item sản phẩm
        ImageView imageView = convertView.findViewById(R.id.image_Product);
        TextView txt_TenSP = convertView.findViewById(R.id.txt_tenSP);
        TextView txt_GiaSP = convertView.findViewById(R.id.txt_giaSP);
        Button btnCart = convertView.findViewById(R.id.btn_cart_product);

        // Hiển thị ảnh, tên, giá sản phẩm
        imageView.setImageResource(item_user.getImage());
        txt_TenSP.setText(item_user.getName_Product());
        txt_GiaSP.setText("Giá: " + dinhDangTien(item_user.getPrice_Product()));

        // Bấm nút thêm vào giỏ hàng
        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Thêm sản phẩm vào giỏ hàng
                themVaoGioHang(item_user);

                Toast.makeText(
                        context,
                        "Đã thêm vào giỏ hàng",
                        Toast.LENGTH_SHORT
                ).show();

                /*
                 * Lấy userId từ user_Page.
                 * Vì Adapter này được gọi từ user_Page,
                 * nên context.getIntent() chính là Intent của user_Page.
                 */
                int userId = context.getIntent().getIntExtra("userId", -1);

                /*
                 * Chuyển sang trang giỏ hàng.
                 * Bắt buộc gửi userId sang giohang.
                 * Nếu không gửi, giohang sẽ nhận userId = -1.
                 */
                Intent intent = new Intent(context, giohang.class);
                intent.putExtra("userId", userId);
                context.startActivity(intent);
            }
        });

        return convertView;
    }

    // Hàm thêm sản phẩm vào giỏ hàng
    private void themVaoGioHang(item_user_page sanPham) {

        boolean daTonTai = false;

        /*
         * Kiểm tra sản phẩm đã tồn tại trong giỏ hàng chưa.
         * Nếu có rồi thì tăng số lượng lên 1.
         */
        for (int i = 0; i < CartModel.cartList.size(); i++) {

            if (CartModel.cartList.get(i).getIdSP() == sanPham.getIdSP()) {

                int soLuongCu = CartModel.cartList.get(i).getSoLuong();

                CartModel.cartList.get(i).setSoLuong(soLuongCu + 1);

                daTonTai = true;
                break;
            }
        }

        /*
         * Nếu sản phẩm chưa có trong giỏ hàng
         * thì thêm mới vào CartModel.cartList.
         */
        if (!daTonTai) {

            CartModel cart = new CartModel(
                    sanPham.getIdSP(),
                    sanPham.getName_Product(),
                    sanPham.getPrice_Product(),
                    1,
                    sanPham.getImage()
            );

            CartModel.cartList.add(cart);
        }
    }

    // Hàm định dạng tiền: 120000 -> 120.000 VND
    private String dinhDangTien(int tien) {
        return String.format("%,d", tien).replace(",", ".") + " VND";
    }
}