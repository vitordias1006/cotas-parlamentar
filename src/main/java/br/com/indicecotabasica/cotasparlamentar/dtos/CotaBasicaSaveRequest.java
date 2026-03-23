package br.com.indicecotabasica.cotasparlamentar.dtos;

import br.com.indicecotabasica.cotasparlamentar.enums.StatusCota;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CotaBasicaSaveRequest {

    private Long id;

    @NotNull(message = "UR-AL é obrigatório")
    @DecimalMin(value = "1", message = "O valor mínimo permitido é 1")
    @DecimalMax(value = "99.99", message = "O valor máximo permitido é 99.99")
    private Double ural;

    @NotNull(message = "Quantidade é obrigatório")

    @Min(1)
    @Max(9999)
    private Integer quantidade;

    public Double getValorCota() {
        if (quantidade == null || ural == null) {
            return null;
        }
        return quantidade * ural;
    }

    @NotNull(message = "Data de Inicio é obrigatória")
    private LocalDate dataInicio;

    private StatusCota statusCota;

    private LocalDate dataFim;
}
