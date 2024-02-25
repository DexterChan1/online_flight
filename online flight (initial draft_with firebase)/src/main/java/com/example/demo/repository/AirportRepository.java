package com.example.demo.repository;

import com.example.demo.model.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AirportRepository extends JpaRepository<Airport, Long> {
    // Custom query methods (if needed) can be defined here
    List<Airport> findByCity(String city);
}
