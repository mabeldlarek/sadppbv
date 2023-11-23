package com.belarek.SADPPBV.repository;

import com.belarek.SADPPBV.entity.AuthToken;
import com.belarek.SADPPBV.entity.Segmento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<AuthToken, Long> {
    AuthToken findByToken(String token);
}
