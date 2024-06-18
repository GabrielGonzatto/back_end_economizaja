package back_end_economizaja.model.cliente.DTO;

import back_end_economizaja.model.cliente.ClienteRole;

public record CadastrarClienteDTO(String primeiro_nome, String segundo_nome, String cpf, String email, String senha, ClienteRole role) {

}
