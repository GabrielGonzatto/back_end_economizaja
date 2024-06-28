package back_end_economizaja.service;

import back_end_economizaja.infra.security.TokenService;
import back_end_economizaja.model.cliente.Cliente;
import back_end_economizaja.model.cliente.ClienteRepository;
import back_end_economizaja.model.cliente.DTO.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

@Service
public class ClienteService{

    @Autowired
    private ClienteRepository repository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private TokenService tokenService;

    public RespostaLogin login (LoginClienteDTO cliente) {
        Cliente c = repository.findByEmailAndPassword(cliente.email());
        if (c != null){
            Boolean b = bCryptPasswordEncoder.matches(cliente.senha(), c.getSenha());

            if (b == true) {
                return new RespostaLogin(this.tokenService.gerarToken(c.getId()));
            }
            System.out.println("login incorreto");
        }
        System.out.println("Cliente não encontrado");
        throw new BadCredentialsException("Cliente não encontrado") ;
    }

    public void cadastrar(CadastrarClienteDTO cliente){
        Cliente novoCliente = new Cliente(cliente.primeiro_nome(), cliente.segundo_nome(), cliente.cpf(), cliente.email(), this.bCryptPasswordEncoder.encode(cliente.senha()));

        this.repository.save(novoCliente);
    }

    public void editar(EditarClienteDTO cliente){
        Cliente c = buscaCliente(cliente.id());
        c = this.repository.getReferenceById(c.getId());

        c.setPrimeiro_nome(cliente.primeiro_nome());
        c.setSegundo_nome(cliente.segundo_nome());
        c.setCpf(cliente.cpf());
        c.setEmail(cliente.email());
        c.setSenha(cliente.senha());
    }

    public DadosCLienteDTO listarDadosCliente(Long id){
        Cliente c = buscaCliente(id);
        return new DadosCLienteDTO(c.getPrimeiro_nome(), c.getSegundo_nome(), c.getCpf(), c.getEmail());
    }

    public Cliente buscaCliente(Long id){
        return repository.findById(id.intValue());
    }

    public ArrayList<Cliente> listarTudoClienteTeste(){
        return this.repository.findAll();
    }
}
