package com.lifeline.lifeline_blood_connect.repository;

import com.lifeline.lifeline_blood_connect.entity.BloodGroup;
import com.lifeline.lifeline_blood_connect.entity.Donor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DonorRepository extends JpaRepository<Donor, Long> {

    // --- soft-delete-aware reads ---
    Optional<Donor> findByIdAndActiveTrue(Long id);

    Page<Donor> findAllByActiveTrue(Pageable pageable);

    List<Donor> findByBloodGroupAndCityIgnoreCaseAndActiveTrueAndAvailableTrue(
            BloodGroup bloodGroup, String city);

    // --- uniqueness checks ---
    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

    boolean existsByEmailAndIdNot(String email, Long id);

    boolean existsByPhoneAndIdNot(String phone, Long id);
}