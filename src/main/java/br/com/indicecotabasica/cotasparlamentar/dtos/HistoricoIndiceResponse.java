package br.com.indicecotabasica.cotasparlamentar.dtos;

import br.com.indicecotabasica.cotasparlamentar.enums.StatusCota;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class HistoricoIndiceResponse {

    private Long id;
    private Double valor;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private StatusCota status;
}
