package back_end_economizaja.controller;

import back_end_economizaja.infra.security.TokenService;
import back_end_economizaja.model.cliente.DTO.*;
import back_end_economizaja.service.ClienteService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class ClienteController {

    @Autowired
    private ClienteService service;

    @Autowired
    private TokenService tokenService;

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
    public ResponseEntity<Map<String, String>> editar(@RequestBody @Valid EditarClienteDTO cliente, HttpServletRequest request){
        Boolean b = this.service.editar(cliente, request);

        if (b == true) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/listarDadosCliente")
    public ResponseEntity<DadosCLienteDTO> listarDadosCliente(HttpServletRequest request){
        return ResponseEntity.ok(this.service.listarDadosCliente(request));
    }
}
