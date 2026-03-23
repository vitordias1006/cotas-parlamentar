package br.com.indicecotabasica.cotasparlamentar.models;

import br.com.indicecotabasica.cotasparlamentar.enums.StatusCota;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

@Entity
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class CotaBasica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double ural;

    private Integer quantidade;

    @Setter(AccessLevel.NONE)
    private Double valorCota;

    private LocalDate dataInicio;

    private LocalDate dataFim;

    private StatusCota statusCota;
}
