# Báo cáo giao diện & CSDL — Dự án PH60001_MOB2041

Tài liệu tóm tắt các màn hình (giao diện, ID các View chính) và sơ đồ cơ sở dữ liệu (SQLite) trích xuất từ mã nguồn hiện có. Dùng để chèn vào báo cáo đồ án.

--------------------------------------------------------------------------------
## 4.1 TẠO GIAO DIỆN

Ghi chú: đường dẫn các layout nằm trong `app/src/main/res/layout/`.

### 4.1.1 Màn hình chính (MainActivity)
- File layout: `app/src/main/res/layout/activity_main.xml`
- Mô tả: trang Home chứa các nhóm chức năng (Thống kê, Quản lý, Người dùng).
- Các View (ID : loại — thuộc tính / chú ý)
  - `@+id/toolbar` : `Toolbar` — title hiển thị "JPMart"
  - `@+id/lnThongKe` : `LinearLayout` — chứa nhóm thống kê
    - `@+id/lnDoanhThu` : `LinearLayout` — nút Doanh thu (kèm `@+id/img_doanh_thu` ImageView)
    - `@+id/lnTopSanPham` : `LinearLayout` — Top sản phẩm (`@+id/img_top_san_pham`)
    - `@+id/lnTopKhachHang` : `LinearLayout` — Top khách hàng (`@+id/img_top_khach_hang`)
  - `@+id/lnSanPham` : `LinearLayout` — chuyển tới `QuanLySanPhamActivity` (`@+id/img_san_pham`)
  - `@+id/lnKhachHang` : `LinearLayout` — chuyển tới `QuanLyKhachHangActivity` (`@+id/img_khach_hang`)
  - `@+id/lnHoaDon` : `LinearLayout` — `QuanLyHoaDonActivity` (`@+id/img_hoa_don`)
  - `@+id/lnDanhMuc` : `LinearLayout` — `QuanLyDanhMucActivity` (`@+id/img_danh_muc`)
  - `@+id/lnNhanVien` : `LinearLayout` — `QuanLyNhanVienActivity` (`@+id/img_nhan_vien`)
  - `@+id/lnDoiMatKhau` : `LinearLayout` — `DoiMatKhauActivity` (`@+id/img_doi_mat_khau`)
  - `@+id/lnDangXuat` : `LinearLayout` — đăng xuất (`@+id/img_logout`)

- File menu: `app/src/main/res/menu/main_menu.xml` (các mục: `menu_doanh_thu`, `menu_top_san_pham`, `menu_top_khach_hang`, `menu_doi_mat_khau`, `menu_dang_xuat`)

---
### 4.1.2 Các màn hình quản lý

#### 4.1.2.1 Quản lý Sản phẩm
- File layout: `app/src/main/res/layout/activity_quan_ly_san_pham.xml`
- Các View chính:
  - `@+id/toolbar` : `Toolbar`
  - `@+id/edtSearch` : `EditText` — tìm kiếm sản phẩm (hint="Nhập tên sản phẩm...")
  - `@+id/itemCart` : `RelativeLayout` — chứa `@+id/img_gio_hang` (ImageView) và `@+id/tvSoLuong` (TextView badge)
  - `@+id/lvSanPham` : `ListView` — danh sách sản phẩm
  - `@+id/fabThemDanhMuc` : `FloatingActionButton` — thêm (mở `EditSanPhamActivity`)

#### 4.1.2.2 Thêm / Sửa Sản phẩm
- File layout: `app/src/main/res/layout/activity_edit_san_pham.xml`
- Các View chính (id : type — chú ý)
  - `@+id/spDanhMuc` : `Spinner` — chọn danh mục
  - `@+id/layoutMaSP` : `LinearLayout` — khung chứa mã SP
  - `@+id/edtMaDanhMuc` : `EditText` — (hiển thị mã sản phẩm khi edit)
  - `@+id/edtTenSanPham` : `EditText` — tên sản phẩm
  - `@+id/edtGiaSanPham` : `EditText` — giá (inputType=number)
  - `@+id/edtSoLuong` : `EditText` — số lượng (inputType=number)
  - `@+id/edtDonViTinh` : `EditText` — đơn vị tính
  - `@+id/edtNgayNhap` : `EditText` — ngày nhập
  - `@+id/btnLuu` : `Button` — Lưu
  - `@+id/btnHuy` : `Button` — Hủy

#### 4.1.2.3 Quản lý Khách hàng
- File layout: `app/src/main/res/layout/activity_quan_ly_khach_hang.xml`
- Các View chính:
  - `@+id/toolbar` : `Toolbar`
  - `@+id/lvKhachHang` : `ListView`
  - `@+id/fabThemKhachHang` : `FloatingActionButton`
- Từng item (file `item_khach_hang.xml`) chứa:
  - `@+id/tvMaKhachHang` : `TextView`
  - `@+id/tvTenKhachHang` : `TextView`
  - `@+id/tvDiaChi` : `TextView`
  - `@+id/tvSoDienThoai` : `TextView`
  - `@+id/tvEmail` : `TextView`
  - `@+id/imgSuaKhachHang` : `ImageView` (edit)
  - `@+id/imgXoaKhachHang` : `ImageView` (delete)

#### 4.1.2.4 Quản lý Nhân viên
- File layout: `app/src/main/res/layout/activity_quan_ly_nhan_vien.xml`
- Các View chính:
  - `@+id/toolbar`
  - `@+id/lvNhanVien` : `ListView`
  - `@+id/fabThemNhanVien` : `FloatingActionButton`
- Item (`item_nhan_vien.xml`) chứa:
  - `@+id/tvMaNhanVien`, `@+id/tvTenNhanVien`, `@+id/tvDiaChi`, `@+id/tvLuong`, `@+id/tvChucVu`
  - `@+id/imgSuaNhanVien`, `@+id/imgXoaNhanVien`

#### 4.1.2.5 Quản lý Hóa đơn
- File layout: `app/src/main/res/layout/activity_quan_ly_hoa_don.xml`
- Các View chính:
  - `@+id/toolbar`
  - `@+id/lvHoaDon` : `ListView`
- Item (`item_hoa_don.xml`) chứa:
  - `@+id/tvMaHoaDon`, `@+id/tvTenNhanVien`, `@+id/tvTenKhachHang`, `@+id/tvNgayLap`, `@+id/tvTongTien`, `@+id/imgXoaHoaDon`

#### 4.1.2.6 Quản lý Danh mục
- File layout: `app/src/main/res/layout/activity_quan_ly_danh_muc.xml`
- Các View chính:
  - `@+id/toolbar`
  - `@+id/lvSanPham` (dùng id lvSanPham trong layout danh mục) : `ListView`
  - `@+id/fabThemDanhMuc` : `FloatingActionButton`
- Item (`item_danh_muc.xml`) chứa:
  - `@+id/tvMaDanhMuc`, `@+id/tvTenDanhMuc`, `@+id/imgSuaDanhMuc`, `@+id/imgXoaDanhMuc`

---
### 4.1.3 Màn hình Thống kê Doanh thu
- File layout: `app/src/main/res/layout/activity_thong_ke_doanh_thu.xml`
- View chính:
  - `@+id/edtNgayBatDau`, `@+id/edtNgayKetThuc` : `EditText` (clickable -> DatePicker)
  - `@+id/btnThongKeDoanhThu` : `Button`
  - `@+id/tvDoanhThu` : `TextView` (hiển thị kết quả)

### 4.1.4 Màn hình Thống kê Top sản phẩm
- File layout: `app/src/main/res/layout/activity_thong_ke_san_pham.xml`
- View chính:
  - `@+id/edtNgayBatDau`, `@+id/edtNgayKetThuc`, `@+id/edtSoLuong`
  - `@+id/btnTopSanPham` : `Button`
  - `@+id/lvSanPham` : `ListView` (hiện Top sản phẩm)

### 4.1.5 Màn hình Thống kê Top khách hàng
- File layout: `app/src/main/res/layout/activity_thong_ke_khach_hang.xml`
- View chính:
  - `@+id/edtNgayBatDau`, `@+id/edtNgayKetThuc`, `@+id/edtSoLuong`
  - `@+id/btnTopKhachHang` : `Button`
  - `@+id/listViewKhachHang` : `ListView`

### 4.1.6 Giao diện hỗ trợ khác
- **Màn hình chào**: `activity_welcome.xml` — `@+id/imgChao` (ImageView)
- **Màn hình đăng nhập**: `activity_login.xml` — views:
  - `@+id/edtUsername`, `@+id/edtPassword` (TextInputEditText)
  - `@+id/chkRemember` : `CheckBox`
  - `@+id/btnLogin` : `Button`
- **Màn hình đổi mật khẩu**: `activity_doi_mat_khau.xml` — views:
  - `@+id/edtOldPassword`, `@+id/edtNewPassword`, `@+id/edtConfirmPassword`
  - `@+id/btnLuu`, `@+id/btnHuy`
- **Hóa đơn chi tiết**: `activity_hoa_don_chi_tiet.xml` — `@+id/lvSanPham`
- **Giỏ hàng**: `activity_gio_hang.xml` — `@+id/spKhachHang`, `@+id/lvSanPham`, `@+id/tvTongTien`, `@+id/btnThanhToan`

--------------------------------------------------------------------------------
## 4.2 TẠO CSDL VỚI SQLITE

Nguồn: `app/src/main/java/fpl/ph60001/ph60001_mob2041/helper/DatabaseHelper.java`

### 4.2.1 Sơ đồ quan hệ (mô tả bằng văn bản)
- `DanhMuc (1)  --- (N) SanPham` : SanPham.maDanhMuc → DanhMuc.maDanhMuc
- `NhanVien (1) --- (N) HoaDon` : HoaDon.maNhanVien → NhanVien.maNhanVien
- `KhachHang (1) --- (N) HoaDon` : HoaDon.maKhachHang → KhachHang.maKhachHang
- `HoaDon (1) --- (N) HoaDonChiTiet` : HoaDonChiTiet.maHoaDon → HoaDon.maHoaDon
- `SanPham (1) --- (N) HoaDonChiTiet` : HoaDonChiTiet.maSanPham → SanPham.maSanPham

Ràng buộc: các FK được khai báo trong các CREATE TABLE như trong code (xem phần CREATE TABLE bên dưới).

### 4.2.2 Chi tiết các bảng & lệnh SQL

#### Bảng DanhMuc
- Tên bảng: `DanhMuc`
- Cột:
  - `maDanhMuc` TEXT PRIMARY KEY
  - `ten_danh_muc` TEXT
- CREATE SQL:
```sql
CREATE TABLE IF NOT EXISTS DanhMuc (
  maDanhMuc TEXT PRIMARY KEY,
  ten_danh_muc TEXT
);
```
- Các lệnh cơ bản (ví dụ Java sử dụng SQLiteDatabase):
  - SELECT ALL: `db.rawQuery("SELECT * FROM " + BANG_DANHMUC, null);`
  - INSERT: `db.insert(BANG_DANHMUC, null, values);`
  - DELETE: `db.delete(BANG_DANHMUC, COT_MA_DANH_MUC + " = ?", new String[]{maDanhMuc});`
  - UPDATE: `db.update(BANG_DANHMUC, values, COT_MA_DANH_MUC + " = ?", new String[]{maDanhMuc});`

#### Bảng SanPham
- Tên bảng: `SanPham`
- Cột:
  - `maSanPham` TEXT PRIMARY KEY
  - `tenSanPham` TEXT
  - `giaSanPham` INTEGER
  - `soLuong` INTEGER
  - `donViTinh` TEXT
  - `ngayNhap` TEXT
  - `maDanhMuc` TEXT (FK → DanhMuc.maDanhMuc)
- CREATE SQL:
```sql
CREATE TABLE IF NOT EXISTS SanPham (
  maSanPham TEXT PRIMARY KEY,
  tenSanPham TEXT,
  giaSanPham INTEGER,
  soLuong INTEGER,
  donViTinh TEXT,
  ngayNhap TEXT,
  maDanhMuc TEXT,
  FOREIGN KEY(maDanhMuc) REFERENCES DanhMuc(maDanhMuc)
);
```

#### Bảng NhanVien
- Tên bảng: `NhanVien`
- Cột:
  - `maNhanVien` TEXT PRIMARY KEY
  - `tenNhanVien` TEXT NOT NULL
  - `diaChi` TEXT
  - `chucVu` INT
  - `luong` REAL NOT NULL
  - `matKhau` TEXT NOT NULL
- CREATE SQL:
```sql
CREATE TABLE NhanVien (
  maNhanVien TEXT PRIMARY KEY,
  tenNhanVien TEXT NOT NULL,
  diaChi TEXT,
  chucVu INT,
  luong REAL NOT NULL,
  matKhau TEXT NOT NULL
);
```

#### Bảng KhachHang
- Tên bảng: `KhachHang`
- Cột:
  - `maKhachHang` TEXT PRIMARY KEY
  - `tenKhachHang` TEXT NOT NULL
  - `diaChi` TEXT
  - `soDienThoai` TEXT NOT NULL
  - `email` TEXT NOT NULL
- CREATE SQL:
```sql
CREATE TABLE KhachHang (
  maKhachHang TEXT PRIMARY KEY,
  tenKhachHang TEXT NOT NULL,
  diaChi TEXT,
  soDienThoai TEXT NOT NULL,
  email TEXT NOT NULL
);
```

#### Bảng HoaDon
- Tên bảng: `HoaDon`
- Cột:
  - `maHoaDon` TEXT PRIMARY KEY
  - `maNhanVien` TEXT
  - `maKhachHang` TEXT
  - `ngayLap` TEXT
  - `tongTien` INTEGER
- CREATE SQL:
```sql
CREATE TABLE IF NOT EXISTS HoaDon (
  maHoaDon TEXT PRIMARY KEY,
  maNhanVien TEXT,
  maKhachHang TEXT,
  ngayLap TEXT,
  tongTien INTEGER,
  FOREIGN KEY (maNhanVien) REFERENCES NhanVien(maNhanVien),
  FOREIGN KEY (maKhachHang) REFERENCES KhachHang(maKhachHang)
);
```

#### Bảng HoaDonChiTiet
- Tên bảng: `HoaDonChiTiet`
- Cột:
  - `maHDCT` TEXT PRIMARY KEY
  - `maHoaDon` TEXT NOT NULL
  - `maSanPham` TEXT NOT NULL
  - `soLuong` INTEGER NOT NULL
  - `donGia` REAL NOT NULL
- CREATE SQL:
```sql
CREATE TABLE HoaDonChiTiet (
  maHDCT TEXT PRIMARY KEY,
  maHoaDon TEXT NOT NULL,
  maSanPham TEXT NOT NULL,
  soLuong INTEGER NOT NULL,
  donGia REAL NOT NULL,
  FOREIGN KEY(maHoaDon) REFERENCES HoaDon(maHoaDon),
  FOREIGN KEY(maSanPham) REFERENCES SanPham(maSanPham)
);
```

### 4.2.3 Các truy vấn / hàm tiện ích nổi bật trong code
- Tạo mã tự động: `taoMaKhachHangMoi()`, `taoMaNhanVienMoi()`, `taoMaDanhMucMoi()`, `taoMaSanPhamMoi()`, `taoMaHoaDonMoi()`, `taoMaHDCTMoi()`.
- Truy vấn liên quan:
  - Lấy danh sách hóa đơn kèm tên NV và KH: đã có hàm `layDanhSachHoaDon()` sử dụng JOIN giữa `HoaDon`, `NhanVien`, `KhachHang`.
  - Lấy chi tiết hóa đơn (join với `SanPham`) trong `layTatCaHDCT(String maHoaDon)`.
  - Thống kê: `thongKeTopSanPham(int n)` và `thongKeTopKhachHang(int m)` (sử dụng GROUP BY, SUM/COUNT).

--------------------------------------------------------------------------------
## Tham chiếu mã nguồn
- Xem hàm tạo bảng trong `DatabaseHelper.onCreate(...)`:
```startLine:endLine:app/src/main/java/fpl/ph60001/ph60001_mob2041/helper/DatabaseHelper.java
public void onCreate(SQLiteDatabase db) {
    // ... (bản gốc trong code chứa các lệnh CREATE TABLE như đã trích ở trên)
}
```

--------------------------------------------------------------------------------
Nếu muốn, tôi có thể:
- Xuất file `docs/database_schema.sql` chứa tất cả các lệnh CREATE TABLE.
- Hoàn thiện bảng chi tiết ID/thuộc tính cho tất cả layout và item nhỏ (hiện tệp này đã liệt kê view chính; tôi có thể mở rộng).
- Sinh file Markdown theo đúng format báo cáo Word/LaTeX bạn cần.

----
Tự động tạo bởi trợ lý — bạn có muốn tôi tạo file SQL (option: yes/no) hoặc mở rộng phần UI chi tiết cho từng layout item không?  


