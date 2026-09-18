package com.lifeline.lifeline_blood_connect.controller;

import com.lifeline.lifeline_blood_connect.dto.DonorRequestDTO;
import com.lifeline.lifeline_blood_connect.dto.DonorResponseDTO;
import com.lifeline.lifeline_blood_connect.service.DonorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/donors")
@RequiredArgsConstructor
public class DonorController {

    private final DonorService donorService;

    @PostMapping
    public ResponseEntity<DonorResponseDTO> create(@Valid @RequestBody DonorRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(donorService.create(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DonorResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(donorService.getById(id));
    }

    @GetMapping
    public ResponseEntity<Page<DonorResponseDTO>> getAll(
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC)
            Pageable pageable) {
        return ResponseEntity.ok(donorService.getAll(pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DonorResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody DonorRequestDTO dto) {
        return ResponseEntity.ok(donorService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        donorService.softDelete(id);
        return ResponseEntity.noContent().build();
    }
}