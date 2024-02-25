package com.example.demo.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TripFlightDTO {
    private Long tripId;
    private Long flightId;
    // Constructors
    public TripFlightDTO() {}

    public TripFlightDTO(Long tripId, Long flightId) {
        this.tripId = tripId;
        this.flightId = flightId;
    }
}
