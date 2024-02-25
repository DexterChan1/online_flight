package com.example.demo.repository;

import com.example.demo.model.TripFlight;
import com.example.demo.model.TripFlightId;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TripFlightRepository extends JpaRepository<TripFlight, TripFlightId> {

    @Query("SELECT tf FROM TripFlight tf WHERE tf.id.tripId = :tripId")
    List<TripFlight> findByTripId(@Param("tripId") Long tripId);

    @Query("SELECT tf FROM TripFlight tf WHERE tf.id.flightId = :flightId")
    List<TripFlight> findByFlightId(@Param("flightId") Long flightId);


    // 2. Using @Query for more complex/custom queries (if needed)
    // For example, if you wanted to delete all TripFlights for a particular trip:
    @Modifying
    @Transactional
    @Query("DELETE FROM TripFlight tf WHERE tf.id.tripId = :tripId AND tf.id.flightId = :flightId")
    void deleteByTripIdAndFlightId(@Param("tripId") Long tripId, @Param("flightId") Long flightId);
}


