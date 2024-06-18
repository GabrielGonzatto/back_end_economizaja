package back_end_economizaja.model.categoria.categoriaDTO;

import back_end_economizaja.model.cliente.Cliente;

public record CadastrarCategoriaDTO(String nome, String tipo, Boolean ativo, Cliente cliente) {
}
