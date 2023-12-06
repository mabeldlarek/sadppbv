package com.belarek.SADPPBV.repository;

import com.belarek.SADPPBV.entity.AuthToken;
import com.belarek.SADPPBV.entity.Segmento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TokenRepository extends JpaRepository<AuthToken, Long> {
    AuthToken findByToken(String token);
    @Query("SELECT t FROM AuthToken t WHERE t.isValido = true")
    List<AuthToken> findAllByIsValidoTrue();
}
