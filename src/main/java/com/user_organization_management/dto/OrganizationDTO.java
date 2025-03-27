package com.user_organization_management.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationDTO {
    private Long id;

    @NotBlank(message = "Name must be required")
    private String name;
}
