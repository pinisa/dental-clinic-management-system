# Design Patterns

โปรเจกต์ Dental Clinic Management System ใช้ Design Patterns เพื่อแบ่งความรับผิดชอบของระบบให้ชัดเจน ลดการเชื่อมโยงระหว่างส่วนต่าง ๆ และช่วยให้สามารถแก้ไขหรือเพิ่มเติมฟังก์ชันในอนาคตได้ง่ายขึ้น โดยแบ่งเป็น 2 กลุ่ม ได้แก่ Enterprise / Architectural Patterns และ GoF Patterns

## Enterprise / Architectural Patterns

| Pattern | ปัญหาที่แก้ | ไฟล์/คลาสที่ใช้ |
|---|---|---|---|
| Layered Architecture | ป้องกันการรวมส่วนแสดงผล การประมวลผลทางธุรกิจ และการเข้าถึงฐานข้อมูลไว้ในส่วนเดียวกัน ทำให้ดูแลและแก้ไขระบบได้ง่ายขึ้น | `controller/`, `service/`, `repository/`, `domain/entity/`, `dto/`|
| MVC (Model–View–Controller) | แยกการรับคำขอและควบคุมการทำงานออกจากข้อมูลและหน้าจอ ช่วยให้การจัดการหน้าเว็บเป็นระบบมากขึ้น | `controller/web/AdminController.java`, Model/Entity classes และ View templates |
| Repository Pattern | ลดการเขียนคำสั่งเข้าถึงฐานข้อมูลซ้ำในส่วน Business Logic และแยกการจัดการข้อมูลออกจาก Service | `AppointmentQueueRepository.java`, `PatientRepository.java`, `DentistRepository.java`|
| Service Layer Pattern | รวม Business Logic ไว้ใน Service เพื่อให้ Controller เรียกใช้งานผ่านเมธอดที่กำหนด แทนการจัดการขั้นตอนทางธุรกิจทั้งหมดใน Controller | `AppointmentQueueService.java`, `AppointmentQueueServiceImpl.java` และ Service classes อื่น ๆ|
| DTO Pattern + Mapper | แยกข้อมูลที่รับเข้าหรือส่งออกผ่าน API ออกจาก Entity ที่ใช้แทนข้อมูลในฐานข้อมูล ทำให้กำหนดรูปแบบข้อมูลที่เปิดเผยต่อผู้เรียกใช้ได้ชัดเจน | `dto/request/QueueBookingRequest.java`, `dto/response/QueueResponse.java`, `dto/mapper/AppointmentQueueMapper.java`|
| Dependency Injection (Constructor Injection) | ลดการสร้าง Dependency ด้วยตนเองภายในคลาส ทำให้ส่วนต่าง ๆ เชื่อมต่อกันอย่างยืดหยุ่นและช่วยให้ทดสอบได้ง่ายขึ้น | Constructor ของ `AppointmentQueueServiceImpl.java`, `AdminController.java`, `PricingContext.java` และ `QueueSubject.java`|


## GoF Design Patterns

### 1. Strategy Pattern

| Pattern | ปัญหาที่แก้ | ไฟล์/คลาสที่ใช้ | Class Diagram |
|---|---|---|---|
| Strategy | วิธีคำนวณค่ารักษาแตกต่างกันตามประเภทผู้ป่วย หากเขียนเงื่อนไขทั้งหมดไว้ใน Service จะทำให้โค้ดซับซ้อน การแยก Strategy ช่วยให้เพิ่มหรือปรับวิธีคำนวณราคาได้ง่ายขึ้น | `pattern/strategy/PricingStrategy.java`, `DirectPayStrategy.java`, `CivilServantStrategy.java`, `SocialSecurityStrategy.java`, `PricingContext.java` | Diagram 7 |

**เหตุผลที่เลือกใช้:** ระบบมีวิธีคำนวณราคาตามประเภทผู้ป่วยที่แตกต่างกัน จึงแยกแต่ละวิธีเป็น Strategy ของตนเอง โดย `PricingContext` เลือก Strategy ที่เหมาะสมและนำไปคำนวณราคา


### 2. Observer Pattern

| Pattern | ปัญหาที่แก้ | ไฟล์/คลาสที่ใช้ | Class Diagram |
|---|---|---|---|
| Observer | เมื่อสถานะคิวเปลี่ยน ระบบต้องแจ้งผู้สังเกตการณ์ที่ลงทะเบียนไว้ การแยก Subject และ Observer ช่วยให้เพิ่มผู้รับการแจ้งเตือนได้โดยไม่ต้องรวมการทำงานทั้งหมดไว้ใน Service | `pattern/observer/QueueObserver.java`, `QueueSubject.java`, `service/impl/AppointmentQueueServiceImpl.java` และคลาสที่ implement `QueueObserver` | Diagram 8 |

