# BÁO CÁO DỰ ÁN
## ỨNG DỤNG QUẢN LÝ BÁN HÀNG - PH60001_MOB2041

---

## 3.0 Thiết kế ứng dụng

### 3.1 Mô hình công nghệ ứng dụng

**Công nghệ sử dụng:**
- **Ngôn ngữ lập trình:** Java
- **IDE:** Android Studio
- **Cơ sở dữ liệu:** SQLite
- **Minimum SDK:** API 24 (Android 7.0 Nougat)
- **Target SDK:** API 35
- **Kiến trúc:** MVC (Model - View - Controller)

**Thư viện sử dụng:**
- AndroidX AppCompat
- Material Design Components
- ConstraintLayout

**Mô hình hoạt động:**
```
┌─────────────────┐     ┌─────────────────┐     ┌─────────────────┐
│     View        │ ←── │   Controller    │ ←── │     Model       │
│  (XML Layout)   │ ──→ │   (Activity)    │ ──→ │ (Entity Class)  │
└─────────────────┘     └─────────────────┘     └─────────────────┘
                               ↓ ↑
                        ┌─────────────────┐
                        │  DatabaseHelper │
                        │    (SQLite)     │
                        └─────────────────┘
```

---

### 3.2 Thực thể

#### 3.2.1 Sơ đồ quan hệ thực thể (ERD)

```
┌─────────────┐       ┌─────────────┐       ┌─────────────┐
│  DANH_MUC   │       │  SAN_PHAM   │       │  NHAN_VIEN  │
├─────────────┤       ├─────────────┤       ├─────────────┤
│ maDanhMuc   │←──────│ maDanhMuc   │       │ maNhanVien  │──┐
│ tenDanhMuc  │  1:N  │ maSanPham   │       │ tenNhanVien │  │
└─────────────┘       │ tenSanPham  │       │ diaChi      │  │
                      │ giaSanPham  │       │ chucVu      │  │
                      │ soLuong     │       │ luong       │  │
                      │ donViTinh   │       │ matKhau     │  │
                      │ ngayNhap    │       └─────────────┘  │
                      └─────────────┘              │         │
                             │                     │         │
                             │ 1:N                 │ 1:N     │
                             ↓                     ↓         │
                      ┌─────────────┐       ┌─────────────┐  │
                      │HOA_DON_CT   │       │  HOA_DON    │  │
                      ├─────────────┤       ├─────────────┤  │
                      │ maCTHD      │       │ maHoaDon    │←─┘
                      │ maHoaDon    │──────→│ maNhanVien  │
                      │ maSanPham   │  N:1  │ maKhachHang │←─┐
                      │ soLuong     │       │ ngayLap     │  │
                      │ donGia      │       │ tongTien    │  │
                      └─────────────┘       └─────────────┘  │
                                                   │         │
                                                   │ N:1     │
                                                   ↓         │
                                            ┌─────────────┐  │
                                            │ KHACH_HANG  │  │
                                            ├─────────────┤  │
                                            │ maKhachHang │──┘
                                            │ tenKhachHang│
                                            │ diaChi      │
                                            │ soDienThoai │
                                            │ email       │
                                            └─────────────┘
```

#### 3.2.2 Chi tiết thực thể

**1. Bảng DANH_MUC (Danh mục sản phẩm)**
| Tên trường | Kiểu dữ liệu | Mô tả |
|------------|--------------|-------|
| maDanhMuc | TEXT | Mã danh mục (Khóa chính) |
| tenDanhMuc | TEXT | Tên danh mục |

**2. Bảng SAN_PHAM (Sản phẩm)**
| Tên trường | Kiểu dữ liệu | Mô tả |
|------------|--------------|-------|
| maSanPham | TEXT | Mã sản phẩm (Khóa chính) |
| tenSanPham | TEXT | Tên sản phẩm |
| giaSanPham | REAL | Giá sản phẩm |
| soLuong | INTEGER | Số lượng tồn kho |
| donViTinh | TEXT | Đơn vị tính (Lon, Hộp, Gói...) |
| ngayNhap | TEXT | Ngày nhập hàng |
| maDanhMuc | TEXT | Mã danh mục (Khóa ngoại) |

**3. Bảng NHAN_VIEN (Nhân viên)**
| Tên trường | Kiểu dữ liệu | Mô tả |
|------------|--------------|-------|
| maNhanVien | TEXT | Mã nhân viên (Khóa chính) |
| tenNhanVien | TEXT | Tên nhân viên |
| diaChi | TEXT | Địa chỉ |
| chucVu | INTEGER | Chức vụ (0: Nhân viên, 1: Quản lý) |
| luong | REAL | Lương |
| matKhau | TEXT | Mật khẩu đăng nhập |

**4. Bảng KHACH_HANG (Khách hàng)**
| Tên trường | Kiểu dữ liệu | Mô tả |
|------------|--------------|-------|
| maKhachHang | TEXT | Mã khách hàng (Khóa chính) |
| tenKhachHang | TEXT | Tên khách hàng |
| diaChi | TEXT | Địa chỉ |
| soDienThoai | TEXT | Số điện thoại |
| email | TEXT | Email |

**5. Bảng HOA_DON (Hóa đơn)**
| Tên trường | Kiểu dữ liệu | Mô tả |
|------------|--------------|-------|
| maHoaDon | TEXT | Mã hóa đơn (Khóa chính) |
| maNhanVien | TEXT | Mã nhân viên (Khóa ngoại) |
| maKhachHang | TEXT | Mã khách hàng (Khóa ngoại) |
| ngayLap | TEXT | Ngày lập hóa đơn |
| tongTien | REAL | Tổng tiền hóa đơn |

**6. Bảng HOA_DON_CHI_TIET (Chi tiết hóa đơn)**
| Tên trường | Kiểu dữ liệu | Mô tả |
|------------|--------------|-------|
| maCTHD | TEXT | Mã chi tiết hóa đơn (Khóa chính) |
| maHoaDon | TEXT | Mã hóa đơn (Khóa ngoại) |
| maSanPham | TEXT | Mã sản phẩm (Khóa ngoại) |
| soLuong | INTEGER | Số lượng mua |
| donGia | REAL | Đơn giá tại thời điểm mua |

---

### 3.3 Giao diện

#### 3.3.1 Sơ đồ tổ chức giao diện

```
                        ┌─────────────────┐
                        │ WelcomeActivity │
                        │  (Màn hình chào)│
                        └────────┬────────┘
                                 │
                                 ↓
                        ┌─────────────────┐
                        │ LoginActivity   │
                        │ (Đăng nhập)     │
                        └────────┬────────┘
                                 │
                                 ↓
                        ┌─────────────────┐
                        │ MainActivity    │
                        │ (Màn hình chính)│
                        └────────┬────────┘
                                 │
        ┌────────────────────────┼────────────────────────┐
        │                        │                        │
        ↓                        ↓                        ↓
┌───────────────┐      ┌───────────────┐      ┌───────────────┐
│   THỐNG KÊ    │      │    QUẢN LÝ    │      │  NGƯỜI DÙNG   │
├───────────────┤      ├───────────────┤      ├───────────────┤
│ - Doanh thu   │      │ - Danh mục    │      │ - Đổi mật khẩu│
│ - Top SP      │      │ - Sản phẩm    │      │ - Đăng xuất   │
│ - Top KH      │      │ - Nhân viên   │      └───────────────┘
└───────────────┘      │ - Khách hàng  │
                       │ - Hóa đơn     │
                       └───────────────┘
```

#### 3.3.2 Giao diện màn hình chính

**a) Màn hình Welcome (WelcomeActivity)**
- Hiển thị logo và tên ứng dụng
- Tự động chuyển sang màn hình đăng nhập sau 1.5 giây
- Layout: `activity_welcome.xml`

**b) Màn hình Đăng nhập (LoginActivity)**
- Gồm các thành phần:
  + TextInputEditText: Nhập tài khoản (mã nhân viên)
  + TextInputEditText: Nhập mật khẩu (có toggle hiện/ẩn)
  + CheckBox: Ghi nhớ mật khẩu
  + Button: Đăng nhập
- Sử dụng SharedPreferences để lưu thông tin đăng nhập
- Layout: `activity_login.xml`

**c) Màn hình chính (MainActivity)**
- Toolbar với menu tùy chọn
- Ảnh banner chào mừng
- GridLayout 2 cột chứa các chức năng:
  + **Phần Thống kê:** Doanh thu, Top sản phẩm, Top khách hàng
  + **Phần Quản lý:** Danh mục, Sản phẩm, Nhân viên, Khách hàng, Hóa đơn
  + **Phần Người dùng:** Đổi mật khẩu, Đăng xuất
- Phân quyền: Nhân viên (chucVu = 0) không thấy phần Thống kê và Quản lý nhân viên
- Layout: `activity_main.xml`

#### 3.3.3 Giao diện quản lý

**a) Quản lý Danh mục (QuanLyDanhMucActivity)**
- Toolbar với nút back
- ListView hiển thị danh sách danh mục
- FloatingActionButton để thêm mới
- Mỗi item gồm: Mã danh mục, Tên danh mục, Icon sửa/xóa
- Layout: `activity_quan_ly_danh_muc.xml`, `item_danh_muc.xml`

**b) Thêm/Sửa Danh mục (EditDanhMucActivity)**
- EditText: Mã danh mục (tự động sinh khi thêm mới)
- EditText: Tên danh mục
- Button Lưu và Button Hủy
- Layout: `activity_edit_danh_muc.xml`

**c) Quản lý Sản phẩm (QuanLySanPhamActivity)**
- Toolbar với nút back
- EditText tìm kiếm sản phẩm theo tên
- ListView hiển thị danh sách sản phẩm
- Icon giỏ hàng với badge số lượng
- FloatingActionButton để thêm mới
- Mỗi item gồm: Ảnh SP, Tên SP, Giá, Số lượng tồn, Icon thêm giỏ hàng/sửa/xóa
- Animation khi thêm vào giỏ hàng
- Layout: `activity_quan_ly_san_pham.xml`, `item_san_pham.xml`

**d) Thêm/Sửa Sản phẩm (EditSanPhamActivity)**
- Spinner: Chọn danh mục
- EditText: Mã sản phẩm, Tên, Giá, Số lượng, Đơn vị tính, Ngày nhập
- Button Lưu và Button Hủy
- Layout: `activity_edit_san_pham.xml`

**e) Quản lý Nhân viên (QuanLyNhanVienActivity)**
- Toolbar với nút back
- ListView hiển thị danh sách nhân viên
- FloatingActionButton để thêm mới
- Mỗi item gồm: Mã NV, Tên, Địa chỉ, Lương, Chức vụ, Icon sửa/xóa
- Định dạng tiền tệ VNĐ
- Layout: `activity_quan_ly_nhan_vien.xml`, `item_nhan_vien.xml`

**f) Thêm/Sửa Nhân viên (EditNhanVienActivity)**
- Spinner: Chọn chức vụ (Nhân viên/Quản lý)
- EditText: Mã NV, Tên, Địa chỉ, Lương, Mật khẩu
- Button Lưu và Button Hủy
- Layout: `activity_edit_nhan_vien.xml`

**g) Quản lý Khách hàng (QuanLyKhachHangActivity)**
- Toolbar với nút back
- ListView hiển thị danh sách khách hàng
- FloatingActionButton để thêm mới
- Mỗi item gồm: Mã KH, Tên, Địa chỉ, SĐT, Email, Icon sửa/xóa
- Layout: `activity_quan_ly_khach_hang.xml`, `item_khach_hang.xml`

**h) Thêm/Sửa Khách hàng (EditKhachHangActivity)**
- EditText: Mã KH, Tên, Địa chỉ, SĐT, Email
- Validate định dạng email
- Button Lưu và Button Hủy
- Layout: `activity_edit_khach_hang.xml`

**i) Quản lý Hóa đơn (QuanLyHoaDonActivity)**
- Toolbar với nút back
- ListView hiển thị danh sách hóa đơn
- Mỗi item gồm: Mã HD, Tên NV, Tên KH, Ngày lập, Tổng tiền, Icon xóa
- Click vào item để xem chi tiết hóa đơn
- Layout: `activity_quan_ly_hoa_don.xml`, `item_hoa_don.xml`

**j) Chi tiết Hóa đơn (HDCTActivity)**
- Toolbar với nút back và tiêu đề mã hóa đơn
- ListView hiển thị danh sách sản phẩm trong hóa đơn
- Mỗi item gồm: Tên SP, Đơn giá, Số lượng
- Layout: `activity_hoa_don_chi_tiet.xml`, `item_hoa_don_chi_tiet_item.xml`

**k) Giỏ hàng (GioHangActivity)**
- Toolbar với nút back
- ListView hiển thị danh sách sản phẩm trong giỏ
- Mỗi item gồm: Tên SP, Giá, Số lượng, Button +/-, Icon xóa
- TextView hiển thị tổng tiền
- Spinner chọn khách hàng
- Button Thanh toán: Tạo hóa đơn và chi tiết hóa đơn
- Layout: `activity_gio_hang.xml`, `item_gio_hang.xml`

#### 3.3.4 Các giao diện hỗ trợ khác

**a) Thống kê Doanh thu (ThongKeDoanhThuActivity)**
- Toolbar với nút back
- EditText: Ngày bắt đầu (có DatePickerDialog)
- EditText: Ngày kết thúc (có DatePickerDialog)
- Button Thống kê
- TextView hiển thị tổng doanh thu (định dạng VNĐ)
- Layout: `activity_thong_ke_doanh_thu.xml`

**b) Thống kê Top Sản phẩm (ThongKeSanPhamActivity)**
- Toolbar với nút back
- EditText: Ngày bắt đầu, Ngày kết thúc
- EditText: Số lượng top (n)
- Button Thống kê
- ListView hiển thị top n sản phẩm bán chạy nhất
- Mỗi item gồm: Ảnh SP, Tên SP, Tổng số lượng đã bán
- Layout: `activity_thong_ke_san_pham.xml`, `item_top_san_pham.xml`

**c) Thống kê Top Khách hàng (ThongKeKhachHangActivity)**
- Toolbar với nút back
- EditText: Ngày bắt đầu, Ngày kết thúc
- EditText: Số lượng top (m)
- Button Thống kê
- ListView hiển thị top m khách hàng mua nhiều nhất
- Mỗi item gồm: Avatar KH, Tên KH, Tổng chi tiêu
- Layout: `activity_thong_ke_khach_hang.xml`, `item_top_khach_hang.xml`

**d) Đổi mật khẩu (DoiMatKhauActivity)**
- Toolbar với nút back
- ImageView: Icon đổi mật khẩu
- TextInputEditText: Mật khẩu cũ (có toggle)
- TextInputEditText: Mật khẩu mới (có toggle)
- TextInputEditText: Nhập lại mật khẩu mới (có toggle)
- Button Lưu và Button Hủy
- Validate: Mật khẩu cũ đúng, mật khẩu mới khác cũ, xác nhận khớp
- Layout: `activity_doi_mat_khau.xml`

---

## 4.0 Thực hiện dự án

### 4.1 Tạo giao diện

#### 4.1.1 MainActivity

**File: `activity_main.xml`**

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:background="@color/white">

    <!-- Toolbar -->
    <androidx.appcompat.widget.Toolbar
        android:id="@+id/toolbar"
        android:layout_width="match_parent"
        android:layout_height="?attr/actionBarSize"
        android:background="?attr/colorPrimary"
        android:theme="@style/ThemeOverlay.AppCompat.Dark.ActionBar" />

    <ScrollView
        android:layout_width="match_parent"
        android:layout_height="match_parent">

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:orientation="vertical"
            android:padding="16dp">

            <!-- Ảnh chào mừng -->
            <ImageView
                android:layout_width="match_parent"
                android:layout_height="200dp"
                android:src="@drawable/background_chao"
                android:scaleType="centerCrop"/>

            <!-- Phần Thống kê -->
            <LinearLayout
                android:id="@+id/lnThongKe"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:orientation="vertical">

                <TextView
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:text="Thống kê"
                    android:textStyle="bold"
                    android:textSize="18sp"/>

                <GridLayout
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:columnCount="2"
                    android:rowCount="2">
                    <!-- Các item: Doanh thu, Top SP, Top KH -->
                </GridLayout>
            </LinearLayout>

            <!-- Phần Quản lý -->
            <!-- Phần Người dùng -->
        </LinearLayout>
    </ScrollView>
</LinearLayout>
```

**File: `MainActivity.java`**

```java
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    int chucVu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Home");

        // Gán sự kiện click cho các LinearLayout
        findViewById(R.id.lnDoanhThu).setOnClickListener(this);
        findViewById(R.id.lnSanPham).setOnClickListener(this);
        // ... các view khác

        // Phân quyền theo chức vụ
        chucVu = getIntent().getIntExtra("CHUC_VU", 0);
        if (chucVu == 0) {
            findViewById(R.id.lnThongKe).setVisibility(View.GONE);
            findViewById(R.id.lnNhanVien).setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.lnDoanhThu) {
            startActivity(new Intent(this, ThongKeDoanhThuActivity.class));
        } else if (id == R.id.lnSanPham) {
            startActivity(new Intent(this, QuanLySanPhamActivity.class));
        }
        // ... xử lý các view khác
    }
}
```

#### 4.1.2 Các màn hình quản lý

**a) Quản lý Sản phẩm**

**File: `activity_quan_ly_san_pham.xml`**
```xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <androidx.appcompat.widget.Toolbar
        android:id="@+id/toolbar"
        android:layout_width="match_parent"
        android:layout_height="?attr/actionBarSize"
        android:background="?attr/colorPrimary"/>

    <!-- Thanh tìm kiếm -->
    <EditText
        android:id="@+id/edtTimKiem"
        android:layout_below="@id/toolbar"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint="Tìm kiếm sản phẩm..."/>

    <!-- Icon giỏ hàng với badge -->
    <FrameLayout
        android:id="@+id/flGioHang"
        android:layout_alignParentEnd="true"
        android:layout_below="@id/toolbar">
        <ImageView android:src="@android:drawable/ic_menu_shopping"/>
        <TextView android:id="@+id/tvBadge"/>
    </FrameLayout>

    <!-- Danh sách sản phẩm -->
    <ListView
        android:id="@+id/lvSanPham"
        android:layout_below="@id/edtTimKiem"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>

    <!-- Nút thêm mới -->
    <com.google.android.material.floatingactionbutton.FloatingActionButton
        android:id="@+id/fabAdd"
        android:layout_alignParentBottom="true"
        android:layout_alignParentEnd="true"/>
</RelativeLayout>
```

**File: `QuanLySanPhamActivity.java`**
```java
public class QuanLySanPhamActivity extends AppCompatActivity 
        implements SanPhamAdapter.OnSanPhamClickListener {
    
    private ListView lvSanPham;
    private SanPhamAdapter adapter;
    private List<SanPham> dsSanPham;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_san_pham);

        db = new DatabaseHelper(this);
        lvSanPham = findViewById(R.id.lvSanPham);
        
        loadData();

        // Tìm kiếm sản phẩm
        edtTimKiem.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                timKiemSanPham(s.toString());
            }
        });
    }

    private void loadData() {
        dsSanPham = db.layTatCaSanPham();
        adapter = new SanPhamAdapter(this, dsSanPham, this);
        lvSanPham.setAdapter(adapter);
    }

    @Override
    public void onAddToCart(SanPham sp) {
        Common.gioHang.themSanPham(sp, 1);
        // Animation thêm giỏ hàng
    }

    @Override
    public void onEdit(SanPham sp) {
        Intent intent = new Intent(this, EditSanPhamActivity.class);
        intent.putExtra("sanPham", sp);
        startActivity(intent);
    }

    @Override
    public void onDelete(SanPham sp) {
        db.xoaSanPham(sp.getMaSanPham());
        loadData();
    }
}
```

**b) Item Sản phẩm**

**File: `item_san_pham.xml`**
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="horizontal"
    android:padding="10dp">

    <ImageView
        android:id="@+id/imgSanPham"
        android:layout_width="80dp"
        android:layout_height="80dp"/>

    <LinearLayout
        android:layout_width="0dp"
        android:layout_weight="1"
        android:orientation="vertical">
        
        <TextView android:id="@+id/tvTenSanPham"/>
        <TextView android:id="@+id/tvGia"/>
        <TextView android:id="@+id/tvSoLuong"/>
    </LinearLayout>

    <ImageView android:id="@+id/imgAddCart"/>
    <ImageView android:id="@+id/imgEdit"/>
    <ImageView android:id="@+id/imgDelete"/>
</LinearLayout>
```

#### 4.1.3 Thống kê doanh thu

**File: `activity_thong_ke_doanh_thu.xml`**
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">

    <androidx.appcompat.widget.Toolbar
        android:id="@+id/toolbar"
        android:layout_width="match_parent"
        android:layout_height="?attr/actionBarSize"/>

    <EditText
        android:id="@+id/edtNgayBatDau"
        android:hint="Từ ngày (yyyy-MM-dd)"
        android:focusable="false"/>

    <EditText
        android:id="@+id/edtNgayKetThuc"
        android:hint="Đến ngày (yyyy-MM-dd)"
        android:focusable="false"/>

    <Button
        android:id="@+id/btnThongKe"
        android:text="Thống kê"/>

    <TextView
        android:id="@+id/tvDoanhThu"
        android:text="Tổng doanh thu: 0 VNĐ"
        android:textSize="20sp"
        android:textStyle="bold"/>
</LinearLayout>
```

**File: `ThongKeDoanhThuActivity.java`**
```java
public class ThongKeDoanhThuActivity extends AppCompatActivity {
    private EditText edtNgayBatDau, edtNgayKetThuc;
    private TextView tvDoanhThu;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // ... setup views
        
        edtNgayBatDau.setOnClickListener(v -> showDatePicker(edtNgayBatDau));
        edtNgayKetThuc.setOnClickListener(v -> showDatePicker(edtNgayKetThuc));
        
        btnThongKe.setOnClickListener(v -> {
            String tuNgay = edtNgayBatDau.getText().toString();
            String denNgay = edtNgayKetThuc.getText().toString();
            double doanhThu = db.layDoanhThu(tuNgay, denNgay);
            
            NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));
            tvDoanhThu.setText("Tổng doanh thu: " + formatter.format(doanhThu) + " VNĐ");
        });
    }

    private void showDatePicker(EditText editText) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(this,
            (view, year, month, day) -> {
                String date = String.format("%04d-%02d-%02d", year, month + 1, day);
                editText.setText(date);
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }
}
```

#### 4.1.4 Thống kê top sản phẩm bán chạy nhất

**File: `ThongKeSanPhamActivity.java`**
```java
public class ThongKeSanPhamActivity extends AppCompatActivity {
    private ListView lvTopSanPham;
    private DatabaseHelper db;

    private void thongKe() {
        String tuNgay = edtNgayBatDau.getText().toString();
        String denNgay = edtNgayKetThuc.getText().toString();
        int n = Integer.parseInt(edtSoLuong.getText().toString());
        
        List<TopSanPham> dsTop = db.thongKeTopSanPham(tuNgay, denNgay, n);
        TopSanPhamAdapter adapter = new TopSanPhamAdapter(this, dsTop);
        lvTopSanPham.setAdapter(adapter);
    }
}
```

#### 4.1.5 Thống kê top khách hàng mua nhiều nhất

**File: `ThongKeKhachHangActivity.java`**
```java
public class ThongKeKhachHangActivity extends AppCompatActivity {
    private ListView lvTopKhachHang;
    private DatabaseHelper db;

    private void thongKe() {
        String tuNgay = edtNgayBatDau.getText().toString();
        String denNgay = edtNgayKetThuc.getText().toString();
        int m = Integer.parseInt(edtSoLuong.getText().toString());
        
        List<TopKhachHang> dsTop = db.thongKeTopKhachHang(tuNgay, denNgay, m);
        TopKhachHangAdapter adapter = new TopKhachHangAdapter(this, dsTop);
        lvTopKhachHang.setAdapter(adapter);
    }
}
```

#### 4.1.6 Các giao diện hỗ trợ khác

**a) Giỏ hàng (GioHangActivity)**
```java
public class GioHangActivity extends AppCompatActivity {
    private ListView lvGioHang;
    private TextView tvTongTien;
    private Spinner spnKhachHang;

    private void thanhToan() {
        // Lấy khách hàng được chọn
        KhachHang kh = (KhachHang) spnKhachHang.getSelectedItem();
        
        // Tạo hóa đơn mới
        String maHD = db.taoMaHoaDonMoi();
        HoaDon hd = new HoaDon(maHD, Common.maNhanVien, 
                              kh.getMaKhachHang(), ngayHienTai, tongTien);
        db.themHoaDon(hd);
        
        // Tạo chi tiết hóa đơn
        for (GioHangItem item : Common.gioHang.getDsGioHang()) {
            String maCTHD = db.taoMaHDCTMoi();
            HoaDonChiTiet hdct = new HoaDonChiTiet(maCTHD, maHD,
                item.getSanPham().getMaSanPham(),
                item.getSoLuong(),
                item.getSanPham().getGiaSanPham());
            db.themHDCT(hdct);
        }
        
        // Xóa giỏ hàng
        Common.gioHang.getDsGioHang().clear();
        Toast.makeText(this, "Thanh toán thành công!", Toast.LENGTH_SHORT).show();
        finish();
    }
}
```

**b) Đổi mật khẩu (DoiMatKhauActivity)**
```java
public class DoiMatKhauActivity extends AppCompatActivity {
    private void doiMatKhau() {
        String matKhauCu = edtMatKhauCu.getText().toString().trim();
        String matKhauMoi = edtMatKhauMoi.getText().toString().trim();
        String xacNhan = edtNhapLaiMatKhauMoi.getText().toString().trim();

        // Validate
        if (matKhauCu.isEmpty() || matKhauMoi.isEmpty() || xacNhan.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }
        
        if (!db.kiemTraMatKhauCu(Common.maNhanVien, matKhauCu)) {
            Toast.makeText(this, "Mật khẩu cũ không chính xác!", Toast.LENGTH_SHORT).show();
            return;
        }
        
        if (matKhauMoi.equals(matKhauCu)) {
            Toast.makeText(this, "Mật khẩu mới phải khác mật khẩu cũ", Toast.LENGTH_SHORT).show();
            return;
        }
        
        if (!matKhauMoi.equals(xacNhan)) {
            Toast.makeText(this, "Mật khẩu mới không khớp!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Cập nhật mật khẩu
        if (db.capNhatMatKhauMoi(Common.maNhanVien, matKhauMoi)) {
            Toast.makeText(this, "Đổi mật khẩu thành công!", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
```

---

### 4.2 Tạo CSDL với SQLite

#### 4.2.1 Sơ đồ quan hệ

```
DANH_MUC (maDanhMuc, tenDanhMuc)
    │
    │ 1:N
    ↓
SAN_PHAM (maSanPham, tenSanPham, giaSanPham, soLuong, donViTinh, ngayNhap, maDanhMuc)
    │
    │ 1:N
    ↓
HOA_DON_CHI_TIET (maCTHD, maHoaDon, maSanPham, soLuong, donGia)
    │
    │ N:1
    ↓
HOA_DON (maHoaDon, maNhanVien, maKhachHang, ngayLap, tongTien)
    ↑           ↑
    │ N:1       │ N:1
    │           │
NHAN_VIEN      KHACH_HANG
```

#### 4.2.2 Chi tiết các bảng

**Script tạo bảng:**

```sql
-- Bảng Danh mục
CREATE TABLE DANH_MUC (
    maDanhMuc TEXT PRIMARY KEY,
    tenDanhMuc TEXT NOT NULL
);

-- Bảng Sản phẩm
CREATE TABLE SAN_PHAM (
    maSanPham TEXT PRIMARY KEY,
    tenSanPham TEXT NOT NULL,
    giaSanPham REAL NOT NULL,
    soLuong INTEGER DEFAULT 0,
    donViTinh TEXT,
    ngayNhap TEXT,
    maDanhMuc TEXT,
    FOREIGN KEY (maDanhMuc) REFERENCES DANH_MUC(maDanhMuc)
);

-- Bảng Nhân viên
CREATE TABLE NHAN_VIEN (
    maNhanVien TEXT PRIMARY KEY,
    tenNhanVien TEXT NOT NULL,
    diaChi TEXT,
    chucVu INTEGER DEFAULT 0,
    luong REAL DEFAULT 0,
    matKhau TEXT NOT NULL
);

-- Bảng Khách hàng
CREATE TABLE KHACH_HANG (
    maKhachHang TEXT PRIMARY KEY,
    tenKhachHang TEXT NOT NULL,
    diaChi TEXT,
    soDienThoai TEXT,
    email TEXT
);

-- Bảng Hóa đơn
CREATE TABLE HOA_DON (
    maHoaDon TEXT PRIMARY KEY,
    maNhanVien TEXT,
    maKhachHang TEXT,
    ngayLap TEXT,
    tongTien REAL DEFAULT 0,
    FOREIGN KEY (maNhanVien) REFERENCES NHAN_VIEN(maNhanVien),
    FOREIGN KEY (maKhachHang) REFERENCES KHACH_HANG(maKhachHang)
);

-- Bảng Chi tiết hóa đơn
CREATE TABLE HOA_DON_CHI_TIET (
    maCTHD TEXT PRIMARY KEY,
    maHoaDon TEXT,
    maSanPham TEXT,
    soLuong INTEGER DEFAULT 1,
    donGia REAL,
    FOREIGN KEY (maHoaDon) REFERENCES HOA_DON(maHoaDon),
    FOREIGN KEY (maSanPham) REFERENCES SAN_PHAM(maSanPham)
);
```

---

### 4.3 Lập trình CSDL

#### 4.3.1 SQLiteOpenHelper

**File: `DatabaseHelper.java`**

```java
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "QuanLyBanHang.db";
    private static final int DATABASE_VERSION = 1;

    // Tên các bảng
    public static final String TABLE_DANH_MUC = "DANH_MUC";
    public static final String TABLE_SAN_PHAM = "SAN_PHAM";
    public static final String TABLE_NHAN_VIEN = "NHAN_VIEN";
    public static final String TABLE_KHACH_HANG = "KHACH_HANG";
    public static final String TABLE_HOA_DON = "HOA_DON";
    public static final String TABLE_HOA_DON_CHI_TIET = "HOA_DON_CHI_TIET";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng DANH_MUC
        String createDanhMuc = "CREATE TABLE " + TABLE_DANH_MUC + " (" +
                "maDanhMuc TEXT PRIMARY KEY, " +
                "tenDanhMuc TEXT NOT NULL)";
        db.execSQL(createDanhMuc);

        // Tạo bảng SAN_PHAM
        String createSanPham = "CREATE TABLE " + TABLE_SAN_PHAM + " (" +
                "maSanPham TEXT PRIMARY KEY, " +
                "tenSanPham TEXT NOT NULL, " +
                "giaSanPham REAL NOT NULL, " +
                "soLuong INTEGER DEFAULT 0, " +
                "donViTinh TEXT, " +
                "ngayNhap TEXT, " +
                "maDanhMuc TEXT, " +
                "FOREIGN KEY (maDanhMuc) REFERENCES " + TABLE_DANH_MUC + "(maDanhMuc))";
        db.execSQL(createSanPham);

        // ... tạo các bảng khác tương tự
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HOA_DON_CHI_TIET);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HOA_DON);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SAN_PHAM);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DANH_MUC);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NHAN_VIEN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_KHACH_HANG);
        onCreate(db);
    }
}
```

#### 4.3.2 Entity class & CRUD

**a) Model class: SanPham.java**

```java
public class SanPham implements Parcelable {
    private String maSanPham;
    private String tenSanPham;
    private double giaSanPham;
    private int soLuong;
    private String donViTinh;
    private String ngayNhap;
    private String maDanhMuc;

    // Constructor
    public SanPham(String maSanPham, String tenSanPham, double giaSanPham,
                   int soLuong, String donViTinh, String ngayNhap, String maDanhMuc) {
        this.maSanPham = maSanPham;
        this.tenSanPham = tenSanPham;
        this.giaSanPham = giaSanPham;
        this.soLuong = soLuong;
        this.donViTinh = donViTinh;
        this.ngayNhap = ngayNhap;
        this.maDanhMuc = maDanhMuc;
    }

    // Getters và Setters
    // ... 

    // Parcelable implementation để truyền object giữa các Activity
    // ...
}
```

**b) CRUD Operations trong DatabaseHelper**

```java
// ========== SẢN PHẨM ==========

// CREATE - Thêm sản phẩm
public long themSanPham(SanPham sp) {
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues values = new ContentValues();
    values.put("maSanPham", sp.getMaSanPham());
    values.put("tenSanPham", sp.getTenSanPham());
    values.put("giaSanPham", sp.getGiaSanPham());
    values.put("soLuong", sp.getSoLuong());
    values.put("donViTinh", sp.getDonViTinh());
    values.put("ngayNhap", sp.getNgayNhap());
    values.put("maDanhMuc", sp.getMaDanhMuc());
    return db.insert(TABLE_SAN_PHAM, null, values);
}

// READ - Lấy tất cả sản phẩm
public List<SanPham> layTatCaSanPham() {
    List<SanPham> dsSanPham = new ArrayList<>();
    SQLiteDatabase db = this.getReadableDatabase();
    Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_SAN_PHAM, null);
    
    if (cursor.moveToFirst()) {
        do {
            SanPham sp = new SanPham(
                cursor.getString(0),  // maSanPham
                cursor.getString(1),  // tenSanPham
                cursor.getDouble(2),  // giaSanPham
                cursor.getInt(3),     // soLuong
                cursor.getString(4),  // donViTinh
                cursor.getString(5),  // ngayNhap
                cursor.getString(6)   // maDanhMuc
            );
            dsSanPham.add(sp);
        } while (cursor.moveToNext());
    }
    cursor.close();
    return dsSanPham;
}

// UPDATE - Cập nhật sản phẩm
public int suaSanPham(SanPham sp) {
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues values = new ContentValues();
    values.put("tenSanPham", sp.getTenSanPham());
    values.put("giaSanPham", sp.getGiaSanPham());
    values.put("soLuong", sp.getSoLuong());
    values.put("donViTinh", sp.getDonViTinh());
    values.put("ngayNhap", sp.getNgayNhap());
    values.put("maDanhMuc", sp.getMaDanhMuc());
    return db.update(TABLE_SAN_PHAM, values, 
                     "maSanPham = ?", new String[]{sp.getMaSanPham()});
}

// DELETE - Xóa sản phẩm
public int xoaSanPham(String maSanPham) {
    SQLiteDatabase db = this.getWritableDatabase();
    return db.delete(TABLE_SAN_PHAM, "maSanPham = ?", new String[]{maSanPham});
}

// Tìm kiếm sản phẩm theo tên
public List<SanPham> timKiemSanPham(String tuKhoa) {
    List<SanPham> dsSanPham = new ArrayList<>();
    SQLiteDatabase db = this.getReadableDatabase();
    String query = "SELECT * FROM " + TABLE_SAN_PHAM + 
                   " WHERE tenSanPham LIKE ?";
    Cursor cursor = db.rawQuery(query, new String[]{"%" + tuKhoa + "%"});
    // ... xử lý cursor
    return dsSanPham;
}

// Tạo mã sản phẩm mới tự động
public String taoMaSanPhamMoi() {
    SQLiteDatabase db = this.getReadableDatabase();
    Cursor cursor = db.rawQuery(
        "SELECT maSanPham FROM " + TABLE_SAN_PHAM + 
        " ORDER BY maSanPham DESC LIMIT 1", null);
    
    if (cursor.moveToFirst()) {
        String lastMa = cursor.getString(0);
        int number = Integer.parseInt(lastMa.substring(2)) + 1;
        cursor.close();
        return "SP" + String.format("%03d", number);
    }
    cursor.close();
    return "SP001";
}
```

#### 4.3.3 Lập trình chức năng thống kê

```java
// ========== THỐNG KÊ ==========

// Thống kê doanh thu theo khoảng thời gian
public double layDoanhThu(String tuNgay, String denNgay) {
    SQLiteDatabase db = this.getReadableDatabase();
    String query = "SELECT SUM(tongTien) FROM " + TABLE_HOA_DON +
                   " WHERE ngayLap BETWEEN ? AND ?";
    Cursor cursor = db.rawQuery(query, new String[]{tuNgay, denNgay});
    
    double tongDoanhThu = 0;
    if (cursor.moveToFirst()) {
        tongDoanhThu = cursor.getDouble(0);
    }
    cursor.close();
    return tongDoanhThu;
}

// Thống kê top N sản phẩm bán chạy nhất
public List<TopSanPham> thongKeTopSanPham(String tuNgay, String denNgay, int n) {
    List<TopSanPham> dsTop = new ArrayList<>();
    SQLiteDatabase db = this.getReadableDatabase();
    
    String query = "SELECT sp.maSanPham, sp.tenSanPham, SUM(hdct.soLuong) as tongSoLuong " +
                   "FROM " + TABLE_HOA_DON_CHI_TIET + " hdct " +
                   "INNER JOIN " + TABLE_SAN_PHAM + " sp ON hdct.maSanPham = sp.maSanPham " +
                   "INNER JOIN " + TABLE_HOA_DON + " hd ON hdct.maHoaDon = hd.maHoaDon " +
                   "WHERE hd.ngayLap BETWEEN ? AND ? " +
                   "GROUP BY sp.maSanPham, sp.tenSanPham " +
                   "ORDER BY tongSoLuong DESC " +
                   "LIMIT ?";
    
    Cursor cursor = db.rawQuery(query, 
        new String[]{tuNgay, denNgay, String.valueOf(n)});
    
    if (cursor.moveToFirst()) {
        do {
            TopSanPham top = new TopSanPham(
                cursor.getString(0),  // maSanPham
                cursor.getString(1),  // tenSanPham
                cursor.getInt(2)      // tongSoLuong
            );
            dsTop.add(top);
        } while (cursor.moveToNext());
    }
    cursor.close();
    return dsTop;
}

// Thống kê top M khách hàng mua nhiều nhất
public List<TopKhachHang> thongKeTopKhachHang(String tuNgay, String denNgay, int m) {
    List<TopKhachHang> dsTop = new ArrayList<>();
    SQLiteDatabase db = this.getReadableDatabase();
    
    String query = "SELECT kh.maKhachHang, kh.tenKhachHang, " +
                   "COUNT(hd.maHoaDon) as soLanMua, SUM(hd.tongTien) as tongChiTieu " +
                   "FROM " + TABLE_KHACH_HANG + " kh " +
                   "INNER JOIN " + TABLE_HOA_DON + " hd ON kh.maKhachHang = hd.maKhachHang " +
                   "WHERE hd.ngayLap BETWEEN ? AND ? " +
                   "GROUP BY kh.maKhachHang, kh.tenKhachHang " +
                   "ORDER BY tongChiTieu DESC " +
                   "LIMIT ?";
    
    Cursor cursor = db.rawQuery(query, 
        new String[]{tuNgay, denNgay, String.valueOf(m)});
    
    if (cursor.moveToFirst()) {
        do {
            TopKhachHang top = new TopKhachHang(
                cursor.getString(0),  // maKhachHang
                cursor.getString(1),  // tenKhachHang
                cursor.getInt(2),     // soLanMua
                cursor.getDouble(3)   // tongChiTieu
            );
            dsTop.add(top);
        } while (cursor.moveToNext());
    }
    cursor.close();
    return dsTop;
}
```

---

### 4.4 Lập trình chức năng

#### 4.4.1 Đăng nhập và phân quyền

```java
// LoginActivity.java
private void checkDangNhap() {
    String maNhanVien = edtUsername.getText().toString().trim();
    String password = edtPassword.getText().toString().trim();

    if (maNhanVien.isEmpty() || password.isEmpty()) {
        Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
        return;
    }

    NhanVien nhanVien = db.layNhanVienBangMaNV(maNhanVien);

    if (nhanVien == null) {
        Toast.makeText(this, "Tài khoản không tồn tại!", Toast.LENGTH_SHORT).show();
        return;
    }

    if (password.equals(nhanVien.getMatKhau())) {
        // Lưu thông tin nếu chọn ghi nhớ
        if (chkRemember.isChecked()) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("username", maNhanVien);
            editor.putString("password", password);
            editor.putBoolean("remember", true);
            editor.apply();
        }

        // Chuyển sang MainActivity với thông tin chức vụ
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.putExtra("CHUC_VU", nhanVien.getChucVu());
        startActivity(intent);
        
        // Lưu mã nhân viên vào Common để sử dụng toàn app
        Common.maNhanVien = maNhanVien;
        finish();
    } else {
        Toast.makeText(this, "Sai mật khẩu!", Toast.LENGTH_SHORT).show();
    }
}
```

#### 4.4.2 Giỏ hàng và thanh toán

```java
// Common.java - Biến toàn cục
public class Common {
    public static GioHang gioHang = new GioHang();
    public static String maNhanVien = "";
}

// GioHang.java - Quản lý giỏ hàng
public class GioHang {
    private List<GioHangItem> dsGioHang = new ArrayList<>();

    public void themSanPham(SanPham sp, int soLuong) {
        // Kiểm tra sản phẩm đã có trong giỏ chưa
        for (GioHangItem item : dsGioHang) {
            if (item.getSanPham().getMaSanPham().equals(sp.getMaSanPham())) {
                item.setSoLuong(item.getSoLuong() + soLuong);
                return;
            }
        }
        // Nếu chưa có, thêm mới
        dsGioHang.add(new GioHangItem(sp, soLuong));
    }

    public double tinhTongTien() {
        double tong = 0;
        for (GioHangItem item : dsGioHang) {
            tong += item.getSanPham().getGiaSanPham() * item.getSoLuong();
        }
        return tong;
    }
}

// GioHangActivity.java - Thanh toán
private void thanhToan() {
    if (Common.gioHang.getDsGioHang().isEmpty()) {
        Toast.makeText(this, "Giỏ hàng trống!", Toast.LENGTH_SHORT).show();
        return;
    }

    KhachHang kh = (KhachHang) spnKhachHang.getSelectedItem();
    String ngayHienTai = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                         .format(new Date());
    double tongTien = Common.gioHang.tinhTongTien();

    // Tạo hóa đơn
    String maHD = db.taoMaHoaDonMoi();
    HoaDon hd = new HoaDon(maHD, Common.maNhanVien, 
                          kh.getMaKhachHang(), ngayHienTai, tongTien);
    db.themHoaDon(hd);

    // Tạo chi tiết hóa đơn
    for (GioHangItem item : Common.gioHang.getDsGioHang()) {
        String maCTHD = db.taoMaHDCTMoi();
        HoaDonChiTiet hdct = new HoaDonChiTiet(
            maCTHD, maHD,
            item.getSanPham().getMaSanPham(),
            item.getSoLuong(),
            item.getSanPham().getGiaSanPham()
        );
        db.themHDCT(hdct);
    }

    // Xóa giỏ hàng
    Common.gioHang.getDsGioHang().clear();
    Toast.makeText(this, "Thanh toán thành công!", Toast.LENGTH_SHORT).show();
    finish();
}
```

#### 4.4.3 Adapter hiển thị dữ liệu

```java
// SanPhamAdapter.java
public class SanPhamAdapter extends BaseAdapter {
    private Context context;
    private List<SanPham> dsSanPham;
    private OnSanPhamClickListener listener;

    public interface OnSanPhamClickListener {
        void onAddToCart(SanPham sp);
        void onEdit(SanPham sp);
        void onDelete(SanPham sp);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                .inflate(R.layout.item_san_pham, parent, false);
        }

        SanPham sp = dsSanPham.get(position);

        TextView tvTen = convertView.findViewById(R.id.tvTenSanPham);
        TextView tvGia = convertView.findViewById(R.id.tvGia);
        TextView tvSoLuong = convertView.findViewById(R.id.tvSoLuong);
        ImageView imgAddCart = convertView.findViewById(R.id.imgAddCart);
        ImageView imgEdit = convertView.findViewById(R.id.imgEdit);
        ImageView imgDelete = convertView.findViewById(R.id.imgDelete);

        tvTen.setText(sp.getTenSanPham());
        
        // Format giá tiền
        NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));
        tvGia.setText(formatter.format(sp.getGiaSanPham()) + " VNĐ");
        
        tvSoLuong.setText("Còn: " + sp.getSoLuong() + " " + sp.getDonViTinh());

        // Xử lý sự kiện click
        imgAddCart.setOnClickListener(v -> listener.onAddToCart(sp));
        imgEdit.setOnClickListener(v -> listener.onEdit(sp));
        imgDelete.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                .setTitle("Xác nhận xóa")
                .setMessage("Bạn có chắc muốn xóa " + sp.getTenSanPham() + "?")
                .setPositiveButton("Xóa", (d, w) -> listener.onDelete(sp))
                .setNegativeButton("Hủy", null)
                .show();
        });

        return convertView;
    }
}
```

#### 4.4.4 Màn hình thống kê

**Thống kê doanh thu:**
```java
public class ThongKeDoanhThuActivity extends AppCompatActivity {
    private void thongKe() {
        String tuNgay = edtNgayBatDau.getText().toString().trim();
        String denNgay = edtNgayKetThuc.getText().toString().trim();

        if (tuNgay.isEmpty() || denNgay.isEmpty()) {
            Toast.makeText(this, "Vui lòng chọn đầy đủ ngày", Toast.LENGTH_SHORT).show();
            return;
        }

        double doanhThu = db.layDoanhThu(tuNgay, denNgay);
        
        NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));
        tvDoanhThu.setText("Tổng doanh thu: " + formatter.format(doanhThu) + " VNĐ");
    }
}
```

**Thống kê top sản phẩm:**
```java
public class ThongKeSanPhamActivity extends AppCompatActivity {
    private void thongKe() {
        String tuNgay = edtNgayBatDau.getText().toString().trim();
        String denNgay = edtNgayKetThuc.getText().toString().trim();
        String strN = edtSoLuong.getText().toString().trim();

        if (tuNgay.isEmpty() || denNgay.isEmpty() || strN.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        int n = Integer.parseInt(strN);
        List<TopSanPham> dsTop = db.thongKeTopSanPham(tuNgay, denNgay, n);
        
        TopSanPhamAdapter adapter = new TopSanPhamAdapter(this, dsTop);
        lvTopSanPham.setAdapter(adapter);
    }
}
```

**Thống kê top khách hàng:**
```java
public class ThongKeKhachHangActivity extends AppCompatActivity {
    private void thongKe() {
        String tuNgay = edtNgayBatDau.getText().toString().trim();
        String denNgay = edtNgayKetThuc.getText().toString().trim();
        String strM = edtSoLuong.getText().toString().trim();

        if (tuNgay.isEmpty() || denNgay.isEmpty() || strM.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        int m = Integer.parseInt(strM);
        List<TopKhachHang> dsTop = db.thongKeTopKhachHang(tuNgay, denNgay, m);
        
        TopKhachHangAdapter adapter = new TopKhachHangAdapter(this, dsTop);
        lvTopKhachHang.setAdapter(adapter);
    }
}
```

---

## 5.0 Tổng kết

### 5.1 Kết quả đạt được

- ✅ Xây dựng hoàn chỉnh ứng dụng quản lý bán hàng trên nền tảng Android
- ✅ Thiết kế cơ sở dữ liệu SQLite với 6 bảng quan hệ
- ✅ Implement đầy đủ chức năng CRUD cho tất cả các đối tượng
- ✅ Chức năng thống kê: Doanh thu, Top sản phẩm, Top khách hàng
- ✅ Hệ thống đăng nhập với phân quyền (Quản lý/Nhân viên)
- ✅ Chức năng giỏ hàng và thanh toán
- ✅ Giao diện thân thiện, dễ sử dụng

### 5.2 Cấu trúc dự án

```
PH60001_MOB2041/
├── app/src/main/
│   ├── java/fpl/ph60001/ph60001_mob2041/
│   │   ├── model/          (8 files: Entity classes)
│   │   ├── dto/            (3 files: Data Transfer Objects)
│   │   ├── common/         (3 files: Common utilities)
│   │   ├── helper/         (1 file: DatabaseHelper)
│   │   ├── adapter/        (9 files: ListView adapters)
│   │   └── *.java          (18 Activity files)
│   │
│   └── res/
│       ├── layout/         (28 XML layout files)
│       ├── drawable/       (7 drawable resources)
│       ├── menu/           (1 menu file)
│       └── values/         (colors, strings, styles)
│
└── AndroidManifest.xml
```

### 5.3 Thông tin đăng nhập mẫu

| Vai trò | Tài khoản | Mật khẩu |
|---------|-----------|----------|
| Quản lý | NV001 | admin123 |
| Nhân viên | NV002 | sales456 |

---

**Ngày hoàn thành:** _____________________

**Sinh viên thực hiện:** _____________________

**Mã sinh viên:** PH60001

