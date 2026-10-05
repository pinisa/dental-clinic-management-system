# Clinic Queue Management and Booking System
ระบบจัดการและจองคิวคลินิก เป็น Web Application สำหรับช่วยบริหารจัดการข้อมูลผู้ป่วย ตารางเวลาแพทย์ การนัดหมาย และคิวการเข้ารับบริการของคลินิกอย่างมีประสิทธิภาพ พัฒนาขึ้นเพื่อช่วยลดระยะเวลาการรอคิวของผู้ป่วย เพิ่มความสะดวกในการทำงานของเจ้าหน้าที่ทางการแพทย์ และเพิ่มความแม่นยำในการจัดสรรตารางตรวจของแพทย์

## สมาชิกกลุ่ม
| ลำดับ | ชื่อ-นามสกุล | รหัสนักศึกษา | Section | Branch | หน้าที่รับผิดชอบ |
|---|---|---|---|---|---|
| 1 | นายชาคริต รุ่งเรืองงาม | 6733803997-3 | Sec.3 | `chakrit_6733803997_03` | User & Patient Management, Database Design & Cloud DB |
| 2 | นายพชรดนัย สุดชาติ | 6733805931-1 | Sec.3 | `phacharadanai_6733805931_03` | Doctor & Schedule Management, System Architecture & Docker |
| 3 | นางสาวพินิสา สุทธมาตย์ | 6733805973-3 | Sec.3 | `pinisa_6733805973_03` | Appointment & Queue Management, GoF Design Patterns, Deployment & CI/CD |

## Tech Stack
- **Backend Framework:** Spring Boot 3.x (Java 17+)
- **Database & ORM:** PostgreSQL / Spring Data JPA
- **API Documentation:** OpenAPI 3.0 / Swagger UI
- **Testing:** JUnit 5 + Mockito
- **Containerization:** Docker & Docker Compose
- **Deployment & CI/CD:** Render / Cloud Service + GitHub Actions

## System Architecture
ระบบได้รับการออกแบบตามหลัก **Layered Architecture** เพื่อแยกบทบาทความรับผิดชอบของซอฟต์แวร์อย่างชัดเจน (Separation of Concerns):
- **Presentation Layer (Controllers):** รับ-ส่ง HTTP Request/Response และจัดการ DTO
- **Service Layer (Business Logic):** จัดการเงื่อนไขทางธุรกิจ (Business Rules) และประยุกต์ใช้ GoF Design Patterns
- **Repository Layer (Spring Data JPA):** จัดการการเข้าถึงและคัดแยกข้อมูลจากฐานข้อมูล
- **Domain / Entity Layer:** นิยามโครงสร้างตารางและความสัมพันธ์ทางข้อมูล (Data Models)

![System Architecture](img/architecture.png)

## Database Design (ER Diagram)
ฐานข้อมูลได้รับการออกแบบให้มีโครงสร้างอย่างน้อย 6 ตาราง รองรับความสัมพันธ์แบบ One-to-One, One-to-Many และ Many-to-Many:
- `users` และ `patient_profiles` (One-to-One)
- `doctors` และ `schedules` (One-to-Many)
- `patients` และ `doctors` ผ่านตาราง `appointments` (Many-to-Many)

![ER Diagram](img/erd.png)

## Installation & Setup
1. **Clone Repository:**
   ```bash
   git clone [https://github.com/pinisa/dental-clinic-management-system.git](https://github.com/pinisa/dental-clinic-management-system.git)
   cd dental-clinic-management-system