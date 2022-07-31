package com.skp.hotel.service;

import com.skp.hotel.model.City;

import java.util.List;

public interface CityService {
  List<City> getAllCities();

  City getCityById(Long id);

  City createCity(City city);
}
