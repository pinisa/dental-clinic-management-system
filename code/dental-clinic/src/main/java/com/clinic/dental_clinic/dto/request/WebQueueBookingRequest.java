package com.clinic.dental_clinic.dto.request;

import com.clinic.dental_clinic.domain.enums.PatientType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Form model used by the customer-facing booking page.
 * It intentionally hides internal database IDs from the user.
 */
public class WebQueueBookingRequest {

    @NotBlank(message = "กรุณากรอกชื่อผู้รับบริการ")
    @Size(min = 2, max = 100, message = "ชื่อต้องมีความยาว 2-100 ตัวอักษร")
    private String patientName;

    @NotBlank(message = "กรุณากรอกเบอร์โทรศัพท์")
    @Pattern(regexp = "^[0-9]{9,10}$", message = "เบอร์โทรศัพท์ต้องเป็นตัวเลข 9-10 หลัก")
    private String phone;

    @Email(message = "รูปแบบอีเมลไม่ถูกต้อง")
    private String email;

    @NotNull(message = "กรุณาเลือกสิทธิการรักษา")
    private PatientType coverageType;

    @NotBlank(message = "กรุณากรอกเบอร์ติดต่อฉุกเฉิน")
    private String emergencyContact;

    @NotBlank(message = "กรุณาเลือกรายการรักษา")
    private String serviceType;

    @NotNull(message = "กรุณาเลือกรายการรักษา")
    @Min(value = 0, message = "ราคาต้องไม่ติดลบ")
    private Double basePrice;

    public WebQueueBookingRequest() {
    }

    public String getPatientName() { return patientName; }
    public void setPatientName(String patientName) { this.patientName = patientName; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public PatientType getCoverageType() { return coverageType; }
    public void setCoverageType(PatientType coverageType) { this.coverageType = coverageType; }

    public String getEmergencyContact() { return emergencyContact; }
    public void setEmergencyContact(String emergencyContact) { this.emergencyContact = emergencyContact; }

    public String getServiceType() { return serviceType; }
    public void setServiceType(String serviceType) { this.serviceType = serviceType; }

    public Double getBasePrice() { return basePrice; }
    public void setBasePrice(Double basePrice) { this.basePrice = basePrice; }
}
