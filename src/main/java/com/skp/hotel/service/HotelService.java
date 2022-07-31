package com.skp.hotel.service;

import com.skp.hotel.model.Hotel;
import javassist.NotFoundException;

import java.util.List;

public interface HotelService {
  List<Hotel> getAllHotels();

  List<Hotel> getHotelsByCity(Long cityId);

  Hotel createNewHotel(Hotel hotel);

  Hotel getHotelsById(Long id) throws NotFoundException;

  void deleteHotelsById(Long hotelId) throws NotFoundException;
}
