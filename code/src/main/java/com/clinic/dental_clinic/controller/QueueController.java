package com.clinic.dental_clinic.controller;

import com.clinic.dental_clinic.service.QueueService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/queues")
public class QueueController {

    private final QueueService queueService;

    public QueueController(QueueService queueService) {
        this.queueService = queueService;
    }

    // หน้าแสดงรายการคิวทั้งหมด
    @GetMapping
    public String listQueues(Model model) {
        model.addAttribute("queues", queueService.getAllQueues());
        return "queue-list";
    }

    // หน้าฟอร์มสำหรับลงทะเบียนจองคิว
    @GetMapping("/new")
    public String showBookingForm() {
        return "queue-form";
    }

    // รับข้อมูลจากฟอร์มเพื่อบันทึกจองคิว
    @PostMapping("/book")
    public String bookAppointment(@RequestParam String name,
                                  @RequestParam String phone,
                                  @RequestParam String coverageType,
                                  @RequestParam String serviceType) {
        queueService.bookAppointment(name, phone, coverageType, serviceType);
        return "redirect:/queues";
    }

    // ปุ่มกดเลื่อนสถานะคิว (WAITING -> IN_TREATMENT -> COMPLETED)
    @PostMapping("/{id}/advance")
    public String advanceStatus(@PathVariable Long id) {
        queueService.advanceQueueState(id);
        return "redirect:/queues";
    }
}