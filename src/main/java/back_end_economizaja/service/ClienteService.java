package back_end_economizaja.service;

import back_end_economizaja.model.cliente.Cliente;
import back_end_economizaja.model.cliente.ClienteRepository;
import back_end_economizaja.model.cliente.ClienteRole;
import back_end_economizaja.model.cliente.DTO.CadastrarClienteDTO;
import back_end_economizaja.model.cliente.DTO.DadosCLienteDTO;
import back_end_economizaja.model.cliente.DTO.EditarClienteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

@Service
public class ClienteService{

    @Autowired
    private ClienteRepository repository;

    public void cadastrar(CadastrarClienteDTO cliente){
        Cliente novoCliente = new Cliente(cliente.primeiro_nome(), cliente.segundo_nome(), cliente.cpf(), cliente.email(), cliente.senha(), ClienteRole.CLIENTE);
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
