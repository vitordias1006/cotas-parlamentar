package br.com.indicecotabasica.cotasparlamentar.mappers;

import br.com.indicecotabasica.cotasparlamentar.dtos.HistoricoIndiceResponse;
import br.com.indicecotabasica.cotasparlamentar.dtos.IndiceComHistoricoResponse;
import br.com.indicecotabasica.cotasparlamentar.models.HistoricoIndiceCotaBasica;
import br.com.indicecotabasica.cotasparlamentar.models.Indice;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class IndiceMapper {
    public IndiceComHistoricoResponse toResponse(Indice indice, List<HistoricoIndiceCotaBasica> historicos) {
        return IndiceComHistoricoResponse.builder()
                .id(indice.getId())
                .nome(indice.getNome())
                .valor(indice.getValor())
                .historico(historicos.stream().map(this::toHistoricoResponse).toList())
                .build();
    }

    private HistoricoIndiceResponse toHistoricoResponse(HistoricoIndiceCotaBasica h) {
        return HistoricoIndiceResponse.builder()
                .id(h.getId())
                .dataInicio(h.getDataInicio())
                .dataFim(h.getDataFim())
                .valor(h.getValor())
                .status(h.getStatus())
                .build();
    }
}
