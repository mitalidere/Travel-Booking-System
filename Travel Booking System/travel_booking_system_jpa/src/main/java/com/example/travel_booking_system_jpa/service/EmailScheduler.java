package com.example.travel_booking_system_jpa.scheduler;

import com.example.travel_booking_system_jpa.model.Customer;
import com.example.travel_booking_system_jpa.service.CustomerService;
import com.example.travel_booking_system_jpa.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class EmailScheduler {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private EmailService emailService;

    @Scheduled(cron = "0 0 10 * * ?")
    public void sendEmailReminders() {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        List<Customer> customers = customerService.filterCustomerByDate(tomorrow);

        for (Customer customer : customers) {
            String subject = "Travel Reminder";
            String body = String.format("Dear %s,%n%nThis is a reminder that you have travel planned to %s on %s.",
                    customer.getName(), customer.getDestination(), customer.getDate());
            emailService.sendEmail(customer.getEmail(), subject, body);
        }
    }
}
