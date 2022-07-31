package com.skp.hotel.controller;

import com.skp.hotel.model.City;
import com.skp.hotel.model.Hotel;
import com.skp.hotel.repository.CityRepository;
import com.skp.hotel.repository.HotelRepository;
import com.testing.SlowTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:data.sql")
@SlowTest
class HotelControllerTest {
  @Autowired private MockMvc mockMvc;
  @Autowired private ObjectMapper mapper;

  @Autowired private HotelRepository repository;
  @Autowired private CityRepository cityRepository;

  @Test
  @DisplayName("When all hotels are requested then they are all returned")
  void allHotelsRequested() throws Exception {
    mockMvc
        .perform(get("/hotel"))
        .andExpect(status().is2xxSuccessful())
        .andExpect(jsonPath("$", hasSize((int) repository.count())));
  }

  @Test
  @DisplayName("Find hotel by id requested then  hotel returned")
  void hotelByIdRequested() throws Exception {
    mockMvc
        .perform(get("/hotel/"+1))
        .andExpect(status().is2xxSuccessful())
        .andExpect(content().string("{\"id\":1,\"name\":\"Monaghan Hotel\",\"rating\":9.2,\"city\":{\"id\":1,\"name\":\"Amsterdam\",\"cityCentreLatitude\":52.36878,\"cityCentreLongitude\":4.903303},\"address\":\"Weesperbuurt en Plantage\",\"latitude\":52.364799,\"longitude\":4.908971,\"deleted\":false}"));
  }

  @Test
  @DisplayName("delete hotel by id requested")
  void deleteHotelByIdRequested() throws Exception {
    mockMvc
        .perform(delete("/hotel/"+2))
        .andExpect(status().is2xxSuccessful());

    mockMvc
        .perform(get("/hotel/"+2))
        .andExpect(status().is4xxClientError());
  }

  @Test
  @DisplayName("When a hotel creation is requested then it is persisted")
  void hotelCreatedCorrectly() throws Exception {
    City city =
        cityRepository
            .findById(1L)
            .orElseThrow(
                () -> new IllegalStateException("Test dataset does not contain a city with ID 1!"));
    Hotel newHotel = Hotel.builder().setName("This is a test hotel").setCity(city).build();

    Long newHotelId =
        mapper
            .readValue(
                mockMvc
                    .perform(
                        post("/hotel")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapper.writeValueAsString(newHotel)))
                    .andExpect(status().isCreated())
                    .andReturn()
                    .getResponse()
                    .getContentAsString(),
                Hotel.class)
            .getId();

    newHotel.setId(newHotelId); // Populate the ID of the hotel after successful creation

    assertThat(
        repository
            .findById(newHotelId)
            .orElseThrow(
                () -> new IllegalStateException("New Hotel has not been saved in the repository")),
        equalTo(newHotel));
  }
}
