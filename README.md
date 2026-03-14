[![Java](https://img.shields.io/badge/Java-8+-orange?style=for-the-badge&logo=java)](https://java.com)
[![Maven](https://img.shields.io/badge/Maven-3.6+-blue?style=for-the-badge&logo=apache-maven)](https://maven.apache.org)
[![Status](https://img.shields.io/badge/Status-Completed-success?style=for-the-badge)]()
[![License](https://img.shields.io/badge/License-FPT%20University-green?style=for-the-badge)]()

---

# 📊 HR Management System

<p align="center">

**Human Resource Management System (HRMS)**

Console-based application for managing employees, attendance, salary and reports.

Built with **Java 8+** and **Maven**

**Version:** 1.6.3 | **PM:** Nguyễn Tiến Dũng

</p>

---

# 📑 Table of Contents

- [Overview](#overview)
- [Features](#features)
- [System Architecture](#system-architecture)
- [Project Structure](#project-structure)
- [Getting Started](#getting-started)
- [Data Storage](#data-storage)
- [Salary Calculation](#salary-calculation)
- [Troubleshooting](#troubleshooting)
- [Development Team](#development-team)
- [License](#license)

---

# 📝 Overview

The **HR Management System (HRMS)** is a **console-based Java application** designed to manage core HR operations.

The system supports:

- ✅ Employee management (Add, Update, View, Delete/Set INACTIVE)
- ✅ Attendance tracking (Mark today, Record, View history, Show all)
- ✅ Salary calculation (Based on working days, overtime, absences)
- ✅ HR reports (Low attendance, Highest paid)

All data is stored using **text files** (pipe-delimited format) and automatically **loaded and saved between sessions**.

---

# ✨ Features

|  Task   | Feature               | Description                                                         |
| :-----: | --------------------- | ------------------------------------------------------------------- |
| **B1**  | Add Employee          | Add new employee (Full-time or Part-time) with complete information |
| **B2**  | Update Employee       | Modify employee department and job title                            |
| **B2+** | Delete Employee       | Set employee status to INACTIVE (soft delete)                       |
| **B3**  | View Employees        | Display all employees in formatted table                            |
| **B4**  | Record Attendance     | Log attendance for a specific date with overtime hours              |
| **B5**  | View History          | View attendance records for an employee (sorted by date)            |
| **B6**  | Salary Calculation    | Calculate salary for a specific month, showing breakdown            |
| **B7**  | Low Attendance Report | Find employees exceeding absence threshold                          |
| **B8**  | Highest Paid Report   | Display highest-paid employees for a period                         |

---

# 🏗️ System Architecture

The system follows a **clean layered architecture** for separation of concerns:

```
┌─────────────────────────────────────┐
│       UI Layer (Main.java)          │
│   • Console interaction             │
│   • Menu display & input            │
└──────────────┬──────────────────────┘
               │
┌──────────────▼──────────────────────┐
│   Manager Layer (HRManager.java)    │
│   • Coordinate services             │
│   • File I/O orchestration          │
└──────────────┬──────────────────────┘
               │
┌──────────────▼──────────────────────┐
│   Service Layer                     │
│   • EmployeeService                 │
│   • AttendanceService               │
│   • SalaryService                   │
│   • ReportService                   │
└──────────────┬──────────────────────┘
               │
┌──────────────▼──────────────────────┐
│   Entity Layer                      │
│   • Employee (+ subtypes)           │
│   • Attendance                      │
└──────────────┬──────────────────────┘
               │
┌──────────────▼──────────────────────┐
│   Data Files (Text format)          │
│   • employees.txt                   │
│   • attendance.txt                  │
└─────────────────────────────────────┘
```

### Design Principles

- **Separation of Concerns** — Services contain logic, UI handles I/O only
- **Data Validation** — Input checked at service layer
- **Persistence** — Automatic file save after mutations
- **Date Standardization** — All dates in `dd/MM/yyyy` format

---

# 📁 Project Structure

```
Project_LAB/
│
├── data/
│   ├── employees.txt                 👥 Employee records
│   └── attendance.txt                📊 Attendance records
│
├── src/main/java/
│   │
│   ├── entity/
│   │   ├── Employee.java             (Abstract base class)
│   │   ├── FullTimeEmployee.java     (Concrete implementation)
│   │   ├── PartTimeEmployee.java     (Concrete implementation)
│   │   └── Attendance.java           (Attendance record)
│   │
│   ├── service/
│   │   ├── EmployeeService.java      (CRUD operations)
│   │   ├── AttendanceService.java    (Attendance logic)
│   │   ├── SalaryService.java        (Salary calculation)
│   │   └── ReportService.java        (Report generation)
│   │
│   ├── manager/
│   │   └── HRManager.java            (Coordinator + File I/O)
│   │
│   ├── ui/
│   │   └── Main.java                 (Console UI & menus)
│   │
│   └── utils/
│       └── Validation.java           (Input validation)
│
├── pom.xml                           (Maven configuration)
└── README.md                         (This file)
```

---

# 🚀 Getting Started

## Prerequisites

- **Java 8** or higher
- **Maven 3.6** or higher
- **Git** (for cloning)

## 1️⃣ Clone Repository

```bash
git clone <repository-url>
cd Project_LAB
```

## 2️⃣ Build Project

```bash
mvn clean compile
```

## 3️⃣ Run Application

```bash
mvn exec:java -Dexec.mainClass="ui.Main"
```

**Alternatively**, run `Main.java` directly from your IDE:

- IntelliJ IDEA
- NetBeans
- VS Code (with Java extension)

### Expected Output

```
================= MAIN MENU =================
        HUMAN RESOURCE MANAGEMENT
=============================================
1. Manage Employees
2. Attendance Management
3. Salary Management
4. Reports
0. Exit
---------------------------------------------
Choose: _
```

---

# 💾 Data Storage

The application persists data using **text files** in **pipe-delimited format** (`|` separator).

Files are located in the `data/` directory and automatically loaded on startup.

## employees.txt

**Format:** 8 fields per line

```
ID | Name | Department | JobTitle | HireDate | BaseSalary | Status | Type
```

**Example:**

```
EM000001|Nguyen Van An|IT|Software Engineer|01/03/2023|12000000|ACTIVE|FULL_TIME
EM000002|Tran Thi Hoa|HR|HR Officer|15/07/2022|10000000|ACTIVE|FULL_TIME
```

**Fields:**

- `ID` — Employee ID (unique)
- `Name` — Full name
- `Department` — Department name
- `JobTitle` — Job position
- `HireDate` — Hire date (dd/MM/yyyy)
- `BaseSalary` — Monthly base salary (VND)
- `Status` — ACTIVE or INACTIVE
- `Type` — FULL_TIME or PART_TIME

---

## attendance.txt

**Format:** 4 fields per line

```
EmployeeID | Date | Status | OvertimeHours
```

**Example:**

```
EM000001|01/03/2026|PRESENT|1.5
EM000001|02/03/2026|ABSENT|0
EM000002|01/03/2026|LEAVE|0
```

**Fields:**

- `EmployeeID` — Reference to employee ID
- `Date` — Attendance date (dd/MM/yyyy)
- `Status` — PRESENT, ABSENT, or LEAVE
- `OvertimeHours` — Hours of overtime (0 if not working)

---

# 💰 Salary Calculation

### Full-time Employee

```
Salary = BaseSalary / WorkingDaysPerMonth × ActualWorkingDays
       + (OvertimeHours × 1.5 × HourlyRate)
       - (AbsencePenalty × AbsenceDays)
```

### Part-time Employee

```
Salary = ActualWorkingDays × DailyRate
       + (OvertimeHours × 2 × HourlyRate)
```

**Business Rules (BR10):**

- Salary can only be calculated for **ACTIVE** employees
- Overtime calculated only on **PRESENT** days
- Absence days reduce salary accordingly

---

# 🐛 Troubleshooting

### Error: "Employee not found"

✅ Check if the employee ID exists  
✅ Verify the ID format (e.g., EM000001)

### Error: "Cannot create data directory"

✅ Ensure write permissions in project directory  
✅ Check disk space availability

### Error: "Date format invalid"

✅ Always use format: `dd/MM/yyyy` (e.g., 15/03/2026)

### Application doesn't start

**Check Java version:**

```bash
java -version
```

**Check Maven installation:**

```bash
mvn -version
```

**Rebuild project:**

```bash
mvn clean install
```

---

# 👥 Development Team

| Role             | Member               |
| ---------------- | -------------------- |
| **👨‍💼 PM**        | **Nguyễn Tiến Dũng** |
| **👨‍💻 Developer** | Trần Tuấn Đạt        |
| **👨‍💻 Developer** | Ngô Công Hoàng       |
| **👨‍💻 Developer** | Huỳnh Ngô Trà Giang  |

---

# 📄 License

This project was developed for the course **PRO192 — Software Engineering** at **FPT University**.

**Educational use only.**

---

<p align="center">

**PRO192 — Spring 2026**  
FPT University

Updated: 14/03/2026

</p>
