package com.hse.somport.somport.view;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route(value = "", layout = MainLayout.class)
@PageTitle("Hello, Vaadin!")
@AnonymousAllowed
public class MainView extends VerticalLayout {

    public MainView() {
        add(new H1("Hello ssss"));
    }

}
