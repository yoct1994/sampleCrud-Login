package com.example.crud_example.service;

import com.example.crud_example.payload.request.SignInRequest;
import com.example.crud_example.payload.response.TokenResponse;

import java.util.Optional;

public interface AuthService {
    TokenResponse getUserToken(SignInRequest signInRequest);
    TokenResponse refreshToken(String oldToken);
}
