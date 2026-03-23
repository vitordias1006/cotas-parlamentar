package br.com.indicecotabasica.cotasparlamentar.repositories;

import br.com.indicecotabasica.cotasparlamentar.models.HistoricoIndiceCotaBasica;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface HistoricoIndiceCotaBasicaRepository extends JpaRepository<HistoricoIndiceCotaBasica, Long> {

    boolean existsByValorAndDataInicio(Double valor, LocalDate dataInicio);

    List<HistoricoIndiceCotaBasica> findByDataInicioBetweenOrderByDataInicioDesc(LocalDate dataInicio, LocalDate dataFim);

    List<HistoricoIndiceCotaBasica> findByIndiceIdOrderByDataInicioDesc (Long id);


}
