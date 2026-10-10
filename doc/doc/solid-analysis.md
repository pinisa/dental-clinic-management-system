# วิเคราะห์หลักการ SOLID ในโปรเจกต์

## 1. Single Responsibility Principle (SRP)

**ไฟล์:** `code/dental-clinic/src/main/java/com/clinic/dental_clinic/service/impl/AppointmentQueueServiceImpl.java`  
**บรรทัด:** 47–70, 101–151

**เหตุผล:** คลาสนี้ทำงานเกี่ยวกับคิวหลายอย่าง ทั้งสร้างคิว ดูข้อมูลคิว อัปเดตสถานะ ยกเลิกคิว และลบคิว ทำให้มีหน้าที่ จึงแยกบางส่วนออกเป็นคลาสย่อย ๆ จะช่วยให้แก้ไขและดูแลโค้ดได้ง่ายขึ้น

## 2. Open/Closed Principle (OCP)

**ไฟล์:** `code/dental-clinic/src/main/java/com/clinic/dental_clinic/pattern/strategy/PricingStrategy.java`  
**บรรทัด:** 3–5

**ไฟล์ที่เกี่ยวข้อง:**
- `code/dental-clinic/src/main/java/com/clinic/dental_clinic/pattern/strategy/DirectPayStrategy.java` บรรทัด 5–10
- `code/dental-clinic/src/main/java/com/clinic/dental_clinic/pattern/strategy/CivilServantStrategy.java` บรรทัด 5–10
- `code/dental-clinic/src/main/java/com/clinic/dental_clinic/pattern/strategy/SocialSecurityStrategy.java` บรรทัด 5–10

**เหตุผล:** โค้ดใช้ Strategy Pattern แยกวิธีคำนวณราคาของผู้ใช้แต่ละประเภทออกจากกัน ถ้าต้องเพิ่มวิธีคำนวณราคาแบบใหม่ ก็สามารถสร้างคลาสเพิ่มได้โดยไม่ต้องไปแก้โค้ดคำนวณของคลาสเดิมทุกคลาส

## 3. Liskov Substitution Principle (LSP)

**ไฟล์:** `code/dental-clinic/src/main/java/com/clinic/dental_clinic/pattern/strategy/PricingStrategy.java`  
**บรรทัด:** 3–5

**ไฟล์ที่เกี่ยวข้อง:**
- `code/dental-clinic/src/main/java/com/clinic/dental_clinic/pattern/strategy/DirectPayStrategy.java` บรรทัด 5–10
- `code/dental-clinic/src/main/java/com/clinic/dental_clinic/pattern/strategy/CivilServantStrategy.java` บรรทัด 5–10
- `code/dental-clinic/src/main/java/com/clinic/dental_clinic/pattern/strategy/SocialSecurityStrategy.java` บรรทัด 5–10

**เหตุผล:** คลาสคำนวณราคาแต่ละแบบใช้ interface เดียวกัน ทำให้เรียกใช้งานผ่าน `PricingStrategy` ได้เหมือนกัน โดยแต่ละคลาสจะคำนวณราคาตามเงื่อนไขของตัวเอง ทั้งนี้แต่ละคลาสต้องทำงานตามข้อตกลงเดียวกันด้วย

## 4. Interface Segregation Principle (ISP)

**ไฟล์:** `code/dental-clinic/src/main/java/com/clinic/dental_clinic/service/AppointmentQueueService.java`  
**บรรทัด:** 8–16

**เหตุผล:** มีการแยกคำสั่งที่เกี่ยวกับการจัดการคิวไว้ใน interface โดยเฉพาะ ทำให้เห็นได้ชัดว่าระบบจัดการคิวมีฟังก์ชันอะไรบ้าง แต่ต้องตรวจสอบการนำไปใช้เพิ่มเติม จึงจะสรุปได้ชัดเจนว่าควรแยก interface อีกหรือไม่

## 5. Dependency Inversion Principle (DIP)

**ไฟล์:** `code/dental-clinic/src/main/java/com/clinic/dental_clinic/service/impl/AppointmentQueueServiceImpl.java`  
**บรรทัด:** 29–45

**ไฟล์ที่เกี่ยวข้อง:** `code/dental-clinic/src/main/java/com/clinic/dental_clinic/controller/web/AdminController.java`  
**บรรทัด:** 36–60, 115–121

**เหตุผล:** ระบบมีการส่ง dependencies ผ่าน constructor ซึ่งช่วยให้จัดการส่วนที่ต้องใช้ร่วมกันได้เป็นระเบียบมากขึ้น แต่ใน `AdminController` ยังมีการเรียก `appointmentRepository` โดยตรงตอนยกเลิกนัดหมาย ทำให้ Controller ต้องจัดการเรื่องเข้าถึงฐานข้อมูลเองด้วย ถ้าย้ายส่วนนี้ไปไว้ใน Service จะช่วยแยกหน้าที่ของแต่ละส่วนได้ชัดเจนขึ้น

## สรุป

จากโค้ดที่ตรวจสอบ พบว่าระบบมีการใช้ Strategy Pattern, interface และ constructor injection ซึ่งช่วยให้โค้ดแบ่งหน้าที่และนำกลับมาใช้ได้สะดวกขึ้น ซึ่งยังมีบางส่วนที่สามารถปรับปรุงได้ เช่น การแยกหน้าที่ใน `AppointmentQueueServiceImpl` และการไม่ให้ `AdminController` เรียก Repository โดยตรง
