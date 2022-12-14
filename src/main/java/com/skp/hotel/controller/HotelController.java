package com.skp.hotel.controller;

import com.skp.hotel.model.Hotel;
import com.skp.hotel.service.HotelService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotel")
public class HotelController {
  private final HotelService hotelService;

  @Autowired
  public HotelController(HotelService hotelService) {
    this.hotelService = hotelService;
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<Hotel> getAllHotels() {
    return hotelService.getAllHotels();
  }

  @GetMapping("/{hotelId}")
  @ResponseStatus(HttpStatus.OK)
  public Hotel getHotelsById(@PathVariable Long hotelId) throws NotFoundException {
    return hotelService.getHotelsById(hotelId);
  }

  @DeleteMapping("/{hotelId}")
  @ResponseStatus(HttpStatus.OK)
  public void deleteHotelsById(@PathVariable Long hotelId) throws NotFoundException {
    hotelService.deleteHotelsById(hotelId);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Hotel createHotel(@RequestBody Hotel hotel) {
    return hotelService.createNewHotel(hotel);
  }
}
