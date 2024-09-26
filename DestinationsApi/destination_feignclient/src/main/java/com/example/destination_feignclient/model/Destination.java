package com.example.destination_feignclient.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Transient;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Destination {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private  String name;
    private String country;
    private String continent;

    @Transient
    private String description;
    @Transient
    private String image;
    @Transient
    private String population;
    @Transient
    private String currency;
    @Transient
    private String language;
    @Transient
    private String best_time_to_visit;
    @Transient
    private String [] top_attractions;
    @Transient
    private String [] local_dishes;
    @Transient
    private String [] activities;
}
