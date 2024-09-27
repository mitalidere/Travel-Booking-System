package com.example.travel_booking_system_jpa.controller;

import com.example.travel_booking_system_jpa.model.Customer;
import com.example.travel_booking_system_jpa.service.CustomerService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public List<Customer> allCustomers() {
        return customerService.allCustomers();
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
    public String databaseToCSV(@RequestParam String fileName) {
        return customerService.databaseToCsv(fileName);
    }

    @GetMapping("/csvToDatabase")
    public String csvToDatabase(@RequestParam String fileName) {
        return customerService.csvToDatabase(fileName);
    }

    @GetMapping("/filterByDate")
    public List<Customer> filterCustomerByDate(@RequestParam LocalDate date) {
        return customerService.filterCustomerByDate(date);
    }

    @GetMapping("/filterByDestination")
    public List<Customer> filterCustomerByDestination(@RequestParam String destination) {
        return customerService.filterCustomerByDestination(destination);
    }

    @GetMapping("/filterByName")
    public List<Customer> filterCustomerByName(@RequestParam String name) {
        return customerService.filterCustomerByName(name);
    }
}
