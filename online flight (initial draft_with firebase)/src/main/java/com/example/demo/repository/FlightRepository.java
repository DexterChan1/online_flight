package com.example.demo.repository;

import com.example.demo.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {
    // Additional custom query methods can be defined here if needed
    @Query("SELECT f FROM Flight f WHERE f.departureAirport.city = :departureCity AND f.arrivalAirport.city = :arrivalCity")
    List<Flight> findByDepartureAirportCityAndArrivalAirportCity(String departureCity, String arrivalCity);}
