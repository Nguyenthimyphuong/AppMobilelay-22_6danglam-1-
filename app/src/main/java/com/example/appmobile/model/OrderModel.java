package com.example.appmobile.model;

import java.util.ArrayList;

public class OrderModel {

    /*
     * Danh sách lưu đơn hàng tạm thời.
     * Dữ liệu chỉ tồn tại trong lúc app đang chạy.
     * Khi tắt app hoàn toàn rồi mở lại thì dữ liệu có thể mất.
     */
    public static ArrayList<OrderModel> orderList = new ArrayList<>();

    // Thông tin của một đơn hàng
    private String maDonHang;
    private int userId;
    private String hoTen;
    private String soDienThoai;
    private String diaChi;
    private String ghiChu;
    private String hinhThucThanhToan;
    private String sanPhamDaMua;
    private String tongTien;

    // Hàm khởi tạo đơn hàng
    public OrderModel(String maDonHang,
                      int userId,
                      String hoTen,
                      String soDienThoai,
                      String diaChi,
                      String ghiChu,
                      String hinhThucThanhToan,
                      String sanPhamDaMua,
                      String tongTien) {

        this.maDonHang = maDonHang;
        this.userId = userId;
        this.hoTen = hoTen;
        this.soDienThoai = soDienThoai;
        this.diaChi = diaChi;
        this.ghiChu = ghiChu;
        this.hinhThucThanhToan = hinhThucThanhToan;
        this.sanPhamDaMua = sanPhamDaMua;
        this.tongTien = tongTien;
    }

    public String getMaDonHang() {
        return maDonHang;
    }

    public int getUserId() {
        return userId;
    }

    public String getHoTen() {
        return hoTen;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public String getHinhThucThanhToan() {
        return hinhThucThanhToan;
    }

    public String getSanPhamDaMua() {
        return sanPhamDaMua;
    }

    public String getTongTien() {
        return tongTien;
    }
}