package com.example.crud_example.service;

import com.example.crud_example.entity.token.Token;
import com.example.crud_example.entity.token.repository.TokenRepository;
import com.example.crud_example.entity.user.User;
import com.example.crud_example.entity.user.repository.UserRepository;
import com.example.crud_example.jwts.JwtProvider;
import com.example.crud_example.payload.request.SignInRequest;
import com.example.crud_example.payload.response.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;

    private final JwtProvider jwtProvider;

    @Override
    public TokenResponse getUserToken(SignInRequest signInRequest) {
        return userRepository.findById(signInRequest.getId())
                .filter(user -> user.getPassword().equals(signInRequest.getPassword()))
                .map(User::getUserId)
                .map(userId -> {
                    String accessToken = jwtProvider.generateAccessToken(userId);
                    String refreshToken = jwtProvider.generateAccessToken(userId);
                    tokenRepository.save(
                            Token.builder()
                            .userId(userId)
                            .refreshToken(refreshToken)
                            .build()
                    );
                    return TokenResponse.builder()
                            .accessToken(accessToken)
                            .refreshToken(refreshToken)
                            .build();
                })
                .orElseThrow(RuntimeException::new);
    }

    @Override
    public TokenResponse refreshToken(String oldToken) {
        if(jwtProvider.isRefreshToken(oldToken))
            throw new RuntimeException();

        return tokenRepository.findByRefreshToken(oldToken)
                .map(token -> {
                    String newRefreshToken = jwtProvider.generateRefreshToken(token.getUserId());
                    return token.update(newRefreshToken);
                })
                .map(tokenRepository::save)
                .map(token -> {
                    String newAccessToken = jwtProvider.generateAccessToken(token.getUserId());
                    return TokenResponse.builder()
                            .accessToken(newAccessToken)
                            .refreshToken(token.getRefreshToken())
                            .build();
                })
                .orElseThrow(RuntimeException::new);
    }
}
