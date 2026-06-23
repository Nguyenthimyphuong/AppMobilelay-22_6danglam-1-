package com.example.appmobile.model;

import java.util.ArrayList;

public class CartModel {

    /*
     * cartList là danh sách giỏ hàng dùng chung trong app.
     * Khi khách bấm thêm sản phẩm vào giỏ hàng,
     * sản phẩm sẽ được thêm vào danh sách này.
     */
    public static ArrayList<CartModel> cartList = new ArrayList<>();

    /*
     * idSP dùng để phân biệt sản phẩm.
     *
     * Nếu idSP > 0:
     * Đây là sản phẩm do admin thêm trong SQLite.
     * Sản phẩm này cần kiểm tra số lượng trong kho.
     *
     * Nếu idSP < 0:
     * Đây là sản phẩm mặc định có sẵn trên trang user.
     * Sản phẩm này mua bao nhiêu cũng được, không trừ kho.
     */
    int idSP;

    // Tên sản phẩm
    String tenSP;

    // Giá sản phẩm
    int giaSP;

    // Số lượng khách chọn mua
    int soLuong;

    // Hình ảnh sản phẩm
    int hinhSP;

    // Constructor dùng để tạo sản phẩm trong giỏ hàng
    public CartModel(int idSP, String tenSP, int giaSP, int soLuong, int hinhSP) {
        this.idSP = idSP;
        this.tenSP = tenSP;
        this.giaSP = giaSP;
        this.soLuong = soLuong;
        this.hinhSP = hinhSP;
    }

    public int getIdSP() {
        return idSP;
    }

    public void setIdSP(int idSP) {
        this.idSP = idSP;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public int getGiaSP() {
        return giaSP;
    }

    public void setGiaSP(int giaSP) {
        this.giaSP = giaSP;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getHinhSP() {
        return hinhSP;
    }

    public void setHinhSP(int hinhSP) {
        this.hinhSP = hinhSP;
    }

    // Hàm tính thành tiền của 1 sản phẩm
    public int thanhTien() {
        return giaSP * soLuong;
    }
}