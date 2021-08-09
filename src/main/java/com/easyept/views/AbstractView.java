package com.easyept.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

/**
 * FIXME poorly designed abstract (but at least i'm tried... right?)
 * Abstract class which provides view for reusability
 * View:
 *      4 Buttons(refresh, add, update, delete) with icons in layout
 *      abstract click listener methods for each button
 *      Layout where to put Grid of items
 *
 */
public abstract class AbstractView extends VerticalLayout {

    VerticalLayout buttonAndGridLayout = new VerticalLayout();
    Button refreshButton;
    Button addButton;
    Button updateButton;
    Button deleteButton;

    public AbstractView() {
        super();
        setSizeFull();
    }

    protected VerticalLayout addButtonLayout() {
        buttonAndGridLayout.add(buttonLayout());
        return buttonAndGridLayout;
    }

    /* adding all buttons and clickListeners */
    protected HorizontalLayout buttonLayout() {
        HorizontalLayout buttonLayout = new HorizontalLayout();

        refreshButton = new Button(VaadinIcon.REFRESH.create(),
                event -> updateGridItems());
        addButton = new Button(VaadinIcon.PLUS.create(),
                event -> addButtonClicked());
        updateButton = new Button(VaadinIcon.PENCIL.create(),
                event -> updateButtonClicked());
        deleteButton = new Button(VaadinIcon.TRASH.create(),
                event -> deleteButtonClicked());

        buttonLayout.add(refreshButton, addButton, updateButton, deleteButton);
        return buttonLayout;
    }

    //Buttons
    protected abstract void updateGridItems();

    protected abstract void addButtonClicked();

    protected abstract void updateButtonClicked();

    protected abstract void deleteButtonClicked();



}


