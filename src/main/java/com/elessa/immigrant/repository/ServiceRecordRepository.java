package com.elessa.immigrant.repository;

import com.elessa.immigrant.model.ServiceRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRecordRepository extends JpaRepository<ServiceRecord, Long> {

    // Buscar atendimentos por imigrante
    List<ServiceRecord> findByImmigrantId(Long immigrantId);

    // Buscar atendimentos por voluntário
    Page<ServiceRecord> findByUserId(Long userId, Pageable pageable);

    // Buscar atendimentos recentes de um imigrante (ordenado por data)
    List<ServiceRecord> findByImmigrantIdOrderByServiceDateDesc(Long immigrantId);
}