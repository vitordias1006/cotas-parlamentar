package br.com.indicecotabasica.cotasparlamentar.services;

import br.com.indicecotabasica.cotasparlamentar.dtos.*;
import br.com.indicecotabasica.cotasparlamentar.enums.StatusCota;
import br.com.indicecotabasica.cotasparlamentar.handlers.DateAndValueOcuppiedException;
import br.com.indicecotabasica.cotasparlamentar.handlers.InvalidDateRequestException;
import br.com.indicecotabasica.cotasparlamentar.handlers.ResourceNotFoundException;
import br.com.indicecotabasica.cotasparlamentar.mappers.IndiceMapper;
import br.com.indicecotabasica.cotasparlamentar.models.HistoricoIndiceCotaBasica;
import br.com.indicecotabasica.cotasparlamentar.models.Indice;
import br.com.indicecotabasica.cotasparlamentar.repositories.HistoricoIndiceCotaBasicaRepository;
import br.com.indicecotabasica.cotasparlamentar.repositories.IndiceCotaBasicaRepository;
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

    private final IndiceCotaBasicaRepository indiceCotaBasicaRepository;

    private final HistoricoIndiceCotaBasicaRepository historicoIndiceCotaBasicaRepository;

    public IndiceResponse save(IndiceSaveRequest indiceRequest) {

        if (historicoIndiceCotaBasicaRepository.existsByValorAndDataInicio(indiceRequest.getValor(), indiceRequest.getDataInicio())) {
            throw new DateAndValueOcuppiedException("Esse valor já existe para a data informada");
        }
        Indice indice = Indice.builder()
                .nome(indiceRequest.getNome())
                .valor(indiceRequest.getValor())
                .build();
        indiceRequest.setStatus(StatusCota.EM_ESPERA);
        indiceCotaBasicaRepository.save(indice);

        HistoricoIndiceCotaBasica historicoIndiceCotaBasica = HistoricoIndiceCotaBasica.builder()
                .valor(indiceRequest.getValor())
                .dataInicio(indiceRequest.getDataInicio())
                .indice(indice)
                .status(indiceRequest.getStatus())
                .build();
        historicoIndiceCotaBasicaRepository.save(historicoIndiceCotaBasica);

        return IndiceResponse.builder()
                .id(indice.getId())
                .valor(indiceRequest.getValor())
                .dataInicio(indiceRequest.getDataInicio())
                .status(indiceRequest.getStatus())
                .build();
    }

    public List<IndiceComHistoricoResponse> consultar(LocalDate dataInicio, LocalDate dataFim) {
        if (!dataInicio.isBefore(dataFim)) {
            throw new InvalidDateRequestException("A data de inicio deve ser antes da data fim");
        }

        List<HistoricoIndiceCotaBasica> historicos = historicoIndiceCotaBasicaRepository
                .findByDataInicioBetweenOrderByDataInicioDesc(dataInicio, dataFim);


        return  historicos.stream()
                .collect(Collectors.groupingBy(HistoricoIndiceCotaBasica::getIndice))
                .entrySet().stream()
                .map(entry -> indiceMapper.toResponse(entry.getKey(), entry.getValue()))
                .toList();
    }

    public IndiceResponse atualizarIndice(Long id, IndiceUpdateRequest request) {

        Indice indice = indiceCotaBasicaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Índice não encontrado"));

        indiceCotaBasicaRepository.save(indice);

        IndiceResponse indiceResponse =  IndiceResponse.builder()
                .id(indice.getId())
                .valor(request.getValor())
                .status(request.getStatus())
                .dataInicio(request.getDataInicio())
                .dataFim(request.getDataFim())
                .build();

        HistoricoIndiceCotaBasica historico = HistoricoIndiceCotaBasica.builder()
                .valor(request.getValor())
                .dataInicio(request.getDataInicio())
                .dataFim(request.getDataFim())
                .status(request.getStatus())
                .indice(indice)
                .build();

        historicoIndiceCotaBasicaRepository.save(historico);

        return indiceResponse;
    }

    public IndiceComHistoricoResponse buscarIndiceComHistorico(Long id) {

        Indice indice = indiceCotaBasicaRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Índice não encontrado"));

        List<HistoricoIndiceCotaBasica> historicos = historicoIndiceCotaBasicaRepository.findByIndiceIdOrderByDataInicioDesc(id);

        return indiceMapper.toResponse(indice, historicos);
    }


}
