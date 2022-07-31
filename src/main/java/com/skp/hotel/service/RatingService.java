package com.skp.hotel.service;

import com.skp.hotel.dto.RatingReportDto;
import com.skp.hotel.model.Hotel;

import java.util.List;

public interface RatingService {
    RatingReportDto getRatingAverage(Long cityId);

    RatingReportDto getRatingAverage(List<Hotel> hotels);
}
