package com.example.hotels.service;

import com.example.hotels.data.model.Hotel;
import com.example.hotels.data.repository.HotelRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelService {
    private HotelRepository repository;

    public HotelService(HotelRepository repository) {
        this.repository = repository;
    }

    public List<Hotel> findAllHotels(String stringFilter){
        if (stringFilter == null || stringFilter.isEmpty()){
            return repository.findAll();
        } else {
            return repository.search(stringFilter);
        }
    }
}
