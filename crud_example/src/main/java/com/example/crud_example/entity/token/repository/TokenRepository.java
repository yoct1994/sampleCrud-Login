package com.example.crud_example.entity.token.repository;

import com.example.crud_example.entity.token.Token;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TokenRepository extends CrudRepository<Token, Integer> {
    Optional<Token> findByRefreshToken(String refreshToken);
}
