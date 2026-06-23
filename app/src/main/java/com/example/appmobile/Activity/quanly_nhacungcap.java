package com.example.appmobile.Activity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appmobile.Adapter.AdapterNCC;
import com.example.appmobile.R;
import com.example.appmobile.model.NhaCungCap;

import java.util.ArrayList;

public class quanly_nhacungcap extends AppCompatActivity {

    // Ô nhập thông tin NCC
    EditText edtMaNCC, edtTenNCC, edtSDT, edtDiaChi, edtSanPham;

    // Ô tìm kiếm
    EditText edtSearch;

    // Nút thêm, sửa, xóa
    Button btnAdd, btnUpdate, btnDelete;

    // Nút quay lại và tìm kiếm
    ImageView btnBack, btnSearch;

    // Danh sách NCC
    ListView lvNCC;

    // Database
    SQLiteDatabase db;

    // Danh sách dữ liệu
    ArrayList<NhaCungCap> dsNCC;

    // Adapter
    AdapterNCC adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quanly_nhacungcap);

        // Ánh xạ ô nhập
        edtMaNCC = findViewById(R.id.edtMaNCC);
        edtTenNCC = findViewById(R.id.edtTenNCC);
        edtSDT = findViewById(R.id.edtSDT);
        edtDiaChi = findViewById(R.id.edtDiaChi);
        edtSanPham = findViewById(R.id.edtSanPham);

        // Ánh xạ tìm kiếm
        edtSearch = findViewById(R.id.edtSearch);
        btnSearch = findViewById(R.id.btnSearch);

        // Ánh xạ nút
        btnAdd = findViewById(R.id.btnAdd);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        btnBack = findViewById(R.id.btnBack);

        // Ánh xạ danh sách
        lvNCC = findViewById(R.id.lvNCC);

        // Khởi tạo danh sách
        dsNCC = new ArrayList<>();
        adapter = new AdapterNCC(this, dsNCC);
        lvNCC.setAdapter(adapter);

        // Mở database
        db = openOrCreateDatabase(
                "dbQLNV.db",
                MODE_PRIVATE,
                null
        );

        // Tạo bảng NCC
        db.execSQL(
                "CREATE TABLE IF NOT EXISTS NhaCungCap(" +
                        "maNCC INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "tenNCC TEXT," +
                        "sdt TEXT," +
                        "diaChi TEXT," +
                        "sanPham TEXT" +
                        ")"
        );

        // Thêm cột sanPham nếu bảng cũ chưa có
        try {
            db.execSQL("ALTER TABLE NhaCungCap ADD COLUMN sanPham TEXT");
        } catch (Exception e) {
            // Cột đã tồn tại thì bỏ qua
        }

        // Load dữ liệu ban đầu
        loadNCC();

        // Tìm kiếm theo tên NCC
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tuKhoa = edtSearch.getText().toString().trim();

                if (tuKhoa.isEmpty()) {
                    loadNCC();
                } else {
                    timKiemNCCTheoTen(tuKhoa);
                }
            }
        });

        // Thêm NCC
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                themNCC();
            }
        });

        // Cập nhật NCC
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                capNhatNCC();
            }
        });

        // Xóa NCC
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xoaNCC();
            }
        });

        // Bấm vào ô NCC để đưa dữ liệu lên form
        lvNCC.setOnItemClickListener((parent, view, position, id) -> {
            NhaCungCap ncc = dsNCC.get(position);

            edtMaNCC.setText(ncc.getMaNCC());
            edtTenNCC.setText(ncc.getTenNCC());
            edtSDT.setText(ncc.getSdt());
            edtDiaChi.setText(ncc.getDiaChi());
            edtSanPham.setText(ncc.getSanPham());
        });

        // Quay lại
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    // Thêm NCC
    private void themNCC() {
        String tenNCC = edtTenNCC.getText().toString().trim();
        String sdt = edtSDT.getText().toString().trim();
        String diaChi = edtDiaChi.getText().toString().trim();
        String sanPham = edtSanPham.getText().toString().trim();

        if (tenNCC.isEmpty() || sdt.isEmpty() || diaChi.isEmpty() || sanPham.isEmpty()) {
            Toast.makeText(
                    quanly_nhacungcap.this,
                    "Vui lòng nhập đầy đủ thông tin",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        ContentValues values = new ContentValues();
        values.put("tenNCC", tenNCC);
        values.put("sdt", sdt);
        values.put("diaChi", diaChi);
        values.put("sanPham", sanPham);

        long result = db.insert(
                "NhaCungCap",
                null,
                values
        );

        if (result != -1) {
            Toast.makeText(
                    quanly_nhacungcap.this,
                    "Thêm thành công",
                    Toast.LENGTH_SHORT
            ).show();

            loadNCC();
            clearData();
        } else {
            Toast.makeText(
                    quanly_nhacungcap.this,
                    "Thêm thất bại",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    // Cập nhật NCC
    private void capNhatNCC() {
        String ma = edtMaNCC.getText().toString().trim();
        String tenNCC = edtTenNCC.getText().toString().trim();
        String sdt = edtSDT.getText().toString().trim();
        String diaChi = edtDiaChi.getText().toString().trim();
        String sanPham = edtSanPham.getText().toString().trim();

        if (ma.isEmpty()) {
            Toast.makeText(
                    quanly_nhacungcap.this,
                    "Vui lòng chọn nhà cung cấp cần cập nhật",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        if (tenNCC.isEmpty() || sdt.isEmpty() || diaChi.isEmpty() || sanPham.isEmpty()) {
            Toast.makeText(
                    quanly_nhacungcap.this,
                    "Vui lòng nhập đầy đủ thông tin",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        ContentValues values = new ContentValues();
        values.put("tenNCC", tenNCC);
        values.put("sdt", sdt);
        values.put("diaChi", diaChi);
        values.put("sanPham", sanPham);

        int result = db.update(
                "NhaCungCap",
                values,
                "maNCC=?",
                new String[]{ma}
        );

        if (result > 0) {
            Toast.makeText(
                    quanly_nhacungcap.this,
                    "Cập nhật thành công",
                    Toast.LENGTH_SHORT
            ).show();

            loadNCC();
            clearData();
        } else {
            Toast.makeText(
                    quanly_nhacungcap.this,
                    "Cập nhật thất bại",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    // Xóa NCC
    private void xoaNCC() {
        String ma = edtMaNCC.getText().toString().trim();

        if (ma.isEmpty()) {
            Toast.makeText(
                    quanly_nhacungcap.this,
                    "Vui lòng chọn nhà cung cấp cần xóa",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        int result = db.delete(
                "NhaCungCap",
                "maNCC=?",
                new String[]{ma}
        );

        if (result > 0) {
            Toast.makeText(
                    quanly_nhacungcap.this,
                    "Xóa thành công",
                    Toast.LENGTH_SHORT
            ).show();

            loadNCC();
            clearData();
        } else {
            Toast.makeText(
                    quanly_nhacungcap.this,
                    "Xóa thất bại",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    // Load toàn bộ NCC
    private void loadNCC() {
        dsNCC.clear();

        Cursor c = db.rawQuery(
                "SELECT * FROM NhaCungCap",
                null
        );

        while (c.moveToNext()) {
            themNCCVaoDanhSach(c);
        }

        c.close();
        adapter.notifyDataSetChanged();
    }

    // Tìm NCC theo tên
    private void timKiemNCCTheoTen(String tuKhoa) {
        dsNCC.clear();

        Cursor c = db.rawQuery(
                "SELECT * FROM NhaCungCap WHERE tenNCC LIKE ?",
                new String[]{"%" + tuKhoa + "%"}
        );

        while (c.moveToNext()) {
            themNCCVaoDanhSach(c);
        }

        c.close();
        adapter.notifyDataSetChanged();

        if (dsNCC.size() == 0) {
            Toast.makeText(
                    quanly_nhacungcap.this,
                    "Không tìm thấy nhà cung cấp",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    // Đưa 1 dòng dữ liệu vào danh sách
    private void themNCCVaoDanhSach(Cursor c) {
        String sanPham = "";

        int indexSanPham = c.getColumnIndex("sanPham");

        if (indexSanPham != -1) {
            sanPham = c.getString(indexSanPham);

            if (sanPham == null) {
                sanPham = "";
            }
        }

        dsNCC.add(
                new NhaCungCap(
                        c.getString(c.getColumnIndexOrThrow("maNCC")),
                        c.getString(c.getColumnIndexOrThrow("tenNCC")),
                        c.getString(c.getColumnIndexOrThrow("sdt")),
                        c.getString(c.getColumnIndexOrThrow("diaChi")),
                        sanPham
                )
        );
    }

    // Xóa dữ liệu form
    private void clearData() {
        edtMaNCC.setText("");
        edtTenNCC.setText("");
        edtSDT.setText("");
        edtDiaChi.setText("");
        edtSanPham.setText("");
        edtSearch.setText("");
    }
}