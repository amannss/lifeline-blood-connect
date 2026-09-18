package com.lifeline.lifeline_blood_connect.dto;

import com.lifeline.lifeline_blood_connect.entity.BloodGroup;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DonorRequestDTO {

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be 2-100 characters")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    private String email;

    @NotBlank(message = "Phone is required")
    @Pattern(regexp = "^[6-9]\\d{9}$", message = "Phone must be a valid 10-digit  number")
    private String phone;

    @NotNull(message = "Blood group is required")
    private BloodGroup bloodGroup;

    @NotBlank(message = "City is required")
    private String city;

    @DecimalMin(value = "-90.0") @DecimalMax(value = "90.0")
    private Double latitude;

    @DecimalMin(value = "-180.0") @DecimalMax(value = "180.0")
    private Double longitude;

    @PastOrPresent(message = "Last donation date cannot be in the future")
    private LocalDate lastDonationDate;

    private Boolean available;
}