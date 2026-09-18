package com.lifeline.lifeline_blood_connect.service;

import com.lifeline.lifeline_blood_connect.dto.DonorRequestDTO;
import com.lifeline.lifeline_blood_connect.dto.DonorResponseDTO;
import com.lifeline.lifeline_blood_connect.entity.Donor;
import com.lifeline.lifeline_blood_connect.exception.DuplicateResourceException;
import com.lifeline.lifeline_blood_connect.exception.ResourceNotFoundException;
import com.lifeline.lifeline_blood_connect.mapper.DonorMapper;
import com.lifeline.lifeline_blood_connect.repository.DonorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.lifeline.lifeline_blood_connect.exception.DuplicateResourceException;

@Service
@RequiredArgsConstructor
@Slf4j
public class DonorServiceImpl implements DonorService {

    private final DonorRepository donorRepository;
    private final DonorMapper donorMapper;

    @Override
    @Transactional
    public DonorResponseDTO create(DonorRequestDTO dto) {
        String email = dto.getEmail().trim().toLowerCase();

        if (donorRepository.existsByEmail(email)) {
            throw new DuplicateResourceException("Donor already registered with email: " + email);
        }
        if (donorRepository.existsByPhone(dto.getPhone().trim())) {
            throw new DuplicateResourceException("Donor already registered with phone: " + dto.getPhone());
        }

        Donor saved = donorRepository.save(donorMapper.toEntity(dto));
        log.info("Donor created id={} bloodGroup={} city={}", saved.getId(), saved.getBloodGroup(), saved.getCity());
        return donorMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public DonorResponseDTO getById(Long id) {
        Donor donor = findActiveOrThrow(id);
        return donorMapper.toResponse(donor);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DonorResponseDTO> getAll(Pageable pageable) {
        return donorRepository.findAllByActiveTrue(pageable)
                .map(donorMapper::toResponse);
    }

    @Override
    @Transactional
    public DonorResponseDTO update(Long id, DonorRequestDTO dto) {
        Donor donor = findActiveOrThrow(id);

        String email = dto.getEmail().trim().toLowerCase();
        if (donorRepository.existsByEmailAndIdNot(email, id)) {
            throw new DuplicateResourceException("Email already in use: " + email);
        }
        if (donorRepository.existsByPhoneAndIdNot(dto.getPhone().trim(), id)) {
            throw new DuplicateResourceException("Phone already in use: " + dto.getPhone());
        }

        donorMapper.updateEntity(donor, dto);
        // No explicit save() needed — donor is a managed entity and Hibernate
        // dirty-checks it at flush time. save() here would be harmless but redundant.
        log.info("Donor updated id={}", id);
        return donorMapper.toResponse(donor);
    }

    @Override
    @Transactional
    public void softDelete(Long id) {
        Donor donor = findActiveOrThrow(id);
        donor.setActive(false);
        donor.setAvailable(false);
        log.info("Donor soft-deleted id={}", id);
    }

    private Donor findActiveOrThrow(Long id) {
        return donorRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Donor not found with id: " + id));
    }
}