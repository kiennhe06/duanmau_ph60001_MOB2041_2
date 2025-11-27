package fpl.ph60001.ph60001_mob2041;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import fpl.ph60001.ph60001_mob2041.adapter.HoaDonAdapter;
import fpl.ph60001.ph60001_mob2041.helper.DatabaseHelper;
import fpl.ph60001.ph60001_mob2041.dto.HoaDonDto;

import java.util.List;
import java.util.Objects;

public class QuanLyHoaDonActivity extends AppCompatActivity implements HoaDonAdapter.OnHoaDonClickListener {

    private HoaDonAdapter hoaDonAdapter;
    private List<HoaDonDto> danhSachHoaDon;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_hoa_don);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ListView lvHoaDon = findViewById(R.id.lvHoaDon);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Quản lý hóa đơn");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db = new DatabaseHelper(this);

        danhSachHoaDon = db.layDanhSachHoaDon();
        hoaDonAdapter = new HoaDonAdapter(this, danhSachHoaDon);
        hoaDonAdapter.setOnHoaDonClickListener(this);
        lvHoaDon.setAdapter(hoaDonAdapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onDeleteHoaDon(HoaDonDto hoaDon) {
        DatabaseHelper db = new DatabaseHelper(this);
        boolean isDeleted = db.xoaHoaDon(hoaDon.getMaHoaDon());
        if (isDeleted) {
            danhSachHoaDon.remove(hoaDon);
            hoaDonAdapter.notifyDataSetChanged();
            Toast.makeText(this, "Xoá hóa đơn thành công", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Xoá hóa đơn thất bại", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onOpenHDCT(HoaDonDto hoaDon) {
        Intent i = new Intent(this, HDCTActivity.class);
        i.putExtra("maHoaDon", hoaDon.getMaHoaDon());
        startActivity(i);
    }
}

