package com.hse.somport.somport.view.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum WsVideoStatuses {
    ERROR("Ошибка подключения"),
    CONNECTED("Успешно подключено"),
    CLOSED("Соединение закрыто");

    private final String description;
}
