package com.example.miniproject.model;
public class PhongTro {
    private String maPhong;
    private String tenPhong;
    private double giaThue;
    private boolean daThuê;
    private String tenNguoiThue;
    private String soDienThoai;

    public PhongTro(String maPhong, String tenPhong, double giaThue,
                    boolean daThuê, String tenNguoiThue, String soDienThoai) {
        this.maPhong = maPhong;
        this.tenPhong = tenPhong;
        this.giaThue = giaThue;
        this.daThuê = daThuê;
        this.tenNguoiThue = tenNguoiThue;
        this.soDienThoai = soDienThoai;
    }

    // Getters
    public String getMaPhong() { return maPhong; }
    public String getTenPhong() { return tenPhong; }
    public double getGiaThue() { return giaThue; }
    public boolean isDaThuê() { return daThuê; }
    public String getTenNguoiThue() { return tenNguoiThue; }
    public String getSoDienThoai() { return soDienThoai; }

    // Setters
    public void setMaPhong(String maPhong) { this.maPhong = maPhong; }
    public void setTenPhong(String tenPhong) { this.tenPhong = tenPhong; }
    public void setGiaThue(double giaThue) { this.giaThue = giaThue; }
    public void setDaThuê(boolean daThuê) { this.daThuê = daThuê; }
    public void setTenNguoiThue(String tenNguoiThue) { this.tenNguoiThue = tenNguoiThue; }
    public void setSoDienThoai(String soDienThoai) { this.soDienThoai = soDienThoai; }
}

