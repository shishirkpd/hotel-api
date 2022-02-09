package com.booking.recruitment.hotel.service;

import com.booking.recruitment.hotel.model.Hotel;
import javassist.NotFoundException;

import java.util.List;

public interface HotelService {
  List<Hotel> getAllHotels();

  List<Hotel> getHotelsByCity(Long cityId);

  Hotel createNewHotel(Hotel hotel);

  Hotel getHotelsById(Long id) throws NotFoundException;

  void deleteHotelsById(Long hotelId) throws NotFoundException;
}
