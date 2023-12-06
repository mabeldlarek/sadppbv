package com.belarek.SADPPBV.dto;

import com.belarek.SADPPBV.dto.segmentos.SegmentoDTO;
import lombok.Data;

import java.util.List;

@Data
public class RotaCalculadaDTO {

  private List<SegmentoDTO> rota;
  private String message;
  private boolean sucess;
}
