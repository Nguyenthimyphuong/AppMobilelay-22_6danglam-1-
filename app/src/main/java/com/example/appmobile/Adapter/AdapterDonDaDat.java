package com.example.appmobile.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.appmobile.R;
import com.example.appmobile.model.HoaDon;

import java.util.ArrayList;

public class AdapterDonDaDat extends BaseAdapter {

    // Context là màn hình đang sử dụng Adapter này
    Context context;

    // Danh sách hóa đơn của người dùng
    ArrayList<HoaDon> list;

    // Constructor nhận context và danh sách hóa đơn
    public AdapterDonDaDat(Context context, ArrayList<HoaDon> list) {
        this.context = context;
        this.list = list;
    }

    // Trả về số lượng hóa đơn trong danh sách
    @Override
    public int getCount() {
        return list.size();
    }

    // Trả về hóa đơn tại vị trí position
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    // Trả về id của item
    @Override
    public long getItemId(int position) {
        return position;
    }

    // Hàm này tạo giao diện cho từng đơn hàng trong ListView
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        /*
         * Nếu convertView chưa có thì nạp giao diện item_don_da_dat.xml.
         * File item_don_da_dat.xml chính là khung của mỗi đơn hàng.
         */
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.item_don_da_dat,
                    parent,
                    false
            );
        }

        // Lấy hóa đơn tại vị trí hiện tại
        HoaDon hd = list.get(position);

        // Ánh xạ các TextView trong item_don_da_dat.xml
        TextView txtMaDon = convertView.findViewById(R.id.txt_item_ma_don);
        TextView txtNgayDat = convertView.findViewById(R.id.txt_item_ngay_dat);
        TextView txtNguoiNhan = convertView.findViewById(R.id.txt_item_nguoi_nhan);
        TextView txtSDT = convertView.findViewById(R.id.txt_item_sdt);
        TextView txtDiaChi = convertView.findViewById(R.id.txt_item_dia_chi);
        TextView txtSanPham = convertView.findViewById(R.id.txt_item_san_pham);
        TextView txtTongTien = convertView.findViewById(R.id.txt_item_tong_tien);
        TextView txtThanhToan = convertView.findViewById(R.id.txt_item_thanh_toan);

        // Đưa dữ liệu hóa đơn lên giao diện
        txtMaDon.setText("Mã đơn hàng: HD" + hd.getId());

        // Dòng ngày đặt mới bổ sung
        txtNgayDat.setText("Ngày đặt: " + hd.getNgayDat());

        txtNguoiNhan.setText("Người nhận: " + hd.getHoTen());
        txtSDT.setText("SĐT: " + hd.getSoDT());
        txtDiaChi.setText("Địa chỉ: " + hd.getDiaChi());
        txtSanPham.setText(hd.getSanPham());
        txtTongTien.setText("Tổng tiền: " + hd.getTongTien());
        txtThanhToan.setText("Thanh toán: " + hd.getThanhToan());

        return convertView;
    }
}