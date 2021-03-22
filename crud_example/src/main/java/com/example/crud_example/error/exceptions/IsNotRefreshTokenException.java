package com.example.crud_example.error.exceptions;

import com.example.crud_example.error.exception.CustomException;
import com.example.crud_example.error.exception.ErrorCode;

public class IsNotRefreshTokenException extends CustomException {
    public IsNotRefreshTokenException() {
        super(ErrorCode.NOT_REFRESH_TOKEN);
    }
}
