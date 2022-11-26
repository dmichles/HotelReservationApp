package com.example.hotels.data.repository;

import com.example.hotels.data.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel,Long> {

    @Query("select b from Hotel b where lower(b.city) like lower(concat('%', :searchTerm, '%'))")
    List<Hotel> search(@Param("searchTerm") String searchTerm);
}
