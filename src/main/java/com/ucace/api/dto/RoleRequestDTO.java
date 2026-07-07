package com.ucace.api.dto;

import javax.validation.constraints.*;

import lombok.Data;

@Data
public class RoleRequestDTO {

    @NotBlank(message = "Role name is required")
    private String roleName;
    private String description;
    @NotNull(message = "Status is required")
    private Boolean status;
}
