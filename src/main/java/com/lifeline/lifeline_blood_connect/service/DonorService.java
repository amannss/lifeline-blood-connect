package com.lifeline.lifeline_blood_connect.service;

import com.lifeline.lifeline_blood_connect.dto.DonorRequestDTO;
import com.lifeline.lifeline_blood_connect.dto.DonorResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DonorService {

    DonorResponseDTO create(DonorRequestDTO dto);

    DonorResponseDTO getById(Long id);

    Page<DonorResponseDTO> getAll(Pageable pageable);

    DonorResponseDTO update(Long id, DonorRequestDTO dto);

    void softDelete(Long id);
}