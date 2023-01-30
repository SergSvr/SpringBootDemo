package com.example.firstexp.model.repository;

import com.example.firstexp.model.entity.ServiceRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface ServiceRecordRepository extends JpaRepository<ServiceRecord, Long> {
    Optional<ServiceRecord> findById(Long id);

    Page<ServiceRecord> findAllByVehicleId(Long id, Pageable pageable);
}
