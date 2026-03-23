package br.com.indicecotabasica.cotasparlamentar.dtos;

import br.com.indicecotabasica.cotasparlamentar.enums.StatusCota;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
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
public class IndiceSaveRequest {

    private Long id;

    @NotNull(message = "Valor deve estar preenchido")
    @Min(value = 100, message = "O valor minimo deve ser 100")
    @Max(value = 10000, message = "O valor maximo deve ser 10000")
    private Double valor;

    @NotBlank(message = "Nome deve ser preenchido")
    private String nome;

    @NotNull(message = "Data deve ser preenchida")
    private LocalDate dataInicio;

    private StatusCota status;

}
