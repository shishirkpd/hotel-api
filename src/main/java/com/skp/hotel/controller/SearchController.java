package com.skp.hotel.controller;

import com.skp.hotel.model.Hotel;
import com.skp.hotel.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/search")
public class SearchController {
    private final SearchService searchService;

    @Autowired
    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/city/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<Hotel> getAllHotelByCity(@PathVariable Long id) {
        return searchService.getAllHotelByCity(id);
    }
}