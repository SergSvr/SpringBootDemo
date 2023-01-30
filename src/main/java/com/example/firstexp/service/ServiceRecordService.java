package com.example.firstexp.service;

import com.example.firstexp.model.dto.ServiceRecordDTO;
import com.example.firstexp.model.entity.ServiceRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface ServiceRecordService {

    ServiceRecord createRecord(ServiceRecordDTO serviceRecordDTO);

    ServiceRecord update(ServiceRecord serviceRecord);

    List<ServiceRecord> getRecords(String vin);

    void delete(Long id);

    Page<ServiceRecord> getRecordsPaged(String vin, Integer page, Integer perPage, String sort, Sort.Direction order);
}
