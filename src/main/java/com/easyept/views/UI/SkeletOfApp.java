package com.easyept.views.UI;


import com.easyept.views.BankView;
import com.easyept.views.ClientView;
import com.easyept.views.CreditView;
import com.easyept.views.CreditOfferView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;


public class SkeletOfApp extends AppLayout {

    public SkeletOfApp() {
        createHeader();
        createDrawer();
    }

    private void createDrawer() {
        RouterLink clients = new RouterLink("Clients", ClientView.class);
        RouterLink credits = new RouterLink("Credits", CreditView.class);
        RouterLink creditOffer =
                new RouterLink("Credit Offers", CreditOfferView.class);
        RouterLink banks = new RouterLink("Banks", BankView.class);

        clients.setHighlightCondition(HighlightConditions.sameLocation());
        credits.setHighlightCondition(HighlightConditions.sameLocation());
        creditOffer.setHighlightCondition(HighlightConditions.sameLocation());
        banks.setHighlightCondition(HighlightConditions.sameLocation());

        addToDrawer(
                new VerticalLayout(
                        clients,
                        credits,
                        creditOffer,
                        banks
                )
        );
    }

    private void createHeader() {
        H2 logo = new H2("Haulmont Test-task");
        H3 by = new H3("by EASYept");
//        logo.setClassName("logo");
//        logo.setMaxHeight("30px");

        HorizontalLayout header =
                new HorizontalLayout(new DrawerToggle(), logo, by);
        header.setClassName("header");
        header.setWidth("100%");
        header.setDefaultVerticalComponentAlignment(
                FlexComponent.Alignment.CENTER);
        header.setMaxHeight("5em");

        addToNavbar(header);

    }



}
