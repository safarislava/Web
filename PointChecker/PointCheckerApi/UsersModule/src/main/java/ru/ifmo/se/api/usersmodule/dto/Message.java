package ru.ifmo.se.api.usersmodule.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Message {
    private MessageType messageType;
    private Object payload;

    public Message() {}
}
