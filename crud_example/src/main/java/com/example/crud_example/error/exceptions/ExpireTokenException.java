package com.example.crud_example.error.exceptions;

import com.example.crud_example.error.exception.CustomException;
import com.example.crud_example.error.exception.ErrorCode;

public class ExpireTokenException extends CustomException {
    public ExpireTokenException() {
        super(ErrorCode.EXPIRE_TOKEN);
    }
}
