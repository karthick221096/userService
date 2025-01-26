package com.karthick.userservice.dtos;

import com.karthick.userservice.model.Token;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDto {
    private Token token;
}
