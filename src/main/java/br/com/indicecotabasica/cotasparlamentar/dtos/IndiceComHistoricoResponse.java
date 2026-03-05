package br.com.indicecotabasica.cotasparlamentar.dtos;

import br.com.indicecotabasica.cotasparlamentar.model.HistoricoIndice;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IndiceComHistoricoResponse {

    private Long id;
    private String nome;
    private Double valor;

    private List<HistoricoIndiceResponse> historico;
}
