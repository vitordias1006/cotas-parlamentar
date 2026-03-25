package br.com.indicecotabasica.cotasparlamentar.controllers;

import br.com.indicecotabasica.cotasparlamentar.dtos.CotaBasicaResponse;
import br.com.indicecotabasica.cotasparlamentar.dtos.CotaBasicaSaveRequest;
import br.com.indicecotabasica.cotasparlamentar.services.CotaBasicaService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("cotas")
@AllArgsConstructor
public class CotaBasicaController {

    private final CotaBasicaService cotaBasicaService;

    @PostMapping
    public ResponseEntity<CotaBasicaResponse> save(@Valid @RequestBody CotaBasicaSaveRequest request) {
        return new ResponseEntity<>(this.cotaBasicaService.cadastrar(request), HttpStatus.CREATED);
    }

    @GetMapping("consultas/busca-entre-datas")
    public ResponseEntity<List<CotaBasicaResponse>> findByDate(@RequestParam("dataInicio") LocalDate dataInicio, @RequestParam("dataFim") LocalDate dataFim) {
        return new ResponseEntity<>(this.cotaBasicaService.findCotaByDate(dataInicio, dataFim), HttpStatus.OK);
    }

    @PutMapping("consultas/atualizas/{id}")
    public ResponseEntity<CotaBasicaResponse> atualizar(@Valid @RequestBody CotaBasicaSaveRequest request, @RequestParam("dataInicio" ) LocalDate dataInicio, @RequestParam("dataFim") LocalDate dataFim, @PathVariable Long id) {
        return new ResponseEntity<>(this.cotaBasicaService.atualizarCota(id, dataInicio, dataFim, request), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id, @RequestParam ("dataInicio") LocalDate dataInicio, @RequestParam ("dataFim") LocalDate dataFim) {
        this.cotaBasicaService.excluirCota(id, dataInicio, dataFim);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
