package br.com.indicecotabasica.cotasparlamentar.services;

import br.com.indicecotabasica.cotasparlamentar.dtos.*;
import br.com.indicecotabasica.cotasparlamentar.enums.StatusCota;
import br.com.indicecotabasica.cotasparlamentar.handler.DateAndValueOcuppiedException;
import br.com.indicecotabasica.cotasparlamentar.handler.IndiceDateRequestException;
import br.com.indicecotabasica.cotasparlamentar.handler.ResourceNotFoundException;
import br.com.indicecotabasica.cotasparlamentar.mappers.IndiceMapper;
import br.com.indicecotabasica.cotasparlamentar.model.HistoricoIndice;
import br.com.indicecotabasica.cotasparlamentar.model.Indice;
import br.com.indicecotabasica.cotasparlamentar.repositories.HistoricoIndiceRepository;
import br.com.indicecotabasica.cotasparlamentar.repositories.IndiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IndiceService {

    @Autowired
    private IndiceMapper indiceMapper;

    private final IndiceRepository indiceRepository;

    private final HistoricoIndiceRepository historicoIndiceRepository;

    public IndiceResponse save(IndiceSaveRequest indiceRequest) {

        if (historicoIndiceRepository.existsByValorAndDataInicio(indiceRequest.getValor(), indiceRequest.getDataInicio())) {
            throw new DateAndValueOcuppiedException("Esse valor já existe para a data informada");
        }
        Indice indice = Indice.builder()
                .nome(indiceRequest.getNome())
                .valor(indiceRequest.getValor())
                .build();
        indiceRequest.setStatus(StatusCota.EM_ESPERA);
        indiceRepository.save(indice);

        HistoricoIndice historicoIndice = HistoricoIndice.builder()
                .valor(indiceRequest.getValor())
                .dataInicio(indiceRequest.getDataInicio())
                .indice(indice)
                .status(indiceRequest.getStatus())
                .build();
        historicoIndiceRepository.save(historicoIndice);

        return IndiceResponse.builder()
                .id(indice.getId())
                .valor(indiceRequest.getValor())
                .dataInicio(indiceRequest.getDataInicio())
                .status(indiceRequest.getStatus())
                .build();
    }

    public List<IndiceComHistoricoResponse> consultar(LocalDate dataInicio, LocalDate dataFim) {
        if (!dataInicio.isBefore(dataFim)) {
            throw new IndiceDateRequestException("A data de inicio deve ser antes da data fim");
        }

        List<HistoricoIndice> historicos = historicoIndiceRepository
                .findByDataInicioBetweenOrderByDataInicioDesc(dataInicio, dataFim);


        return  historicos.stream()
                .collect(Collectors.groupingBy(HistoricoIndice::getIndice))
                .entrySet().stream()
                .map(entry -> indiceMapper.toResponse(entry.getKey(), entry.getValue()))
                .toList();
    }

    public IndiceResponse atualizarIndice(Long id, IndiceUpdateRequest request) {

        Indice indice = indiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Índice não encontrado"));

        indiceRepository.save(indice);

        IndiceResponse indiceResponse =  IndiceResponse.builder()
                .id(indice.getId())
                .valor(request.getValor())
                .status(request.getStatus())
                .dataInicio(request.getDataInicio())
                .dataFim(request.getDataFim())
                .build();

        HistoricoIndice historico = HistoricoIndice.builder()
                .valor(request.getValor())
                .dataInicio(request.getDataInicio())
                .dataFim(request.getDataFim())
                .status(request.getStatus())
                .indice(indice)
                .build();

        historicoIndiceRepository.save(historico);

        return indiceResponse;
    }

    public IndiceComHistoricoResponse buscarIndiceComHistorico(Long id) {

        Indice indice = indiceRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Índice não encontrado"));

        List<HistoricoIndice> historicos = historicoIndiceRepository.findByIndiceIdOrderByDataInicioDesc(id);

        return indiceMapper.toResponse(indice, historicos);
    }


}
