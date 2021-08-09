package com.easyept.views;

import com.easyept.entity.Bank;
import com.easyept.service.ClientService;
import com.easyept.views.UI.SkeletOfApp;
import com.easyept.service.BankService;
import com.easyept.service.CreditService;
import com.easyept.views.forms.AddClientToBankForm;
import com.easyept.views.forms.AddCreditToBankForm;
import com.easyept.views.forms.BankCreateForm;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;


@Route(value = "/banks", layout = SkeletOfApp.class)
public class BankView extends AbstractView {

    private final BankService   bankService;
    private final ClientService clientService;
    private final CreditService creditService;

    private final BankCreateForm      bankCreateForm    = new BankCreateForm();
    private final AddClientToBankForm clientsInBank     = new AddClientToBankForm();
    private final AddCreditToBankForm creditsInBank     = new AddCreditToBankForm();

    protected Grid<Bank>    grid            = new Grid<Bank>(Bank.class);
    protected Button        addClientButton = new Button("Add/Remove client in Bank");
    protected Button        addCreditButton = new Button("Add/Remove credit in Bank");

    public BankView(BankService bankService,
                    ClientService clientService,
                    CreditService creditService) {
        this.bankService = bankService;
        this.clientService = clientService;
        this.creditService = creditService;
        setSizeFull();

        bankCreateFormSettings(bankCreateForm);
        clientsInBankSettings(clientsInBank);
        creditsInBankSettings(creditsInBank);


        add(
                bankCreateForm,
                clientsInBank,
                creditsInBank,
                addButtonLayout()
        );
        //add additional buttons
        buttonAndGridLayout
                .add(new HorizontalLayout(addClientButton, addCreditButton));
        //add grid under buttons
        buttonAndGridLayout.add(grid);

        gridConfiguration();

        //Buttons Listeners
        addClientButton.addClickListener(e -> showAddClientForm(true));
        addCreditButton.addClickListener(e -> showAddCreditForm(true));

        setEnableButtons(false);
        updateGridItems();
    }

    /* Grid columns re-build, Grid single select listener */
    private void gridConfiguration() {
        grid.removeAllColumns();
        grid.addColumn(uuid -> uuid.getUuid().toString()).setHeader("uuid");
        grid.addColumn(Bank::getBankName).setHeader("BankName");
        grid.addColumn(Bank::getClients).setHeader("Clients");
        grid.addColumn(Bank::getCredits).setHeader("Credits");

        grid.asSingleSelect().addValueChangeListener(e -> {
            if (e.getValue() == null) {
                setEnableButtons(false);
            } else {
                setEnableButtons(true);
            }
        });
    }

    /* FORMS SETTINGS (Listeners, setVisible) */

    private void bankCreateFormSettings(BankCreateForm bankCreateForm) {
        bankCreateForm.addListener(
                BankCreateForm.SaveEvent.class, this::saveBank);
        bankCreateForm.addListener(
                BankCreateForm.CloseEvent.class, this::cancel);
        bankCreateForm.setVisible(false);
    }

    private void clientsInBankSettings(AddClientToBankForm clientsInBank) {
        clientsInBank.addListener(
                AddClientToBankForm.SaveEvent.class, this::updateBank);
        clientsInBank.addListener(
                AddClientToBankForm.CloseEvent.class, this::cancel);
        clientsInBank.setVisible(false);
    }

    private void creditsInBankSettings(AddCreditToBankForm creditsInBank) {
        creditsInBank.addListener(
                AddCreditToBankForm.SaveEvent.class, this::updateBank);
        creditsInBank.addListener(
                AddCreditToBankForm.CloseEvent.class, this::cancel);
        creditsInBank.setVisible(false);
    }

    /* SAVE EVENTS methods handlers */

    private void saveBank(BankCreateForm.SaveEvent event) {
        bankService.save(event.getBank());
        bankCreateForm.setVisible(false);
        updateGridItems();
    }

    private void updateBank(AddClientToBankForm.SaveEvent event) {
        bankService.save(event.getBank());
        showAddClientForm(false);
        updateGridItems();
    }

    private void updateBank(AddCreditToBankForm.SaveEvent event) {
        bankService.save(event.getBank());
        showAddCreditForm(false);
        updateGridItems();
    }

    /* CLOSE EVENTS */

    private void cancel(BankCreateForm.CloseEvent event){
        bankCreateForm.setVisible(false);
    }

    private void cancel(AddClientToBankForm.CloseEvent event){
        showAddClientForm(false);
    }

    private void cancel(AddCreditToBankForm.CloseEvent event){
        showAddCreditForm(false);
    }

    /* BUTTONS ACTIONS */

    @Override
    protected void updateGridItems() {
        grid.setItems(bankService.findAll());
    }

    @Override
    protected void addButtonClicked() {
        bankCreateForm.setBank(new Bank());
        bankCreateForm.setVisible(true);
    }

    @Override
    protected void updateButtonClicked() {
        bankCreateForm.setBank(grid.asSingleSelect().getValue());
        bankCreateForm.setVisible(true);
    }

    @Override
    protected void deleteButtonClicked() {
        bankService.delete(grid.asSingleSelect().getValue());
        updateGridItems();
    }

    /* DISPLAY METHODS */

    /*
     *   Disable or enable buttons:
     *       updateButton
     *       deleteButton
     *       addClientButton
     *       addCreditButton
     */
    private void setEnableButtons(boolean bool) {
        updateButton.setEnabled(bool);
        deleteButton.setEnabled(bool);
        addClientButton.setEnabled(bool);
        addCreditButton.setEnabled(bool);
    }

    /* Show form for add/remove credits */
    private void showAddCreditForm(boolean bool) {
        Bank tempBank = grid.asSingleSelect().getValue();
        if (bool) {
            creditsInBank.setGridItemsAndBank(
                    creditService.findByBankUuid(tempBank.getUuid()),
                    tempBank);
        }
        creditsInBank.setVisible(bool);
        buttonAndGridLayout.setVisible(!bool);
    }

    /* Show form for add/remove clients */
    private void showAddClientForm(boolean bool) {
        Bank tempBank = grid.asSingleSelect().getValue();
        if (bool) {
            clientsInBank.setGridItemsAndBank(
                    clientService.findByBankUuid(tempBank.getUuid()),
                    tempBank);
        }
        clientsInBank.setVisible(bool);
        buttonAndGridLayout.setVisible(!bool);
    }



}
