package com.lifeline.lifeline_blood_connect.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiError(
        LocalDateTime timestamp,
        int status,
        String error,
        String message,
        String path,
        Map<String, String> fieldErrors
) {
    public static ApiError of(int status, String error, String message, String path) {
        return new ApiError(LocalDateTime.now(), status, error, message, path, null);
    }

    public static ApiError validation(int status, String message, String path, Map<String, String> fieldErrors) {
        return new ApiError(LocalDateTime.now(), status, "Validation Failed", message, path, fieldErrors);
    }
}