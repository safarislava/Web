package ru.ifmo.se.api.common.dto.user;

public enum MessageType {
    REGISTER_REQUEST,
    LOGIN_REQUEST,
    SYNC_REQUEST,
    REFRESH_REQUEST,
    LOGOUT_REQUEST,
    PARSE_REQUEST,
    GET_ROLES_REQUEST,
    SUCCESS_RESPONSE,
    ERROR_RESPONSE
}
