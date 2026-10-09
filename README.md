# Clinic Queue Management and Booking System

ระบบจัดการและจองคิวคลินิก เป็น Web Application สำหรับบริหารจัดการข้อมูลผู้ป่วย แพทย์ ตารางเวลา การนัดหมาย และคิวเข้ารับบริการของคลินิกอย่างมีประสิทธิภาพ ช่วยลดระยะเวลารอคิว เพิ่มความสะดวกในการทำงานของเจ้าหน้าที่ และเพิ่มความแม่นยำในการจัดสรรตารางตรวจของแพทย์

## สมาชิกกลุ่ม

| ลำดับ | ชื่อ-นามสกุล | รหัสนักศึกษา | Section | Branch | หน้าที่รับผิดชอบ |
|---|---|---|---|---|---|
| 3 | นางสาวพินิสา สุทธมาตย์ | 6733805973-3 | Sec.3 | `pinisa_6733805973_03` | Patient Management, Database Design และ Pricing Strategy Pattern |
| 1 | นายพชรดนัย สุดชาติ | 6733805931-1 | Sec.3 | `phacharadanai_6733805931_03` | Dentist & Appointment Management และ Observer Pattern |
| 2 | นายชาคริต รุ่งเรืองงาม | 673380997-3 | Sec.3 | `chakhit_6733803997_03` | Queue & Treatment Management, Template Method Pattern และ System Integration |

## System Architecture

ระบบใช้หลัก **Layered Architecture** เพื่อแยกหน้าที่ของแต่ละส่วนให้ชัดเจน (Separation of Concerns)

- **Presentation Layer (Controller):** รับ HTTP Request และส่ง HTTP Response ผ่าน REST API
- **Service Layer:** จัดการ Business Logic และเรียกใช้ Design Patterns ตามความเหมาะสม
- **Repository Layer:** จัดการเข้าถึงข้อมูลผ่าน Spring Data JPA
- **Domain / Entity Layer:** กำหนด Entity และความสัมพันธ์ระหว่างข้อมูล
- **DTO / Mapper Layer:** แยกข้อมูลที่รับจากผู้ใช้และข้อมูลที่ส่งกลับจาก Entity
- **Configuration Layer:** จัดการการตั้งค่าระบบและการเชื่อมต่อฐานข้อมูล

## Database Design (ER Diagram)

ฐานข้อมูลออกแบบให้รองรับข้อมูลหลักของคลินิกอย่างน้อย 6 ตาราง โดยความสัมพันธ์จริงให้ยึดตาม ER Diagram และ Entity ที่พัฒนาร่วมกัน

ตารางหลักที่เกี่ยวข้อง ได้แก่

- `Patient` — ข้อมูลผู้ป่วย
- `PatientProfile` — ข้อมูลรายละเอียดของผู้ป่วย
- `Dentist` — ข้อมูลทันตแพทย์
- `Appointment` — ข้อมูลการนัดหมาย
- `AppointmentQueue` — ข้อมูลคิวเข้ารับบริการ
- `TreatmentRecord` — ประวัติการรักษา

ความสัมพันธ์ที่ระบบต้องรองรับ ได้แก่

- `Patient` กับ `PatientProfile` — One-to-One ตามการออกแบบ
- `Dentist` กับ `Appointment` — One-to-Many
- `Patient` กับ `Appointment` — One-to-Many
- `Appointment` กับ `AppointmentQueue` — กำหนดตามกติกาการสร้างคิวของระบบ
- `Patient` กับ `TreatmentRecord` — One-to-Many
- `Dentist` กับ `TreatmentRecord` — เชื่อมโยงประวัติการรักษากับทันตแพทย์ผู้ให้บริการ

## File Ownership and Responsibilities

### Pinisa: Patient Management & Pricing Strategy

**Entity และ Repository**
- `domain/entity/Patient.java`
- `domain/entity/PatientProfile.java`
- `repository/PatientRepository.java`
- `repository/PatientProfileRepository.java`

**DTO และ Mapper**
- `dto/request/PatientRequest.java`
- `dto/response/PatientResponse.java`
- `mapper/PatientMapper.java`

**Service และ Controller**
- `service/PatientService.java`
- `service/impl/PatientServiceImpl.java`
- `controller/PatientController.java`

**Strategy Pattern**
- `pattern/strategy/PricingStrategy.java`
- `pattern/strategy/PricingContext.java`
- `pattern/strategy/DirectPayStrategy.java`
- `pattern/strategy/SocialSecurityStrategy.java`
- `pattern/strategy/CivilServantStrategy.java`

**Tests**
- `src/test/java/com/clinic/dental_clinic/service/PatientServiceImplTest.java`
- เพิ่ม unit tests สำหรับแต่ละ Pricing Strategy ตามเงื่อนไขที่กำหนด

### พชรดนัย: Dentist & Appointment Management / Observer Pattern

**Entity และ Repository**
- `domain/entity/Dentist.java`
- `domain/entity/Appointment.java`
- `repository/DentistRepository.java`
- `repository/AppointmentRepository.java`

**DTO และ Mapper**
- `dto/request/AppointmentRequest.java`
- `dto/response/AppointmentResponse.java`
- `mapper/AppointmentMapper.java`

**Service และ Controller**
- `service/AppointmentService.java`
- `service/impl/AppointmentServiceImpl.java`
- `controller/AppointmentController.java`

**Observer Pattern**
- `pattern/observer/QueueObserver.java`
- `pattern/observer/QueueSubject.java`
- `pattern/observer/SmsNotificationObserver.java`
- `pattern/observer/AuditLogObserver.java`

**Tests**
- `src/test/java/com/clinic/dental_clinic/service/AppointmentServiceImplTest.java`
- เพิ่ม tests สำหรับ Observer เพื่อทดสอบการแจ้งเตือนเมื่อสถานะคิวเปลี่ยนแปลง

### ชาคริต: Queue & Treatment Management / Template Method Pattern

**Entity และ Repository**
- `domain/entity/AppointmentQueue.java`
- `domain/entity/TreatmentRecord.java`
- `domain/enums/QueueStatus.java`
- `repository/AppointmentQueueRepository.java`
- `repository/TreatmentRecordRepository.java`

**DTO และ Mapper**
- `dto/request/QueueBookingRequest.java`
- `dto/response/QueueResponse.java`
- `dto/request/TreatmentRecordRequest.java`
- `dto/response/TreatmentRecordResponse.java`
- `mapper/AppointmentQueueMapper.java`
- เพิ่ม `mapper/TreatmentRecordMapper.java` หากจำเป็น

**Service และ Controller**
- `service/AppointmentQueueService.java`
- `service/impl/AppointmentQueueServiceImpl.java`
- `controller/AppointmentQueueController.java`
- เพิ่ม `service/TreatmentRecordService.java`
- เพิ่ม `service/impl/TreatmentRecordServiceImpl.java`
- เพิ่ม `controller/TreatmentRecordController.java` หากระบบต้องเปิด API สำหรับประวัติการรักษา

**Template Method Pattern**
- `pattern/template/NotificationTemplate.java`
- เพิ่มคลาสย่อยสำหรับรูปแบบการแจ้งเตือนแต่ละประเภท โดยกำหนดชื่อคลาสและวิธีทำงานร่วมกันให้ชัดเจน

**Tests**
- `src/test/java/com/clinic/dental_clinic/service/AppointmentQueueServiceImplTest.java`
- เพิ่ม tests สำหรับ Treatment Record และ Template Method ตามการทำงานที่พัฒนา

## Design Patterns

ระบบนำ GoF Design Patterns มาใช้เพื่อแสดงการประยุกต์ใช้รูปแบบการออกแบบซอฟต์แวร์

1. **Strategy Pattern:** เลือกวิธีคำนวณค่าบริการตามประเภทสิทธิ์การชำระเงิน เช่น จ่ายเอง ประกันสังคม และสิทธิข้าราชการ
2. **Observer Pattern:** แจ้งเตือนหรือบันทึก Log เมื่อสถานะคิวมีการเปลี่ยนแปลง

## Installation & Setup

### 1. Clone Repository

```bash
git clone https://github.com/pinisa/dental-clinic-management-system.git
cd dental-clinic-management-system
```

### 2. เข้าโฟลเดอร์ Spring Boot

```bash
cd code/dental-clinic
```

### 3. ตั้งค่าฐานข้อมูล

ตั้งค่า PostgreSQL ใน `src/main/resources/application.properties` โดยใช้ค่าที่เหมาะกับเครื่องของแต่ละคน หลีกเลี่ยงการ commit รหัสผ่านหรือ secret ลง GitHub

### 4. Run Tests

บน macOS หรือ Linux:

```bash
chmod +x mvnw
./mvnw test
```

### 5. Run Application

```bash
./mvnw spring-boot:run
```

เมื่อแอปเริ่มทำงานสำเร็จ สามารถเปิด `http://localhost:8080` ในเบราว์เซอร์ได้ ทั้งนี้หน้าแรกจะใช้งานได้ก็ต่อเมื่อมีการกำหนด route หรือหน้าเว็บไว้แล้ว

## API Documentation

เมื่อกำหนด OpenAPI/Swagger UI และเปิดใช้งานแล้ว สามารถตรวจสอบ API ผ่านหน้าเอกสารที่:

`http://localhost:8080/swagger-ui/index.html`

## Testing

ใช้ JUnit 5 และ Mockito สำหรับทดสอบการทำงานของ Service, Business Logic และ Design Patterns โดยครอบคลุมกรณีปกติและกรณีผิดพลาด เช่น ข้อมูลไม่ถูกต้อง ไม่พบข้อมูล และการจองเวลาซ้ำ

```bash
./mvnw test
```

