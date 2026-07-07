package com.ucace.api.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class UserRequestDTO {

    private String userName;

    private String password;

    private String email;

    private Long mobileNo;

    private Boolean status;

    private Long roleId;

}
