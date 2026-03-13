package com.example.miniproject.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.miniproject.R;
import com.example.miniproject.controller.PhongTroController;

public class AddEditActivity extends AppCompatActivity {

    private EditText edtMaPhong, edtTenPhong, edtGiaThue, edtTenNguoiThue, edtSoDienThoai;
    private Switch switchTinhTrang;
    private PhongTroController controller;
    private int editPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        controller = PhongTroController.getInstance();

        edtMaPhong = findViewById(R.id.edtMaPhong);
        edtTenPhong = findViewById(R.id.edtTenPhong);
        edtGiaThue = findViewById(R.id.edtGiaThue);
        edtTenNguoiThue = findViewById(R.id.edtTenNguoiThue);
        edtSoDienThoai = findViewById(R.id.edtSoDienThoai);
        switchTinhTrang = findViewById(R.id.switchTinhTrang);

        Intent intent = getIntent();
        editPosition = intent.getIntExtra("position", -1);

        if (editPosition != -1) {
            // Chế độ sửa
            edtMaPhong.setText(intent.getStringExtra("maPhong"));
            edtMaPhong.setEnabled(false); // Không cho sửa mã phòng
            edtTenPhong.setText(intent.getStringExtra("tenPhong"));
            edtGiaThue.setText(String.valueOf(intent.getDoubleExtra("giaThue", 0)));
            switchTinhTrang.setChecked(intent.getBooleanExtra("daThuê", false));
            edtTenNguoiThue.setText(intent.getStringExtra("tenNguoiThue"));
            edtSoDienThoai.setText(intent.getStringExtra("soDienThoai"));
            setTitle("Sửa Phòng");
        } else {
            setTitle("Thêm Phòng");
        }

        findViewById(R.id.btnLuu).setOnClickListener(v -> luuPhong());
    }

    private void luuPhong() {
        String ma = edtMaPhong.getText().toString().trim();
        String ten = edtTenPhong.getText().toString().trim();
        String giaStr = edtGiaThue.getText().toString().trim();
        boolean daThue = switchTinhTrang.isChecked();
        String tenNguoiThue = edtTenNguoiThue.getText().toString().trim();
        String soDienThoai = edtSoDienThoai.getText().toString().trim();
        boolean isCreate = editPosition == -1;

        String error = controller.validate(
                ma, ten, giaStr, daThue, tenNguoiThue, soDienThoai, isCreate
        );
        if (error != null) {
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
            return;
        }

        if (controller.isMaPhongTrung(ma, editPosition)) {
            Toast.makeText(this, "Mã phòng đã tồn tại!", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent result = new Intent();
        result.putExtra("maPhong", ma);
        result.putExtra("tenPhong", ten);
        result.putExtra("giaThue", Double.parseDouble(giaStr));
        result.putExtra("daThuê", daThue);
        result.putExtra("tenNguoiThue", tenNguoiThue);
        result.putExtra("soDienThoai", soDienThoai);
        result.putExtra("position", editPosition);
        setResult(RESULT_OK, result);
        finish();
    }
}
