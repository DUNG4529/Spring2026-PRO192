# HR Management System

**Version:** 1.5.3 &nbsp;|&nbsp; **Project Manager:** Nguyễn Tiến Dũng &nbsp;|&nbsp; **Course:** PRO192 — Spring 2026 — FPT University

---

## Mô tả

Console application quản lý nhân sự (HRMS) viết bằng Java. Hỗ trợ quản lý nhân viên, chấm công, tính lương và xuất báo cáo. Dữ liệu được tự động đọc/ghi từ file text qua các phiên sử dụng.

---

## Chức năng

| Task | Mô tả                                      |
| ---- | ------------------------------------------ |
| B1   | Thêm nhân viên mới (Full-time / Part-time) |
| B2   | Cập nhật thông tin nhân viên               |
| B3   | Xem danh sách nhân viên                    |
| B4   | Ghi nhận chấm công theo ngày               |
| B5   | Xem lịch sử chấm công theo nhân viên       |
| B6   | Tính lương nhân viên theo tháng/năm        |
| B7   | Báo cáo nhân viên vắng vượt ngưỡng         |
| B8   | Báo cáo nhân viên được trả lương cao nhất  |

---

## Cấu trúc project

```
Project_LAB/
├── data/
│   ├── employees.txt        # Dữ liệu nhân viên
│   └── attendance.txt       # Dữ liệu chấm công
├── src/main/java/
│   ├── entity/              # Employee, FullTimeEmployee, PartTimeEmployee, Attendance
│   ├── service/             # EmployeeService, AttendanceService, SalaryService, ReportService
│   ├── manager/             # HRManager (điều phối các service)
│   ├── ui/                  # Main (toàn bộ console I/O)
│   └── utils/               # Validation
├── pom.xml
└── README.md
```

---

## Công nghệ

| Thành phần     | Chi tiết                         |
| -------------- | -------------------------------- |
| Ngôn ngữ       | Java 8+                          |
| Build tool     | Maven                            |
| Lưu trữ        | File text, pipe-delimited (`\|`) |
| Định dạng ngày | dd/MM/yyyy                       |

---

## Cài đặt & chạy

```bash
# Build
mvn clean compile

# Chạy
mvn exec:java -Dexec.mainClass="ui.Main"
```

Hoặc chạy trực tiếp `Main.java` từ IDE (IntelliJ / NetBeans / VS Code).

> Dữ liệu được tự động load từ `data/` khi khởi động và tự động lưu sau mỗi thao tác.

---

## Format file dữ liệu

**employees.txt** — 8 trường mỗi dòng:

```
EM000001|Nguyen Van An|IT|Software Engineer|01/03/2023|12000000|ACTIVE|FULL_TIME
```

**attendance.txt** — 4 trường mỗi dòng:

```
EM000001|01/03/2026|PRESENT|1.5
```

---

## Nhóm phát triển

- **Nguyễn Tiến Dũng** _(Project Manager)_
- Trần Tuấn Đạt
- Ngô Công Hoàng
- Huỳnh Ngô Trà Giang

---

_Cập nhật: 11/03/2026_

---

## Mô tả

Hệ thống quản lý nhân sự (HRMS) console application xây dựng bằng Java, hỗ trợ quản lý nhân viên, chấm công, tính lương và xuất báo cáo. Dữ liệu được lưu trữ và tải từ file text giữa các phiên sử dụng.

---

## Chức năng

### 1. Quản lý nhân viên

- Xem danh sách nhân viên (B3)
- Thêm nhân viên mới — Full-time hoặc Part-time (B1)
- Cập nhật thông tin nhân viên (B2)

### 2. Quản lý chấm công

- Điểm danh hôm nay theo ID
- Ghi nhận chấm công theo ngày tùy chọn (B4)
- Xem lịch sử chấm công theo nhân viên (B5)

### 3. Quản lý lương

- Tính lương nhân viên theo tháng/năm (B6)
  - Lương cơ bản + ngày công + giờ overtime − ngày nghỉ

### 4. Báo cáo

- Nhân viên có số ngày vắng vượt ngưỡng (B7)
- Nhân viên được trả lương cao nhất trong tháng (B8)

---

## Cấu trúc project

```
Project_LAB/
├── data/
│   ├── employees.txt        # Dữ liệu nhân viên (pipe-delimited)
│   └── attendance.txt       # Dữ liệu chấm công (pipe-delimited)
├── src/main/java/
│   ├── entity/
│   │   ├── Employee.java
│   │   ├── FullTimeEmployee.java
│   │   ├── PartTimeEmployee.java
│   │   └── Attendance.java
│   ├── service/
│   │   ├── EmployeeService.java
│   │   ├── AttendanceService.java
│   │   ├── SalaryService.java
│   │   └── ReportService.java
│   ├── manager/
│   │   └── HRManager.java
│   ├── ui/
│   │   └── Main.java
│   └── utils/
│       └── Validation.java
├── pom.xml
└── README.md
```

---

## Công nghệ

| Thành phần     | Chi tiết                   |
| -------------- | -------------------------- |
| Ngôn ngữ       | Java 8+                    |
| Build tool     | Maven                      |
| Lưu trữ        | File text (pipe-delimited) |
| Định dạng ngày | dd/MM/yyyy                 |

---

## Cài đặt & chạy

```bash
# Build
mvn clean compile

# Chạy
mvn exec:java -Dexec.mainClass="ui.Main"
```

Hoặc chạy trực tiếp `Main.java` từ IDE (IntelliJ / NetBeans / VS Code).

Dữ liệu được tự động load từ `data/` khi khởi động và tự động lưu sau mỗi thay đổi.

---

## Format file dữ liệu

**employees.txt**

```
EM000001|Nguyen Van An|IT|Software Engineer|01/03/2023|12000000|ACTIVE|FULL_TIME
```

**attendance.txt**

```
EM000001|01/03/2026|PRESENT|1.5
```

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

**Phiên Bản**: 1.5.3
**Ngày Cập Nhật**: 11/03/2026
**Trạng Thái**: Đã Hoàn Thành
