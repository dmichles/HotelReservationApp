package com.example.hotels.data.repository;

import com.example.hotels.data.model.Hotel;
import com.example.hotels.data.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room,Long> {
    List<Room> findRoomsByHotel(Hotel hotel);
}
