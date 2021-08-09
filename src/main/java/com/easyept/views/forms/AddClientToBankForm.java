package com.easyept.views.forms;

import com.easyept.entity.Bank;
import com.easyept.entity.Client;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.shared.Registration;

import java.util.List;
import java.util.Set;

/**
 * Form for changing which Clients are mapped to Bank
 * API ->   1.Create form
 *          2.call setGridItemsAndBank(List<Client> clientList, Bank bank)
 *          (where clientList is clients which already mapped to bank, and clients which have bank as null)
 */
public class AddClientToBankForm extends VerticalLayout {

    private Bank bank   = new Bank();

    Button save         = new Button("Save");
    Button cancel       = new Button("Cancel");

    Grid<Client> grid   = new Grid<>(Client.class);

    public AddClientToBankForm() {
        add(
                new HorizontalLayout(save, cancel),
                grid
        );

        grid.setSelectionMode(Grid.SelectionMode.MULTI);

        save.addClickListener(e -> {
            Set<Client> clientSet = grid.asMultiSelect().getSelectedItems();
            bank.setClients(clientSet);
            fireEvent(new AddClientToBankForm.SaveEvent(this, bank));
        });
        cancel.addClickListener(e -> fireEvent(new AddClientToBankForm.CloseEvent(this)));
    }

    /**
     * Populate grid with passed Clients.
     * And then selecting Clients which are already mapped to Bank
     * @param clientList
     * @param bank
     */
    public void setGridItemsAndBank(List<Client> clientList, Bank bank) {
        setGridClients(clientList);
        setBank(bank);
    }

    private void setBank(Bank bank) {
        if (bank == null) {
            bank = new Bank();
        }
        this.bank = bank;
        for (Client client: this.bank.getClients()) {
            grid.select(client);
        }
    }

    private void setGridClients(List<Client> clientList) {
        grid.setItems(clientList);
    }

    // Events (took from https://vaadin.com/docs/v14/flow/tutorials/in-depth-course/forms-and-validation?utm_campaign=Java%20Web%20App%20Tutorial&utm_source=youtube&utm_medium=social)
    public static abstract class AddClientToBankFormEvent extends ComponentEvent<AddClientToBankForm> {
        private Bank bank;

        protected AddClientToBankFormEvent(AddClientToBankForm source, Bank bank) {
            super(source, false);
            this.bank = bank;
        }

        public Bank getBank() {
            return bank;
        }
    }

    public static class SaveEvent extends AddClientToBankForm.AddClientToBankFormEvent {
        SaveEvent(AddClientToBankForm source, Bank bank) {
            super(source, bank);
        }
    }

    public static class CloseEvent extends AddClientToBankForm.AddClientToBankFormEvent {
        CloseEvent(AddClientToBankForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }

}
