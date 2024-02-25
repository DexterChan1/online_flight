package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "flights")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "flight_id")
    private Long flightId;

    @Column(name = "flight_number")
    private String flightNumber;

    @Column(name = "aircraft_type")
    private String aircraftType;

    @Column(name = "airline")
    private String airline;

    @Column(name = "departure_time")
    private Timestamp departureTime;

    @Column(name = "arrival_time")
    private Timestamp arrivalTime;

    @Column(name = "economy_class_fare")
    private Double economyClassFare;

    @Column(name = "business_class_fare")
    private Double businessClassFare;

    @Column(name = "first_class_fare")
    private Double firstClassFare;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "departure_airport_id", referencedColumnName = "airport_id")
    private Airport departureAirport;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "arrival_airport_id", referencedColumnName = "airport_id")
    private Airport arrivalAirport;

    // Getters and Setters

    public Long getFlightId() {
        return flightId;
    }

    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getAircraftType() {
        return aircraftType;
    }

    public void setAircraftType(String aircraftType) {
        this.aircraftType = aircraftType;
    }

    public Timestamp getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Timestamp departureTime) {
        this.departureTime = departureTime;
    }

    public Timestamp getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Timestamp arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Double getEconomyClassFare() {
        return economyClassFare;
    }

    public void setEconomyClassFare(Double economicClassFare) {
        this.economyClassFare = economyClassFare;
    }

    public Double getBusinessClassFare() {
        return businessClassFare;
    }

    public void setBusinessClassFare(Double businessClassFare) {
        this.businessClassFare = businessClassFare;
    }

    public Double getFirstClassFare() {
        return firstClassFare;
    }

    public void setFirstClassFare(Double firstClassFare) {
        this.firstClassFare = firstClassFare;
    }

    public Airport getDepartureAirport() {
        return departureAirport;
    }

    public void setDepartureAirport(Airport departureAirport) {
        this.departureAirport = departureAirport;
    }

    public Airport getArrivalAirport() {
        return arrivalAirport;
    }

    public void setArrivalAirport(Airport arrivalAirport) {
        this.arrivalAirport = arrivalAirport;
    }

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

}