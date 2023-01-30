package com.example.firstexp.service.implementation;

import com.example.firstexp.exceptions.CustomException;
import com.example.firstexp.model.dto.ServiceRecordDTO;
import com.example.firstexp.model.entity.ServiceRecord;
import com.example.firstexp.model.entity.Vehicle;
import com.example.firstexp.model.repository.ServiceRecordRepository;
import com.example.firstexp.model.repository.VehicleRepository;
import com.example.firstexp.service.ServiceRecordService;
import com.example.firstexp.service.VehicleService;
import com.example.firstexp.utils.PaginationUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ServiceRecordServiceImpl implements ServiceRecordService {
    private final VehicleRepository vehicleRepository;
    private final ServiceRecordRepository serviceRecordRepository;
    private final VehicleService vehicleService;

    @Override
    public ServiceRecord createRecord(ServiceRecordDTO serviceRecordDTO) {
        Vehicle vehicle = vehicleService.getVehicle(serviceRecordDTO.getVin());
        ServiceRecord serviceRecord1 = new ServiceRecord();
        serviceRecord1.setDate(serviceRecordDTO.getDate());
        serviceRecord1.setOdometer(serviceRecordDTO.getOdometer());
        serviceRecord1.setOperations(serviceRecordDTO.getOperations());
        serviceRecord1.setVehicleId(vehicle.getId());
        vehicle.getServiceRecord().add(serviceRecord1);
        Vehicle save=vehicleRepository.save(vehicle);
        List<ServiceRecord> serviceRecord = save.getServiceRecord();
        return serviceRecord.get(serviceRecord.size()-1);
    }

    @Override
    public ServiceRecord update(ServiceRecord serviceRecord) {
        ServiceRecord temp = serviceRecordRepository.findById(serviceRecord.getId())
                .orElseThrow(() -> new CustomException("Исходная запись не найдена", HttpStatus.NOT_FOUND));
        temp.setDate(serviceRecord.getDate());
        temp.setOdometer(serviceRecord.getOdometer());
        serviceRecordRepository.save(temp);
        return (temp);
    }

    @Override
    public List<ServiceRecord> getRecords(String vin) {
        Vehicle vehicle = vehicleService.getVehicle(vin);
        return vehicle.getServiceRecord();
    }

    public Page<ServiceRecord> getRecordsPaged(String vin, Integer page, Integer perPage, String sort, Sort.Direction order) {
        Pageable pageRequest = PaginationUtil.getPageRequest(page, perPage, sort, order);
        Vehicle vehicle = vehicleService.getVehicle(vin);
        serviceRecordRepository.findAllByVehicleId(vehicle.getId(), pageRequest);
        return serviceRecordRepository.findAllByVehicleId(vehicle.getId(), pageRequest);
    }

    @Override
    public void delete(Long id) {
        try{
        serviceRecordRepository.deleteById(id);}
        catch (Exception ex){
            throw new CustomException("Исходная запись не найдена", HttpStatus.NOT_FOUND);
        }
    }
}
