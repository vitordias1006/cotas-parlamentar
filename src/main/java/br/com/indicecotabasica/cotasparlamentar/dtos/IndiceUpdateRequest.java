package br.com.indicecotabasica.cotasparlamentar.dtos;

import br.com.indicecotabasica.cotasparlamentar.enums.StatusCota;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IndiceUpdateRequest {

    @NotNull(message = "Valor deve estar preenchido")
    @Min(value = 100, message = "O valor minimo deve ser 100")
    @Max(value = 10000, message = "O valor maximo deve ser 10000")
    private Double valor;

    @NotNull(message = "Data Inicio deve ser preenchida")
    private LocalDate dataInicio;

    @NotNull(message = "Status deve ser preenchido")
    private StatusCota status;

    @NotNull(message = "Data Fim deve ser preenchida")
    private LocalDate dataFim;

    private Long id;
}
