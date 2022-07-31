package com.skp.hotel.service;

import com.skp.hotel.model.Hotel;

import java.util.List;

public interface SearchService {
    List<Hotel> getAllHotelByCity(Long id);
}
