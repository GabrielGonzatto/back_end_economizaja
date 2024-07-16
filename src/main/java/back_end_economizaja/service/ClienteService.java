package back_end_economizaja.service;

import back_end_economizaja.infra.security.TokenService;
import back_end_economizaja.model.cliente.Cliente;
import back_end_economizaja.model.cliente.ClienteRepository;
import back_end_economizaja.model.cliente.DTO.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

@Service
public class ClienteService{

    @Autowired
    private ClienteRepository repository;

    @Autowired
    private TokenService tokenService;

    public RespostaLogin login (LoginClienteDTO cliente) {
        Cliente c = repository.findByEmailAndPassword(cliente.email());

        if (c != null){
            Boolean b = cliente.senha().equals(c.getSenha());

            if (b == true) {
                return this.tokenService.gerarToken(c.getId());
            }
            System.out.println("login incorreto");
        }
        System.out.println("Cliente não encontrado");
        throw new BadCredentialsException("Cliente não encontrado") ;
    }

    public void cadastrar(CadastrarClienteDTO cliente){
        Cliente novoCliente = new Cliente(cliente.primeiro_nome(), cliente.segundo_nome(), cliente.cpf(), cliente.email(), cliente.senha());

        this.repository.save(novoCliente);
    }

    public Boolean editar(EditarClienteDTO cliente, HttpServletRequest request){
        Cliente cliente_banco = this.repository.getReferenceById(Long.valueOf(this.tokenService.recuperarIdDoToken(request)));

        if (cliente_banco.getSenha().equals(cliente.senha())) {
            cliente_banco.setPrimeiro_nome(cliente.primeiro_nome());
            cliente_banco.setSegundo_nome(cliente.segundo_nome());
            cliente_banco.setCpf(cliente.cpf());
            //cliente_banco.setSenha(cliente.nova_senha());

            return true;
        }
        return false;
    }

    public DadosCLienteDTO listarDadosCliente(HttpServletRequest request){
        Cliente c = buscaCliente(this.tokenService.recuperarIdDoToken(request));
        return new DadosCLienteDTO(c.getPrimeiro_nome(), c.getSegundo_nome(), c.getCpf(), c.getEmail());
    }

    public Cliente buscaCliente(String id){
        return repository.findById(Integer.parseInt(id));
    }

    public Cliente geraCliente(String id){
        Cliente cliente = new Cliente();
        cliente.setId(Long.parseLong(id));
        return cliente;
    }

    public ArrayList<Cliente> listarTudoClienteTeste(){
        return this.repository.findAll();
    }
}
