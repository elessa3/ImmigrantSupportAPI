package com.elessa.immigrant.dto;

import com.elessa.immigrant.enums.ImmigrantStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImmigrantResponseDTO {
    private Long id;
    private String fullName;
    private String nationality;
    private LocalDate birthDate;
    private String documentNumber;
    private ImmigrantStatus status;
    private String observations;
    private LocalDateTime registeredAt;
    private LocalDateTime lastUpdate;
    private Integer totalServiceRecords; // Quantos atendimentos já teve
}