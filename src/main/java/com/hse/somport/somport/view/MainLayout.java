package com.hse.somport.somport.view;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@AnonymousAllowed
public class MainLayout extends AppLayout {

    public MainLayout() {
        Tabs tabs = new Tabs(
                createTab("Главная", MainView.class),
                createTab("Конфигурация корма", FeedConfigView.class),
                createTab("Трансляция", VideoStreamView.class)

        );

        addToNavbar(tabs);
    }

    private Tab createTab(String label, Class<? extends Component> navigationTarget) {
        RouterLink link = new RouterLink(label, navigationTarget);
        link.setTabIndex(-1); // чтобы фокус был на Tab, а не на ссылке
        Tab tab = new Tab(link);
        return tab;
    }
}
