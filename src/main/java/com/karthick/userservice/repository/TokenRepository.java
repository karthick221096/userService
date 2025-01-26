package com.karthick.userservice.repository;

import com.karthick.userservice.model.Token;
import com.karthick.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

    Optional<Token> findByValueAndDeletedAndExpiryAtGreaterThan(String value, Boolean deleted, Date expiry);

    Optional<Token> findByValueAndDeleted(String value, Boolean deleted);
}