package com.example.hotels.view;

import com.example.hotels.security.UserDetailsServiceImpl;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.VaadinSession;


public class MainLayout extends AppLayout  {
    private final UserDetailsServiceImpl userDetailsService;
    public MainLayout(UserDetailsServiceImpl userDetailsService) {
        VaadinSession.getCurrent().getSession().setMaxInactiveInterval(600);
        this.userDetailsService = userDetailsService;
        createHeader();
        createDrawer();
    }

    private void createHeader() {
        H1 logo = new H1("Hotel Reservations");
        logo.addClassNames("text-l", "m-m");

        Button logout = new Button("Log out", e -> userDetailsService.logout());
        HorizontalLayout header = new HorizontalLayout(
                new DrawerToggle(),
                logo,logout
        );

        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.expand(logo);
        header.setWidth("100%");
        header.addClassNames("py-0", "px-m");

        addToNavbar(header);

    }

    private void createDrawer() {
        RouterLink listLink = new RouterLink("Hotels", HotelView.class);
        listLink.setHighlightCondition(HighlightConditions.sameLocation());

        addToDrawer(new VerticalLayout(
                listLink));
    }


}
