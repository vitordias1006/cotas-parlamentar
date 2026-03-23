package br.com.indicecotabasica.cotasparlamentar.dtos;

import br.com.indicecotabasica.cotasparlamentar.enums.StatusCota;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CotaBasicaResponse {

    private Long id;
    private Double ural;
    private Integer quantidade;
    private Double valorCota;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private StatusCota statusCota;
}
