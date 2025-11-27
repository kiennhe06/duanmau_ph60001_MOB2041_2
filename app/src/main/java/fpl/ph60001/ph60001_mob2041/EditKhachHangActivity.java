package fpl.ph60001.ph60001_mob2041;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import fpl.ph60001.ph60001_mob2041.helper.DatabaseHelper;
import fpl.ph60001.ph60001_mob2041.model.KhachHang;

public class EditKhachHangActivity extends AppCompatActivity {
    private EditText edtMaKhachHang, edtTenKhachHang, edtDiaChi, edtSoDienThoai, edtEmail;
    private DatabaseHelper db;
    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_khach_hang);

        db = new DatabaseHelper(this);
        edtMaKhachHang = findViewById(R.id.edtMaKhachHang);
        edtTenKhachHang = findViewById(R.id.edtTenKhachHang);
        edtDiaChi = findViewById(R.id.edtDiaChi);
        edtSoDienThoai = findViewById(R.id.edtSoDienThoai);
        edtEmail = findViewById(R.id.edtEmail);
        findViewById(R.id.btnLuu).setOnClickListener(v -> luuKhachHang());
        findViewById(R.id.btnHuy).setOnClickListener(v -> finish());
        LinearLayout layoutMaKhachHang = findViewById(R.id.layoutMaKhachHang);

        type = getIntent().getIntExtra("Type", -1);
        if (type == 0) { // Edit
            edtMaKhachHang.setEnabled(false);
            KhachHang khachHang = getIntent().getParcelableExtra(QuanLyKhachHangActivity.KHACH_HANG);
            edtMaKhachHang.setText(khachHang.getMaKhachHang());
            edtTenKhachHang.setText(khachHang.getTenKhachHang());
            edtDiaChi.setText(khachHang.getDiaChi());
            edtSoDienThoai.setText(khachHang.getSoDienThoai());
            edtEmail.setText(khachHang.getEmail());
        } else if (type == 1) { // Add
            layoutMaKhachHang.setVisibility(View.GONE);
        }
    }

    private void luuKhachHang() {
        String maKhachHang = edtMaKhachHang.getText().toString().trim();
        String tenKhachHang = edtTenKhachHang.getText().toString().trim();
        if (tenKhachHang.equalsIgnoreCase("")) {
            Toast.makeText(this, "Ten khach hang ko dươc de trong", Toast.LENGTH_SHORT).show();
            return;
        }
        String diaChi = edtDiaChi.getText().toString().trim();
        String soDienThoai = edtSoDienThoai.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        if (!email.endsWith("example.com")) {
            Toast.makeText(this, "email phai ket thu bang \"example.com\"", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean isOK;
        if (type == 0) { // Edit
            KhachHang khachHang = new KhachHang(maKhachHang, tenKhachHang, diaChi, soDienThoai, email);
            isOK = db.suaKhachHang(khachHang);
        } else { // Add new
            maKhachHang = db.taoMaKhachHangMoi();
            KhachHang khachHang = new KhachHang(maKhachHang, tenKhachHang, diaChi, soDienThoai, email);
            isOK = db.themKhachHang(khachHang);
        }
        if (isOK) {
            Toast.makeText(this, (type == 0)? "Cập nhật": "Thêm" + " khách hàng thành công", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, (type == 0)? "Cập nhật": "Thêm" + " khách hàng thất bại", Toast.LENGTH_SHORT).show();
        }
        finish();
    }
}

