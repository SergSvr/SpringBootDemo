package com.example.firstexp.controllers;

import com.example.firstexp.model.dto.VehicleDTO;
import com.example.firstexp.model.dto.VehicleTypeDTO;
import com.example.firstexp.model.enums.Manufacturer;
import com.example.firstexp.model.enums.VehicleClass;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.ConfigurableMockMvcBuilder;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
public class VehicleControllerTest {
    String uri = "/vehicles";

    VehicleDTO vehicle = new VehicleDTO();
    VehicleDTO vehicle1 = new VehicleDTO();

    {
        VehicleTypeDTO vehicleTypeDTO = new VehicleTypeDTO();
        vehicleTypeDTO.setManufacturer(Manufacturer.valueOf("FORD"));
        vehicleTypeDTO.setModel("MONDEO 3");
        vehicle.setVehicleType(vehicleTypeDTO);
        vehicle.setVehicleClass(VehicleClass.B);
        vehicle.setVin("ABCDABCD");
        vehicle1.setVehicleClass(VehicleClass.B);
        vehicle1.setVin("123123");
        vehicle1.setVehicleType(vehicle.getVehicleType());
    }

    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    ObjectMapper objectMapper;
    MockMvc mockMvc;

    @Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation("target/generated-snippets");

    @Before
    public void setUp() {
        ConfigurableMockMvcBuilder<DefaultMockMvcBuilder> builder =
                MockMvcBuilders.webAppContextSetup(this.webApplicationContext)
                        .alwaysDo(document(" {method-name}/{step}/"))
                        .apply(documentationConfiguration(this.restDocumentation));
        this.mockMvc = builder.build();
    }

    @Test
    @SneakyThrows
    public void createVehicle() {
        String content = objectMapper.writeValueAsString(vehicle);
        System.out.println(content);
        mockMvc.perform(post(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vin").value("ABCDABCD"))
                .andDo(document(uri.replace("/", "\\")));
    }

    @Test
    @SneakyThrows
    public void getVehicle() {
        mockMvc.perform(get(uri).param("vin", "123123")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vin").value("123123"));
    }

    @Test
    @SneakyThrows
    public void updateVehicle() {
        String content = objectMapper.writeValueAsString(vehicle1);
        System.out.println(content);
        mockMvc.perform(put(uri).contentType(MediaType.APPLICATION_JSON).content(content))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vin").value("123123"))
                .andExpect(jsonPath("$.vehicleClass").value("B"))
                .andDo(document(uri.replace("/", "\\")));
    }

    @Test
    @SneakyThrows
    public void deleteVehicle() {
        mockMvc.perform(delete(uri).param("vin", "12312345")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document(uri.replace("/", "\\")));
    }

    @Test
    @SneakyThrows
    public void addToDriver() {
        mockMvc.perform(post(uri + "/addtodriver").param("vin", "1231233").param("email", "123@123.ru")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document(uri.replace("/", "\\")));
    }

}
