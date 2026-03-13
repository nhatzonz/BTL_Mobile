package com.example.miniproject.controller;

import com.example.miniproject.model.PhongTro;
import java.util.ArrayList;
import java.util.List;

public class PhongTroController {
    private static PhongTroController instance;
    private List<PhongTro> danhSachPhong;

    // Singleton để tránh mất dữ liệu khi chuyển màn hình
    public static PhongTroController getInstance() {
        if (instance == null) {
            instance = new PhongTroController();
        }
        return instance;
    }

    private PhongTroController() {
        danhSachPhong = new ArrayList<>();
        // Dữ liệu mẫu
        danhSachPhong.add(new PhongTro("P001", "Phòng 101", 2500000, false, "", ""));
        danhSachPhong.add(new PhongTro("P002", "Phòng 102", 3000000, true, "Nguyễn Văn A", "0901234567"));
        danhSachPhong.add(new PhongTro("P003", "Phòng 103", 2800000, false, "", ""));
    }

    public List<PhongTro> getDanhSachPhong() {
        return danhSachPhong;
    }

    public boolean isMaPhongTrung(String maPhong, int excludePosition) {
        if (maPhong == null) return false;
        for (int i = 0; i < danhSachPhong.size(); i++) {
            if (i == excludePosition) continue;
            PhongTro p = danhSachPhong.get(i);
            if (maPhong.equalsIgnoreCase(p.getMaPhong())) {
                return true;
            }
        }
        return false;
    }

    public double tinhTongDoanhThuDaThue() {
        double tong = 0;
        for (PhongTro p : danhSachPhong) {
            if (p.isDaThuê()) {
                tong += p.getGiaThue();
            }
        }
        return tong;
    }

    // CREATE
    public boolean themPhong(PhongTro phong) {
        for (PhongTro p : danhSachPhong) {
            if (p.getMaPhong().equals(phong.getMaPhong())) {
                return false; // Trùng mã
            }
        }
        danhSachPhong.add(phong);
        return true;
    }

    // UPDATE
    public boolean suaPhong(int position, PhongTro phongMoi) {
        if (position < 0 || position >= danhSachPhong.size()) {
            return false;
        }
        danhSachPhong.set(position, phongMoi);
        return true;
    }

    // DELETE
    public void xoaPhong(int position) {
        if (position >= 0 && position < danhSachPhong.size()) {
            danhSachPhong.remove(position);
        }
    }

    // VALIDATE
    public String validate(String maPhong, String tenPhong, String giaThue,
                           boolean daThue, String tenNguoiThue, String soDienThoai,
                           boolean isCreate) {
        if (maPhong.isEmpty()) return "Mã phòng không được để trống!";
        if (!maPhong.matches("^P\\d{3,}$")) {
            return "Mã phòng không hợp lệ! Ví dụ: P001";
        }
        if (tenPhong.isEmpty()) return "Tên phòng không được để trống!";
        if (giaThue.isEmpty()) return "Giá thuê không được để trống!";

        try {
            double gia = Double.parseDouble(giaThue);
            if (gia <= 0) return "Giá thuê phải lớn hơn 0!";
        } catch (NumberFormatException e) {
            return "Giá thuê không hợp lệ!";
        }

        // Khi tạo mới/sửa mà phòng đã thuê, bắt buộc nhập người thuê và số điện thoại.
        if (daThue) {
            if (tenNguoiThue == null || tenNguoiThue.trim().isEmpty()) {
                return "Phòng đã thuê phải có tên người thuê!";
            }
            if (soDienThoai == null || soDienThoai.trim().isEmpty()) {
                return "Phòng đã thuê phải có số điện thoại!";
            }
        }

        // Khi tạo mới, bắt buộc các thông tin liên hệ nếu có người thuê.
        if (isCreate && tenNguoiThue != null && !tenNguoiThue.trim().isEmpty()
                && (soDienThoai == null || soDienThoai.trim().isEmpty())) {
            return "Vui lòng nhập số điện thoại khi đã có tên người thuê!";
        }

        // Validate định dạng SĐT ở cả tạo và sửa nếu người dùng nhập.
        if (soDienThoai != null && !soDienThoai.trim().isEmpty()
                && !soDienThoai.matches("^0\\d{9}$")) {
            return "Số điện thoại phải gồm 10 chữ số và bắt đầu bằng 0!";
        }
        return null; // null = hợp lệ
    }
}
