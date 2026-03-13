package com.example.miniproject.view;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.miniproject.R;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setTitle("Chi Tiết Phòng");

        TextView tvMaPhong = findViewById(R.id.tvDetailMaPhong);
        TextView tvTenPhong = findViewById(R.id.tvDetailTenPhong);
        TextView tvGiaThue = findViewById(R.id.tvDetailGiaThue);
        TextView tvTinhTrang = findViewById(R.id.tvDetailTinhTrang);
        TextView tvTenNguoiThue = findViewById(R.id.tvDetailTenNguoiThue);
        TextView tvSoDienThoai = findViewById(R.id.tvDetailSoDienThoai);

        String maPhong = getIntent().getStringExtra("maPhong");
        String tenPhong = getIntent().getStringExtra("tenPhong");
        double giaThue = getIntent().getDoubleExtra("giaThue", 0);
        boolean daThue = getIntent().getBooleanExtra("daThuê", false);
        String tenNguoiThue = getIntent().getStringExtra("tenNguoiThue");
        String soDienThoai = getIntent().getStringExtra("soDienThoai");

        tvMaPhong.setText(maPhong);
        tvTenPhong.setText(tenPhong);
        tvGiaThue.setText(String.format("%,.0f VNĐ/tháng", giaThue));
        tvTinhTrang.setText(daThue ? "Đã thuê" : "Còn trống");
        tvTenNguoiThue.setText((tenNguoiThue == null || tenNguoiThue.isEmpty()) ? "Chưa có" : tenNguoiThue);
        tvSoDienThoai.setText((soDienThoai == null || soDienThoai.isEmpty()) ? "Chưa có" : soDienThoai);
    }
}
