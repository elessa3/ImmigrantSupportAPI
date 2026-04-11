package com.elessa.immigrant.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceRecordResponseDTO {
    private Long id;
    private String description;
    private LocalDateTime serviceDate;
    private String volunteerName;     // Nome do voluntário que atendeu
    private String immigrantName;     // Nome do imigrante atendido
    private Long immigrantId;
}