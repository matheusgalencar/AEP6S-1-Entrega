package com.agroPec.agropec.controller;

import com.agroPec.agropec.dto.LoteRequestDTO;
import com.agroPec.agropec.model.LoteModel;
import com.agroPec.agropec.service.LoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.lang.management.OperatingSystemMXBean;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/lotes")
@RequiredArgsConstructor
public class LoteController {
    private final LoteService loteService;

    @PostMapping
    public ResponseEntity<LoteModel> cadastrarLote(@Valid @RequestBody LoteRequestDTO dto){
        LoteModel lote = loteService.cadastrarLote(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(lote);
    }

    @GetMapping
    public ResponseEntity<List<LoteModel>> listarLotes(){
        return ResponseEntity.ok(loteService.listarLotes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<LoteModel>> buscarLotePorId(@PathVariable("id") String idLote){
        return ResponseEntity.ok(loteService.listarLoteId(idLote));
    }

    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<Optional<LoteModel>> buscarLotePorCodigo(@PathVariable("codigo") String codigoLote){
        return ResponseEntity.ok(loteService.buscarPorCodigo(codigoLote));
    }
}
