package com.example.firstexp.service.implementation;

import com.example.firstexp.exceptions.CustomException;
import com.example.firstexp.model.dto.DriverDTO;
import com.example.firstexp.model.entity.Driver;
import com.example.firstexp.model.enums.Status;
import com.example.firstexp.model.repository.DriverRepository;
import com.example.firstexp.service.DriverService;
import com.example.firstexp.utils.PaginationUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import javax.mail.internet.InternetAddress;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DriverServiceImplementation implements DriverService {
    private final DriverRepository driverRepository;
    private final ObjectMapper mapper;

    @Override
    public DriverDTO createDriver(DriverDTO driverDTO) {
        try {
            InternetAddress emailAddr = new InternetAddress(driverDTO.getEmail());
            emailAddr.validate();
        } catch (Exception ex) {
            throw new CustomException("Невалидный email", HttpStatus.BAD_REQUEST);
        }
        driverRepository.findByEmailAndStatus(driverDTO.getEmail(), Status.A).ifPresent(
                driver -> {
                    throw new CustomException("Водитель с таким email уже существует", HttpStatus.BAD_REQUEST);
                }
        );
        Driver driver = mapper.convertValue(driverDTO, Driver.class);
        driver.setStatus(Status.A);
        Driver save = driverRepository.save(driver);
        return mapper.convertValue(save, DriverDTO.class);
    }

    @Override
    public DriverDTO update(DriverDTO driverDTO) {
        Driver driver = getDriver(driverDTO.getEmail());
        //Historical records
        Driver driverHist = new Driver();
        driverHist.setName(driver.getName());
        driverHist.setEmail(driver.getEmail());
        driverHist.setSurname(driver.getSurname());
        driverHist.setPrev(driver.getId());
        driverHist.setStatus(Status.I);
        driverHist.setUpdatedAt(driver.getUpdatedAt());
        driverRepository.save(driverHist);
        //----
        driver.setName(driverDTO.getName());
        driver.setSurname(driverDTO.getSurname());
        driver.setUpdatedAt(LocalDateTime.now());
        driverRepository.save(driver);
        return mapper.convertValue(driver, DriverDTO.class);
    }

    @Override
    public DriverDTO get(String email) {
        return mapper.convertValue(getDriver(email), DriverDTO.class);
    }

    @Override
    public void delete(String email) {
        Driver driver = getDriver(email);
        driver.setStatus(Status.C);
        driver.setUpdatedAt(LocalDateTime.now());
        driverRepository.save(driver);
    }

    @Override
    public Driver getDriver(String email) {
        return driverRepository.
                findByEmailAndStatus(email, Status.A).
                orElseThrow(() -> new CustomException("Водитель с таким email не найден", HttpStatus.NOT_FOUND));
    }

    @Override
    public ModelMap getDriversPaged(Integer page, Integer perPage, String sort, Sort.Direction order) {
        Pageable pageRequest = PaginationUtil.getPageRequest(page, perPage, sort, order);
        Page<Driver> pageResult;
        pageResult = driverRepository.findByStatus(Status.A, pageRequest);
        List<DriverDTO> content = pageResult.getContent()
                .stream()
                .map(driver -> mapper.convertValue(driver, DriverDTO.class))
                .collect(Collectors.toList());

        PagedListHolder<DriverDTO> result = new PagedListHolder<>(content);
        result.setPage(page);
        result.setPageSize(perPage);
        ModelMap map = new ModelMap();
        map.addAttribute("content", result.getPageList());
        map.addAttribute("pageNumber", page);
        map.addAttribute("pageSize", result.getPageSize());
        map.addAttribute("totalPages", pageResult.getTotalPages());
        return map;
    }
}
