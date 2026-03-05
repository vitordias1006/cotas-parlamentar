package br.com.indicecotabasica.cotasparlamentar.repositories;

import br.com.indicecotabasica.cotasparlamentar.model.HistoricoIndice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface HistoricoIndiceRepository extends JpaRepository<HistoricoIndice, Long> {

    boolean existsByValorAndDataInicio(Double valor, LocalDate dataInicio);

    List<HistoricoIndice> findByDataInicioBetweenOrderByDataInicioDesc(LocalDate dataInicio, LocalDate dataFim);

    List<HistoricoIndice> findByIndiceIdOrderByDataInicioDesc (Long id);


}
