package com.example.crud_example.controller;

import com.example.crud_example.payload.request.SignUpRequest;
import com.example.crud_example.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping
    public void signUp(@RequestBody SignUpRequest signUpRequest) {
        System.out.println(signUpRequest.getName());
        System.out.println(signUpRequest.getPassword());
        System.out.println(signUpRequest.getId());

        userService.signUp(signUpRequest);
    }
}
