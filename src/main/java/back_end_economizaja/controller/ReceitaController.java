package back_end_economizaja.controller;

import back_end_economizaja.model.receita.Receita;
import back_end_economizaja.model.receita.receitaDTO.CadastrarReceitaDTO;
import back_end_economizaja.model.receita.receitaDTO.EditarReceitaDTO;
import back_end_economizaja.service.ReceitaService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@CrossOrigin(origins = "4200")
@RequestMapping("/receita")
public class ReceitaController {
    @Autowired
    private ReceitaService service;

    @PostMapping("/cadastrar")
    @Transactional
    public ResponseEntity<CadastrarReceitaDTO> cadastrar(@RequestBody @Valid CadastrarReceitaDTO receita){
        this.service.cadastrar(receita);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/")
    @Transactional
    public ResponseEntity<Receita> editar(@RequestBody EditarReceitaDTO receita){
        this.service.editar(receita);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Receita> desativar(@PathVariable Long id){
        this.service.desativar(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/receber/{id}")
    public ResponseEntity<Receita> receberReceita (@PathVariable Long id){
        this.service.receberReceita(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/receitasPagas")
    public void listarReceitasPagas (HttpServletRequest request){
        //this.service.listarReceitasPagas(request);
    }

    @GetMapping("/teste")
    public ResponseEntity<ArrayList<Receita>> listarTudoReceitaTeste(){
        return ResponseEntity.ok(this.service.listarTudoReceitaTeste());
    }
}
