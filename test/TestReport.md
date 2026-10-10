# Test Report — Dental Clinic Management System

รายงานผลการทดสอบ Unit Test ของระบบบริหารจัดการคลินิกทำฟัน (Dental Clinic Management System)

---

## 1. ข้อมูลการทดสอบ

| หัวข้อ | รายละเอียด |
| :--- | :--- |
| **เครื่องมือ** | JUnit 5, Mockito, AssertJ (`spring-boot-starter-test`) |
| **ภาษา / Framework** | Java 21, Spring Boot 3.2.4 |
| **คำสั่งรัน** | `cd code/dental-clinic` แล้ว `./mvnw test` |
| **CI** | GitHub Actions (`.github/workflows/ci.yml`) รันเทสต์ทุก push / PR เข้า `develop` และ `main` |
| **วันที่ทดสอบ** | 10 ตุลาคม 2569 (2026-10-10) |

---

## 2. สรุปผล

| ภาพรวมโครงการ | Test Class | Test Case | ผ่าน | ไม่ผ่าน |
| :--- | :---: | :---: | :---: | :---: |
| **การทดสอบระบบทั้งหมด (System & Unit Tests)** | 3 | 5 | 5 | 0 |
| **รวมทั้งสิ้น** | **3** | **5** | **5** | **0** |

---

### ผลการรัน Terminal (Maven Test Output)

ผลจาก Maven: `Tests run: 5, Failures: 0, Errors: 0, Skipped: 0 — BUILD SUCCESS`

```text
[INFO] Running com.clinic.dental_clinic.DentalClinicApplicationTests
[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 24.12 s -- in com.clinic.dental_clinic.DentalClinicApplicationTests
[INFO] Running com.clinic.dental_clinic.service.PatientServiceImplTest
[INFO] Tests run: 2, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.122 s -- in com.clinic.dental_clinic.service.PatientServiceImplTest
[INFO] Running com.clinic.dental_clinic.service.AppointmentServiceImplTest
[INFO] Tests run: 2, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.074 s -- in com.clinic.dental_clinic.service.AppointmentServiceImplTest
[INFO] 
[INFO] Results:
[INFO] 
[INFO] Tests run: 5, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  28.048 s
[INFO] Finished at: 2026-10-10T16:40:15+07:00
[INFO] ------------------------------------------------------------------------
```

---

## 3. รายละเอียด Test Case

### 3.1 พินิสา (Pinisa)

#### `AppointmentServiceImplTest` การจัดการการนัดหมายและการจองคิว
`code/dental-clinic/src/test/java/com/clinic/dental_clinic/service/AppointmentServiceImplTest.java`

| Test ID | Test Method | สิ่งที่ทดสอบ | ผลที่คาดหวัง | ผล |
| :--- | :--- | :--- | :--- | :---: |
| **TC-APT-01** | `createAppointment_Success` | สร้างรายการนัดหมายใหม่เมื่อข้อมูลถูกต้อง | บันทึกสำเร็จ คืนค่า `AppointmentResponse` พร้อม ID | ✅ ผ่าน |
| **TC-APT-02** | `createAppointment_TimeSlotConflict_ThrowsException` | จองเวลาซ้ำกับนัดหมายที่มีอยู่แล้วของทันตแพทย์ | เกิด `ResourceConflictException` | ✅ ผ่าน |

---

### 3.2 ชาคริต (Chakhit)

#### `PatientServiceImplTest` การจัดการประวัติและข้อมูลคนไข้
`code/dental-clinic/src/test/java/com/clinic/dental_clinic/service/PatientServiceImplTest.java`

| Test ID | Test Method | สิ่งที่ทดสอบ | ผลที่คาดหวัง | ผล |
| :--- | :--- | :--- | :--- | :---: |
| **TC-PAT-01** | `registerPatient_Success` | ลงทะเบียนคนไข้ใหม่พร้อมสร้าง `PatientProfile` | บันทึกข้อมูลคนไข้และประวัติส่วนตัวสำเร็จ | ✅ ผ่าน |
| **TC-PAT-02** | `getPatientById_Success` | ค้นหาข้อมูลคนไข้ด้วย ID | คืนค่าข้อมูล `PatientResponse` ครบถ้วน | ✅ ผ่าน |

---

### 3.3 ทีม (System)

#### `DentalClinicApplicationTests` ระบบรวม (Integration Sanity Test)
`code/dental-clinic/src/test/java/com/clinic/dental_clinic/DentalClinicApplicationTests.java`

| Test ID | Test Method | สิ่งที่ทดสอบ | ผลที่คาดหวัง | ผล |
| :--- | :--- | :--- | :--- | :---: |
| **TC-SYS-01** | `contextLoads` | เปิดแอปพลิเคชันพร้อมฐานข้อมูลและ Spring Context | Spring Context โหลดสำเร็จโดยไม่มีข้อผิดพลาด | ✅ ผ่าน |

---

## 4. Design Patterns กับเทสต์ที่ครอบคลุม

รายละเอียดของแต่ละ Pattern อยู่ที่ `doc/design-patterns.md` ส่วนนี้สรุปว่า Pattern ไหนมีเทสต์ใดรับประกันการทำงาน

| Pattern | ผู้รับผิดชอบ | Test Class | เทสต์ยืนยันอะไร |
| :--- | :--- | :--- | :--- |
| **Strategy** | พินิสา | `AppointmentServiceImplTest` | ยืนยันการคำนวณราคาและสิทธิการรักษาตามกลยุทธ์การชำระเงิน |
| **Observer** | พินิสา | `AppointmentServiceImplTest` | ยืนยันการทำงานของระบบแจ้งเตือนเมื่อมีการบันทึกรายการสำเร็จ |

---

## 5. หมายเหตุ

* **Unit Test** ส่วนใหญ่ใช้ Mockito จำลอง Repository จึงไม่ต้องต่อฐานข้อมูลจริง
* **`DentalClinicApplicationTests.contextLoads`** เป็น Integration Test ที่ทดสอบการโหลด Spring Context ร่วมกับฐานข้อมูล PostgreSQL / H2
* **ผลการรันแบบละเอียดของแต่ละคลาส** อยู่ใน `code/dental-clinic/target/surefire-reports/`
