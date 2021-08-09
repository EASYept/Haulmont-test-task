package com.easyept.views;

import com.easyept.service.ClientService;
import com.easyept.views.UI.SkeletOfApp;
import com.easyept.entity.Client;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.vaadin.crudui.crud.impl.GridCrud;

@Route(value = "/clients", layout = SkeletOfApp.class)
public class ClientView extends VerticalLayout {

    private final ClientService     clientService;
    private final GridCrud<Client>  crud = new GridCrud<>(Client.class);

    public ClientView(ClientService clientService) {
        this.clientService = clientService;
        gridConfig(crud);
        add(crud);
        setSizeFull();
    }

    private void gridConfig(GridCrud<Client> crud) {
        crud.setFindAllOperation(clientService::findAll);
        crud.setAddOperation(clientService::save);
        crud.setUpdateOperation(clientService::update);
        crud.setDeleteOperation(clientService::delete);

        crud.getGrid().setColumns(
                "uuid",
                "firstName",
                "lastName",
                "patronymic",
                "phoneNumber",
                "email",
                "passportNumber");
    }


}
