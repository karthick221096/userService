package com.karthick.userservice.security.service;

import com.karthick.userservice.model.User;
import com.karthick.userservice.repository.UserRepository;
import com.karthick.userservice.security.model.UserDetailsImp;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Optional;

@Service
public class UserDetailsServiceImp implements UserDetailsService {

    private UserRepository userRepository;

    public UserDetailsServiceImp(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        com.karthick.userservice.model.User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("user email not found"));

        List<SimpleGrantedAuthority> authorities = user.getRoles() == null
                ? List.of()
                : user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getRole()))
                .toList();

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getHashedPassword(), authorities);
    }


}
