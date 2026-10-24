package com.karthick.userservice.dtos;

import com.karthick.userservice.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpResponseDto {
    private User user;
}
