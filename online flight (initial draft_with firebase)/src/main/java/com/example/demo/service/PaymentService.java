package com.example.demo.service;

import com.example.demo.dto.PaymentInputDTO;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.factory.PaymentInputDTOFactory;
import com.example.demo.model.Passenger;
import com.example.demo.model.Payment;
import com.example.demo.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentInputDTOFactory paymentInputDTOFactory;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository, PaymentInputDTOFactory paymentInputDTOFactory) {
        this.paymentRepository = paymentRepository;
        this.paymentInputDTOFactory = paymentInputDTOFactory;
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public Payment getPaymentById(Long paymentId) {
        return paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment", "id", paymentId));
    }

    public Payment createPayment(PaymentInputDTO paymentInputDTO) {
        Payment payment = paymentInputDTOFactory.createEntity(paymentInputDTO);
        return paymentRepository.save(payment);
    }

    public Payment updatePayment(Long paymentId, PaymentInputDTO paymentInputDTO) {
        if (!paymentId.equals(paymentInputDTO.getPaymentId())) {
            throw new IllegalArgumentException("Payment ID mismatch.");
        }

        // This will throw a ResourceNotFoundException if the payment isn't found.
        Payment existingPayment = getPaymentById(paymentId);

        Payment updatedPayment = paymentInputDTOFactory.createEntity(paymentInputDTO);
        updatedPayment.setPaymentId(paymentId);

        return paymentRepository.save(updatedPayment);
    }

    public void deletePayment(Long paymentId) {
        paymentRepository.deleteById(paymentId);
    }
}
