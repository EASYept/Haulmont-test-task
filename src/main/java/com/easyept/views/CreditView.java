package com.easyept.views;

import com.easyept.entity.Credit;
import com.easyept.service.CreditService;
import com.easyept.views.UI.SkeletOfApp;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.vaadin.crudui.crud.impl.GridCrud;

@Route(value = "/credit", layout = SkeletOfApp.class)
public class CreditView extends VerticalLayout {

    private final CreditService     creditService;
    private final GridCrud<Credit>  grid = new GridCrud<>(Credit.class);

    public CreditView(CreditService creditService) {
        this.creditService = creditService;
        gridConfig(grid);
        add(grid);
        setSizeFull();
    }

    private void gridConfig(GridCrud<Credit> crud) {
        crud.setFindAllOperation(creditService::findAll);
        crud.setAddOperation(creditService::save);
        crud.setUpdateOperation(creditService::update);
        crud.setDeleteOperation(creditService::delete);

        crud.getGrid().setColumns("uuid", "creditLimit", "creditRate");
    }


}
