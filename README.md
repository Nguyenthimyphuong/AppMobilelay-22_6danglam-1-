# Tên Đề Tài Dự Án: Xây Dựng App Bán Hàng Mẹ Và Bé

> ☕ **Nếu dự án này giúp ích cho bạn, hãy mời mình một cốc nước để có thêm động lực nhé! Cảm ơn bạn rất nhiều!**
>Hoan hỉ hoan hỉ. Donate thì sẽ được gấp đôi sự may mắn đó. Ahihi. UTEHY - K21
> **Thông tin Donate (Chuyển khoản):**
> - **Ngân hàng:** MB Bank
> - **Số tài khoản:** 02788888888899
> - **Chủ tài khoản:** NGUYEN THI MY PHUONG
> - **Nội dung:** `Donate App Mobile`

## 1. Giới thiệu đề tài
- **Bài toán:** 
Trong những năm gần đây, nhu cầu mua sắm trực tuyến ngày càng phát triển mạnh mẽ, đặc biệt là trên các thiết bị di động. Điện thoại thông minh trở thành công cụ quen thuộc trong đời sống hằng ngày, giúp người dùng có thể tìm kiếm thông tin, lựa chọn sản phẩm và thực hiện các giao dịch mua bán một cách nhanh chóng, thuận tiện. Đối với các gia đình có trẻ nhỏ, nhu cầu mua sắm các sản phẩm mẹ và bé như bỉm, sữa, khăn ướt, đồ dùng sơ sinh, quần áo trẻ em, sản phẩm chăm sóc mẹ và bé luôn diễn ra thường xuyên. Vì vậy, việc xây dựng một ứng dụng mobile hỗ trợ bán đồ mẹ và bé là cần thiết, phù hợp với xu hướng mua sắm hiện đại.
- **Mục tiêu:** 
Mục tiêu tổng quát của đề tài là xây dựng ứng dụng mobile bán đồ mẹ và bé trên nền tảng Android, hỗ trợ khách hàng xem thông tin sản phẩm, lựa chọn sản phẩm phù hợp và thực hiện các thao tác mua hàng cơ bản một cách thuận tiện. Bên cạnh đó, ứng dụng còn hỗ trợ người quản trị trong việc quản lý sản phẩm, khách hàng, nhà cung cấp, hóa đơn và thống kê dữ liệu bán hàng. Qua đó, hệ thống góp phần nâng cao hiệu quả quản lý cửa hàng, cải thiện trải nghiệm mua sắm của khách hàng và phù hợp với xu hướng ứng dụng công nghệ trong hoạt động kinh doanh hiện nay.

## 2. Dataset / Cơ sở dữ liệu
- **Nguồn dữ liệu:** Ứng dụng sử dụng SQLite.
- **Mô tả cấu trúc:** 
Bảng users
id
username
password
hoten
sdt
diachi
Bảng hoadon
id
userId
hoTen
soDienThoai
diaChi
ghiChu
hinhThucThanhToan
sanPham
tongTien
ngayDat
## 3. Pipeline / Luồng xử lý của ứng dụng
Đăng ký → Đăng nhập → Xem sản phẩm → Thêm vào giỏ hàng → Thanh toán → Lưu hóa đơn → Hiển thị lịch sử đơn hàng.

## 4. Mô hình / Công nghệ sử dụng
- **Ngôn ngữ:** Java
- **Công nghệ chính:** 
Java
Android Studio
SQLite
XML
- **Lý do chọn:** 
Java: Là ngôn ngữ lập trình phổ biến trong phát triển ứng dụng Android, có cú pháp rõ ràng, dễ tiếp cận và được hỗ trợ tốt bởi Android Studio.
Android Studio: Là môi trường phát triển tích hợp (IDE) chính thức dành cho Android, cung cấp đầy đủ công cụ thiết kế giao diện, lập trình, kiểm thử và đóng gói ứng dụng.
SQLite: Là hệ quản trị cơ sở dữ liệu nhúng được tích hợp sẵn trên Android, phù hợp với các ứng dụng quy mô nhỏ và vừa, không yêu cầu máy chủ riêng, giúp lưu trữ dữ liệu cục bộ hiệu quả.
XML: Được sử dụng để thiết kế giao diện người dùng, giúp tách biệt phần giao diện và phần xử lý nghiệp vụ, thuận tiện cho việc phát triển và bảo trì ứng dụng.

## 5. Kết quả đạt được
- Sau quá trình phân tích, thiết kế và triển khai, ứng dụng quản lý cửa hàng mẹ và bé trên nền tảng Android đã được xây dựng hoàn chỉnh với các chức năng chính như sau:
- Phân hệ người dùng
Đăng ký tài khoản mới.
Đăng nhập hệ thống.
Xem danh sách sản phẩm.
Xem chi tiết sản phẩm.
Thêm sản phẩm vào giỏ hàng.
Cập nhật và xóa sản phẩm trong giỏ hàng.
Thanh toán và tạo hóa đơn mua hàng.
Xem lịch sử đơn hàng đã đặt.
Xem chi tiết hóa đơn.
- Phân hệ quản trị viên
Quản lý sản phẩm (thêm, sửa, xóa, tìm kiếm).
Quản lý khách hàng (xem, tìm kiếm, xóa).
Quản lý nhà cung cấp (thêm, sửa, xóa, tìm kiếm, xem chi tiết).
Quản lý hóa đơn (xem danh sách, tìm kiếm, xem chi tiết, xóa).
Thống kê số lượng khách hàng.
Thống kê số lượng hóa đơn.
Thống kê doanh thu bán hàng.
Thống kê số lượng nhà cung cấp.
- Kết quả triển khai
Xây dựng thành công giao diện ứng dụng bằng XML.
Xây dựng các chức năng xử lý nghiệp vụ bằng Java.
Sử dụng SQLite để lưu trữ dữ liệu người dùng và hóa đơn.
Đóng gói và chạy thành công trên thiết bị Android thông qua file APK.
Hoàn thành đầy đủ các tài liệu phân tích, thiết kế, kiểm thử và triển khai hệ thống.

## 6. Hướng dẫn chạy dự án
### Cài đặt môi trường
- Tải và cài đặt [Android Studio](https://developer.android.com/studio).
- Cấu hình JDK 17 (hoặc phiên bản bạn đang dùng).

### Cách chạy ứng dụng
1. Mở Android Studio, chọn **Open** và tìm đến thư mục gốc của dự án này.
2. Đợi dự án Gradle đồng bộ xong (Sync Gradle).
3. Kết nối thiết bị Android (hoặc mở máy ảo Emulator).
4. Nhấn nút **Run** (hình tam giác màu xanh) để chạy app.

## 7. Cấu trúc thư mục dự án
```text
├── .github/
├── app/               # Source code chính của ứng dụng Android
├── demo/              # Video demo / file APK chạy thử ứng dụng
├── data/              # File dữ liệu mẫu / cấu hình cơ sở dữ liệu ban đầu
├── reports/           # Tài liệu báo cáo chi tiết (Word/PDF)
├── slides/            # Slide thuyết trình (PPTX/PDF)
├── requirements.txt   # Danh sách công cụ và phiên bản môi trường cần thiết
└── README.md          # File hướng dẫn này

## 8. Tác giả
- Họ và tên: Nguyễn Thị Mỹ Phượng & Nguyễn Thị Thúy
- Mã SV: 12523067 & 12523082
- Lớp: 12523T.1
- Nhóm:8