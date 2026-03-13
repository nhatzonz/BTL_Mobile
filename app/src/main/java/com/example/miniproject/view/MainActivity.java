package com.example.miniproject.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miniproject.R;
import com.example.miniproject.adapter.PhongTroAdapter;
import com.example.miniproject.controller.PhongTroController;
import com.example.miniproject.model.PhongTro;

public class MainActivity extends AppCompatActivity implements PhongTroAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private PhongTroAdapter adapter;
    private PhongTroController controller;
    private TextView tvTongDoanhThu;

    // ✅ Thay đổi 1: Khai báo ActivityResultLauncher
    private ActivityResultLauncher<Intent> addLauncher;
    private ActivityResultLauncher<Intent> editLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        controller = PhongTroController.getInstance();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        tvTongDoanhThu = findViewById(R.id.tvTongDoanhThu);

        adapter = new PhongTroAdapter(controller.getDanhSachPhong(), this);
        recyclerView.setAdapter(adapter);
        capNhatTongDoanhThu();

        // ✅ Thay đổi 2: Đăng ký launcher cho THÊM
        addLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Intent data = result.getData();
                        String ma = data.getStringExtra("maPhong");
                        String ten = data.getStringExtra("tenPhong");
                        double gia = data.getDoubleExtra("giaThue", 0);
                        boolean thuê = data.getBooleanExtra("daThuê", false);
                        String nguoiThue = data.getStringExtra("tenNguoiThue");
                        String sdt = data.getStringExtra("soDienThoai");

                        PhongTro phongMoi = new PhongTro(ma, ten, gia, thuê, nguoiThue, sdt);

                        if (controller.themPhong(phongMoi)) {
                            adapter.notifyItemInserted(controller.getDanhSachPhong().size() - 1);
                            capNhatTongDoanhThu();
                            Toast.makeText(this, "Thêm phòng thành công!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "Mã phòng đã tồn tại!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        // ✅ Thay đổi 3: Đăng ký launcher cho SỬA
        editLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Intent data = result.getData();
                        String ma = data.getStringExtra("maPhong");
                        String ten = data.getStringExtra("tenPhong");
                        double gia = data.getDoubleExtra("giaThue", 0);
                        boolean thuê = data.getBooleanExtra("daThuê", false);
                        String nguoiThue = data.getStringExtra("tenNguoiThue");
                        String sdt = data.getStringExtra("soDienThoai");
                        int pos = data.getIntExtra("position", -1);

                        PhongTro phongMoi = new PhongTro(ma, ten, gia, thuê, nguoiThue, sdt);
                        controller.suaPhong(pos, phongMoi);
                        adapter.notifyItemChanged(pos);
                        capNhatTongDoanhThu();
                        Toast.makeText(this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        // ✅ Thay đổi 4: Nút thêm phòng dùng launcher
        findViewById(R.id.btnThemPhong).setOnClickListener(v -> {
            Intent intent = new Intent(this, AddEditActivity.class);
            addLauncher.launch(intent);
        });
    }

    @Override
    public void onEditClick(int position) {
        PhongTro phong = controller.getDanhSachPhong().get(position);
        Intent intent = new Intent(this, AddEditActivity.class);
        intent.putExtra("position", position);
        putPhongExtra(intent, phong);

        editLauncher.launch(intent);
    }

    @Override
    public void onDeleteClick(int position) {
        new AlertDialog.Builder(this)
                .setTitle("Xác nhận xóa")
                .setMessage("Bạn có chắc muốn xóa phòng này?")
                .setPositiveButton("Xóa", (dialog, which) -> {
                    controller.xoaPhong(position);
                    adapter.notifyItemRemoved(position);
                    adapter.notifyItemRangeChanged(position, controller.getDanhSachPhong().size());
                    capNhatTongDoanhThu();
                    Toast.makeText(this, "Đã xóa phòng!", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    @Override
    public void onDetailClick(int position) {
        PhongTro phong = controller.getDanhSachPhong().get(position);
        Intent intent = new Intent(this, DetailActivity.class);
        putPhongExtra(intent, phong);
        startActivity(intent);
    }

    private void putPhongExtra(Intent intent, PhongTro phong) {
        intent.putExtra("maPhong", phong.getMaPhong());
        intent.putExtra("tenPhong", phong.getTenPhong());
        intent.putExtra("giaThue", phong.getGiaThue());
        intent.putExtra("daThuê", phong.isDaThuê());
        intent.putExtra("tenNguoiThue", phong.getTenNguoiThue());
        intent.putExtra("soDienThoai", phong.getSoDienThoai());
    }

    private void capNhatTongDoanhThu() {
        double tongDoanhThu = controller.tinhTongDoanhThuDaThue();
        tvTongDoanhThu.setText(String.format(
                "Tổng doanh thu phòng đã thuê: %,.0f VNĐ/tháng",
                tongDoanhThu
        ));
    }
}
