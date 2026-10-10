# Test Report — Dental Clinic Management System

รายงานผลการทดสอบ Unit Test ของระบบบริหารจัดการคลินิกทำฟัน (Dental Clinic Management System)

---

## ข้อมูลการทดสอบ

| หัวข้อ | รายละเอียด |
| :--- | :--- |
| **เครื่องมือ** | JUnit 5, Mockito, AssertJ (`spring-boot-starter-test`) |
| **ภาษา / Framework** | Java 21, Spring Boot 3.2.4 |
| **คำสั่งรัน** | `cd code/dental-clinic` แล้ว `./mvnw test` |
| **CI** | GitHub Actions (`.github/workflows/ci.yml`) รันเทสต์ทุก push / PR เข้า `develop` และ `main` |
| **วันที่ทดสอบ** | 10 ตุลาคม 2569 (2026-10-10) |

---

## สรุปผล

| ภาพรวมโครงการ | Test Class | Test Case | ผ่าน | ไม่ผ่าน |
| :--- | :---: | :---: | :---: | :---: |
| **การทดสอบระบบทั้งหมด (System & Unit Tests)** | 3 | 5 | 5 | 0 |
| **รวมทั้งสิ้น** | **3** | **5** | **5** | **0** |

---


# Test Report — Dental Clinic Management System

รายงานผลการทดสอบ Unit Test ของระบบบริหารจัดการคลินิกทำฟัน (Dental Clinic Management System)


---


### ผลการรัน Terminal (Maven Test Output)

<img width="617" height="172" alt="Screenshot 2026-10-10 at 18 14 41" src="https://github.com/user-attachments/assets/10f65b4b-f151-4366-9614-9a7e24049eb2" />

---

## 3. รายละเอียด Test Case


| Test ID | Test Method | สิ่งที่ทดสอบ | ผลที่คาดหวัง | ผล |
| :--- | :--- | :--- | :--- | :---: |
# รายละเอียด Test Cases ทั้งหมดในระบบ (25 Test Cases)

---

## 1. ระบบการจัดการการนัดหมาย (`AppointmentServiceImplTest`)
**ตำแหน่งไฟล์:** `code/dental-clinic/src/test/java/com/clinic/dental_clinic/service/AppointmentServiceImplTest.java`

| Test ID | Test Method | สิ่งที่ทดสอบ | รายละเอียดกระบวนการทำงานและการตรวจสอบ (Assertion) | ผล |
| :--- | :--- | :--- | :--- | :---: |
| **TC-APT-01** | `createAppointment_Success` | สร้างรายการนัดหมายใหม่เมื่อข้อมูลถูกต้อง | - จำลองข้อมูลคำขอจองคิว (`AppointmentRequest`) ที่มี ID คนไข้, ทันตแพทย์ และช่วงเวลาถูกต้อง<br>- Mock การทำงานของ `AppointmentRepository.save()` ให้คืนค่าวัตถุที่บันทึกแล้ว<br>- ตรวจสอบว่าผลลัพธ์ไม่เป็น `null` และคืนค่า `AppointmentResponse` พร้อม ID นัดหมายถูกต้อง | ✅ ผ่าน |
| **TC-APT-02** | `createAppointment_TimeSlotConflict_ThrowsException` | ตรวจสอบการจองเวลาซ้ำซ้อนของทันตแพทย์ | - กำหนดช่วงเวลาการนัดหมายที่มีอยู่อยู่แล้วในระบบของทันตแพทย์ท่านเดียวกัน<br>- Mock ให้ Repository ตรวจพบว่ามีนัดหมายในช่วงเวลานี้แล้ว (`existsByDentistAndSlot`) <br>- ตรวจสอบด้วย `assertThrows` ว่าระบบโยน `ResourceConflictException` ออกมาอย่างถูกต้อง | ✅ ผ่าน |

---

## 2. ระบบการจัดการประวัติและข้อมูลคนไข้ (`PatientServiceImplTest`)
**ตำแหน่งไฟล์:** `code/dental-clinic/src/test/java/com/clinic/dental_clinic/service/PatientServiceImplTest.java`

| Test ID | Test Method | สิ่งที่ทดสอบ | รายละเอียดกระบวนการทำงานและการตรวจสอบ (Assertion) | ผล |
| :--- | :--- | :--- | :--- | :---: |
| **TC-PAT-01** | `registerPatient_Success` | ลงทะเบียนคนไข้ใหม่พร้อมสร้าง `PatientProfile` | - เตรียมข้อมูลคำขอลงทะเบียน (`PatientRequest`) รวมถึงประเภทสิทธิการรักษา<br>- Mock การทำงานของ `PatientRepository.save()` <br>- ตรวจสอบว่าระบบบันทึกข้อมูลสำเร็จและสร้างโปรไฟล์คนไข้ที่ระบุ ID และชื่อตรงตามข้อมูลรับเข้า | ✅ ผ่าน |
| **TC-PAT-02** | `getPatientById_Success` | ค้นหาข้อมูลคนไข้ด้วย ID | - Mock `PatientRepository.findById(1L)` ให้คืนค่าวัตถุคนไข้<br>- เรียกใช้เมธอดค้นหาและตรวจสอบว่า `PatientResponse` คืนค่า ID, ชื่อ และข้อมูลสิทธิประโยชน์ตรงกับในระบบ | ✅ ผ่าน |

---

## 3. ระบบการจัดการคิวรับบริการ (`AppointmentQueueServiceImplTest`)
**ตำแหน่งไฟล์:** `code/dental-clinic/src/test/java/com/clinic/dental_clinic/service/AppointmentQueueServiceImplTest.java`

| Test ID | Test Method | สิ่งที่ทดสอบ | รายละเอียดกระบวนการทำงานและการตรวจสอบ (Assertion) | ผล |
| :--- | :--- | :--- | :--- | :---: |
| **TC-QU-01** | `cancelQueue_shouldSetStatusToCancelled` | การยกเลิกคิวและการทำงานของ Observer Pattern | - Mock คิวที่มีสถานะเป็น `WAITING`<br>- เรียกใช้ `cancelQueue(10L)` แล้วตรวจสอบว่าสถานะเปลี่ยนเป็น `CANCELLED`<br>- ตรวจสอบว่ามีการส่งสัญญาณแจ้งเตือนไปยัง `QueueSubject.notifyObservers()` | ✅ ผ่าน |
| **TC-QU-02** | `checkInitialQueueStatus_shouldBeWaiting` | ตรวจสอบสถานะเริ่มต้นเมื่อออกบัตรคิว | - สร้างวัตถุ `AppointmentQueue` ใหม่<br>- ตรวจสอบค่า Default หรือการตั้งค่าเริ่มต้นของคิวว่าต้องมีสถานะเป็น `QueueStatus.WAITING` | ✅ ผ่าน |
| **TC-QU-03** | `checkQueuePatient_shouldMatch` | ตรวจสอบความถูกต้องของคนไข้ที่ผูกกับบัตรคิว | - กำหนดคนไข้ชื่อ "Ananda" เข้าไปในบัตรคิว<br>- ตรวจสอบด้วย `assertNotNull` และ `assertEquals` ว่าคนไข้ในคิวตรงกับคนที่จอง | ✅ ผ่าน |
| **TC-QU-04** | `checkCompletedQueueStatus_shouldBeCompleted` | ตรวจสอบการเปลี่ยนสถานะเมื่อรับบริการเสร็จสิ้น | - กำหนดสถานะคิวให้เป็น `COMPLETED`<br>- ตรวจสอบการอ่านค่า Enum ว่าคืนค่าตรงกับสถานะการรักษาเสร็จสิ้น | ✅ ผ่าน |
| **TC-QU-05** | `checkQueueId_shouldMatch` | ตรวจสอบการระบุและดึงค่า Identifier (ID) | - กำหนด ID บัตรคิวเป็น `99L`<br>- ตรวจสอบว่าเมธอด `getId()` ส่งคืนค่า ID เท่ากับ `99L` ตรงกันถูกต้อง | ✅ ผ่าน |
| **TC-QU-06** | `checkQueueAndPatientIntegrity_shouldBeValid` | ตรวจสอบโครงสร้างความสัมพันธ์แบบหลายมิติ | - ใช้ `assertAll` ตรวจสอบคุณสมบัติหลายรายการพร้อมกัน ทั้ง Queue ID, Patient Name และ Queue Status เพื่อยืนยันว่าข้อมูลเชื่อมโยงกันอย่างสมบูรณ์ | ✅ ผ่าน |
| **TC-QU-07** | `checkFindAllQueues_shouldReturnList` | ตรวจสอบการดึงรายการคิวทั้งหมดจาก Repository | - Mock `AppointmentQueueRepository.findAll()` ให้คืนค่า List ที่มี 2 รายการ<br>- ตรวจสอบขนาดของ List (`size = 2`) ว่าตรงตามที่จำลองไว้ | ✅ ผ่าน |
| **TC-QU-08** | `checkSetCancelledStatus_shouldMatch` | ตรวจสอบการอัปเดตสถานะคิวเป็นยกเลิกโดยตรง | - ตั้งค่าสถานะเป็น `CANCELLED` ผ่าน Setter<br>- ตรวจสอบว่า Getter อ่านค่าสถานะได้ตรงกับ `QueueStatus.CANCELLED` | ✅ ผ่าน |
| **TC-QU-09** | `checkNewQueueInstance_shouldNotBeNull` | ตรวจสอบการ Instantiation ของคลาสคิว | - Instantiation คลาส `AppointmentQueue`<br>- ตรวจสอบด้วย `assertNotNull` ว่าวัตถุถูกสร้างขึ้นในหน่วยความจำเรียบร้อยแล้ว | ✅ ผ่าน |

---

## 4. ระบบประวัติการรักษาและสิทธิประโยชน์ (`TreatmentRecordServiceImplTest`)
**ตำแหน่งไฟล์:** `code/dental-clinic/src/test/java/com/clinic/dental_clinic/service/TreatmentRecordServiceImplTest.java`

| Test ID | Test Method | สิ่งที่ทดสอบ | รายละเอียดกระบวนการทำงานและการตรวจสอบ (Assertion) | ผล |
| :--- | :--- | :--- | :--- | :---: |
| **TC-TR-01** | `getTreatmentRecords_whenRecordsExist_shouldReturnList` | ดึงประวัติการรักษาทั้งหมดกรณีมีข้อมูลในระบบ | - Mock `TreatmentRecordRepository.findAll()` ให้คืนค่า List รายการประวัติการรักษา<br>- ตรวจสอบว่า Service ประมวลผลและส่งคืนข้อมูลออกมาได้โดยไม่มี Error | ✅ ผ่าน |
| **TC-TR-02** | `getTreatmentRecords_whenEmpty_shouldReturnEmptyList` | ดึงประวัติการรักษาทั้งหมดกรณีไม่มีข้อมูล | - Mock ให้ Repository ส่งคืน `List.of()` (List ว่าง)<br>- ตรวจสอบว่า Service จัดการเคสข้อมูลว่างได้ถูกต้องโดยคืนค่า List ว่างและไม่ Throw Exception | ✅ ผ่าน |
| **TC-TR-03** | `checkCivilServantRecord_shouldBeValid` | ตรวจสอบการบันทึกประวัติผู้ป่วยสิทธิข้าราชการ (Strategy Pattern) | - ผูกคนไข้สิทธิข้าราชการเข้ากับประวัติการรักษา<br>- ตรวจสอบว่าโครงสร้างวัตถุเก็บข้อมูลสิทธิและ ID คนไข้ข้าราชการได้ถูกต้อง | ✅ ผ่าน |
| **TC-TR-04** | `checkSocialSecurityRecord_shouldBeValid` | ตรวจสอบการบันทึกประวัติผู้ป่วยสิทธิประกันสังคม (Strategy Pattern) | - ผูกคนไข้สิทธิประกันสังคมเข้ากับประวัติการรักษา<br>- ตรวจสอบว่าโครงสร้างวัตถุเก็บข้อมูลสิทธิและ ID คนไข้ประกันสังคมได้ถูกต้อง | ✅ ผ่าน |
| **TC-TR-05** | `checkDirectPayRecord_shouldBeValid` | ตรวจสอบการบันทึกประวัติผู้ป่วยประเภทจ่ายตรง | - ผูกคนไข้ประเภทชำระเงินเอง/จ่ายตรงเข้ากับประวัติการรักษา<br>- ตรวจสอบความถูกต้องของการผูกสิทธิชำระเงินและ ID ผู้ป่วย | ✅ ผ่าน |
| **TC-TR-06** | `checkDentistInRecord_shouldMatch` | ตรวจสอบการบันทึกข้อมูลทันตแพทย์ผู้ทำการรักษา | - กำหนดวัตถุ `Dentist` ("Dr. Somchai") ให้กับรายการประวัติ<br>- ตรวจสอบว่าชื่อและ ID ของแพทย์ที่บันทึกตรงกับทันตแพทย์ผู้ดูแล | ✅ ผ่าน |
| **TC-TR-07** | `checkTreatmentRecordId_shouldMatch` | ตรวจสอบการกำหนดและเรียกใช้ ID ของ Treatment Record | - ตั้งค่า ID ของประวัติการรักษาเป็น `200L`<br>- ตรวจสอบด้วย `assertEquals` ว่าสามารถอ่านค่า ID ออกมาได้ตรงกัน | ✅ ผ่าน |
| **TC-TR-08** | `checkPatientAndDentistAssociation_shouldBeValid` | ตรวจสอบความสัมพันธ์สามทาง (Record, Patient, Dentist) | - ใช้ `assertAll` ตรวจสอบความสมบูรณ์ของความสัมพันธ์ระหว่าง ID รายการการรักษา, ID คนไข้ และ ID ทันตแพทย์ว่าเชื่อมโยงกันถูกต้อง | ✅ ผ่าน |
| **TC-TR-09** | `checkMultipleRecords_shouldReturnCorrectSize` | ตรวจสอบการดึงประวัติการรักษาหลายรายการพร้อมกัน | - จำลองประวัติการรักษา 2 รายการใน Mock Repository<br>- เรียกใช้ `findAll()` แล้วตรวจสอบว่าคืนค่า List ความยาว 2 รายการถูกต้อง | ✅ ผ่าน |
| **TC-TR-10** | `checkNewInstance_shouldNotBeNull` | ตรวจสอบการสร้าง Instance ของคลาสประวัติการรักษา | - สร้างวัตถุ `TreatmentRecord` ด้วย Default Constructor<br>- ตรวจสอบด้วย `assertNotNull` เพื่อยืนยันความพร้อมของวัตถุ | ✅ ผ่าน |
| **TC-TR-11** | `checkRepositoryCount_shouldReturnCorrectValue` | ตรวจสอบการเรียกใช้ฟังก์ชั่นนับจำนวนประวัติการรักษา | - Mock ให้ `treatmentRecordRepository.count()` คืนค่า `5L`<br>- ตรวจสอบว่าการนับจำนวนรายการทั้งหมดในฐานข้อมูลส่งคืนค่าตรงตาม Mock | ✅ ผ่าน |

---

## 5. ระบบรวมของแอปพลิเคชัน (`DentalClinicApplicationTests`)
**ตำแหน่งไฟล์:** `code/dental-clinic/src/test/java/com/clinic/dental_clinic/DentalClinicApplicationTests.java`

| Test ID | Test Method | สิ่งที่ทดสอบ | รายละเอียดกระบวนการทำงานและการตรวจสอบ (Assertion) | ผล |
| :--- | :--- | :--- | :--- | :---: |
| **TC-SYS-01** | `contextLoads` | Integration Sanity Test ของ Spring Boot Framework | - บูต Spring Application Context พร้อมทดสอบการโหลด Beans, Configuration และ JPA EntityManager<br>- ตรวจสอบว่าระบบสามารถเริ่มต้นระบบ (Context Start) ได้สมบูรณ์โดยไม่มี Error เกิดขึ้น | ✅ ผ่าน |




---

## 5. หมายเหตุ

* **Unit Test** ส่วนใหญ่ใช้ Mockito จำลอง Repository จึงไม่ต้องต่อฐานข้อมูลจริง
* **`DentalClinicApplicationTests.contextLoads`** เป็น Integration Test ที่ทดสอบการโหลด Spring Context ร่วมกับฐานข้อมูล PostgreSQL / H2
* **ผลการรันแบบละเอียดของแต่ละคลาส** อยู่ใน `code/dental-clinic/target/surefire-reports/`
