package back_end_economizaja.model.receita.receitaDTO;

import back_end_economizaja.model.categoria.Categoria;

import java.time.LocalDate;

public record EditarReceitaDTO(Long id, String descricao, Double valor, LocalDate data, Integer numero_de_parcelas, Categoria categoria) {
}
