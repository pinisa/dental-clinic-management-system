### พินิสา: Dentist & Appointment Management / Observer Pattern

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
- `src/test/java/com/clinic/dental_clinic/service/AppointmentServiceImplTest.java` (2 Test Cases)
- `src/test/java/com/clinic/dental_clinic/DentalClinicApplicationTests.java` (1 Integration Sanity Test Case)
