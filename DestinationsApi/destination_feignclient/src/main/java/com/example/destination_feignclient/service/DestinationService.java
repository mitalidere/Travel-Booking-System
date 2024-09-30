package com.example.destination_feignclient.service;

import com.example.destination_feignclient.exception.RecordNotFoundException;
import com.example.destination_feignclient.interfaces.DestinationClient;
import com.example.destination_feignclient.model.Destination;
import com.example.destination_feignclient.repository.DestinationRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DestinationService {
    DestinationClient destinationClient;
    DestinationRepository destinationRepository;

    public DestinationService(DestinationClient destinationClient, DestinationRepository destinationRepository) {
        this.destinationClient = destinationClient;
        this.destinationRepository=destinationRepository;
    }

    public Destination[] getDestinationsForCountry(String country) {
        Destination [] destinations=destinationClient.getDestinationsByCountry(country);
        List<Destination> destinationList= Arrays.stream(destinations).filter(destination -> destination.getCountry().equalsIgnoreCase(country)).toList();
        if(!destinationList.isEmpty()) {
            return destinationList.toArray(new Destination[0]);
        }
        throw new RecordNotFoundException("Record not found");
    }

    public String saveToDatabase() {
        Destination[] destinations = destinationClient.getDestinationsByCountry("");
        for (Destination destination : destinations) {
            destinationRepository.save(destination);
        }
        return "Saved to database";
    }
}
