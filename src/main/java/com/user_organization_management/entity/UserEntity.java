package com.user_organization_management.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Column(unique = true)
    private String name;
    private String password;
    @Email
    @NotBlank
    private String email;
    @NotBlank
    private String mobile;
    @ManyToOne
    @JoinColumn(name = "organization_id")
    private OrganizationEntity organization;

}
