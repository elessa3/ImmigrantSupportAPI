package com.elessa.immigrant.controller;

import com.elessa.immigrant.dto.ServiceRecordRequestDTO;
import com.elessa.immigrant.dto.ServiceRecordResponseDTO;
import com.elessa.immigrant.service.ServiceRecordService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/service-records")
public class ServiceRecordController {

    @Autowired
    private ServiceRecordService serviceRecordService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'VOLUNTEER')")
    public ResponseEntity<ServiceRecordResponseDTO> create(@Valid @RequestBody ServiceRecordRequestDTO requestDTO) {
        ServiceRecordResponseDTO response = serviceRecordService.create(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/immigrant/{immigrantId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'VOLUNTEER')")
    public ResponseEntity<List<ServiceRecordResponseDTO>> findByImmigrant(@PathVariable Long immigrantId) {
        return ResponseEntity.ok(serviceRecordService.findByImmigrant(immigrantId));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'VOLUNTEER')")
    public ResponseEntity<ServiceRecordResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(serviceRecordService.findById(id));
    }
}