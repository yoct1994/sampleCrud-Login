package com.example.crud_example.service;

import com.example.crud_example.payload.request.SignUpRequest;

public interface UserService {
    void signUp(SignUpRequest signUpRequest);
}
