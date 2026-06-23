package com.example.appmobile.Activity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appmobile.DatabaseHelper;
import com.example.appmobile.R;

import java.util.ArrayList;

public class quanly_khachhang extends AppCompatActivity {

    EditText edt_TK,edt_tenKH,edt_idKH,edt_sdt,edt_diachi;
    ImageView btn_search,btn_back;
    Button btn_add,btn_update,btn_delete;
    ListView lvClient;
    ArrayList<String> mylistClient ;
    ArrayAdapter<String> adapterClient;
    DatabaseHelper databaseHelper;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quanly_khachhang);

        //
        edt_TK = findViewById(R.id.edt_TK);
        edt_tenKH = findViewById(R.id.edt_tenKH);
        edt_idKH = findViewById(R.id.edt_idKH);
        edt_diachi = findViewById(R.id.edt_diachi);
        edt_sdt = findViewById(R.id.edt_sdt);

        //
        btn_search= findViewById(R.id.btn_search);
        btn_back = findViewById(R.id.btn_back);
        btn_add = findViewById(R.id.btn_add);
        btn_update = findViewById(R.id.btn_update);
        btn_delete = findViewById(R.id.btn_delete);
        //
        lvClient = findViewById(R.id.lv_client);
        //
        mylistClient = new ArrayList<>();
        //
        adapterClient= new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,mylistClient);
        lvClient.setAdapter(adapterClient);
        // tao sql
        databaseHelper = new DatabaseHelper(this);

        //
        renderClient();
//        btn_add.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String idKH = edt_idKH.getText().toString();
//                String tenKH = edt_tenKH.getText().toString();
//                String diachi = edt_diachi.getText().toString();
//                String soDT = edt_sdt.getText().toString();
//                if ( tenKH.isEmpty() || diachi.isEmpty() || soDT.isEmpty()) {
//                    Toast.makeText(quanly_khachhang.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                if (tenKH.matches("\\d+")) {
//                    Toast.makeText(quanly_khachhang.this, "Tên khách hàng phải là số", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                if (diachi.matches("\\d+")) {
//                    Toast.makeText(quanly_khachhang.this, "Địa chỉ khách hàng phải là số", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                if (!soDT.matches("\\d{10}")) {
//                    Toast.makeText(quanly_khachhang.this, "Số điện thoại phải có đúng 10 chữ số", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                ContentValues contentValues = new ContentValues();
//                contentValues.put("tenKH",tenKH);
//                contentValues.put("diachi",diachi);
//                contentValues.put("soDT",soDT);
//                String msq="";
//                if(dbClient.insert("KhachHang",null,contentValues)==-1){
//                    msq="Them that bai";
//                }
//                else {
//                    msq="Them thanh cong";
//                }
//                Toast.makeText(quanly_khachhang.this,msq,Toast.LENGTH_SHORT).show();
//                renderClient();
//            }
//        });
//        btn_delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String idKH = edt_idKH.getText().toString();
//                String msg = "";
//                int dlt_result = dbClient.delete("KhachHang","idKH=?",new String[]{idKH});
//                if(dlt_result==0){
//                    msg="Xóa không thành công";
//                }
//                else {
//                    msg="Xóa thành công";
//                }
//                Toast.makeText(quanly_khachhang.this,msg,Toast.LENGTH_SHORT).show();
//                renderClient();
//            }
//        });
//        btn_update.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String idKH = edt_idKH.getText().toString();
//                String tenKH = edt_tenKH.getText().toString();
//                String diachi = edt_diachi.getText().toString();
//                String soDT = edt_sdt.getText().toString();
//                if (idKH.isEmpty() || tenKH.isEmpty() || diachi.isEmpty() || soDT.isEmpty()) {
//                    Toast.makeText(quanly_khachhang.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                if (tenKH.matches("\\d+")) {
//                    Toast.makeText(quanly_khachhang.this, "Tên khách hàng phải là số", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                if (diachi.matches("\\d+")) {
//                    Toast.makeText(quanly_khachhang.this, "Địa chỉ khách hàng phải là số", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                if (!soDT.matches("\\d{10}")) {
//                    Toast.makeText(quanly_khachhang.this, "Số điện thoại phải có đúng 10 chữ số", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                ContentValues contentValues = new ContentValues();
//                contentValues.put("tenKH",tenKH);
//                contentValues.put("diachi",diachi);
//                contentValues.put("soDT",soDT);
//                int result = dbClient.update("KhachHang",contentValues,"idKH=?",new String[]{idKH});
//                String msq="";
//                if(result==0){
//                    msq="Cap nhạt khong thanh cong";
//                }
//                else {
//                    msq="Cap nhạt thanh cong";
//                }
//                Toast.makeText(quanly_khachhang.this,msq,Toast.LENGTH_SHORT).show();
//                renderClient();
//            }
//        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        lvClient.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                // Lấy giá trị của phần tử đã chọn
                Cursor cursor = databaseHelper.getAllKhachHang();
                if (cursor.moveToPosition(position)) {
                    String idKH = cursor.getString(0);
                    String tenKH = cursor.getString(1);
                    String soDT = cursor.getString(2);
                    String diachi = cursor.getString(3);

                    // Gán giá trị vào các EditText
                    edt_idKH.setText(idKH);
                    edt_tenKH.setText(tenKH);
                    edt_diachi.setText(diachi);
                    edt_sdt.setText(soDT);
                }
                cursor.close();
            }
        });

    }
    public void renderClient() {

        mylistClient.clear();

        Cursor render =
                databaseHelper.getAllKhachHang();

        while(render.moveToNext()) {

            String data =
                    "Mã KH: " + render.getInt(0)
                            + "\nTên: " + render.getString(1)
                            + "\nSĐT: " + render.getString(2)
                            + "\nĐịa chỉ: " + render.getString(3);

            mylistClient.add(data);
        }

        render.close();

        adapterClient.notifyDataSetChanged();
    }
}