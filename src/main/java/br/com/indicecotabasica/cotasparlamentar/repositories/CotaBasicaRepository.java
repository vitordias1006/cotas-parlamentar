package br.com.indicecotabasica.cotasparlamentar.repositories;

import br.com.indicecotabasica.cotasparlamentar.models.CotaBasica;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface CotaBasicaRepository extends JpaRepository<CotaBasica, Long> {

    boolean existsByUralAndValorCotaAndDataInicio(Double ural, Double valorCota, LocalDate dataInicio);

    List<CotaBasica> findByDataInicioBetweenOrderByDataInicioDesc(LocalDate dataInicio, LocalDate dataFim);

}
