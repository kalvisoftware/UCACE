package com.ucace.api.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class UserResponseDTO {
    private Long id;
    private String userName;
    private String email;
    private Long mobileNo;
    private String roleName;
    private Boolean status;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
