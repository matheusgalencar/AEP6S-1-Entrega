package com.agroPec.agropec.controller;

import com.agroPec.agropec.dto.VendaLoteDTO;
import com.agroPec.agropec.model.VendaModel;
import com.agroPec.agropec.service.VendaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.swing.plaf.OptionPaneUI;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/vendas")
@RequiredArgsConstructor

public class VendaController {
    private final VendaService vendaService;

    @PostMapping
    public ResponseEntity<VendaModel> venderAnimais(@Valid @RequestBody VendaLoteDTO dto){
        VendaModel venda = vendaService.venderLote(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(venda);
    }

    @GetMapping
    public ResponseEntity<List<VendaModel>> listarVendas(){
        return ResponseEntity.ok(vendaService.listarVendas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<VendaModel>> listarVendaId(@PathVariable String id){
        return ResponseEntity.ok(vendaService.listarVendaId(id));
    }

    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<Optional<VendaModel>> listarVendaCodigo(@PathVariable String codigoVenda){
        return ResponseEntity.ok(vendaService.listarVendaCodigo(codigoVenda));
    }
}
