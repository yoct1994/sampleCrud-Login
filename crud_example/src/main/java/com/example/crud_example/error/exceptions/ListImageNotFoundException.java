package com.example.crud_example.error.exceptions;

import com.example.crud_example.error.exception.CustomException;
import com.example.crud_example.error.exception.ErrorCode;

public class ListImageNotFoundException extends CustomException {
    public ListImageNotFoundException() {
        super(ErrorCode.LIST_IMAGE_NOT_FOUND);
    }
}
