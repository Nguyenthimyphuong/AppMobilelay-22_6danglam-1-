package com.example.appmobile.Item;

public class item_user_page {

    /*
     * idSP dùng để phân biệt sản phẩm.
     *
     * idSP > 0:
     * Là sản phẩm do admin thêm trong SQLite.
     * Sản phẩm này cần kiểm tra số lượng và trừ kho khi khách mua.
     *
     * idSP <= 0:
     * Là sản phẩm mặc định có sẵn trên trang khách hàng.
     * Sản phẩm mặc định mua bao nhiêu cũng được, không kiểm tra kho.
     */
    private int idSP;

    // Ảnh sản phẩm
    private int image;

    // Tên sản phẩm
    private String name_Product;

    // Giá sản phẩm
    private int price_Product;

    // Số lượng còn trong kho, chủ yếu dùng cho sản phẩm admin thêm
    private int soLuong;

    /*
     * Constructor dùng cho sản phẩm lấy từ SQLite.
     * Sản phẩm admin thêm vào sẽ dùng constructor này.
     */
    public item_user_page(int idSP, int image, String name_Product, int price_Product, int soLuong) {
        this.idSP = idSP;
        this.image = image;
        this.name_Product = name_Product;
        this.price_Product = price_Product;
        this.soLuong = soLuong;
    }

    /*
     * Constructor cũ, giữ lại để không lỗi các phần khác.
     * Sản phẩm dùng constructor này sẽ được xem là sản phẩm mặc định.
     */
    public item_user_page(int image, String name_Product, int price_Product) {
        this.idSP = -1;
        this.image = image;
        this.name_Product = name_Product;
        this.price_Product = price_Product;
        this.soLuong = 0;
    }

    public int getIdSP() {
        return idSP;
    }

    public void setIdSP(int idSP) {
        this.idSP = idSP;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName_Product() {
        return name_Product;
    }

    public void setName_Product(String name_Product) {
        this.name_Product = name_Product;
    }

    public int getPrice_Product() {
        return price_Product;
    }

    public void setPrice_Product(int price_Product) {
        this.price_Product = price_Product;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
}