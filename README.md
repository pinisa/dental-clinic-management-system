### พชรดนัย: Patient Management & Pricing Strategy

**Entity และ Repository**
- domain/entity/Patient.java
- domain/entity/PatientProfile.java
- repository/PatientRepository.java
- repository/PatientProfileRepository.java

**DTO และ Mapper**
- dto/request/PatientRequest.java
- dto/response/PatientResponse.java
- mapper/PatientMapper.java

**Service และ Controller**
- service/PatientService.java
- service/impl/PatientServiceImpl.java
- controller/PatientController.java

**Strategy Pattern**
- pattern/strategy/PricingStrategy.java
- pattern/strategy/PricingContext.java
- pattern/strategy/DirectPayStrategy.java
- pattern/strategy/SocialSecurityStrategy.java
- pattern/strategy/CivilServantStrategy.java

**Tests**
- src/test/java/com/clinic/dental_clinic/service/PatientServiceImplTest.java (2 Test Cases)
