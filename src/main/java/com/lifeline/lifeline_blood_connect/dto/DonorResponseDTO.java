package com.lifeline.lifeline_blood_connect.dto;

import com.lifeline.lifeline_blood_connect.entity.BloodGroup;

import java.time.LocalDate;
import java.time.LocalDateTime;
public record DonorResponseDTO(
        Long id,
        String name,
        String email,
        String phone,
        BloodGroup bloodGroup,
        String bloodGroupLabel,
        String city,
        LocalDate lastDonationDate,
        Integer donationCount,
        Boolean available,
        Boolean eligibleToDonate,
        LocalDateTime createdAt
) {}