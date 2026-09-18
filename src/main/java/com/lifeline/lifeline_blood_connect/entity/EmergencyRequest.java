package com.lifeline.lifeline_blood_connect.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "emergency_requests",
        indexes = {
                @Index(name = "idx_req_status_blood", columnList = "status, blood_group")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmergencyRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "hospital_id", nullable = false)
    private Hospital hospital;

    @Column(name = "patient_name", length = 100)
    private String patientName;

    @Enumerated(EnumType.STRING)
    @Column(name = "blood_group", nullable = false, length = 15)
    private BloodGroup bloodGroup;

    @Column(name = "units_required", nullable = false)
    private Integer unitsRequired;

    @Column(name = "units_fulfilled", nullable = false)
    @Builder.Default
    private Integer unitsFulfilled = 0;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 15)
    @Builder.Default
    private Urgency urgency = Urgency.NORMAL;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 25)
    @Builder.Default
    private RequestStatus status = RequestStatus.PENDING;

    @Column(name = "needed_by")
    private LocalDateTime neededBy;

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
