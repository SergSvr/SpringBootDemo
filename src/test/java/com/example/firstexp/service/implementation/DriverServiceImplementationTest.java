package com.example.firstexp.service.implementation;

import com.example.firstexp.exceptions.CustomException;
import com.example.firstexp.model.dto.DriverDTO;
import com.example.firstexp.model.entity.Driver;
import com.example.firstexp.model.enums.Status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.firstexp.model.repository.DriverRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.ui.ModelMap;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
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

    @Test
    public void getDriversPaged() {
        Integer page=0;
        Integer perPage=3;
        String sort="id";
        Sort.Direction order= Sort.Direction.DESC;
        Driver[] drivers=new Driver[11];
        List<Driver> drivers1 = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            drivers[i]=new Driver();
            drivers[i].setName("Ivan"+i);
            drivers[i].setEmail("123@123.r"+i);
            drivers[i].setId((long) i+1);
            drivers[i].setSurname("Ivanov"+i);
            drivers[i].setStatus(Status.A);
            drivers1.add(drivers[i]);
        }
       Page<Driver> driverPage = new PageImpl<>(drivers1);
        when(driverRepository.findByStatus(any(Status.class),any(Pageable.class))).thenReturn(driverPage);
        ModelMap map =driverServiceImpl.getDriversPaged(page,perPage,sort,order);
        assertEquals(map.getAttribute("pageNumber"),page);
        @SuppressWarnings("unchecked") List<DriverDTO> drivers2=(List<DriverDTO>) map.get("content");
        System.out.println(drivers2);
        assertEquals(drivers1.get(1).getSurname(),drivers2.get(1).getSurname());
    }
}