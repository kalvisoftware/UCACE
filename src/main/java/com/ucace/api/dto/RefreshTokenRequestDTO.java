package com.ucace.api.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class RefreshTokenRequestDTO {
    @NotBlank(message = "Refresh Token is Required")
    private String refreshToken;
}
