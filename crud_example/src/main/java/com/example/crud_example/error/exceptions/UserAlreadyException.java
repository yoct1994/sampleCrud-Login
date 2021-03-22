package com.example.crud_example.error.exceptions;

import com.example.crud_example.error.exception.CustomException;
import com.example.crud_example.error.exception.ErrorCode;

public class UserAlreadyException extends CustomException {
    public UserAlreadyException() {
        super(ErrorCode.ALREADY_USER_SIGNED);
    }
}
