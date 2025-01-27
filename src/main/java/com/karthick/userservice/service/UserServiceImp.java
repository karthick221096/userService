package com.karthick.userservice.service;

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
public class UserServiceImp implements UserService{

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;

    public UserServiceImp(BCryptPasswordEncoder bCryptPasswordEncoder,
                          UserRepository userRepository,
                          TokenRepository tokenRepository){
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
    }
    @Override
    public User signUp(String name, String email, String password) {

        Optional<User> userOptional = userRepository.findByEmail(email);

        if(userOptional.isPresent()){
            //TODO throw UserAlreadyPresent Exception
            return null;
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
        if(userOptional.isEmpty()){
            //TODO throw user not found exception
            return null;
        }
        User user = userOptional.get();
        if(!bCryptPasswordEncoder.matches(password,user.getHashedPassword())){
            //TODO throw password not matching exception
            return null;
        }
        Token token = createToken(user);
        return tokenRepository.save(token);
    }

    @Override
    public User validate(String token) {
        Optional<Token> tokenOptional = tokenRepository.findByValueAndDeletedAndExpiryAtGreaterThan(
                token,
                false,
                new Date());

        if(tokenOptional.isEmpty()){
            //TODO throw token invalid exception
            return null;
        }

        Token validatedToken = tokenOptional.get();
        return validatedToken.getUser();
    }

    @Override
    public void logout(String tokenValue) {
        Optional<Token> tokenOptional = tokenRepository.findByValueAndDeleted(tokenValue,false);
        if(tokenOptional.isEmpty()){
            //TODO throw token not found exception
        }

        Token token = tokenOptional.get();
        token.setDeleted(true);
        tokenRepository.save(token);
    }

    private Token createToken(User user){
        Token token = new Token();
        token.setValue(RandomStringUtils.randomAlphanumeric(128));
        token.setUser(user);

        Date today = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.add(Calendar.DAY_OF_MONTH,30);
        Date dateAfter30Days = calendar.getTime();

        token.setExpiryAt(dateAfter30Days);
        token.setDeleted(false);
        return token;
    }
}
