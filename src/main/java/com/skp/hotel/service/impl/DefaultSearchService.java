package com.skp.hotel.service.impl;

import com.skp.hotel.model.City;
import com.skp.hotel.model.Hotel;
import com.skp.hotel.service.CityService;
import com.skp.hotel.service.HotelService;
import com.skp.hotel.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DefaultSearchService implements SearchService {

    private final HotelService hotelService;
    private final CityService cityService;

    @Autowired
    DefaultSearchService(CityService cityService, HotelService hotelService){
        this.hotelService = hotelService;
        this.cityService = cityService;
    }
    @Override
    public List<Hotel> getAllHotelByCity(Long id) {
        Map<Hotel, Double> hotelDoubleMap = new HashMap<>();

        City city = cityService.getCityById(id);
        List<Hotel> hotelsByCity = hotelService.getHotelsByCity(id);
        for (Hotel hotel: hotelsByCity) {
            hotelDoubleMap.put(hotel, distance(city, hotel));
        }

        return hotelDoubleMap.entrySet().stream()
                        .sorted(Map.Entry.comparingByValue()).map(k -> k.getKey()).collect(Collectors.toList());
    }

    public static double distance(City city, Hotel hotel)
    {

        // The math module contains a function
        // named toRadians which converts from
        // degrees to radians.
        double lon1 = Math.toRadians(city.getCityCentreLongitude());
        double lat1 = Math.toRadians(city.getCityCentreLatitude());
        double lon2 = Math.toRadians(hotel.getLongitude());
        double lat2 = Math.toRadians(hotel.getLongitude());

        // Haversine formula
        double dlon = lon2 - lon1;
        double dlat = lat2 - lat1;
        double a = Math.pow(Math.sin(dlat / 2), 2)
                + Math.cos(lat1) * Math.cos(lat2)
                * Math.pow(Math.sin(dlon / 2),2);

        double c = 2 * Math.asin(Math.sqrt(a));

        // Radius of earth in kilometers. Use 3956
        // for miles
        double r = 6371;

        // calculate the result
        return(c * r);
    }
}
