package com.example.travel_booking_system_jpa.service;

import com.example.travel_booking_system_jpa.model.Customer;
import com.example.travel_booking_system_jpa.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class CustomerService {
    CustomerRepository customerRepository;

    @Value("${file.upload-dir}")
    String uploadDir;

    CustomerService(CustomerRepository customerRepository) {
        this.customerRepository=customerRepository;
    }

    public Customer addCustomer(String name, int age, String destination, LocalDate date, MultipartFile document) throws Exception {
        Customer customer=new Customer();
        customer.setName(name);
        customer.setAge(age);
        customer.setDestination(destination);
        customer.setDate(date);

        String fileName= UUID.randomUUID()+"_"+document.getOriginalFilename();
        Path filePath= Paths.get(uploadDir+fileName);
        document.transferTo(filePath);
        customer.setDocumentPath(filePath.toString());

        return customerRepository.save(customer);
    }

    public String databaseToCsv() {
        File file=new File("C:/Users/DELL/IdeaProjects/Travel Booking System/TravelBookingRecord.csv");
        List<Customer> customerList=customerRepository.findAll();

        try(FileWriter fw=new FileWriter(file)) {
            if(file.length()==0) {
                fw.append("ID, NAME, AGE, DESTINATION, DATE, DOCUMENT\n");
            }
            for(Customer customer:customerList) {
                fw.append(customer.getId()+","+customer.getName()+","+customer.getAge()+","+customer.getDestination()+","+customer.getDate()+","+customer.getDocumentPath()+"\n");
            }
        }
        catch (Exception e) {
            throw new RuntimeException("Database not exported to CSV");
        }
        return "Database exported to CSV";
    }

    public String csvToDatabase() {
        File file=new File("C:/Users/DELL/IdeaProjects/Travel Booking System/TravelBookingRecord.csv");
        try(BufferedReader br=new BufferedReader(new FileReader(file))) {
            String line;
            br.readLine();
            while((line=br.readLine())!=null) {
                String [] customerArray=line.split(",");
                Customer customer=new Customer();
                customer.setId(Integer.parseInt(customerArray[0]));
                customer.setName(customerArray[1]);
                customer.setAge(Integer.parseInt(customerArray[2]));
                customer.setDestination(customerArray[3]);
                customer.setDate(LocalDate.parse(customerArray[4]));
                customer.setDocumentPath(customerArray[5]);

                customerRepository.save(customer);
            }
        }
        catch (Exception e) {
            throw new RuntimeException("CSV not exported to database");
        }
        return "CSV exported to Database";
    }
}
