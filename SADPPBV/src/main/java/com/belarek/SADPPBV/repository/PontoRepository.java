package com.belarek.SADPPBV.repository;

import com.belarek.SADPPBV.entity.Ponto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PontoRepository extends JpaRepository<Ponto, Integer> {
}
