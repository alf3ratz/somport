package com.hse.somport.somport.view;

import com.hse.somport.somport.view.enums.WsVideoStatuses;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;

@Tag("canvas")
@JsModule("./src/ws-video-player.js")
public class WsVideoPlayer extends Component {

    private final String streamId;


    public WsVideoPlayer(String streamId) {
        this.streamId = streamId;
        getElement().getStyle().set("width", "640px");
        getElement().getStyle().set("height", "480px");
        getElement().getStyle().set("background", "#000");
        getElement().getStyle().set("display", "block");
    }

    @ClientCallable
    private void wsStatus(String status, String details) {
        var enumStatus = WsVideoStatuses.valueOf(status);
        var n = Notification.show(enumStatus.getDescription());

        if (enumStatus.equals(WsVideoStatuses.CLOSED)) {
            n.addThemeVariants(NotificationVariant.LUMO_CONTRAST);
        } else {
            n.addThemeVariants(NotificationVariant.LUMO_ERROR);
        }
        n.setDuration(10000);
        n.setPosition(Notification.Position.TOP_END);
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        getElement().executeJs(
                """
                                const wsUrl = (window.location.protocol === 'https:' ? 'wss://' : 'ws://')
                                            + 'localhost:8081' + '/ws/' + $0;
                                        console.log('Opening WS', wsUrl);
                                        this.__wsHandle = window.WsVideoViewer.start(this, wsUrl);
                        """,
                streamId
        );
    }

    @Override
    protected void onDetach(DetachEvent detachEvent) {
        getElement().executeJs("if (this.__wsHandle) { this.__wsHandle.stop(); this.__wsHandle = null; }");
    }
}
