package com.hse.somport.somport.view;

import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route(value = "video/1", layout = MainLayout.class)
@PageTitle("Video Stream")
@AnonymousAllowed
public class VideoStreamView extends VerticalLayout {

    public VideoStreamView() {
        add(new WsVideoPlayer("1"));
        setAlignItems(FlexComponent.Alignment.CENTER);
        setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
    }

}
