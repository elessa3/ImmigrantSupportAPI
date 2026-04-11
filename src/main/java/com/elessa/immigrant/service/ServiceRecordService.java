package com.elessa.immigrant.service;

import com.elessa.immigrant.dto.ServiceRecordRequestDTO;
import com.elessa.immigrant.dto.ServiceRecordResponseDTO;
import com.elessa.immigrant.exception.ResourceNotFoundException;
import com.elessa.immigrant.model.Immigrant;
import com.elessa.immigrant.model.ServiceRecord;
import com.elessa.immigrant.model.User;
import com.elessa.immigrant.repository.ImmigrantRepository;
import com.elessa.immigrant.repository.ServiceRecordRepository;
import com.elessa.immigrant.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceRecordService {

    @Autowired
    private ServiceRecordRepository serviceRecordRepository;

    @Autowired
    private ImmigrantRepository immigrantRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public ServiceRecordResponseDTO create(ServiceRecordRequestDTO requestDTO) {
        // Buscar imigrante
        Immigrant immigrant = immigrantRepository.findById(requestDTO.getImmigrantId())
                .orElseThrow(() -> new ResourceNotFoundException("Imigrante não encontrado com ID: " + requestDTO.getImmigrantId()));

        // Pegar usuário logado (voluntário/admin)
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        // Criar atendimento
        ServiceRecord record = new ServiceRecord();
        record.setDescription(requestDTO.getDescription());
        record.setImmigrant(immigrant);
        record.setUser(user);

        ServiceRecord saved = serviceRecordRepository.save(record);
        return convertToResponseDTO(saved);
    }

    public List<ServiceRecordResponseDTO> findByImmigrant(Long immigrantId) {
        // Verificar se imigrante existe
        if (!immigrantRepository.existsById(immigrantId)) {
            throw new ResourceNotFoundException("Imigrante não encontrado com ID: " + immigrantId);
        }

        return serviceRecordRepository.findByImmigrantIdOrderByServiceDateDesc(immigrantId)
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public ServiceRecordResponseDTO findById(Long id) {
        ServiceRecord record = serviceRecordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Atendimento não encontrado com ID: " + id));
        return convertToResponseDTO(record);
    }

    private ServiceRecordResponseDTO convertToResponseDTO(ServiceRecord record) {
        return new ServiceRecordResponseDTO(
                record.getId(),
                record.getDescription(),
                record.getServiceDate(),
                record.getUser().getName(),
                record.getImmigrant().getFullName(),
                record.getImmigrant().getId()
        );
    }
}