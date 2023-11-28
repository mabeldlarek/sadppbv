package com.belarek.SADPPBV.repository;

import com.belarek.SADPPBV.entity.Ponto;
import com.belarek.SADPPBV.entity.Segmento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SegmentoRepository extends JpaRepository<Segmento, Long> {
    @Query("SELECT s FROM Segmento s WHERE s.ponto_inicial = :pontoInicial AND s.ponto_final = :pontoFinal")
    Segmento findCustomQueryByPontos(Ponto pontoInicial, Ponto pontoFinal);

}
