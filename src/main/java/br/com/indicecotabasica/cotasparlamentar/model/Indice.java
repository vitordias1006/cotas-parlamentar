package br.com.indicecotabasica.cotasparlamentar.model;

import br.com.indicecotabasica.cotasparlamentar.enums.StatusCota;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Indice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private Double valor;

    @OneToMany(mappedBy = "indice")
    private List<HistoricoIndice> historicoIndice;
}
