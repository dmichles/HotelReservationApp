package com.example.hotels.view;

import com.example.hotels.component.customfield.CustomField;
import com.example.hotels.data.model.Hotel;
import com.example.hotels.data.model.Reservation;
import com.example.hotels.data.model.Room;
import com.example.hotels.data.repository.HotelRepository;
import com.example.hotels.data.repository.ReservationRepository;
import com.example.hotels.data.repository.RoomRepository;
import com.example.hotels.service.ReservationService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;


import javax.annotation.security.PermitAll;

@PermitAll
@Route(value = "room", layout = MainLayout.class)
@PageTitle("Rooms")
public class RoomView extends VerticalLayout implements HasUrlParameter<Long> {
    private RoomRepository roomRepository;
    private HotelRepository hotelRepository;
    private ReservationService reservationService;
    private Hotel hotel;
    private Grid<Room> rooms = new Grid<>(Room.class);
    private H5 hotelName = new H5();
    CustomField customField;
    Room room;


    public RoomView(RoomRepository roomRepository, HotelRepository hotelRepository,
                    ReservationService reservationService) {
        this.roomRepository = roomRepository;
        this.hotelRepository = hotelRepository;
        this.reservationService = reservationService;
        configureCustomField();
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        configureGrid();
        add(hotelName, getContent());
    }

    public void configureGrid() {
        rooms.addClassNames("hotel-grid");
        rooms.setWidthFull();
        rooms.setSizeFull();
        rooms.setColumns("id", "room_type", "room_price");
        rooms.getColumns().forEach(col -> col.setAutoWidth(true));
        rooms.asSingleSelect().addValueChangeListener(event -> {
            customField.edit(event.getValue(), hotel.getName());
            this.room = event.getValue();
        });
    }

    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(rooms, customField);
        content.setFlexGrow(2, rooms);
        content.setFlexGrow(1, customField);
        content.addClassNames("content");
        content.setSizeFull();
        return content;
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, Long aLong) {
        hotel = hotelRepository.findById(aLong).get();
        hotelName.setText(hotel.getName());
        rooms.setItems(roomRepository.findRoomsByHotel(hotel));
    }

    private void configureCustomField() {
        customField = new CustomField();
        customField.setWidth("25em");
        customField.addListener(CustomField.SaveEvent.class, this::saveBook);

    }

    private void saveBook(CustomField.SaveEvent event) {
        if (reservationService.saveReservation(event.getReservation(), room)) {
            String notificationText = "Booked " + room.getRoom_type() + " room from " +
                    event.getReservation().getStartDate() + " to " + event.getReservation().getEndDate()
                    + " at " + hotelName.getText() + " hotel";
            Notification.show(notificationText, 4000, Notification.Position.BOTTOM_START);
            getUI().ifPresent(ui -> ui.navigate(""));
        } else {
            Notification.show("Room is unavailable during this time period");
        }
    }
}
