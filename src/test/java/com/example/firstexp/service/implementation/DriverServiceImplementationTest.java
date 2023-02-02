package com.example.firstexp.service.implementation;

import com.example.firstexp.exceptions.CustomException;
import com.example.firstexp.model.dto.DriverDTO;
import com.example.firstexp.model.entity.Driver;
import com.example.firstexp.model.enums.Status;
import com.example.firstexp.model.repository.DriverRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DriverServiceImplementationTest {

    @InjectMocks
    private DriverServiceImplementation driverServiceImpl;

    @Mock
    private DriverRepository driverRepository;

    @Spy
    private ObjectMapper mapper;

    @Test
    public void createDriver() {
        DriverDTO driverDTO = new DriverDTO();
        driverDTO.setEmail("123@123.ru");
        when(driverRepository.findByEmailAndStatus(anyString(), any(Status.class))).thenReturn(Optional.empty());
        when(driverRepository.save(any(Driver.class))).thenAnswer(i -> i.getArguments()[0]);
        DriverDTO res = driverServiceImpl.createDriver(driverDTO);
        assertEquals(res.getEmail(), driverDTO.getEmail());
    }

    @Test(expected = CustomException.class)
    public void createDriverEx() {
        DriverDTO driverDTO = new DriverDTO();
        driverDTO.setEmail("123@123.ru");
        when(driverRepository.findByEmailAndStatus(anyString(), any(Status.class))).thenReturn(Optional.of(new Driver()));
        driverServiceImpl.createDriver(driverDTO);
    }

    @Test(expected = CustomException.class)
    public void createDriverEx2() {
        DriverDTO driverDTO = new DriverDTO();
        driverDTO.setEmail("12323.ru");
        driverServiceImpl.createDriver(driverDTO);
    }

    @Test
    public void update() {
        DriverDTO driverDTO = new DriverDTO();
        driverDTO.setEmail("123@123.ru");
        driverDTO.setName("Test");
        Driver driver = mapper.convertValue(driverDTO, Driver.class);
        driver.setName("ForUpdate");
        when(driverRepository.findByEmailAndStatus(anyString(), any(Status.class))).thenReturn(Optional.of(driver));
        when(driverRepository.save(any(Driver.class))).thenAnswer(i -> i.getArguments()[0]);
        DriverDTO result = driverServiceImpl.update(driverDTO);
        assertEquals(result.getName(), driverDTO.getName());
    }

    @Test
    public void get() {
        Driver driver = new Driver();
        driver.setEmail("11@11.ru");
        when(driverRepository.findByEmailAndStatus(anyString(), any(Status.class))).thenReturn(Optional.of(driver));
        DriverDTO driverDTO = driverServiceImpl.get("11@11.ru");
        assertEquals(driver.getEmail(), driverDTO.getEmail());
    }

    @Test
    public void delete() {
        Driver driver = new Driver();
        driver.setEmail("11@11.ru");
        when(driverRepository.findByEmailAndStatus(anyString(), any(Status.class))).thenReturn(Optional.of(driver));
        when(driverRepository.save(any(Driver.class))).thenReturn(driver);
        driverServiceImpl.delete("11@11.ru");
    }

    @Test
    public void getDriver() {
        when(driverRepository.findByEmailAndStatus(anyString(), any(Status.class))).thenReturn(Optional.of(new Driver()));
        driverServiceImpl.getDriver("123");
    }

    @Test(expected = CustomException.class)
    public void getDriverEx() {
        when(driverRepository.findByEmailAndStatus(anyString(), any(Status.class))).thenReturn(Optional.empty());
        driverServiceImpl.getDriver("123");
    }
}