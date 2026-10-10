### ชาคริต: Queue & Treatment Management / Template Method Pattern / Deployment and Database

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
- `mapper/TreatmentRecordMapper.java`

**Service และ Controller**
- `service/AppointmentQueueService.java`
- `service/impl/AppointmentQueueServiceImpl.java`
- `controller/AppointmentQueueController.java`
- `service/TreatmentRecordService.java`
- `service/impl/TreatmentRecordServiceImpl.java`
- `controller/TreatmentRecordController.java`

**Template Method Pattern**
- `pattern/template/NotificationTemplate.java`

**Tests**
- `src/test/java/com/clinic/dental_clinic/service/AppointmentQueueServiceImplTest.java` (9 Test Cases)
- `src/test/java/com/clinic/dental_clinic/service/TreatmentRecordServiceImplTest.java` (11 Test Cases)

**Deployment and Database**
- Deployment on Render
- Database on Neon
---
