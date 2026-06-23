package com.example.appmobile.Adapter;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appmobile.Activity.giohang;
import com.example.appmobile.R;
import com.example.appmobile.model.CartModel;

import java.util.ArrayList;

public class Adapter_giohang extends BaseAdapter {

    // Activity đang sử dụng adapter này
    Activity context;

    // Danh sách sản phẩm trong giỏ hàng
    ArrayList<CartModel> cartList;

    // Nhận dữ liệu từ giohang.java truyền sang
    public Adapter_giohang(Activity context, ArrayList<CartModel> cartList) {
        this.context = context;
        this.cartList = cartList;
    }

    // Trả về số lượng sản phẩm trong giỏ
    @Override
    public int getCount() {
        return cartList.size();
    }

    // Trả về sản phẩm tại vị trí position
    @Override
    public Object getItem(int position) {
        return cartList.get(position);
    }

    // Trả về id của item
    @Override
    public long getItemId(int position) {
        return position;
    }

    // Đổ dữ liệu từng sản phẩm lên item_giohang.xml
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Nạp giao diện item giỏ hàng
        LayoutInflater inflater = context.getLayoutInflater();

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_giohang, parent, false);
        }

        // Lấy sản phẩm hiện tại
        CartModel cart = cartList.get(position);

        // Ánh xạ giao diện
        ImageView imgCart = convertView.findViewById(R.id.img_cart);
        TextView txtName = convertView.findViewById(R.id.txt_cart_name);
        TextView txtPrice = convertView.findViewById(R.id.txt_cart_price);
        TextView txtSoLuong = convertView.findViewById(R.id.txt_so_luong);
        TextView txtThanhTien = convertView.findViewById(R.id.txt_thanh_tien);

        // Ánh xạ nút trừ, cộng, xóa
        TextView btnTru = convertView.findViewById(R.id.btn_tru);
        TextView btnCong = convertView.findViewById(R.id.btn_cong);
        TextView btnXoa = convertView.findViewById(R.id.btn_xoa);

        // Hiển thị thông tin sản phẩm
        imgCart.setImageResource(cart.getHinhSP());
        txtName.setText(cart.getTenSP());
        txtPrice.setText("Giá: " + dinhDangTien(cart.getGiaSP()));
        txtSoLuong.setText(String.valueOf(cart.getSoLuong()));
        txtThanhTien.setText("Thành tiền: " + dinhDangTien(cart.thanhTien()));

        // Bấm nút cộng
        btnCong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Số lượng sau khi bấm cộng
                int soLuongMoi = cart.getSoLuong() + 1;

                // Kiểm tra kho trước khi tăng
                if (!kiemTraKhoKhiTang(cart, soLuongMoi)) {
                    return;
                }

                // Còn hàng thì mới tăng
                cart.setSoLuong(soLuongMoi);

                // Cập nhật lại ListView
                notifyDataSetChanged();

                // Cập nhật tổng tiền
                ((giohang) context).capNhatTongTien();
            }
        });

        // Bấm nút trừ
        btnTru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Lấy số lượng hiện tại
                int soLuong = cart.getSoLuong();

                // Nếu lớn hơn 1 thì cho giảm
                if (soLuong > 1) {

                    cart.setSoLuong(soLuong - 1);

                    // Cập nhật lại ListView
                    notifyDataSetChanged();

                    // Cập nhật tổng tiền
                    ((giohang) context).capNhatTongTien();

                } else {

                    // Nếu đang là 1 thì không cho giảm nữa
                    Toast.makeText(
                            context,
                            "Số lượng tối thiểu là 1, nếu muốn xóa hãy bấm nút Xóa",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }
        });

        // Bấm nút xóa
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Xóa sản phẩm khỏi giỏ hàng
                cartList.remove(cart);

                // Cập nhật lại ListView
                notifyDataSetChanged();

                // Cập nhật tổng tiền
                ((giohang) context).capNhatTongTien();
            }
        });

        return convertView;
    }

    // Kiểm tra kho khi bấm nút cộng
    private boolean kiemTraKhoKhiTang(CartModel cart, int soLuongMoi) {

        // Lấy mã sản phẩm trong giỏ
        int idSP = cart.getIdSP();

        /*
         * idSP <= 0 là sản phẩm mặc định.
         * Sản phẩm mặc định không kiểm tra kho.
         */
        if (idSP <= 0) {
            return true;
        }

        // Mở database chứa bảng SanPham
        SQLiteDatabase db = context.openOrCreateDatabase(
                "dbQLNV.db",
                Activity.MODE_PRIVATE,
                null
        );

        // Tìm sản phẩm admin thêm theo idSP
        Cursor cursor = db.rawQuery(
                "SELECT tenSP, soLuong FROM SanPham WHERE idSP = ?",
                new String[]{String.valueOf(idSP)}
        );

        // Nếu tìm thấy sản phẩm
        if (cursor.moveToFirst()) {

            String tenSP = cursor.getString(
                    cursor.getColumnIndexOrThrow("tenSP")
            );

            int soLuongCon = cursor.getInt(
                    cursor.getColumnIndexOrThrow("soLuong")
            );

            cursor.close();
            db.close();

            // Nếu số lượng muốn mua lớn hơn kho thì báo ngay
            if (soLuongMoi > soLuongCon) {
                Toast.makeText(
                        context,
                        "Sản phẩm " + tenSP + " chỉ còn " + soLuongCon + " sản phẩm trong kho",
                        Toast.LENGTH_SHORT
                ).show();

                return false;
            }

            // Còn hàng thì cho tăng
            return true;

        } else {

            cursor.close();
            db.close();

            // Không tìm thấy sản phẩm trong kho
            Toast.makeText(
                    context,
                    "Không tìm thấy sản phẩm trong kho",
                    Toast.LENGTH_SHORT
            ).show();

            return false;
        }
    }

    // Định dạng tiền: 120000 -> 120.000 VND
    private String dinhDangTien(int tien) {
        return String.format("%,d", tien).replace(",", ".") + " VND";
    }
}