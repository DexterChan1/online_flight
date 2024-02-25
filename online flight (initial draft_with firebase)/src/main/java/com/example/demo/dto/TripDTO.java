package com.example.demo.dto;

import com.example.demo.model.Trip;
import com.example.demo.model.enums.ClassType;
import com.example.demo.model.enums.PaymentStatus;
import com.example.demo.model.enums.TripType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TripDTO {

    private Long tripId;

    private Long userId;
    private String userName;

    // Departure Flight details
    private Long departureFlightId;
    private String departureFlightNumber;
    private String departureAirportName;
    private String departureAirportCity;
    private String departureAirportCountry;
    private String departureArrivalAirportName;
    private String departureArrivalAirportCity;
    private String departureArrivalAirportCountry;
    private Timestamp departureTime; // Added this for departure flight's departure time
    private Timestamp arrivalTime;   // Added this for departure flight's arrival time
    private Double departureEconomyClassFare;        // Added this for departure flight's fare
    private Double departureBusinessClassFare;
    private Double departureFirstClassFare;

    // Returning Flight details
    private Long returningFlightId;
    private String returningFlightNumber;
    private String returningAirportName;
    private String returningAirportCity;
    private String returningAirportCountry;
    private String returningArrivalAirportName;
    private String returningArrivalAirportCity;
    private String returningArrivalAirportCountry;
    private Timestamp returningDepartureTime; // Added this for return flight's departure time
    private Timestamp returningArrivalTime;   // Added this for return flight's arrival time
    private Double returningEconomyClassFare;                 // Added this for return flight's fare
    private Double returningBusinessClassFare;
    private Double returningFirstClassFare;
    private LocalDate departureDate;
    private LocalDate returningDate;
    private Integer numberOfPassengers;
    private TripType tripType;
    private ClassType departureClassType;
    private ClassType returningClassType;
//    private PaymentStatus paymentStatus;



    // You can add more fields or methods if required.


//    public TripDTO(Long tripId, Long userId, String userName,
//                   Long departureFlightId, String departureFlightNumber, String departureAirportName,
//                   String departureAirportCity, String departureAirportCountry, Timestamp departureTime, Timestamp arrivalTime,
//                    Long returningFlightId, String returningFlightNumber, String returningAirportName,
//                   String returningAirportCity, String returningAirportCountry, Timestamp returningDepartureTime,
//                   Timestamp returningArrivalTime, Integer numberOfPassengers,
//                   TripType tripType, ClassType classType, PaymentStatus paymentStatus
//
//                   ) {
//        this.tripId = tripId;
//        this.userId = userId;
//        this.userName = userName;
//        this.departureFlightId = departureFlightId;
//        this.departureFlightNumber = departureFlightNumber;
//        this.departureAirportName = departureAirportName;
//        this.departureAirportCity = departureAirportCity;
//        this.departureAirportCountry = departureAirportCountry;
//        this.departureTime = departureTime;
//        this.arrivalTime = arrivalTime;
////        this.departureEconomyClassFare = departureEconomyClassFare;
////        this.departureBusinessClassFare = departureBusinessClassFare;
////        this.departureFirstClassFare = departureFirstClassFare;
//        this.returningFlightId = returningFlightId;
//        this.returningFlightNumber = returningFlightNumber;
//        this.returningAirportName = returningAirportName;
//        this.returningAirportCity = returningAirportCity;
//        this.returningAirportCountry = returningAirportCountry;
//        this.returningDepartureTime = returningDepartureTime;
//        this.returningArrivalTime = returningArrivalTime;
////        this.returningEconomyClassFare = returningEconomyClassFare;
////        this.returningBusinessClassFare = returningBusinessClassFare;
////        this.returningFirstClassFare = returningFirstClassFare;
//        this.numberOfPassengers = numberOfPassengers;
//        this.tripType = tripType;
//        this.classType = classType;
//        this.paymentStatus = paymentStatus;
//    }
}