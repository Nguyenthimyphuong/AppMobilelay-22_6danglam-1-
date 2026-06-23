package com.example.appmobile.model;

public class KhachHang {

    private int id;
    private String hoten;
    private String sdt;
    private String diachi;
    private int userId;

    public KhachHang(int id, int userId, String hoten, String diachi, String sdt) {
        this.id = id;
        this.userId = userId;
        this.hoten = hoten;
        this.diachi = diachi;
        this.sdt = sdt;
    }

    public int getId() {
        return id;
    }

    public String getHoten() {
        return hoten;
    }

    public String getSdt() {
        return sdt;
    }

    public String getDiachi() {
        return diachi;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
