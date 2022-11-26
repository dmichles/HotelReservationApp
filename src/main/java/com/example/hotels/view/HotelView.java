package com.example.hotels.view;

import com.example.hotels.data.model.Hotel;
import com.example.hotels.data.repository.HotelRepository;
import com.example.hotels.data.repository.RoomRepository;
import com.example.hotels.service.HotelService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.annotation.security.PermitAll;

@PermitAll
@Route(value = "",layout = MainLayout.class)
@PageTitle("Hotels")
public class HotelView extends VerticalLayout {
    private Grid<Hotel> hotels = new Grid<>(Hotel.class);
    private HotelRepository repo;
    private RoomRepository roomRepo;

    private HotelService service;
    private H5 header = new H5();

    private TextField filterText = new TextField();

    public HotelView(HotelRepository repo, RoomRepository roomRepo, HotelService service) {
        this.repo = repo;
        this.roomRepo = roomRepo;
        this.service = service;
        addClassName("hotel-view");
        header.setText("Hotels");
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        configureGrid();
        add(header, getToolbar(), getContent());
    }

    public void configureGrid() {
        hotels.addClassNames("hotel-grid");
        hotels.setSizeFull();
        hotels.setColumns("id", "city", "name", "stars");
        hotels.getColumns().forEach(col -> col.setAutoWidth(true));
        hotels.setItems(repo.findAll());

        hotels.asSingleSelect().addValueChangeListener(event -> getUI().
                ifPresent(ui -> ui.navigate(RoomView.class, event.getValue().getId())));

    }

    private Component getHeader() {
        H5 title = new H5();
        title.setText("HOTELS");
        Emphasis emphasis = new Emphasis(title);
        HorizontalLayout header = new HorizontalLayout(emphasis);
        header.expand(emphasis);
        header.setDefaultVerticalComponentAlignment(Alignment.CENTER);

        return header;
    }

    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(hotels);
        content.setFlexGrow(2, hotels);
        content.addClassNames("content");
        content.setSizeFull();
        return content;
    }

    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Filter by city...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());


        HorizontalLayout toolbar = new HorizontalLayout(filterText);
        toolbar.addClassName("toolbar");
        return toolbar;
    }
    private void updateList() {
        hotels.setItems(service.findAllHotels(filterText.getValue()));
    }
}
