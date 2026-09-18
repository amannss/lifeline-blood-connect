package com.lifeline.lifeline_blood_connect.mapper;

import com.lifeline.lifeline_blood_connect.dto.DonorRequestDTO;
import com.lifeline.lifeline_blood_connect.dto.DonorResponseDTO;
import com.lifeline.lifeline_blood_connect.entity.Donor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Component
public class DonorMapper {

    /** Minimum gap required to become eligible**/
    public static final int DONATION_GAP_DAYS = 90;

    public Donor toEntity(DonorRequestDTO dto) {
        return Donor.builder()
                .name(dto.getName().trim())
                .email(dto.getEmail().trim().toLowerCase())
                .phone(dto.getPhone().trim())
                .bloodGroup(dto.getBloodGroup())
                .city(dto.getCity().trim())
                .latitude(dto.getLatitude())
                .longitude(dto.getLongitude())
                .lastDonationDate(dto.getLastDonationDate())
                .available(dto.getAvailable() == null || dto.getAvailable())
                .donationCount(0)
                .active(true)
                .build();
    }

    /** Copies mutable fields onto a managed entity — deliberately does NOT touch id, active, donationCount. */
    public void updateEntity(Donor donor, DonorRequestDTO dto) {
        donor.setName(dto.getName().trim());
        donor.setEmail(dto.getEmail().trim().toLowerCase());
        donor.setPhone(dto.getPhone().trim());
        donor.setBloodGroup(dto.getBloodGroup());
        donor.setCity(dto.getCity().trim());
        donor.setLatitude(dto.getLatitude());
        donor.setLongitude(dto.getLongitude());
        donor.setLastDonationDate(dto.getLastDonationDate());
        if (dto.getAvailable() != null) {
            donor.setAvailable(dto.getAvailable());
        }
    }

    public DonorResponseDTO toResponse(Donor donor) {
        return new DonorResponseDTO(
                donor.getId(),
                donor.getName(),
                donor.getEmail(),
                donor.getPhone(),
                donor.getBloodGroup(),
                donor.getBloodGroup().getLabel(),
                donor.getCity(),
                donor.getLastDonationDate(),
                donor.getDonationCount(),
                donor.getAvailable(),
                isEligible(donor),
                donor.getCreatedAt()
        );
    }

    private boolean isEligible(Donor donor) {
        if (donor.getAvailable()==false) {
            return false;
        }
        if (donor.getLastDonationDate() == null) {
            return true;
        }
        long days = ChronoUnit.DAYS.between(donor.getLastDonationDate(), LocalDate.now());
        return days >= DONATION_GAP_DAYS;
    }
}