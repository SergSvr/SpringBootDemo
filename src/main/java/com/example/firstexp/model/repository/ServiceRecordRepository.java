package com.example.firstexp.model.repository;

import com.example.firstexp.model.entity.ServiceRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface ServiceRecordRepository extends JpaRepository<ServiceRecord, Long> {
}
