package com.lifeline.lifeline_blood_connect.repository;

import com.lifeline.lifeline_blood_connect.entity.BloodGroup;
import com.lifeline.lifeline_blood_connect.entity.EmergencyRequest;
import com.lifeline.lifeline_blood_connect.entity.RequestStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmergencyRequestRepository extends JpaRepository<EmergencyRequest, Long> {

    Optional<EmergencyRequest> findByIdAndActiveTrue(Long id);

    Page<EmergencyRequest> findAllByStatusAndActiveTrue(RequestStatus status, Pageable pageable);

    List<EmergencyRequest> findByBloodGroupAndStatusAndActiveTrue(
            BloodGroup bloodGroup, RequestStatus status);

    List<EmergencyRequest> findByStatusInAndNeededByBefore(
            List<RequestStatus> statuses, LocalDateTime cutoff);
}