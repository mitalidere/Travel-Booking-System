package com.example.destination_feignclient.service;

import com.example.destination_feignclient.interfaces.DestinationClient;
import com.example.destination_feignclient.model.Destination;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DestinationService {
    DestinationClient destinationClient;

    public DestinationService(DestinationClient destinationClient) {
        this.destinationClient = destinationClient;
    }

    public Destination[] getDestinationsForCountry(String country) {
        Destination [] destinations=destinationClient.getDestinationsByCountry(country);
        List<Destination> destinationList=Arrays.stream(destinations).filter(destination -> destination.getCountry().equalsIgnoreCase(country)).collect(Collectors.toList());
        return destinationList.toArray(new Destination[0]);
    }
}
