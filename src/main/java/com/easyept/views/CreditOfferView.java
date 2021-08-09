package com.easyept.views;

import com.easyept.entity.CreditOffer;
import com.easyept.service.ClientService;
import com.easyept.views.UI.SkeletOfApp;
import com.easyept.views.forms.CreditOfferForm;
import com.easyept.service.CreditOfferService;
import com.easyept.service.CreditService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.Route;

@Route(value = "/creditOffer", layout = SkeletOfApp.class)
public class CreditOfferView extends AbstractView {

    private final CreditOfferService creditOfferService;

    private final Grid<CreditOffer> grid = new Grid<>(CreditOffer.class);
    private final CreditOfferForm   form;

    public CreditOfferView(CreditOfferService creditOfferService,
                           ClientService clientService,
                           CreditService creditService) {
        super();
        this.creditOfferService = creditOfferService;

        /* creating form */
        form = new CreditOfferForm(clientService.findAll(),
                                    creditService.findAll());
        /* subscribe formEvents to EventBus(Vaadin feature) */
        form.addListener(CreditOfferForm.SaveEvent.class,
                        this::saveCreditOffer);
        form.addListener(CreditOfferForm.CloseEvent.class,
                        this::cancel);
        /* hide form */
        form.setVisible(false);

        /* add elements to this view */
        add(form, addButtonLayout());
        buttonAndGridLayout.add(grid);

        gridConfig();

        /* disable buttons because nothing is selected */
        updateButton.setEnabled(false);
        deleteButton.setEnabled(false);

        updateGridItems();
    }

    private void gridConfig() {
        grid.removeAllColumns();
        grid.addColumn(client -> client.getClient().fullNameToString())
                .setHeader("Client");
        grid.addColumn(credit -> credit.getCredit().creditNameToString())
                .setHeader("Credit");
        grid.addColumn(CreditOffer::getAmount).setHeader("Amount");
        grid.addColumn(CreditOffer::getMonths).setHeader("Months");
        grid.addColumn(CreditOffer::getStartingDate)
                .setHeader("Starting Date");
        grid.addColumn(CreditOffer::getUuid)
                .setHeader("uuid");
        grid.getColumns().forEach(column -> {
            column.setAutoWidth(true);
            column.setSortable(true);
        });

        grid.asSingleSelect().addValueChangeListener(item -> {
            if (item.getValue() == null) {
                updateButton.setEnabled(false);
                deleteButton.setEnabled(false);
            } else {
                updateButton.setEnabled(true);
                deleteButton.setEnabled(true);
            }
        });
    }

    /* EVENTS HANDLERS */

    private void saveCreditOffer(CreditOfferForm.SaveEvent event) {
        creditOfferService.save(event.getCreditOffer());
        showForm(false);
        form.setOffer(null);
        updateGridItems();
    }

    private void cancel(CreditOfferForm.CloseEvent event) {
        showForm(false);
    }

    /* BUTTONS ACTIONS */

    @Override
    protected void updateGridItems() {
        grid.setItems(creditOfferService.findAll());
    }

    @Override
    protected void addButtonClicked() {
        form.setOffer(new CreditOffer());
        showForm(true);
    }

    @Override
    protected void updateButtonClicked() {
        if (grid.asSingleSelect().getValue() != null) {
            form.setOffer(grid.asSingleSelect().getValue());
            showForm(true);
        }
    }

    @Override
    protected void deleteButtonClicked() {
        if (grid.asSingleSelect().getValue() != null) {
            creditOfferService.delete(grid.asSingleSelect().getValue());
            updateGridItems();
        }
    }

    protected void showForm(boolean bool){
        buttonAndGridLayout.setVisible(!bool);
        form.setVisible(bool);
    }


}
