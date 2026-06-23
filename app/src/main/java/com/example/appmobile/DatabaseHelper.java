package com.example.appmobile;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    /*
     * DB_NAME là tên database dùng để lưu tài khoản user và hóa đơn.
     *
     * DB_VERSION tăng từ 4 lên 5 vì mình đã sửa cấu trúc bảng hoadon:
     * - Xóa cột trangThai
     *
     * Khi tăng version, hàm onUpgrade sẽ chạy lại.
     */
    private static final String DB_NAME = "UserDB";
    private static final int DB_VERSION = 5;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        /*
         * Bảng users dùng để lưu tài khoản người dùng đăng ký.
         * id là khóa chính, tự tăng.
         */
        db.execSQL(
                "CREATE TABLE users (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "username TEXT UNIQUE," +
                        "password TEXT," +
                        "hoten TEXT," +
                        "sdt TEXT," +
                        "diachi TEXT)"
        );

        /*
         * Bảng hoadon dùng để lưu đơn hàng của khách.
         *
         * userId dùng để biết hóa đơn này thuộc tài khoản nào.
         * Đã xóa cột trangThai vì bài không dùng trạng thái đơn hàng nữa.
         */
        db.execSQL(
                "CREATE TABLE hoadon (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "userId INTEGER," +
                        "hoTen TEXT," +
                        "soDienThoai TEXT," +
                        "diaChi TEXT," +
                        "ghiChu TEXT," +
                        "hinhThucThanhToan TEXT," +
                        "sanPham TEXT," +
                        "tongTien TEXT," +
                        "ngayDat TEXT)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db,
                          int oldVersion,
                          int newVersion) {

        /*
         * Khi đổi cấu trúc database, xóa bảng cũ và tạo lại bảng mới.
         * Cách này đơn giản, phù hợp bài thực hành SQLite.
         */
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS hoadon");

        onCreate(db);
    }

    // Hàm đăng ký tài khoản user
    public boolean registerUser(String username,
                                String password,
                                String hoten,
                                String sdt,
                                String diachi) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("username", username);
        values.put("password", password);
        values.put("hoten", hoten);
        values.put("sdt", sdt);
        values.put("diachi", diachi);

        long result = db.insert(
                "users",
                null,
                values
        );

        return result != -1;
    }

    // Hàm kiểm tra tài khoản và mật khẩu khi đăng nhập
    public boolean checkUser(String username,
                             String password) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM users WHERE username=? AND password=?",
                new String[]{username, password}
        );

        boolean exists = cursor.moveToFirst();

        cursor.close();

        return exists;
    }

    // Hàm lấy toàn bộ user, chủ yếu dùng để kiểm tra dữ liệu
    public String getAllUsers() {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM users",
                null
        );

        StringBuilder result = new StringBuilder();

        while (cursor.moveToNext()) {

            int id = cursor.getInt(
                    cursor.getColumnIndexOrThrow("id")
            );

            String username = cursor.getString(
                    cursor.getColumnIndexOrThrow("username")
            );

            String password = cursor.getString(
                    cursor.getColumnIndexOrThrow("password")
            );

            result.append("ID: ")
                    .append(id)
                    .append(" | User: ")
                    .append(username)
                    .append(" | Pass: ")
                    .append(password)
                    .append("\n");
        }

        cursor.close();

        return result.toString();
    }

    // Hàm lấy danh sách khách hàng cho phần admin quản lý khách hàng
    public Cursor getAllKhachHang() {

        SQLiteDatabase db = getReadableDatabase();

        return db.rawQuery(
                "SELECT id, username, hoten, sdt, diachi FROM users",
                null
        );
    }

    /*
     * Hàm lấy id của user sau khi đăng nhập.
     * id này được gọi là userId.
     * Khi đặt hàng, hóa đơn sẽ lưu theo userId này.
     */
    public int getUserId(String username, String password) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT id FROM users WHERE username=? AND password=?",
                new String[]{username, password}
        );

        int userId = -1;

        if (cursor.moveToFirst()) {
            userId = cursor.getInt(0);
        }

        cursor.close();

        return userId;
    }

    /*
     * Hàm lưu hóa đơn vào bảng hoadon.
     *
     * Đã xóa phần trangThai vì không dùng "Chờ xác nhận" nữa.
     */
    public boolean insertHoaDon(
            int userId,
            String hoTen,
            String soDienThoai,
            String diaChi,
            String ghiChu,
            String hinhThucThanhToan,
            String sanPham,
            String tongTien,
            String ngayDat
    ) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("userId", userId);
        values.put("hoTen", hoTen);
        values.put("soDienThoai", soDienThoai);
        values.put("diaChi", diaChi);
        values.put("ghiChu", ghiChu);
        values.put("hinhThucThanhToan", hinhThucThanhToan);
        values.put("sanPham", sanPham);
        values.put("tongTien", tongTien);
        values.put("ngayDat", ngayDat);

        long result = db.insert(
                "hoadon",
                null,
                values
        );

        return result != -1;
    }

    /*
     * Hàm lấy hóa đơn theo userId.
     * Dùng cho:
     * - Trang đơn đã đặt của user
     * - Trang chi tiết khách hàng bên admin
     */
    public Cursor getHoaDonByUserId(int userId) {

        SQLiteDatabase db = this.getReadableDatabase();

        return db.rawQuery(
                "SELECT * FROM hoadon WHERE userId = ? ORDER BY id DESC",
                new String[]{String.valueOf(userId)}
        );
    }

    // Hàm lấy tất cả hóa đơn dạng chuỗi, dùng để kiểm tra dữ liệu
    public String getAllHoaDon() {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM hoadon",
                null
        );

        StringBuilder result = new StringBuilder();

        while (cursor.moveToNext()) {

            result.append("ID: ")
                    .append(cursor.getInt(cursor.getColumnIndexOrThrow("id")))
                    .append(" | UserID: ")
                    .append(cursor.getInt(cursor.getColumnIndexOrThrow("userId")))
                    .append(" | ")
                    .append(cursor.getString(cursor.getColumnIndexOrThrow("hoTen")))
                    .append("\n");
        }

        cursor.close();

        return result.toString();
    }

    // Hàm debug hóa đơn, dùng để kiểm tra hóa đơn có lưu đúng userId không
    public String debugHoaDon() {

        SQLiteDatabase db = getReadableDatabase();

        Cursor c = db.rawQuery(
                "SELECT id, userId, hoTen FROM hoadon",
                null
        );

        StringBuilder sb = new StringBuilder();

        while (c.moveToNext()) {

            sb.append("HD:")
                    .append(c.getInt(c.getColumnIndexOrThrow("id")))
                    .append(" | userId=")
                    .append(c.getInt(c.getColumnIndexOrThrow("userId")))
                    .append(" | ")
                    .append(c.getString(c.getColumnIndexOrThrow("hoTen")))
                    .append("\n");
        }

        c.close();

        return sb.toString();
    }

    /*
     * Hàm lấy tất cả hóa đơn cho trang admin quản lý hóa đơn.
     */
    public Cursor getAllHoaDonCursor() {

        SQLiteDatabase db = this.getReadableDatabase();

        return db.rawQuery(
                "SELECT * FROM hoadon ORDER BY id DESC",
                null
        );
    }
}