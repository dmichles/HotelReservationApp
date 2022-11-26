package com.example.hotels.data.repository;

import com.example.hotels.data.model.Reservation;
import com.example.hotels.data.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation,Long> {
    List<Reservation> findReservationsByRoom(Room room);





}
