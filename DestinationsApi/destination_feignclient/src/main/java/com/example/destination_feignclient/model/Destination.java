package com.example.destination_feignclient.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Destination {
    private int id;
    private  String name;
    private String country;
    private String continent;
    private String description;
    private String image;
    private String population;
    private String currency;
    private String language;
    private String best_time_to_visit;
    private String [] top_attractions;
    private String [] local_dishes;
    private String [] activities;
}
