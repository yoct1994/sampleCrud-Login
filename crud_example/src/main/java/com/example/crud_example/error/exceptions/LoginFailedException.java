package com.example.crud_example.error.exceptions;

import com.example.crud_example.error.exception.CustomException;
import com.example.crud_example.error.exception.ErrorCode;

public class LoginFailedException extends CustomException {
    public LoginFailedException() {
        super(ErrorCode.LOGIN_FAILED);
    }
}
