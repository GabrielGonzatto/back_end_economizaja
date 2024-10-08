package back_end_economizaja.controller;

import back_end_economizaja.model.lancamento.DTO.*;
import back_end_economizaja.model.lancamento.Lancamento;
import back_end_economizaja.service.LancamentoService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@CrossOrigin("*")
@RequestMapping("/lancamento")
public class LancamentoController {

    @Autowired
    private LancamentoService service;

    @PostMapping("/cadastrar")
    @Transactional
    public ResponseEntity<CadastrarLancamentoDTO> cadastrarReceita(@RequestBody @Valid CadastrarLancamentoDTO lancamentoDTO, HttpServletRequest request){
        this.service.cadastrar(lancamentoDTO, request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/deletar")
    public ResponseEntity<ListagemLancamentosPagarReceber> deletar (@RequestBody @Valid ListagemLancamentosPagarReceber lancamento) {
        this.service.deletar(lancamento);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/dadosHome")
    @Transactional
    public ResponseEntity<DadosHome> dadosHome(HttpServletRequest request){
        return ResponseEntity.ok(this.service.dadosHome(request));
    }

    @PostMapping("/dadosRelatorios")
    @Transactional
    public ResponseEntity<DadosRelatorios> dadosRelatorios(@RequestBody @Valid DataLancamentosConsultaDTO dataLancamento, HttpServletRequest request){
        return ResponseEntity.ok(this.service.dadosRelatorios(dataLancamento, request));
    }

    @PostMapping("/getLancamentosData")
    @Transactional
    public ResponseEntity<ArrayList<ListagemLancamentosPagarReceber>> getLancamentosData(@RequestBody @Valid DataLancamentosConsultaDTO dataLancamento, HttpServletRequest request){
        return ResponseEntity.ok(this.service.getLancamentosData(dataLancamento, request));
    }

    @PostMapping("/pagarDespagarLancamento")
    @Transactional
    public ResponseEntity<ListagemLancamentosPagarReceber> pagarDespagarLancamento(@RequestBody @Valid ListagemLancamentosPagarReceber item){
        this.service.pagarDespagarLancamento(item);
        return ResponseEntity.ok().build();
    }

}
