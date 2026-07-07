package com.ucace.api.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class RoleResponseDTO {
    private Long id;
    private String roleName;
    private String description;
    private Boolean status;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

}
