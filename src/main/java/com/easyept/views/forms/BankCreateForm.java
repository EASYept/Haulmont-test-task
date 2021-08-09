package com.easyept.views.forms;

import com.easyept.entity.Bank;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

public class BankCreateForm extends VerticalLayout {

    private Binder<Bank> binder     = new Binder<>(Bank.class);
    private Bank bindedBank         = new Bank();

    TextField bankName              = new TextField("Bank name");
    HorizontalLayout buttonLayout   = new HorizontalLayout();

    Button save                     = new Button("Save");
    Button cancel                   = new Button("Cancel");

    public BankCreateForm() {
        add(bankName, buttonLayout);
        buttonLayout.add(save, cancel);

        setBinderForFields();
        saveButtonClickListener();
        cancelButtonClickListener();
    }

    private void setBinderForFields() {
        binder.setBean(bindedBank);
        binder.forField(bankName)
                .asRequired()
                .withValidator(v -> !v.isEmpty(), "Should not be empty" )
                .bind(Bank::getBankName,
                        Bank::setBankName);
    }

    private void cancelButtonClickListener() {
        cancel.addClickListener(e ->fireEvent(new CloseEvent(this)));
    }

    private void saveButtonClickListener() {
        save.addClickListener(click -> {
            try {
                binder.writeBean(bindedBank);
                binder.readBean(bindedBank);
                fireEvent(new SaveEvent(this, bindedBank));
            } catch (ValidationException e) {
                Notification notification = new Notification(
                        "Something went wrong", 4000);
                notification.setPosition(Notification.Position.MIDDLE);
                notification.open();
            }
        });
    }

    public void setBank(Bank bindedBank) {
        this.bindedBank = bindedBank;
        binder.readBean(bindedBank);
    }


// Events (took from https://vaadin.com/docs/v14/flow/tutorials/in-depth-course/forms-and-validation?utm_campaign=Java%20Web%20App%20Tutorial&utm_source=youtube&utm_medium=social)
public static abstract class BankFormEvent extends ComponentEvent<BankCreateForm> {
    private Bank bank;

    protected BankFormEvent(BankCreateForm source, Bank bank) {
        super(source, false);
        this.bank = bank;
    }

    public Bank getBank() {
        return bank;
    }
}

    public static class SaveEvent extends BankCreateForm.BankFormEvent {
        SaveEvent(BankCreateForm source, Bank bank) {
            super(source, bank);
        }
    }

    public static class CloseEvent extends BankCreateForm.BankFormEvent {
        CloseEvent(BankCreateForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
    
}
