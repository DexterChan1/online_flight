package com.example.demo.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class ApiResponseDTO<T> {
    private boolean success;
    private String message ;
    private T data;

    @Builder
    public ApiResponseDTO(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public static <T> ApiResponseDTO<T> success(T data) {
        return ApiResponseDTO.<T>builder()
                .success(true)
                .data(data)
                .build();
    }

    public static <T> ApiResponseDTO<T> fail(String errorMessage) {
        return ApiResponseDTO.<T>builder()
                .success(false)
                .message(errorMessage)
                .build();
    }
}
