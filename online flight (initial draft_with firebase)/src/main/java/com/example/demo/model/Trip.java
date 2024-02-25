package com.example.demo.model;


import com.example.demo.model.enums.ClassType;
import com.example.demo.model.enums.PaymentStatus;
import com.example.demo.model.enums.TripType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "trips")
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trip_id")
    private Long tripId;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "departure_flight_id", referencedColumnName = "flight_id")
    private Flight departureFlight;

    @ManyToOne
    @JoinColumn(name = "returning_flight_id", referencedColumnName = "flight_id")
    private Flight returningFlight;

    @Column(name = "departure_date")
    private LocalDate departureDate;

    @Column(name = "returning_date")
    private LocalDate returningDate;

    @Column(name = "number_of_passengers")
    private Integer numberOfPassengers;

    @Enumerated(EnumType.STRING)
    @Column(name = "trip_type")
    private TripType tripType;

    @Enumerated(EnumType.STRING)
    @Column(name = "departure_class_type")
    private ClassType departureClassType;

    @Enumerated(EnumType.STRING)
    @Column(name = "returning_class_type")
    private ClassType returningClassType;

//    @Enumerated(EnumType.STRING)        // For testing backend purpose only
//    @Column(name = "payment_status")
//    private PaymentStatus paymentStatus;


    public Long getTripId() {
        return tripId;
    }

    public void setTripId(Long tripId) {
        this.tripId = tripId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Flight getDepartureFlight() {
        return departureFlight;
    }

    public void setDepartureFlight(Flight departureFlight) {
        this.departureFlight = departureFlight;
    }

    public Flight getReturningFlight() { return returningFlight;}

    public void setReturningFlight(Flight returningFlight) { this.returningFlight = returningFlight;}

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
    }

    public LocalDate getReturningDate() {
        return returningDate;
    }

    public void setReturningDate(LocalDate returningDate) {
        this.returningDate = returningDate;
    }

    public Integer getNumberOfPassengers() {
        return numberOfPassengers;
    }

    public void setNumberOfPassengers(Integer numberOfPassengers) {
        this.numberOfPassengers = numberOfPassengers;
    }

    public TripType getTripType() {
        return tripType;
    }

    public void setTripType(TripType tripType) {
        this.tripType = tripType;
    }

    public ClassType getDepartureClassType() {
        return departureClassType;
    }

    public void setDepartureClassType(ClassType departureClassType) {
        this.departureClassType = departureClassType;
    }

    public ClassType getReturningClassType() {
        return returningClassType;
    }

    public void setReturningClassType(ClassType returningClassType) {
        this.returningClassType = returningClassType;
    }

//    public PaymentStatus getPaymentStatus() {
//        return paymentStatus;
//    }
//
//    public void setPaymentStatus(PaymentStatus paymentStatus) {
//        this.paymentStatus = paymentStatus;
//    }

}
