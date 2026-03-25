package br.com.indicecotabasica.cotasparlamentar.controllers;

import br.com.indicecotabasica.cotasparlamentar.dtos.*;
import br.com.indicecotabasica.cotasparlamentar.services.IndiceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/indices")
@RequiredArgsConstructor
public class IndiceController {

    private final IndiceService indiceService;

    @PostMapping
    public ResponseEntity<IndiceResponse> salvarIndice(@Valid @RequestBody IndiceSaveRequest indiceRequest) {
        return new ResponseEntity<>(this.indiceService.save(indiceRequest), HttpStatus.CREATED);
    }

    @GetMapping("/consultas/busca-entre-datas")
    public ResponseEntity<List<IndiceComHistoricoResponse>> consultarIndice(@RequestParam("dataInicio") LocalDate dataInicio, @RequestParam("dataFim") LocalDate dataFim) {
        return new ResponseEntity<>(this.indiceService.consultar(dataInicio, dataFim), HttpStatus.OK);
    }

    @GetMapping("/{id}/historicos")
    public ResponseEntity<IndiceComHistoricoResponse>buscarHistoricoDeIndice(@PathVariable Long id) {
        return new ResponseEntity<>(this.indiceService.buscarIndiceComHistorico(id), HttpStatus.OK);
    }

    @PutMapping("/{id}/atualizas")
    public ResponseEntity<IndiceResponse> atualizarIndice(@PathVariable Long id, @RequestBody IndiceUpdateRequest indiceUpdateRequest) {
        return new ResponseEntity<>(this.indiceService.atualizarIndice(id, indiceUpdateRequest), HttpStatus.OK);
    }
}
