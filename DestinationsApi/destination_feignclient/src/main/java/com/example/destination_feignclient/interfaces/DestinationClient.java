package com.example.destination_feignclient.interfaces;

import com.example.destination_feignclient.model.Destination;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "destinationClient", url = "${feign.url}")
public interface DestinationClient {
    @GetMapping
    Destination [] getDestinationsByCountry(@RequestParam String country);
}
