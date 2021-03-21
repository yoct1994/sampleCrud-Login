package com.example.crud_example.payload.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignUpRequest {
    private String name;
    private String id;
    private String password;
}
