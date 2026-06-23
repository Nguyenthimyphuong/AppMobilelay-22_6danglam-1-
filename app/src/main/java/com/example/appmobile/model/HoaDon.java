package com.example.appmobile.model;

public class HoaDon {

    int id;
    int userId;

    String hoTen;
    String soDienThoai;
    String diaChi;

    String sanPham;
    String tongTien;


    String ngayDat;
    String hinhThucThanhToan;

    public HoaDon(
            int id,
            int userId,
            String hoTen,
            String soDienThoai,
            String diaChi,
            String hinhThucThanhToan,
            String sanPham,
            String tongTien,
            String ngayDat
    ) {
        this.id = id;
        this.userId = userId;
        this.hoTen = hoTen;
        this.soDienThoai = soDienThoai;
        this.diaChi = diaChi;
        this.hinhThucThanhToan = hinhThucThanhToan;
        this.sanPham = sanPham;
        this.tongTien = tongTien;
        this.ngayDat = ngayDat;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getHoTen() {
        return hoTen;
    }

    public String getSoDT() {
        return soDienThoai;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public String getSanPham() {
        return sanPham;
    }

    public String getTongTien() {
        return tongTien;
    }


    public String getNgayDat() {
        return ngayDat;
    }

    public String getThanhToan() {
        return hinhThucThanhToan;
    }
}