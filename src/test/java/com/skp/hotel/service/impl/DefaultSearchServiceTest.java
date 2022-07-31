package com.skp.hotel.service.impl;

import com.skp.hotel.model.Hotel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class DefaultSearchServiceTest {

    @Autowired
    DefaultSearchService searchService;

    @Test
    @DisplayName("When hotels have ratings then the average is correctly calculated")
    public void listTheHotelsNearToCityCenter() {
        List<Hotel> hotelList = searchService.getAllHotelByCity(1L);

    }
}
