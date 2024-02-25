package com.example.demo.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class AirportDTO {

    private Long airportId;
    private String airportName;
    private String iata_Code;
    private String city;
    private String country;
    // Constructors, getters, setters...

    @Builder
    public AirportDTO(Long id, String name, String iataCode, String city, String country) {
        this.airportId = id;
        this.airportName = name;
        this.iata_Code = iataCode;
        this.city = city;
        this.country = country;
    }
}

