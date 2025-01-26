package com.karthick.userservice.service;

import com.karthick.userservice.model.Token;
import com.karthick.userservice.model.User;

public interface UserService {
    public User signUp(String name , String email, String password);
    public Token login(String email, String password);
    public User validate(String token);
    public void logout(String token);
}
