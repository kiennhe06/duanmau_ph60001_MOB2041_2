package fpl.ph60001.ph60001_mob2041.dto;

public class HoaDonChiTietDto {
    private String maCTHD;
    private String maHoaDon;
    private String maSanPham;
    private String tenSanPham;
    private int soLuong;
    private double donGia;

    public HoaDonChiTietDto(String maCTHD, String maHoaDon, String maSanPham, String tenSanPham, int soLuong, double donGia) {
        this.maCTHD = maCTHD;
        this.maHoaDon = maHoaDon;
        this.maSanPham = maSanPham;
        this.tenSanPham = tenSanPham;
        this.soLuong = soLuong;
        this.donGia = donGia;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public double getDonGia() {
        return donGia;
    }
}

