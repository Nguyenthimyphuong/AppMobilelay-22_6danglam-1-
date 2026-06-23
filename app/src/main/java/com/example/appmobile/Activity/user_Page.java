package com.example.appmobile.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appmobile.Adapter.Adapter_item_user_page;
import com.example.appmobile.Item.item_user_page;
import com.example.appmobile.R;
import com.example.appmobile.model.CartModel;

import java.util.ArrayList;

public class user_Page extends AppCompatActivity {

    // Ảnh sản phẩm mặc định
    int image[] = {
            R.drawable.itemuser1,
            R.drawable.itemuser2,
            R.drawable.itemuser3,
            R.drawable.hangquatang4,
            R.drawable.binhsua5,
            R.drawable.numty6,
            R.drawable.numty7,
            R.drawable.numty8,
            R.drawable.suachobe9,
            R.drawable.suachobe10
    };

    // Tên sản phẩm mặc định
    String nameProduct[] = {
            "Nước giặt xả Dr.Spock Organics",
            "Ti giả dành cho bé từ 1-2 tháng tuổi",
            "Nước xả vải thơm nức mũi người dùng",
            "Hàng quà tặng cho mẹ và bé",
            "Bình sữa cho bé",
            "Núm ty silicon cho bé",
            "Núm ty mềm cho bé",
            "Núm ty chống sặc",
            "Sữa cho bé phát triển",
            "Sữa dinh dưỡng cho bé"
    };

    // Giá sản phẩm mặc định
    int price[] = {
            120000,
            100000,
            150000,
            150000,
            150000,
            150000,
            150000,
            150000,
            150000,
            150000
    };

    // GridView hiển thị sản phẩm
    GridView gridView;

    // userId của người dùng đang đăng nhập
    int userId;

    // Danh sách sản phẩm
    ArrayList<item_user_page> myItemUserPages;

    // Adapter sản phẩm
    Adapter_item_user_page adapterItemUserPage;

    // Database sản phẩm
    SQLiteDatabase dbProduct;

    // Các nút ở thanh dưới
    LinearLayout btn_nav_cart;
    LinearLayout btn_nav_payment;
    LinearLayout btn_nav_order;

    /*
     * Nút đăng xuất ở thanh trên.
     * Trong XML nút này là TextView nên Java cũng phải khai báo TextView.
     */
    TextView btn_logout_top;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);

        // Nhận userId từ trang đăng nhập gửi sang
        userId = getIntent().getIntExtra("userId", -1);

        // Ánh xạ GridView
        gridView = findViewById(R.id.gv_item_user_page);

        // Ánh xạ các nút thanh dưới
        btn_nav_cart = findViewById(R.id.btn_nav_cart);
        btn_nav_payment = findViewById(R.id.btn_nav_payment);
        btn_nav_order = findViewById(R.id.btn_nav_order);

        // Ánh xạ nút đăng xuất
        btn_logout_top = findViewById(R.id.btn_logout_top);

        // Khởi tạo danh sách sản phẩm
        myItemUserPages = new ArrayList<>();

        // Khởi tạo Adapter
        adapterItemUserPage = new Adapter_item_user_page(
                user_Page.this,
                R.layout.item_product_user_page,
                myItemUserPages
        );

        // Gắn Adapter vào GridView
        gridView.setAdapter(adapterItemUserPage);

        // Mở database sản phẩm
        dbProduct = openOrCreateDatabase("dbQLNV.db", MODE_PRIVATE, null);

        // Tạo bảng sản phẩm nếu chưa có
        dbProduct.execSQL(
                "CREATE TABLE IF NOT EXISTS SanPham(" +
                        "idSP INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "tenSP TEXT," +
                        "soLuong INTEGER," +
                        "giaTien INTEGER," +
                        "imageName TEXT" +
                        ")"
        );

        // Load sản phẩm lên trang chủ
        loadTatCaSanPham();

        // Bấm vào sản phẩm để sang trang chi tiết
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                item_user_page sanPham = myItemUserPages.get(position);

                Intent intent = new Intent(user_Page.this, SubActivity.class);

                intent.putExtra("idSP", sanPham.getIdSP());
                intent.putExtra("image", sanPham.getImage());
                intent.putExtra("name", sanPham.getName_Product());
                intent.putExtra("price", sanPham.getPrice_Product());
                intent.putExtra("soLuong", sanPham.getSoLuong());
                intent.putExtra("userId", userId);

                startActivity(intent);
            }
        });

        // Nút Giỏ hàng
        btn_nav_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(user_Page.this, giohang.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
            }
        });

        // Nút Thanh toán
        btn_nav_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (CartModel.cartList.size() == 0) {
                    Toast.makeText(
                            user_Page.this,
                            "Giỏ hàng đang trống",
                            Toast.LENGTH_SHORT
                    ).show();
                } else {
                    Intent intent = new Intent(user_Page.this, thanhtoan_user.class);
                    intent.putExtra("userId", userId);
                    startActivity(intent);
                }
            }
        });

        // Nút Đơn hàng
        btn_nav_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(user_Page.this, don_da_dat_user.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
            }
        });

        // Nút Đăng xuất ở góc phải trên cùng
        btn_logout_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Xóa giỏ hàng khi đăng xuất
                CartModel.cartList.clear();

                Toast.makeText(
                        user_Page.this,
                        "Đăng xuất thành công",
                        Toast.LENGTH_SHORT
                ).show();

                // Quay về màn hình chính
                Intent intent = new Intent(user_Page.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    // Load cả sản phẩm mặc định và sản phẩm admin thêm
    private void loadTatCaSanPham() {

        myItemUserPages.clear();

        loadSanPhamMacDinh();

        loadSanPhamAdminThem();

        adapterItemUserPage.notifyDataSetChanged();
    }

    // Load sản phẩm mặc định
    private void loadSanPhamMacDinh() {

        for (int i = 0; i < nameProduct.length; i++) {

            // Sản phẩm mặc định dùng id âm để không trùng với sản phẩm SQLite
            int idMacDinh = -(i + 1);

            myItemUserPages.add(
                    new item_user_page(
                            idMacDinh,
                            image[i],
                            nameProduct[i],
                            price[i],
                            100
                    )
            );
        }
    }

    // Load sản phẩm admin thêm từ SQLite
    private void loadSanPhamAdminThem() {

        Cursor cursor = dbProduct.query(
                "SanPham",
                null,
                null,
                null,
                null,
                null,
                "idSP DESC"
        );

        if (cursor.moveToFirst()) {

            do {
                int idSP = cursor.getInt(cursor.getColumnIndexOrThrow("idSP"));
                String tenSP = cursor.getString(cursor.getColumnIndexOrThrow("tenSP"));
                int soLuong = cursor.getInt(cursor.getColumnIndexOrThrow("soLuong"));
                int giaTien = cursor.getInt(cursor.getColumnIndexOrThrow("giaTien"));
                String imageName = cursor.getString(cursor.getColumnIndexOrThrow("imageName"));

                int imageId = layIdAnhTheoTen(imageName);

                // Chỉ hiện sản phẩm admin thêm nếu còn hàng
                if (soLuong > 0) {
                    myItemUserPages.add(
                            new item_user_page(
                                    idSP,
                                    imageId,
                                    tenSP,
                                    giaTien,
                                    soLuong
                            )
                    );
                }

            } while (cursor.moveToNext());
        }

        cursor.close();
    }

    // Đổi tên ảnh trong SQLite thành id ảnh drawable
    private int layIdAnhTheoTen(String imageName) {

        int imageId = getResources().getIdentifier(
                imageName,
                "drawable",
                getPackageName()
        );

        // Nếu không tìm thấy ảnh thì lấy ảnh mặc định
        if (imageId == 0) {
            imageId = R.drawable.itemuser1;
        }

        return imageId;
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Khi quay lại trang chủ thì load lại sản phẩm
        if (dbProduct != null && adapterItemUserPage != null) {
            loadTatCaSanPham();
        }
    }
}