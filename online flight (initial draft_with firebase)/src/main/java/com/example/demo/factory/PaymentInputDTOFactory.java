package com.example.demo.factory;

import com.example.demo.dto.PaymentInputDTO;
import com.example.demo.model.Payment;
import com.example.demo.model.Trip;
import com.example.demo.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PaymentInputDTOFactory {
    // Assuming you have a TripService or TripRepository to fetch the Trip entity by its ID
    @Autowired
    private TripService tripService; // Replace with TripRepository if you don't have a TripService

    public PaymentInputDTO createDTO(Payment payment) {
        return PaymentInputDTO.builder()
                .tripId(payment.getTrip().getTripId())
//                .paymentMethod(payment.getPaymentMethod())
                .paymentAmount(payment.getPaymentAmount())
                .cardNumber(payment.getCardNumber())
                .cardHolderName(payment.getCardHolderName())
                .expiryDate(payment.getExpiryDate())
                // You shouldn't have the CVV stored, but if it was, you'd add it here
                .securityCode(payment.getSecurityCode()) // Not recommended!
                .build();

    }

    public Payment createEntity(PaymentInputDTO paymentInputDTO) {
        Trip associatedTrip = tripService.findTripEntityById(paymentInputDTO.getTripId());

        Payment payment = new Payment();
        payment.setTrip(associatedTrip);
//        payment.setPaymentMethod(paymentInputDTO.getPaymentMethod());
        payment.setPaymentAmount(paymentInputDTO.getPaymentAmount());
        payment.setCardNumber(paymentInputDTO.getCardNumber());
        payment.setCardHolderName(paymentInputDTO.getCardHolderName());
        payment.setExpiryDate(paymentInputDTO.getExpiryDate());
        // Note: We're setting the securityCode (CVV) here, but you should ensure it's not saved to the database.
        payment.setSecurityCode(paymentInputDTO.getSecurityCode());

        return payment;
    }
}
