package com.hse.somport.somport.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Getter
@Builder
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "user_ref")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotBlank(message = "Name is mandatory")
    private String username;

    @NotBlank(message = "Email is mandatory")
    private String password;
}