package com.example.appmobile.Activity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appmobile.Adapter.AdapterHoaDon;
import com.example.appmobile.DatabaseHelper;
import com.example.appmobile.R;
import com.example.appmobile.model.HoaDon;

import java.util.ArrayList;

public class DanhSachHoaDonActivity extends AppCompatActivity {

    TextView txtTongHD;
    ImageView btnBack;

    RecyclerView recyclerView;

    ArrayList<HoaDon> list;

    AdapterHoaDon adapter;

    DatabaseHelper databaseHelper;

    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danhsach_hoadon);

        txtTongHD = findViewById(R.id.txtTongHD);
        btnBack = findViewById(R.id.btnBack);
        recyclerView = findViewById(R.id.rvHoaDon);

        btnBack.setOnClickListener(v -> finish());

        recyclerView.setLayoutManager(
                new LinearLayoutManager(this)
        );

        databaseHelper = new DatabaseHelper(this);
        db = databaseHelper.getWritableDatabase();

        list = new ArrayList<>();

        loadData();
    }

    private void loadData() {

        list.clear();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM hoadon ORDER BY id DESC",
                null
        );

        while (cursor.moveToNext()) {

            HoaDon hoaDon = new HoaDon(
                    cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("userId")),
                    cursor.getString(cursor.getColumnIndexOrThrow("hoTen")),
                    cursor.getString(cursor.getColumnIndexOrThrow("soDienThoai")),
                    cursor.getString(cursor.getColumnIndexOrThrow("diaChi")),
                    cursor.getString(cursor.getColumnIndexOrThrow("hinhThucThanhToan")),
                    cursor.getString(cursor.getColumnIndexOrThrow("sanPham")),
                    cursor.getString(cursor.getColumnIndexOrThrow("tongTien")),
                    cursor.getString(cursor.getColumnIndexOrThrow("ngayDat"))
            );

            list.add(hoaDon);
        }

        cursor.close();

        txtTongHD.setText(
                "Tổng số hóa đơn: " + list.size()
        );

        adapter = new AdapterHoaDon(
                this,
                list,
                db
        );

        recyclerView.setAdapter(adapter);
    }
}