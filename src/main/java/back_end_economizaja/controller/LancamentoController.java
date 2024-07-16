package back_end_economizaja.controller;

import back_end_economizaja.model.lancamento.DTO.CadastrarLancamentoDTO;
import back_end_economizaja.model.lancamento.DTO.DadosHome;
import back_end_economizaja.model.lancamento.DTO.ListagemHomePagarReceber;
import back_end_economizaja.model.lancamento.Lancamento;
import back_end_economizaja.service.LancamentoService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/dadosHome")
    @Transactional
    public ResponseEntity<DadosHome> dadosHome(HttpServletRequest request){
        return ResponseEntity.ok(this.service.dadosHome(request));
    }

    @PostMapping("/pagarDespagarLancamento")
    @Transactional
    public ResponseEntity<ListagemHomePagarReceber> pagarDespagarLancamento(@RequestBody @Valid ListagemHomePagarReceber item){
        this.service.pagarDespagarLancamento(item);
        return ResponseEntity.ok().build();
    }

}
