package com.example.crud_example.error.exceptions;

import com.example.crud_example.error.exception.CustomException;
import com.example.crud_example.error.exception.ErrorCode;

public class ListNotFoundException extends CustomException {
    public ListNotFoundException() {
        super(ErrorCode.LIST_NOT_FOUND);
    }
}
