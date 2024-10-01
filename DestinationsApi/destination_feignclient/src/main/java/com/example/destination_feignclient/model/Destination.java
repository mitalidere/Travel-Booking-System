package com.example.destination_feignclient.model;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("best_time_to_visit")
    private String bestTimeToVisit;

    @Transient
    @JsonProperty("top_attractions")
    private String [] topAttractions;

    @Transient
    @JsonProperty("local_dishes")
    private String [] localDishes;

    @Transient
    private String [] activities;
}
