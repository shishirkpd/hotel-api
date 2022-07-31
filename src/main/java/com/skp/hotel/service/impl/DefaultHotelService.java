package com.skp.hotel.service.impl;

import com.skp.hotel.exception.BadRequestException;
import com.skp.hotel.model.Hotel;
import com.skp.hotel.repository.HotelRepository;
import com.skp.hotel.service.HotelService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
class DefaultHotelService implements HotelService {
  private final HotelRepository hotelRepository;

  private List<Hotel> logicallyDeleteHotel = new ArrayList<>();

  @Autowired
  DefaultHotelService(HotelRepository hotelRepository) {
    this.hotelRepository = hotelRepository;
  }

  @Override
  public List<Hotel> getAllHotels() {
    return hotelRepository.findAll().stream().filter(x -> !logicallyDeleteHotel.contains(x)).collect(Collectors.toList());
  }

  @Override
  public List<Hotel> getHotelsByCity(Long cityId) {
    return getAllHotels().stream()
        .filter((hotel) -> cityId.equals(hotel.getCity().getId()))
        .collect(Collectors.toList());
  }

  @Override
  public Hotel createNewHotel(Hotel hotel) {
    if (hotel.getId() != null) {
      throw new BadRequestException("The ID must not be provided when creating a new Hotel");
    }

    return hotelRepository.save(hotel);
  }

  @Override
  public Hotel getHotelsById(Long id) throws NotFoundException {
    Optional<Hotel> optionalHotel = hotelRepository.findById(id).filter(x -> !logicallyDeleteHotel.contains(x));
    if(optionalHotel.isPresent() && optionalHotel.orElseGet(null) != null)
      return optionalHotel.get();
    else
      throw new BadRequestException("The ID provided for hotel does not exist id:" + id);
  }

  @Override
  public void deleteHotelsById(Long hotelId) throws NotFoundException {
    Optional<Hotel> optionalHotel = Optional.ofNullable(getHotelsById(hotelId));
    if(optionalHotel != null) {
     logicallyDeleteHotel.add(optionalHotel.get());
   }
  }
}
