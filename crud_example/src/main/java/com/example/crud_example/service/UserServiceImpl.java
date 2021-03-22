package com.example.crud_example.service;

import com.example.crud_example.entity.user.User;
import com.example.crud_example.entity.user.repository.UserRepository;
import com.example.crud_example.error.exceptions.UserAlreadyException;
import com.example.crud_example.payload.request.SignUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Override
    public void signUp(SignUpRequest signUpRequest) {
        User user = userRepository.findById(signUpRequest.getId())
                .orElse(null);

        if(user != null) {
            throw new UserAlreadyException();
        }

        userRepository.save(
                User.builder()
                        .id(signUpRequest.getId())
                        .name(signUpRequest.getName())
                        .password(signUpRequest.getPassword())
                        .build()
        );
    }
}
