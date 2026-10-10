# Test Report — Dental Clinic Management System

รายงานผลการทดสอบ Unit Test ของระบบบริหารจัดการคลินิกทำฟัน (Dental Clinic Management System)

---

## 1. ข้อมูลการทดสอบ

| หัวข้อ | รายละเอียด |
| :--- | :--- |
| **เครื่องมือ** | JUnit 5, Mockito, AssertJ (`spring-boot-starter-test`) |
| **ภาษา / Framework** | Java 17, Spring Boot 3.2.4 |
| **คำสั่งรัน** | `cd code/dental-clinic` แล้ว `./mvnw test` |
| **CI** | GitHub Actions (`.github/workflows/ci.yml`) รันเทสต์ทุก push / PR เข้า `develop` และ `main` |
| **วันที่ทดสอบ** | 10 ตุลาคม 2569 (2026-10-10) |

---

## 2. สรุปผล

| สมาชิก | Test Class | Test Case | ผ่าน | ไม่ผ่าน |
| :--- | :---: | :---: | :---: | :---: |
| **พินิสา (Pinisa)** | 4 | 18 | 18 | 0 |
| **ชาคริต (Chakhit)** | 3 | 12 | 12 | 0 |
| **พชรดนัย (Phacharadanai)** | 3 | 10 | 10 | 0 |
| **ทีม (System)** | 1 | 1 | 1 | 0 |
| **รวม** | **11** | **41** | **41** | **0** |

---

### ผลการรัน Terminal (Maven Test Output)

ผลจาก Maven: `Tests run: 41, Failures: 0, Errors: 0, Skipped: 0 — BUILD SUCCESS`

```text
[INFO] Results:
[INFO] 
[INFO] Tests run: 41, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  18.520 s
[INFO] Finished at: 2026-10-10T16:30:00+07:00
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
| **TC-APT-02** | `createAppointment_PatientNotFound_ThrowsException` | สร้างนัดหมายแต่ไม่พบ ID ผู้ป่วยในระบบ | เกิด `ResourceNotFoundException` | ✅ ผ่าน |
| **TC-APT-03** | `createAppointment_DentistNotFound_ThrowsException` | สร้างนัดหมายแต่ไม่พบ ID ทันตแพทย์ | เกิด `ResourceNotFoundException` | ✅ ผ่าน |
| **TC-APT-04** | `createAppointment_TimeSlotConflict_ThrowsException` | จองเวลาซ้ำกับนัดหมายที่มีอยู่แล้วของทันตแพทย์ | เกิด `ResourceConflictException` | ✅ ผ่าน |
| **TC-APT-05** | `getAppointmentById_Success` | ดึงข้อมูลนัดหมายด้วย ID ที่มีอยู่จริง | คืนค่าข้อมูลนัดหมายถูกต้อง | ✅ ผ่าน |
| **TC-APT-06** | `getAppointmentById_NotFound` | ดึงข้อมูลนัดหมายด้วย ID ที่ไม่มีในระบบ | เกิด `ResourceNotFoundException` | ✅ ผ่าน |
| **TC-APT-07** | `cancelAppointment_Success` | ยกเลิกรายการนัดหมาย | เปลี่ยนสถานะเป็น `CANCELLED` สำเร็จ | ✅ ผ่าน |
| **TC-APT-08** | `getAppointmentsByPatient_Success` | ดึงรายการนัดหมายทั้งหมดของคนไข้เฉพาะราย | คืนค่า List ของนัดหมายตรงตาม ID คนไข้ | ✅ ผ่าน |

#### `PricingStrategyTest` การคำนวณค่ารักษาตามสิทธิการรักษา (Strategy Pattern)
`code/dental-clinic/src/test/java/com/clinic/dental_clinic/pattern/strategy/PricingStrategyTest.java`

| Test ID | Test Method | สิ่งที่ทดสอบ | ผลที่คาดหวัง | ผล |
| :--- | :--- | :--- | :--- | :---: |
| **TC-STR-01** | `directPayStrategy_CalculatesFullPrice` | คำนวณราคาสำหรับชำระเงินสด/จ่ายตรง (`DIRECT_PAY`) | จ่ายเต็มจำนวน 100% ไม่ได้รับส่วนลด | ✅ ผ่าน |
| **TC-STR-02** | `socialSecurityStrategy_AppliesDeduction` | คำนวณราคาสำหรับสิทธิประกันสังคม (`SOCIAL_SECURITY`) | หักส่วนลดเบิกประกันสังคมตามเกณฑ์กำหนด (สูงสุด 900 บาท) | ✅ ผ่าน |
| **TC-STR-03** | `civilServantStrategy_AppliesFullReimbursement` | คำนวณราคาสำหรับสิทธิข้าราชการ (`CIVIL_SERVANT`) | เบิกได้ตามสิทธิสวัสดิการข้าราชการยอดสุทธิถูกต้อง | ✅ ผ่าน |
| **TC-STR-04** | `pricingContext_SelectsCorrectStrategy` | Context เลือก Strategy ตามประเภท `PatientType` | เรียกใช้ Strategy ตรงตามประเภทสิทธิของคนไข้ | ✅ ผ่าน |

#### `AppointmentQueueServiceImplTest` การจัดการคิวเข้ารับการรักษา
`code/dental-clinic/src/test/java/com/clinic/dental_clinic/service/AppointmentQueueServiceImplTest.java`

| Test ID | Test Method | สิ่งที่ทดสอบ | ผลที่คาดหวัง | ผล |
| :--- | :--- | :--- | :--- | :---: |
| **TC-QU-01** | `bookQueue_Success` | กดจองคิวเข้ารับบริการประจำวัน | สร้างคิวใหม่ สถานะเป็น `WAITING` พร้อมเลขคิวลำดับถัดไป | ✅ ผ่าน |
| **TC-QU-02** | `callNextQueue_Success` | เรียกคิวถัดไปเข้ารับการรักษา | คิวถัดไปเปลี่ยนสถานะเป็น `IN_PROGRESS` | ✅ ผ่าน |
| **TC-QU-03** | `completeQueue_Success` | ทำการรักษาเสร็จสิ้น ปิดคิว | เปลี่ยนสถานะเป็น `COMPLETED` | ✅ ผ่าน |
| **TC-QU-04** | `cancelQueue_Success` | ข้าม/ยกเลิกคิว | เปลี่ยนสถานะเป็น `CANCELLED` | ✅ ผ่าน |

#### `QueueObserverTest` ระบบแจ้งเตือนและการบันทึกประวัติคิว (Observer Pattern)
`code/dental-clinic/src/test/java/com/clinic/dental_clinic/pattern/observer/QueueObserverTest.java`

| Test ID | Test Method | สิ่งที่ทดสอบ | ผลที่คาดหวัง | ผล |
| :--- | :--- | :--- | :--- | :---: |
| **TC-OBS-01** | `notifyObservers_SendsSmsNotification` | สถานะคิวเปลี่ยน แล้วระบบส่ง SMS แจ้งเตือน | `SmsNotificationObserver` ทำงานและส่งข้อความถูกต้อง | ✅ ผ่าน |
| **TC-OBS-02** | `notifyObservers_LogsAuditTrail` | สถานะคิวเปลี่ยน แล้วบันทึก Audit Log | `AuditLogObserver` บันทึกประวัติการเปลี่ยนสถานะลงระบบ | ✅ ผ่าน |

---

### 3.2 ชาคริต (Chakhit)

#### `PatientServiceImplTest` การจัดการประวัติและข้อมูลคนไข้
`code/dental-clinic/src/test/java/com/clinic/dental_clinic/service/PatientServiceImplTest.java`

| Test ID | Test Method | สิ่งที่ทดสอบ | ผลที่คาดหวัง | ผล |
| :--- | :--- | :--- | :--- | :---: |
| **TC-PAT-01** | `registerPatient_Success` | ลงทะเบียนคนไข้ใหม่พร้อมสร้าง `PatientProfile` | บันทึกข้อมูลคนไข้และประวัติส่วนตัวสำเร็จ | ✅ ผ่าน |
| **TC-PAT-02** | `registerPatient_DuplicateIdCard_ThrowsException` | ลงทะเบียนด้วยเลขบัตรประชาชนที่ซ้ำในระบบ | เกิด `ResourceConflictException` | ✅ ผ่าน |
| **TC-PAT-03** | `getPatientById_Success` | ค้นหาข้อมูลคนไข้ด้วย ID | คืนค่าข้อมูล `PatientResponse` ครบถ้วน | ✅ ผ่าน |
| **TC-PAT-04** | `getPatientById_NotFound` | ค้นหาคนไข้ด้วย ID ที่ไม่มีอยู่จริง | เกิด `ResourceNotFoundException` | ✅ ผ่าน |
| **TC-PAT-05** | `updatePatient_Success` | แก้ไขข้อมูลส่วนตัวและสิทธิการรักษาของคนไข้ | ข้อมูลถูกอัปเดตตรงตามที่แก้ไข | ✅ ผ่าน |
| **TC-PAT-06** | `searchPatientsByName_ReturnsMatchedList` | ค้นหาคนไข้ด้วยชื่อหรือนามสกุล | คืนค่ารายการคนไข้ที่ชื่อตรงตามคำค้นหา | ✅ ผ่าน |

#### `PatientWebControllerTest` ส่วนแสดงผลหน้าเว็บจัดการคนไข้
`code/dental-clinic/src/test/java/com/clinic/dental_clinic/controller/web/PatientWebControllerTest.java`

| Test ID | Test Method | สิ่งที่ทดสอบ | ผลที่คาดหวัง | ผล |
| :--- | :--- | :--- | :--- | :---: |
| **TC-PAT-07** | `listPatients_ReturnsPatientView` | เรียกหน้าเว็บรายชื่อคนไข้ (`/patients`) | ส่งกลับวิว `patients/list` พร้อม Model Data | ✅ ผ่าน |
| **TC-PAT-08** | `showCreateForm_ReturnsFormView` | เรียกหน้าฟอร์มเพิ่มคนไข้ (`/patients/new`) | ส่งกลับวิว `patients/form` พร้อมวัตถุฟอร์มเปล่า | ✅ ผ่าน |
| **TC-PAT-09** | `savePatient_RedirectsToList` | ส่งฟอร์มบันทึกคนไข้ | บันทึกสำเร็จและ Redirect ไปยัง `/patients` | ✅ ผ่าน |

#### `PatientMapperTest` การแปลงข้อมูล Entity เป็น DTO
`code/dental-clinic/src/test/java/com/clinic/dental_clinic/dto/mapper/PatientMapperTest.java`

| Test ID | Test Method | สิ่งที่ทดสอบ | ผลที่คาดหวัง | ผล |
| :--- | :--- | :--- | :--- | :---: |
| **TC-PAT-10** | `toEntity_CopiesAllFields` | แปลง `PatientRequest` เป็น `Patient` Entity | คัดลอกข้อมูลตรงกันทุกฟิลด์ | ✅ ผ่าน |
| **TC-PAT-11** | `toResponse_CopiesAllFields` | แปลง `Patient` Entity เป็น `PatientResponse` | คัดลอกข้อมูลและข้อมูล Profile ครบถ้วน | ✅ ผ่าน |
| **TC-PAT-12** | `toResponse_NullProfile_HandlesGracefully` | แปลง Entity ที่ไม่มี Profile | ส่งกลับ DTO โดยไม่เกิด `NullPointerException` | ✅ ผ่าน |

---

### 3.3 พชรดนัย (Phacharadanai)

#### `TreatmentRecordServiceImplTest` การจัดการประวัติการรักษาและใบเสร็จ
`code/dental-clinic/src/test/java/com/clinic/dental_clinic/service/TreatmentRecordServiceImplTest.java`

| Test ID | Test Method | สิ่งที่ทดสอบ | ผลที่คาดหวัง | ผล |
| :--- | :--- | :--- | :--- | :---: |
| **TC-TRT-01** | `createRecord_Success` | บันทึกประวัติการรักษาของคนไข้ | บันทึกรายการรักษาและคำนวณราคาสุทธิสำเร็จ | ✅ ผ่าน |
| **TC-TRT-02** | `createRecord_PatientNotFound_ThrowsException` | บันทึกประวัติการรักษาแต่ไม่พบ ID คนไข้ | เกิด `ResourceNotFoundException` | ✅ ผ่าน |
| **TC-TRT-03** | `getRecordsByPatientId_ReturnsHistory` | เรียกดูประวัติการรักษาย้อนหลังของคนไข้ | คืนค่ารายการประวัติการรักษาทั้งหมดของคนไข้รายนั้น | ✅ ผ่าน |
| **TC-TRT-04** | `getRecordById_Success` | ค้นหาใบบันทึกการรักษาตาม ID | คืนค่ารายละเอียดการรักษาถูกต้อง | ✅ ผ่าน |

#### `AdminControllerTest` ระบบควบคุมส่วนงานผู้ดูแลระบบ (Admin)
`code/dental-clinic/src/test/java/com/clinic/dental_clinic/controller/web/AdminControllerTest.java`

| Test ID | Test Method | สิ่งที่ทดสอบ | ผลที่คาดหวัง | ผล |
| :--- | :--- | :--- | :--- | :---: |
| **TC-ADM-01** | `adminDashboard_ReturnsAppointmentsView` | เรียกดูหน้ารายการนัดหมายทั้งหมดสำหรับแอดมิน | ส่งกลับวิว `admin/appointments` | ✅ ผ่าน |
| **TC-ADM-02** | `manageDentists_ReturnsDentistList` | เรียกดูหน้ารายชื่อแพทย์ในระบบ | ส่งกลับวิว `admin/dentists` พร้อมข้อมูลแพทย์ | ✅ ผ่าน |
| **TC-ADM-03** | `manageTreatments_ReturnsTreatmentRecords` | เรียกดูหน้ารายการบันทึกการรักษาทั้งหมด | ส่งกลับวิว `admin/treatments` | ✅ ผ่าน |

#### `QueueWebControllerTest` หน้าจอลงทะเบียนคิวออนไลน์สำหรับประชาชน
`code/dental-clinic/src/test/java/com/clinic/dental_clinic/controller/web/QueueWebControllerTest.java`

| Test ID | Test Method | สิ่งที่ทดสอบ | ผลที่คาดหวัง | ผล |
| :--- | :--- | :--- | :--- | :---: |
| **TC-QWC-01** | `showQueueForm_ReturnsFormView` | เปิดหน้าจองคิวออนไลน์ | ส่งกลับวิว `queues/form` | ✅ ผ่าน |
| **TC-QWC-02** | `submitQueueBooking_Success_RedirectsToSuccess` | กรอกข้อมูลจองคิวสำเร็จ | สร้างคิวและ Redirect ไปยังหน้า `queues/success` | ✅ ผ่าน |
| **TC-QWC-03** | `getQueueStatus_ReturnsQueueList` | เรียกดูหน้าบอร์ดแสดงสถานะคิวปัจจุบัน | ส่งกลับวิว `queues/list` แสดงคิวที่กำลังรอคิว | ✅ ผ่าน |

---

### 3.4 ทีม (System)

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
| **Strategy** | พินิสา | `PricingStrategyTest`, `TreatmentRecordServiceImplTest` | คำนวณราคาสุทธิและหักส่วนลดตามประเภทสิทธิการรักษา (`DIRECT_PAY`, `SOCIAL_SECURITY`, `CIVIL_SERVANT`) ได้ถูกต้อง (`TC-STR-01` ถึง `04`) |
| **Observer** | พินิสา | `QueueObserverTest`, `AppointmentQueueServiceImplTest` | เมื่อสถานะคิวเปลี่ยน ระบบจะส่งข้อความแจ้งเตือนผ่าน SMS (`SmsNotificationObserver`) และบันทึกประวัติกิจกรรม (`AuditLogObserver`) โดยอัตโนมัติ (`TC-OBS-01`, `02`) |

---

## 5. หมายเหตุ

* **Unit Test** ส่วนใหญ่ใช้ Mockito จำลอง Repository จึงไม่ต้องต่อฐานข้อมูลจริง
* **`DentalClinicApplicationTests.contextLoads`** เป็น Integration Test ที่ทดสอบกับฐานข้อมูล H2 (In-Memory) สำหรับการทดสอบ หรือ PostgreSQL บน Cloud
* **ผลการรับแบบละเอียดของแต่ละคลาส** อยู่ใน `code/dental-clinic/target/surefire-reports/` และดาวน์โหลดได้จากหน้า GitHub Actions (artifact `test-reports`)
