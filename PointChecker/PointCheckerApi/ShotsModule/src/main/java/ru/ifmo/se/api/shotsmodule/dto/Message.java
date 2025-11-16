package ru.ifmo.se.api.shotsmodule.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Message {
    private MessageType messageType;
    private Long userId;
    private Object payload;
}
