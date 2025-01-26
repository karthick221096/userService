package com.karthick.userservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;

import java.util.Date;

@Entity
public class Token extends BaseModel{
    private String value;
    @OneToOne
    private User user;
    private Date expiryAt;
}
