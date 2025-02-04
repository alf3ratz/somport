package com.hse.somport.somport.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "token_jn")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "access_token")
    private String accessToken;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Column(name = "is_logged_out")
    private boolean loggedOut;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
