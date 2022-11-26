package com.example.hotels.service;

import com.example.hotels.data.model.Reservation;
import com.example.hotels.data.model.Room;
import com.example.hotels.data.repository.ReservationRepository;
import com.vaadin.flow.component.notification.Notification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {
    private ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public boolean saveReservation(Reservation reservation, Room room) {
        if (!checkOverlappingReservation(reservation, room)) {
            reservationRepository.save(reservation);
            return true;
        } else {
            return false;
        }
    }

    private boolean checkOverlappingReservation(Reservation reservation, Room room) {
        List<Reservation> reservations = reservationRepository.findReservationsByRoom(room);

        for(Reservation res: reservations) {
            if(reservation.getStartDate().isAfter(res.getStartDate()) &&
            reservation.getStartDate().isBefore(res.getEndDate())) {
                return true;
            } else if (reservation.getStartDate().isEqual(res.getStartDate())) {
                return true;
            } else if (reservation.getEndDate().isAfter(res.getStartDate()) &&
            reservation.getEndDate().isBefore(res.getEndDate())) {
                return true;
            } else if (reservation.getEndDate().isEqual(res.getEndDate())) {
                return true;
            } else if (reservation.getStartDate().isBefore(res.getStartDate()) &&
            reservation.getEndDate().isAfter(res.getEndDate())) {
                return true;
            }
        }
        return false;
    }
}
