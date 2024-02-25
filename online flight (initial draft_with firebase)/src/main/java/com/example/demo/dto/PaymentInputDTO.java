package com.example.demo.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class PaymentInputDTO {

    private Long tripId; // The associated trip's ID
    private Long paymentId;
//    private String paymentMethod;
    private Double paymentAmount;
    private String cardNumber;
    private String cardHolderName;
    private String expiryDate; // Format: MM/YY
    private String securityCode; // CVV

    @Builder
    public PaymentInputDTO(Long tripId, Long paymentId,Double paymentAmount, String cardNumber, String cardHolderName, String expiryDate, String securityCode) {
        this.tripId = tripId;
        this.paymentId = paymentId;
//        this.paymentMethod = paymentMethod;
        this.paymentAmount = paymentAmount;
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
        this.expiryDate = expiryDate;
        this.securityCode = securityCode;
    }
}