package com.skp.hotel.service.impl;

import com.skp.hotel.dto.RatingReportDto;
import com.skp.hotel.model.Hotel;
import com.skp.hotel.service.HotelService;
import com.skp.hotel.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class DefaultRatingService implements RatingService {
  private final HotelService hotelService;

  @Autowired
  DefaultRatingService(HotelService hotelService) {
    this.hotelService = hotelService;
  }

  @Override
  public RatingReportDto getRatingAverage(Long cityId) {
    return getRatingAverage(hotelService.getHotelsByCity(cityId));
  }

  @Override
  public RatingReportDto getRatingAverage(List<Hotel> hotels) {
    double ratingSum = 0;
    int ratingCount = 0;

    for (Hotel hotel : hotels) {
      ratingSum += hotel.getRating();
      ratingCount++;
    }

    return new RatingReportDto(ratingCount, ratingSum / ratingCount);
  }
}
