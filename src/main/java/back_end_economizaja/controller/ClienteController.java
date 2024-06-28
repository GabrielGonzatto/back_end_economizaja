package back_end_economizaja.controller;

import back_end_economizaja.model.cliente.DTO.*;
import back_end_economizaja.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class ClienteController {

    @Autowired
    private ClienteService service;

    @PostMapping("/login")
    @Transactional
    public ResponseEntity<RespostaLogin> login(@RequestBody @Valid LoginClienteDTO cliente){
        return ResponseEntity.ok(this.service.login(cliente));
    }

    @PostMapping("/cadastrar")
    @Transactional
    public ResponseEntity<CadastrarClienteDTO> cadastrar(@RequestBody @Valid CadastrarClienteDTO cliente){
        this.service.cadastrar(cliente);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/editar")
    @Transactional
    public ResponseEntity<EditarClienteDTO> editar(@RequestBody EditarClienteDTO cliente){
        this.service.editar(cliente);
        return ResponseEntity.ok(cliente);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosCLienteDTO> listarDadosCliente(@PathVariable Long id){
        return ResponseEntity.ok(this.service.listarDadosCliente(id));
    }
}
