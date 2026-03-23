package br.com.indicecotabasica.cotasparlamentar.services;

import br.com.indicecotabasica.cotasparlamentar.dtos.CotaBasicaResponse;
import br.com.indicecotabasica.cotasparlamentar.dtos.CotaBasicaSaveRequest;
import br.com.indicecotabasica.cotasparlamentar.enums.StatusCota;
import br.com.indicecotabasica.cotasparlamentar.handlers.CotaBasicaAlreadyExistsException;
import br.com.indicecotabasica.cotasparlamentar.handlers.InvalidDateRequestException;
import br.com.indicecotabasica.cotasparlamentar.handlers.ResourceNotFoundException;
import br.com.indicecotabasica.cotasparlamentar.handlers.UnavailableCotaRequestException;
import br.com.indicecotabasica.cotasparlamentar.mappers.CotaMapper;
import br.com.indicecotabasica.cotasparlamentar.models.CotaBasica;
import br.com.indicecotabasica.cotasparlamentar.repositories.CotaBasicaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Service
@AllArgsConstructor
public class CotaBasicaService {

    private CotaMapper cotaMapper;

    private final CotaBasicaRepository cotaBasicaRepository;

    public CotaBasicaResponse cadastrar(CotaBasicaSaveRequest request) {
        if (cotaBasicaRepository.existsByUralAndValorCotaAndDataInicio(request.getUral(), request.getValorCota(), request.getDataInicio())) {
            throw new CotaBasicaAlreadyExistsException("Cota básica com esses valores já existente no sistema");
        }

        request.setStatusCota(StatusCota.EM_ESPERA);

        CotaBasica cotaBasica = CotaBasica.builder()
                .id(request.getId())
                .ural(request.getUral())
                .quantidade(request.getQuantidade())
                .dataInicio(request.getDataInicio())
                .valorCota(request.getValorCota())
                .statusCota(request.getStatusCota())
                .dataFim(request.getDataInicio().with(TemporalAdjusters.lastDayOfMonth()))
                .build();

        cotaBasicaRepository.save(cotaBasica);

        return CotaBasicaResponse.builder()
                .id(cotaBasica.getId())
                .ural(cotaBasica.getUral())
                .quantidade(cotaBasica.getQuantidade())
                .valorCota(cotaBasica.getValorCota())
                .dataInicio(cotaBasica.getDataInicio())
                .dataFim(cotaBasica.getDataFim())
                .statusCota(cotaBasica.getStatusCota())
                .build();
    }

    public List<CotaBasicaResponse> findCotaByDate(LocalDate dataInicio, LocalDate dataFim) {
        if (!dataInicio.isBefore(dataFim)) {
            throw new InvalidDateRequestException("A data inicial deve ser antes da data final");
        }

        return cotaBasicaRepository.findByDataInicioBetweenOrderByDataInicioDesc(dataInicio, dataFim).stream().map(cotaMapper::toCotaResponse).toList();
    }

    public CotaBasicaResponse atualizarCota(Long id, LocalDate dataInicio, LocalDate dataFim, CotaBasicaSaveRequest request) {
        findCotaByDate(dataInicio, dataFim);

        CotaBasica cotaBasica = cotaBasicaRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Não existe cota para o id: " + id)
        );

        if (!cotaBasica.getStatusCota().equals(StatusCota.EM_ESPERA)) {
            throw new UnavailableCotaRequestException("Essa cota não pode ser atualizada pois seu status não é EM_ESPERA");
        }

        cotaBasica.setUral(request.getUral());
        cotaBasica.setQuantidade(request.getQuantidade());
        cotaBasica.setStatusCota(request.getStatusCota());

        cotaBasicaRepository.save(cotaBasica);

        return cotaMapper.toCotaResponse(cotaBasica);
    }

    public void excluirCota(Long id, LocalDate dataInicio, LocalDate dataFim) {
        findCotaByDate(dataInicio, dataFim);

        CotaBasica cotaBasica = cotaBasicaRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Não existe cota para o id: " + id)
        );

        if (!cotaBasica.getStatusCota().equals(StatusCota.EM_ESPERA)) {
            throw new UnavailableCotaRequestException("Essa cota não pode ser atualizada pois seu status não é EM_ESPERA");
        }

        cotaBasicaRepository.deleteById(id);
    }

}
