package back_end_economizaja.model.receita.receitaDTO;

import back_end_economizaja.model.categoria.Categoria;
import back_end_economizaja.model.cliente.Cliente;

import java.time.LocalDate;

public record CadastrarReceitaDTO(String descricao, Double valor, LocalDate data, Boolean recebida, Boolean fixa, Boolean parcelada, Integer numero_de_parcelas, Categoria categoria, Cliente cliente) {
}
