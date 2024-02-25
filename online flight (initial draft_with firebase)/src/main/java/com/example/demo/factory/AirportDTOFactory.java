package com.example.demo.factory;



import com.example.demo.dto.AirportDTO;
import com.example.demo.model.Airport;
import org.springframework.stereotype.Component;

@Component
public class AirportDTOFactory {

    public AirportDTO createAirportDto(Airport airport) {
        return AirportDTO.builder()
                .id(airport.getAirportId())
                .name(airport.getAirportName())
                .iataCode(airport.getIATA_Code())
                .city(airport.getCity())
                .country(airport.getCountry())
                .build();
    }

    public Airport createEntity(AirportDTO airportDTO) {
        return new Airport(
                airportDTO.getAirportId(),
                airportDTO.getAirportName(),
                airportDTO.getIata_Code(),
                airportDTO.getCity(),
                airportDTO.getCountry()
        );
    }
}
