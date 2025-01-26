package com.karthick.userservice.service;

import com.karthick.userservice.model.Token;
import com.karthick.userservice.model.User;
import com.karthick.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImp implements UserService{

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private final UserRepository userRepository;

    public UserServiceImp(BCryptPasswordEncoder bCryptPasswordEncoder,
                          UserRepository userRepository){
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
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
        return null;
    }

    @Override
    public User validate(String token) {
        return null;
    }

    @Override
    public void logout(String token) {

    }
}
