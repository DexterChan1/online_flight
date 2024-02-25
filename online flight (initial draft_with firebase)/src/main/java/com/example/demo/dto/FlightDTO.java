package com.example.demo.dto;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class FlightDTO {

    private Long flightId;
    private String flightNumber;
    private String aircraftType;
    private String airline;
    private Timestamp departureTime;
    private Timestamp arrivalTime;
    private Long departureAirportId;
    private String departureAirportName;
    private String departureAirportCity;
    private String departureAirportCountry;
    private Long arrivalAirportId;
    private String arrivalAirportName;
    private String arrivalAirportCity;
    private String arrivalAirportCountry;
    private Double economyClassFare;
    private Double businessClassFare;
    private Double firstClassFare;


    public FlightDTO(Long flightId, String flightNumber, String aircraftType, String airline,
                     Timestamp departureTime, Timestamp arrivalTime,
                     Long departureAirportId, String departureAirportName, String departureAirportCity, String departureAirportCountry,
                     Long arrivalAirportId, String arrivalAirportName, String arrivalAirportCity, String arrivalAirportCountry,
                     Double economyClassFare, Double businessClassFare, Double firstClassFare) {
        this.flightId = flightId;
        this.flightNumber = flightNumber;
        this.aircraftType = aircraftType;
        this.airline = airline;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.departureAirportId = departureAirportId;
        this.departureAirportName = departureAirportName;
        this.departureAirportCity = departureAirportCity;
        this.departureAirportCountry = departureAirportCountry;
        this.arrivalAirportId = arrivalAirportId;
        this.arrivalAirportName = arrivalAirportName;
        this.arrivalAirportCity = arrivalAirportCity;
        this.arrivalAirportCountry = arrivalAirportCountry;
        this.economyClassFare = economyClassFare;
        this.businessClassFare = businessClassFare;
        this.firstClassFare = firstClassFare;
    }
}
