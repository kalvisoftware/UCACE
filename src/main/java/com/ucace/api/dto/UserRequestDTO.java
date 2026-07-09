package com.ucace.api.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class UserRequestDTO {

    @NotNull(message = "Username is required")
    private String userName;

    @NotBlank(message = "Passwprd is required")
    private String password;

    @Email(message = "")
    private String email;

    private Long mobileNo;

    private Boolean status;

    private Long roleId;

}
