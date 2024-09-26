package com.example.destination_feignclient.controller;

import com.example.destination_feignclient.model.Destination;
import com.example.destination_feignclient.service.DestinationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/destination")
public class DestinationController {
    DestinationService destinationService;

    public DestinationController(DestinationService destinationService) {
        this.destinationService = destinationService;
    }

    @GetMapping("/filter")
    public Destination[] getDestinationsByCountry(@RequestParam String country) {
        return destinationService.getDestinationsForCountry(country);
    }

    @GetMapping("/saveToDatabase")
    public String saveToDatabase() {
        return destinationService.saveToDatabase();
    }
}
