package br.com.indicecotabasica.cotasparlamentar.models;

import jakarta.persistence.*;
import lombok.*;

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
    private List<HistoricoIndiceCotaBasica> historicoIndiceCotaBasica;
}
