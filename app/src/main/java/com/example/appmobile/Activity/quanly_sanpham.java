package com.example.appmobile.Activity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.SharedPreferences;
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
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appmobile.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class quanly_sanpham extends AppCompatActivity {
    Spinner sp_image;
    String historyAddProductss = "";
    EditText edt_TK,edt_tenSP,edt_idSP,edt_sl,edt_gia;
    ImageView btn_search,btn_back;
    Button btn_add,btn_update,btn_delete;
    ListView lvProduct;
    ArrayList<String> mylistProduct ;
    ArrayAdapter<String> adapterProduct;
    SQLiteDatabase dbProduct;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quanly_sanpham);

        sp_image = findViewById(R.id.sp_image);

        String[] images = {
                "itemuser1",
                "itemuser2",
                "itemuser3",
                "hangquatang4",
                "binhsua5",
                "numty6",
                "numty7",
                "numty8",
                "suachobe9",
                "suachobe10",
                "khanmat"
        };

        ArrayAdapter<String> imageAdapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_spinner_item,
                        images
                );

        imageAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        sp_image.setAdapter(imageAdapter);

        //
        edt_TK = findViewById(R.id.edt_TK);
        edt_tenSP = findViewById(R.id.edt_tenSP);
        edt_idSP = findViewById(R.id.edt_idSP);
        edt_sl = findViewById(R.id.edt_sl);
        edt_gia = findViewById(R.id.edt_gia);

        //
        btn_back = findViewById(R.id.btn_back);
        btn_add = findViewById(R.id.btn_add);
        btn_update = findViewById(R.id.btn_update);
        btn_delete = findViewById(R.id.btn_delete);
        btn_search= findViewById(R.id.btn_search);
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String keyword =
                        edt_TK.getText().toString().trim();

                if(keyword.isEmpty()){

                    renderProduct();

                }else{

                    searchProduct(keyword);

                }
            }
        });
        //
        lvProduct = findViewById(R.id.lv_product);
        //
        mylistProduct = new ArrayList<>();
        //
        adapterProduct= new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,mylistProduct);
        lvProduct.setAdapter(adapterProduct);
        // tao sql
        dbProduct = openOrCreateDatabase("dbQLNV.db",MODE_PRIVATE,null);

        //tao cac table
        try {
            String sql =
                    "CREATE TABLE SanPham(" +
                            "idSP INTEGER PRIMARY KEY AUTOINCREMENT," +
                            "tenSP TEXT," +
                            "soLuong INTEGER," +
                            "giaTien INTEGER," +
                            "imageName TEXT" +
                            ")";
            dbProduct.execSQL(sql);
        }catch (Exception error){
            Log.e("Error", "Table da ton tai");
        }
        //
        renderProduct();
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog_add = new AlertDialog.Builder(quanly_sanpham.this);
                dialog_add.setTitle("Question");
                dialog_add.setMessage("Bạn có chắc chắn muốn thêm mới?");
                dialog_add.setIcon(R.drawable.question);
                dialog_add.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                dialog_add.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String idSP = edt_idSP.getText().toString();
                        String tenSP = edt_tenSP.getText().toString();
                        String soLuong = edt_sl.getText().toString();
                        String giaTien = edt_gia.getText().toString();
                        String imageName = sp_image.getSelectedItem().toString();

                        Date ngayLap = new Date();
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String formattedDate = formatter.format(ngayLap);
                        if ( tenSP.isEmpty() || soLuong.isEmpty() || giaTien.isEmpty()) {
                            Toast.makeText(quanly_sanpham.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (tenSP.matches("\\d+")) {
                            Toast.makeText(quanly_sanpham.this, "Tên khách hàng phải là chữ cái", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (!giaTien.matches("\\d+")) {
                            Toast.makeText(quanly_sanpham.this, "Giá tiền hàng phải là số", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (!soLuong.matches("\\d+")) {
                            Toast.makeText(quanly_sanpham.this, "Số lượng hàng phải là số", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        historyAddProductss += "Bạn vừa thêm mới sản phẩm" + "\n" + "Tên sản phẩm: " + tenSP + "\n" + "Số lượng: " + soLuong + "\n" + "Đơn giá: " + giaTien + "\n" + "Thời gian: " + formattedDate;
                        historyAddProductss +="\n";
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("tenSP", tenSP);
                        contentValues.put("soLuong", soLuong);
                        contentValues.put("giaTien", giaTien);
                        contentValues.put("imageName", imageName);

                        long result = dbProduct.insert("SanPham", null, contentValues);

                        if(result == -1){
                            Toast.makeText(quanly_sanpham.this,
                                    "Them that bai",
                                    Toast.LENGTH_SHORT).show();

                            Log.e("SQL_ERROR", "Insert loi");
                        }
                        else{
                            Toast.makeText(quanly_sanpham.this,
                                    "Them thanh cong",
                                    Toast.LENGTH_SHORT).show();
                        }

                        edt_idSP.setText("");
                        edt_tenSP.setText("");
                        edt_sl.setText("");
                        edt_gia.setText("");
                        renderProduct();
                    }
                });
                dialog_add.create().show();

            }
        });
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog_dlt = new AlertDialog.Builder(quanly_sanpham.this);
                dialog_dlt.setTitle("Question");
                dialog_dlt.setMessage("Bạn có chắc chắn muốn xóa?");
                dialog_dlt.setIcon(R.drawable.question);
                dialog_dlt.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                dialog_dlt.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String idSP = edt_idSP.getText().toString();
                        if (idSP.isEmpty()){
                            Toast.makeText(quanly_sanpham.this, "Không tìm thấy sản phẩm", Toast.LENGTH_SHORT).show();
                        }
                        String msg = "";
                        int dlt_result = dbProduct.delete("SanPham","idSP=?",new String[]{idSP});
                        if(dlt_result==0){
                            msg="Xóa không thành công";
                        }
                        else {
                            msg="Xóa thành công";
                        }
                        Toast.makeText(quanly_sanpham.this,msg,Toast.LENGTH_SHORT).show();
                        edt_idSP.setText("");
                        edt_tenSP.setText("");
                        edt_sl.setText("");
                        edt_gia.setText("");
                        renderProduct();
                    }
                });

                dialog_dlt.create().show();
            }
        });
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog_upd = new AlertDialog.Builder(quanly_sanpham.this);
                dialog_upd.setTitle("Question");
                dialog_upd.setMessage("Bạn có chắc chắn muốn thêm mới?");
                dialog_upd.setIcon(R.drawable.question);
                dialog_upd.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                dialog_upd.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String idSP = edt_idSP.getText().toString();
                        String tenSP = edt_tenSP.getText().toString();
                        String soLuong = edt_sl.getText().toString();
                        String giaTien = edt_gia.getText().toString();
                        String imageName = sp_image.getSelectedItem().toString();

                        if (idSP.isEmpty() || tenSP.isEmpty() || soLuong.isEmpty() || giaTien.isEmpty()) {
                            Toast.makeText(quanly_sanpham.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (tenSP.matches("\\d+")) {
                            Toast.makeText(quanly_sanpham.this, "Tên khách hàng phải là chữ cái", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (!giaTien.matches("\\d+")) {
                            Toast.makeText(quanly_sanpham.this, "Giá tiền hàng phải là số", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (!soLuong.matches("\\d+")) {
                            Toast.makeText(quanly_sanpham.this, "Số lượng hàng phải là số", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("tenSP", tenSP);
                        contentValues.put("soLuong", soLuong);
                        contentValues.put("giaTien", giaTien);
                        contentValues.put("imageName", imageName);
                        int result = dbProduct.update(
                                "SanPham",
                                contentValues,
                                "idSP=?",
                                new String[]{idSP}
                        );
                        String msq="";
                        if(result==0){
                            msq="Cap nhạt khong thanh cong";
                        }
                        else {
                            msq="Cap nhạt thanh cong";
                        }
                        Toast.makeText(quanly_sanpham.this,msq,Toast.LENGTH_SHORT).show();
                        edt_idSP.setText("");
                        edt_tenSP.setText("");
                        edt_sl.setText("");
                        edt_gia.setText("");
                        renderProduct();
                    }
                });
                dialog_upd.create().show();
            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        lvProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                // Lấy giá trị của phần tử đã chọn
                Cursor cursor = dbProduct.query("SanPham", null, null, null, null, null, null);
                if (cursor.moveToPosition(position)) {
                    String idSP = cursor.getString(0);
                    String tenSP = cursor.getString(1);
                    String soLuong = cursor.getString(2);
                    String giaTien = cursor.getString(3);
                    String imageName = cursor.getString(4);

                    // Gán giá trị vào các EditText
                    edt_idSP.setText(idSP);
                    edt_tenSP.setText(tenSP);
                    edt_sl.setText(soLuong);
                    edt_gia.setText(giaTien);
                    ArrayAdapter adapter = (ArrayAdapter) sp_image.getAdapter();

                    for (int j = 0; j < adapter.getCount(); j++) {
                        if (adapter.getItem(j).toString().equals(imageName)) {
                            sp_image.setSelection(j);
                            break;
                        }
                    }
                }
                cursor.close();
            }
        });

    }
    public void renderProduct() {

        mylistProduct.clear();

        Cursor render = dbProduct.query(
                "SanPham",
                null,
                null,
                null,
                null,
                null,
                null
        );

        if (render.moveToFirst()) {

            do {

                String data =
                        "Mã: " + render.getString(0)
                                + "\nTên: " + render.getString(1)
                                + "\nSố lượng: " + render.getString(2)
                                + "\nGiá: " + render.getString(3);

                mylistProduct.add(data);

            } while (render.moveToNext());
        }

        render.close();

        adapterProduct.notifyDataSetChanged();
    }
    // THÊM HÀM TÌM KIẾM
    public void searchProduct(String keyword) {

        mylistProduct.clear();

        Cursor cursor = dbProduct.query(
                "SanPham",
                null,
                "tenSP LIKE ?",
                new String[]{"%" + keyword + "%"},
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {

            do {

                String data =
                        "Mã: " + cursor.getString(0)
                                + "\nTên: " + cursor.getString(1)
                                + "\nSố lượng: " + cursor.getString(2)
                                + "\nGiá: " + cursor.getString(3);

                mylistProduct.add(data);

            } while (cursor.moveToNext());
        }

        cursor.close();

        adapterProduct.notifyDataSetChanged();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //4 bước
        //Bước 1: gọi hàm SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("historyAddProductss",MODE_PRIVATE);
        //Bước 2: gọi đến Editor để chỉnh sửa SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        //Bước 3: lưu dữ liệu
        editor.putString("historyAddProductss",historyAddProductss);
        //Bước 4: Xác nhận lại
        editor.commit();
    }
}