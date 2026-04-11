package com.elessa.immigrant.controller;

import com.elessa.immigrant.dto.ImmigrantRequestDTO;
import com.elessa.immigrant.dto.ImmigrantResponseDTO;
import com.elessa.immigrant.enums.ImmigrantStatus;
import com.elessa.immigrant.service.ImmigrantService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/immigrants")
public class ImmigrantController {

    @Autowired
    private ImmigrantService immigrantService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ImmigrantResponseDTO> create(@Valid @RequestBody ImmigrantRequestDTO requestDTO) {
        ImmigrantResponseDTO response = immigrantService.create(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'VOLUNTEER')")
    public ResponseEntity<Page<ImmigrantResponseDTO>> findAll(
            @PageableDefault(size = 10, sort = "registeredAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(immigrantService.findAll(pageable));
    }

    @GetMapping("/status/{status}")
    @PreAuthorize("hasAnyRole('ADMIN', 'VOLUNTEER')")
    public ResponseEntity<Page<ImmigrantResponseDTO>> findByStatus(
            @PathVariable ImmigrantStatus status,
            @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(immigrantService.findByStatus(status, pageable));
    }

    @GetMapping("/nationality/{nationality}")
    @PreAuthorize("hasAnyRole('ADMIN', 'VOLUNTEER')")
    public ResponseEntity<Page<ImmigrantResponseDTO>> findByNationality(
            @PathVariable String nationality,
            @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(immigrantService.findByNationality(nationality, pageable));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'VOLUNTEER')")
    public ResponseEntity<ImmigrantResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(immigrantService.findById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ImmigrantResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody ImmigrantRequestDTO requestDTO) {
        return ResponseEntity.ok(immigrantService.update(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        immigrantService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ImmigrantResponseDTO> updateStatus(
            @PathVariable Long id,
            @RequestParam ImmigrantStatus status) {
        return ResponseEntity.ok(immigrantService.updateStatus(id, status));
    }
}