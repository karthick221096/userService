package com.karthick.userservice.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class User extends BaseModel{
    private String email;
    private String password;
    private List<Role> roles;
}
