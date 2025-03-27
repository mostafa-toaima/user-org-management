package com.user_organization_management.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;

    @NotNull(message = "name required")
    @Size(min = 3, max = 10, message = "Size from 3 to 20")
    private String name;

    @Email(message = "Invalid Email Format")
    @NotNull(message = "Email required")
    private String email;

    private String mobile;

    private Long organizationId;
    private String organizationName;


    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
}
