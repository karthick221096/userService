package com.karthick.userservice.service;

import com.karthick.userservice.exceptions.InvalidPasswordException;
import com.karthick.userservice.exceptions.InvalidTokenException;
import com.karthick.userservice.exceptions.UserAlreadyExistsException;
import com.karthick.userservice.exceptions.UserNotFoundException;
import com.karthick.userservice.model.Token;
import com.karthick.userservice.repository.TokenRepository;
import com.karthick.userservice.model.User;
import com.karthick.userservice.repository.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@Service
public class UserServiceImp implements UserService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;

    public UserServiceImp(BCryptPasswordEncoder bCryptPasswordEncoder,
                          UserRepository userRepository,
                          TokenRepository tokenRepository) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
    }

    @Override
    public User signUp(String name, String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            throw new UserAlreadyExistsException("User with email " + email + " already exists");
        }

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setHashedPassword(bCryptPasswordEncoder.encode(password));
        return userRepository.save(user);
    }

    @Override
    public Token login(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("User with email " + email + " not found");
        }

        User user = userOptional.get();
        if (!bCryptPasswordEncoder.matches(password, user.getHashedPassword())) {
            throw new InvalidPasswordException("Incorrect password");
        }

        Token token = createToken(user);
        return tokenRepository.save(token);
    }

    @Override
    public User validate(String tokenValue) {
        Optional<Token> tokenOptional = tokenRepository.findByValueAndDeletedAndExpiryAtGreaterThan(
                tokenValue,
                false,
                new Date());

        if (tokenOptional.isEmpty()) {
            throw new InvalidTokenException("Token is invalid or expired");
        }

        return tokenOptional.get().getUser();
    }

    @Override
    public void logout(String tokenValue) {
        Optional<Token> tokenOptional = tokenRepository.findByValueAndDeleted(tokenValue, false);
        if (tokenOptional.isEmpty()) {
            throw new InvalidTokenException("Token not found or already logged out");
        }

        Token token = tokenOptional.get();
        token.setDeleted(true);
        tokenRepository.save(token);
    }

    private Token createToken(User user) {
        Token token = new Token();
        token.setValue(RandomStringUtils.randomAlphanumeric(128));
        token.setUser(user);

        Date today = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.add(Calendar.DAY_OF_MONTH, 30);
        token.setExpiryAt(calendar.getTime());
        token.setDeleted(false);
        return token;
    }
}
