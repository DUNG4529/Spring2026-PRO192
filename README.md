# Spring2026-PRO192

Human Resource Management System

## 📋 Mô Tả Dự Án

Hệ thống quản lý nhân sự (HRMS) được xây dựng bằng Java, cung cấp các chức năng quản lý nhân viên, chấm công, tính lương và báo cáo cho các công ty.

## 🎯 Chức Năng Chính

### 1. Quản Lý Nhân Viên

- Thêm nhân viên mới (Full-time hoặc Part-time)
- Xem danh sách nhân viên
- Cập nhật thông tin nhân viên
- Xóa nhân viên khỏi hệ thống

### 2. Quản Lý Chấm Công

- Ghi nhận chấm công hàng ngày
- Xem lịch sử chấm công
- Cập nhật bản ghi chấm công
- Xóa bản ghi chấm công

### 3. Quản Lý Lương

- Tính lương nhân viên dựa trên:
  - Lương cơ bản
  - Ngày công
  - Giờ làm thêm (Overtime)
  - Số ngày nghỉ
- Xem bảng lương tất cả nhân viên
- Xuất bảng lương ra file

### 4. Báo Cáo

- Báo cáo tham gia làm việc
- Báo cáo lương
- Báo cáo tổng hợp

## 🏗️ Cấu Trúc Dự Án

```
Project_LAB/
├── src/
│   ├── main/java/Model/
│   │   ├── Employee.java (lớp trừu tượng)
│   │   ├── FullTimeEmployee.java
│   │   ├── PartTimeEmployee.java
│   │   ├── Attendance.java
│   │   ├── HRManager.java
│   │   ├── displayMenu.java
│   │   └── Validation.java
│   └── test/java/
├── pom.xml
└── README.md
```

## 🔧 Công Nghệ Sử Dụng

- **Ngôn Ngữ**: Java
- **Build Tool**: Maven
- **JDK**: 8 trở lên

## 📦 Cách Cài Đặt

1. Clone dự án:

```bash
git clone <repository-url>
cd Project_LAB
```

2. Build dự án với Maven:

```bash
mvn clean install
```

3. Chạy ứng dụng:

```bash
mvn exec:java -Dexec.mainClass="Main"
```

## 📝 Hướng Dẫn Sử Dụng

### Thêm Nhân Viên

1. Chọn "Quản lý nhân viên" từ menu chính
2. Chọn "Thêm nhân viên mới"
3. Nhập thông tin nhân viên (ID, Tên, Phòng ban, v.v.)
4. Chọn loại nhân viên (Full-time/Part-time)

### Ghi Chấm Công

1. Chọn "Quản lý chấm công"
2. Chọn "Ghi chấm công"
3. Nhập mã nhân viên, ngày, trạng thái chấm công
4. Nhập giờ làm thêm (nếu có)

### Tính Lương

1. Chọn "Quản lý lương"
2. Chọn "Tính lương nhân viên"
3. Nhập mã nhân viên
4. Nhập số ngày nghỉ (nếu có)

## 👥 Thông Tin Nhân Viên

Mỗi nhân viên có các thuộc tính:

- **ID**: Mã nhân viên (duy nhất)
- **Tên**: Tên đầy đủ
- **Phòng Ban**: Nơi làm việc
- **Lương Cơ Bản**: Mức lương cơ bản
- **Chức Vụ**: Vị trí công việc
- **Ngày Vào Làm**: Ngày bắt đầu
- **Trạng Thái**: Active/Inactive

## 💰 Cách Tính Lương

### Nhân Viên Full-time

```
Lương = Lương Cơ Bản + Phụ Cấp + (Giờ Làm Thêm × 1.5 × Giờ Công)
```

### Nhân Viên Part-time

```
Lương = Giờ Công × Tỉ Giá Giờ + (Giờ Làm Thêm × 2 × Tỉ Giá Giờ)
```

## 🐛 Troubleshooting

### Lỗi khi chạy ứng dụng

- Kiểm tra phiên bản Java: `java -version`
- Kiểm tra Maven: `mvn -version`
- Xóa folder `target` và build lại: `mvn clean install`

## 📞 Hỗ Trợ

Nếu gặp vấn đề, vui lòng:

1. Kiểm tra file log
2. Xem lại input dữ liệu
3. Liên hệ với team phát triển

## 📄 Giấy Phép

Dự án này là phần của khóa học PRO192 tại FPT University.

## 👨‍💼 Tác Giả

Nhóm phát triển:

- **Trần Tuấn Đạt**
- **Nguyễn Tiến Dũng**
- **Ngô Công Hoàng**
- **Huỳnh Ngô Trà Giang**

---

**Phiên Bản**: 1.8
**Ngày Cập Nhật**: 2026-01-16  
**Trạng Thái**: Đang Phát Triển
