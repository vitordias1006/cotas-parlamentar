package br.com.indicecotabasica.cotasparlamentar.mappers;

import br.com.indicecotabasica.cotasparlamentar.dtos.HistoricoIndiceResponse;
import br.com.indicecotabasica.cotasparlamentar.dtos.IndiceComHistoricoResponse;
import br.com.indicecotabasica.cotasparlamentar.model.HistoricoIndice;
import br.com.indicecotabasica.cotasparlamentar.model.Indice;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class IndiceMapper {
    public IndiceComHistoricoResponse toResponse(Indice indice, List<HistoricoIndice> historicos) {
        return IndiceComHistoricoResponse.builder()
                .id(indice.getId())
                .nome(indice.getNome())
                .valor(indice.getValor())
                .historico(historicos.stream().map(this::toHistoricoResponse).toList())
                .build();
    }

    private HistoricoIndiceResponse toHistoricoResponse(HistoricoIndice h) {
        return HistoricoIndiceResponse.builder()
                .id(h.getId())
                .dataInicio(h.getDataInicio())
                .dataFim(h.getDataFim())
                .valor(h.getValor())
                .status(h.getStatus())
                .build();
    }
}
