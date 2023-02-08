package com.example.firstexp.controllers;

import com.example.firstexp.model.dto.ServiceRecordDTO;
import com.example.firstexp.model.entity.ServiceRecord;
import com.example.firstexp.service.ServiceRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/servicerecords")
@RequiredArgsConstructor
@Tag(name = "Сервисные Записи")
public class ServiceRecordConroller {
    private final ServiceRecordService servRecService;


    @PostMapping
    @Operation(summary = "создать сервисную запись")
    public ServiceRecord createRecord(@RequestBody ServiceRecordDTO serviceRecordDTO) {
        return servRecService.createRecord(serviceRecordDTO);
    }

    @PutMapping
    @Operation(summary = "обновить сервисную запись")
    public ResponseEntity<ServiceRecord> updateRecord(@RequestBody ServiceRecord serviceRecord) {
        return ResponseEntity.ok(servRecService.update(serviceRecord));
    }


    @GetMapping
    @Operation(summary = "получить сервисную запись")
    public List<ServiceRecord> getRecords(@RequestParam String vin) {
        return servRecService.getRecords(vin);
    }


    @GetMapping("/paged")
    @Operation(summary = "получить сервисную запись постранично")
    public List<ServiceRecord> getRecordsPaged(@RequestParam String vin,
                                               @RequestParam(required = false, defaultValue = "1") Integer page,
                                               @RequestParam(required = false, defaultValue = "10") Integer perPage,
                                               @RequestParam(required = false, defaultValue = "name") String sort,
                                               @RequestParam(required = false, defaultValue = "ASC") Sort.Direction order) {
        return servRecService.getRecordsPaged(vin, page, perPage,sort,order).getContent();
    }

    @DeleteMapping
    @Operation(summary = "удалить сервисную запись")
    public ResponseEntity<HttpStatus> deleteRecord(@RequestParam Long id) {
        servRecService.delete(id);
        return ResponseEntity.ok().build();
    }


}
