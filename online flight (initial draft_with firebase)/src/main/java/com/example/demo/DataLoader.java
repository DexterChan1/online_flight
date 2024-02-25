package com.example.demo;

import com.example.demo.model.*;
import com.example.demo.model.enums.ClassType;
import com.example.demo.model.enums.PaymentStatus;
import com.example.demo.model.enums.TripType;
import com.example.demo.repository.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

@Component
public class DataLoader {

    @Autowired
    private AirportRepository airportRepository;

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private PassengerRepository passengerRepository;

    @Autowired
    private ContactPersonRepository contactPersonRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @PostConstruct
    public void init() {
        // Adding sample airports
        Airport airport1 = Airport.builder()
                .airportName("John F. Kennedy International Airport")
                .IATA_Code("JFK")
                .city("New York")
                .country("United States")
                .build();

        Airport airport2 = Airport.builder()
                .airportName("Los Angeles International Airport")
                .IATA_Code("LAX")
                .city("Los Angeles")
                .country("United States")
                .build();

        Airport airport3 = Airport.builder()
                .airportName("Heathrow Airport")
                .IATA_Code("LHR")
                .city("London")
                .country("United Kingdom")
                .build();

        airportRepository.save(airport1);
        airportRepository.save(airport2);
        airportRepository.save(airport3);


        // Adding sample flights

        Flight flight1 = Flight.builder()
                .flightNumber("AA101")
                .aircraftType("Boeing 747")
                .airline("American Airlines")
                .departureTime(Timestamp.valueOf(LocalDateTime.of(2023, Month.NOVEMBER, 1, 8, 0)))
                .arrivalTime(Timestamp.valueOf(LocalDateTime.of(2023, Month.NOVEMBER, 1, 12, 0)))
                .economyClassFare(450.00)
                .businessClassFare(1350.00)
                .firstClassFare(2250.00)
                .departureAirport(airport1)
                .arrivalAirport(airport2)
                .build();

        Flight flight2 = Flight.builder()
                .flightNumber("AA102")
                .aircraftType("Boeing 747")
                .airline("American Airlines")
                .departureTime(Timestamp.valueOf(LocalDateTime.of(2023, Month.NOVEMBER, 1, 9, 0)))
                .arrivalTime(Timestamp.valueOf(LocalDateTime.of(2023, Month.NOVEMBER, 1, 13, 0)))
                .economyClassFare(460.00)
                .businessClassFare(1380.00)
                .firstClassFare(2300.00)
                .departureAirport(airport1)
                .arrivalAirport(airport2)
                .build();

        Flight flight3 = Flight.builder()
                .flightNumber("AA103")
                .aircraftType("Boeing 747")
                .airline("American Airlines")
                .departureTime(Timestamp.valueOf(LocalDateTime.of(2023, Month.NOVEMBER, 1, 10, 0)))
                .arrivalTime(Timestamp.valueOf(LocalDateTime.of(2023, Month.NOVEMBER, 1, 14, 0)))
                .economyClassFare(470.00)
                .businessClassFare(1410.00)
                .firstClassFare(2350.00)
                .departureAirport(airport1)
                .arrivalAirport(airport2)
                .build();

        Flight flight4 = Flight.builder()
                .flightNumber("AA104")
                .aircraftType("Boeing 777")
                .airline("American Airlines")
                .departureTime(Timestamp.valueOf(LocalDateTime.of(2023, Month.NOVEMBER, 1, 15, 0)))
                .arrivalTime(Timestamp.valueOf(LocalDateTime.of(2023, Month.NOVEMBER, 1, 19, 0)))
                .economyClassFare(450.00)
                .businessClassFare(1350.00)
                .firstClassFare(2250.00)
                .departureAirport(airport2)
                .arrivalAirport(airport1)
                .build();

        Flight flight5 = Flight.builder()
                .flightNumber("AA105")
                .aircraftType("Boeing 777")
                .airline("American Airlines")
                .departureTime(Timestamp.valueOf(LocalDateTime.of(2023, Month.NOVEMBER, 1, 16, 0)))
                .arrivalTime(Timestamp.valueOf(LocalDateTime.of(2023, Month.NOVEMBER, 1, 20, 0)))
                .economyClassFare(460.00)
                .businessClassFare(1380.00)
                .firstClassFare(2300.00)
                .departureAirport(airport2)
                .arrivalAirport(airport1)
                .build();

        Flight flight6 = Flight.builder()
                .flightNumber("AA106")
                .aircraftType("Boeing 777")
                .airline("American Airlines")
                .departureTime(Timestamp.valueOf(LocalDateTime.of(2023, Month.NOVEMBER, 1, 17, 0)))
                .arrivalTime(Timestamp.valueOf(LocalDateTime.of(2023, Month.NOVEMBER, 1, 21, 0)))
                .economyClassFare(470.00)
                .businessClassFare(1410.00)
                .firstClassFare(2350.00)
                .departureAirport(airport2)
                .arrivalAirport(airport1)
                .build();

        Flight flight7 = Flight.builder()
                .flightNumber("AA107")
                .aircraftType("Boeing 747")
                .airline("Delta Airlines")
                .departureTime(Timestamp.valueOf(LocalDateTime.of(2023, Month.NOVEMBER, 1, 13, 0)))
                .arrivalTime(Timestamp.valueOf(LocalDateTime.of(2023, Month.NOVEMBER, 2, 7, 0)))
                .economyClassFare(1000.00)
                .businessClassFare(3000.00)
                .firstClassFare(5000.00)
                .departureAirport(airport2)
                .arrivalAirport(airport3)
                .build();

        Flight flight8 = Flight.builder()
                .flightNumber("AA108")
                .aircraftType("Boeing 747")
                .airline("Delta Airlines")
                .departureTime(Timestamp.valueOf(LocalDateTime.of(2023, Month.NOVEMBER, 1, 18, 0)))
                .arrivalTime(Timestamp.valueOf(LocalDateTime.of(2023, Month.NOVEMBER, 1, 20, 0)))
                .economyClassFare(1100.00)
                .businessClassFare(3300.00)
                .firstClassFare(5500.00)
                .departureAirport(airport3)
                .arrivalAirport(airport2)
                .build();

        flightRepository.save(flight1);
        flightRepository.save(flight2);
        flightRepository.save(flight3);
        flightRepository.save(flight4);
        flightRepository.save(flight5);
        flightRepository.save(flight6);
        flightRepository.save(flight7);
        flightRepository.save(flight8);

        // Adding users
        User user1 = User.builder()
                .userName("john_doe")
//                .password("securepassword123") // Make sure to use bcrypt or another hashing method in production
                .firstName("John")
                .lastName("Doe")
                .email("johndoe@example.com")
                .phoneNumber("1234567890")
                .address("123 Main St, New York, United States")
                .uid("zoWXSbSH62XnXGJhuiNbN55TUgF3")
                .build();

        User user2 = User.builder()
                .userName("jane_smith")
//                .password("anothersecurepassword") // Make sure to use bcrypt or another hashing method in production
                .firstName("Jane")
                .lastName("Smith")
                .email("janesmith@example.com")
                .phoneNumber("2345678901")
                .address("456 Secondary St, London, United Kingdom")
                .uid("sgPFFXOvIzbicl1j6mP8F6rS6Cr1")
                .build();
        userRepository.save(user1);
        userRepository.save(user2);


        // Create a Trip for a round-trip
        Trip trip1 = Trip.builder()
                .user(user1)
                .departureFlight(flight1)
                .returningFlight(flight4)
                .departureDate(LocalDate.of(2023, 11, 4)) // Using LocalDate.of
                .returningDate(LocalDate.of(2023, 11, 18)) // Assuming a return date, using LocalDate.of
                .numberOfPassengers(2)
                .tripType(TripType.ROUND_TRIP)
                .departureClassType(ClassType.ECONOMY)
                .returningClassType(ClassType.FIRST_CLASS)
//                .paymentStatus(PaymentStatus.PAID)
                .build();
        tripRepository.save(trip1);

        //Testing for one-way trip
        Trip trip2 = Trip.builder()
                .user(user1)
                .departureFlight(flight2)
                .departureDate(LocalDate.of(2023, 11, 11)) // Using LocalDate.of
                .numberOfPassengers(1)
                .tripType(TripType.ONE_WAY)
                .departureClassType(ClassType.BUSINESS)
                .returningClassType(ClassType.BUSINESS)
//                .paymentStatus(PaymentStatus.PENDING)
                .build();
        tripRepository.save(trip2);

        // Adding sample passengers
        Passenger passenger1 = Passenger.builder()
                .firstName("Alice")
                .lastName("Johnson")
                .dateOfBirth(Date.valueOf(LocalDate.of(1990, Month.JANUARY, 1)))
                .nationality("American")
                .passportNumber("A1234567")
                .trip(trip1)
                .build();
        passengerRepository.save(passenger1);

        Passenger passenger2 = Passenger.builder()
                .firstName("Bob")
                .lastName("Smith")
                .dateOfBirth(Date.valueOf(LocalDate.of(1985, Month.FEBRUARY, 15)))
                .nationality("British")
                .passportNumber("B7654321")
                .trip(trip1)
                .build();
        passengerRepository.save(passenger2);

//        Passenger passenger3 = Passenger.builder()
//                .firstName("Elmo")
//                .lastName("Jackson")
//                .dateOfBirth(Date.valueOf(LocalDate.of(1997, Month.JUNE, 18)))
//                .nationality("American")
//                .passportNumber("Z98765432")
//                .trip(trip2)
//                .build();
//        passengerRepository.save(passenger3);



        // Adding sample contact persons
        ContactPerson contactPerson1 = ContactPerson.builder()
                .firstName("Charlie")
                .lastName("Brown")
                .nationality("American")
                .phoneNumber("(212) 555-1234")
                .emailAddress("charlie.brown@example.com")
                .address("1234 Peanut St, Cartoonville, New York, United States")
                .trip(trip1)
                .build();
        contactPersonRepository.save(contactPerson1);

//        ContactPerson contactPerson2 = ContactPerson.builder()
//                .firstName("Elmo")
//                .lastName("Jackson")
//                .nationality("American")
//                .phoneNumber("(718) 555-5678")
//                .emailAddress("elmo.jackson@example.com")
//                .address("5678 Seasame St, Happyville, New York, United States")
//                .trip(trip2)
//                .build();
//        contactPersonRepository.save(contactPerson2);

        // Adding sample payments
        Payment payment1 = Payment.builder()
                .trip(trip1)
//                .paymentMethod("Credit Card")
                .paymentAmount(1800.00)
                .cardNumber("4111231477795432")
                .cardHolderName("Charlie Brown")
                .expiryDate("12/25")  // You might want to set an actual future date
                .securityCode("123")
                .build();
        paymentRepository.save(payment1);
    }
}
