package com.example.crud_example.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    EXPIRE_TOKEN(403, "token expired"),
    INVALID_TOKEN(403, "token invalid"),
    USER_NOT_FOUND(403, "user not found"),
    LIST_NOT_FOUND(404, "list is empty"),
    LOGIN_FAILED(404, "login failed"),
    LIST_IMAGE_NOT_FOUND(404, "image not found"),
    NOT_REFRESH_TOKEN(404, "is not refreshToken"),
    ALREADY_USER_SIGNED(409, "user is already signed");

    private int status;
    private String message;
}
