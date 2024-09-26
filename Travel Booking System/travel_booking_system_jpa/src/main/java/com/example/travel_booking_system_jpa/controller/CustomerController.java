package com.example.travel_booking_system_jpa.controller;

import com.example.travel_booking_system_jpa.model.Customer;
import com.example.travel_booking_system_jpa.service.CustomerService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public Customer addCustomer(@RequestParam String name,
                                @RequestParam int age,
                                @RequestParam String destination,
                                @RequestParam LocalDate date,
                                @RequestParam MultipartFile document) throws Exception {
        return customerService.addCustomer(name, age, destination, date, document);
    }

    @GetMapping("/databaseToCsv")
    public String databaseToCSV() {
        return customerService.databaseToCsv();
    }

    @GetMapping("/csvToDatabase")
    public String csvToDatabase() {
        return customerService.csvToDatabase();
    }
}
