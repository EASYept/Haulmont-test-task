package com.easyept.views.forms;

import com.easyept.entity.Client;
import com.easyept.entity.Credit;
import com.easyept.entity.CreditOffer;
import com.easyept.util.CreditPaymentUtil;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.converter.StringToFloatConverter;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.shared.Registration;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class CreditOfferForm extends VerticalLayout {

    Binder<CreditOffer> binder = new Binder<>(CreditOffer.class);
    // The object that will be edited
    CreditOffer offer = new CreditOffer();

    // Create fields
    DatePicker          date                    = new DatePicker("Date of payment");
    ComboBox<Client>    client                  = new ComboBox<>("Client");
    ComboBox<Credit>    credit                  = new ComboBox<>("Credit");
    TextField           fieldAmount             = new TextField("Amount");
    TextField           fieldMonths             = new TextField("Months");
    //Buttons
    Button              createPaymentSchedule   = new Button("Create Payment Schedule");
    Button              saveOffer               = new Button("Create Offer");
    Button              cancel                  = new Button("Cancel");

    Span                percentsRepay           = new Span();
    Grid<CreditOffer.Payment> grid              = new Grid<>(CreditOffer.Payment.class);

    //Constructor
    public CreditOfferForm(List<Client> allClients, List<Credit> allCredits) {
        setSizeFull();
        add(
                fields(),
                buttonLayout(),
                percentsRepay,
                grid
        );

        grid.setColumns("date", "paymentAmount", "bodyPayment", "percentPayment");

        setClientsAndCredits(allClients, allCredits);
        binderSettings(offer);
        buttonsSettings();
    }

    private void buttonsSettings() {
        saveOffer.setThemeName("primary");
        saveOffer.addClickListener( click -> bindToBeanAndSave());

        cancel.setThemeName("danger");
        cancel.addClickListener(click -> fireEvent(new CreditOfferForm.CloseEvent(this)));

        createPaymentSchedule.addClickListener(click -> generatePaymentSchedule());
    }

    private HorizontalLayout buttonLayout() {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.add(
                createPaymentSchedule,
                saveOffer,
                cancel
        );
        return buttonLayout;
    }

    private HorizontalLayout fields() {
        HorizontalLayout fields = new HorizontalLayout();
        fields.add(
                client,
                credit,
                fieldAmount,
                fieldMonths,
                date
        );
        client.setItemLabelGenerator(Client::fullNameToString);
        credit.setItemLabelGenerator(Credit::creditNameToString);

        return fields;
    }

    //validation on fields
    private void binderSettings(CreditOffer offer) {
        binder.setBean(offer);

        binder.forField(fieldAmount)
                .asRequired()
                .withConverter(new StringToFloatConverter("Must enter the number with float point"))
                .withValidator(value ->
                        value < ((credit.getValue() != null)
                            ? (credit.getValue().getCreditLimit())
                            : 0),
                        "Should be less then credit Limit")
                .withValidator(value -> value > 0 , "Should be more then zero")
                .bind(CreditOffer::getAmount,
                    CreditOffer::setAmount);
        binder.forField(fieldMonths)
                .asRequired()
                .withConverter(new StringToIntegerConverter("Must enter the whole number"))
                .withValidator(value -> value > 0 , "Should be more then zero")
                .bind(CreditOffer::getMonths,
                        CreditOffer::setMonths);
        binder.forField(date)
                .asRequired()
                .withValidator(value -> value.isAfter(LocalDate.now().minusDays(1)) , "Must be in future or today")
                .bind(CreditOffer::getStartingDate,
                        CreditOffer::setStartingDate);
        binder.forField(client)
                .asRequired()
                .withValidator(client -> client != null , "Choose someone")
                .bind(CreditOffer::getClient, CreditOffer::setClient);
        binder.forField(credit)
                .asRequired()
                .withValidator(credit -> credit != null , "Choose any credit")
                .bind(CreditOffer::getCredit, CreditOffer::setCredit);
    }

    private void bindToBeanAndSave() {
        try {
            binder.writeBean(offer);
            binder.readBean(offer);
            fireEvent(new SaveEvent(this, offer));
        } catch (ValidationException e) {
            showNotificationError();
        }
    }

    private void showNotificationError() {
        Notification notification = new Notification(
                "You forgot something to fill", 4000);
        notification.setPosition(Notification.Position.MIDDLE);
        notification.open();
    }

    private void generatePaymentSchedule() {
        try {
            binder.writeBean(offer);
            List<CreditOffer.Payment> schedule = offer.getPaymentSchedule();
            grid.setItems(schedule);
            BigDecimal summ = new BigDecimal(0);
            //Show total percents
            for (CreditOffer.Payment payment :schedule){
                summ = summ.add(BigDecimal.valueOf(payment.getPercentPayment())
                        .setScale(CreditPaymentUtil.OUTPUT_AMOUNT_SCALE,
                                CreditPaymentUtil.ROUNDING_MODE));
            }
            percentsRepay.setText("Total percents repay: "+ summ);

        } catch (ValidationException e) {
            showNotificationError();
        }
    }

    public void setOffer(CreditOffer creditOffer) {
            this.offer = creditOffer;
            binder.readBean(offer);
    }

    private void setClientsAndCredits(List<Client> allClients, List<Credit> allCredits) {
        client.setItems(allClients);
        credit.setItems(allCredits);
    }

    // Events (took from https://vaadin.com/docs/v14/flow/tutorials/in-depth-course/forms-and-validation?utm_campaign=Java%20Web%20App%20Tutorial&utm_source=youtube&utm_medium=social)
    public static abstract class CreditOfferFormEvent extends ComponentEvent<CreditOfferForm> {
        private CreditOffer creditOffer;

        protected CreditOfferFormEvent(CreditOfferForm source, CreditOffer creditOffer) {
            super(source, false);
            this.creditOffer = creditOffer;
        }

        public CreditOffer getCreditOffer() {
            return creditOffer;
        }
    }

    public static class SaveEvent extends CreditOfferFormEvent {
        SaveEvent(CreditOfferForm source, CreditOffer creditOffer) {
            super(source, creditOffer);
        }
    }

    public static class CloseEvent extends CreditOfferFormEvent {
        CloseEvent(CreditOfferForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }




}
