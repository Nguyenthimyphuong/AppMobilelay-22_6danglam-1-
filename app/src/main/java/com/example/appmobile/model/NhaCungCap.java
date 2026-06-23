package com.example.appmobile.model;

public class NhaCungCap {

    // Mã nhà cung cấp
    String maNCC;

    // Tên nhà cung cấp
    String tenNCC;

    // Số điện thoại
    String sdt;

    // Địa chỉ
    String diaChi;

    // Sản phẩm cung cấp
    String sanPham;

    public NhaCungCap(String maNCC, String tenNCC, String sdt, String diaChi, String sanPham) {
        this.maNCC = maNCC;
        this.tenNCC = tenNCC;
        this.sdt = sdt;
        this.diaChi = diaChi;
        this.sanPham = sanPham;
    }

    // Constructor cũ để tránh lỗi file khác
    public NhaCungCap(String maNCC, String tenNCC, String sdt, String diaChi) {
        this.maNCC = maNCC;
        this.tenNCC = tenNCC;
        this.sdt = sdt;
        this.diaChi = diaChi;
        this.sanPham = "";
    }

    public String getMaNCC() {
        return maNCC;
    }

    public String getTenNCC() {
        return tenNCC;
    }

    public String getSdt() {
        return sdt;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public String getSanPham() {
        return sanPham;
    }
}