package back_end_economizaja.model.cliente.DTO;

public record EditarClienteDTO(Long id, String primeiro_nome, String segundo_nome, String cpf, String email, String senha) {
}
