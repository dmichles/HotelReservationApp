package com.example.hotels.component.customfield;


import com.example.hotels.data.model.Reservation;
import com.example.hotels.data.model.Room;
import com.example.hotels.service.ReservationService;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.shared.Registration;
import org.springframework.beans.factory.annotation.Autowired;


import javax.annotation.PostConstruct;
import javax.annotation.security.PermitAll;
import java.time.LocalDate;

@Route("custom")
@PermitAll
public class CustomField extends Div {
    private Room room;
    @Autowired
    public ReservationService service;
    private H1 roomName = new H1("No room chosen");

    DateRangePicker dateRangePicker = new DateRangePicker();

    private Reservation reservation = new Reservation();

    Button reserve = new Button("Reserve");
    Binder<Reservation> binder = new Binder();

    public CustomField() {

        //setSizeFull();
        dateRangePicker.setLabel("Reservation period");
        dateRangePicker.setEnabled(false);
        reserve.setEnabled(false);
        //dateRangePicker.setHelperText("Cannot be longer than 30 days");
        add(roomName, dateRangePicker, createButtonLayout());


    }

    @PostConstruct
    public void init() {
        System.out.println("test");
        bindToReservation();

    }

    public void bindToReservation() {

        binder.forField(dateRangePicker)
                .asRequired("Enter a start and end date")
                .withValidator(localDateRange -> localDateRange.getStartDate() != null
                || localDateRange.getEndDate() != null,"")
                .withValidator(
                        localDateRange -> (localDateRange.getStartDate() != null &&
                                localDateRange.getStartDate().isAfter(localDateRange.getToday()))
                                || (localDateRange.getStartDate() != null &&
                                localDateRange.getStartDate().isEqual(localDateRange.getToday())),
                        "Dates cannot be in the past")
                                .withValidator (localDateRange ->  localDateRange.getEndDate() != null &&
                                localDateRange.getEndDate().isAfter(localDateRange.getToday()),
                        "Dates cannot be in the past")
                .withValidator(
                        localDateRange ->  localDateRange.getEndDate() != null &&
                                localDateRange.getStartDate() != null &&
                                localDateRange.getStartDate().isBefore(localDateRange.getEndDate()),
                        "")
                .bind(reservation -> new LocalDateRange(
                                reservation.getStartDate(), reservation.getEndDate()),
                        (reservation, localDateRange) -> {
                            reservation.setStartDate(
                                    localDateRange.getStartDate());
                            reservation.setEndDate(localDateRange.getEndDate());

                        });

    }

    public void edit(Room room, String hotelName) {
        this.room = room;
        roomName.setText(room.getRoom_type() + " room");
        reservation.setRoom(room);
        dateRangePicker.setEnabled(true);
        bindToReservation();
    }

    public static abstract class CustomFieldEvent extends ComponentEvent<CustomField> {
        private Reservation reservation;

        protected CustomFieldEvent(CustomField source, Reservation reservation) {
            super(source, false);
            this.reservation = reservation;
        }

        public Reservation getReservation() {
            return reservation;
        }
    }

    public static class SaveEvent extends CustomFieldEvent {
        SaveEvent(CustomField source, Reservation reservation) {
            super(source, reservation);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }

    private com.vaadin.flow.component.Component createButtonLayout() {
        reserve.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        reserve.addClickShortcut(Key.ENTER);
        reserve.addClickListener(event -> validateAndSave());
        binder.addStatusChangeListener(e -> reserve.setEnabled(binder.isValid()));
        return new HorizontalLayout(reserve);
    }

    private void validateAndSave() {
        try {
            binder.writeBean(reservation);
            fireEvent(new SaveEvent(this, reservation));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }
}

