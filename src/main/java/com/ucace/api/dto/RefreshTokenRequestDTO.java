package com.ucace.api.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public abstract class RefreshTokenRequestDTO {
    @NotBlank(message = "Refresh Token is Required")
    private String refreshToken;
}
