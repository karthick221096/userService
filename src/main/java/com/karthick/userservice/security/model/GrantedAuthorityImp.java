package com.karthick.userservice.security.model;

import com.karthick.userservice.model.Role;
import org.springframework.security.core.GrantedAuthority;

public class GrantedAuthorityImp implements GrantedAuthority {

    private final String authority;

    public GrantedAuthorityImp (Role role){
        this.authority = role.getRole();
    }

    @Override
    public String getAuthority() {
        return authority;
    }
}
