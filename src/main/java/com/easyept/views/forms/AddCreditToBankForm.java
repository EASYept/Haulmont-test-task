package com.easyept.views.forms;

import com.easyept.entity.Bank;
import com.easyept.entity.Credit;
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
 * Form for changing which Credits are mapped to Bank
 * API ->   1.Create form
 *          2.call setGridItemsAndBank(List<Credit> creditList, Bank bank)
 *          (where creditList is credits which already mapped to bank, and credits which have bank as null)
 */
public class AddCreditToBankForm extends VerticalLayout {

    private Bank bank   = new Bank();

    Button save         = new Button("Save");
    Button cancel       = new Button("Cancel");

    Grid<Credit> grid   = new Grid<>(Credit.class);

    public AddCreditToBankForm() {
        add(
                new HorizontalLayout(save, cancel),
                grid
        );

        grid.setSelectionMode(Grid.SelectionMode.MULTI);

        save.addClickListener(e -> {
            Set<Credit> creditSet = grid.asMultiSelect().getSelectedItems();
            bank.setCredits(creditSet);
            fireEvent(new AddCreditToBankForm.SaveEvent(this, bank));
        });
        cancel.addClickListener(e -> fireEvent(new AddCreditToBankForm.CloseEvent(this)));
    }

    /**
     * Populate grid with passed Credits.
     * And then selecting Credits which are already mapped to Bank
     * @param creditList
     * @param bank
     */
    public void setGridItemsAndBank(List<Credit> creditList, Bank bank) {
        setGridCredits(creditList);
        setBank(bank);
    }

    private void setBank(Bank bank) {
        if (bank == null) {
            bank = new Bank();
        }
        this.bank = bank;
        for (Credit credit: this.bank.getCredits()) {
            grid.select(credit);
        }
    }

    private void setGridCredits(List<Credit> creditList) {
        grid.setItems(creditList);
    }

    // Events (took from https://vaadin.com/docs/v14/flow/tutorials/in-depth-course/forms-and-validation?utm_campaign=Java%20Web%20App%20Tutorial&utm_source=youtube&utm_medium=social)
    public static abstract class AddCreditToBankFormEvent extends ComponentEvent<AddCreditToBankForm> {
        private Bank bank;

        protected AddCreditToBankFormEvent(AddCreditToBankForm source, Bank bank) {
            super(source, false);
            this.bank = bank;
        }

        public Bank getBank() {
            return bank;
        }
    }

    public static class SaveEvent extends AddCreditToBankForm.AddCreditToBankFormEvent {
        SaveEvent(AddCreditToBankForm source, Bank bank) {
            super(source, bank);
        }
    }

    public static class CloseEvent extends AddCreditToBankForm.AddCreditToBankFormEvent {
        CloseEvent(AddCreditToBankForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }

}
