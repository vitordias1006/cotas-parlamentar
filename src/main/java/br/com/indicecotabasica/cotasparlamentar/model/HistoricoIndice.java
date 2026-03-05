package br.com.indicecotabasica.cotasparlamentar.model;

import br.com.indicecotabasica.cotasparlamentar.enums.StatusCota;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HistoricoIndice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dataInicio;
    private LocalDate dataFim;
    private Double valor;
    private String descricao;

    @Enumerated(EnumType.STRING)
    private StatusCota status;

    @ManyToOne
    @JoinColumn(name = "cd_indice")
    private Indice indice;
}
