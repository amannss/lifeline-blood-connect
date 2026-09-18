package com.lifeline.lifeline_blood_connect.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "donors",
        indexes = {
                @Index(name = "idx_donor_blood_city", columnList = "blood_group, city"),
                @Index(name = "idx_donor_active", columnList = "active")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Donor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Column(nullable = false, unique = true, length = 15)
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(name = "blood_group", nullable = false, length = 15)
    private BloodGroup bloodGroup;

    @Column(nullable = false, length = 80)
    private String city;

    private Double latitude;

    private Double longitude;

    @Column(name = "last_donation_date")
    private LocalDate lastDonationDate;

    @Column(name = "donation_count", nullable = false)
    @Builder.Default
    private Integer donationCount = 0;

    /** Donor has opted in to receive emergency requests. */
    @Column(nullable = false)
    @Builder.Default
    private Boolean available = true;

    /** Soft-delete flag. false = deleted from the user's point of view. */
    @Column(nullable = false)
    @Builder.Default
    private Boolean active = true;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}