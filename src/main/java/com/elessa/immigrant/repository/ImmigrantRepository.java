package com.elessa.immigrant.repository;

import com.elessa.immigrant.enums.ImmigrantStatus;
import com.elessa.immigrant.model.Immigrant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImmigrantRepository extends JpaRepository<Immigrant, Long> {

    // Buscar imigrantes por status (com paginação)
    Page<Immigrant> findByStatus(ImmigrantStatus status, Pageable pageable);

    // Buscar por nacionalidade (com paginação)
    Page<Immigrant> findByNationality(String nationality, Pageable pageable);

    // Buscar por nome (contém, ignorando maiúsculas/minúsculas)
    List<Immigrant> findByFullNameContainingIgnoreCase(String fullName);

    // Verificar se documento já existe (para evitar duplicação)
    boolean existsByDocumentNumber(String documentNumber);
}