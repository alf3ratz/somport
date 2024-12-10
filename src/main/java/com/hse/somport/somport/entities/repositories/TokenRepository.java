package com.hse.somport.somport.entities.repositories;

import com.hse.somport.somport.entities.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<TokenEntity, Long> {


    @Query("""
            SELECT t FROM TokenEntity t inner join UserEntity u
            on t.user.id = u.id
            where t.user.id = :userId and t.loggedOut = false
            """)
    List<TokenEntity> findAllAccessTokenByUser(Long userId);

    Optional<TokenEntity> findByAccessToken(String accessToken);

    Optional<TokenEntity> findByRefreshToken(String refreshToken);
}
