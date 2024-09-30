package com.example.travel_booking_system_jpa.service;

import com.example.travel_booking_system_jpa.exception.FileConversionException;
import com.example.travel_booking_system_jpa.exception.RecordNotFoundException;
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
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

@Service
public class CustomerService {
    CustomerRepository customerRepository;

    @Value("${file.upload-dir}")
    String uploadDir;

    CustomerService(CustomerRepository customerRepository) {
        this.customerRepository=customerRepository;
    }

    public List<Customer> allCustomers() {
        return customerRepository.findAll();
    }

    public Customer addCustomer(String name, int age, String destination, LocalDate date, MultipartFile document) throws IOException {
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

    public Customer updateCustomer(int id, String name, int age, String destination, LocalDate date, MultipartFile document) throws IOException {
        if(customerRepository.findById(id).isPresent()) {
            Customer customer = new Customer();
            customer.setId(id);
            customer.setName(name);
            customer.setAge(age);
            customer.setDestination(destination);
            customer.setDate(date);

            String fileName = UUID.randomUUID() + "_" + document.getOriginalFilename();
            Path filePath = Paths.get(uploadDir + fileName);
            document.transferTo(filePath);
            customer.setDocumentPath(filePath.toString());

            return customerRepository.save(customer);
        }
        throw new RecordNotFoundException("Customer not found with id "+id);
    }

    public String deleteCustomer(int id) {
        if(customerRepository.findById(id).isPresent()) {
            customerRepository.deleteById(id);
            return "Customer deleted";
        }
        throw new RecordNotFoundException("Customer not found with id "+id);
    }

    public String databaseToCsv(String fileName) {
        File file=new File("C:/Users/DELL/IdeaProjects/Assessment/Travel Booking System/"+fileName+".csv");
        List<Customer> customerList=customerRepository.findAll();

        try(FileWriter fw=new FileWriter(file, true)) {
            if(file.length()==0) {
                fw.append("ID, NAME, AGE, DESTINATION, DATE, DOCUMENT\n");
            }
            for(Customer customer:customerList) {
                fw.append(String.valueOf(customer.getId())).append(",").append(customer.getName()).append(",").append(String.valueOf(customer.getAge())).append(",").append(customer.getDestination()).append(",").append(String.valueOf(customer.getDate())).append(",").append(customer.getDocumentPath()).append("\n");
            }
        }
        catch (Exception e) {
            throw new FileConversionException("Database not exported to CSV");
        }
        return "Database exported to CSV";
    }

    public String csvToDatabase(String fileName) {
        File file=new File("C:/Users/DELL/IdeaProjects/Assessment/Travel Booking System/"+fileName+".csv");
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
            throw new FileConversionException("CSV not exported to database");
        }
        return "CSV exported to Database";
    }

    public List<Customer> filterCustomerByDate(LocalDate date) {
        List<Customer> customers = allCustomers();

        Function<LocalDate, List<Customer>> filterByDate = filterDate ->
                customers.stream()
                        .filter(customer -> Optional.ofNullable(customer.getDate())
                                .map(travelDate -> travelDate.equals(filterDate))
                                .orElse(false))
                        .toList();

        return Optional.ofNullable(date)
                .map(filterByDate)
                .filter(filteredCustomers -> !filteredCustomers.isEmpty())
                .orElseThrow(() -> new RecordNotFoundException("No customers found for date "+date));
    }


    public List<Customer> filterCustomerByDestination(String destination) {
        List<Customer> customers = allCustomers();

        Function<String, List<Customer>> filterByDestination = filterDest ->
                customers.stream()
                        .filter(customer -> Optional.ofNullable(customer.getDestination())
                                .map(dest -> dest.equalsIgnoreCase(filterDest))
                                .orElse(false))
                        .toList();

        return Optional.ofNullable(destination)
                .map(filterByDestination)
                .filter(filteredCustomers -> !filteredCustomers.isEmpty())
                .orElseThrow(() -> new RecordNotFoundException("No customers found for destination "+destination));
    }

    public List<Customer> filterCustomerByName(String name) {
        List<Customer> customers = allCustomers();

        Function<String, List<Customer>> filterByName = filterName ->
                customers.stream()
                        .filter(customer -> Optional.ofNullable(customer.getName())
                                .map(customerName -> customerName.equalsIgnoreCase(filterName))
                                .orElse(false))
                        .toList();

        return Optional.ofNullable(name)
                .map(filterByName)
                .filter(filteredCustomers -> !filteredCustomers.isEmpty())
                .orElseThrow(() -> new RecordNotFoundException("No customers found for name "+name));
    }
}
