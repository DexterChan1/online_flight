package com.example.demo.controller;

import com.example.demo.dto.ApiResponseDTO;
import com.example.demo.dto.PaymentInputDTO;
import com.example.demo.model.Payment;
import com.example.demo.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<Payment>>> getAllPayments() {
        List<Payment> payments = paymentService.getAllPayments();
        return ResponseEntity.ok(ApiResponseDTO.success(payments));
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<ApiResponseDTO<Payment>> getPaymentById(@PathVariable Long paymentId) {
        Payment payment = paymentService.getPaymentById(paymentId);
        return ResponseEntity.ok(ApiResponseDTO.success(payment));
    }

    @PostMapping
    public ResponseEntity<ApiResponseDTO<Payment>> createPayment(@RequestBody PaymentInputDTO paymentInputDTO) {
        System.out.println("Received paymentInputDTO: " + paymentInputDTO); // Add this line
        Payment payment = paymentService.createPayment(paymentInputDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponseDTO.success(payment));
    }

    @PutMapping("/{paymentId}")
    public ResponseEntity<ApiResponseDTO<Payment>> updatePayment(@PathVariable Long paymentId, @RequestBody PaymentInputDTO paymentInputDTO) {
        Payment updatedPayment = paymentService.updatePayment(paymentId, paymentInputDTO);
        return ResponseEntity.ok(ApiResponseDTO.success(updatedPayment));
    }

    /*@DeleteMapping("/{paymentId}")
    public ResponseEntity<ApiResponseDTO<Void>> deletePayment(@PathVariable Long paymentId) {
        paymentService.deletePayment(paymentId);
        return ResponseEntity.noContent().build();
    } */

    @DeleteMapping("/{paymentId}")
    public ResponseEntity<ApiResponseDTO<String>> deletePayment(@PathVariable Long paymentId) {
        paymentService.deletePayment(paymentId);
        return ResponseEntity.ok(ApiResponseDTO.success("Payment deleted successfully."));
    }
}
